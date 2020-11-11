package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserOrderDto {
    /**
     * 订单金额
     */
    private BigDecimal orderMoney;
    /**
     * 订单数
     */
    private Integer orderNum;
    /**
     * 用户类型，1：VIP；2：店主
     */
    private Integer utype;
}
