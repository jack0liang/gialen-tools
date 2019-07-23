package com.gialen.tools.service.convertor;

import com.gialen.common.beantools.Copier;
import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import com.gialen.tools.service.model.CustomerModel;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author lupeibo
 * @date 2019-07-22
 */
public class CustomerConvertor {

    /**
     * 用户实体列表转成model列表
     * @param customerList
     * @return
     */
    public static List<CustomerModel> convertCustomerListToModelList(List<BlcCustomer> customerList) {
        if(CollectionUtils.isEmpty(customerList)) {
            return Collections.emptyList();
        }
        List<CustomerModel> modelList = Lists.newArrayListWithCapacity(customerList.size());
        customerList.forEach(blcCustomer -> {
            CustomerModel model = Copier.copy(blcCustomer, new CustomerModel());
            model.setIsTempStoreCustomer(blcCustomer.getIsTempStoreCustomer() == 1);
            modelList.add(model);
        });
        return modelList;
    }

}
