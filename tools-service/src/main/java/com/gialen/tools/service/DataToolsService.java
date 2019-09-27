package com.gialen.tools.service;

import com.gialen.common.model.GLResponse;
import com.gialen.tools.service.model.DataToolsModel;
import com.gialen.tools.service.model.UserDataModel;

public interface DataToolsService {

    GLResponse getDataList(Long startTime, Long endTime, Byte dataType);

    UserDataModel countUserOrderData(Long startTime, Long endTime);

    DataToolsModel getUserData(Long startTime, Long endTime);

}
