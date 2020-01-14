package com.gialen.tools.service;

/**
 * 财务需求工具接口
 */
public interface FinanceService {

    /**
     * 根据月份生成大店经佣金核算表
     * @param month
     * @return
     */
    void genBigSuperMgrCommission(Integer month);

}
