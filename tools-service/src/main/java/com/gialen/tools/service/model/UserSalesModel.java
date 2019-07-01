package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2019-06-25
 */
@Data
public class UserSalesModel implements Serializable {

    private static final long serialVersionUID = -1129425859066343182L;
    /**
     * 月度销售
     */
    private BigDecimal monthSales = BigDecimal.ZERO;

    /**
     * 今日销售
     */
    private BigDecimal todaySales = BigDecimal.ZERO;
}
