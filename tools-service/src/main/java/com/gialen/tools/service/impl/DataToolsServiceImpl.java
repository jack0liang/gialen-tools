package com.gialen.tools.service.impl;

import com.gialen.common.model.GLResponse;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.constant.DataToolsConstant;
import com.gialen.tools.common.enums.DataTypeEnum;
import com.gialen.tools.common.enums.PlatFormTypeEnum;
import com.gialen.tools.common.enums.RelativeDataTypeEnum;
import com.gialen.tools.dao.dto.DateTimeDto;
import com.gialen.tools.dao.dto.OrderDto;
import com.gialen.tools.dao.dto.SalesDto;
import com.gialen.tools.dao.dto.UserDataDto;
import com.gialen.tools.dao.repository.gialen.BlcOrderMapper;
import com.gialen.tools.dao.repository.point.GdataPointMapper;
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

import java.util.Date;
import java.util.List;

/**
 * 数据看板业务类
 * @author lupeibo
 * @date 2019-05-27
 */
@Slf4j
@Service
public class DataToolsServiceImpl implements DataToolsService {

    @Autowired
    private BlcOrderMapper orderMapper;

    @Autowired
    private GdataPointMapper gdataPointMapper;

    @Override
    public GLResponse getDataList(Long startTime, Long endTime, Byte dataType) {
        List<DataToolsModel> dataList = Lists.newArrayList();
        if(DataTypeEnum.SALES_DATA.getType() == dataType) {
            dataList.add(getSalesCountData(startTime, endTime));
        } else if (DataTypeEnum.UV_DATA.getType() == dataType) {
            dataList.add(getUv(startTime, endTime));
        } else if (DataTypeEnum.ORDER_DATA.getType() == dataType) {
            dataList.add(getOrderPayData(startTime, endTime));
        } else if (DataTypeEnum.CONVERSION_DATA.getType() == dataType) {
            dataList.add(getOrderConversionData(startTime, endTime));
        } else if(DataTypeEnum.USER_DATA.getType() == dataType) {
            dataList.add(getUserData(startTime, endTime));
        }

        return GLResponse.succ(dataList);
    }

    /**
     * 获取用户统计数据
     * @param startTime
     * @param endTime
     * @return
     */
    private DataToolsModel getUserData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();
        DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);

        //统计用户类型
        List<UserDataDto> userDataDtoList = orderMapper.countUserType(dateTimeDto);
        UserDataModel userDataModel = calculateUserType(userDataDtoList, startTime);
        //统计当前新老客订单量
        UserDataDto currentUserOrderData = orderMapper.countUserOrderNum(dateTimeDto);
        userDataModel = calculateUserOrder(currentUserOrderData, userDataModel, RelativeDataTypeEnum.DATA);
        //统计环比新老客订单量
        Date preStartTime = DateUtils.addDays(new Date(startTime), -1);
        Date preEndTime = DateUtils.addDays(new Date(endTime), -1);
        dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(preStartTime.getTime(), preEndTime.getTime());
        UserDataDto UserOrderDataRelative = orderMapper.countUserOrderNum(dateTimeDto);
        userDataModel = calculateUserOrder(UserOrderDataRelative, userDataModel, RelativeDataTypeEnum.RELATIVE_DATA);
        userDataModel.setNewUserOrderNumRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(userDataModel.getNewUserOrderNum()+""),
                NumberUtils.createDouble(userDataModel.getNewUserOrderNumRelative()+""), 4));
        userDataModel.setOldUserOrderNumRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(userDataModel.getOldUserOrderNum()+""),
                NumberUtils.createDouble(userDataModel.getOldUserOrderNumRelative()+""), 4));

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
     * @param startTime
     * @param endTime
     * @return
     */
    private DataToolsModel getUv(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();

        List<Integer> uvList = gdataPointMapper.countUv(DateTimeDtoBuilder.createDateTimeDto(startTime, endTime));
        UvDataModel uvDataModel = calculateUv(uvList);

        dataToolsModel.setTitle(DataToolsConstant.TITLE_UV);
        List<ItemModel> itemList = Lists.newArrayList();

        ItemModel miniProgramItem = createItem(DataToolsConstant.LABEL_MINI_PROGRAM,
                String.valueOf(uvDataModel.getMiniProgramUv()), uvDataModel.getMiniProgramUvRelativeRatio());
        ItemModel appItem = createItem(DataToolsConstant.LABEL_APP, "0", 0d);
        ItemModel h5Item = createItem(DataToolsConstant.LABEL_H5, "0", 0d);

        itemList.add(miniProgramItem);
        itemList.add(appItem);
        itemList.add(h5Item);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 按平台来源统计订单成功数
     * @param startTime
     * @param endTime
     * @return
     */
    private DataToolsModel getOrderPayData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();
        DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);

        List<OrderDto> orderDtoList = orderMapper.countOrderPayNum(dateTimeDto.getStartTime(), dateTimeDto.getEndTime());
        List<OrderDto> relativeOrderDtoList = orderMapper.countOrderPayNum(dateTimeDto.getRelativeStartTime(), dateTimeDto.getRelativeEndTime());

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
     * @return
     */
    private DataToolsModel getSalesCountData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();

        //总销量
        List<SalesDto> totalSalesNumList = orderMapper.countSales(
                DateTimeDtoBuilder.createDateTimeDto(startTime, endTime), null);
        //大礼包销量
        List<SalesDto> totalSalesNumByGiftPackageList = orderMapper.countSales(
                DateTimeDtoBuilder.createDateTimeDto(startTime, endTime), DataToolsConstant.GIFT_PACKAGE_GROUP_ID);
        SalesDataModel salesDataModel = calculateSales(totalSalesNumList, totalSalesNumByGiftPackageList, startTime);

        dataToolsModel.setTitle(DataToolsConstant.TITLE_SALES_COUNT);
        List<ItemModel> itemList = Lists.newArrayList();

        ItemModel totalSalesItem = createItem(DataToolsConstant.LABEL_SALES_TOTAL,
                String.valueOf(DecimalCalculate.round(salesDataModel.getTotalSales(),2)), salesDataModel.getTotalSalseRelativeRatio());
        ItemModel giftPackageSalesItem = createItem(DataToolsConstant.LABEL_SALES_GIFT_PACKAGE,
                String.valueOf(DecimalCalculate.round(salesDataModel.getGiftPackageSales(),2)), salesDataModel.getGiftPackageSalesRelativeRatio());
        ItemModel usualSalesItem = createItem(DataToolsConstant.LABEL_SALES_USUAL,
                String.valueOf(DecimalCalculate.round(salesDataModel.getUsualSales(),2)), salesDataModel.getUsualSalesRelativeRatio());

        itemList.add(totalSalesItem);
        itemList.add(giftPackageSalesItem);
        itemList.add(usualSalesItem);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 计算销售看板数据
     * @param totalSalesList 总销售数据
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
        if(CollectionUtils.isNotEmpty(totalSalesList)) {
            for(SalesDto salesDto : totalSalesList) {
                if(startTimeStr.equals(salesDto.getCountTime())) { //当前数据
                    totalSales = salesDto.getSalesNum() != null ? salesDto.getSalesNum() : NumberUtils.DOUBLE_ZERO;
                } else { //环比数据
                    totalSalseRelative = salesDto.getSalesNum() != null ? salesDto.getSalesNum() : NumberUtils.DOUBLE_ZERO;
                }
            }
        }
        model.setTotalSales(totalSales);
        model.setTotalSalseRelativeRatio(calculateRelativeRatio(totalSales, totalSalseRelative, 4));

        //计算大礼包销售
        if(CollectionUtils.isNotEmpty(giftPackageSalesList)) {
            for(SalesDto salesDto : giftPackageSalesList) {
                if(startTimeStr.equals(salesDto.getCountTime())) { //当前数据
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

        if(CollectionUtils.isNotEmpty(userTypeDataList)) {
            for(UserDataDto userDataDto : userTypeDataList) {
                if(startTimeStr.equals(userDataDto.getCountTime())) { //当前数据
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
                NumberUtils.createDouble(vipNum+""), NumberUtils.createDouble(vipNumRelative+""), 4));
        model.setStoreNumRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(storeNum+""), NumberUtils.createDouble(storeNumRelative+""), 4));
        return model;
    }

    /**
     * 计算新老客订单看板数据
     * @param userOrderData
     * @param userDataModel
     * @param dataTypeEnum
     * @return
     */
    private UserDataModel calculateUserOrder(UserDataDto userOrderData, UserDataModel userDataModel,
                                             RelativeDataTypeEnum dataTypeEnum) {
        Integer newUserOrderNum = NumberUtils.INTEGER_ZERO;
        Integer oldUserOrderNum = NumberUtils.INTEGER_ZERO;

        if(userOrderData != null) {
            newUserOrderNum = userOrderData.getNewUserOrderNum();
            oldUserOrderNum = userOrderData.getOldUserOrderNum();
        }
        if(RelativeDataTypeEnum.DATA.equals(dataTypeEnum)) {
            userDataModel.setNewUserOrderNum(newUserOrderNum);
            userDataModel.setOldUserOrderNum(oldUserOrderNum);
        } else if(RelativeDataTypeEnum.RELATIVE_DATA.equals(dataTypeEnum)) {
            userDataModel.setNewUserOrderNumRelative(newUserOrderNum);
            userDataModel.setOldUserOrderNumRelative(oldUserOrderNum);
        }
        return userDataModel;
    }

    /**
     * 计算UV看板数据
     * @param miniProgramUvList
     * @return
     */
    private UvDataModel calculateUv(List<Integer> miniProgramUvList) {
        UvDataModel model = new UvDataModel();
        Integer miniProgramUv = NumberUtils.INTEGER_ZERO;
        Integer miniProgramUvRelative = NumberUtils.INTEGER_ZERO;
        //计算小程序uv
        if(CollectionUtils.isNotEmpty(miniProgramUvList)) {
            miniProgramUv = miniProgramUvList.get(0) != null ? miniProgramUvList.get(0) : miniProgramUv;
            miniProgramUvRelative = miniProgramUvList.get(1) != null ? miniProgramUvList.get(1) : miniProgramUvRelative;
        }
        model.setMiniProgramUv(miniProgramUv);
        model.setMiniProgramUvRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(miniProgramUv+""), NumberUtils.createDouble(miniProgramUvRelative+""), 4));
        return model;
    }

    /**
     * 计算订单看板数据
     * @param orderDtoList
     * @param relativeOrderDtoList
     * @return
     */
    private PaiedOrderDataModel calculatePaiedOrder(List<OrderDto> orderDtoList, List<OrderDto> relativeOrderDtoList) {
        PaiedOrderDataModel model = new PaiedOrderDataModel();
        model = calculateOrderDtoList(orderDtoList, model, RelativeDataTypeEnum.DATA);
        model = calculateOrderDtoList(relativeOrderDtoList, model, RelativeDataTypeEnum.RELATIVE_DATA);

        model.setMiniProgramOrderRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(model.getMiniProgramOrder()+""),
                NumberUtils.createDouble(model.getMiniProgramOrderRelative()+""), 4));
        model.setH5OrderRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(model.getH5Order()+""),
                NumberUtils.createDouble(model.getH5OrderRelative()+""),4));
        model.setAppOrderRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(model.getAppOrder()+""),
                NumberUtils.createDouble(model.getAppOrderRelative()+""), 4));
        return model;
    }

    private PaiedOrderDataModel calculateOrderDtoList(List<OrderDto> orderDtoList, PaiedOrderDataModel model, RelativeDataTypeEnum dataTypeEnum) {
        int miniProgramOrder = 0;
        int h5Order = 0;
        int appOrder = 0;
        if(CollectionUtils.isNotEmpty(orderDtoList)) {
            for(OrderDto orderDto : orderDtoList) {
                String platForm = orderDto.getPlatForm();
                if(PlatFormTypeEnum.APP_STORE.getCode().equals(platForm)
                        || PlatFormTypeEnum.APP_VIP.getCode().equals(platForm)) {
                    appOrder += orderDto.getSuccessNum();
                } else if (PlatFormTypeEnum.GROUP_WX_PUBLIC_ACCOUNTS.getCode().equals(platForm)
                        || PlatFormTypeEnum.MOBILE.getCode().equals(platForm)) {
                    h5Order += orderDto.getSuccessNum();
                } else if (PlatFormTypeEnum.WX_MINI_PROGRAM.getCode().equals(platForm)) {
                    miniProgramOrder += orderDto.getSuccessNum();
                }
            }
        }
        if(dataTypeEnum.equals(RelativeDataTypeEnum.DATA)) {
            model.setMiniProgramOrder(miniProgramOrder);
            model.setH5Order(h5Order);
            model.setAppOrder(appOrder);
        } else if(dataTypeEnum.equals(RelativeDataTypeEnum.RELATIVE_DATA)) {
            model.setMiniProgramOrderRelative(miniProgramOrder);
            model.setH5OrderRelative(h5Order);
            model.setAppOrderRelative(appOrder);
        }
        return model;
    }

    /**
     * 计算环比
     * @return
     */
    private double calculateRelativeRatio(double data, double relativeData, int scale) {
        double relativeRatio;
        if(relativeData <= NumberUtils.DOUBLE_ZERO) {
            relativeRatio = data;
        } else {
            relativeRatio = DecimalCalculate.div(data - relativeData, relativeData, scale);
        }
        return relativeRatio;
    }

    /**
     * 获取订单转化数据
     * @return
     */
    private DataToolsModel getOrderConversionData(Long startTime, Long endTime) {
        DataToolsModel dataToolsModel = new DataToolsModel();
        DateTimeDto dateTimeDto = DateTimeDtoBuilder.createDateTimeDto(startTime, endTime);

        List<OrderDto> orderDtoList = orderMapper.countOrderNum(dateTimeDto);
        ConversionDataModel conversionDataModel = calculateOrderConversion(orderDtoList, startTime);

        dataToolsModel.setTitle(DataToolsConstant.TITLE_ORDER_CONVERSION);
        List<ItemModel> itemList = Lists.newArrayList();
        ItemModel createNumItem = createItem(DataToolsConstant.LABEL_ORDER_CREATE_NUM,
                conversionDataModel.getOrderCreated() + "", conversionDataModel.getOrderCreatedRelativeRatio());
        ItemModel successNumItem = createItem(DataToolsConstant.LABEL_ORDER_SUCCESS_NUM,
                conversionDataModel.getOrderSuccess() + "", conversionDataModel.getOrderSuccessRelativeRatio());
        ItemModel successRateItem = createItem(DataToolsConstant.LABEL_ORDER_SUCCESS_RATE,
                conversionDataModel.getOrderSuccessRate() + "", conversionDataModel.getOrderSuccessRateRelativeRatio());
        itemList.add(createNumItem);
        itemList.add(successNumItem);
        itemList.add(successRateItem);
        dataToolsModel.setItems(itemList);
        return dataToolsModel;
    }

    /**
     * 计算订单转化
     * @param orderDtoList
     * @return
     */
    private ConversionDataModel calculateOrderConversion(List<OrderDto> orderDtoList, Long startTime) {
        ConversionDataModel model = new ConversionDataModel();
        Integer createNum = NumberUtils.INTEGER_ZERO;
        Integer successNum = NumberUtils.INTEGER_ZERO;
        Double successRate = NumberUtils.DOUBLE_ZERO;
        Integer createNumRelative = NumberUtils.INTEGER_ZERO;
        Integer successNumRelative = NumberUtils.INTEGER_ZERO;
        Double successRateRelative = NumberUtils.DOUBLE_ZERO;
        String startTimeStr = DateFormatUtils.format(startTime, "yyyyMMdd");

        if(CollectionUtils.isNotEmpty(orderDtoList)) {
            for(OrderDto orderDto : orderDtoList) {
                if(startTimeStr.equals(orderDto.getCountTime())) { //当前数据
                    createNum = orderDto.getCreateNum() != null ? orderDto.getCreateNum() : NumberUtils.INTEGER_ZERO;
                    successNum = orderDto.getSuccessNum() != null ? orderDto.getSuccessNum() : NumberUtils.INTEGER_ZERO;
                    successRate = orderDto.getSuccessRate() != null ? orderDto.getSuccessRate() : NumberUtils.DOUBLE_ZERO;
                } else { //环比数据
                    createNumRelative = orderDto.getCreateNum() != null ? orderDto.getCreateNum() : NumberUtils.INTEGER_ZERO;
                    successNumRelative = orderDto.getSuccessNum() != null ? orderDto.getSuccessNum() : NumberUtils.INTEGER_ZERO;
                    successRateRelative = orderDto.getSuccessRate() != null ? orderDto.getSuccessRate() : NumberUtils.DOUBLE_ZERO;
                }
            }
        }
        model.setOrderCreated(createNum);
        model.setOrderSuccess(successNum);
        model.setOrderSuccessRate(successRate);
        model.setOrderCreatedRelative(createNumRelative);
        model.setOrderSuccessRelative(successNumRelative);
        model.setOrderSuccessRateRelative(successRateRelative);

        model.setOrderCreatedRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(createNum+""),
                NumberUtils.createDouble(createNumRelative+""), 4));
        model.setOrderSuccessRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(successNum+""),
                NumberUtils.createDouble(successNumRelative+""),4));
        model.setOrderSuccessRateRelativeRatio(calculateRelativeRatio(
                NumberUtils.createDouble(successRate+""),
                NumberUtils.createDouble(successRateRelative+""), 4));
        return model;
    }

    /**
     * 创建数据item
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


}
