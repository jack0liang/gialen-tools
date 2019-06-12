package com.gialen.tools.service.model;

import lombok.Getter;
import lombok.Setter;

/**
 * UV数据模型
 * @author lupeibo
 * @date 2019-05-31
 */
@Getter
@Setter
public class UvDataModel {

    /**
     * 小程序uv
     */
    private Integer miniProgramUv;

    /**
     * 小程序uv环比
     */
    private Double miniProgramUvRelativeRatio;

    /**
     * app uv
     */
    private Integer appUv;

    /**
     * app uv环比
     */
    private Double appUvRelativeRatio;

    /**
     * h5 uv
     */
    private Integer h5Uv;

    /**
     * h5 uv环比
     */
    private Double h5UvRelativeRatio;

}
