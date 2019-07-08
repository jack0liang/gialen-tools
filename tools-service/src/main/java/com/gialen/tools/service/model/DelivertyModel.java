package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 物流订单模型
 * @author lupeibo
 * @date 2019-07-05
 */
@Data
public class DelivertyModel implements Serializable {

    private static final long serialVersionUID = 3127459940347572092L;
    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 物流单号
     */
    private String deliverySn;

    /**
     * 物流公司名称
     */
    private String deliveryCompanyName;
}
