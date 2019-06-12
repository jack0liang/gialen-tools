package com.gialen.tools.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lupeibo
 * @date 2019-05-27
 */
@Getter
@Setter
public class DataToolsModel {

    private String title;

    private Boolean open = true;

    private List<ItemModel> items;
}
