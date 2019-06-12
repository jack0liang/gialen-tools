package com.gialen.tools.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付订单数据模型
 * @author lupeibo
 * @date 2019-05-31
 */
@Getter
@Setter
public class PaiedOrderDataModel {

    /**
     * 小程序订单数
     */
    private Integer miniProgramOrder;

    /**
     * 小程序环比订单数
     */
    private Integer miniProgramOrderRelative;

    /**
     * 小程序订单数环比
     */
    private Double miniProgramOrderRelativeRatio;

    /**
     * app订单数
     */
    private Integer appOrder;

    /**
     * app环比订单数
     */
    private Integer appOrderRelative;

    /**
     * app订单数环比
     */
    private Double appOrderRelativeRatio;

    /**
     * h5订单数
     */
    private Integer h5Order;

    /**
     * h5环比订单数
     */
    private Integer h5OrderRelative;

    /**
     * h5订单数环比
     */
    private Double h5OrderRelativeRatio;

}
