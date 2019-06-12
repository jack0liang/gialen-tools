package com.gialen.tools.common.enums;

public enum UserTypeEnum {

    STORE("STORE", "店主"),
    VIP("VIP","VIP"),

    NEW_USER("NEW_USER", "新客"),
    OLD_USER("OLD_USER", "老客");

    private String code;

    private String name;

    private UserTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
