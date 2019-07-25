package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 店经店董社群数据dto
 * @author lupeibo
 * @date 2019-07-18
 */
@Getter
@Setter
public class CommunityDto {

    /**
     * 月新增店经数
     */
    private Integer monthNewStoreManagerNum;

    /**
     * 月新增店主数
     */
    private Integer monthNewStoreNum;

    /**
     * 月新增直接店主数
     */
    private Integer monthNewDirectStoreNum;

    /**
     * 月新增间接店主数
     */
    private Integer monthNewIndirectStoreNum;

    /**
     * 月新增vip数
     */
    private Integer monthNewVipNum;

    /**
     * 日新增店主数
     */
    private Integer dayNewStoreNum;

    /**
     * 日新增vip数
     */
    private Integer dayNewVipNum;

    /**
     * 下级总人数
     */
    private Integer totalNum;

    /**
     * 实习店经倒计时
     */
    private String countDown;

    /**
     * 总店经数
     */
    private Integer totalStoreManagerNum;

    /**
     * 总店主数
     */
    private Integer totalStoreNum;

    /**
     * 总直接店主数
     */
    private Integer totalDirectStoreNum;

    /**
     * 总间接店主数
     */
    private Integer totalIndirectStoreNum;

    /**
     * 总vip数
     */
    private Integer totalVipNum;

    /**
     * 店主id
     */
    private Long storeId;

    /**
     * 店主姓名
     */
    private String storeName;

}
