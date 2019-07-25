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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 店董社群数据业务类
 * @author lupeibo
 * @date 2019-07-23
 */
@Service("storeDirectorCommunityBiz")
public class StoreDirectorCommunityBusiness extends BaseCommunityBusiness {

    @Autowired
    private BlcCustomerMapper blcCustomerMapper;

    @Autowired
    private BlcCustomerRelationMapper blcCustomerRelationMapper;

    @Override
    public CommunityModel countTotalCommunityData(Long userId) {
        CommunityDto dto = blcCustomerMapper.countTotalNumForDirector(userId);
        if(dto == null) {
            return null;
        }
        return Copier.copy(dto, new CommunityModel());
    }

    @Override
    public CommunityModel countMonthCommunityData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        //统计月新增店经数
        Integer monthNewStoreManagerNum = blcCustomerMapper.countMonthManagerNumForDirector(userId, month);
        model.setMonthNewStoreManagerNum(monthNewStoreManagerNum);
        //统计月新增店主和vip数
        CommunityDto monthDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, month, null);
        if(monthDto != null) {
            model.setMonthNewStoreNum(monthDto.getMonthNewStoreNum());
            model.setMonthNewVipNum(monthDto.getMonthNewVipNum());
        }
        //统计今日新增店主和vip数
        Integer today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        CommunityDto dayDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, null, today);
        if(dayDto != null) {
            model.setTodayNewStoreNum(dayDto.getDayNewStoreNum());
            model.setTodayNewVipNum(dayDto.getDayNewVipNum());
        }
        CommunityDto totalDto = blcCustomerMapper.countTotalNumForDirector(userId);
        if(totalDto != null) {
            model.setTotalNum(totalDto.getTotalNum());
        }
        return model;
    }

    @Override
    public PageResponse<CustomerModel> getChildList(Long userId, Byte childType, PageRequest pageRequest) {
        CommunityModel communityModel = countTotalCommunityData(userId);
        List<CustomerModel> modelList = getChildListForStoreDirector(userId, pageRequest, childType);
        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            totalCount = communityModel.getTotalStoreManagerNum();
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType || ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            totalCount = communityModel.getTotalStoreNum();
        } else if (ChildTypeEnum.VIP.equals(childType)) {
            totalCount = communityModel.getTotalVipNum();
        }
        return PageResponse.success(modelList,pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public PageResponse<CustomerModel> getMonthChildList(Long userId, Byte childType, PageRequest pageRequest, Integer month) {
        List<CustomerModel> modelList = getMonthChildListForStoreDirector(userId, pageRequest, childType, month);
        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            Integer monthNewStoreManagerNum = blcCustomerMapper.countMonthManagerNumForDirector(userId, month);
            totalCount = monthNewStoreManagerNum != null ?  monthNewStoreManagerNum.longValue() : 0L;
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType || ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            CommunityDto monthDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, month, null);
            totalCount = monthDto != null ? monthDto.getMonthNewStoreNum().longValue() : 0L;
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            CommunityDto monthDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, month, null);
            totalCount = monthDto != null ? monthDto.getMonthNewVipNum().longValue() : 0L;
        }
        return PageResponse.success(modelList,pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public CommunityModel countMonthVipData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        CommunityDto monthDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, month, null);
        model.setMonthNewVipNum(monthDto != null ? monthDto.getMonthNewVipNum() : 0);
        return model;
    }

    @Override
    public CommunityModel countDayVipData(Long userId, Integer day) {
        CommunityModel model = new CommunityModel();
        CommunityDto dayDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, null, day);
        model.setTodayNewVipNum(null != dayDto ? dayDto.getDayNewVipNum() : 0);
        return model;
    }

    @Override
    public PageResponse<VipCommunityModel> getMonthNewVipList(Long userId, PageRequest pageRequest, Integer month, String storeName) {
        Long totalCount = blcCustomerMapper.countMonthHasNewVipStoreNumForDirector(userId, month, storeName);
        if(totalCount == null || totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        List<CommunityDto> communityDtoList = blcCustomerMapper.getMonthNewVipListForDirector(userId, today, month, pageRequest, storeName);

        return PageResponse.success(convertCommunityDtoToVipCommunityModel(communityDtoList), pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public StoreActivityModel countMonthActivityStore(Long userId) {
        StoreActivityModel model = new StoreActivityModel();
        int curMonth = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        int preMonth = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        model = countActivityOrSilenceStoreTotal(userId, UserTypeEnum.STORE_DIRECTOR.getType(), curMonth, model);

        CommunityDto curMonthDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, curMonth,null);
        model.setCurMonthNewStoreNum(curMonthDto != null ? curMonthDto.getMonthNewStoreNum() : 0);
        CommunityDto preMonthDto = blcCustomerMapper.countStoreAndVipNumForDirector(userId, preMonth,null);
        model.setPreMonthNewStoreNum(preMonthDto != null ? preMonthDto.getMonthNewStoreNum() : 0);
        return model;
    }

    /**
     * 查询店董的全部下级会员列表
     * @param storeDirectorId
     * @param page
     * @param childType
     * @return
     */
    private List<CustomerModel> getChildListForStoreDirector(Long storeDirectorId, PageRequest page, Byte childType) {
        BlcCustomerExample example = new BlcCustomerExample();
        BlcCustomerExample.Criteria criteria = example.createCriteria();
        criteria.andShopStoreIdEqualTo(storeDirectorId);

        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) { //店经
            criteria.andUserLevelNewIdEqualTo("4").andUserTypeEqualTo(UserTypeEnum.STORE.getCode());
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType ||
                ChildTypeEnum.INDIRECT_STORE.getCode() == childType) { //店主
            criteria.andUserLevelNewIdEqualTo("1").andUserTypeEqualTo(UserTypeEnum.STORE.getCode());
        } else if (ChildTypeEnum.VIP.getCode() == childType) { //vip
            criteria.andUserTypeEqualTo(UserTypeEnum.VIP.getCode());
        }
        example.setOrderByClause("DATE_CREATED desc");
        example.setLimit(page.getLimit());
        example.setOffset(page.getOffset());
        List<BlcCustomer> customerList = blcCustomerMapper.selectByExample(example);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }

    /**
     * 查询店董的月新增会员列表
     * @param storeDirectorId
     * @param page
     * @param childType
     * @param month
     * @return
     */
    private List<CustomerModel> getMonthChildListForStoreDirector(Long storeDirectorId, PageRequest page, Byte childType, Integer month) {
        List<BlcCustomer> customerList = blcCustomerMapper.getMonthUserChildListForDirector(storeDirectorId, childType, month, page);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }

}
