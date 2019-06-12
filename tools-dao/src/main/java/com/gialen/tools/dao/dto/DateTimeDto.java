package com.gialen.tools.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lupeibo
 * @date 2019-05-27
 */
@Getter
@Setter
@AllArgsConstructor
public class DateTimeDto {

    private String startTime;

    private String endTime;

    private String relativeStartTime;

    private String relativeEndTime;

    private String todayStartTime;

    private String todayEndTime;

}
