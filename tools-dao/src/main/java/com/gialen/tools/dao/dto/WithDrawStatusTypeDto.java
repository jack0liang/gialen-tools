package com.gialen.tools.dao.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wong
 * @Date: 2020-08-12
 * @Version: 1.0
 */
@Data
public class WithDrawStatusTypeDto {


    private BigDecimal accountArrived;
    private BigDecimal accountNotArrived;
}
