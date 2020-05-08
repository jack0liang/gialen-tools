package com.gialen.tools.integration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gialen.common.model.GLResponse;
import com.gialen.order.client.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author wong
 * @Date: 2020-04-21
 * @Version: 1.0
 */
@Service
@Slf4j
public class RpcOrderCheckService {

    @Reference
    private OrderService orderService;

    /**
     * 门店核销取货码
     *
     * @param pickerCode
     * @return
     */
    public GLResponse<String> verifyPickerCode(String pickerCode) {
        if (StringUtils.isEmpty(pickerCode)) {
            return null;
        }
        return orderService.verification(pickerCode);
    }


}
