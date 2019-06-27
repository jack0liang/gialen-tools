package com.gialen.tools.common.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum UserTypeEnum {
    UNKNOWN((byte)0, "UNKNOWN", "未知"),

    VIP((byte)1, "VIP","VIP"),
    STORE((byte)2, "STORE", "店主"),
    STORE_MANAGER((byte)3, "STORE_MANAGER", "店经"),
    STORE_DIRECTOR((byte)4,"STORE_DIRECTOR", "店董"),

    NEW_USER((byte)5,"NEW_USER", "新客"),
    OLD_USER((byte)6,"OLD_USER", "老客");

    private Byte type;

    private String code;

    private String name;

    private UserTypeEnum(Byte type, String code, String name) {
        this.type = type;
        this.code = code;
        this.name = name;
    }

    public static UserTypeEnum getByType(Byte type) {
        if(type == null) {
            return UNKNOWN;
        }
        switch (type) {
            case 1 :
                return VIP;
            case 2 :
                return STORE;
            case 3 :
                return STORE_MANAGER;
            case 4 :
                return STORE_DIRECTOR;
            case 5 :
                return NEW_USER;
            case 6 :
                return OLD_USER;
            default:
                log.warn("not found this enum type!");
        }
        return UNKNOWN;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
