package com.gialen.tools.service.business;

import com.gialen.tools.dao.entity.order.PayRecord;
import com.gialen.tools.dao.entity.order.PayRecordExample;
import com.gialen.tools.dao.repository.order.PayRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}