package com.gialen.tools.service.business;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.dao.dto.CommunityDto;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.dao.repository.customer.UserRelationMapper;
import com.gialen.tools.service.convertor.CustomerConvertor;
import com.gialen.tools.service.model.CommunityModel;
import com.gialen.tools.service.model.CustomerModel;
import com.gialen.tools.service.model.StoreActivityModel;
import com.gialen.tools.service.model.VipCommunityModel;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 店董社群数据业务类
 * @author lupeibo
 * @date 2019-07-23
 */
@Service("storeDirectorCommunityBiz")
public class StoreDirectorCommunityBusiness extends BaseCommunityBusiness {

    @Autowired
    private UserRelationMapper userRelationMapper;

    @Override
    public CommunityModel countTotalCommunityData(Long userId, String userName) {
        CommunityDto dto = userRelationMapper.countTotalNumForDirector(userId, userName);
        if(dto == null) {
            return null;
        }
        return Copier.copy(dto, new CommunityModel());
    }

    @Override
    public CommunityModel countMonthCommunityData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();

        //统计月新增店经数
        Integer monthNewStoreManagerNum = userRelationMapper.countMonthManagerNumForDirector(userId, month);
        model.setMonthNewStoreManagerNum(monthNewStoreManagerNum);

        //统计月新增店主数
        Integer monthNewStoreNum = userRelationMapper.countStoreNumForDirector(userId, month, null);
        model.setMonthNewStoreNum(monthNewStoreNum);

        //统计月新增vip数
        Integer monthNewVipNum = userRelationMapper.countVipNumForDirector(userId, month, null);
        model.setMonthNewVipNum(monthNewVipNum);

        //统计今日新增店主和vip数
        Integer today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        Integer todayNewStoreNum = userRelationMapper.countStoreNumForDirector(userId, null, today);
        model.setTodayNewStoreNum(todayNewStoreNum);
        Integer todayNewVipNum = userRelationMapper.countVipNumForDirector(userId, null, today);
        model.setTodayNewVipNum(todayNewVipNum);

        //统计所有下级人数
        CommunityDto totalDto = userRelationMapper.countTotalNumForDirector(userId, null);
        model.setTotalNum((totalDto != null ? totalDto.getTotalVipNum() : 0)
                + (totalDto != null ? totalDto.getTotalStoreNum() : 0)
                + (totalDto != null ? totalDto.getTotalStoreManagerNum() : 0));
        return model;
    }

    @Override
    public PageResponse<CustomerModel> getChildList(Long userId, Byte childType, PageRequest pageRequest, String userName) {
        CommunityModel communityModel = countTotalCommunityData(userId, userName);

        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            totalCount = communityModel != null ? communityModel.getTotalStoreManagerNum() : 0L;
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType || ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            totalCount = communityModel != null ? communityModel.getTotalStoreNum() : 0L;
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            totalCount = communityModel != null ? communityModel.getTotalVipNum() : 0L;
        }
        if(totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<CustomerModel> modelList = getChildListForStoreDirector(userId, pageRequest, childType, userName);
        return PageResponse.success(modelList,pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public PageResponse<CustomerModel> getMonthChildList(Long userId, Byte childType, PageRequest pageRequest, Integer month) {
        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            Integer monthNewStoreManagerNum = userRelationMapper.countMonthManagerNumForDirector(userId, month);
            totalCount = monthNewStoreManagerNum != null ?  monthNewStoreManagerNum.longValue() : 0L;
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType || ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            Integer monthNewStoreNum = userRelationMapper.countStoreNumForDirector(userId, month, null);
            totalCount = monthNewStoreNum != null ? monthNewStoreNum.longValue() : 0L;
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            Integer monthNewVipNum = userRelationMapper.countVipNumForDirector(userId, month, null);
            totalCount = monthNewVipNum != null ? monthNewVipNum.longValue() : 0L;
        }
        if(totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }

        List<CustomerModel> modelList = getMonthChildListForStoreDirector(userId, pageRequest, childType, month);
        return PageResponse.success(modelList,pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public CommunityModel countMonthVipData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        Integer monthNewVipNum = userRelationMapper.countVipNumForDirector(userId, month, null);
        model.setMonthNewVipNum(monthNewVipNum != null ? monthNewVipNum : 0);
        return model;
    }

    @Override
    public CommunityModel countDayVipData(Long userId, Integer day) {
        CommunityModel model = new CommunityModel();
        Integer dayNewVipNum = userRelationMapper.countVipNumForDirector(userId, null, day);
        model.setTodayNewVipNum(null != dayNewVipNum ? dayNewVipNum : 0);
        return model;
    }

    @Override
    public PageResponse<VipCommunityModel> getMonthNewVipList(Long userId, PageRequest pageRequest, Integer month, String storeName) {
        Long totalCount = userRelationMapper.countMonthHasNewVipStoreNumForDirector(userId, month, storeName);
        if(totalCount == null || totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        List<CommunityDto> communityDtoList = userRelationMapper.getMonthNewVipListForDirector(userId, today, month, pageRequest, storeName);

        return PageResponse.success(convertCommunityDtoToVipCommunityModel(communityDtoList), pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public StoreActivityModel countMonthActivityStore(Long directorId) {
        int curMonth = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        return countActivityOrSilenceStoreTotal(directorId, UserTypeEnum.STORE_DIRECTOR.getType(), curMonth);
    }

    /**
     * 查询店董的全部下级会员列表
     * @param storeDirectorId
     * @param page
     * @param childType
     * @return
     */
    private List<CustomerModel> getChildListForStoreDirector(Long storeDirectorId, PageRequest page, Byte childType, String userName) {
        List<BlcCustomer> customerList = userRelationMapper.getUserChildListForDirector(storeDirectorId, childType, page, userName);
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
        List<BlcCustomer> customerList = userRelationMapper.getMonthUserChildListForDirector(storeDirectorId, childType, month, page);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }

}
