package com.gialen.tools.service;

import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.service.model.DelivertyModel;
import com.gialen.tools.service.model.OrderDetailModel;
import com.gialen.tools.service.model.UserAchievementModel;

import java.util.List;

/**
 * 快递工具服务接口
 */
public interface DeliveryService {

    /**
     * 批量更新订单的物流单号
     * @param modelList
     * @return
     */
    GLResponse<String> batchUpdateDeliveryCode(List<DelivertyModel> modelList);

}
