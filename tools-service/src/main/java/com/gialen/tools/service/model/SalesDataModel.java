package com.gialen.tools.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 销售数据模型
 * @author lupeibo
 * @date 2019-05-31
 */
@Getter
@Setter
public class SalesDataModel {

    /**
     * 总销售
     */
    private Double totalSales;

    /**
     * 总销售环比
     */
    private Double totalSalseRelativeRatio;

    /**
     * 大礼包销售
     */
    private Double giftPackageSales;

    /**
     * 大礼包销售环比
     */
    private Double giftPackageSalesRelativeRatio;

    /**
     * 平销
     */
    private Double usualSales;

    /**
     * 平销环比
     */
    private Double usualSalesRelativeRatio;


    /**
     * 折扣值 实付总金额/总销售额
     */
    private Double discountNums;

    /**
     * 折扣值环比
     */
    private Double discountNumsRelativeRatio;




}
