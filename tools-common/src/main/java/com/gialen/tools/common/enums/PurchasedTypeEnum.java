package com.gialen.tools.common.enums;

/**
 * 用户是否开单枚举
 */
public enum PurchasedTypeEnum {

    PURCHASED((byte)1, "已开单"),

    NOT_PURCHASED((byte)2, "未开单");

    private byte code;

    private String name;

    private PurchasedTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
