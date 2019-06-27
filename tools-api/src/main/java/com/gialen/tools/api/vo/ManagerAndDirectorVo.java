package com.gialen.tools.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 店经店董vo
 * @author lupeibo
 * @date 2019-06-26
 */
@Data
public class ManagerAndDirectorVo implements Serializable {

    private Long userId;

    private String loginId;

    /**
     * 用户类型：3店经，4店董
     */
    private Byte userType;

}
