package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 门店收益dto
 * @author lupeibo
 * @date 2020-02-08
 */
@Getter
@Setter
public class StoreIncomeDto {

    /**
     * 门店可用收益
     */
    private BigDecimal storeAvailableIncome;

    /**
     * 店董可用收益 (总仓）
     */
    private BigDecimal directorAvailableIncome;

    /**
     * 店董可用收益 (门店出货）
     */
    private BigDecimal storeTake_directorAvailableIncome;

    /**
     * 店经可用收益
     */
    private BigDecimal managerAvailableIncome;

    /**
     * 店主可用收益
     */
    private BigDecimal keeperAvailableIncome;

    /**
     * 门店待收益
     */
    private BigDecimal storeToBeIncome;

    /**
     * 店董待收益
     */
    private BigDecimal directorToBeIncome;

    /**
     * 店经待收益
     */
    private BigDecimal managerToBeIncome;

    /**
     * 店主待收益
     */
    private BigDecimal keeperToBeIncome;

    /**
     * 门店总收益
     */
    private BigDecimal storeTotalIncome;

    /**
     * 店董总收益（总仓）
     */
    private BigDecimal directorTotalIncome;

    /**
     * 店董总收益（门店）
     */
    private BigDecimal storeTake_directorTotalIncome;

    /**
     * 店经总收益
     */
    private BigDecimal managerTotalIncome;

    /**
     * 店主总收益
     */
    private BigDecimal keeperTotalIncome;


}
