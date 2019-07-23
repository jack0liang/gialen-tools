package com.gialen.tools.common.enums;

/**
 * 社群数据查询类型枚举
 */
public enum CommunityQueryTypeEnum {

    All((byte)1, "全部"),

    CUR_MONTH((byte)2, "当月");

    private byte code;

    private String name;

    private CommunityQueryTypeEnum(byte code, String name) {
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
