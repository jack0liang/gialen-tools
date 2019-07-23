package com.gialen.tools.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lupeibo
 * @date 2019-07-22
 */
@Data
public class SeriesNodeVo implements Serializable  {

    private static final long serialVersionUID = 3499406478907335133L;

    private String name;

    private List<Integer> data;
}
