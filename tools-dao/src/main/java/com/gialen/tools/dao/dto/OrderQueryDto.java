package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单查询入参dto
 * @author lupeibo
 * @date 2019-06-27
 */
@Getter
@Setter
public class OrderQueryDto {

    private Long userId;

    private Byte userType;

    private Integer month;

    private Byte subOrderStatus;

}
