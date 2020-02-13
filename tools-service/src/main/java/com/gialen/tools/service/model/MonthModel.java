package com.gialen.tools.service.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lupeibo
 * @date 2020-02-07
 */
@Data
public class MonthModel implements Serializable {

    private static final long serialVersionUID = 1852381555563124188L;

    private String monthStr;

    private Integer month;
}
