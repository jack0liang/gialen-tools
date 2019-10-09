package com.gialen.tools.service.impl;

import com.gialen.common.model.GLResponse;
import com.gialen.common.utils.DateTools;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.constant.DataToolsConstant;
import com.gialen.tools.common.enums.DataCountTypeEnum;
import com.gialen.tools.common.enums.DataTypeEnum;
import com.gialen.tools.common.enums.PlatFormTypeEnum;
import com.gialen.tools.common.enums.RelativeDataTypeEnum;
import com.gialen.tools.dao.dto.*;
import com.gialen.tools.dao.entity.customer.UserExample;
import com.gialen.tools.dao.entity.order.Orders;
import com.gialen.tools.dao.entity.order.OrdersExample;
import com.gialen.tools.dao.entity.point.UvStatDay;
import com.gialen.tools.dao.entity.point.UvStatDayExample;
import com.gialen.tools.dao.entity.tools.TbDatacountRelative;
import com.gialen.tools.dao.entity.tools.TbDatacountRelativeExample;
import com.gialen.tools.dao.repository.customer.extend.UserExtendMapper;
import com.gialen.tools.dao.repository.order.extend.OrdersExtendMapper;
import com.gialen.tools.dao.repository.point.GdataPointMapper;
import com.gialen.tools.dao.repository.point.UvStatDayMapper;
import com.gialen.tools.dao.repository.tools.extend.TbDatacountRelativeExtendMapper;
import com.gialen.tools.dao.util.DateTimeDtoBuilder;
import com.gialen.tools.service.DataToolsService;
import com.gialen.tools.service.model.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据看板业务类
 *
 * @author lupeibo
 * @date 2019-05-27
 */
@Slf4j
@Service
public class DataToolsServiceImpl implements DataToolsService {

    @Autowired
    private UserExtendMapper userMapper;

    @Autowired
    private OrdersExtendMapper ordersMapper;

    @Autowired
    private GdataPointMapper gdataPointMapper;

    @Autowired
    private UvStatDayMapper uvStatDayMapper;

    @Autowired
    private TbDatacountRelativeExtendMapper countMapper;

    public static int totalUv = 0;

    public static int totalUvRelative = 0;

    @Override
    public GLResponse getDataList(Long startTime, Long endTime, Byte dataType) {
        List<DataToolsModel> dataList = Lists.newArrayList();
        Map<Integer, List<TbDatacountRelative>> map = getCountData(startTime, endTime);

        if (DataTypeEnum.SALES_DATA.getType() == dataType) {

            if (map == null) {
                dataList.add(getSalesCountData(startTime, endTime));
            } else {
                dataList.add(getSalesCountDataByCache(map));
            }

        } else if (DataTypeEnum.UV_DATA.getType() == dataType) {
            dataList.add(getUv(startTime, endTime));
        } else if (DataTypeEnum.ORDER_DATA.getType() == dataType) {

            if (map == null) {
                dataList.add(getOrderPayData(startTime, endTime));
            } else {
                dataList.add(getOrderPayDataByCache(map));
            }

        } else if (DataTypeEnum.CONVERSION_DATA.getType() == dataType) {

            if (map == null) {
                dataList.add(getOrderConversionData(startTime, endTime));
            } else {
                dataList.add(getOrderConversionDataByCache(map,startTime,endTime));
            }

        } else if (DataTypeEnum.USER_DATA.getType() == dataType) {
            dataList.add(getUserData(startTime, endTime));
        }

        return GLResponse.succ(dataList);
    }


    public Map<Integer, List<TbDatacountRelative>> getCountData(Long startTime, Long endTime) {
        List<String> timerPoint = new ArrayList<>(12);
        int dexHour = (int) ((endTime - startTime) / 1000 / 60 / 60);
        Date s = new Date(startTime);
        for (int i = 0; i < dexHour; i++) {
            timerPoint.add(DateTools.date2String(DateUtils.addHours(s, i), "yyyyMMddHH"));
        }

        TbDatacountRelativeExample example = new TbDatacountRelativeExample();
        example.createCriteria().andCountTimeIn(timerPoint);
        List<TbDatacountRelative> datacountRelatives = countMapper.selectByExample(example);
        Map<Integer, List<TbDatacountRelative>> listMap = datacountRelatives.stream().collect(Collectors.groupingBy(TbDatacountRelative::getType));
        if (listMap.size() != DataCountTypeEnum.values().length
                || !listMap.values().stream().allMatch(tbDatacountRelatives -> tbDatacountRelatives.size() == dexHour)) {
            return null;
        }
        return listMap;
    }

//    public static void main(String[] args) {
//        Date s1 = DateTools.string2Date("2019-09-29 01:30:00", DateTools.LONG_DATE_FORMAT);
//        Date e1 = DateTools.string2Date("2019-09-29 02:40:00", DateTools.LONG_DATE_FORMAT);
//
//        List<String> timepoint = new ArrayList<>(12);
//
//        // 拆成小时
//        int dexHour = (int) ((e1.getTime() - s1.getTime()) / 1000 / 60 / 60);
//        for (int i = 0; i < dexHour; i++) {
//            timepoint.add(DateTools.date2String(DateUtils.addHours(s1, i), "yyyyMMddHH"));
//        }
//        System.out.println(timepoint);
//    }


    @Override
    public UserDataModel countUserOrderData(Long startTime, Long endTime) {
        DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);
        //统计vip,store数量、环比
        UserDataModel userDataModel = calculateUserType(userMapper.countUserType(dateTimeDto), startTime);
        //统计当前新老客订单量
        calculateUserOrder(dateTimeDto, userDataModel, RelativeDataTypeEnum.DATA);
        //统计环比新老客订单量
        calculateUserOrder(dateTimeDto, userDataModel, RelativeDataTypeEnum.RELATIVE_DATA);
        return userDataModel;
    }

    /**
     * 获取用户统计数据
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public DataToolsModel getUserData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();
        DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);

        //统计vip,store数量、环比
        UserDataModel userDataModel = calculateUserType(userMapper.countUserType(dateTimeDto), startTime);
        //统计当前新老客订单量
        calculateUserOrder(dateTimeDto, userDataModel, RelativeDataTypeEnum.DATA);
        //统计环比新老客订单量
        calculateUserOrder(dateTimeDto, userDataModel, RelativeDataTypeEnum.RELATIVE_DATA);
        //统计新客订单环比
        userDataModel.setNewUserOrderNumRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(userDataModel.getNewUserOrderNum() + ""),
                NumberUtils.createDouble(userDataModel.getNewUserOrderNumRelative() + ""), 4));
        //统计老客订单环比
        userDataModel.setOldUserOrderNumRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(userDataModel.getOldUserOrderNum() + ""),
                NumberUtils.createDouble(userDataModel.getOldUserOrderNumRelative() + ""), 4));

        //封装结果
        dataToolsModel.setTitle(DataToolsConstant.TITLE_USER);
        List<ItemModel> itemList = Lists.newArrayList();
        ItemModel storeNumItem = createItem(DataToolsConstant.LABEL_STORE_NUM,
                String.valueOf(userDataModel.getStoreNum()), userDataModel.getStoreNumRelativeRatio());
        ItemModel vipNumItem = createItem(DataToolsConstant.LABEL_VIP_NUM,
                String.valueOf(userDataModel.getVipNum()), userDataModel.getVipNumRelativeRatio());
        ItemModel newUserOrderNumItem = createItem(DataToolsConstant.LABEL_NEW_USER_ORDER_NUM,
                String.valueOf(userDataModel.getNewUserOrderNum()), userDataModel.getNewUserOrderNumRelativeRatio());
        ItemModel oldUserOrderNumItem = createItem(DataToolsConstant.LABEL_OLD_USER_ORDER_NUM,
                String.valueOf(userDataModel.getOldUserOrderNum()), userDataModel.getOldUserOrderNumRelativeRatio());

        itemList.add(storeNumItem);
        itemList.add(vipNumItem);
        itemList.add(newUserOrderNumItem);
        itemList.add(oldUserOrderNumItem);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 统计小程序uv
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private DataToolsModel getUv(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();

        UvDataModel uvDataModel = getUvData(startTime, endTime);

        dataToolsModel.setTitle(DataToolsConstant.TITLE_UV);
        List<ItemModel> itemList = Lists.newArrayList();

        ItemModel miniProgramItem = createItem(DataToolsConstant.LABEL_MINI_PROGRAM,
                String.valueOf(uvDataModel.getMiniProgramUv()), uvDataModel.getMiniProgramUvRelativeRatio());
        ItemModel appItem = createItem(DataToolsConstant.LABEL_APP,
                String.valueOf(uvDataModel.getAppUv()), uvDataModel.getAppUvRelativeRatio());
        ItemModel h5Item = createItem(DataToolsConstant.LABEL_H5,
                String.valueOf(uvDataModel.getH5Uv()), uvDataModel.getH5UvRelativeRatio());

        itemList.add(miniProgramItem);
        itemList.add(appItem);
        itemList.add(h5Item);
        dataToolsModel.setItems(itemList);
        log.info("miniProgramItem = {}, appItem = {}, h5Item = {}", miniProgramItem, appItem, h5Item);
        return dataToolsModel;
    }

    private UvDataModel getUvData(Long startTime, Long endTime) {
        //当前
        String startTimeStr = DateFormatUtils.format(startTime, "yyyyMMdd");
        UvStatDay uvStatDay = selectUvStatDay(startTimeStr);
        UvDto uv = new UvDto();
        uv.setMiniProgramUv(uvStatDay.getUvMiniapp());
        uv.setAppUv(uvStatDay.getUvApp());
        uv.setH5Uv(uvStatDay.getUvH5());

        //环比
        String relativeTimer = DateFormatUtils.format(DateUtils.addDays(new Date(startTime), -1), "yyyyMMdd");
        UvStatDay uvStatDayRelative = selectUvStatDay(relativeTimer);
        UvDto relativeUv = new UvDto();
        relativeUv.setMiniProgramUv(uvStatDayRelative.getUvMiniapp());
        relativeUv.setAppUv(uvStatDayRelative.getUvApp());
        relativeUv.setH5Uv(uvStatDayRelative.getUvH5());
        return calculateUv(uv, relativeUv);
    }

    private DataToolsModel getOrderPayDataByCache(Map<Integer, List<TbDatacountRelative>> map) {
        DataToolsModel dataToolsModel = new DataToolsModel();
        List<TbDatacountRelative> wxminiOrders = map.get(DataCountTypeEnum.ITEM_ORDER_WXMINI.getType());
        int wxnimiNumber = wxminiOrders.stream().mapToInt(value -> value.getNumber().intValue()).sum();
        int wxnimiNumberRelative = wxminiOrders.stream().mapToInt(value -> value.getRelativeNumber().intValue()).sum();
        List<TbDatacountRelative> h5Orders = map.get(DataCountTypeEnum.ITEM_ORDER_H5.getType());
        int h5Number = h5Orders.stream().mapToInt(value -> value.getNumber().intValue()).sum();
        int h5NumberRelative = h5Orders.stream().mapToInt(value -> value.getRelativeNumber().intValue()).sum();
        List<TbDatacountRelative> appOrders = map.get(DataCountTypeEnum.ITEM_ORDER_APP.getType());
        int appNumber = appOrders.stream().mapToInt(value -> value.getNumber().intValue()).sum();
        int appNumberRelative = appOrders.stream().mapToInt(value -> value.getRelativeNumber().intValue()).sum();

        dataToolsModel.setTitle(DataToolsConstant.TITLE_ORDER_PLATFORM_PAY);
        List<ItemModel> itemList = Lists.newArrayList();
        ItemModel miniProgramItem = createItem(DataToolsConstant.LABEL_MINI_PROGRAM,
                String.valueOf(wxnimiNumber), calculateRelativeRatio(NumberUtils.createDouble(wxnimiNumber + ""), NumberUtils.createDouble(wxnimiNumberRelative + ""), 4));
        ItemModel appItem = createItem(DataToolsConstant.LABEL_APP,
                String.valueOf(appNumber), calculateRelativeRatio(NumberUtils.createDouble(appNumber + ""), NumberUtils.createDouble(appNumberRelative + ""), 4));
        ItemModel h5Item = createItem(DataToolsConstant.LABEL_H5,
                String.valueOf(h5Number), calculateRelativeRatio(NumberUtils.createDouble(h5Number + ""), NumberUtils.createDouble(h5NumberRelative + ""), 4));

        itemList.add(miniProgramItem);
        itemList.add(appItem);
        itemList.add(h5Item);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 按平台来源统计订单成功数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private DataToolsModel getOrderPayData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();
        DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);

        List<OrderDto> orderDtoList = ordersMapper.countOrderPayNum(dateTimeDto.getStartTime(), dateTimeDto.getEndTime());
        List<OrderDto> relativeOrderDtoList = ordersMapper.countOrderPayNum(dateTimeDto.getRelativeStartTime(), dateTimeDto.getRelativeEndTime());
        PaiedOrderDataModel paiedOrderDataModel = calculatePaiedOrder(orderDtoList, relativeOrderDtoList);

        dataToolsModel.setTitle(DataToolsConstant.TITLE_ORDER_PLATFORM_PAY);
        List<ItemModel> itemList = Lists.newArrayList();
        ItemModel miniProgramItem = createItem(DataToolsConstant.LABEL_MINI_PROGRAM,
                String.valueOf(paiedOrderDataModel.getMiniProgramOrder()), paiedOrderDataModel.getMiniProgramOrderRelativeRatio());
        ItemModel appItem = createItem(DataToolsConstant.LABEL_APP,
                String.valueOf(paiedOrderDataModel.getAppOrder()), paiedOrderDataModel.getAppOrderRelativeRatio());
        ItemModel h5Item = createItem(DataToolsConstant.LABEL_H5,
                String.valueOf(paiedOrderDataModel.getH5Order()), paiedOrderDataModel.getH5OrderRelativeRatio());

        itemList.add(miniProgramItem);
        itemList.add(appItem);
        itemList.add(h5Item);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 统计销售量
     *
     * @return
     */
    public DataToolsModel getSalesCountData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();

        //总销量
        List<SalesDto> totalSalesNumList = ordersMapper.countSales(
                DateTimeDtoBuilder.createDateTimeDto(startTime, endTime), null);
        //大礼包销量
        List<SalesDto> totalSalesNumByGiftPackageList = ordersMapper.countSales(
                DateTimeDtoBuilder.createDateTimeDto(startTime, endTime), DataToolsConstant.GIFT_PACKAGE_TYPE);
        SalesDataModel salesDataModel = calculateSales(totalSalesNumList, totalSalesNumByGiftPackageList, startTime);

        dataToolsModel.setTitle(DataToolsConstant.TITLE_SALES_COUNT);
        List<ItemModel> itemList = Lists.newArrayList();

        ItemModel totalSalesItem = createItem(DataToolsConstant.LABEL_SALES_TOTAL,
                String.valueOf(DecimalCalculate.round(salesDataModel.getTotalSales(), 2)), salesDataModel.getTotalSalseRelativeRatio());
        ItemModel giftPackageSalesItem = createItem(DataToolsConstant.LABEL_SALES_GIFT_PACKAGE,
                String.valueOf(DecimalCalculate.round(salesDataModel.getGiftPackageSales(), 2)), salesDataModel.getGiftPackageSalesRelativeRatio());
        ItemModel usualSalesItem = createItem(DataToolsConstant.LABEL_SALES_USUAL,
                String.valueOf(DecimalCalculate.round(salesDataModel.getUsualSales(), 2)), salesDataModel.getUsualSalesRelativeRatio());

        itemList.add(totalSalesItem);
        itemList.add(giftPackageSalesItem);
        itemList.add(usualSalesItem);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 从统计表中计算销售数据
     *
     * @param cacheMap
     * @return
     */
    public DataToolsModel getSalesCountDataByCache(Map<Integer, List<TbDatacountRelative>> cacheMap) {
        /*
         * 清算总销售额
         */
        List<TbDatacountRelative> sales = cacheMap.get(DataCountTypeEnum.ITEM_SALE.getType());
        double totalSale = sales.stream().mapToDouble(TbDatacountRelative::getNumber).sum();
        double totalSaleRelative = sales.stream().mapToDouble(TbDatacountRelative::getRelativeNumber).sum();
        /*
         * 大礼包销售额
         */
        List<TbDatacountRelative> giftSales = cacheMap.get(DataCountTypeEnum.ITEM_GIT_SALE.getType());
        double gitSale = giftSales.stream().mapToDouble(TbDatacountRelative::getNumber).sum();
        double gitSaleRelative = giftSales.stream().mapToDouble(TbDatacountRelative::getRelativeNumber).sum();
        /*
         * 平销
         */
        double usualSales = totalSale - totalSaleRelative;
        double usualSalesRelative = gitSale - gitSaleRelative;

        DataToolsModel dataToolsModel = new DataToolsModel();
        dataToolsModel.setTitle(DataToolsConstant.TITLE_SALES_COUNT);
        List<ItemModel> itemList = Lists.newArrayList();
        ItemModel totalSalesItem = createItem(DataToolsConstant.LABEL_SALES_TOTAL,
                String.valueOf(DecimalCalculate.round(totalSale, 2)), calculateRelativeRatio(totalSale, totalSaleRelative, 4));
        ItemModel giftPackageSalesItem = createItem(DataToolsConstant.LABEL_SALES_GIFT_PACKAGE,
                String.valueOf(DecimalCalculate.round(gitSale, 2)), calculateRelativeRatio(gitSale, gitSaleRelative, 4));
        ItemModel usualSalesItem = createItem(DataToolsConstant.LABEL_SALES_USUAL,
                String.valueOf(DecimalCalculate.round(usualSales, 2)), calculateRelativeRatio(usualSales, usualSalesRelative, 4));

        itemList.add(totalSalesItem);
        itemList.add(giftPackageSalesItem);
        itemList.add(usualSalesItem);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }


    /**
     * 计算销售看板数据
     *
     * @param totalSalesList       总销售数据
     * @param giftPackageSalesList 大礼包销售数据
     * @return
     */
    private SalesDataModel calculateSales(List<SalesDto> totalSalesList, List<SalesDto> giftPackageSalesList, Long startTime) {
        SalesDataModel model = new SalesDataModel();
        Double totalSales = NumberUtils.DOUBLE_ZERO;
        Double totalSalseRelative = NumberUtils.DOUBLE_ZERO;
        Double giftPackageSales = NumberUtils.DOUBLE_ZERO;
        Double giftPackageSalseRelative = NumberUtils.DOUBLE_ZERO;

        String startTimeStr = DateFormatUtils.format(startTime, "yyyyMMdd");
        //计算总销售
        if (CollectionUtils.isNotEmpty(totalSalesList)) {
            for (SalesDto salesDto : totalSalesList) {
                if (startTimeStr.equals(salesDto.getCountTime())) { //当前数据
                    totalSales = salesDto.getSalesNum() != null ? salesDto.getSalesNum() : NumberUtils.DOUBLE_ZERO;
                } else { //环比数据
                    totalSalseRelative = salesDto.getSalesNum() != null ? salesDto.getSalesNum() : NumberUtils.DOUBLE_ZERO;
                }
            }
        }
        model.setTotalSales(totalSales);
        model.setTotalSalseRelativeRatio(calculateRelativeRatio(totalSales, totalSalseRelative, 4));

        //计算大礼包销售
        if (CollectionUtils.isNotEmpty(giftPackageSalesList)) {
            for (SalesDto salesDto : giftPackageSalesList) {
                if (startTimeStr.equals(salesDto.getCountTime())) { //当前数据
                    giftPackageSales = salesDto.getSalesNum() != null ? salesDto.getSalesNum() : NumberUtils.DOUBLE_ZERO;
                } else { //环比数据
                    giftPackageSalseRelative = salesDto.getSalesNum() != null ? salesDto.getSalesNum() : NumberUtils.DOUBLE_ZERO;
                }
            }
        }
        model.setGiftPackageSales(giftPackageSales);
        model.setGiftPackageSalesRelativeRatio(calculateRelativeRatio(giftPackageSales, giftPackageSalseRelative, 4));

        //计算平常销售
        Double usualSales = totalSales - giftPackageSales;
        Double usualSalesRelative = totalSalseRelative - giftPackageSalseRelative;
        model.setUsualSales(usualSales);
        model.setUsualSalesRelativeRatio(calculateRelativeRatio(usualSales, usualSalesRelative, 4));
        return model;
    }

    /**
     * 计算用户类型看板数据
     *
     * @param userTypeDataList
     * @return
     */
    private UserDataModel calculateUserType(List<UserDataDto> userTypeDataList, Long startTime) {
        UserDataModel model = new UserDataModel();
        Integer vipNum = NumberUtils.INTEGER_ZERO;
        Integer vipNumRelative = NumberUtils.INTEGER_ZERO;
        Integer storeNum = NumberUtils.INTEGER_ZERO;
        Integer storeNumRelative = NumberUtils.INTEGER_ZERO;
        String startTimeStr = DateFormatUtils.format(startTime, "yyyyMMdd");

        if (CollectionUtils.isNotEmpty(userTypeDataList)) {
            for (UserDataDto userDataDto : userTypeDataList) {
                if (startTimeStr.equals(userDataDto.getCountTime())) { //当前数据
                    vipNum = userDataDto.getVipNum() != null ? userDataDto.getVipNum() : NumberUtils.INTEGER_ZERO;
                    storeNum = userDataDto.getStoreNum() != null ? userDataDto.getStoreNum() : NumberUtils.INTEGER_ZERO;
                } else { //环比数据
                    vipNumRelative = userDataDto.getVipNum() != null ? userDataDto.getVipNum() : NumberUtils.INTEGER_ZERO;
                    storeNumRelative = userDataDto.getStoreNum() != null ? userDataDto.getStoreNum() : NumberUtils.INTEGER_ZERO;
                }
            }
        }
        model.setVipNum(vipNum);
        model.setStoreNum(storeNum);
        model.setVipNumRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(vipNum + ""), NumberUtils.createDouble(vipNumRelative + ""), 4));
        model.setStoreNumRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(storeNum + ""), NumberUtils.createDouble(storeNumRelative + ""), 4));
        return model;
    }

    /**
     * 计算新老客订单看板数据
     *
     * @param userDataModel
     * @param dataTypeEnum
     * @return
     */
    private void calculateUserOrder(DateTimeDto dateTimeDto, UserDataModel userDataModel, RelativeDataTypeEnum dataTypeEnum) {

        Integer newUserOrderNum = 0;
        Integer oldUserOrderNum = 0;
        Date start = DateTools.string2Date(RelativeDataTypeEnum.DATA.equals(dataTypeEnum) ? dateTimeDto.getStartTime() : dateTimeDto.getRelativeStartTime(), DateTools.LONG_DATE_FORMAT);
        Date end = DateTools.string2Date(RelativeDataTypeEnum.DATA.equals(dataTypeEnum) ? dateTimeDto.getEndTime() : dateTimeDto.getRelativeEndTime(), DateTools.LONG_DATE_FORMAT);
        OrdersExample ordersExample = new OrdersExample();
        ordersExample.createCriteria().andPayStatusEqualTo((short) 1).andIsParentEqualTo(true).andCreateTimeBetween(start, end);
        List<Orders> orders = ordersMapper.selectByExample(ordersExample);
        List<Long> userIds = orders.stream().map(Orders::getUserId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userIds)) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdIn(userIds).andCreateTimeBetween(start, end);
            newUserOrderNum = (int) userMapper.countByExample(userExample);
            oldUserOrderNum = userIds.size() - newUserOrderNum;
        }

        if (RelativeDataTypeEnum.DATA.equals(dataTypeEnum)) {
            userDataModel.setNewUserOrderNum(newUserOrderNum);
            userDataModel.setOldUserOrderNum(oldUserOrderNum);
        } else if (RelativeDataTypeEnum.RELATIVE_DATA.equals(dataTypeEnum)) {
            userDataModel.setNewUserOrderNumRelative(newUserOrderNum);
            userDataModel.setOldUserOrderNumRelative(oldUserOrderNum);
        }
    }

    /**
     * 计算UV看板数据
     *
     * @param uv
     * @param relativeUv
     * @return
     */
    private UvDataModel calculateUv(UvDto uv, UvDto relativeUv) {
        UvDataModel model = new UvDataModel();

        //计算uv
        Integer miniProgramUv = getUvData(uv.getMiniProgramUv());
        Integer appUv = getUvData(uv.getAppUv());
        Integer h5Uv = getUvData(uv.getH5Uv());
        totalUv = miniProgramUv + appUv + h5Uv;

        Integer miniProgramUvRelative = getUvData(relativeUv.getMiniProgramUv());
        Integer appUvRelative = getUvData(relativeUv.getAppUv());
        Integer h5UvRelative = getUvData(relativeUv.getH5Uv());
        totalUvRelative = miniProgramUvRelative + appUvRelative + h5UvRelative;

        model.setMiniProgramUv(miniProgramUv);
        model.setMiniProgramUvRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(miniProgramUv + ""), NumberUtils.createDouble(miniProgramUvRelative + ""), 4));
        model.setAppUv(appUv);
        model.setAppUvRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(appUv + ""), NumberUtils.createDouble(appUvRelative + ""), 4));
        model.setH5Uv(h5Uv);
        model.setH5UvRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(h5Uv + ""), NumberUtils.createDouble(h5UvRelative + ""), 4));
        return model;
    }

    private Integer getUvData(Integer uvData) {
        return uvData != null ? uvData : NumberUtils.INTEGER_ZERO;
    }

    /**
     * 计算订单看板数据
     *
     * @param orderDtoList
     * @param relativeOrderDtoList
     * @return
     */
    private PaiedOrderDataModel calculatePaiedOrder(List<OrderDto> orderDtoList, List<OrderDto> relativeOrderDtoList) {
        PaiedOrderDataModel model = new PaiedOrderDataModel();
        model = calculateOrderDtoList(orderDtoList, model, RelativeDataTypeEnum.DATA);
        model = calculateOrderDtoList(relativeOrderDtoList, model, RelativeDataTypeEnum.RELATIVE_DATA);

        model.setMiniProgramOrderRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(model.getMiniProgramOrder() + ""),
                NumberUtils.createDouble(model.getMiniProgramOrderRelative() + ""), 4));
        model.setH5OrderRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(model.getH5Order() + ""),
                NumberUtils.createDouble(model.getH5OrderRelative() + ""), 4));
        model.setAppOrderRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(model.getAppOrder() + ""),
                NumberUtils.createDouble(model.getAppOrderRelative() + ""), 4));
        return model;
    }

    private PaiedOrderDataModel calculateOrderDtoList(List<OrderDto> orderDtoList, PaiedOrderDataModel model, RelativeDataTypeEnum dataTypeEnum) {
        int miniProgramOrder = 0;
        int h5Order = 0;
        int appOrder = 0;
        if (CollectionUtils.isNotEmpty(orderDtoList)) {
            for (OrderDto orderDto : orderDtoList) {
                String platForm = orderDto.getPlatForm();
                if (PlatFormTypeEnum.APP.getCode().equals(platForm)) {
                    appOrder += orderDto.getSuccessNum();
                } else if (PlatFormTypeEnum.H5.getCode().equals(platForm)) {
                    h5Order += orderDto.getSuccessNum();
                } else if (PlatFormTypeEnum.WX_MINI_PROGRAM.getCode().equals(platForm)) {
                    miniProgramOrder += orderDto.getSuccessNum();
                }
            }
        }
        if (dataTypeEnum.equals(RelativeDataTypeEnum.DATA)) {
            model.setMiniProgramOrder(miniProgramOrder);
            model.setH5Order(h5Order);
            model.setAppOrder(appOrder);
        } else if (dataTypeEnum.equals(RelativeDataTypeEnum.RELATIVE_DATA)) {
            model.setMiniProgramOrderRelative(miniProgramOrder);
            model.setH5OrderRelative(h5Order);
            model.setAppOrderRelative(appOrder);
        }
        return model;
    }

    /**
     * 计算环比
     *
     * @return
     */
    private double calculateRelativeRatio(double data, double relativeData, int scale) {
        double relativeRatio;
        if (relativeData <= NumberUtils.DOUBLE_ZERO) {
            relativeRatio = data;
        } else {
            relativeRatio = DecimalCalculate.div(data - relativeData, relativeData, scale);
        }
        return relativeRatio;
    }

    /**
     * 获取订单转化数据
     *
     * @return
     */
    private DataToolsModel getOrderConversionData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();
        DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);

        ConversionDataModel conversionDataModel = calculateOrderConversion(ordersMapper.countOrderNum(dateTimeDto), startTime, endTime);
        dataToolsModel.setTitle(DataToolsConstant.TITLE_ORDER_CONVERSION);
        List<ItemModel> itemList = Lists.newArrayList();
        ItemModel createNumItem = createItem(DataToolsConstant.LABEL_ORDER_CREATE_NUM,
                conversionDataModel.getOrderCreated() + "", conversionDataModel.getOrderCreatedRelativeRatio());
        ItemModel successNumItem = createItem(DataToolsConstant.LABEL_ORDER_SUCCESS_NUM,
                conversionDataModel.getOrderSuccess() + "", conversionDataModel.getOrderSuccessRelativeRatio());
        ItemModel successRateItem = createItem(DataToolsConstant.LABEL_ORDER_SUCCESS_RATE,
                conversionDataModel.getOrderSuccessRate() + "", conversionDataModel.getOrderSuccessRateRelativeRatio());
        ItemModel conversionRateItem = createItem(DataToolsConstant.CONVERSION_RATE,
                conversionDataModel.getConversionRate() + "", conversionDataModel.getConversionRateRelativeRatio());

        itemList.add(createNumItem);
        itemList.add(successNumItem);
        itemList.add(successRateItem);
        itemList.add(conversionRateItem);

        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    private DataToolsModel getOrderConversionDataByCache(Map<Integer, List<TbDatacountRelative>> map,Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();

        List<TbDatacountRelative> orderCreates = map.get(DataCountTypeEnum.ITEM_ORDER_CREATE.getType());
        double orderCreaterNum = orderCreates.stream().mapToDouble(TbDatacountRelative::getNumber).sum();
        double orderCreaterRelativeNum = orderCreates.stream().mapToDouble(TbDatacountRelative::getRelativeNumber).sum();

        List<TbDatacountRelative> orderPaieds = map.get(DataCountTypeEnum.ITEM_ORDER_PAIED.getType());
        double orderPaiedNum = orderPaieds.stream().mapToDouble(TbDatacountRelative::getNumber).sum();
        double orderPaiedRelativeNum = orderPaieds.stream().mapToDouble(TbDatacountRelative::getRelativeNumber).sum();

        double successRate = DecimalCalculate.div(orderPaiedNum, orderCreaterNum, 4);
        double successRateRelative = DecimalCalculate.div(orderPaiedNum, orderCreaterRelativeNum, 4);

        if (totalUv <= 0 && totalUvRelative <= 0) {
            getUvData(startTime, endTime);
            totalUv = totalUv <= 0 ? 1 : totalUv;
            totalUvRelative = totalUvRelative <= 0 ? 1 : totalUvRelative;
        }

        double  conversionRate = DecimalCalculate.div(orderPaiedNum, totalUv , 4);
        double  conversionRateRelative = DecimalCalculate.div(orderPaiedRelativeNum, totalUvRelative, 4);;

        dataToolsModel.setTitle(DataToolsConstant.TITLE_ORDER_CONVERSION);
        List<ItemModel> itemList = Lists.newArrayList();
        ItemModel createNumItem = createItem(DataToolsConstant.LABEL_ORDER_CREATE_NUM,
                orderCreaterNum + "", calculateRelativeRatio(orderCreaterNum, orderCreaterRelativeNum, 4));

        ItemModel successNumItem = createItem(DataToolsConstant.LABEL_ORDER_SUCCESS_NUM,
                orderPaiedNum + "", calculateRelativeRatio(orderPaiedNum, orderPaiedRelativeNum, 4));

        ItemModel successRateItem = createItem(DataToolsConstant.LABEL_ORDER_SUCCESS_RATE, successRate + "",
                calculateRelativeRatio(successRate, successRateRelative, 4));

        ItemModel conversionRateItem = createItem(DataToolsConstant.CONVERSION_RATE,
                conversionRate + "", calculateRelativeRatio(conversionRate, conversionRateRelative, 4));

        itemList.add(createNumItem);
        itemList.add(successNumItem);
        itemList.add(successRateItem);
        itemList.add(conversionRateItem);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 计算订单转化
     *
     * @param orderDtoList
     * @return
     */
    private ConversionDataModel calculateOrderConversion(List<OrderDto> orderDtoList, Long startTime, Long endTime) {
        ConversionDataModel model = new ConversionDataModel();
        Integer createNum = NumberUtils.INTEGER_ZERO;
        Integer successNum = NumberUtils.INTEGER_ZERO;
        Double successRate = NumberUtils.DOUBLE_ZERO;
        Double conversionRate = NumberUtils.DOUBLE_ZERO;

        Integer createNumRelative = NumberUtils.INTEGER_ZERO;
        Integer successNumRelative = NumberUtils.INTEGER_ZERO;
        Double successRateRelative = NumberUtils.DOUBLE_ZERO;
        Double conversionRateRelative = NumberUtils.DOUBLE_ZERO;

        String startTimeStr = DateFormatUtils.format(startTime, "yyyyMMdd");
        if (totalUv <= 0 && totalUvRelative <= 0) {
            getUvData(startTime, endTime);
            totalUv = totalUv <= 0 ? 1 : totalUv;
            totalUvRelative = totalUvRelative <= 0 ? 1 : totalUvRelative;
        }
        log.info("totalUv : {}, totalUvRelative : {}", totalUv, totalUvRelative);
        if (CollectionUtils.isNotEmpty(orderDtoList)) {
            for (OrderDto orderDto : orderDtoList) {
                if (startTimeStr.equals(orderDto.getCountTime())) { //当前数据
                    successNum = orderDto.getSuccessNum() != null ? orderDto.getSuccessNum() : NumberUtils.INTEGER_ZERO;
                    createNum = orderDto.getCreateNum();
                    successRate = DecimalCalculate.div(
                            NumberUtils.createDouble(successNum + ""),
                            NumberUtils.createDouble(createNum + ""), 4);
                    conversionRate = DecimalCalculate.div(
                            NumberUtils.createDouble(successNum + ""),
                            NumberUtils.createDouble(totalUv + ""), 4);
                } else { //环比数据
                    successNumRelative = orderDto.getSuccessNum() != null ? orderDto.getSuccessNum() : NumberUtils.INTEGER_ZERO;
                    createNumRelative =orderDto.getCreateNum();
                    successRateRelative = DecimalCalculate.div(
                            NumberUtils.createDouble(successNumRelative + ""),
                            NumberUtils.createDouble(createNumRelative + ""), 4);
                    conversionRateRelative = DecimalCalculate.div(
                            NumberUtils.createDouble(successNumRelative + ""),
                            NumberUtils.createDouble(totalUvRelative + ""), 4);
                }
            }
        }
        model.setOrderCreated(createNum);
        model.setOrderSuccess(successNum);
        model.setOrderSuccessRate(successRate);
        model.setConversionRate(conversionRate);

        model.setOrderCreatedRelative(createNumRelative);
        model.setOrderSuccessRelative(successNumRelative);
        model.setOrderSuccessRateRelative(successRateRelative);
        model.setConversionRateRelative(conversionRateRelative);

        model.setOrderCreatedRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(createNum + ""),
                NumberUtils.createDouble(createNumRelative + ""), 4));
        model.setOrderSuccessRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(successNum + ""),
                NumberUtils.createDouble(successNumRelative + ""), 4));
        model.setOrderSuccessRateRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(successRate + ""),
                NumberUtils.createDouble(successRateRelative + ""), 4));
        model.setConversionRateRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(conversionRate + ""),
                NumberUtils.createDouble(conversionRateRelative + ""), 4));
        return model;
    }

    /**
     * 创建数据item
     *
     * @param label
     * @param value
     * @param relativeRatio
     * @return
     */
    private ItemModel createItem(String label, String value, Double relativeRatio) {
        ItemModel itemModel = new ItemModel();
        itemModel.setLabel(label);
        itemModel.setValue(value);
        itemModel.setRelativeRatio(relativeRatio);
        return itemModel;
    }

    private UvStatDay selectUvStatDay(String stateDate) {
        UvStatDayExample uvStatDayExample = new UvStatDayExample();
        uvStatDayExample.createCriteria().andStatDateEqualTo(stateDate);
        List<UvStatDay> uvStatDays = uvStatDayMapper.selectByExample(uvStatDayExample);
        return CollectionUtils.isEmpty(uvStatDays) ? new UvStatDay() : uvStatDays.get(0);
    }


}
