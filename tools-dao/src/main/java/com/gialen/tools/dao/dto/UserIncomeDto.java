package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用户收益dto
 * @author lupeibo
 * @date 2019-06-25
 */
@Getter
@Setter
public class UserIncomeDto {

    /**
     * 待收益
     */
    private BigDecimal toBeIncome;

    /**
     * 月累计收益
     */
    private BigDecimal monthTotalIncome;

    /**
     * 月可用收益
     */
    private BigDecimal monthAvailableIncome;

    /**
     * 月度销售额
     */
    private BigDecimal monthTotalSales;
}
