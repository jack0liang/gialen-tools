package com.gialen.tools.service.model;

import com.gialen.common.model.PageResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wong
 * @Date: 2020-08-12
 * @Version: 1.0
 */
@Data
public class StoreUserWithDrawRespModel implements Serializable {
    private static final long serialVersionUID = -7970677340834493523L;

    /**
     * 已到账
     */
    private BigDecimal accountArrived = BigDecimal.ZERO;
    /**
     * 未到账
     */
    private BigDecimal accountNotArrived = BigDecimal.ZERO;
    /**
     * 明细
     */
    private PageResponse<WithDrawModel> withdrawList;

    public StoreUserWithDrawRespModel(int page,int pageSize) {
        this.withdrawList = PageResponse.empty(page,pageSize);
    }

    public StoreUserWithDrawRespModel() {
    }

    @Data
    public static class WithDrawModel{
        private String bizOrderNo;
        private Long userId;
        private String bankCardNo;
        private BigDecimal amount = BigDecimal.ZERO;
        private String createTime;
        private String status;
    }


}
