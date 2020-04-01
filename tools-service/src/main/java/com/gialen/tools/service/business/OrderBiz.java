package com.gialen.tools.service.business;

import com.gialen.tools.dao.entity.order.PayRecord;
import com.gialen.tools.dao.entity.order.PayRecordExample;
import com.gialen.tools.dao.repository.order.PayRecordMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author lupeibo
 * @date 2020-03-05
 */
@Slf4j
@Service
public class OrderBiz {

    @Autowired
    public PayRecordMapper payRecordMapper;

    /**
     * 查询用户是否完成支付
     * @param userId
     * @return
     */
    public Boolean isPaied(Long userId) {
        PayRecordExample example = new PayRecordExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andPayStatusEqualTo((byte)1);
        example.setLimit(1);
        List<PayRecord> payRecordList = payRecordMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(payRecordList) ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 批量查询已支付成功的用户id
     * @param userIds
     * @return
     */
    public List<Long> getPaidUserIdList(List<Long> userIds) {
        PayRecordExample example = new PayRecordExample();
        example.createCriteria().andUserIdIn(userIds).andPayStatusEqualTo((byte) 1);
        List<PayRecord> payRecordList = payRecordMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(payRecordList)) {
            return Lists.newArrayList();
        }
        Set<Long> userIdSet = payRecordList.stream().map(PayRecord::getUserId).collect(Collectors.toCollection((Supplier<Set<Long>>) LinkedHashSet::new));
        return Lists.newArrayList(userIdSet);
    }
}