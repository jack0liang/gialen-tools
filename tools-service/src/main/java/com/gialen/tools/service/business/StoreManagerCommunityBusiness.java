package com.gialen.tools.service.business;

import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.dao.dto.CommunityDto;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.dao.entity.gialen.RomaImportSuperCustomerRecord;
import com.gialen.tools.dao.repository.customer.UserRelationMapper;
import com.gialen.tools.dao.repository.gialen.BlcCustomerMapper;
import com.gialen.tools.dao.repository.gialen.BlcCustomerRelationMapper;
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

    @Autowired
    private UserRelationMapper userRelationMapper;

    @Override
    public CommunityModel countTotalCommunityData(Long userId, String userName) {
        CommunityModel model = new CommunityModel();
        CommunityDto totalDto = userRelationMapper.countTotalNumForManager(userId, userName);
        model.setTotalVipNum(totalDto != null ? totalDto.getTotalVipNum() : 0);
        model.setTotalIndirectStoreNum(totalDto != null ? totalDto.getTotalIndirectStoreNum() : 0);
        model.setTotalDirectStoreNum(totalDto != null ? totalDto.getTotalDirectStoreNum() : 0);
        model.setTotalStoreNum(model.getTotalIndirectStoreNum() + model.getTotalDirectStoreNum());
        model.setTotalStoreManagerNum(totalDto != null ? totalDto.getTotalStoreManagerNum() : 0);
        model.setTotalNum(model.getTotalVipNum() + model.getTotalStoreNum() + model.getTotalStoreManagerNum());
        return model;
    }

    @Override
    public CommunityModel countMonthCommunityData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));

        //vip
        Integer monthNewVipNum = userRelationMapper.countVipNumForManager(userId, month, null);
        model.setMonthNewVipNum(monthNewVipNum);
        Integer todayNewVipNum = userRelationMapper.countVipNumForManager(userId, null, today);
        model.setTodayNewVipNum(todayNewVipNum);

        //店主
        CommunityDto todayStoreDto = userRelationMapper.countStoreNumForManager(userId, null, today);
        model.setTodayNewStoreNum(todayStoreDto != null ? todayStoreDto.getDayNewStoreNum() : 0);
        CommunityDto monthStoreDto = userRelationMapper.countStoreNumForManager(userId, month, null);
        model.setMonthNewIndirectStoreNum(monthStoreDto != null ? monthStoreDto.getMonthNewIndirectStoreNum() : 0);
        model.setMonthNewDirectStoreNum(monthStoreDto != null ? monthStoreDto.getMonthNewDirectStoreNum() : 0);
        model.setMonthNewStoreNum(model.getMonthNewIndirectStoreNum() + model.getMonthNewDirectStoreNum());

        //店经
        Integer monthNewStoreManagerNum = userRelationMapper.countMonthManagerNumForManager(userId, month);
        model.setMonthNewStoreManagerNum(monthNewStoreManagerNum);

        CommunityModel totalModel = countTotalCommunityData(userId, null);
        model.setTotalNum(totalModel.getTotalNum());
        BlcCustomer customer = getCustomer(userId);
        if(customer != null && customer.getIsTempSuperCustomer() == (byte)1) {
            //实习店经查询到期时间
            RomaImportSuperCustomerRecord record = getTempSuperCustomerRecord(userId);
            model.setCountDown(record == null ? null : calCountDown(record.getExpireDate()));
        }
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
        List<CustomerModel> modelList = getChildListForManager(userId, pageRequest, childType, userName);
        return PageResponse.success(modelList, pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public PageResponse<CustomerModel> getMonthChildList(Long userId, Byte childType, PageRequest pageRequest, Integer month) {
        long totalCount = 0L;
        if(ChildTypeEnum.STORE_MANAGER.getCode() == childType) {
            Integer monthNewStoreManagerNum = userRelationMapper.countMonthManagerNumForManager(userId, month);
            totalCount = monthNewStoreManagerNum != null ? monthNewStoreManagerNum.longValue() : 0L;
        } else if (ChildTypeEnum.DIRECT_STORE.getCode() == childType) {
            CommunityDto monthStoreDto = userRelationMapper.countStoreNumForManager(userId, month, null);
            Integer monthNewDirectStoreNum = monthStoreDto != null ? monthStoreDto.getMonthNewDirectStoreNum() : null;
            totalCount = monthNewDirectStoreNum != null ? monthNewDirectStoreNum.longValue() : 0L;
        } else if (ChildTypeEnum.INDIRECT_STORE.getCode() == childType) {
            CommunityDto monthStoreDto = userRelationMapper.countStoreNumForManager(userId, month, null);
            Integer monthNewIndirectStoreNum = monthStoreDto != null ? monthStoreDto.getMonthNewIndirectStoreNum() : null;
            totalCount = monthNewIndirectStoreNum != null ? monthNewIndirectStoreNum.longValue() : 0L;
        } else if (ChildTypeEnum.VIP.getCode() == childType) {
            Integer monthNewVipNum = userRelationMapper.countVipNumForManager(userId, month, null);
            totalCount = monthNewVipNum != null ? monthNewVipNum.longValue() : 0L;
        }
        if(totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<CustomerModel> modelList = getMonthChildListForManager(userId, pageRequest, childType, month);
        return PageResponse.success(modelList, pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public CommunityModel countMonthVipData(Long userId, Integer month) {
        CommunityModel model = new CommunityModel();
        Integer monthNewVipNum = userRelationMapper.countVipNumForManager(userId, month, null);
        model.setMonthNewVipNum(monthNewVipNum != null ? monthNewVipNum : 0);
        return model;
    }

    @Override
    public CommunityModel countDayVipData(Long userId, Integer day) {
        CommunityModel model = new CommunityModel();
        Integer dayNewVipNum = userRelationMapper.countVipNumForManager(userId, null, day);
        model.setTodayNewVipNum(dayNewVipNum != null ? dayNewVipNum : 0);
        return model;
    }

    @Override
    public PageResponse<VipCommunityModel> getMonthNewVipList(Long userId, PageRequest pageRequest, Integer month, String storeName) {
        Long totalCount = userRelationMapper.countMonthHasNewVipStoreNumForManager(userId, month, storeName);
        if(totalCount == null || totalCount <= 0L) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        List<CommunityDto> communityDtoList = userRelationMapper.getMonthNewVipListForManager(userId, today, month, pageRequest, storeName);

        return PageResponse.success(convertCommunityDtoToVipCommunityModel(communityDtoList), pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    @Override
    public StoreActivityModel countMonthActivityStore(Long userId) {
        StoreActivityModel model = new StoreActivityModel();
        int curMonth = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        model = countActivityOrSilenceStoreTotal(userId, UserTypeEnum.STORE_MANAGER.getType(), curMonth, model);
        return model;
    }

    /**
     * 查询店经的下级用户列表
     * @param userId
     * @param page
     * @param childType
     * @return
     */
    private List<CustomerModel> getChildListForManager(Long userId, PageRequest page, Byte childType, String userName) {
        List<BlcCustomer> customerList = userRelationMapper.getUserChildListForManager(userId, childType, page, userName);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }

    /**
     * 查询店经的月新增下级用户列表
     * @param userId
     * @param page
     * @param childType
     * @param month
     * @return
     */
    private List<CustomerModel> getMonthChildListForManager(Long userId, PageRequest page, Byte childType, Integer month) {
        List<BlcCustomer> customerList = userRelationMapper.getMonthUserChildListForManager(userId, childType, month, page);
        return CustomerConvertor.convertCustomerListToModelList(customerList);
    }
}
