package com.gialen.tools.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 活跃店主明细model
 * @author lupeibo
 * @date 2019-07-18
 */
@ApiModel("活跃店主明细model")
@Data
public class StoreActivityDetailModel implements Serializable {

    private static final long serialVersionUID = -7845296858681616014L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("邀请人")
    private String invitor;

    @ApiModelProperty("销售额")
    private BigDecimal salesNum;
}
