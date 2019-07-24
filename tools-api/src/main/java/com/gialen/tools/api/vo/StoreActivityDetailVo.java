package com.gialen.tools.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lupeibo
 * @date 2019-07-24
 */
@ApiModel("活跃店主明细")
@Data
public class StoreActivityDetailVo implements Serializable {

    private static final long serialVersionUID = 2880616176702089977L;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("邀请人")
    private String invitor;

    @ApiModelProperty("销售额")
    private BigDecimal salesNum;

}
