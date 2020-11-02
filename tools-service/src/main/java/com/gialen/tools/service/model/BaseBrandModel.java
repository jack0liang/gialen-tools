package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2020-02-06
 */
@Data
public class BaseBrandModel implements Serializable {

    private static final long serialVersionUID = 5567112829434119710L;

    /**
     * 月度销售
     */
    private BigDecimal monthSales;

    /**
     * 今日销售
     */
    private BigDecimal todaySales;

    /**
     * 门店月收益
     */
    private BigDecimal storeMonthTotalIncome;

    /**
     * 门店今日收益
     */
    private BigDecimal storeTodayTotalIncome;

    /**
     * 店董月收益（总仓出货）
     */
    private BigDecimal directorMonthTotalIncome;

    /**
     * 店董今日收益（总仓出货）
     */
    private BigDecimal directorTodayTotalIncome;

    /**
     * 店董月收益(门店出货)
     */
    private BigDecimal storeTake_directorMonthTotalIncome;
    /**
     *  店董今日收益（门店出货）
     */
    private BigDecimal storeTake_directorTodayTotalIncome;


    /**
     * 店经月收益
     */
    private BigDecimal managerMonthTotalIncome;

    /**
     * 店经今日收益
     */
    private BigDecimal managerTodayTotalIncome;

    /**
     * 店主月收益
     */
    private BigDecimal keeperMonthTotalIncome;

    /**
     * 店主今日收益
     */
    private BigDecimal keeperTodayTotalIncome;
}
