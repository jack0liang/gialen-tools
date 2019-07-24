package com.gialen.tools.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 饼状图数据项vo
 * @author lupeibo
 * @date 2019-07-22
 */
@Data
public class PieSeriesNodeVo implements Serializable  {

    private static final long serialVersionUID = 8669148353334301276L;

    private String name;

    private Integer data;

    private BigDecimal rate;

    public PieSeriesNodeVo() {}

    public PieSeriesNodeVo(String name, Integer data, BigDecimal rate) {
        this.name = name;
        this.data = data;
        this.rate = rate;
    }
}
