package com.gialen.tools.api.vo;

import com.gialen.tools.common.util.SheetTitle;
import com.gialen.tools.common.util.ToCell;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量导入物流单号vo
 * @auther wong
 * @Date: 2019-06-28
 * @Version: 1.0
 */
@Data
@SheetTitle({"订单号","物流单号","物流公司"})

public class DeliveryExcelVo implements Serializable {

    private static final long serialVersionUID = 8430542084088745265L;
    /**
     * 订单号
     */
    @ToCell(position = 0)
    private String orderSn;

    /**
     * 物流单号
     */
    @ToCell(position = 1)
    private String deliverySn;

    /**
     * 物流公司名称
     */
    @ToCell(position = 2)
    private String deliveryCompanyName;

}
