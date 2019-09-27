package com.gialen.tools.common.enums;

/**
 * @author wong
 * @Date: 2019-09-27
 * @Version: 1.0
 */
public enum DataCountTypeEnum {

    ITEM_SALE(1, "销售额(含大礼包)"),
    ITEM_GIT_SALE(2, "大礼包销售额"),
    ITEM_UV_WX_MINI(3, "小程序uv"),
    ITEM_UV_APP(4, "APP_UV"),
    ITEM_UV_H5(5, "H5_UV"),
    ITEM_ORDER_WXMINI(6, "小程序订单支付数"),
    ITEM_ORDER_APP(7, "APP订单支付数"),
    ITEM_ORDER_H5(8, "H5订单支付数"),
    ITEM_ORDER_CREATE(9, "订单创建数"),
    ITEM_ORDER_PAIED(10, "订单支付成功数"),
    ITEM_STORE_CREATE(11, "新增店主"),
    ITEM_VIP_CREATE(12, "新增VIP数"),
    ITEM_ORDER_NEW(13, "新客订单数"),
    ITEM_ORDER_OLD(14, "老客订单数"),
    ;


    private String des;

    private Integer type;

    DataCountTypeEnum(Integer type, String des) {
        this.type = type;
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
