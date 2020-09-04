package com.gialen.tools.service.impl;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.common.utils.DateTools;
import com.gialen.common.utils.DecimalCalculate;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.DateTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.dao.dto.*;
import com.gialen.tools.dao.entity.customer.*;
import com.gialen.tools.dao.entity.tools.ManagerAndDirector;
import com.gialen.tools.dao.entity.tools.ManagerAndDirectorExample;
import com.gialen.tools.dao.repository.customer.StoreMapper;
import com.gialen.tools.dao.repository.customer.UserMapper;
import com.gialen.tools.dao.repository.customer.WithdrawTlMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementDetailMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementMapper;
import com.gialen.tools.dao.repository.tools.ManagerAndDirectorMapper;
import com.gialen.tools.integration.RpcTlMemberService;
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
import java.util.stream.Collectors;

/**
 * 店经店董服务实现类
 *
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

    @Resource(name = "storeDirectorCommunityBiz")
    private CommunityBusiness storeDirectorCommunityBiz;

    @Resource(name = "storeManagerCommunityBiz")
    private CommunityBusiness storeManagerCommunityBiz;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private RpcTlMemberService rpcTlMemberService;

    @Autowired
    private WithdrawTlMapper withdrawTlMapper;

    @Override
    public GLResponse<Long> login(String logigId, String password, UserTypeEnum userType) {
        if (StringUtils.isBlank(logigId) || StringUtils.isBlank(password)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "请输入账号密码");
        }
        if (null == userType) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "请选择用户类型");
        }
        ManagerAndDirector user = getManagerAndDirectorByLoginId(logigId);
        //首次登录
        if (user == null) {
            if (!logigId.equals(password)) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "账号或密码不正确!");
            }
            Long userId = null;
            if (UserTypeEnum.STORE_MANAGER.equals(userType)) {//店经
                userId = getUserIdByPhoneFromCustomer(logigId);
            } else if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {//店董
                userId = getUserIdByStoreCode(logigId);
            }
            if (userId == null) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "账号不存在");
            }
            addManagerAndDirector(logigId, userId, userType);
            return GLResponse.succ(userId);
        } else {
            if (!user.getPassword().equals(DigestUtils.md5Hex(password))) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "账号或密码不正确!");
            }
            if (user.getUserType() != userType.getType()) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "用户不存在，请检查用户类型");
            }
            return GLResponse.succ(user.getUserId());
        }
    }

    @Override
    public GLResponse<?> modifyPassword(Long userId, String password, String rePassword) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(rePassword)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "密码不能为空");
        }
        if (!password.equals(rePassword)) {
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
    public GLResponse<?> resetPassword(String loginId) {
        if (StringUtils.isBlank(loginId)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "请输入登录账号");
        }
        ManagerAndDirectorExample example = new ManagerAndDirectorExample();
        example.createCriteria().andLoginIdEqualTo(loginId);
        managerAndDirectorMapper.deleteByExample(example);
        return GLResponse.succ("密码重置成功");
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
    public GLResponse<UserAchievementModel> getHistoryMonthUserAchievement(Long userId, Integer month) {
        return getHistoryAchievement(userId, month);
    }

    @Override
    public GLResponse<List<MonthModel>> getAchievementMonthList() {
        List<MonthModel> monthModelList = Lists.newArrayListWithExpectedSize(6);
        Date now = new Date();
        for (int i = 2; i < 8; i++) {
            int month = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(now, -i), "yyyyMM"));
            String monthStr = DateFormatUtils.format(DateUtils.addMonths(now, -i), "yyyy年M月");
            MonthModel monthModel = new MonthModel();
            monthModel.setMonth(month);
            monthModel.setMonthStr(monthStr);
            monthModelList.add(monthModel);
        }
        return GLResponse.succ(monthModelList);
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
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.countMonthCommunityData(userId, month);
        } else {
            return storeManagerCommunityBiz.countMonthCommunityData(userId, month);
        }
    }

    @Override
    public CommunityModel getCommunity(Long userId, UserTypeEnum userType) {
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.countTotalCommunityData(userId, null);
        } else {
            return storeManagerCommunityBiz.countTotalCommunityData(userId, null);
        }
    }

    @Override
    public PageResponse<CustomerModel> getUserChildList(Long userId, UserTypeEnum userType, ChildTypeEnum childType, PageRequest pageRequest, String userName) {
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.getChildList(userId, childType.getCode(), pageRequest, userName);
        } else {
            return storeManagerCommunityBiz.getChildList(userId, childType.getCode(), pageRequest, userName);
        }
    }

    @Override
    public PageResponse<CustomerModel> getCurMonthUserChildList(Long userId, UserTypeEnum userType, ChildTypeEnum childType, PageRequest pageRequest) {
        Integer month = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
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
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
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
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        } else {
            return storeManagerCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        }
    }

    @Override
    public PageResponse<VipCommunityModel> getPreMonthNewVipList(Long userId, UserTypeEnum userType, PageRequest pageRequest, String storeName) {
        Integer month = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
            return storeDirectorCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        } else {
            return storeManagerCommunityBiz.getMonthNewVipList(userId, pageRequest, month, storeName);
        }
    }

    @Override
    public StoreActivityModel getStoreActivity(Long userId, UserTypeEnum userType) {
        if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
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

    @Override
    public StoreUserWithDrawRespModel getStoreUserWithdrawList(Long userId, PageRequest pageRequest) {
        StoreUserWithDrawRespModel storeUserWithDrawRespModel = new StoreUserWithDrawRespModel(pageRequest.getPage(), pageRequest.getLimit());

        WithDrawStatusTypeDto drawStatusTypeDto = withdrawTlMapper.selectGroupByStatus(userId);
        if (null != drawStatusTypeDto) {
            storeUserWithDrawRespModel.setAccountArrived(drawStatusTypeDto.getAccountArrived());
        }

        //查询自动结算额
        storeUserWithDrawRespModel.setAccountNotArrived(rpcTlMemberService.getStoreMgrBalanceAmount(userId));

        long count = withdrawTlMapper.countByUserIdAndOther(userId);
        if (count == 0) {
            return storeUserWithDrawRespModel;
        }

        List<StoreUserWithDrawRespModel.WithDrawModel> withdrawTlList = withdrawTlMapper.selectByUserIdAndOther(userId,pageRequest.getOffset(),pageRequest.getLimit()).stream().map(withdrawTl -> {
            StoreUserWithDrawRespModel.WithDrawModel withDrawModel = Copier.copy(withdrawTl, new StoreUserWithDrawRespModel.WithDrawModel());
            withDrawModel.setCreateTime(DateTools.date2String(withdrawTl.getCreateTime(), "yyy/MM/dd HH:mm"));

            String cardno = withdrawTl.getBankCardNo();
            if (StringUtils.isNotEmpty(withdrawTl.getBankCardNo()) && withdrawTl.getBankCardNo().length() > 4) {
                cardno = cardno.substring(0, 4) + " **** **** " + cardno.substring(cardno.length() - 4);
            }
            withDrawModel.setBankCardNo(cardno);

            switch (withdrawTl.getProgress()) {
                case 0:
                    withDrawModel.setStatus("成功");
                    break;
                case 1:
                    withDrawModel.setStatus("提现中");
                    break;
                case 2:
                    withDrawModel.setStatus("失败");
                    break;
            }
            return withDrawModel;
        }).collect(Collectors.toList());
        storeUserWithDrawRespModel.setWithdrawList(PageResponse.success(withdrawTlList, pageRequest.getPage(), pageRequest.getLimit(), count));
        return storeUserWithDrawRespModel;
    }

    /**
     * 获取店经店董的战绩数据
     *
     * @param userId
     * @param userType
     * @param dateTypeEnum
     * @return
     */
    private GLResponse<UserAchievementModel> getUserAchievement(Long userId, UserTypeEnum userType, DateTypeEnum dateTypeEnum) {
        int month = 0;
        int today = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        if (DateTypeEnum.CUR_MONTH.equals(dateTypeEnum)) {
            month = Integer.parseInt(DateFormatUtils.format(new Date(), "yyyyMM"));
        } else if (DateTypeEnum.PRE_MONTH.equals(dateTypeEnum)) {
            month = Integer.parseInt(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyyMM"));
        }

        UserIncomeModel userIncomeModel = new UserIncomeModel();
        UserSalesModel userSalesModel = new UserSalesModel();
        StoreIncomeModel storeIncomeModel = new StoreIncomeModel();
        UserIncomeModel managerIncomeModel = new UserIncomeModel();
        UserIncomeModel keeperIncomeModel = new UserIncomeModel();
        BaseBrandModel ownerBrandModel = new BaseBrandModel();
        BaseBrandModel circulatedBrandModel = new BaseBrandModel();

        BigDecimal balanceMoney = BigDecimal.ZERO;
        BigDecimal manualSettlementAmount = BigDecimal.ZERO;

        try {
            //获取待收益
            BigDecimal toBeIncome = executeGetUserToBeIncome(userId, userType.getType());
            //获取月可用收益
            BigDecimal monthAvailableIncome = executeGetUserAvailableIncomeByMonth(userId, userType.getType(), month);
            //获取月总收益和月总销售额
            UserIncomeDto monthTotalIncome = executeGetUserTotalIncomeByMonth(userId, userType.getType(), month);
            //获取今日销售额
            BigDecimal todaySales = executeGetUserSalesByToday(userId, userType.getType(), today, (byte) 0);
            //获取月销售额
            UserIncomeDto monthSales = executeGetUserSalesByMonth(userId, userType.getType(), month, (byte) 0);

            if (UserTypeEnum.STORE_DIRECTOR.equals(userType)) {
                //获取门店名称
                storeIncomeModel.setStoreName(getStoreNameById(userId));

                StoreIncomeDto monthStoreAvailableIncome = getStoreAvailableIncomeByMonth(userId, month, (byte) 0);
                StoreIncomeDto storeToBeIncome = getToBeIncome(userId, (byte) 0);
                StoreIncomeDto monthStoreTotalIncome = getTotalIncomeByMonth(userId, month, (byte) 0);

                //查询自动结算额
                balanceMoney = rpcTlMemberService.getStoreMgrBalanceAmount(userId);
                //查询手工结算额
                manualSettlementAmount = getStoreDirectorManualSettleAmount(userId,month, userType.getType());

                //门店收益数据
                storeIncomeModel.setToBeIncome(storeToBeIncome.getStoreToBeIncome() != null ? storeToBeIncome.getStoreToBeIncome() : BigDecimal.ZERO);
                storeIncomeModel.setMonthAvailableIncome(monthStoreAvailableIncome.getStoreAvailableIncome() != null ? monthStoreAvailableIncome.getStoreAvailableIncome() : BigDecimal.ZERO);
                storeIncomeModel.setMonthTotalIncome(monthStoreTotalIncome.getStoreTotalIncome() != null ? monthStoreTotalIncome.getStoreTotalIncome() : BigDecimal.ZERO);

                //获取店经收益数据
                managerIncomeModel.setToBeIncome(storeToBeIncome.getManagerToBeIncome() != null ? storeToBeIncome.getManagerToBeIncome() : BigDecimal.ZERO);
                managerIncomeModel.setMonthAvailableIncome(monthStoreAvailableIncome.getManagerAvailableIncome() != null ? monthStoreAvailableIncome.getManagerAvailableIncome() : BigDecimal.ZERO);
                managerIncomeModel.setMonthTotalIncome(monthStoreTotalIncome.getManagerTotalIncome() != null ? monthStoreTotalIncome.getManagerTotalIncome() : BigDecimal.ZERO);

                //获取店主收益数据
                keeperIncomeModel.setToBeIncome(storeToBeIncome.getKeeperToBeIncome() != null ? storeToBeIncome.getKeeperToBeIncome() : BigDecimal.ZERO);
                keeperIncomeModel.setMonthAvailableIncome(monthStoreAvailableIncome.getKeeperAvailableIncome() != null ? monthStoreAvailableIncome.getKeeperAvailableIncome() : BigDecimal.ZERO);
                keeperIncomeModel.setMonthTotalIncome(monthStoreTotalIncome.getKeeperTotalIncome() != null ? monthStoreTotalIncome.getKeeperTotalIncome() : BigDecimal.ZERO);

                if (DateTypeEnum.CUR_MONTH.equals(dateTypeEnum)) {
                    ownerBrandModel = getBrandDataForCurMonth(userId, month, today, (byte) 2);
                    circulatedBrandModel = getBrandDataForCurMonth(userId, month, today, (byte) 1);
                } else if (DateTypeEnum.PRE_MONTH.equals(dateTypeEnum)) {
                    ownerBrandModel = getBrandHistoryData(userId, month, (byte) 2);
                    circulatedBrandModel = getBrandHistoryData(userId, month, (byte) 1);
                }
            }

            userIncomeModel.setToBeIncome(toBeIncome != null ? toBeIncome : BigDecimal.ZERO);
            userIncomeModel.setMonthAvailableIncome(monthAvailableIncome != null ? monthAvailableIncome : BigDecimal.ZERO);
            userIncomeModel.setMonthTotalIncome(monthTotalIncome.getMonthTotalIncome() != null ? monthTotalIncome.getMonthTotalIncome() : BigDecimal.ZERO);
            userIncomeModel.setAvailableBalanceAmount(balanceMoney == null ? BigDecimal.ZERO : balanceMoney);
            userIncomeModel.setManualSettlementAmount(manualSettlementAmount == null ? BigDecimal.ZERO : manualSettlementAmount);
            userSalesModel.setMonthSales(monthTotalIncome.getMonthTotalSales() != null ? monthTotalIncome.getMonthTotalSales() : BigDecimal.ZERO);
            userSalesModel.setTodaySales(todaySales != null ? todaySales : BigDecimal.ZERO);
            userSalesModel.setMonthRefundSales(monthSales.getMonthRefundSales() != null ? monthSales.getMonthRefundSales() : BigDecimal.ZERO);
            BigDecimal monthSalesRate = monthSales.getMonthSalesRate() != null ? monthSales.getMonthSalesRate() : BigDecimal.ZERO;
            userSalesModel.setMonthSalseRate(BigDecimal.valueOf(DecimalCalculate.round(monthSalesRate.doubleValue(), 4)));
            BigDecimal monthRefundRate = monthSales.getMonthRefundRate() != null ? monthSales.getMonthRefundRate() : BigDecimal.ZERO;
            userSalesModel.setMonthRefundRate(BigDecimal.valueOf(DecimalCalculate.round(monthRefundRate.doubleValue(), 4)));
        } catch (Exception e) {
            log.error("getUserAchievement exception : {} \nuserId = {}, userType = {}, month = {}",
                    e.getMessage(), userId, userType.getType(), month);
        }

        UserAchievementModel userAchievementModel = new UserAchievementModel();
        userAchievementModel.setIncomeModel(userIncomeModel);
        userAchievementModel.setSalesModel(userSalesModel);
        userAchievementModel.setStoreIncomeModel(storeIncomeModel);
        userAchievementModel.setManagerIncomeModel(managerIncomeModel);
        userAchievementModel.setKeeperIncomeModel(keeperIncomeModel);
        userAchievementModel.setOwnerBrandModel(ownerBrandModel);
        userAchievementModel.setCirculatedBrandModel(circulatedBrandModel);
        return GLResponse.succ(userAchievementModel);
    }


    /**
     * 获取历史战绩数据
     */
    private GLResponse<UserAchievementModel> getHistoryAchievement(Long userId, Integer month) {
        UserIncomeModel directorIncomeModel = new UserIncomeModel();
        UserSalesModel userSalesModel = new UserSalesModel();
        StoreIncomeModel storeIncomeModel = new StoreIncomeModel();
        UserIncomeModel managerIncomeModel = new UserIncomeModel();
        UserIncomeModel keeperIncomeModel = new UserIncomeModel();
        BaseBrandModel ownerBrandModel = new BaseBrandModel();
        BaseBrandModel circulatedBrandModel = new BaseBrandModel();
        try {
            //获取销售额
            UserIncomeDto monthTotalIncome = executeGetUserTotalIncomeByMonth(userId, UserTypeEnum.STORE_DIRECTOR.getType(), month);
            userSalesModel.setMonthSales(monthTotalIncome.getMonthTotalSales() != null ? monthTotalIncome.getMonthTotalSales() : BigDecimal.ZERO);

            //获取门店名称
            storeIncomeModel.setStoreName(getStoreNameById(userId));

            //获取收益数据
            StoreIncomeDto storeIncomeDto = getStoreAvailableIncomeByMonth(userId, month, (byte) 0);
            storeIncomeModel.setMonthAvailableIncome(storeIncomeDto.getStoreAvailableIncome() != null ? storeIncomeDto.getStoreAvailableIncome() : BigDecimal.ZERO);
            directorIncomeModel.setMonthAvailableIncome(storeIncomeDto.getDirectorAvailableIncome() != null ? storeIncomeDto.getDirectorAvailableIncome() : BigDecimal.ZERO);
            managerIncomeModel.setMonthAvailableIncome(storeIncomeDto.getManagerAvailableIncome() != null ? storeIncomeDto.getManagerAvailableIncome() : BigDecimal.ZERO);
            keeperIncomeModel.setMonthAvailableIncome(storeIncomeDto.getKeeperAvailableIncome() != null ? storeIncomeDto.getKeeperAvailableIncome() : BigDecimal.ZERO);

            //获取自有商品销售和收益数据
            ownerBrandModel = getBrandHistoryData(userId, month, (byte) 2);

            //获取流通商品销售和收益数据
            circulatedBrandModel = getBrandHistoryData(userId, month, (byte) 1);

        } catch (Exception e) {
            log.error("getHistoryAchievement exception : {} \nuserId = {}, month = {}", e.getMessage(), userId, month);
        }

        UserAchievementModel userAchievementModel = new UserAchievementModel();
        userAchievementModel.setIncomeModel(directorIncomeModel);
        userAchievementModel.setSalesModel(userSalesModel);
        userAchievementModel.setStoreIncomeModel(storeIncomeModel);
        userAchievementModel.setManagerIncomeModel(managerIncomeModel);
        userAchievementModel.setKeeperIncomeModel(keeperIncomeModel);
        userAchievementModel.setOwnerBrandModel(ownerBrandModel);
        userAchievementModel.setCirculatedBrandModel(circulatedBrandModel);
        return GLResponse.succ(userAchievementModel);
    }

    /**
     * 获取分类商品的历史销售和收益数据
     */
    private BaseBrandModel getBrandHistoryData(Long userId, int month, byte brandType) {
        BaseBrandModel baseBrandModel = new BaseBrandModel();
        UserIncomeDto monthSales = executeGetUserSalesByMonth(userId, UserTypeEnum.STORE_DIRECTOR.getType(), month, brandType);
        baseBrandModel.setMonthSales(monthSales.getMonthTotalSales() != null ? monthSales.getMonthTotalSales() : BigDecimal.ZERO);
        StoreIncomeDto storeIncomeDto = getStoreAvailableIncomeByMonth(userId, month, brandType);
        baseBrandModel.setStoreMonthTotalIncome(storeIncomeDto.getStoreAvailableIncome() != null ? storeIncomeDto.getStoreAvailableIncome() : BigDecimal.ZERO);
        baseBrandModel.setDirectorMonthTotalIncome(storeIncomeDto.getDirectorAvailableIncome() != null ? storeIncomeDto.getDirectorAvailableIncome() : BigDecimal.ZERO);
        baseBrandModel.setManagerMonthTotalIncome(storeIncomeDto.getManagerAvailableIncome() != null ? storeIncomeDto.getManagerAvailableIncome() : BigDecimal.ZERO);
        baseBrandModel.setKeeperMonthTotalIncome(storeIncomeDto.getKeeperAvailableIncome() != null ? storeIncomeDto.getKeeperAvailableIncome() : BigDecimal.ZERO);
        return baseBrandModel;
    }

    /**
     * 获取当前月份分类商品销售和收益数据
     *
     * @return
     */
    private BaseBrandModel getBrandDataForCurMonth(Long userId, int month, int today, byte brandType) {
        BaseBrandModel baseBrandModel = new BaseBrandModel();
        BigDecimal todaySales = executeGetUserSalesByToday(userId, UserTypeEnum.STORE_DIRECTOR.getType(), today, brandType);
        UserIncomeDto monthSales = executeGetUserSalesByMonth(userId, UserTypeEnum.STORE_DIRECTOR.getType(), month, brandType);
        baseBrandModel.setMonthSales(monthSales.getMonthTotalSales() != null ? monthSales.getMonthTotalSales() : BigDecimal.ZERO);
        baseBrandModel.setTodaySales(todaySales != null ? todaySales : BigDecimal.ZERO);

        StoreIncomeDto storeMonthTotalIncome = getTotalIncomeByMonth(userId, month, brandType);
        baseBrandModel.setStoreMonthTotalIncome(storeMonthTotalIncome.getStoreTotalIncome() != null ? storeMonthTotalIncome.getStoreTotalIncome() : BigDecimal.ZERO);
        baseBrandModel.setDirectorMonthTotalIncome(storeMonthTotalIncome.getDirectorTotalIncome() != null ? storeMonthTotalIncome.getDirectorTotalIncome() : BigDecimal.ZERO);
        baseBrandModel.setManagerMonthTotalIncome(storeMonthTotalIncome.getManagerTotalIncome() != null ? storeMonthTotalIncome.getManagerTotalIncome() : BigDecimal.ZERO);
        baseBrandModel.setKeeperMonthTotalIncome(storeMonthTotalIncome.getKeeperTotalIncome() != null ? storeMonthTotalIncome.getKeeperTotalIncome() : BigDecimal.ZERO);

        StoreIncomeDto storeTodayIncome = getTotalIncomeByDay(userId, today, brandType);
        baseBrandModel.setStoreTodayTotalIncome(storeTodayIncome.getStoreTotalIncome() != null ? storeTodayIncome.getStoreTotalIncome() : BigDecimal.ZERO);
        baseBrandModel.setDirectorTodayTotalIncome(storeTodayIncome.getDirectorTotalIncome() != null ? storeTodayIncome.getDirectorTotalIncome() : BigDecimal.ZERO);
        baseBrandModel.setManagerTodayTotalIncome(storeTodayIncome.getManagerTotalIncome() != null ? storeTodayIncome.getManagerTotalIncome() : BigDecimal.ZERO);
        baseBrandModel.setKeeperTodayTotalIncome(storeTodayIncome.getKeeperTotalIncome() != null ? storeTodayIncome.getKeeperTotalIncome() : BigDecimal.ZERO);
        return baseBrandModel;
    }

    /**
     * 查询用户待收益
     */
    private BigDecimal executeGetUserToBeIncome(final long userId, final byte userType) {
        BigDecimal result = commissionSettlementMapper.getUserToBeIncome(userId, userType);
        return result != null ? result : BigDecimal.ZERO;
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
    private UserIncomeDto executeGetUserSalesByMonth(final long userId, final byte userType, final int month, final byte brandType) {
        return commissionSettlementMapper.getUserSalesByMonth(userId, userType, month, brandType);
    }

    /**
     * 查询用户月可用收益
     */
    private BigDecimal executeGetUserAvailableIncomeByMonth(final long userId, final byte userType, final int month) {
        BigDecimal result = commissionSettlementMapper.getUserAvailableIncomeByMonth(userId, userType, month);
        return result != null ? result : BigDecimal.ZERO;
    }

    /**
     * 查询用户今日销售额
     */
    private BigDecimal executeGetUserSalesByToday(final long userId, final byte userType, final int day, final byte brandType) {
        BigDecimal result = commissionSettlementMapper.getUserTodaySales(userId, userType, day, brandType);
        return result != null ? result : BigDecimal.ZERO;
    }

    /**
     * 查询门店待收益
     */
    private StoreIncomeDto getToBeIncome(final long userId, final byte brandType) {
        return commissionSettlementMapper.getStoreToBeIncome(userId, brandType);
    }

    /**
     * 查询门店月可用收益
     */
    private StoreIncomeDto getStoreAvailableIncomeByMonth(final long userId, final int month, final byte brandType) {
        return commissionSettlementMapper.getStoreAvailableIncomeByMonth(userId, month, brandType);
    }

    /**
     * 查询门店月总收益
     */
    private StoreIncomeDto getTotalIncomeByMonth(final long userId, final int month, final byte brandType) {
        return commissionSettlementMapper.getStoreTotalIncomeByMonth(userId, month, brandType);
    }

    /**
     * 查询门店日总收益
     */
    private StoreIncomeDto getTotalIncomeByDay(final long userId, final int day, final byte brandType) {
        return commissionSettlementMapper.getStoreTotalIncomeByDay(userId, day, brandType);
    }

    /**
     * 查询店董手工结算收益
     *
     * @param userId
     * @param type
     * @return
     */
    private BigDecimal getStoreDirectorManualSettleAmount(Long userId, final int month,Byte type) {
        return commissionSettlementMapper.getStoreDirectorManualSettleAmount(userId, month,type);
    }

    /**
     * 按月获取用户的订单明细列表
     *
     * @param userId
     * @param userTypeEnum
     * @param month        yyyyMM
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
        if (CollectionUtils.isEmpty(orderDetailDtoList)) {
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
     *
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
        if (totalCount == null || totalCount <= 0) {
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
     * 查询用户id
     *
     * @param phone
     * @return
     */
    private Long getUserIdByPhoneFromCustomer(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        example.setLimit(1);
        List<User> userList = userMapper.selectByExample(example);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0).getId();
    }

    /**
     * 查询门店id
     *
     * @param storeCode
     * @return
     */
    private Long getUserIdByStoreCode(String storeCode) {
        StoreExample example = new StoreExample();
        example.createCriteria().andStoreCodeEqualTo(storeCode);
        List<Store> storeList = storeMapper.selectByExample(example);
        return CollectionUtils.isEmpty(storeList) ? null : storeList.get(0).getStoreId();
    }

    /**
     * 根据登录账号从店经店董表查询用户
     *
     * @param loginId
     * @return
     */
    private ManagerAndDirector getManagerAndDirectorByLoginId(String loginId) {
        ManagerAndDirectorExample example = new ManagerAndDirectorExample();
        ManagerAndDirectorExample.Criteria criteria = example.createCriteria();
        criteria.andLoginIdEqualTo(loginId);
        List<ManagerAndDirector> userList = managerAndDirectorMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    /**
     * 新增店经店董
     *
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

    /**
     * 查询门店名称
     *
     * @param storeId
     * @return
     */
    private String getStoreNameById(Long storeId) {
        StoreExample example = new StoreExample();
        example.createCriteria().andStoreIdEqualTo(storeId);
        example.setLimit(1);
        List<Store> storeList = storeMapper.selectByExample(example);
        return CollectionUtils.isEmpty(storeList) ? "" : storeList.get(0).getStoreName();
    }

}
