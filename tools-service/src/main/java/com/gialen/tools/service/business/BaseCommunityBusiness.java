package com.gialen.tools.service.business;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.enums.PurchasedTypeEnum;
import com.gialen.tools.dao.dto.ActivityUserDetailDto;
import com.gialen.tools.dao.dto.CommunityDto;
import com.gialen.tools.dao.entity.customer.*;
import com.gialen.tools.dao.repository.customer.UserLevelChangeLogMapper;
import com.gialen.tools.dao.repository.customer.UserLevelMapper;
import com.gialen.tools.dao.repository.customer.UserMapper;
import com.gialen.tools.dao.repository.customer.UserRelationMapper;
import com.gialen.tools.service.model.StoreActivityDetailModel;
import com.gialen.tools.service.model.StoreActivityModel;
import com.gialen.tools.service.model.VipCommunityModel;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 基础社群数据业务类
 * @author lupeibo
 * @date 2019-07-23
 */
public abstract class BaseCommunityBusiness implements CommunityBusiness {

    @Autowired
    private UserRelationMapper userRelationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLevelMapper userLevelMapper;

    @Autowired
    private UserLevelChangeLogMapper userLevelChangeLogMapper;

    @Override
    public PageResponse<StoreActivityDetailModel> getMonthActivityStoreList(Long userId, Byte userType, Integer month, Byte purchasedType, PageRequest pageRequest, String storeName) {
        Long countTotal = userRelationMapper.countActivityOrSilenceStoreTotal(userId, month, userType, purchasedType, storeName);
        if(countTotal == null || countTotal <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<ActivityUserDetailDto> dtoList = userRelationMapper.getActivityOrSilenceStoreList(userId, month, userType, purchasedType, pageRequest, storeName);

        List<StoreActivityDetailModel> modelList = Lists.newArrayListWithCapacity(dtoList.size());
        dtoList.forEach(dto -> modelList.add(Copier.copy(dto, new StoreActivityDetailModel())));

        return PageResponse.success(modelList, pageRequest.getPage(), pageRequest.getLimit(),countTotal);
    }

    /**
     * 统计活跃和静默店主数量
     */
    protected StoreActivityModel countActivityOrSilenceStoreTotal(Long userId, Byte userType, Integer month) {
        StoreActivityModel model = new StoreActivityModel();
        Long activityStoreNum = userRelationMapper.countActivityOrSilenceStoreTotal(userId, month, userType, PurchasedTypeEnum.PURCHASED.getCode(), null);
        Long silenceStoreNum =  userRelationMapper.countActivityOrSilenceStoreTotal(userId, month, userType, PurchasedTypeEnum.NOT_PURCHASED.getCode(), null);

        model.setPurchasedStoreNum(activityStoreNum != null ? activityStoreNum.intValue() : 0);
        model.setNotPurchasedStoreNum(silenceStoreNum != null ? silenceStoreNum.intValue() : 0);

        Long total = activityStoreNum + silenceStoreNum;
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

    /**
     * 获取用户
     * @param userId
     * @return
     */
    protected User getUser(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 获取用户等级
     * @param userId
     * @return
     */
    protected UserLevel getUserLevel(Long userId) {
        UserLevelExample example = new UserLevelExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserLevel> userLevels = userLevelMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(userLevels) ? userLevels.get(0) : null;
    }

    /**
     * 获取实习店经有效期
     * @param userId
     * @return
     */
    protected UserLevelChangeLog getTempSuperCustomerRecord(Long userId) {
        UserLevelChangeLogExample example = new UserLevelChangeLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andNewLevelTypeEqualTo((byte)5);
        example.setOrderByClause("valid_end_time desc");
        example.setLimit(1);
        List<UserLevelChangeLog> list = userLevelChangeLogMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * 计算倒计时
     * @param date
     * @return
     */
    protected String calCountDown(Date date) {
        if(null == date) {
            return null;
        }
        long time1 = date.getTime();
        long time2 = new Date().getTime();

        long diff = time1 - time2;
        if (diff <= 0) {
           return null;
        }
        int day = (int) (diff / (1000 * 60 * 60 * 24));
        int hour = (int) ((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        int minute = (int) ((diff % (1000 * 60 * 60)) / (1000 * 60));
        if(day == 0 && hour == 0 && minute == 0) {
            return null;
        }
        return (day > 0 ? day + "天" : "") + (hour > 0 ? hour + "小时" : "") + minute + "分";
    }

}
