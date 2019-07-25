package com.gialen.tools.service.model;

import lombok.Data;

import java.util.Date;

/**
 * @author lupeibo
 * @date 2019-07-16
 */
@Data
public class CustomerModel {

    /**
     * 用户id
     */
    private Long customerId;

    /**
     * 注册时间
     */
    private Date dateCreated;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 上级用户id
     */
    private Long higherCustomerId;

    /**
     * 店主店经等级，1：店主，4：店经
     */
    private Byte userLevelNewId;

    /**
     * 用户类型，VIP or STORE
     */
    private String userType;

    /**
     * 是否体验店主
     */
    private Boolean isTempStoreCustomer;

    /**
     * 是否实习店经
     */
    private Boolean isTempSuperCustomer;

}
