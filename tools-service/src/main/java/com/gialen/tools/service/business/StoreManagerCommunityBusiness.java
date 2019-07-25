package com.gialen.tools.service.business;

import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.common.enums.ChildTypeEnum;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CommunityModel countTotalCommunityData(Long userId, String userName) {
        CommunityModel model = new CommunityModel();
        CommunityDto indirectStoreAndVipDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, null, null, userName);
        model.setTotalVipNum(indirectStoreAndVipDto != null ? indirectStoreAndVipDto.getTotalVipNum() : 0);
        model.setTotalIndirectStoreNum(indirectStoreAndVipDto != null ? indirectStoreAndVipDto.getTotalIndirectStoreNum() : 0);

        CommunityDto directStoreAndManagerDto = blcCustomerMapper.countDirectStoreAndManagerNumForManager(userId, userName);
        model.setTotalStoreManagerNum(directStoreAndManagerDto != null ? directStoreAndManagerDto.getTotalStoreManagerNum() : 0);
        model.setTotalDirectStoreNum(directStoreAndManagerDto != null ? directStoreAndManagerDto.getTotalDirectStoreNum() : 0);
        model.setTotalStoreNum(model.getTotalIndirectStoreNum() + model.getTotalDirectStoreNum());
        model.setTotalNum(model.getTotalVipNum() + model.getTotalStoreNum() + model.getTotalStoreManagerNum());
        return model;
    }

    @Override
    public CommunityModel countMonthCommunityData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        CommunityDto monthIndirectStoreAndVipDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, month, null, null);
        CommunityDto todayIndirectStoreAndVipDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, null, today, null);
        model.setMonthNewVipNum(monthIndirectStoreAndVipDto != null ? monthIndirectStoreAndVipDto.getMonthNewVipNum() : 0);
        model.setMonthNewIndirectStoreNum(monthIndirectStoreAndVipDto != null ? monthIndirectStoreAndVipDto.getMonthNewIndirectStoreNum() : 0);
        model.setTodayNewVipNum(todayIndirectStoreAndVipDto != null ? todayIndirectStoreAndVipDto.getDayNewVipNum() : 0);
        model.setTodayNewStoreNum(todayIndirectStoreAndVipDto != null ? todayIndirectStoreAndVipDto.getDayNewStoreNum() : 0);

        Integer monthNewDirectStoreNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, UserTypeEnum.STORE.getType(), month);
        model.setMonthNewDirectStoreNum(monthNewDirectStoreNum);
        model.setMonthNewStoreNum(model.getMonthNewIndirectStoreNum() + model.getMonthNewDirectStoreNum());
        Integer monthNewStoreManagerNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, UserTypeEnum.STORE_MANAGER.getType(), month);
        model.setMonthNewStoreManagerNum(monthNewStoreManagerNum);

        CommunityModel totalModel = countTotalCommunityData(userId, null);
        model.setTotalNum(totalModel.getTotalNum());
        return model;
    }

    @Override
    public PageResponse<CustomerModel> getChildList(Long userId, Byte childType, PageRequest pageRequest, String userName) {
        CommunityModel communityModel = countTotalCommunityData(userId, userName);
        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            totalCount = communityModel != null ? communityModel.getTotalStoreManagerNum() : 0L;
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            totalCount = communityModel != null ? communityModel.getTotalDirectStoreNum() : 0L;
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            totalCount = communityModel != null ? communityModel.getTotalIndirectStoreNum() : 0L;
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            totalCount = communityModel != null ? communityModel.getTotalVipNum() : 0L;
        }
        if(totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<CustomerModel> modelList = null;
        //查店经或直接店主
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType || ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            modelList = getManagerOrDirectStoreList(userId, pageRequest, childType, userName);
        //查间接店主或vip
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType || ChildTypeEnum.VIP.getCode() == childType) {
            modelList = getIndirectStoreOrVipList(userId, pageRequest, childType, userName);
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
            CommunityDto monthNewIndirectStoreDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, month, null, null);
            totalCount = monthNewIndirectStoreDto != null ? monthNewIndirectStoreDto.getMonthNewIndirectStoreNum() : 0L;
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            CommunityDto monthStoreAndVipDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, month, null, null);
            totalCount = monthStoreAndVipDto != null ? monthStoreAndVipDto.getMonthNewVipNum().longValue() : 0L;
        }
        if(totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<CustomerModel> modelList = null;
        //店经和直接店主
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType || ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            modelList = getMonthManagerOrDirectStoreList(userId, pageRequest, childType, month);
        //间接店主和vip
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType || ChildTypeEnum.VIP.getCode() == childType) {
            modelList = getMonthIndirectStoreOrVipList(userId, pageRequest, childType, month);
        }
        return PageResponse.success(modelList, pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public CommunityModel countMonthVipData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        CommunityDto monthStoreAndVipDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, month, null, null);
        model.setMonthNewVipNum(monthStoreAndVipDto != null ? monthStoreAndVipDto.getMonthNewVipNum() : 0);
        return model;
    }

    @Override
    public CommunityModel countDayVipData(Long userId, Integer day) {
        CommunityModel model = new CommunityModel();
        CommunityDto dayStoreAndVipDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, null, day, null);
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

        Integer curMonthDirectStoreNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, ChildTypeEnum.DIRECT_STORE.getCode(), curMonth);
        CommunityDto curMonthInDirectStoreDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, curMonth, null, null);
        model.setCurMonthNewStoreNum(curMonthDirectStoreNum + (curMonthInDirectStoreDto != null ? curMonthInDirectStoreDto.getMonthNewIndirectStoreNum() : 0));

        Integer preMonthDirectStoreNum = blcCustomerMapper.countMonthDirectStoreAndManagerNumForManager(userId, ChildTypeEnum.DIRECT_STORE.getCode(), preMonth);
        CommunityDto preMonthInDirectStoreDto = blcCustomerRelationMapper.countIndirectStoreAndVipNumForManager(userId, preMonth, null, null);
        model.setPreMonthNewStoreNum(preMonthDirectStoreNum + (preMonthInDirectStoreDto != null ? preMonthInDirectStoreDto.getMonthNewStoreNum() : 0));
        return model;
    }

    /**
     * 查询店经的下级店经或直接店主列表
     * @param userId
     * @param page
     * @param childType
     * @return
     */
    private List<CustomerModel> getManagerOrDirectStoreList(Long userId, PageRequest page, Byte childType, String userName) {
        BlcCustomerExample example = new BlcCustomerExample();
        BlcCustomerExample.Criteria criteria = example.createCriteria();
        criteria.andUserTypeEqualTo(UserTypeEnum.STORE.getCode());
        criteria.andHigherCustomerIdEqualTo(userId);
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            criteria.andUserLevelNewIdEqualTo("4");
        } else if(ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            criteria.andUserLevelNewIdEqualTo("1");
        }
        if(StringUtils.isNotBlank(userName)) {
            criteria.andRealNameLike(userName);
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
    private List<CustomerModel> getIndirectStoreOrVipList(Long userId, PageRequest page, Byte childType, String userName) {
        List<BlcCustomer> customerList = blcCustomerMapper.getIndirectStoreOrVipListForManager(userId, childType, page, null, userName);
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
        List<BlcCustomer> customerList = blcCustomerMapper.getIndirectStoreOrVipListForManager(userId, childType, page, month, null);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }
}
