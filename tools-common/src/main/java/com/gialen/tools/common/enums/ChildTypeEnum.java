package com.gialen.tools.common.enums;

import lombok.extern.slf4j.Slf4j;

/**
 * 下级会员类型枚举：1vip，2直接店主，3间接店主，4店经
 */
@Slf4j
public enum ChildTypeEnum {

    VIP((byte)1, "VIP"),

    DIRECT_STORE((byte)2, "直接店主"),

    INDIRECT_STORE((byte)3, "间接店主"),

    STORE_MANAGER((byte)4, "店经");

    private byte code;

    private String name;

    private ChildTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ChildTypeEnum getByType(Byte code) {
        if(code == null) {
            return null;
        }
        switch (code) {
            case 1 :
                return VIP;
            case 2 :
                return DIRECT_STORE;
            case 3 :
                return INDIRECT_STORE;
            case 4 :
                return STORE_MANAGER;
            default:
                log.warn("not found this enum type!");
        }
        return null;
    }

    public static ChildTypeEnum getByIndexForDirector(Byte index) {
        if(index == null) {
            log.warn("index is null! defaul set to vip.");
            return VIP;
        }
        switch (index) {
            case 1 :
                return VIP;
            case 2 :
                return DIRECT_STORE;
            case 3 :
                return STORE_MANAGER;
            default:
                log.warn("not found this index : {}! defaul set to vip.", index);
                return VIP;
        }
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
