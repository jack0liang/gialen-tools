package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lupeibo
 * @date 2019-06-03
 */
@Getter
@Setter
public class UserDataDto {

    /**
     * 统计时间
     */
    private String countTime;

    /**
     * vip数
     */
    private Integer vipNum;

    /**
     * 店主数
     */
    private Integer storeNum;

    /**
     * 新客订单量
     */
    private Integer newUserOrderNum;

    /**
     * 老客订单量
     */
    private Integer oldUserOrderNum;

}
