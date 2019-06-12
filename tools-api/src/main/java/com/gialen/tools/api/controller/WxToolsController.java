package com.gialen.tools.api.controller;

import com.gialen.tools.service.WxMessageService;
import com.gialen.common.model.GLResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/wxTools")
public class WxToolsController {

    @Resource
    private WxMessageService wxMessageService;

    @ApiOperation(value = "发送微信模版消息")
    @PostMapping("/sendMsg")
    public GLResponse sendMsg() {
        return wxMessageService.sendWxMsgUrl();
    }
}
