package com.gialen.tools.service.impl;

import com.gialen.common.beantools.Copier;
import com.gialen.tools.dao.dto.BigSuperMgrDto;
import com.gialen.tools.dao.entity.customer.UserLevelExample;
import com.gialen.tools.dao.entity.settlement.CommissionSettlement;
import com.gialen.tools.dao.entity.tools.BigSuperMgrSales;
import com.gialen.tools.dao.repository.customer.UserLevelMapper;
import com.gialen.tools.dao.repository.customer.UserRelationMapper;
import com.gialen.tools.dao.repository.order.extend.OrdersExtendMapper;
import com.gialen.tools.dao.repository.settlement.CommissionSettlementMapper;
import com.gialen.tools.dao.repository.tools.BigSuperMgrSalesMapper;
import com.gialen.tools.service.FinanceService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lupeibo
 * @date 2020-01-07
 */
@Slf4j
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private UserRelationMapper userRelationMapper;

    @Autowired
    private UserLevelMapper userLevelMapper;

    @Autowired
    private OrdersExtendMapper ordersExtendMapper;

    @Autowired
    private BigSuperMgrSalesMapper bigSuperMgrSalesMapper;

    @Autowired
    private CommissionSettlementMapper commissionSettlementMapper;

    @Override
    public void genBigSuperMgrCommission(Integer month) {
        //查询大店经列表
        List<BigSuperMgrDto> bigSuperMgrDtoList = userRelationMapper.getBigSuperMgrList();

        for(BigSuperMgrDto bigSuperMgrDto : bigSuperMgrDtoList) {
            List<Long> childUserIdList = getAllChildUserIds(bigSuperMgrDto.getSuperMgrId(), month);
            Long storeManagerNum = countStoreMangerNum(childUserIdList);

            childUserIdList.add(bigSuperMgrDto.getSuperMgrId());//添加自己
            BigDecimal bigGiftBagSales = ordersExtendMapper.calBigSuperMgrSales(month, childUserIdList, (byte) 3); //大礼包销量
            BigDecimal totalSales = ordersExtendMapper.calBigSuperMgrSales(month, childUserIdList, null); //总销量

            BigDecimal totalMasterCommission = commissionSettlementMapper.calStoreMasterCommission(childUserIdList, month); //店主佣金
            BigDecimal totalManagerCommission = commissionSettlementMapper.calStoreManagerCommission(childUserIdList, month); //店经佣金

            bigSuperMgrDto.setStoreMangerNum(storeManagerNum.intValue());
            bigSuperMgrDto.setBigGiftBagSales(bigGiftBagSales);
            bigSuperMgrDto.setTotalSales(totalSales);
            bigSuperMgrDto.setTotalCommission(totalMasterCommission.add(totalManagerCommission));
            bigSuperMgrDto.setMonth(month);
            log.info("店经：{}", bigSuperMgrDto);
        }
        batchInsertBigSuperMgrSales(bigSuperMgrDtoList);
    }

    /**
     * 获取用户的全部下级用户id列表
     * @param parentId
     * @return
     */
    private List<Long> getAllChildUserIds(Long parentId, Integer month) {
        List<Long> childUserIdList = Lists.newArrayList();

        List<Long> directUserIdList = getChildUserList(parentId, month);
        if(CollectionUtils.isEmpty(directUserIdList)) {
            return childUserIdList;
        }
        childUserIdList.addAll(directUserIdList);
        List<Long> userIds = getChildUserList(directUserIdList, month);
        childUserIdList.addAll(userIds);
        while(CollectionUtils.isNotEmpty(userIds)) {
            userIds = getChildUserList(userIds, month);
            childUserIdList.addAll(userIds);
        }
        return childUserIdList;
    }

    /**
     * 统计店主店经个数
     * @param userIds
     */
    private Long countStoreMangerNum(List<Long> userIds) {
        if(CollectionUtils.isEmpty(userIds)) {
            return 0L;
        }
        UserLevelExample example = new UserLevelExample();
        example.createCriteria().andUserIdIn(userIds).andLevelTypeIn(Lists.newArrayList((byte)2,(byte)3,(byte)4,(byte)5));
        return userLevelMapper.countByExample(example);
    }

    /**
     * 批量查询用户的直接下级Id列表
     * @param invitorIds
     * @return
     */
    private List<Long> getChildUserList(List<Long> invitorIds, Integer month) {
        return userRelationMapper.getChildUserListByIds(invitorIds, month);
    }

    /**
     * 查询用户的直接下级Id列表
     * @param invitorId
     * @return
     */
    private List<Long> getChildUserList(Long invitorId, Integer month) {
        return userRelationMapper.getChildUserList(invitorId, month);
    }

    /**
     * 批量写入
     * @param bigSuperMgrDtoList
     */
    private void batchInsertBigSuperMgrSales(List<BigSuperMgrDto> bigSuperMgrDtoList) {
        if(CollectionUtils.isEmpty(bigSuperMgrDtoList)) {
            return;
        }
        List<BigSuperMgrSales> bigSuperMgrSalesList = Lists.newArrayListWithExpectedSize(bigSuperMgrDtoList.size());
        bigSuperMgrDtoList.forEach(bigSuperMgrDto -> bigSuperMgrSalesList.add(Copier.copy(bigSuperMgrDto, new BigSuperMgrSales())));

        bigSuperMgrSalesMapper.batchInsertBigSuperMgrSales(bigSuperMgrSalesList);
    }
}
