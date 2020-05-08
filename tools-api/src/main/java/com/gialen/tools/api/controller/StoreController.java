package com.gialen.tools.api.controller;

import com.gialen.common.model.GLResponse;
import com.gialen.tools.common.lock.RepeaterLock;
import com.gialen.tools.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {

    @Resource
    private StoreService storeService;

    @ApiOperation(value = "批量更新门店编码")
    @PostMapping("/batchChangeStoreCode")
    public GLResponse batchChangeStoreCode(@RequestParam("filePath") String filePath) {
        return storeService.batchChangeStoreCode(filePath);
    }

    @RepeaterLock(keyPrefix = "PICKER_CODE", fieldName = "code")
    @ApiOperation(value = "核销取货码")
    @PostMapping("/verify/pickerCode")
    public GLResponse<String> verifyPickerCode(@RequestParam("code") String code) {
        try {
            return storeService.verifyStorePickerCode(code);
        } catch (Exception e) {
            return GLResponse.fail("核销取货码异常");
        }
    }
}
