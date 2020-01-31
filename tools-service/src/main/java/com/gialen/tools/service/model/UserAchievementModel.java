package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户销售战绩model
 * @author lupeibo
 * @date 2019-06-25
 */
@Data
public class UserAchievementModel implements Serializable {

    private static final long serialVersionUID = -7340916043760803438L;

    /**
     * 店董/店经收益
     */
    private UserIncomeModel incomeModel;

    /**
     * 销售数据
     */
    private UserSalesModel salesModel;

    /**
     * 门店收益
     */
    private StoreIncomeModel storeIncomeModel;

}
