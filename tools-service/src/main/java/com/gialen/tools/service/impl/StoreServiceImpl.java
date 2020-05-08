package com.gialen.tools.service.impl;

import com.gialen.common.model.GLResponse;
import com.gialen.tools.common.util.CsvUtil;
import com.gialen.tools.dao.entity.customer.Store;
import com.gialen.tools.dao.entity.customer.StoreExample;
import com.gialen.tools.dao.repository.customer.StoreMapper;
import com.gialen.tools.integration.RpcOrderCheckService;
import com.gialen.tools.service.StoreService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author lupeibo
 * @date 2019-09-05
 */
@Slf4j
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private RpcOrderCheckService rpcOrderCheckService;

    @Override
    public GLResponse batchChangeStoreCode(String filePath) {
        List<List<String>> datas = CsvUtil.readCsv(filePath);
        if(CollectionUtils.isEmpty(datas)) {
            log.error("csv数据为空");
            return GLResponse.fail("csv数据为空");
        }
        List<String> oldStoreCodes = Lists.newArrayList();
        Map<String, String> oldNewCodeMap = Maps.newHashMap();

        for(List<String> row : datas) {
            if(CollectionUtils.isEmpty(row) || StringUtils.isBlank(row.get(0))) {
                continue;
            }
            oldStoreCodes.add(row.get(0));
            oldNewCodeMap.put(row.get(0), row.get(1));
        }
        log.info("更新门店编码数量：{}", oldStoreCodes.size());

        List<Store> storeList = getStoreListByCodes(oldStoreCodes);
        if(CollectionUtils.isEmpty(storeList)) {
            return GLResponse.succ(null);
        }
        for(Store store : storeList) {
            store.setStoreCode(oldNewCodeMap.get(store.getStoreCode()));
        }
        //todo：批量更新门店编码 mainRomaStoreMapper.batchUpdateStore(storeList);
        return GLResponse.succ(null);
    }

    /**
     * 根据门店编码批量查询门店信息
     * @param storeCodeList
     * @return
     */
    private List<Store> getStoreListByCodes(List<String> storeCodeList) {
        if(CollectionUtils.isEmpty(storeCodeList)) {
            return Collections.emptyList();
        }
        StoreExample example = new StoreExample();
        example.createCriteria().andStoreCodeIn(storeCodeList);
        return storeMapper.selectByExample(example);
    }

    @Override
    public GLResponse verifyStorePickerCode(String code){
        return rpcOrderCheckService.verifyPickerCode(code);
    }
}
