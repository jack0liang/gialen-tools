package com.gialen.tools.job;

import com.gialen.common.utils.DateTools;
import com.gialen.tools.common.constant.DataToolsConstant;
import com.gialen.tools.common.enums.DataCountTypeEnum;
import com.gialen.tools.common.enums.PlatFormTypeEnum;
import com.gialen.tools.dao.dto.DateTimeDto;
import com.gialen.tools.dao.dto.OrderDto;
import com.gialen.tools.dao.dto.SalesDto;
import com.gialen.tools.dao.entity.tools.TbDatacountRelative;
import com.gialen.tools.dao.entity.tools.TbDatacountRelativeExample;
import com.gialen.tools.dao.repository.customer.extend.UserExtendMapper;
import com.gialen.tools.dao.repository.order.extend.OrdersExtendMapper;
import com.gialen.tools.dao.repository.point.GdataPointMapper;
import com.gialen.tools.dao.repository.point.UvStatDayMapper;
import com.gialen.tools.dao.repository.tools.extend.TbDatacountRelativeExtendMapper;
import com.gialen.tools.dao.util.DateTimeDtoBuilder;
import com.gialen.tools.service.DataToolsService;
import com.gialen.tools.service.model.UserDataModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    private TbDatacountRelativeExtendMapper countMapper;

    @Autowired
    private UvStatDayMapper uvStatDayMapper;

    private Long startTemp;

//    @Scheduled(cron = "0 0/1 * * * ?")
    public void queryJob() {

        try {
            /**
             * 统计时间
             */
            log.info("定时器开始执行");
            String startTime = DateTools.date2String(new Date(System.currentTimeMillis()), "yyyy-MM-dd");
            long start = DateTools.string2Date(startTime + " 00:00:00", DateTools.LONG_DATE_FORMAT).getTime();
            long end = DateTools.string2Date(startTime + " 23:59:59", DateTools.LONG_DATE_FORMAT).getTime();

            DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(start, end);
            saveSaleNumber(dateTimeDto);
            savePlatformOrderNumber(dateTimeDto);
            saveOrderConvertRate(dateTimeDto);
//            saveOrderUser(start, end);

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
            List<SalesDto> t1 = ordersMapper.countSalesV1(dateTimeDto, null);
            List<SalesDto> curSale = t1.stream().filter(salesDto -> Integer.parseInt(salesDto.getCountTime()) >= Integer.parseInt(dateTimeDto.getCountStartTime())).collect(Collectors.toList());
            List<SalesDto> relativeSale = t1.stream().filter(salesDto -> Integer.parseInt(salesDto.getCountTime()) < Integer.parseInt(dateTimeDto.getCountStartTime())).collect(Collectors.toList());

            //大礼包销量
            List<SalesDto> t2 = ordersMapper.countSalesV1(dateTimeDto, DataToolsConstant.GIFT_PACKAGE_TYPE);
            List<SalesDto> curGifSale = t2.stream().filter(salesDto -> Integer.parseInt(salesDto.getCountTime()) >= Integer.parseInt(dateTimeDto.getCountStartTime())).collect(Collectors.toList());
            List<SalesDto> relativeGifSale = t2.stream().filter(salesDto -> Integer.parseInt(salesDto.getCountTime()) < Integer.parseInt(dateTimeDto.getCountStartTime())).collect(Collectors.toList());

            // 初始化24小时的数据
            for (int i = 0; i < 24; i++) {
                String countTime = dateTimeDto.getFormatStartDay() + String.format("%02d", i);
                insertForm(countTime, null, null, DataCountTypeEnum.ITEM_SALE.getType());
                insertForm(countTime, null, null, DataCountTypeEnum.ITEM_GIT_SALE.getType());
            }

            insertSaleOrderForm(curSale,relativeSale,DataCountTypeEnum.ITEM_SALE);
            insertSaleOrderForm(curGifSale,relativeGifSale,DataCountTypeEnum.ITEM_GIT_SALE);
        } catch (Exception e) {
            log.error("定时统计销售额异常", e);
        }
    }

    private void insertSaleOrderForm(List<SalesDto> salesDtos,List<SalesDto> salesRelativeDtos , DataCountTypeEnum countTypeEnum) {
        Map<String,SalesDto> orderDtoRelativeMap = salesRelativeDtos.stream().collect(Collectors.toMap(SalesDto::getCountTime, Function.identity()));
        for (int i = 0; i < salesDtos.size(); i++) {
            SalesDto salesDto = salesDtos.get(i);
            SalesDto relativeDto = orderDtoRelativeMap.get(String.valueOf(Integer.parseInt(salesDto.getCountTime()) - 100));
            insertForm(salesDto.getCountTime(), (salesDto.getSalesNum() == null ? 0 : salesDto.getSalesNum()),
                     (relativeDto == null || relativeDto.getSalesNum() == null ? 0 : relativeDto.getSalesNum()), countTypeEnum.getType());
        }
    }

    private void insertOrderPlatformForm(List<OrderDto> orderDtos, List<OrderDto> orderRelativeDtos, DataCountTypeEnum countTypeEnum) {
        Map<String,OrderDto> orderDtoRelativeMap = orderRelativeDtos.stream().collect(Collectors.toMap(OrderDto::getCountTime, Function.identity()));
        for (int i = 0; i < orderDtos.size(); i++) {
            OrderDto orderDto = orderDtos.get(i);
            OrderDto relativeDto = orderDtoRelativeMap.get(String.valueOf(Integer.parseInt(orderDto.getCountTime()) - 100));
            insertForm(orderDto.getCountTime(), (double) (orderDto.getSuccessNum() == null ? 0 : orderDto.getSuccessNum()),
                    (double) (relativeDto == null || relativeDto.getSuccessNum() == null ? 0 : relativeDto.getSuccessNum()), countTypeEnum.getType());
        }
    }

    /**
     * 平台订单成功支付数
     *
     * @param dateTimeDto
     */
    private void savePlatformOrderNumber(DateTimeDto dateTimeDto) {
        try {
            List<OrderDto> orderDtoList = ordersMapper.countOrderPayNumV1(dateTimeDto.getStartTime(), dateTimeDto.getEndTime());
            List<OrderDto> relativeOrderDtoList = ordersMapper.countOrderPayNumV1(dateTimeDto.getRelativeStartTime(), dateTimeDto.getRelativeEndTime());

            Map<String, List<OrderDto>> orderDtoMap = orderDtoList.stream().collect(Collectors.groupingBy(OrderDto::getPlatForm));
            Map<String, List<OrderDto>> relativeOrderDtoMap = relativeOrderDtoList.stream().collect(Collectors.groupingBy(OrderDto::getPlatForm));

            for (int i = 0; i < 24; i++) {
                String countTime = dateTimeDto.getFormatStartDay() + String.format("%02d", i);
                insertForm(countTime, null, null, DataCountTypeEnum.ITEM_ORDER_APP.getType());
                insertForm(countTime, null, null, DataCountTypeEnum.ITEM_ORDER_H5.getType());
                insertForm(countTime, null, null, DataCountTypeEnum.ITEM_ORDER_WXMINI.getType());
            }

            List<OrderDto> appOrderSales = orderDtoMap.get(PlatFormTypeEnum.APP.getCode());
            List<OrderDto> appOrderSaleRelatives = relativeOrderDtoMap.get(PlatFormTypeEnum.APP.getCode());
            insertOrderPlatformForm(appOrderSales,appOrderSaleRelatives,DataCountTypeEnum.ITEM_ORDER_APP);

            List<OrderDto> h5OrderSales = orderDtoMap.get(PlatFormTypeEnum.H5.getCode());
            List<OrderDto> h5OrderSaleRelatives = relativeOrderDtoMap.get(PlatFormTypeEnum.H5.getCode());
            insertOrderPlatformForm(h5OrderSales,h5OrderSaleRelatives,DataCountTypeEnum.ITEM_ORDER_H5);

            List<OrderDto> miniOrderSales = orderDtoMap.get(PlatFormTypeEnum.WX_MINI_PROGRAM.getCode());
            List<OrderDto> miniOrderSaleRelatives = relativeOrderDtoMap.get(PlatFormTypeEnum.WX_MINI_PROGRAM.getCode());
            insertOrderPlatformForm(miniOrderSales,miniOrderSaleRelatives,DataCountTypeEnum.ITEM_ORDER_WXMINI);

        } catch (Exception e) {
            log.error("定时统计下单平台异常", e);
        }
    }


    /**
     * 订单转化
     *
     * @param dateTimeDto
     */
    private void saveOrderConvertRate(DateTimeDto dateTimeDto) {
        try {
            List<OrderDto> t1 = ordersMapper.countOrderPayStatusNum(dateTimeDto);
            List<OrderDto> curSale = t1.stream().filter(orderDto -> Integer.parseInt(orderDto.getCountTime()) >= Integer.parseInt(dateTimeDto.getCountStartTime())).collect(Collectors.toList());
            Map<String,OrderDto> relativeSaleMap = t1.stream().filter(orderDto -> Integer.parseInt(orderDto.getCountTime()) < Integer.parseInt(dateTimeDto.getCountStartTime())).collect(Collectors.toMap(OrderDto::getCountTime, Function.identity()));

            // 初始化24小时的数据
            for (int i = 0; i < 24; i++) {
                String countTime = dateTimeDto.getFormatStartDay() + String.format("%02d", i);
                insertForm(countTime, null, null, DataCountTypeEnum.ITEM_ORDER_CREATE.getType());
                insertForm(countTime, null, null, DataCountTypeEnum.ITEM_ORDER_PAIED.getType());
            }

            for (int i = 0; i < curSale.size(); i++) {
                OrderDto orderDto = curSale.get(i);
                OrderDto relativeDto = relativeSaleMap.get(String.valueOf(Integer.parseInt(orderDto.getCountTime()) - 100));
                // 创建数
                insertForm(orderDto.getCountTime(), (double) (orderDto.getCreateNum() == null ? 0 : orderDto.getCreateNum()),(double) (relativeDto == null || relativeDto.getCreateNum() == null ? 0 : relativeDto.getCreateNum()), DataCountTypeEnum.ITEM_ORDER_CREATE.getType());
                // 支付数
                insertForm(orderDto.getCountTime(), (double) (orderDto.getSuccessNum() == null ? 0 : orderDto.getSuccessNum()),(double) (relativeDto == null || relativeDto.getSuccessNum() == null ? 0 : relativeDto.getSuccessNum()), DataCountTypeEnum.ITEM_ORDER_PAIED.getType());
            }
        } catch (Exception e) {
            log.error("定时统计订单转化异常", e);
        }
    }

    /**
     * 用户
     */
    private void saveOrderUser(Long startTime, Long endTime) {
        try {
            UserDataModel model = toolsService.countUserOrderData(startTime, endTime);
            String countTime = DateTools.date2String(new Date(startTime), "yyyyMMddHH");
            insertForm(countTime, model.getVipNum() != null ? model.getVipNum().doubleValue() : 0, model.getVipNumRelative() != null ? model.getVipNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_VIP_CREATE.getType());
            insertForm(countTime, model.getStoreNum() != null ? model.getStoreNum().doubleValue() : 0, model.getStoreNumRelative() != null ? model.getStoreNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_STORE_CREATE.getType());
            insertForm(countTime, model.getNewUserOrderNum() != null ? model.getNewUserOrderNum().doubleValue() : 0, model.getNewUserOrderNumRelative() != null ? model.getNewUserOrderNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_ORDER_NEW.getType());
            insertForm(countTime, model.getOldUserOrderNum() != null ? model.getOldUserOrderNum().doubleValue() : 0, model.getOldUserOrderNumRelative() != null ? model.getOldUserOrderNumRelative().doubleValue() : 0, DataCountTypeEnum.ITEM_ORDER_OLD.getType());
        } catch (Exception e) {
            log.error("定时统计新老用户订单数据异常", e);
        }
    }


    public void insertForm(String countTime, Double num, Double relativeNum, Integer type) {
        TbDatacountRelativeExample example = new TbDatacountRelativeExample();
        example.createCriteria().andCountTimeEqualTo(countTime).andTypeEqualTo(type);
        TbDatacountRelative dataTimerReportForm = new TbDatacountRelative();
        dataTimerReportForm.setType(type);
        dataTimerReportForm.setUpdateTime(new Date());
        dataTimerReportForm.setNumber(num);
        dataTimerReportForm.setRelativeNumber(relativeNum);
        dataTimerReportForm.setCountTime(countTime);
        if (CollectionUtils.isEmpty(countMapper.selectByExample(example))) {
            countMapper.insertSelective(dataTimerReportForm);
        } else {
            countMapper.updateByExampleSelective(dataTimerReportForm, example);
        }
    }

}
