package com.gialen.tools.common.enums;

public enum DataTypeEnum {

    SALES_DATA((byte)1, "总销售"),

    UV_DATA((byte)2, "uv"),

    ORDER_DATA((byte)3, "订单数"),

    CONVERSION_DATA((byte)4, "订单转化"),

    USER_DATA((byte)5, "用户");

    private byte type;

    private String name;

    private DataTypeEnum(byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
