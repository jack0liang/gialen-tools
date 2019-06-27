package com.gialen.tools.common.enums;

/**
 * 子订单状态枚举
 */
public enum SubOrderStatusEnum {

    INITIAL ((byte)1, "初始"),

    FINISH ((byte)2, "完结"),

    CANCEL ((byte)3, "取消");

    private byte code;

    private String name;

    private SubOrderStatusEnum(byte code, String name) {
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
