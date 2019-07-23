package com.gialen.tools.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lupeibo
 * @date 2019-07-22
 */
@Data
public class ChartDataVo implements Serializable  {

    private static final long serialVersionUID = -6967409938260911322L;

    private List<String> categories;

    private List<SeriesNodeVo> series;
}
