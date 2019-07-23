package com.gialen.tools.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * vip社群数据model
 * @author lupeibo
 * @date 2019-07-18
 */
@Data
public class VipCommunityModel implements Serializable {

    private static final long serialVersionUID = -1410509317998889056L;

    @ApiModelProperty("店主id")
    private Long storeId;

    @ApiModelProperty("店主姓名")
    private String storeName;

    @ApiModelProperty("当月新增vip")
    private Integer curMonthNewVipNum;

    @ApiModelProperty("今天新增vip")
    private Integer todayNewVipNum;

    @ApiModelProperty("上月新增vip")
    private Integer preMonthNewVipNum;

}
