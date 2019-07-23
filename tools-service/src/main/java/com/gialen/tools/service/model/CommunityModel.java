package com.gialen.tools.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店经店董社群数据model
 * @author lupeibo
 * @date 2019-07-18
 */
@ApiModel("店经店董社群数据模型")
@Data
public class CommunityModel implements Serializable {
    private static final long serialVersionUID = -5056501004955099353L;

    @ApiModelProperty("本月新增店主数")
    private Integer monthNewStoreNum;

    @ApiModelProperty("本月新增直接店主数")
    private Integer monthNewDirectStoreNum;

    @ApiModelProperty("本月新增间接店主数")
    private Integer monthNewIndirectStoreNum;

    @ApiModelProperty("今日新增店主数")
    private Integer todayNewStoreNum;

    @ApiModelProperty("本月新增vip数")
    private Integer monthNewVipNum;

    @ApiModelProperty("今日新增vip数")
    private Integer todayNewVipNum;

    @ApiModelProperty("本月新增店经数")
    private Integer monthNewStoreManagerNum;

    @ApiModelProperty("统计月份")
    private Byte month;

    @ApiModelProperty("社群总人数")
    private Integer totalNum;

    @ApiModelProperty("倒计时，当用户为实习店经时才有倒计时数据")
    private String countDown;

    @ApiModelProperty("总店主数")
    private Integer totalStoreNum;

    @ApiModelProperty("总直接店主数")
    private Integer totalDirectStoreNum;

    @ApiModelProperty("总间接店主数")
    private Integer totalIndirectStoreNum;

    @ApiModelProperty("总vip数")
    private Integer totalVipNum;

    @ApiModelProperty("总店经数")
    private Integer totalStoreManagerNum;

}
