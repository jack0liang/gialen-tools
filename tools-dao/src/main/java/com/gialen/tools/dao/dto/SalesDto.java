package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lupeibo
 * @date 2019-05-27
 */
@Getter
@Setter
public class SalesDto {

    private String countTime;

    private Double salesNum;

    /**
     * 实付金额
     */
    private Double realSalesNum;

}
