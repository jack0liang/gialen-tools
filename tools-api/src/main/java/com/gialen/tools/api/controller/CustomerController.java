package com.gialen.tools.api.controller;

import com.gialen.common.model.GLResponse;
import com.gialen.tools.service.CustomerService;
import com.gialen.tools.service.WxMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @ApiOperation(value = "添加店主")
    @PostMapping("/addKeepers")
    public GLResponse addKeepers() {
        return customerService.addKeepers();
    }

    @ApiOperation(value = "对比店主门店")
    @PostMapping("/userStoreCompare")
    public GLResponse userStoreCompare() {
        return customerService.userStoreCompare();
    }

    @ApiOperation(value = "初始化新老用户标识")
    @PostMapping("/initUserNewOldFlag")
    public GLResponse initUserNewOldFlag() {
        return customerService.initUserNewOldFlag();
    }
}
