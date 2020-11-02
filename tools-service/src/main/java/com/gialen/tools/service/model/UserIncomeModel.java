package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2019-06-25
 */
@Data
public class    UserIncomeModel implements Serializable {

    private static final long serialVersionUID = 5344891766358449109L;

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
     * 可用余额
     */
    private BigDecimal availableBalanceAmount = BigDecimal.ZERO;

    /**
     * 手工结算金额
     */
    private BigDecimal manualSettlementAmount = BigDecimal.ZERO;



    /**
     * 待收益(门店出货)
     */
    private BigDecimal storeTake_toBeIncome = BigDecimal.ZERO;

    /**
     * 月累计收益 (门店出货)
     */
    private BigDecimal storeTake_monthTotalIncome = BigDecimal.ZERO;

    /**
     * 可用收益 (门店出货)
     */
    private BigDecimal storeTake_monthAvailableIncome = BigDecimal.ZERO;







}
