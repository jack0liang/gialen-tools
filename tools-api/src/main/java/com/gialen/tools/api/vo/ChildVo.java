package com.gialen.tools.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lupeibo
 * @date 2019-07-22
 */
@Data
public class ChildVo implements Serializable  {

    private static final long serialVersionUID = -8686289857232791994L;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registTime;

    /**
     * 是否体验店主
     */
    private Boolean isTempStore;
}
