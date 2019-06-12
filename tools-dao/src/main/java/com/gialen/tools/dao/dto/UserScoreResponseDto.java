package com.gialen.tools.dao.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lupeibo
 * @date 2019-05-22
 */
@Getter
@Setter
public class UserScoreResponseDto {

    private String outerUserId;

    private Integer reduceScore;

    private String phone;

    private String userType;
}
