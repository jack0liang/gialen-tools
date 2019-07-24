package com.gialen.tools.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 饼状图数据vo
 * @author lupeibo
 * @date 2019-07-22
 */
@Data
public class PieChartDataVo implements Serializable  {

    private static final long serialVersionUID = 8593625963249119031L;

    private List<PieSeriesNodeVo> series;
}
