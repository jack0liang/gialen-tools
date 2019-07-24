package com.gialen.tools.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 店主活跃数据model
 * @author lupeibo
 * @date 2019-07-18
 */
@ApiModel("店主活跃数据model")
@Data
public class StoreActivityModel implements Serializable {

    private static final long serialVersionUID = -2794341962689935335L;

    @ApiModelProperty("当前月份")
    private String curMonth;

    @ApiModelProperty("上月份")
    private String preMonth;

    @ApiModelProperty("当前月应培训人数(新增店主)")
    private Integer curMonthNewStoreNum;

    @ApiModelProperty("上月应培训人数(上月新增店主)")
    private Integer preMonthNewStoreNum;

    @ApiModelProperty("已开单店主数")
    private Integer purchasedStoreNum;

    @ApiModelProperty("已开单率")
    private BigDecimal purchasedRate;

    @ApiModelProperty("未开单店主数")
    private Integer notPurchasedStoreNum;

    @ApiModelProperty("未开单率")
    private BigDecimal notPurchasedRate;
}
