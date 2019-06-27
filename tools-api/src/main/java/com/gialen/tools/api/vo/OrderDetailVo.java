package com.gialen.tools.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单明细vo
 * @author lupeibo
 * @date 2019-05-27
 */
@Data
public class OrderDetailVo implements Serializable {
    private static final long serialVersionUID = 7122621824069087113L;
    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderCreateTime;

    /**
     * 子订单总价
     */
    private BigDecimal subOrderPrice;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 子订单状态
     */
    private Byte subOrderStatus;

    /**
     * 佣金
     */
    private BigDecimal commission;

}
