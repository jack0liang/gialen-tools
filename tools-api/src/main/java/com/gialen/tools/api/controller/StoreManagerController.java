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
import com.gialen.tools.common.util.TokenUtil;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.model.*;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 店经店董收益查询控制器
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
        log.info("loginId = {}, password = {}, userType = {}", logigId, password, userType);
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
    public GLResponse<?> modifyPassword(@RequestParam(name = "userId") Long userId,
                                        @RequestParam(name = "password") String password,
                                        @RequestParam(name = "rePassword") String rePassword,
                                        HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("userId = {}, password = {}, rePassword = {}", userId, password, rePassword);
        return storeManagerService.modifyPassword(userId, password, rePassword);
    }

    @RequestMapping("/getCurMonthUserAchievement")
    @ResponseBody
    @RequireLogin
    public GLResponse<UserAchievementVo> getCurMonthUserAchievement(@RequestParam(name = "userId") Long userId,
                                                                    @RequestParam(name = "userType") @ApiParam(value = "用户类型：3店经, 4店董") Byte userType,
                                                                    HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("userId = {}, userType = {}", userId, userType);
        GLResponse<UserAchievementModel> modelGLResponse = storeManagerService.getCurMonthUserAchievement(userId, UserTypeEnum.getByType(userType));
        UserAchievementVo vo = StoreManagerConvertor.userAchievementModelConvertToVo(modelGLResponse.getData());
        byte month = Byte.parseByte(DateFormatUtils.format(new Date(), "M"));
        vo.setMonth(month);
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getPreMonthUserAchievement")
    @ResponseBody
    @RequireLogin
    public GLResponse<UserAchievementVo> getPreMonthUserAchievement(@RequestParam(name = "userId") Long userId,
                                                                    @RequestParam(name = "userType") @ApiParam(value = "用户类型：3店经, 4店董") Byte userType,
                                                                    HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("userId = {}, userType = {}", userId, userType);
        GLResponse<UserAchievementModel> modelGLResponse = storeManagerService.getPreMonthUserAchievement(userId, UserTypeEnum.getByType(userType));
        UserAchievementVo vo = StoreManagerConvertor.userAchievementModelConvertToVo(modelGLResponse.getData());
        byte month = Byte.parseByte(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "M"));
        vo.setMonth(month);
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getCurMonthUserOrderList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<OrderDetailVo>> getCurMonthUserOrderList(@RequestParam(name = "userId") Long userId,
                                                                            @RequestParam(name = "userType") Byte userType,
                                                                            @RequestParam(name = "subOrderStatus", required = false) Byte subOrderStatus,
                                                                            PageRequest page, HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("userId = {}, userType = {}, subOrderStatus={}, offset = {}, limit = {}", userId, userType, subOrderStatus, page.getOffset(), page.getLimit());
        GLResponse<PageResponse<OrderDetailModel>> modelGLResponse = storeManagerService.getCurMonthUserOrderList(userId, UserTypeEnum.getByType(userType), subOrderStatus, page);
        PageResponse<OrderDetailModel> modelPage = modelGLResponse.getData();
        PageResponse<OrderDetailVo> voPage = StoreManagerConvertor.orderDetailModelPageConvertToVoPage(modelPage, UserTypeEnum.getByType(userType));
        return GLResponse.succ(voPage);
    }

    @RequestMapping("/getPreMonthUserOrderList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<OrderDetailVo>> getPreMonthUserOrderList(@RequestParam(name = "userId") Long userId,
                                                                            @RequestParam(name = "userType") Byte userType,
                                                                            @RequestParam(name = "subOrderStatus", required = false) Byte subOrderStatus,
                                                                            PageRequest page, HttpServletRequest request) {
        if (userId == null || userId <= 0) {
            String token = request.getHeader("token");
            userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        }
        log.info("userId = {}, userType = {}, subOrderStatus = {}, offset = {}, limit = {}", userId, userType, subOrderStatus, page.getOffset(), page.getLimit());
        GLResponse<PageResponse<OrderDetailModel>> modelGLResponse = storeManagerService.getPreMonthUserOrderList(userId, UserTypeEnum.getByType(userType), subOrderStatus, page);
        PageResponse<OrderDetailModel> modelPage = modelGLResponse.getData();
        PageResponse<OrderDetailVo> voPage = StoreManagerConvertor.orderDetailModelPageConvertToVoPage(modelPage, UserTypeEnum.getByType(userType));
        return GLResponse.succ(voPage);
    }

    @RequestMapping("/getCommunity")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<CommunityVo>> getCommunity(@RequestParam(name = "userType") Byte userType, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("userId = {}, userType = {}", userId, userType);
        CommunityModel model = storeManagerService.getCommunity(userId, UserTypeEnum.getByType(userType));
        return GLResponse.succ(StoreManagerConvertor.convertToCommunityVo(model, CommunityQueryTypeEnum.All, UserTypeEnum.getByType(userType)));
    }

    @RequestMapping("/getCurMonthCommunity")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<CommunityVo>> getCurMonthCommunity(@RequestParam(name = "userType") Byte userType, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("userId = {}, userType = {}", userId, userType);
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
                                                              HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        if(UserTypeEnum.STORE_DIRECTOR.getType() == userType) {
            childType = ChildTypeEnum.getByIndexForDirector(childType).getCode();
        }
        log.info("userId = {}, userType = {}, childType = {}, page = {}, limit = {}", userId, userType, childType, page, limit);
        PageResponse<CustomerModel> modelPageResponse = storeManagerService.getUserChildList(userId, UserTypeEnum.getByType(userType),
                ChildTypeEnum.getByType(childType), new PageRequest(page, limit));
        List<ChildVo> voList = StoreManagerConvertor.convertToChildVoList(modelPageResponse.getList());

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
        if(UserTypeEnum.STORE_DIRECTOR.getType() == userType) {
            childType = ChildTypeEnum.getByIndexForDirector(childType).getCode();
        }
        log.info("userId = {}, userType = {}, childType = {}, page = {}, limit = {}", userId, userType, childType, page, limit);
        PageResponse<CustomerModel> modelPageResponse = storeManagerService.getCurMonthUserChildList(userId, UserTypeEnum.getByType(userType),
                ChildTypeEnum.getByType(childType), new PageRequest(page, limit));
        List<ChildVo> voList = StoreManagerConvertor.convertToChildVoList(modelPageResponse.getList());

        return GLResponse.succ(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()));
    }

    @RequestMapping("/getNewVipNum")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<VipCommunityVo>> getNewVipNum(@RequestParam(name = "userType") Byte userType, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("userId = {}, userType = {}", userId, userType);
        VipCommunityModel model = storeManagerService.getNewVipNum(userId, UserTypeEnum.getByType(userType));
        return GLResponse.succ(StoreManagerConvertor.convertVipCommunityModelToVo(model));
    }

    @RequestMapping("/getCurMonthNewVipList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<NewVipVo>> getCurMonthNewVipList(@RequestParam(name = "userType") Byte userType,
                                                                    @RequestParam(name = "page") int page,
                                                                    @RequestParam(name = "limit") int limit,
                                                                    HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("userId = {}, userType = {}, page = {}, limit = {}", userId, userType, page, limit);
        PageResponse<VipCommunityModel> modelPageResponse = storeManagerService.getCurMonthNewVipList(userId, UserTypeEnum.getByType(userType), new PageRequest(page, limit));
        List<NewVipVo> voList = StoreManagerConvertor.convertToNewVipVoList(modelPageResponse.getList());

        return GLResponse.succ(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()));
    }

    @RequestMapping("/getPreMonthNewVipList")
    @ResponseBody
    @RequireLogin
    public GLResponse<PageResponse<NewVipVo>> getPreMonthNewVipList(@RequestParam(name = "userType") Byte userType,
                                                                    @RequestParam(name = "page") int page,
                                                                    @RequestParam(name = "limit") int limit,
                                                                    HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = TokenUtil.tokenUserIdCache.getIfPresent(token);
        log.info("userId = {}, userType = {}, page = {}, limit = {}", userId, userType, page, limit);
        PageResponse<VipCommunityModel> modelPageResponse = storeManagerService.getPreMonthNewVipList(userId, UserTypeEnum.getByType(userType), new PageRequest(page, limit));
        List<NewVipVo> voList = StoreManagerConvertor.convertToNewVipVoList(modelPageResponse.getList());

        return GLResponse.succ(PageResponse.success(voList, page, limit, modelPageResponse.getTotalCount()));
    }

}