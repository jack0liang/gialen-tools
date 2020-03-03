package com.gialen.tools.service.impl;

import com.gialen.common.model.GLResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.tools.common.util.CsvUtil;
import com.gialen.tools.dao.entity.customer.Store;
import com.gialen.tools.dao.entity.customer.UserLevel;
import com.gialen.tools.dao.entity.customer.UserLevelChangeLog;
import com.gialen.tools.dao.entity.customer.UserRelation;
import com.gialen.tools.dao.entity.order.Orders;
import com.gialen.tools.dao.entity.order.OrdersExample;
import com.gialen.tools.dao.repository.order.OrdersMapper;
import com.gialen.tools.service.CustomerService;
import com.gialen.tools.service.business.CustomerBiz;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lupeibo
 * @date 2019-07-16
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private String filePath = "/Users/lupeibo/Downloads/add_keepers.csv";

    private String filePath2 = "/Users/lupeibo/Downloads/keepers.csv";

    private String filePath3 = "/Users/lupeibo/Downloads/phone.csv";

    @Resource
    private CustomerBiz customerBiz;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public GLResponse addKeepers() {
        List<List<String>> datas = CsvUtil.readCsv(filePath);
        if(CollectionUtils.isEmpty(datas)) {
            log.error("csv数据为空");
            return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(),"csv数据为空");
        }
        List<String> nameList = Lists.newArrayList();
        List<String> phoneList = Lists.newArrayList();
        List<String> storeCodeList = Lists.newArrayList();

        datas.forEach(row -> nameList.add(row.get(0)));
        datas.forEach(row -> phoneList.add(row.get(1)));
        datas.forEach(row -> storeCodeList.add(row.get(2)));

        for(int i = 0; i < phoneList.size(); i++) {
            if(StringUtils.isBlank(nameList.get(i)) && StringUtils.isBlank(phoneList.get(i))) {
                break;
            }
            addKeeper(nameList.get(i), phoneList.get(i), storeCodeList.get(i));
//            changeUserStore(phoneList.get(i), storeCodeList.get(i));
//            changeUserLevelLogChannel(phoneList.get(i));
        }

        return GLResponse.succ("处理成功");
    }

    private void addKeeper(String userName, String phone, String storeCode) {
        log.info("username={},phone={},storeCode={}", userName, phone, storeCode);
        try {
            Store store = customerBiz.getStoreByCode(storeCode);
            if(store == null) {
                log.error("[{}] 门店不存在", storeCode);
                return;
            }
            Long userId = customerBiz.getUserIdByPhone(phone);
            if(userId != null && userId > 0L) {
                UserLevel userLevel = customerBiz.getUserLevel(userId);
                if(userLevel == null) {
                    log.error("[{}] userLevel is null", userId);
                }
                Byte levelType = userLevel.getLevelType();
                if(levelType == (byte) 1) {
                    UserRelation userRelation = customerBiz.getUserRelation(userId);
                    Store oldStore = customerBiz.getStoreById(userRelation.getStoreId());
                    customerBiz.updateUserToKeeper(userLevel, userId, store, oldStore);
                }
            } else {
                //注册用户
                log.info("注册新用户{}", phone);
                customerBiz.registerUser(userName, phone, store);
            }
        } catch (Exception e) {
            log.info("phone={} 未处理成功", phone);
            log.error("", e);
        }

    }

    private void changeUserStore(String phone, String storeCode) {
        log.info("phone={},storeCode={}", phone, storeCode);
        try {
            Store store = customerBiz.getStoreByCode(storeCode);
            if(store == null) {
                log.error("[{}] 门店不存在", storeCode);
                return;
            }
            Long userId = customerBiz.getUserIdByPhone(phone);
            if(userId != null && userId > 0L) {
                customerBiz.changeUserStore(userId, store);
            }
        } catch (Exception e) {
            log.info("phone={} 未处理成功", phone);
            log.error("", e);
        }
    }

    /**
     * 将0元升级店主的用户等级变更日志channel设置为8(0元升店主)
     * @param phone
     */
    private void changeUserLevelLogChannel(String phone) {
        try {
            Long userId = customerBiz.getUserIdByPhone(phone);
            if(userId == null || userId <= 0L) {
                return;
            }
            UserLevelChangeLog changeLog = customerBiz.getUserLevelChangeLog(userId);
            if(changeLog == null) {
                return;
            }
            customerBiz.updateUserLevelLog(changeLog.getId());
            log.info("userId={}, phone={}， channel 10改为8", userId, phone);
        } catch (Exception e) {
            log.error("phone={} 处理异常：{}", phone, e);
        }
    }

    public GLResponse userStoreCompare() {
        List<List<String>> datas = CsvUtil.readCsv(filePath2);
        if(CollectionUtils.isEmpty(datas)) {
            log.error("csv数据为空");
            return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(),"csv数据为空");
        }
        List<String> phoneList = Lists.newArrayList();
        List<String> newStoreCodeList = Lists.newArrayList();

        datas.forEach(row -> phoneList.add(row.get(0)));
        datas.forEach(row -> newStoreCodeList.add(row.get(1)));

        for(int i = 0; i < phoneList.size(); i++) {
            Long userId = customerBiz.getUserIdByPhone(phoneList.get(i));
            if(userId == null || userId <= 0L) {
                continue;
            }
            UserLevel userLevel = customerBiz.getUserLevel(userId);
            if(userLevel == null) {
                log.error("[{}] userlevel is null", phoneList.get(i));
                continue;
            }
            if(userLevel.getLevelType() == (byte)2) {
                UserRelation userRelation = customerBiz.getUserRelation(userId);
                Store oldStore = customerBiz.getStoreById(userRelation.getStoreId());
                if(!newStoreCodeList.get(i).equals(oldStore.getStoreCode())) {
                    if(isBuyBigBag(userId)) {
                        continue;
                    }
                    UserRelation childRelation = customerBiz.getUserRelationByInvitorId(userId);
                    if(childRelation == null || childRelation.getCreateTime().compareTo(DateUtils.addDays(new Date(), -1)) > -1) {
                        System.out.println("phone="+ phoneList.get(i) + ", newcode="+newStoreCodeList.get(i) + ", oldcode="+oldStore.getStoreCode());
                    }
                }
            }
        }

        System.out.println("finish");

        return GLResponse.succ("处理成功");
    }


    private Boolean isBuyBigBag(Long userId) {
        OrdersExample example = new OrdersExample();
        example.createCriteria().andUserIdEqualTo(userId).andOrderTypeEqualTo((short)3)
                .andOrderStatusIn(Lists.newArrayList(2,3,4,5,6,10));
        example.setLimit(1);
        List<Orders> list = ordersMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? Boolean.TRUE : Boolean.FALSE;
    }

}