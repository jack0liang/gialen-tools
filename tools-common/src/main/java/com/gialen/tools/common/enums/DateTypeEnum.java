package com.gialen.tools.common.enums;

/**
 * 统计日期类型
 */
public enum DateTypeEnum {

    CUR_MONTH((byte)1, "当月"),

    PRE_MONTH((byte)2, "上月");

    private byte code;

    private String name;

    private DateTypeEnum(byte code, String name) {
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
