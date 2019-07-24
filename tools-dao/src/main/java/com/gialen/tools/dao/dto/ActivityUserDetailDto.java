package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2019-07-24
 */
@Getter
@Setter
public class ActivityUserDetailDto {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 邀请人
     */
    private String invitor;

    /**
     * 销售额
     */
    private BigDecimal salesNum;

}
