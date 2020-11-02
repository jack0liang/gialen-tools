package com.gialen.tools.common.enums;

/**
 * 订单出货类型
 * @author wong
 * @Date: 2020-09-09
 * @Version: 1.0
 */
public enum OrderTakeTypeEnum {

    STORE_TAKE((byte) 2,"门店出货"),
    SELF_TAKE((byte) 1,"总仓出货");
    private byte type;

    private String name;

    private OrderTakeTypeEnum(byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
