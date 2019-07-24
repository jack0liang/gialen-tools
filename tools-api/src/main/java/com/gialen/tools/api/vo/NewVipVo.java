package com.gialen.tools.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店主vip
 * @author lupeibo
 * @date 2019-07-18
 */
@ApiModel("店主vip")
@Data
public class NewVipVo implements Serializable {

    private static final long serialVersionUID = -186025395385494161L;

    @ApiModelProperty("店主姓名")
    private String storeName;

    @ApiModelProperty("当月新增vip")
    private Integer curMonthNewVipNum;

    @ApiModelProperty("今日新增vip")
    private Integer todayNewVipNum;

}
