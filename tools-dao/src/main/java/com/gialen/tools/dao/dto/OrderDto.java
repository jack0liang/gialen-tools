package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lupeibo
 * @date 2019-05-27
 */
@Getter
@Setter
public class OrderDto {

    private String countTime;

    private Integer createNum;

    private Integer successNum;

    private Double successRate;

    private String platForm;

}
