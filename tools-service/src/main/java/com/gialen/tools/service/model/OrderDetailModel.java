package com.gialen.tools.service.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细model
 * @author lupeibo
 * @date 2019-05-27
 */
@Data
public class OrderDetailModel implements Serializable {
    private static final long serialVersionUID = -5542289874705912085L;
    /**
     * 订单创建时间
     */
    private Date orderCreateTime;

    /**
     * 子订单总价
     */
    private BigDecimal subOrderPrice;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 子订单状态
     */
    private Byte subOrderStatus;

    /**
     * 店主佣金
     */
    private BigDecimal storeMasterCommission;

    /**
     * 店经佣金
     */
    private BigDecimal storeManagerCommission;

    /**
     * 门店佣金
     */
    private BigDecimal storeCommission;

    /**
     * 分公司佣金
     */
    private BigDecimal companyCommission;

    /**
     * 结算类型 手工结算/自动结算
     */
    private String settleType;

}
