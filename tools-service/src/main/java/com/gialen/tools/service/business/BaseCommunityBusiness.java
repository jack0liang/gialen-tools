package com.gialen.tools.service.business;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.PurchasedTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.dao.dto.ActivityUserDetailDto;
import com.gialen.tools.dao.dto.CommunityDto;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.dao.entity.gialen.BlcCustomerExample;
import com.gialen.tools.dao.repository.gialen.BlcCustomerMapper;
import com.gialen.tools.dao.repository.gialen.BlcCustomerRelationMapper;
import com.gialen.tools.service.convertor.CustomerConvertor;
import com.gialen.tools.service.model.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基础社群数据业务类
 * @author lupeibo
 * @date 2019-07-23
 */
public abstract class BaseCommunityBusiness implements CommunityBusiness {

    @Autowired
    private BlcCustomerRelationMapper blcCustomerRelationMapper;

    @Override
    public PageResponse<StoreActivityDetailModel> getMonthActivityStoreList(Long userId, Byte userType, Integer month, Byte purchasedType, PageRequest pageRequest) {
        Long countTotal = blcCustomerRelationMapper.countActivityOrSilenceStoreTotal(userId, month, userType, purchasedType);
        if(countTotal == null || countTotal <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<ActivityUserDetailDto> dtoList = blcCustomerRelationMapper.getActivityOrSilenceStoreList(userId, month, userType, purchasedType, pageRequest);
        List<StoreActivityDetailModel> modelList = Lists.newArrayListWithCapacity(dtoList.size());
        dtoList.forEach(dto -> modelList.add(Copier.copy(dto, new StoreActivityDetailModel())));

        return PageResponse.success(modelList, pageRequest.getPage(), pageRequest.getLimit(),countTotal);
    }

    /**
     * 统计活跃和静默店主数量
     * @param userId
     * @param month
     * @param model
     * @return
     */
    protected StoreActivityModel countActivityOrSilenceStoreTotal(Long userId, Byte userType, Integer month, StoreActivityModel model) {
        Long activityStoreNum = blcCustomerRelationMapper.countActivityOrSilenceStoreTotal(userId, month, userType, PurchasedTypeEnum.PURCHASED.getCode());
        Long silenceStoreNum = blcCustomerRelationMapper.countActivityOrSilenceStoreTotal(userId, month, userType, PurchasedTypeEnum.NOT_PURCHASED.getCode());
        Long total = activityStoreNum + silenceStoreNum;

        model.setPurchasedStoreNum(activityStoreNum.intValue());
        model.setNotPurchasedStoreNum(silenceStoreNum.intValue());

        if(total == null || total <= 0) {
            model.setPurchasedRate(BigDecimal.ZERO);
            model.setNotPurchasedRate(BigDecimal.ZERO);
        } else {
            model.setPurchasedRate(BigDecimal.valueOf(DecimalCalculate.div(activityStoreNum.doubleValue(), total.doubleValue(), 4)));
            model.setNotPurchasedRate(BigDecimal.valueOf(DecimalCalculate.div(silenceStoreNum.doubleValue(), total.doubleValue(), 4)));
        }
        return model;
    }

    protected List<VipCommunityModel> convertCommunityDtoToVipCommunityModel(List<CommunityDto> communityDtoList) {
        if(CollectionUtils.isEmpty(communityDtoList)) {
            return Collections.emptyList();
        }
        List<VipCommunityModel> vipCommunityModelList = Lists.newArrayListWithCapacity(communityDtoList.size());
        for(CommunityDto communityDto : communityDtoList) {
            VipCommunityModel model = new VipCommunityModel();
            model.setStoreName(StringUtils.isNotBlank(communityDto.getStoreName()) ? communityDto.getStoreName() : "");
            model.setCurMonthNewVipNum(communityDto.getMonthNewVipNum());
            model.setTodayNewVipNum(communityDto.getDayNewVipNum());
            vipCommunityModelList.add(model);
        }
        return vipCommunityModelList;
    }
}
