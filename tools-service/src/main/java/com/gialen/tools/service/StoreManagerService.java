package com.gialen.tools.service;

import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.service.model.*;

import java.util.List;

/**
 * 店经店董服务接口
 */
public interface StoreManagerService {

    /**
     * 店经店董登录
     * @param logigId
     * @param password
     * @param userType
     * @return
     */
    GLResponse<Long> login(String logigId, String password, UserTypeEnum userType);

    /**
     * 修改密码
     * @param userId
     * @param password
     * @param rePassword
     * @return
     */
    GLResponse<?> modifyPassword(Long userId, String password, String rePassword);

    /**
     * 查询用户当月战绩数据
     * @param userId
     * @param userType
     * @return
     */
    GLResponse<UserAchievementModel> getCurMonthUserAchievement(Long userId, UserTypeEnum userType);

    /**
     * 查询用户上月战绩数据
     * @param userId
     * @param userType
     * @return
     */
    GLResponse<UserAchievementModel> getPreMonthUserAchievement(Long userId, UserTypeEnum userType);

    /**
     * 查询用户当月订单明细列表
     * @param userId
     * @param userType
     * @param subOrderStatus
     * @param pageRequest
     * @return
     */
    GLResponse<PageResponse<OrderDetailModel>> getCurMonthUserOrderList(Long userId, UserTypeEnum userType, Byte subOrderStatus, PageRequest pageRequest);

    /**
     * 查询用户上月订单明细列表
     * @param userId
     * @param userType
     * @param subOrderStatus
     * @param pageRequest
     * @return
     */
    GLResponse<PageResponse<OrderDetailModel>> getPreMonthUserOrderList(Long userId, UserTypeEnum userType, Byte subOrderStatus, PageRequest pageRequest);

    /**
     * 查询店经店董当月社群数据
     * @param userId
     * @param userType
     * @return
     */
    CommunityModel getCurMonthCommunity(Long userId, UserTypeEnum userType);

    /**
     * 查询店经店董全部社群数据
     * @param userId
     * @param userType
     * @return
     */
    CommunityModel getCommunity(Long userId, UserTypeEnum userType);

    /**
     * 查询店经店董全部会员明细列表
     * @param userId
     * @param userType
     * @param childType
     * @param pageRequest
     * @return
     */
    PageResponse<CustomerModel> getUserChildList(Long userId, UserTypeEnum userType, ChildTypeEnum childType, PageRequest pageRequest);

    /**
     * 查询店经店董当月会员明细列表
     * @param userId
     * @param userType
     * @param childType
     * @param pageRequest
     * @return
     */
    PageResponse<CustomerModel> getCurMonthUserChildList(Long userId, UserTypeEnum userType, ChildTypeEnum childType, PageRequest pageRequest);


    VipCommunityModel getNewVipNum(Long userId, UserTypeEnum userType);

    PageResponse<VipCommunityModel> getCurMonthNewVipList(Long userId, UserTypeEnum userType, PageRequest pageRequest);

    PageResponse<VipCommunityModel> getPreMonthNewVipList(Long userId, UserTypeEnum userType, PageRequest pageRequest);
}
