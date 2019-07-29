package com.gialen.tools.api.convertor;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.PageResponse;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.api.vo.*;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.CommunityQueryTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.service.model.*;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author lupeibo
 * @date 2019-06-25
 */
public class StoreManagerConvertor {

    public static UserAchievementVo userAchievementModelConvertToVo(UserAchievementModel model) {
        UserAchievementVo userAchievementVo = new UserAchievementVo();
        if(model == null) {
            return userAchievementVo;
        }
        if(model.getSalesModel() != null) {
            userAchievementVo.setMonthSales(model.getSalesModel().getMonthSales());
            userAchievementVo.setTodaySales(model.getSalesModel().getTodaySales());
        }
        if(model.getIncomeModel() != null) {
            userAchievementVo.setMonthTotalIncome(model.getIncomeModel().getMonthTotalIncome());
            userAchievementVo.setMonthAvailableIncome(model.getIncomeModel().getMonthAvailableIncome());
            userAchievementVo.setToBeIncome(model.getIncomeModel().getToBeIncome());
        }
        PieChartDataVo pieChartDataVo = new PieChartDataVo();
        List<PieSeriesNodeVo> seriesNodeVoList = Lists.newArrayListWithCapacity(2);
        seriesNodeVoList.add(new PieSeriesNodeVo("净销售额", model.getSalesModel().getMonthSales(),
                BigDecimal.valueOf(DecimalCalculate.round(model.getSalesModel().getMonthSalseRate().doubleValue(),4))));
        seriesNodeVoList.add(new PieSeriesNodeVo("退款", model.getSalesModel().getMonthRefundSales(),
                BigDecimal.valueOf(DecimalCalculate.round(model.getSalesModel().getMonthRefundRate().doubleValue(),4))));
        pieChartDataVo.setSeries(seriesNodeVoList);
        userAchievementVo.setChartData(pieChartDataVo);
        return userAchievementVo;
    }

    public static OrderDetailVo orderDetailModelConvertToVo(OrderDetailModel model, UserTypeEnum userTypeEnum) {
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        if(model == null) {
            return orderDetailVo;
        }
        Copier.copy(model, orderDetailVo);
        if(UserTypeEnum.STORE.equals(userTypeEnum)) {
            orderDetailVo.setCommission(model.getStoreMasterCommission());
        } else if (UserTypeEnum.STORE_MANAGER.equals(userTypeEnum)) {
            orderDetailVo.setCommission(model.getStoreManagerCommission());
        } else if (UserTypeEnum.STORE_DIRECTOR.equals(userTypeEnum)) {
            orderDetailVo.setCommission(model.getStoreCommission());
        }
        return orderDetailVo;
    }

    public static PageResponse<OrderDetailVo> orderDetailModelPageConvertToVoPage(PageResponse<OrderDetailModel> modelPage, UserTypeEnum userTypeEnum) {
        if(CollectionUtils.isEmpty(modelPage.getList())) {
            return PageResponse.empty(modelPage.getCurrPage(), modelPage.getPageSize());
        }
        List<OrderDetailVo> voList = Lists.newArrayListWithCapacity(modelPage.getList().size());
        modelPage.getList().forEach(model -> {
            voList.add(orderDetailModelConvertToVo(model, userTypeEnum));
        });
        return PageResponse.success(voList, modelPage.getCurrPage(), modelPage.getPageSize(), modelPage.getTotalCount());
    }

    /**
     * 用户model转成childVo列表
     * @param modelList
     * @return
     */
    public static List<ChildVo> convertToChildVoList(List<CustomerModel> modelList, Byte childType) {
        if(CollectionUtils.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        List<ChildVo> voList = Lists.newArrayListWithCapacity(modelList.size());
        modelList.forEach(model -> {
            ChildVo vo = new ChildVo();
            vo.setUserName(StringUtils.isNotBlank(model.getRealName()) ? model.getRealName() : "");
            if(ChildTypeEnum.VIP.getCode() == childType) {
                String phone = StringUtils.isNotBlank(model.getPhone()) ?
                        model.getPhone().substring(0, 3) + "****" + model.getPhone().substring(7, model.getPhone().length()) : "";
                vo.setUserName(phone);
            }
            vo.setRegistTime(model.getDateCreated());
            vo.setIsTempStore(model.getIsTempStoreCustomer());
            voList.add(vo);
        });
        return voList;
    }

    /**
     * vip社群model转vo
     * @param model
     * @return
     */
    public static VipCommunityVo convertVipCommunityModelToVo(VipCommunityModel model) {
        VipCommunityVo vo = Copier.copy(model, new VipCommunityVo());
        vo.setCurMonth(DateFormatUtils.format(new Date(), "yyyy年M月"));
        vo.setPreMonth(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyy年M月"));
        return vo;
    }

    /**
     * VipCommunityModel转NewVipVo
     * @param modelList
     * @return
     */
    public static List<NewVipVo> convertToNewVipVoList(List<VipCommunityModel> modelList) {
        if(CollectionUtils.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        List<NewVipVo> voList = Lists.newArrayListWithCapacity(modelList.size());
        modelList.forEach(model -> voList.add(Copier.copy(model, new NewVipVo())));
        return voList;
    }

    /**
     * 转成社群数据vo
     * @param model
     * @param queryType
     * @param userType
     * @return
     */
    public static CommunityVo convertToCommunityVo(CommunityModel model, CommunityQueryTypeEnum queryType, UserTypeEnum userType) {
        CommunityVo vo = Copier.copy(model, new CommunityVo());
        ChartDataVo chartDataVo = new ChartDataVo();

        SeriesNodeVo nodeVo = new SeriesNodeVo();
        nodeVo.setName("人数");
        List<Integer> dataList = Lists.newArrayList();
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            String[] categories = {"VIP", "店主", "店经"};
            chartDataVo.setCategories(Arrays.asList(categories));
            addDirectorChartData(dataList, model, queryType);
        } else {
            String[] categories = {"VIP", "直接店主", "间接店主", "直接店经"};
            chartDataVo.setCategories(Arrays.asList(categories));
            addManagerChartData(dataList, model, queryType);
        }
        nodeVo.setData(dataList);
        List<SeriesNodeVo> seriesNodeVoList = Lists.newArrayList();
        seriesNodeVoList.add(nodeVo);
        chartDataVo.setSeries(seriesNodeVoList);
        vo.setChartData(chartDataVo);
        vo.setMonth(Byte.parseByte(DateFormatUtils.format(new Date(), "M")));
        return vo;
    }

    /**
     * 店主活跃数据model转vo
     * @param model
     * @return
     */
    public static StoreActivityDataVo convertStoreActivityModelToVo(StoreActivityModel model) {
        StoreActivityDataVo vo = Copier.copy(model, new StoreActivityDataVo());
        PieChartDataVo pieChartDataVo = new PieChartDataVo();
        List<PieSeriesNodeVo> seriesNodeVoList = Lists.newArrayListWithCapacity(2);
        seriesNodeVoList.add(new PieSeriesNodeVo("已开单", BigDecimal.valueOf(model.getPurchasedStoreNum()), model.getPurchasedRate()));
        seriesNodeVoList.add(new PieSeriesNodeVo("未开单", BigDecimal.valueOf(model.getNotPurchasedStoreNum()), model.getNotPurchasedRate()));
        pieChartDataVo.setSeries(seriesNodeVoList);
        vo.setChartData(pieChartDataVo);
        vo.setCurMonth(DateFormatUtils.format(new Date(), "yyyy年M月"));
        vo.setPreMonth(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyy年M月"));
        vo.setMonth(Byte.parseByte(DateFormatUtils.format(new Date(), "M")));
        return vo;
    }

    /**
     * 活跃店主明细model转vo
     * @param modelList
     * @return
     */
    public static List<StoreActivityDetailVo> convertStoreActivityDetailModelToVoList(List<StoreActivityDetailModel> modelList) {
        if(CollectionUtils.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        List<StoreActivityDetailVo> voList = Lists.newArrayListWithCapacity(modelList.size());
        modelList.forEach(model -> voList.add(Copier.copy(model, new StoreActivityDetailVo())));
        return voList;
    }

    /**
     * 设置店董图表数据
     * @param dataList
     * @param model
     * @param queryType
     */
    private static void addDirectorChartData(List<Integer> dataList, CommunityModel model, CommunityQueryTypeEnum queryType) {
        if(CommunityQueryTypeEnum.All.equals(queryType)) {
            dataList.add(model.getTotalVipNum());
            dataList.add(model.getTotalStoreNum());
            dataList.add(model.getTotalStoreManagerNum());
        } else if (CommunityQueryTypeEnum.CUR_MONTH.equals(queryType)) {
            dataList.add(model.getMonthNewVipNum());
            dataList.add(model.getMonthNewStoreNum());
            dataList.add(model.getMonthNewStoreManagerNum());
        }
    }

    /**
     * 设置店经图表数据
     * @param dataList
     * @param model
     * @param queryType
     */
    private static void addManagerChartData(List<Integer> dataList, CommunityModel model, CommunityQueryTypeEnum queryType) {
        if(CommunityQueryTypeEnum.All.equals(queryType)) {
            dataList.add(model.getTotalVipNum());
            dataList.add(model.getTotalDirectStoreNum());
            dataList.add(model.getTotalIndirectStoreNum());
            dataList.add(model.getTotalStoreManagerNum());
        } else if (CommunityQueryTypeEnum.CUR_MONTH.equals(queryType)) {
            dataList.add(model.getMonthNewVipNum());
            dataList.add(model.getMonthNewDirectStoreNum());
            dataList.add(model.getMonthNewIndirectStoreNum());
            dataList.add(model.getMonthNewStoreManagerNum());
        }
    }

}
