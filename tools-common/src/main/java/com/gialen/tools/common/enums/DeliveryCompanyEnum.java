package com.gialen.tools.common.enums;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 物流公司枚举
 */
@Slf4j
public enum DeliveryCompanyEnum {
    UNKNOWN("UNKNOWN", "未知"),

    SHUNFENG("shunfeng","顺丰"),
    ZHONGTONG("zhongtong", "中通快递"),
    YUNDA("yunda", "韵达"),
    SHENTONG("shentong", "申通快递"),
    EMS("ems", "EMS"),
    YUANTONG("yuantong", "圆通快递"),
    HUITONGKUAIDI("huitongkuaidi", "汇通快递"),
    POSTB("postb", "邮政小包"),
    SUNING("suning", "苏宁物流"),
    KUAYUE("kuayue", "跨越"),
    DEBANGKUAIDI("debangkuaidi", "德邦");


    private String code;

    private String name;

    private DeliveryCompanyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DeliveryCompanyEnum getByCodeOrName(String code) {
        if(StringUtils.isBlank(code)) {
            return UNKNOWN;
        }
        if (SHUNFENG.code.equalsIgnoreCase(code) || SHUNFENG.name.equals(code)) {
            return SHUNFENG;
        } else if (ZHONGTONG.code.equalsIgnoreCase(code) || ZHONGTONG.name.equals(code)) {
            return ZHONGTONG;
        } else if (YUNDA.code.equalsIgnoreCase(code) || YUNDA.name.equals(code)) {
            return YUNDA;
        } else if (SHENTONG.code.equalsIgnoreCase(code) || SHENTONG.name.equals(code)) {
            return SHENTONG;
        } else if (EMS.code.equalsIgnoreCase(code) || EMS.name.equals(code)) {
            return EMS;
        } else if (YUANTONG.code.equalsIgnoreCase(code) || YUANTONG.name.equals(code)) {
            return YUANTONG;
        } else if (HUITONGKUAIDI.code.equalsIgnoreCase(code) || HUITONGKUAIDI.name.equals(code)) {
            return HUITONGKUAIDI;
        } else if (POSTB.code.equalsIgnoreCase(code) || POSTB.name.equals(code)) {
            return POSTB;
        } else if (SUNING.code.equalsIgnoreCase(code) || SUNING.name.equals(code)) {
            return SUNING;
        } else if (KUAYUE.code.equalsIgnoreCase(code) || KUAYUE.name.equals(code)) {
            return KUAYUE;
        } else if (DEBANGKUAIDI.code.equalsIgnoreCase(code) || DEBANGKUAIDI.name.equals(code)) {
            return DEBANGKUAIDI;
        } else {
            log.warn("not found this enum : {}", code);
            return UNKNOWN;
        }
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
