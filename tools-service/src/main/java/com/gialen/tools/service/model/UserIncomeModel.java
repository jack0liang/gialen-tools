package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2019-06-25
 */
@Data
public class UserIncomeModel implements Serializable {

    private static final long serialVersionUID = 5344891766358449109L;

    /**
     * 待收益
     */
    private BigDecimal toBeIncome;

    /**
     * 月累计收益
     */
    private BigDecimal monthTotalIncome;

    /**
     * 可用收益
     */
    private BigDecimal monthAvailableIncome;
}
