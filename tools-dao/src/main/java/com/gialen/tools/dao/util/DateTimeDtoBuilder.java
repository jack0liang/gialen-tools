package com.gialen.tools.dao.util;

import com.gialen.tools.dao.dto.DateTimeDto;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author lupeibo
 * @date 2019-05-31
 */
public class DateTimeDtoBuilder {

    public static DateTimeDto createDateTimeDto(String startTime, String endTime,
                                                String relativeStartTime, String relativeEndTime,
                                                String todayStartTime, String todayEndTime) {
        DateTimeDto dateTimeDto = new DateTimeDto(startTime, endTime,
                relativeStartTime, relativeEndTime,
                todayStartTime, todayEndTime);
        return dateTimeDto;
    }

    public static DateTimeDto createDateTimeDto(Long startTime, Long endTime) {
        Date preStartTime = DateUtils.addDays(new Date(startTime), -1);
        Date preEndTime = DateUtils.addDays(new Date(endTime), -1);

        String startTimeStr = DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss");
        String endTimeStr = DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss");

        String preStartTimeStr = DateFormatUtils.format(preStartTime, "yyyy-MM-dd HH:mm:ss");
        String preEndTimeStr = DateFormatUtils.format(preEndTime, "yyyy-MM-dd HH:mm:ss");

        String todayStartTimeStr = DateFormatUtils.format(startTime,"yyyy-MM-dd") + " 00:00:00";
        String todayEndTimeStr = DateFormatUtils.format(endTime, "yyyy-MM-dd") + " 23:59:59";

        return createDateTimeDto(startTimeStr, endTimeStr,
                preStartTimeStr, preEndTimeStr,
                todayStartTimeStr,todayEndTimeStr);
    }

}


