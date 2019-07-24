package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lupeibo
 * @date 2019-07-24
 */
@Getter
@Setter
public class ActivityUserDataDto {

    /**
     * 月活跃店主数量
     */
    private Integer monthActivityStoreNum;

    /**
     * 月活跃VIP数量
     */
    private Integer monthActivityVipNum;

    /**
     * 月静默店主数量
     */
    private Integer monthSilenceStoreNum;

    /**
     * 月静默VIP数量
     */
    private Integer monthSilenceVipNum;
}
