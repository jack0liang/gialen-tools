package com.gialen.tools.service;

import com.gialen.common.model.GLResponse;

public interface StoreService {

    GLResponse batchChangeStoreCode(String filePath);

    GLResponse<String> verifyStorePickerCode(String code);

}
