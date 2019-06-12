package com.gialen.tools.common.enums;

public enum RelativeDataTypeEnum {

    DATA((byte)1, "当前数据"),
    RELATIVE_DATA((byte)2,"环比数据");

    private byte type;

    private String name;

    private RelativeDataTypeEnum(byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
