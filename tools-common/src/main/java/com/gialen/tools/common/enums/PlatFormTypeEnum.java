package com.gialen.tools.common.enums;

public enum PlatFormTypeEnum {

    APP_STORE("APP_STORE", "店主版app"),

    APP_VIP("APP_VIP", "vip app"),

    GROUP_WX_PUBLIC_ACCOUNTS("GROP_WX_PUBLIC_ACCOUNTS", "集团公众号"),

    MOBILE("MOBILE", "手机H5"),

    WX_MINI_PROGRAM("WX_MINI_PROGRAM", "小程序");

    private String code;

    private String name;

    private PlatFormTypeEnum(String code, String name) {
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
