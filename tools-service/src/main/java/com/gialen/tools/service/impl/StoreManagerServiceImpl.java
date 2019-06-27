package com.gialen.tools.service.impl;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.tools.common.enums.DateTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.common.util.ThreadPoolManager;
import com.gialen.tools.dao.dto.OrderDetailDto;
import com.gialen.tools.dao.dto.OrderQueryDto;
import com.gialen.tools.dao.dto.UserIncomeDto;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementDetailMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementMapper;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.exception.StoreManagerServiceException;
import com.gialen.tools.service.model.OrderDetailModel;
import com.gialen.tools.service.model.UserAchievementModel;
import com.gialen.tools.service.model.UserIncomeModel;
import com.gialen.tools.service.model.UserSalesModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author lupeibo
 * @date 2019-06-25
 */
@Slf4j
@Service
public class StoreManagerServiceImpl implements StoreManagerService {

    @Autowired
    private CommissionSettlementMapper commissionSettlementMapper;

    @Autowired
    private CommissionSettlementDetailMapper commissionSettlementDetailMapper;

    @Override
    public GLResponse<Long> login(String logigId, String password, UserTypeEnum userType) {
        if(StringUtils.isBlank(logigId) || StringUtils.isBlank(password)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "请输入账号密码");
        }
        if(null == userType) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "请选择用户类型");
        }
        //todo:根据手机号前往数据库查询用户密码，若无数据，则为初始密码，与登录账号一致
        //db查用户密码
        //ManagerAndDirector managerAndDirector = null;
        if(true) {
            //比较输入密码与数据库密码是否一致，采用md5加密保存
        }
        //初始密码登录
        if(!logigId.equals(password)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "账号或密码不正确！");
        }
        //todo:通过手机号查询用户id，返回前端
        Long userId = 1l;
        return GLResponse.succ(userId);
    }

    @Override
    public GLResponse<UserAchievementModel> getCurMonthUserAchievement(Long userId, UserTypeEnum userType) {
        return getUserAchievement(userId, userType, DateTypeEnum.CUR_MONTH);
    }

    @Override
    public GLResponse<UserAchievementModel> getPreMonthUserAchievement(Long userId, UserTypeEnum userType) {
        return getUserAchievement(userId, userType, DateTypeEnum.PRE_MONTH);
    }

    @Override
    public GLResponse<PageResponse<OrderDetailModel>> getCurMonthUserOrderList(Long userId, UserTypeEnum userType, Byte subOrderStatus, PageRequest pageRequest) {
        Integer month = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        return GLResponse.succ(getOrderDetailPageByMonth(userId, userType, month, subOrderStatus, pageRequest));
    }

    @Override
    public GLResponse<PageResponse<OrderDetailModel>> getPreMonthUserOrderList(Long userId, UserTypeEnum userType, Byte subOrderStatus, PageRequest pageRequest) {
        Integer month = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        return GLResponse.succ(getOrderDetailPageByMonth(userId, userType, month, subOrderStatus, pageRequest));
    }

    /**
     * 获取店经店董的战绩数据
     * @param userId
     * @param userType
     * @param dateTypeEnum
     * @return
     */
    private GLResponse<UserAchievementModel> getUserAchievement(Long userId, UserTypeEnum userType, DateTypeEnum dateTypeEnum) {
        int month = 0;
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        if(DateTypeEnum.CUR_MONTH.equals(dateTypeEnum)) {
            month = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        } else if(DateTypeEnum.PRE_MONTH.equals(dateTypeEnum)) {
            month = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        }

        //获取待收益
        Future<BigDecimal> toBeIncomeFuture = executeGetUserToBeIncome(userId, userType.getType());

        //获取月可用收益
        Future<BigDecimal> monthAvailableIncomeFuture = executeGetUserAvailableIncomeByMonth(userId, userType.getType(), month);

        //获取月总收益和月总销售额
        Future<UserIncomeDto> monthTotalIncomeFuture = executeGetUserTotalIncomeByMonth(userId, userType.getType(), month) ;

        //获取今日销售额
        Future<BigDecimal> todaySalesFuture = executeGetUserSalesByToday(userId, userType.getType(),today);

        UserIncomeModel userIncomeModel = new UserIncomeModel();
        UserSalesModel userSalesModel = new UserSalesModel();
        try {
            if(toBeIncomeFuture != null) {
                userIncomeModel.setToBeIncome(toBeIncomeFuture.get() != null ? toBeIncomeFuture.get() : BigDecimal.ZERO);
            }
            if(monthAvailableIncomeFuture != null) {
                userIncomeModel.setMonthAvailableIncome(monthAvailableIncomeFuture.get() != null ? monthAvailableIncomeFuture.get() : BigDecimal.ZERO);
            }
            if(monthTotalIncomeFuture != null) {
                userIncomeModel.setMonthTotalIncome(monthTotalIncomeFuture.get().getMonthTotalIncome() != null ?
                        monthTotalIncomeFuture.get().getMonthTotalIncome() : BigDecimal.ZERO);
                userSalesModel.setMonthSales(monthTotalIncomeFuture.get().getMonthTotalSales() != null ?
                        monthTotalIncomeFuture.get().getMonthTotalSales() : BigDecimal.ZERO);
            }
            if(todaySalesFuture != null) {
                userSalesModel.setTodaySales(todaySalesFuture.get() != null ? todaySalesFuture.get() : BigDecimal.ZERO);
            }
        } catch (Exception e) {
            log.error("getUserAchievement exception : {} \nuserId = {}, userType = {}, month = {}",
                    e.getMessage(), userId, userType.getType(), month);
        }

        UserAchievementModel userAchievementModel = new UserAchievementModel();
        userAchievementModel.setIncomeModel(userIncomeModel);
        userAchievementModel.setSalesModel(userSalesModel);
        return GLResponse.succ(userAchievementModel);
    }

    /**
     * 开启线程查询用户待收益
     */
    private Future<BigDecimal> executeGetUserToBeIncome(final long userId, final byte userType) {
        return ThreadPoolManager.getsInstance().submit(() -> commissionSettlementMapper.getUserToBeIncome(userId, userType));
    }

    /**
     * 开启线程查询用户月总收益和月总销售额
     */
    private Future<UserIncomeDto> executeGetUserTotalIncomeByMonth(final long userId, final byte userType, final int month) {
        return ThreadPoolManager.getsInstance().submit(() -> commissionSettlementMapper.getUserTotalIncomeByMonth(userId, userType, month));
    }

    /**
     * 开启线程查询用户月可用收益
     */
    private Future<BigDecimal> executeGetUserAvailableIncomeByMonth(final long userId, final byte userType, final int month) {
        return ThreadPoolManager.getsInstance().submit(() -> commissionSettlementMapper.getUserAvailableIncomeByMonth(userId, userType, month));
    }

    /**
     * 开启线程查询用户今日销售额
     */
    private Future<BigDecimal> executeGetUserSalesByToday(final long userId, final byte userType, final int day) {
        return ThreadPoolManager.getsInstance().submit(() -> commissionSettlementMapper.getUserTodaySales(userId, userType, day));
    }

    /**
     * 按月获取用户的订单明细列表
     * @param userId
     * @param userTypeEnum
     * @param month yyyyMM
     * @return
     */
    public List<OrderDetailModel> getOrderDetailListByMonth(Long userId, UserTypeEnum userTypeEnum,
                                                            Integer month, Byte subOrderStatus, PageRequest pageRequest) {
        List<OrderDetailDto> orderDetailDtoList;
        OrderQueryDto queryDto = buildOrderQueryDto(month, userId, userTypeEnum.getType(), subOrderStatus);
        try {
            orderDetailDtoList = commissionSettlementDetailMapper.
                    getOrderDetailListByMonth(queryDto, pageRequest.getOffset(), pageRequest.getLimit());
        } catch (Exception e) {
            log.error("getOrderDetailListByMonth exception : {} \nuserId = {}, userType = {}, month = {}, subOrderStatus = {}, offset={}, limit={}",
                    e.getMessage(), userId, userTypeEnum.getType(), month, subOrderStatus, pageRequest.getOffset(), pageRequest.getLimit());
            throw new StoreManagerServiceException("按月获取用户订单明细列表异常", e);
        }
        if(CollectionUtils.isEmpty(orderDetailDtoList)) {
            return Collections.emptyList();
        }
        List<OrderDetailModel> orderDetailModelList = Lists.newArrayListWithCapacity(orderDetailDtoList.size());
        orderDetailDtoList.forEach(orderDetailDto -> {
            orderDetailModelList.add(Copier.copy(orderDetailDto, new OrderDetailModel()));
        });
        return orderDetailModelList;
    }

    /**
     * 按月获取用户的订单明细分页列表
     * @param userId
     * @param userTypeEnum
     * @param month
     * @param pageRequest
     * @return
     */
    public PageResponse<OrderDetailModel> getOrderDetailPageByMonth(Long userId, UserTypeEnum userTypeEnum,
                                                                    Integer month, Byte subOrderStatus, PageRequest pageRequest) {
        Long totalCount;
        OrderQueryDto queryDto = buildOrderQueryDto(month, userId, userTypeEnum.getType(), subOrderStatus);
        try {
            totalCount = commissionSettlementDetailMapper.countOrderDetailByMonth(queryDto);
        } catch (Exception e) {
            log.error("countOrderDetailByMonth exception : {} \nuserId = {}, userType = {}, month = {}, subOrderStatus = {}",
                    e.getMessage(), userId, userTypeEnum.getType(), month, subOrderStatus);
            throw new StoreManagerServiceException("统计用户月订单明细总记录数异常", e);
        }
        if(totalCount == null || totalCount <= 0) {
            return PageResponse.empty(pageRequest.getPage(), pageRequest.getLimit());
        }
        List<OrderDetailModel> orderDetailModelList = getOrderDetailListByMonth(userId, userTypeEnum, month, subOrderStatus, pageRequest);
        return PageResponse.success(orderDetailModelList, pageRequest.getPage(), pageRequest.getLimit(), totalCount);
    }

    private OrderQueryDto buildOrderQueryDto(Integer month, Long userId, Byte userType, Byte subOrderStatus) {
        OrderQueryDto dto = new OrderQueryDto();
        dto.setMonth(month);
        dto.setUserId(userId);
        dto.setUserType(userType);
        dto.setSubOrderStatus(subOrderStatus);
        return dto;
    }

}
