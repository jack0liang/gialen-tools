package com.gialen.tools.api.controller;

import com.gialen.tools.service.CoinService;
import com.gialen.common.model.GLResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/cointools")
public class CoinToolsController {

    @Resource
    private CoinService coinService;

    @ApiOperation(value = "爱心转娇币")
    @PostMapping("/processScoreToCoin")
    public GLResponse processScoreToCoin(@RequestParam(name = "coinIdForTen") @ApiParam(value = "10元娇币id") String coinIdForTen,
                                         @RequestParam(name = "coinIdForFive") @ApiParam(value = "5元娇币id") String coinIdForFive,
                                         @RequestParam(name = "filePath") @ApiParam(value = "文件路径") String filePath,
                                         @RequestParam(name = "sessionId") @ApiParam(value = "sessionId") String sessionId) {

        return coinService.processUserScoreToCoin(filePath, sessionId, coinIdForTen, coinIdForFive);
    }

    @ApiOperation(value = "检查数据")
    @PostMapping("/checkDatas")
    public GLResponse checkDatas(@RequestParam(name = "filePath") @ApiParam(value = "文件路径") String filePath) {

        return coinService.checkDatas(filePath);
    }

}
