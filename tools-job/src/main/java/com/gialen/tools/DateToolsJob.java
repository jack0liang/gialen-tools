package com.gialen.tools;

import com.gialen.common.utils.DateTools;
import com.gialen.tools.common.constant.DataToolsConstant;
import com.gialen.tools.common.enums.DataCountTypeEnum;
import com.gialen.tools.common.enums.PlatFormTypeEnum;
import com.gialen.tools.dao.dto.DateTimeDto;
import com.gialen.tools.dao.dto.OrderDto;
import com.gialen.tools.dao.dto.SalesDto;
import com.gialen.tools.dao.entity.tools.DataTimerReportForm;
import com.gialen.tools.dao.repository.customer.extend.UserExtendMapper;
import com.gialen.tools.dao.repository.tools.extend.DataTimerReportFormExtendMapper;
import com.gialen.tools.dao.repository.order.extend.OrdersExtendMapper;
import com.gialen.tools.dao.repository.point.GdataPointMapper;
import com.gialen.tools.dao.repository.point.UvStatDayMapper;
import com.gialen.tools.dao.util.DateTimeDtoBuilder;
import com.gialen.tools.service.DataToolsService;
import com.gialen.tools.service.model.UserDataModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wong
 * @Date: 2019-09-27
 * @Version: 1.0
 */
@Component
@Slf4j
@EnableScheduling
public class DateToolsJob {

    @Autowired
    private DataToolsService toolsService;

    @Autowired
    private UserExtendMapper userMapper;

    @Autowired
    private OrdersExtendMapper ordersMapper;

    @Autowired
    private GdataPointMapper gdataPointMapper;

    @Autowired
    private DataTimerReportFormExtendMapper countMapper;

    @Autowired
    private UvStatDayMapper uvStatDayMapper;


    @PostConstruct
    public void onStart(){
        System.out.println();
    }


//    @Scheduled(cron = "0 0/1 * * * ?")
    public void queryJob() {

        try {
            /**
             * 统计时间
             */
            String format = DateTools.date2String(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH") + ":00:00";
            log.info("定时器开始执行。currentTime={}", format);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateTools.string2Date(format, DateTools.LONG_DATE_FORMAT));
            long endTime = calendar.getTime().getTime();
            calendar.set(Calendar.HOUR, -1);
            long startTime = calendar.getTime().getTime();

            /**
             * 统计并且缓存过去一小时内的数据
             */
            DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);
            saveSaleNumber(dateTimeDto);
            savePlatformOrderNumber(dateTimeDto);
            saveOrderConvertRate(dateTimeDto);
            saveOrderUser(startTime,endTime);
            saveUv(dateTimeDto);

        } catch (Exception e) {
            log.error("定时统计小娇数坊信息异常", e);
        }
    }


    /**
     * 保存销售额
     */
    private void saveSaleNumber(DateTimeDto dateTimeDto) {
        try {
            // 查询销售额
            List<SalesDto> t1 = ordersMapper.countSales(dateTimeDto, null);
            //大礼包销量
            List<SalesDto> t2 = ordersMapper.countSales(dateTimeDto, DataToolsConstant.GIFT_PACKAGE_TYPE);

            insertForm(dateTimeDto.getEndTime(), !CollectionUtils.isEmpty(t1) && t1.size() > 1 ? t1.get(1).getSalesNum() : 0,
                    !CollectionUtils.isEmpty(t1) && t1.size() > 0 ? t1.get(0).getSalesNum() : 0, DataCountTypeEnum.ITEM_SALE.getType());

            insertForm(dateTimeDto.getEndTime(), !CollectionUtils.isEmpty(t2) && t2.size() > 1 ? t2.get(1).getSalesNum() : 0,
                    !CollectionUtils.isEmpty(t2) && t2.size() > 0 ? t2.get(0).getSalesNum() : 0, DataCountTypeEnum.ITEM_GIT_SALE.getType());
        } catch (Exception e) {
            log.error("定时统计销售额异常",e);
        }
    }

    /**
     * 平台订单成功支付数
     *
     * @param dateTimeDto
     */
    private void savePlatformOrderNumber(DateTimeDto dateTimeDto) {
        try {
            List<OrderDto> orderDtoList = ordersMapper.countOrderPayNum(dateTimeDto.getStartTime(), dateTimeDto.getEndTime());
            List<OrderDto> relativeOrderDtoList = ordersMapper.countOrderPayNum(dateTimeDto.getRelativeStartTime(), dateTimeDto.getRelativeEndTime());
            if (orderDtoList == null) {
                orderDtoList = new ArrayList<>(0);
            }
            if (relativeOrderDtoList == null) {
                relativeOrderDtoList = new ArrayList<>(0);
            }
            Map<String, Double> curentMap = orderDtoList.stream().collect(Collectors.toMap(OrderDto::getPlatForm, orderDto -> orderDto.getSuccessNum() != null ? orderDto.getSuccessNum().doubleValue() : 0));
            Map<String, Double> relativeMap = relativeOrderDtoList.stream().collect(Collectors.toMap(OrderDto::getPlatForm, orderDto -> orderDto.getSuccessNum() != null ? orderDto.getSuccessNum().doubleValue() : 0));

            insertForm(dateTimeDto.getEndTime(), curentMap.get(PlatFormTypeEnum.APP.getCode()), relativeMap.get(PlatFormTypeEnum.APP.getCode()), DataCountTypeEnum.ITEM_ORDER_APP.getType());
            insertForm(dateTimeDto.getEndTime(), curentMap.get(PlatFormTypeEnum.H5.getCode()), relativeMap.get(PlatFormTypeEnum.H5.getCode()), DataCountTypeEnum.ITEM_ORDER_H5.getType());
            insertForm(dateTimeDto.getEndTime(), curentMap.get(PlatFormTypeEnum.WX_MINI_PROGRAM.getCode()), relativeMap.get(PlatFormTypeEnum.WX_MINI_PROGRAM.getCode()), DataCountTypeEnum.ITEM_ORDER_WXMINI.getType());
        } catch (Exception e) {
            log.error("定时统计下单平台异常",e);
        }
    }

    /**
     * 平台UV
     */
    private void saveUv(DateTimeDto dateTimeDto) {

    }

    /**
     * 订单转化
     *
     * @param dateTimeDto
     */
    private void saveOrderConvertRate(DateTimeDto dateTimeDto) {
        try {
            List<OrderDto> list = ordersMapper.countOrderPayStatusNum(dateTimeDto);
            // 创建数
            insertForm(dateTimeDto.getEndTime(), !CollectionUtils.isEmpty(list) && list.size() > 1 ? list.get(1).getCreateNum().doubleValue() : 0,
                    !CollectionUtils.isEmpty(list) && list.size() > 0 ? list.get(0).getCreateNum().doubleValue() : 0, DataCountTypeEnum.ITEM_ORDER_CREATE.getType());
            // 支付数
            insertForm(dateTimeDto.getEndTime(), !CollectionUtils.isEmpty(list) && list.size() > 1 ? list.get(1).getSuccessNum().doubleValue() : 0,
                    !CollectionUtils.isEmpty(list) && list.size() > 0 ? list.get(0).getSuccessNum().doubleValue() : 0, DataCountTypeEnum.ITEM_ORDER_PAIED.getType());
        } catch (Exception e) {
            log.error("定时统计订单转化异常",e);
        }

    }

    /**
     * 用户
     */
    private void saveOrderUser(Long startTime, Long endTime) {
        try {
            UserDataModel model = toolsService.countUserOrderData(startTime, endTime);
            String countTime = DateTools.date2String(new Date(startTime), DateTools.LONG_DATE_FORMAT);
            insertForm(countTime, model.getVipNum() != null ? model.getVipNum().doubleValue() : 0, model.getVipNumRelative() != null ? model.getVipNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_VIP_CREATE.getType());
            insertForm(countTime, model.getStoreNum() != null ? model.getStoreNum().doubleValue() : 0, model.getStoreNumRelative() != null ? model.getStoreNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_STORE_CREATE.getType());
            insertForm(countTime, model.getNewUserOrderNum() != null ? model.getNewUserOrderNum().doubleValue() : 0, model.getNewUserOrderNumRelative() != null ? model.getNewUserOrderNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_ORDER_NEW.getType());
            insertForm(countTime, model.getOldUserOrderNum() != null ? model.getOldUserOrderNum().doubleValue() : 0, model.getOldUserOrderNumRelative() != null ? model.getOldUserOrderNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_ORDER_OLD.getType());
        } catch (Exception e) {
            log.error("定时统计新老用户订单数据异常",e);
        }
    }


    public void insertForm(String countTime, Double num, Double relativeNum, Integer type) {
        DataTimerReportForm dataTimerReportForm = new DataTimerReportForm();
        dataTimerReportForm.setCountTime(DateTools.string2Date(countTime, DateTools.LONG_DATE_FORMAT));
        dataTimerReportForm.setType(type);
        dataTimerReportForm.setNumber(num);
        dataTimerReportForm.setRelativeNumber(relativeNum);
        countMapper.insertSelective(dataTimerReportForm);
    }

}
