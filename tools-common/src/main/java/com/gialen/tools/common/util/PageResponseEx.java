package com.gialen.tools.common.util;

import com.gialen.common.model.PageResponse;

/**
 * 扩展分页字段
 * @author lupeibo
 * @date 2019-07-24
 */
public class PageResponseEx<T> extends PageResponse {

    private byte month;

    public static <T> PageResponseEx<T> buildPageResponseEx(PageResponse<T> response, byte month) {
        PageResponseEx<T> responseEx = new PageResponseEx<>();
        responseEx.setMonth(month);
        responseEx.setCurrPage(response.getCurrPage());
        responseEx.setPageSize(response.getPageSize());
        responseEx.setTotalCount(response.getTotalCount());
        responseEx.setList(response.getList());
        return responseEx;
    }

    public Byte getMonth() {
        return month;
    }

    public void setMonth(Byte month) {
        this.month = month;
    }
}
