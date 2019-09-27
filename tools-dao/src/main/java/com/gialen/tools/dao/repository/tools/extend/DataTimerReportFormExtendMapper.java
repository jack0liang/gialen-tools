package com.gialen.tools.dao.repository.tools.extend;

import com.gialen.tools.dao.dto.DateTimeDto;
import com.gialen.tools.dao.entity.tools.DataTimerReportForm;
import com.gialen.tools.dao.repository.tools.DataTimerReportFormMapper;

import java.util.List;

/**
 * @author wong
 * @Date: 2019-09-27
 * @Version: 1.0
 */
@org.springframework.stereotype.Repository
public interface DataTimerReportFormExtendMapper extends DataTimerReportFormMapper {
    List<DataTimerReportForm> countDataByTime(DateTimeDto dateTimeDto);
}
