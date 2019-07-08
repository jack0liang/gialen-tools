package com.gialen.tools.service.impl;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.common.model.PageResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.tools.common.enums.DateTypeEnum;
import com.gialen.tools.common.enums.DeliveryCompanyEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.common.util.ThreadPoolManager;
import com.gialen.tools.dao.dto.OrderDetailDto;
import com.gialen.tools.dao.dto.OrderQueryDto;
import com.gialen.tools.dao.dto.UserIncomeDto;
import com.gialen.tools.dao.entity.gialen.*;
import com.gialen.tools.dao.entity.tools.ManagerAndDirector;
import com.gialen.tools.dao.entity.tools.ManagerAndDirectorExample;
import com.gialen.tools.dao.repository.gialen.BlcCustomerMapper;
import com.gialen.tools.dao.repository.gialen.BlcOrderMapper;
import com.gialen.tools.dao.repository.gialen.RomaStoreMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementDetailMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementMapper;
import com.gialen.tools.dao.repository.tools.ManagerAndDirectorMapper;
import com.gialen.tools.service.DeliveryService;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.exception.StoreManagerServiceException;
import com.gialen.tools.service.model.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 物流工具服务实现类
 * @author lupeibo
 * @date 2019-06-25
 */
@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
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
