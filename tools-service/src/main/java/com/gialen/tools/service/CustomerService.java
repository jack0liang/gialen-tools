package com.gialen.tools.service;

import com.gialen.common.model.GLResponse;

public interface CustomerService {

    /**
     * 添加店主
     * @return
     */
    GLResponse addKeepers();

    GLResponse userStoreCompare();

    /**
     * 初始化用户新老客标识
     * @return
     */
    GLResponse initUserNewOldFlag();
}
