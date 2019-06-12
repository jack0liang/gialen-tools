package com.gialen.tools.service;

import com.gialen.common.model.GLResponse;

public interface DataToolsService {

    GLResponse getDataList(Long startTime, Long endTime, Byte dataType);

}
