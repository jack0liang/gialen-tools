package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 大店经数据模型
 * @author lupeibo
 * @date 2020-01-13
 */
@Getter
@Setter
@ToString
public class BigSuperMgrDto {

    /**
     * 店经id
     */
    private Long superMgrId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 姓名
     */
    private String username;

    /**
     * 累积店主个数
     */
    private Integer storeMangerNum;

    /**
     * 大礼包销售额
     */
    private BigDecimal bigGiftBagSales;

    /**
     * 总销售额
     */
    private BigDecimal totalSales;

    /**
     * 佣金合计
     */
    private BigDecimal totalCommission;

    /**
     * 月份
     */
    private Integer month;

}
