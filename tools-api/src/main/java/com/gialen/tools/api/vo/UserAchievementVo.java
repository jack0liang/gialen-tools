package com.gialen.tools.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2019-06-25
 */
@Data
public class UserAchievementVo implements Serializable {
    private static final long serialVersionUID = -5508679711189913829L;
    /**
     * 统计月份
     */
    private Byte month;
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
     * 月度销售
     */
    private BigDecimal monthSales;

    /**
     * 今日销售
     */
    private BigDecimal todaySales;
}
