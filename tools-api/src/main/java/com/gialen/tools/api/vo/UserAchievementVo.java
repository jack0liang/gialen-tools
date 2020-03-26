package com.gialen.tools.api.vo;

import com.gialen.tools.service.model.BaseBrandModel;
import com.gialen.tools.service.model.CirculatedBrandModel;
import com.gialen.tools.service.model.OwnerBrandModel;
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
     * 可用余额
     */
    private BigDecimal availableBalanceAmount;

    /**
     * 门店待收益（待完成）
     */
    private BigDecimal storeToBeIncome;

    /**
     * 门店月累积收益（本月）
     */
    private BigDecimal storeMonthTotalIncome;

    /**
     * 门店月可用收益（已完成）
     */
    private BigDecimal storeMonthAvailableIncome;

    /**
     * 月度销售
     */
    private BigDecimal monthSales;

    /**
     * 今日销售
     */
    private BigDecimal todaySales;

    /**
     * 饼图数据
     */
    private PieChartDataVo chartData;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 店经待收益（待结算）
     */
    private BigDecimal managerToBeIncome;

    /**
     * 店经月累积收益（本月）
     */
    private BigDecimal managerMonthTotalIncome;

    /**
     * 店经月可用收益（已完成）
     */
    private BigDecimal managerMonthAvailableIncome;

    /**
     * 店主待收益（待结算）
     */
    private BigDecimal keeperToBeIncome;

    /**
     * 店主月累积收益（本月）
     */
    private BigDecimal keeperMonthTotalIncome;

    /**
     * 店主月可用收益（已完成）
     */
    private BigDecimal keeperMonthAvailableIncome;

    /**
     * 自有品牌销售与收益数据
     */
    private BaseBrandModel ownerBrandModel;

    /**
     * 流通品牌销售与收益数据
     */
    private BaseBrandModel circulatedBrandModel;
}
