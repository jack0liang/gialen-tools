package com.gialen.tools.api.convertor;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.api.vo.OrderDetailVo;
import com.gialen.tools.api.vo.UserAchievementVo;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.service.model.OrderDetailModel;
import com.gialen.tools.service.model.UserAchievementModel;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

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

}
