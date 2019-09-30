package com.gialen.tools.common.enums;

public enum PlatFormTypeEnum {

    APP("1", "app"),
    WX_MINI_PROGRAM("2", "小程序"),
    H5("3", "手机H5");
//    GROUP_WX_PUBLIC_ACCOUNTS("GROP_WX_PUBLIC_ACCOUNTS", "集团公众号"),

    private String code;

    private String name;

    PlatFormTypeEnum(String code, String name) {
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
