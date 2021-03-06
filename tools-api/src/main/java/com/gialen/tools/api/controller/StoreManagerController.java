package com.gialen.tools.api.controller;

import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.api.annotation.RequireLogin;
import com.gialen.tools.api.convertor.StoreManagerConvertor;
import com.gialen.tools.api.vo.*;
import com.gialen.tools.common.enums.ChildTypeEnum;
import com.gialen.tools.common.enums.CommunityQueryTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.common.util.PageResponseEx;
import com.gialen.tools.common.util.TokenUtil;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.model.*;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 掌数宝工具控制器
 */
@Slf4j
@RestController
@RequestMapping("/storeManager")
public class StoreManagerController {

    @Resource
    private StoreManagerService storeManagerService;

    @PostMapping("/login")
    @ResponseBody
    public GLResponse<?> login(@RequestParam(name = "loginId") @ApiParam(value = "登录名") String logigId,
                               @RequestParam(name = "password") @ApiParam(value = "密码") String password,
                               @RequestParam(name = "userType") @ApiParam(value = "用户类型：3店经, 4店董") Byte userType) {
        log.info("login : loginId = {}, password = {}, userType = {}", logigId, password, userType);
        GLResponse<Long> response = storeManagerService.login(logigId, password, UserTypeEnum.getByType(userType));
        if (!response.getSuccess()) {
            return response;
        }
        ManagerAndDirectorVo vo = new ManagerAndDirectorVo();
        vo.setLoginId(logigId);
        vo.setUserType(userType);
        vo.setUserId(response.getData());
        String token = StringUtils.replaceAll(UUID.randomUUID().toString(), "-", "");
        vo.setToken(token);
        TokenUtil.tokenUserIdCache.put(token, response.getData());
        return GLResponse.succ(vo);
    }

    @PostMapping("/logout")
    @ResponseBody
    @RequireLogin
    public GLResponse logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        TokenUtil.tokenUserIdCache.invalidate(token);
        return GLResponse.succ(null);
    }

    @PostMapping("/modifyPassword")
    @ResponseBody
    @RequireLogin
    public GLResponse<?> modifyPassword(@RequestParam(name = "userId", required = false) Long userId,
                                        @RequestParam(name = "password") String password,
                                        @RequestParam(name = "rePassword") String rePassword,
                                        HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("modifyPassword : userId = {}, password = {}, rePassword = {}", userId, password, rePassword);
        return storeManagerService.modifyPassword(userId, password, rePassword);
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public GLResponse<?> resetPassword(@RequestParam(name = "loginId") String loginId) {
        log.info("resetPassword : loginId = {}", loginId);
        return storeManagerService.resetPassword(loginId);
    }

    @RequestMapping("/getCurMonthUserAchievement")
    @ResponseBody
    @RequireLogin
    public GLResponse<UserAchievementVo> getCurMonthUserAchievement(@RequestParam(name = "userId", required = false) Long userId,
                                                                    @RequestParam(name = "userType") @ApiParam(value = "用户类型：3店经, 4店董") Byte userType,
                                                                    HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("getCurMonthUserAchievement : userId = {}, userType = {}", userId, userType);
        GLResponse<UserAchievementModel> modelGLResponse = storeManagerService.getCurMonthUserAchievement(userId, UserTypeEnum.getByType(userType));
        UserAchievementVo vo = StoreManagerConvertor.userAchievementModelConvertToVo(modelGLResponse.getData());
        byte month = Byte.parseByte(DateFormatUtils.format(new Date(), "M"));
        vo.setMonth(month);
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getPreMonthUserAchievement")
    @ResponseBody
    @RequireLogin
    public GLResponse<UserAchievementVo> getPreMonthUserAchievement(@RequestParam(name = "userId", required = false) Long userId,
                                                                    @RequestParam(name = "userType") @ApiParam(value = "用户类型：3店经, 4店董") Byte userType,
                                                                    HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("getPreMonthUserAchievement : userId = {}, userType = {}", userId, userType);
        GLResponse<UserAchievementModel> modelGLResponse = storeManagerService.getPreMonthUserAchievement(userId, UserTypeEnum.getByType(userType));
        UserAchievementVo vo = StoreManagerConvertor.userAchievementModelConvertToVo(modelGLResponse.getData());
        byte month = Byte.parseByte(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "M"));
        vo.setMonth(month);
        log.info("response : " + vo);
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getHistoryMonthUserAchievement")
    @ResponseBody
    @RequireLogin
    public GLResponse<UserAchievementVo> getHistoryMonthUserAchievement(@RequestParam(name = "userId", required = false) Long userId,
                                                                        @RequestParam(name = "month", required = false) Integer month,
                                                                        HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        byte monthShow = 0;
        try {
            Date date = DateUtils.parseDate(String.valueOf(month), "yyyyMM");
            monthShow = Byte.parseByte(DateFormatUtils.format(date, "M"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //add by 唐艳需求
        List<Long> skipQueryList = Lists.newArrayList(5402L,
                5901L,
                4629L,
                4645L,
                4663L,
                4725L,
                4726L,
                4825L,
                4205L,
                4319L,
                4092L,
                4115L,
                4423L,
                4529L,
                5016L);

        //指定的门店 2020-01月前不让看门店历史数据
        if (skipQueryList.contains(userId) && month < 202001) {
            log.info("门店历史数据屏蔽:{},{}", userId, month);
            month = 201801;
        }
        GLResponse<UserAchievementModel> modelGLResponse = storeManagerService.getHistoryMonthUserAchievement(userId, month);
        UserAchievementVo vo = StoreManagerConvertor.userAchievementModelConvertToVo(modelGLResponse.getData());
        vo.setMonth(monthShow);


        log.info("response : " + vo);
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getCurMonthUserOrderList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<OrderDetailVo>> getCurMonthUserOrderList(@RequestParam(name = "userId", required = false) Long userId,
                                                                            @RequestParam(name = "userType") Byte userType,
                                                                            @RequestParam(name = "subOrderStatus", required = false) Byte subOrderStatus,
                                                                            PageRequest page, HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("getCurMonthUserOrderList : userId = {}, userType = {}, subOrderStatus={}, offset = {}, limit = {}", userId, userType, subOrderStatus, page.getOffset(), page.getLimit());
        GLResponse<PageResponse<OrderDetailModel>> modelGLResponse = storeManagerService.getCurMonthUserOrderList(userId, UserTypeEnum.getByType(userType), subOrderStatus, page);
        PageResponse<OrderDetailModel> modelPage = modelGLResponse.getData();
        PageResponse<OrderDetailVo> voPage = StoreManagerConvertor.orderDetailModelPageConvertToVoPage(modelPage, UserTypeEnum.getByType(userType));
        return GLResponse.succ(voPage);
    }

    @RequestMapping("/getPreMonthUserOrderList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<OrderDetailVo>> getPreMonthUserOrderList(@RequestParam(name = "userId", required = false) Long userId,
                                                                            @RequestParam(name = "userType") Byte userType,
                                                                            @RequestParam(name = "subOrderStatus", required = false) Byte subOrderStatus,
                                                                            PageRequest page, HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("getPreMonthUserOrderList : userId = {}, userType = {}, subOrderStatus = {}, offset = {}, limit = {}", userId, userType, subOrderStatus, page.getOffset(), page.getLimit());
        GLResponse<PageResponse<OrderDetailModel>> modelGLResponse = storeManagerService.getPreMonthUserOrderList(userId, UserTypeEnum.getByType(userType), subOrderStatus, page);
        PageResponse<OrderDetailModel> modelPage = modelGLResponse.getData();
        PageResponse<OrderDetailVo> voPage = StoreManagerConvertor.orderDetailModelPageConvertToVoPage(modelPage, UserTypeEnum.getByType(userType));
        return GLResponse.succ(voPage);
    }

    @RequestMapping("/getCommunity")
    @ResponseBody
    @RequireLogin
    public GLResponse<CommunityVo> getCommunity(@RequestParam(name = "userType") Byte userType, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getCommunity : userId = {}, userType = {}", userId, userType);
        CommunityModel model = storeManagerService.getCommunity(userId, UserTypeEnum.getByType(userType));
        return GLResponse.succ(StoreManagerConvertor.convertToCommunityVo(model, CommunityQueryTypeEnum.All, UserTypeEnum.getByType(userType)));
    }

    @RequestMapping("/getCurMonthCommunity")
    @ResponseBody
    @RequireLogin
    public GLResponse<CommunityVo> getCurMonthCommunity(@RequestParam(name = "userType") Byte userType, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getCurMonthCommunity : userId = {}, userType = {}", userId, userType);
        CommunityModel model = storeManagerService.getCurMonthCommunity(userId, UserTypeEnum.getByType(userType));
        return GLResponse.succ(StoreManagerConvertor.convertToCommunityVo(model, CommunityQueryTypeEnum.CUR_MONTH, UserTypeEnum.getByType(userType)));
    }

    @RequestMapping("/getUserChildList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<ChildVo>> getUserChildList(@RequestParam(name = "userType") Byte userType,
                                                              @RequestParam(name = "childType") Byte childType,
                                                              @RequestParam(name = "page") int page,
                                                              @RequestParam(name = "limit") int limit,
                                                              @RequestParam(name = "storeName", required = false) String userName,
                                                              HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        if (UserTypeEnum.STORE_DIRECTOR.getType() == userType) {
            childType = ChildTypeEnum.getByIndexForDirector(childType).getCode();
        }
        log.info("getUserChildList : userId = {}, userType = {}, childType = {}, page = {}, limit = {}, userName = {}", userId, userType, childType, page, limit, userName);
        PageResponse<CustomerModel> modelPageResponse = storeManagerService.getUserChildList(userId, UserTypeEnum.getByType(userType),
                ChildTypeEnum.getByType(childType), new PageRequest(page, limit), userName);
        List<ChildVo> voList = StoreManagerConvertor.convertToChildVoList(modelPageResponse.getList(), childType);

        return GLResponse.succ(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()));
    }

    @RequestMapping("/getCurMonthUserChildList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<ChildVo>> getCurMonthUserChildList(@RequestParam(name = "userType") Byte userType,
                                                                      @RequestParam(name = "childType") Byte childType,
                                                                      @RequestParam(name = "page") int page,
                                                                      @RequestParam(name = "limit") int limit,
                                                                      HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        if (UserTypeEnum.STORE_DIRECTOR.getType() == userType) {
            childType = ChildTypeEnum.getByIndexForDirector(childType).getCode();
        }
        log.info("getCurMonthUserChildList : userId = {}, userType = {}, childType = {}, page = {}, limit = {}", userId, userType, childType, page, limit);
        PageResponse<CustomerModel> modelPageResponse = storeManagerService.getCurMonthUserChildList(userId, UserTypeEnum.getByType(userType),
                ChildTypeEnum.getByType(childType), new PageRequest(page, limit));
        List<ChildVo> voList = StoreManagerConvertor.convertToChildVoList(modelPageResponse.getList(), childType);

        return GLResponse.succ(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()));
    }

    @RequestMapping("/getNewVipNum")
    @ResponseBody
    @RequireLogin
    public GLResponse<VipCommunityVo> getNewVipNum(@RequestParam(name = "userType") Byte userType, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getNewVipNum : userId = {}, userType = {}", userId, userType);
        VipCommunityModel model = storeManagerService.getNewVipNum(userId, UserTypeEnum.getByType(userType));
        return GLResponse.succ(StoreManagerConvertor.convertVipCommunityModelToVo(model));
    }

    @RequestMapping("/getCurMonthNewVipList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<NewVipVo>> getCurMonthNewVipList(@RequestParam(name = "userType") Byte userType,
                                                                    @RequestParam(name = "page") int page,
                                                                    @RequestParam(name = "limit") int limit,
                                                                    @RequestParam(name = "storeName", required = false) String storeName,
                                                                    HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getCurMonthNewVipList : userId = {}, userType = {}, page = {}, limit = {}, storeName = {}", userId, userType, page, limit, storeName);
        PageResponse<VipCommunityModel> modelPageResponse = storeManagerService.getCurMonthNewVipList(userId, UserTypeEnum.getByType(userType), new PageRequest(page, limit), storeName);
        List<NewVipVo> voList = StoreManagerConvertor.convertToNewVipVoList(modelPageResponse.getList());

        byte month = Byte.parseByte(DateFormatUtils.format(new Date(), "MM"));
        return GLResponse.succ(PageResponseEx.buildPageResponseEx(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()), month));
    }

    @RequestMapping("/getPreMonthNewVipList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<NewVipVo>> getPreMonthNewVipList(@RequestParam(name = "userType") Byte userType,
                                                                    @RequestParam(name = "page") int page,
                                                                    @RequestParam(name = "limit") int limit,
                                                                    @RequestParam(name = "storeName", required = false) String storeName,
                                                                    HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getPreMonthNewVipList : userId = {}, userType = {}, page = {}, limit = {}, storeName = {}", userId, userType, page, limit, storeName);
        PageResponse<VipCommunityModel> modelPageResponse = storeManagerService.getPreMonthNewVipList(userId, UserTypeEnum.getByType(userType), new PageRequest(page, limit), storeName);
        List<NewVipVo> voList = StoreManagerConvertor.convertToNewVipVoList(modelPageResponse.getList());

        byte month = Byte.parseByte(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "MM"));
        return GLResponse.succ(PageResponseEx.buildPageResponseEx(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()), month));
    }

    @RequestMapping("/getStoreActivity")
    @ResponseBody
    @RequireLogin
    public GLResponse<StoreActivityDataVo> getStoreActivity(@RequestParam(name = "userType") Byte userType, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getStoreActivity ：userId = {}, userType = {}", userId, userType);
        StoreActivityModel model = storeManagerService.getStoreActivity(userId, UserTypeEnum.getByType(userType));
        return GLResponse.succ(StoreManagerConvertor.convertStoreActivityModelToVo(model));
    }

    @RequestMapping("/getCurMonthActivityStoreList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponseEx<StoreActivityDetailVo>> getCurMonthActivityStoreList(@RequestParam(name = "userType") Byte userType,
                                                                                          @RequestParam(name = "purchasedType") Byte purchasedType,
                                                                                          @RequestParam(name = "page") int page,
                                                                                          @RequestParam(name = "limit") int limit,
                                                                                          @RequestParam(name = "storeName", required = false) String storeName,
                                                                                          HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getCurMonthActivityStoreList : userId = {}, userType = {}, purchasedType = {}, page = {}, limit = {}, storeName = {}", userId, userType, purchasedType, page, limit, storeName);
        PageResponse<StoreActivityDetailModel> modelPageResponse = storeManagerService.getCurMonthActivityStoreList(userId, UserTypeEnum.getByType(userType), purchasedType, new PageRequest(page, limit), storeName);
        List<StoreActivityDetailVo> voList = StoreManagerConvertor.convertStoreActivityDetailModelToVoList(modelPageResponse.getList());

        byte month = Byte.parseByte(DateFormatUtils.format(new Date(), "MM"));
        return GLResponse.succ(PageResponseEx.buildPageResponseEx(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()), month));
    }

    @RequestMapping("/getPreMonthActivityStoreList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponseEx<StoreActivityDetailVo>> getPreMonthActivityStoreList(@RequestParam(name = "userType") Byte userType,
                                                                                          @RequestParam(name = "purchasedType") Byte purchasedType,
                                                                                          @RequestParam(name = "page") int page,
                                                                                          @RequestParam(name = "limit") int limit,
                                                                                          @RequestParam(name = "storeName", required = false) String storeName,
                                                                                          HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("getPreMonthActivityStoreList : userId = {}, userType = {}, purchasedType = {}, page = {}, limit = {}, storeName = {}", userId, userType, purchasedType, page, limit, storeName);
        PageResponse<StoreActivityDetailModel> modelPageResponse = storeManagerService.getPreMonthActivityStoreList(userId, UserTypeEnum.getByType(userType), purchasedType, new PageRequest(page, limit), storeName);
        List<StoreActivityDetailVo> voList = StoreManagerConvertor.convertStoreActivityDetailModelToVoList(modelPageResponse.getList());

        byte month = Byte.parseByte(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "MM"));
        return GLResponse.succ(PageResponseEx.buildPageResponseEx(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()), month));
    }

    @RequestMapping("/getAchievementMonthList")
    @ResponseBody
    public GLResponse<List<MonthModel>> getAchievementMonthList() {
        return storeManagerService.getAchievementMonthList();
    }


    @RequireLogin
    @GetMapping("/getStoreUserWithdrawList")
    public GLResponse<StoreUserWithDrawRespModel> getStoreUserWithdrawList(HttpServletRequest request,PageRequest pageRequest) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        return GLResponse.succ(storeManagerService.getStoreUserWithdrawList(userId,pageRequest));
    }

}