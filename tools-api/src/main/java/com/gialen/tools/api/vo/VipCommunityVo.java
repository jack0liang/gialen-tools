package com.gialen.tools.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店经店董社群数据vo
 * @author lupeibo
 * @date 2019-07-18
 */
@ApiModel("vip社群数据vo")
@Data
public class VipCommunityVo implements Serializable {

    private static final long serialVersionUID = 8055538679843202956L;

    @ApiModelProperty("当前月份")
    private String curMonth;

    @ApiModelProperty("上月份")
    private String preMonth;

    @ApiModelProperty("当月新增vip")
    private Integer curMonthNewVipNum;

    @ApiModelProperty("今天新增vip")
    private Integer todayNewVipNum;

    @ApiModelProperty("上月新增vip")
    private Integer preMonthNewVipNum;

}
