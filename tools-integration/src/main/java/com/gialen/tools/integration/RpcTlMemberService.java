package com.gialen.tools.integration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gialen.common.model.GLResponse;
import com.gialen.customer.client.AllInpayService;
import com.gialen.customer.client.model.TlMemberBaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wong
 * @Date: 2020-03-26
 * @Version: 1.0
 */
@Slf4j
@Service
public class RpcTlMemberService {


    @Reference
    private AllInpayService allInpayService;



    public BigDecimal getStoreMgrBalanceAmount(Long storeId) {
        if (storeId == null || storeId == 0) {
            return BigDecimal.ZERO;
        }
        try {
            GLResponse<TlMemberBaseModel> tlMemberBaseModelGLResponse = allInpayService.getTlMemberInfoB(storeId, true);
            if (tlMemberBaseModelGLResponse.getSuccess())
                return tlMemberBaseModelGLResponse.getData().getBalanceAmount();
        } catch (Exception e) {
            log.error("查询店董余额异常 {}", storeId, e);
        }
        return BigDecimal.ZERO;
    }


}
