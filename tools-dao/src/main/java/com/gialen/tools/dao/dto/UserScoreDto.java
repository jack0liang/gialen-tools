package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lupeibo
 * @date 2019-05-22
 */
@Setter
@Getter
public class UserScoreDto {

    private String outerUserId;

    private String phone;

    private Integer score;

    private String userType;
}
