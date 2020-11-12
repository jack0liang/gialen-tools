package com.gialen.tools.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用户数据模型
 * @author lupeibo
 * @date 2019-05-31
 */
@Getter
@Setter
public class UserDataModel {

    /**
     * vip注册数
     */
    private Integer vipNum;

    /**
     * vip注册环比数
     */
    private Integer vipNumRelative;

    /**
     * vip注册数环比
     */
    private Double vipNumRelativeRatio;

    /**
     * 店主注册数
     */
    private Integer storeNum;

    /**
     * 店主注册环比数
     */
    private Integer storeNumRelative;

    /**
     * 店主注册数环比
     */
    private Double storeNumRelativeRatio;

    /**
     * 新客订单数
     */
    private Integer newUserOrderNum;

    /**
     * 环比新客订单数
     */
    private Integer newUserOrderNumRelative;

    /**
     * 新客订单数环比
     */
    private Double newUserOrderNumRelativeRatio;

    /**
     * 老客订单数
     */
    private Integer oldUserOrderNum;

    /**
     * 环比老客订单数
     */
    private Integer oldUserOrderNumRelative;

    /**
     * 老客订单数环比
     */
    private Double oldUserOrderNumRelativeRatio;

    /**
     * 店主客单价
     */
    private Double storeAvgPrice;
    /**
     * VIP客单价
     */
    private Double vipAvgPrice;
    /**
     * 店主客单价
     */
    private Double storeAvgPriceRelative;
    /**
     * VIP客单价
     */
    private Double vipAvgPriceRelative;
    /**
     * 店主客单价
     */
    private Double storeAvgPriceRelativeRatio;
    /**
     * VIP客单价
     */
    private Double vipAvgPriceRelativeRatio;

    /**
     * 新用户客单价
     */
    private Double newUserAvgPrice;
    /**
     * 老用户客单价
     */
    private Double oldUserAvgPrice;
    /**
     * 店主客单价
     */
    private Double newUserAvgPriceRelative;
    /**
     * VIP客单价
     */
    private Double oldUserAvgPriceRelative;
    /**
     * 店主客单价
     */
    private Double newUserAvgPriceRelativeRatio;
    /**
     * VIP客单价
     */
    private Double oldUserAvgPriceRelativeRatio;
}
