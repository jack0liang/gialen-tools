package com.gialen.tools.api.controller;

import com.gialen.common.model.GLResponse;
import com.gialen.tools.service.FinanceService;
import com.gialen.tools.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Resource
    private FinanceService financeService;

    @ApiOperation(value = "大店经佣金核算")
    @PostMapping("/genBigSuperMgrCommission")
    public GLResponse batchChangeStoreCode(@RequestParam("month") Integer month) {
        try {
            financeService.genBigSuperMgrCommission(month);
            return GLResponse.succ(null);
        } catch (Exception e) {
            log.error("大店经佣金核算异常", e);
            return GLResponse.fail(e.getMessage());
        }
    }
}
