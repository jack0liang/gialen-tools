package com.gialen.tools.service.business;

import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.PurchasedTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.dao.dto.CommunityDto;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.dao.entity.gialen.BlcCustomerExample;
import com.gialen.tools.dao.repository.gialen.BlcCustomerMapper;
import com.gialen.tools.dao.repository.gialen.BlcCustomerRelationMapper;
import com.gialen.tools.service.convertor.CustomerConvertor;
import com.gialen.tools.service.model.CommunityModel;
import com.gialen.tools.service.model.CustomerModel;
import com.gialen.tools.service.model.StoreActivityModel;
import com.gialen.tools.service.model.VipCommunityModel;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 店经社群数据业务类
 * @author lupeibo
 * @date 2019-07-23
 */
@Service("storeManagerCommunityBiz")
public class StoreManagerCommunityBusiness extends BaseCommunityBusiness {

    @Autowired
    private BlcCustomerMapper blcCustomerMapper;

    @Autowired
    private BlcCustomerRelationMapper blcCustomerRelationMapper;

    @Override
    public CommunityModel countTotalCommunityData(Long userId) {
        CommunityModel model = new CommunityModel();
        CommunityDto storeAndVipDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, null, null);
        model.setTotalVipNum(storeAndVipDto != null ? storeAndVipDto.getTotalVipNum() : 0);
        model.setTotalStoreNum(storeAndVipDto != null ? storeAndVipDto.getTotalStoreNum() : 0);

        CommunityDto directStoreAndManagerDto = blcCustomerMapper.countDirectStoreAndManagerNumForManager(userId);
        model.setTotalStoreManagerNum(directStoreAndManagerDto != null ? directStoreAndManagerDto.getTotalStoreManagerNum() : 0);
        model.setTotalDirectStoreNum(directStoreAndManagerDto != null ? directStoreAndManagerDto.getTotalDirectStoreNum() : 0);
        model.setTotalIndirectStoreNum(model.getTotalStoreNum() - model.getTotalDirectStoreNum());
        model.setTotalNum(model.getTotalVipNum() + model.getTotalStoreNum() + model.getTotalStoreManagerNum());
        return model;
    }

    @Override
    public CommunityModel countMonthCommunityData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        CommunityDto monthStoreAndVipDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, month, null);
        CommunityDto todayStoreAndVipDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, null, today);
        model.setMonthNewVipNum(monthStoreAndVipDto != null ? monthStoreAndVipDto.getMonthNewVipNum() : 0);
        model.setMonthNewStoreNum(monthStoreAndVipDto != null ? monthStoreAndVipDto.getMonthNewStoreNum() : 0);
        model.setTodayNewVipNum(todayStoreAndVipDto != null ? todayStoreAndVipDto.getDayNewVipNum() : 0);
        model.setTodayNewStoreNum(todayStoreAndVipDto != null ? todayStoreAndVipDto.getDayNewStoreNum() : 0);

        Integer monthNewDirectStoreNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, UserTypeEnum.STORE.getType(), month);
        model.setMonthNewDirectStoreNum(monthNewDirectStoreNum);
        model.setMonthNewIndirectStoreNum(model.getMonthNewStoreNum() - model.getMonthNewDirectStoreNum());
        Integer monthNewStoreManagerNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, UserTypeEnum.STORE_MANAGER.getType(), month);
        model.setMonthNewStoreManagerNum(monthNewStoreManagerNum);

        CommunityModel totalModel = countTotalCommunityData(userId);
        model.setTotalNum(totalModel.getTotalNum());
        return model;
    }

    @Override
    public PageResponse<CustomerModel> getChildList(Long userId, Byte childType, PageRequest pageRequest) {
        CommunityModel communityModel = countTotalCommunityData(userId);
        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            totalCount = communityModel.getTotalStoreManagerNum();
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            totalCount = communityModel.getTotalDirectStoreNum();
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            totalCount = communityModel.getTotalIndirectStoreNum();
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            totalCount = communityModel.getTotalVipNum();
        }
        List<CustomerModel> modelList = null;
        //店经的下级店经和直接店主
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType || ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            modelList = getManagerOrDirectStoreList(userId, pageRequest, childType);
        //店经的间接店主和vip
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType || ChildTypeEnum.VIP.getCode() == childType) {
            modelList = getIndirectStoreOrVipList(userId, pageRequest, childType);
        }
        return PageResponse.success(modelList, pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public PageResponse<CustomerModel> getMonthChildList(Long userId, Byte childType, PageRequest pageRequest, Integer month) {
        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            Integer monthNewStoreManagerNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, UserTypeEnum.STORE_MANAGER.getType(), month);
            totalCount = monthNewStoreManagerNum != null ? monthNewStoreManagerNum.longValue() : 0L;
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            Integer monthNewDirectStoreNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, UserTypeEnum.STORE.getType(), month);
            totalCount = monthNewDirectStoreNum != null ? monthNewDirectStoreNum.longValue() : 0L;
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            CommunityDto monthStoreAndVipDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, month, null);
            Integer monthNewDirectStoreNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, UserTypeEnum.STORE.getType(), month);
            Integer monthNewStoreNum = monthStoreAndVipDto != null ? monthStoreAndVipDto.getMonthNewStoreNum() : 0;
            totalCount = monthNewStoreNum - (monthNewDirectStoreNum != null ? monthNewDirectStoreNum : 0L);
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            CommunityDto monthStoreAndVipDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, month, null);
            totalCount = monthStoreAndVipDto != null ? monthStoreAndVipDto.getMonthNewVipNum().longValue() : 0L;
        }
        List<CustomerModel> modelList = null;
        //店经的下级店经和直接店主
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType || ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            modelList = getMonthManagerOrDirectStoreList(userId, pageRequest, childType, month);
            //店经的间接店主和vip
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType || ChildTypeEnum.VIP.getCode() == childType) {
            modelList = getMonthIndirectStoreOrVipList(userId, pageRequest, childType, month);
        }
        return PageResponse.success(modelList, pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public CommunityModel countMonthVipData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        CommunityDto monthStoreAndVipDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, month, null);
        model.setMonthNewVipNum(monthStoreAndVipDto != null ? monthStoreAndVipDto.getMonthNewVipNum() : 0);
        return model;
    }

    @Override
    public CommunityModel countDayVipData(Long userId, Integer day) {
        CommunityModel model = new CommunityModel();
        CommunityDto dayStoreAndVipDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, null, day);
        model.setTodayNewVipNum(dayStoreAndVipDto != null ? dayStoreAndVipDto.getDayNewVipNum() : 0);
        return model;
    }

    @Override
    public PageResponse<VipCommunityModel> getMonthNewVipList(Long userId, PageRequest pageRequest, Integer month, String storeName) {
        Long totalCount = blcCustomerRelationMapper.countMonthHasNewVipStoreNumForManager(userId, month, storeName);
        if(totalCount == null || totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        List<CommunityDto> communityDtoList = blcCustomerRelationMapper.getMonthNewVipListForManager(userId, today, month, pageRequest, storeName);

        return PageResponse.success(convertCommunityDtoToVipCommunityModel(communityDtoList), pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public StoreActivityModel countMonthActivityStore(Long userId) {
        StoreActivityModel model = new StoreActivityModel();
        int curMonth = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        int preMonth = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        model = countActivityOrSilenceStoreTotal(userId, UserTypeEnum.STORE_MANAGER.getType(), curMonth, model);

        CommunityDto curMonthDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, curMonth, null);
        model.setCurMonthNewStoreNum(curMonthDto != null ? curMonthDto.getMonthNewStoreNum() : 0);
        CommunityDto preMonthDto = blcCustomerRelationMapper.countStoreAndVipCommunityNumForManager(userId, preMonth, null);
        model.setPreMonthNewStoreNum(preMonthDto != null ? preMonthDto.getMonthNewStoreNum() : 0);
        return model;
    }

    /**
     * 查询店经的下级店经或直接店主列表
     * @param userId
     * @param page
     * @param childType
     * @return
     */
    private List<CustomerModel> getManagerOrDirectStoreList(Long userId, PageRequest page, Byte childType) {
        BlcCustomerExample example = new BlcCustomerExample();
        BlcCustomerExample.Criteria criteria = example.createCriteria();
        criteria.andUserTypeEqualTo(UserTypeEnum.STORE.getCode());
        criteria.andHigherCustomerIdEqualTo(userId);
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            criteria.andUserLevelNewIdEqualTo("4");
        } else if(ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            criteria.andUserLevelNewIdEqualTo("1");
        }
        example.setOrderByClause("DATE_CREATED desc");
        example.setLimit(page.getLimit());
        example.setOffset(page.getOffset());
        List<BlcCustomer> customerList = blcCustomerMapper.selectByExample(example);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }

    /**
     * 查询店经的间接店主或vip列表
     * @param userId
     * @param page
     * @param childType
     * @return
     */
    private List<CustomerModel> getIndirectStoreOrVipList(Long userId, PageRequest page, Byte childType) {
        List<BlcCustomer> customerList = blcCustomerMapper.getIndirectStoreOrVipListForManager(userId, childType, page, null);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }

    /**
     * 查询店经的月新增店经或直接店主列表
     * @param userId
     * @param page
     * @param childType
     * @param month
     * @return
     */
    private List<CustomerModel> getMonthManagerOrDirectStoreList(Long userId, PageRequest page, Byte childType, Integer month) {
        List<BlcCustomer> customerList = blcCustomerMapper.getMonthManagerOrDirectStoreListForManager(userId, childType, page, month);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }

    /**
     * 查询店经的月新增间接店主或vip列表
     * @param userId
     * @param page
     * @param childType
     * @param month
     * @return
     */
    private List<CustomerModel> getMonthIndirectStoreOrVipList(Long userId, PageRequest page, Byte childType, Integer month) {
        List<BlcCustomer> customerList = blcCustomerMapper.getIndirectStoreOrVipListForManager(userId, childType, page, month);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }
}
