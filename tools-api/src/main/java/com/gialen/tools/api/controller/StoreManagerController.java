package com.gialen.tools.api.controller;

import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.api.convertor.StoreManagerConvertor;
import com.gialen.tools.api.vo.ManagerAndDirectorVo;
import com.gialen.tools.api.vo.OrderDetailVo;
import com.gialen.tools.api.vo.UserAchievementVo;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.model.OrderDetailModel;
import com.gialen.tools.service.model.UserAchievementModel;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

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
        log.info("loginId = {}, password = {}", logigId, password);
        GLResponse<Long> response = storeManagerService.login(logigId, password, UserTypeEnum.getByType(userType));
        if(!response.getSuccess()) {
            return response;
        }
        ManagerAndDirectorVo vo = new ManagerAndDirectorVo();
        vo.setLoginId(logigId);
        vo.setUserType(userType);
        vo.setUserId(response.getData());
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getCurMonthUserAchievement")
    @ResponseBody
    public GLResponse<UserAchievementVo> getCurMonthUserAchievement(@RequestParam(name = "userId") Long userId,
                                                                    @RequestParam(name = "userType") @ApiParam(value = "用户类型：3店经, 4店董") Byte userType) {
        log.info("userId = {}, userType = {}", userId, userType);
        GLResponse<UserAchievementModel> modelGLResponse = storeManagerService.getCurMonthUserAchievement(userId, UserTypeEnum.getByType(userType));
        UserAchievementVo vo = StoreManagerConvertor.userAchievementModelConvertToVo(modelGLResponse.getData());
        byte month = Byte.parseByte(DateFormatUtils.format(new Date(), "M"));
        vo.setMonth(month);
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getPreMonthUserAchievement")
    @ResponseBody
    public GLResponse<UserAchievementVo> getPreMonthUserAchievement(@RequestParam(name = "userId") Long userId,
                                                                    @RequestParam(name = "userType") @ApiParam(value = "用户类型：3店经, 4店董") Byte userType) {
        log.info("userId = {}, userType = {}", userId, userType);
        GLResponse<UserAchievementModel> modelGLResponse = storeManagerService.getPreMonthUserAchievement(userId, UserTypeEnum.getByType(userType));
        UserAchievementVo vo = StoreManagerConvertor.userAchievementModelConvertToVo(modelGLResponse.getData());
        byte month = Byte.parseByte(DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "M"));
        vo.setMonth(month);
        return GLResponse.succ(vo);
    }

    @RequestMapping("/getCurMonthUserOrderList")
    @ResponseBody
    public GLResponse<PageResponse<OrderDetailVo>> getCurMonthUserOrderList(@RequestParam(name = "userId") Long userId,
                                                                            @RequestParam(name = "userType") Byte userType,
                                                                            @RequestParam(name = "subOrderStatus", required = false) Byte subOrderStatus,
                                                                            PageRequest page) {
        log.info("userId = {}, userType = {}, subOrderStatus={}, offset = {}, limit = {}", userId, userType, subOrderStatus, page.getOffset(), page.getLimit());
        GLResponse<PageResponse<OrderDetailModel>> modelGLResponse = storeManagerService.getCurMonthUserOrderList(userId, UserTypeEnum.getByType(userType), subOrderStatus, page);
        PageResponse<OrderDetailModel> modelPage = modelGLResponse.getData();
        PageResponse<OrderDetailVo> voPage = StoreManagerConvertor.orderDetailModelPageConvertToVoPage(modelPage,UserTypeEnum.getByType(userType));
        return GLResponse.succ(voPage);
    }

    @RequestMapping("/getPreMonthUserOrderList")
    @ResponseBody
    public GLResponse<PageResponse<OrderDetailVo>> getPreMonthUserOrderList(@RequestParam(name = "userId") Long userId,
                                                                            @RequestParam(name = "userType") Byte userType,
                                                                            @RequestParam(name = "subOrderStatus", required = false) Byte subOrderStatus,
                                                                            PageRequest page) {
        log.info("userId = {}, userType = {}, subOrderStatus = {}, offset = {}, limit = {}", userId, userType, subOrderStatus, page.getOffset(), page.getLimit());
        GLResponse<PageResponse<OrderDetailModel>> modelGLResponse = storeManagerService.getPreMonthUserOrderList(userId, UserTypeEnum.getByType(userType), subOrderStatus, page);
        PageResponse<OrderDetailModel> modelPage = modelGLResponse.getData();
        PageResponse<OrderDetailVo> voPage = StoreManagerConvertor.orderDetailModelPageConvertToVoPage(modelPage,UserTypeEnum.getByType(userType));
        return GLResponse.succ(voPage);
    }
}
