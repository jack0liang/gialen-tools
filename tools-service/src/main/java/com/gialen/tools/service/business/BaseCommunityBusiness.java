package com.gialen.tools.service.business;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.enums.PurchasedTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.dao.dto.ActivityUserDetailDto;
import com.gialen.tools.dao.dto.CommunityDto;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.dao.entity.gialen.RomaImportSuperCustomerRecord;
import com.gialen.tools.dao.entity.gialen.RomaImportSuperCustomerRecordExample;
import com.gialen.tools.dao.repository.gialen.BlcCustomerMapper;
import com.gialen.tools.dao.repository.gialen.BlcCustomerRelationMapper;
import com.gialen.tools.dao.repository.gialen.RomaImportSuperCustomerRecordMapper;
import com.gialen.tools.service.model.StoreActivityDetailModel;
import com.gialen.tools.service.model.StoreActivityModel;
import com.gialen.tools.service.model.VipCommunityModel;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
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
    private BlcCustomerRelationMapper blcCustomerRelationMapper;

    @Autowired
    private BlcCustomerMapper blcCustomerMapper;

    @Autowired
    private RomaImportSuperCustomerRecordMapper romaImportSuperCustomerRecordMapper;

    @Override
    public PageResponse<StoreActivityDetailModel> getMonthActivityStoreList(Long userId, Byte userType, Integer month, Byte purchasedType, PageRequest pageRequest, String storeName) {
        Long countTotal;
        if(UserTypeEnum.STORE_DIRECTOR.getType() == userType) {
            countTotal = blcCustomerMapper.countActivityOrSilenceStoreTotalForDirector(userId, month, purchasedType, storeName);
        } else {
            countTotal= blcCustomerRelationMapper.countActivityOrSilenceStoreTotalForManager(userId, month, purchasedType, storeName);
        }
        if(countTotal == null || countTotal <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<ActivityUserDetailDto> dtoList;
        if(UserTypeEnum.STORE_DIRECTOR.getType() == userType) {
            dtoList = blcCustomerMapper.getActivityOrSilenceStoreListForDirector(userId, month, purchasedType, pageRequest, storeName);
        } else {
            dtoList = blcCustomerRelationMapper.getActivityOrSilenceStoreListForManager(userId, month, purchasedType, pageRequest, storeName);
        }

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
        Long activityStoreNum, silenceStoreNum;

        if(UserTypeEnum.STORE_DIRECTOR.getType() == userType) {
            activityStoreNum = blcCustomerMapper.countActivityOrSilenceStoreTotalForDirector(userId, month, PurchasedTypeEnum.PURCHASED.getCode(), null);
            silenceStoreNum = blcCustomerMapper.countActivityOrSilenceStoreTotalForDirector(userId, month, PurchasedTypeEnum.NOT_PURCHASED.getCode(), null);
        } else {
            activityStoreNum = blcCustomerRelationMapper.countActivityOrSilenceStoreTotalForManager(userId, month, PurchasedTypeEnum.PURCHASED.getCode(), null);
            silenceStoreNum = blcCustomerRelationMapper.countActivityOrSilenceStoreTotalForManager(userId, month, PurchasedTypeEnum.NOT_PURCHASED.getCode(), null);
        }
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
     * @param customerId
     * @return
     */
    protected BlcCustomer getCustomer(Long customerId) {
        return blcCustomerMapper.selectByPrimaryKey(customerId);
    }

    /**
     * 获取实习店经
     * @param customerId
     * @return
     */
    protected RomaImportSuperCustomerRecord getTempSuperCustomerRecord(Long customerId) {
        RomaImportSuperCustomerRecordExample example = new RomaImportSuperCustomerRecordExample();
        example.createCriteria().andCustomerIdEqualTo(customerId);
        List<RomaImportSuperCustomerRecord> list = romaImportSuperCustomerRecordMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
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
