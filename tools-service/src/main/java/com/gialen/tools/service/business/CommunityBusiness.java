package com.gialen.tools.service.business;

import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.service.convertor.CustomerConvertor;
import com.gialen.tools.service.model.CommunityModel;
import com.gialen.tools.service.model.CustomerModel;
import com.gialen.tools.service.model.VipCommunityModel;

import java.util.List;

public interface CommunityBusiness {

    /**
     * 统计用户总社群数据
     * @param userId
     * @return
     */
    CommunityModel countTotalCommunityData(Long userId);

    /**
     * 统计月社群数据
     * @param userId
     * @param month
     * @return
     */
    CommunityModel countMonthCommunityData(Long userId, Integer month);

    /**
     * 查询所有下级会员列表
     * @param userId
     * @param childType
     * @param pageRequest
     * @return
     */
    PageResponse<CustomerModel> getChildList(Long userId, Byte childType, PageRequest pageRequest);

    /**
     * 查询月新增会员列表
     * @param userId
     * @param childType
     * @param pageRequest
     * @param month
     * @return
     */
    PageResponse<CustomerModel> getMonthChildList(Long userId, Byte childType, PageRequest pageRequest, Integer month);

    /**
     * 统计vip月数据
     * @param userId
     * @param month
     * @return
     */
    CommunityModel countMonthVipData(Long userId, Integer month);

    /**
     * 统计vip每日数据
     * @param userId
     * @param day
     * @return
     */
    CommunityModel countDayVipData(Long userId, Integer day);

    /**
     * 查询月新增vip列表
     * @param userId
     * @param pageRequest
     * @param month
     * @return
     */
    PageResponse<VipCommunityModel> getMonthNewVipList(Long userId, PageRequest pageRequest, Integer month);
}
