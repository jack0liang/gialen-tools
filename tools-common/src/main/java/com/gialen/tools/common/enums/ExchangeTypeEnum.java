package com.gialen.tools.common.enums;

public enum ExchangeTypeEnum {

    EXCHANGE_All((byte)1, "全量抵扣"),

    EXCHANGE_TEN((byte)2, "对满足10元的爱心值进行兑换，剩余爱心值积累"),

    EXCHANGE_FIVE_TO_TEN((byte)3, "补发满足5-10元"),

    EXCHANGE_GREATER_AND_EQUALS_FIVE((byte)4, "爱心值大于等于500，发放娇币");

    private byte type;

    private String name;

    private ExchangeTypeEnum(byte type, String name) {
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
