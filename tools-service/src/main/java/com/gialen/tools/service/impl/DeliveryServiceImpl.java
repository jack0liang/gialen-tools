package com.gialen.tools.service.impl;

import com.gialen.common.model.GLResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.tools.common.enums.DeliveryCompanyEnum;
import com.gialen.tools.dao.entity.gialen.BlcOrder;
import com.gialen.tools.dao.repository.gialenMain.BlcOrderMapper;
import com.gialen.tools.service.DeliveryService;
import com.gialen.tools.service.model.DelivertyModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * 物流工具服务实现类
 * @author lupeibo
 * @date 2019-06-25
 */
@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Resource(name = "mainBlcOrderMapper")
    private BlcOrderMapper blcOrderMapper;


    @Override
    public GLResponse<String> batchUpdateDeliveryCode(List<DelivertyModel> modelList) {
        GLResponse<String> response = checkDeliveryModelList(modelList);
        if(! response.getSuccess()) {
            return response;
        }
        List<BlcOrder> deliveryList = Lists.newArrayListWithCapacity(modelList.size());
        modelList.forEach(delivertyModel -> {
            BlcOrder order = new BlcOrder();
            order.setOrderNumber(delivertyModel.getOrderSn());
            order.setOrderDeliveryNumber(delivertyModel.getDeliverySn());
            order.setDeliveryCompany(DeliveryCompanyEnum.getByCodeOrName(delivertyModel.getDeliveryCompanyName()).getCode());
            deliveryList.add(order);
        });
        try {
            List<List<BlcOrder>> partitionList = Lists.partition(deliveryList, 500);
            for(List<BlcOrder> partiton : partitionList) {
                blcOrderMapper.batchUpdateOrderDelivery(partiton);
            }
        } catch (Exception e) {
            log.error("batchUpdateDeliveryCode exception : {}", e.getMessage());
            return GLResponse.fail(ResponseStatus.FAIL.getCode(), "批量导入失败");
        }
        return response;
    }

    private GLResponse<String> checkDeliveryModelList(List<DelivertyModel> modelList) {
        Iterator<DelivertyModel> itr = modelList.iterator();
        while(itr.hasNext()) {
            DelivertyModel model = itr.next();
            String orderSn = model.getOrderSn();
            String deliverySn = model.getDeliverySn();
            String deliveryCompanyName = model.getDeliveryCompanyName();
            if (StringUtils.isBlank(orderSn) && StringUtils.isBlank(deliverySn) &&
                    StringUtils.isBlank(deliveryCompanyName)) {
                itr.remove();
                continue;
            }
            if(StringUtils.isBlank(orderSn) || StringUtils.isBlank(deliverySn) ||
                    StringUtils.isBlank(deliveryCompanyName)) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "有空数据，请检查修改！");
            }
            if(DeliveryCompanyEnum.UNKNOWN.equals(DeliveryCompanyEnum.getByCodeOrName(deliveryCompanyName))) {
                return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "物流公司【"+deliveryCompanyName+"】不存在！请检查数据");
            }
        }
        return GLResponse.succ("");
    }
}
