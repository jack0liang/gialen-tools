package com.gialen.tools.service.impl;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.DateTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.common.util.ThreadPoolManager;
import com.gialen.tools.dao.dto.OrderDetailDto;
import com.gialen.tools.dao.dto.OrderQueryDto;
import com.gialen.tools.dao.dto.UserIncomeDto;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.dao.entity.gialen.BlcCustomerExample;
import com.gialen.tools.dao.entity.gialen.RomaStore;
import com.gialen.tools.dao.entity.gialen.RomaStoreExample;
import com.gialen.tools.dao.entity.tools.ManagerAndDirector;
import com.gialen.tools.dao.entity.tools.ManagerAndDirectorExample;
import com.gialen.tools.dao.repository.gialen.BlcCustomerMapper;
import com.gialen.tools.dao.repository.gialen.BlcCustomerRelationMapper;
import com.gialen.tools.dao.repository.gialen.RomaStoreMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementDetailMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementMapper;
import com.gialen.tools.dao.repository.tools.ManagerAndDirectorMapper;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.business.CommunityBusiness;
import com.gialen.tools.service.exception.StoreManagerServiceException;
import com.gialen.tools.service.model.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 店经店董服务实现类
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

    @Autowired
    private ManagerAndDirectorMapper managerAndDirectorMapper;

    @Autowired
    private BlcCustomerMapper blcCustomerMapper;

    @Autowired
    private RomaStoreMapper romaStoreMapper;

    @Resource(name = "storeDirectorCommunityBiz")
    private CommunityBusiness storeDirectorCommunityBiz;

    @Resource(name = "storeManagerCommunityBiz")
    private CommunityBusiness storeManagerCommunityBiz;

    @Override
    public GLResponse<Long> login(String logigId, String password, UserTypeEnum userType) {
        if(StringUtils.isBlank(logigId) || StringUtils.isBlank(password)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "请输入账号密码");
        }
        if(null == userType) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "请选择用户类型");
        }
        ManagerAndDirector user = getManagerAndDirectorByLoginId(logigId);
        //首次登录
        if(user == null) {
            if(!logigId.equals(password)) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "账号或密码不正确!");
            }
            Long userId = null;
            if(UserTypeEnum.STORE_MANAGER.equals(userType)) {//店经
                userId = getUserIdByPhoneFromCustomer(logigId);
            } else if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {//店董
                userId = getUserIdByStoreCode(logigId);
            }
            if(userId == null) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "账号不存在");
            }
            addManagerAndDirector(logigId, userId, userType);
            return GLResponse.succ(userId);
        } else {
            if(!user.getPassword().equals(DigestUtils.md5Hex(password))) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "账号或密码不正确!");
            }
            if(user.getUserType() != userType.getType()) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "用户不存在，请检查用户类型");
            }
            return GLResponse.succ(user.getUserId());
        }
    }

    @Override
    public GLResponse<?> modifyPassword(Long userId, String password, String rePassword) {
        if(StringUtils.isBlank(password) || StringUtils.isBlank(rePassword)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "密码不能为空");
        }
        if(!password.equals(rePassword)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "两次输入的密码不一致");
        }
        ManagerAndDirectorExample example = new ManagerAndDirectorExample();
        ManagerAndDirectorExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        ManagerAndDirector user = new ManagerAndDirector();
        user.setPassword(DigestUtils.md5Hex(password));
        managerAndDirectorMapper.updateByExampleSelective(user, example);
        return GLResponse.succ(null);
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

    @Override
    public CommunityModel getCurMonthCommunity(Long userId, UserTypeEnum userType) {
        Integer month = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.countMonthCommunityData(userId, month);
        } else {
            return storeManagerCommunityBiz.countMonthCommunityData(userId, month);
        }
    }

    @Override
    public CommunityModel getCommunity(Long userId, UserTypeEnum userType) {
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.countTotalCommunityData(userId, null);
        } else {
            return storeManagerCommunityBiz.countTotalCommunityData(userId, null);
        }
    }

    @Override
    public PageResponse<CustomerModel> getUserChildList(Long userId, UserTypeEnum userType, ChildTypeEnum childType, PageRequest pageRequest, String userName) {
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.getChildList(userId, childType.getCode(), pageRequest, userName);
        } else {
            return storeManagerCommunityBiz.getChildList(userId, childType.getCode(), pageRequest, userName);
        }
    }

    @Override
    public PageResponse<CustomerModel> getCurMonthUserChildList(Long userId, UserTypeEnum userType, ChildTypeEnum childType, PageRequest pageRequest) {
        Integer month = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.getMonthChildList(userId, childType.getCode(), pageRequest, month);
        } else {
            return storeManagerCommunityBiz.getMonthChildList(userId, childType.getCode(), pageRequest, month);
        }
    }

    @Override
    public VipCommunityModel getNewVipNum(Long userId, UserTypeEnum userType) {
        Integer curMonth = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        Integer preMonth = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        Integer today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        VipCommunityModel vipCommunityModel = new VipCommunityModel();
        CommunityModel curMonthModel;
        CommunityModel preMonthModel;
        CommunityModel todayModel;
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            curMonthModel = storeDirectorCommunityBiz.countMonthVipData(userId, curMonth);
            preMonthModel = storeDirectorCommunityBiz.countMonthVipData(userId, preMonth);
            todayModel = storeDirectorCommunityBiz.countDayVipData(userId, today);
        } else {
            curMonthModel = storeManagerCommunityBiz.countMonthVipData(userId, curMonth);
            preMonthModel = storeManagerCommunityBiz.countMonthVipData(userId, preMonth);
            todayModel = storeManagerCommunityBiz.countDayVipData(userId, today);
        }
        vipCommunityModel.setCurMonthNewVipNum(curMonthModel != null ? curMonthModel.getMonthNewVipNum() : 0);
        vipCommunityModel.setPreMonthNewVipNum(preMonthModel != null ? preMonthModel.getMonthNewVipNum() : 0);
        vipCommunityModel.setTodayNewVipNum(todayModel != null ? todayModel.getTodayNewVipNum() : 0);
        return vipCommunityModel;
    }

    @Override
    public PageResponse<VipCommunityModel> getCurMonthNewVipList(Long userId, UserTypeEnum userType, PageRequest pageRequest, String storeName) {
        Integer month = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        } else {
            return storeManagerCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        }
    }

    @Override
    public PageResponse<VipCommunityModel> getPreMonthNewVipList(Long userId, UserTypeEnum userType, PageRequest pageRequest, String storeName) {
        Integer month = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        } else {
            return storeManagerCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        }
    }

    @Override
    public StoreActivityModel getStoreActivity(Long userId, UserTypeEnum userType) {
        if(UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.countMonthActivityStore(userId);
        } else {
            return storeManagerCommunityBiz.countMonthActivityStore(userId);
        }
    }

    @Override
    public PageResponse<StoreActivityDetailModel> getCurMonthActivityStoreList(Long userId, UserTypeEnum userType, Byte purchasedType, PageRequest pageRequest, String storeName) {
        int curMonth = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        return storeDirectorCommunityBiz.getMonthActivityStoreList(userId, userType.getType(), curMonth, purchasedType, pageRequest, storeName);
    }

    @Override
    public PageResponse<StoreActivityDetailModel> getPreMonthActivityStoreList(Long userId, UserTypeEnum userType, Byte purchasedType, PageRequest pageRequest, String storeName) {
        int preMonth = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        return storeDirectorCommunityBiz.getMonthActivityStoreList(userId, userType.getType(), preMonth, purchasedType, pageRequest, storeName);
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

        UserIncomeModel userIncomeModel = new UserIncomeModel();
        UserSalesModel userSalesModel = new UserSalesModel();
        try {
            //获取待收益
            BigDecimal toBeIncome = executeGetUserToBeIncome(userId, userType.getType());
            //获取月可用收益
            BigDecimal monthAvailableIncome = executeGetUserAvailableIncomeByMonth(userId, userType.getType(), month);
            //获取月总收益和月总销售额
            UserIncomeDto monthTotalIncome = executeGetUserTotalIncomeByMonth(userId, userType.getType(), month) ;
            //获取今日销售额
            BigDecimal todaySales = executeGetUserSalesByToday(userId, userType.getType(),today);
            //获取月销售额
            UserIncomeDto monthSales = executeGetUserSalesByMonth(userId, userType.getType(), month);

            userIncomeModel.setToBeIncome(toBeIncome != null ? toBeIncome : BigDecimal.ZERO);
            userIncomeModel.setMonthAvailableIncome(monthAvailableIncome != null ? monthAvailableIncome : BigDecimal.ZERO);
            userIncomeModel.setMonthTotalIncome(monthTotalIncome.getMonthTotalIncome() != null ? monthTotalIncome.getMonthTotalIncome() : BigDecimal.ZERO);
            userSalesModel.setMonthSales(monthTotalIncome.getMonthTotalSales() != null ? monthTotalIncome.getMonthTotalSales() : BigDecimal.ZERO);
            userSalesModel.setTodaySales(todaySales != null ? todaySales : BigDecimal.ZERO);
            userSalesModel.setMonthRefundSales(monthSales.getMonthRefundSales() != null ? monthSales.getMonthRefundSales() : BigDecimal.ZERO);
            BigDecimal monthSalesRate = monthSales.getMonthSalesRate() != null ? monthSales.getMonthSalesRate() : BigDecimal.ZERO;
            userSalesModel.setMonthSalseRate(BigDecimal.valueOf(DecimalCalculate.round(monthSalesRate.doubleValue(),4)));
            BigDecimal monthRefundRate = monthSales.getMonthRefundRate() != null ? monthSales.getMonthRefundRate() : BigDecimal.ZERO;
            userSalesModel.setMonthRefundRate(BigDecimal.valueOf(DecimalCalculate.round(monthRefundRate.doubleValue(),4)));
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
     * 查询用户待收益
     */
    private BigDecimal executeGetUserToBeIncome(final long userId, final byte userType) {
        return commissionSettlementMapper.getUserToBeIncome(userId, userType);
    }

    /**
     * 查询用户月总收益和月总销售额
     */
    private UserIncomeDto executeGetUserTotalIncomeByMonth(final long userId, final byte userType, final int month) {
        return commissionSettlementMapper.getUserTotalIncomeByMonth(userId, userType, month);
    }

    /**
     * 查询用户月销售数据
     */
    private UserIncomeDto executeGetUserSalesByMonth(final long userId, final byte userType, final int month) {
        return commissionSettlementMapper.getUserSalesByMonth(userId, userType, month);
    }

    /**
     * 查询用户月可用收益
     */
    private BigDecimal executeGetUserAvailableIncomeByMonth(final long userId, final byte userType, final int month) {
        return commissionSettlementMapper.getUserAvailableIncomeByMonth(userId, userType, month);
    }

    /**
     * 查询用户今日销售额
     */
    private BigDecimal executeGetUserSalesByToday(final long userId, final byte userType, final int day) {
        return commissionSettlementMapper.getUserTodaySales(userId, userType, day);
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

    /**
     * 旧库查询用户id
     * @param phone
     * @return
     */
    private Long getUserIdByPhoneFromCustomer(String phone) {
        BlcCustomerExample example = new BlcCustomerExample();
        BlcCustomerExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone).andUserTypeEqualTo(UserTypeEnum.STORE.getCode());
        List<BlcCustomer> customerList = blcCustomerMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(customerList)) {
            return null;
        }
        return customerList.get(0).getCustomerId();
    }

    /**
     * 旧库查询门店id
     * @param storeCode
     * @return
     */
    private Long getUserIdByStoreCode(String storeCode) {
        RomaStoreExample example = new RomaStoreExample();
        RomaStoreExample.Criteria criteria = example.createCriteria();
        criteria.andStoreCodeEqualTo(storeCode);
        List<RomaStore> storeList = romaStoreMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(storeList)) {
            return null;
        }
        return storeList.get(0).getStoreId().longValue();
    }

    /**
     * 根据登录账号从店经店董表查询用户
     * @param loginId
     * @return
     */
    private ManagerAndDirector getManagerAndDirectorByLoginId(String loginId) {
        ManagerAndDirectorExample example = new ManagerAndDirectorExample();
        ManagerAndDirectorExample.Criteria criteria = example.createCriteria();
        criteria.andLoginIdEqualTo(loginId);
        List<ManagerAndDirector> userList = managerAndDirectorMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    /**
     * 新增店经店董
     * @param loginId
     * @param userTypeEnum
     * @return
     */
    private Long addManagerAndDirector(String loginId, Long userId, UserTypeEnum userTypeEnum) {
        ManagerAndDirector user = new ManagerAndDirector();
        user.setLoginId(loginId);
        user.setPassword(DigestUtils.md5Hex(loginId));
        user.setUserId(userId);
        user.setUserType(userTypeEnum.getType());
        long id = (long) managerAndDirectorMapper.insertSelective(user);
        return id;
    }

}
