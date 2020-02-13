package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2020-01-31
 */
@Data
public class StoreIncomeModel implements Serializable {

    private static final long serialVersionUID = -98196922106639835L;

    /**
     * 待收益
     */
    private BigDecimal toBeIncome = BigDecimal.ZERO;

    /**
     * 月累计收益
     */
    private BigDecimal monthTotalIncome = BigDecimal.ZERO;

    /**
     * 可用收益
     */
    private BigDecimal monthAvailableIncome = BigDecimal.ZERO;

    /**
     * 门店名称
     */
    private String storeName = "";
}
