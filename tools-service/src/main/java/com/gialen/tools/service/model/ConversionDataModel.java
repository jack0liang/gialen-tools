package com.gialen.tools.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 转化数据模型
 * @author lupeibo
 * @date 2019-05-31
 */
@Getter
@Setter
public class ConversionDataModel {

    /**
     * 订单创建数
     */
    private Integer orderCreated;

    /**
     * 订单环比创建数
     */
    private Integer orderCreatedRelative;

    /**
     * 订单创建数环比
     */
    private Double orderCreatedRelativeRatio;

    /**
     * 订单成功支付数
     */
    private Integer orderSuccess;

    /**
     * 订单环比成功支付数
     */
    private Integer orderSuccessRelative;

    /**
     * 订单成功支付数环比
     */
    private Double orderSuccessRelativeRatio;

    /**
     * 支付成功率
     */
    private Double orderSuccessRate;

    /**
     * 支付环比成功率
     */
    private Double orderSuccessRateRelative;

    /**
     * 支付成功率环比
     */
    private Double orderSuccessRateRelativeRatio;

}
