package com.gialen.tools.service.business;

import com.gialen.tools.dao.entity.customer.*;
import com.gialen.tools.dao.repository.customer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lupeibo
 * @date 2020-02-21
 */
@Slf4j
@Service
public class CustomerBiz {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLevelMapper userLevelMapper;

    @Autowired
    private UserRelationMapper userRelationMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private UserLevelChangeLogMapper levelChangeLogMapper;

    @Transactional(transactionManager = "customerTransactionManager")
    public void registerUser(String userName, String phone, Store store) {
        Long newUserId = addUser(userName, phone, store.getStoreId());
        addUserLevel(newUserId, (byte) 2);
        addUserLevelLog(newUserId);
        addUserRelation(newUserId, store.getStoreId(), store.getCompanyId());
    }

    @Transactional(transactionManager = "customerTransactionManager")
    public void updateUserToKeeper(UserLevel userLevel, Long userId, Store store, Store oldStore) {
        updateUserLevel(userLevel, (byte)2);
        addUserLevelLog(userId);
        if(store.getStoreCode().equals(oldStore.getStoreCode())) {
            log.info("vip升级为店主，门店一致");
            updateUserRelation(userId);
        } else {
            log.info("vip升级为店主，门店不一致，更新门店");
            updateUserStore(userId, store.getStoreId());
            updateUserRelation(userId, store);
        }
    }

    @Transactional(transactionManager = "customerTransactionManager")
    public void changeUserStore(Long userId, Store store) {
        log.info("vip升级为店主，门店不一致，更新门店");
        updateUserStore(userId, store.getStoreId());
        updateUserRelation(userId, store);
    }

    private void updateUserStore(Long userId, Long storeId) {
        User user = new User();
        user.setId(userId);
        user.setStoreId(storeId);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }


    private Long addUser(String userName, String phone, Long storeId) {
        User user = new User();
        user.setUsername(userName);
        user.setNickname(userName);
        user.setPhone(phone);
        user.setStoreId(storeId);
        userMapper.insertSelective(user);
        return user.getId();
    }

    public Long getUserIdByPhone(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        example.setLimit(1);
        List<User> userList = userMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(userList) ? userList.get(0).getId() : null;
    }

    public UserLevel getUserLevel(Long userId) {
        UserLevelExample example = new UserLevelExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setLimit(1);
        List<UserLevel> levelList = userLevelMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(levelList) ? levelList.get(0) : null;
    }

    private void updateUserLevel(UserLevel userLevel, Byte levelType) {
        userLevel.setLevelType(levelType);
        userLevel.setUpdateTime(new Date());
        userLevelMapper.updateByPrimaryKeySelective(userLevel);
    }

    private void addUserLevel(Long userId, Byte levelType) {
        UserLevel userLevel = new UserLevel();
        userLevel.setUserId(userId);
        userLevel.setLevelType(levelType);
        userLevelMapper.insertSelective(userLevel);
    }

    private void addUserLevelLog(Long userId) {
        UserLevelChangeLog log = new UserLevelChangeLog();
        log.setUserId(userId);
        log.setOldLevelType((byte)1);
        log.setNewLevelType((byte)2);
        log.setChannel((byte)8);
        Date date = new Date();
        log.setValidStartTime(date);
        log.setValidEndTime(DateUtils.addYears(date, 5));
        log.setCreateTime(date);
        levelChangeLogMapper.insertSelective(log);
    }

    public void updateUserLevelLog(Long logId) {
        UserLevelChangeLog log = new UserLevelChangeLog();
        log.setChannel((byte)8);
        UserLevelChangeLogExample example = new UserLevelChangeLogExample();
        example.createCriteria().andIdEqualTo(logId);
        levelChangeLogMapper.updateByExampleSelective(log, example);
    }

    public UserLevelChangeLog getUserLevelChangeLog(Long userId) {
        UserLevelChangeLogExample example = new UserLevelChangeLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andOldLevelTypeEqualTo((byte)1)
                .andNewLevelTypeEqualTo((byte)2).andChannelEqualTo((byte)10);
        example.setLimit(1);
        List<UserLevelChangeLog> logs = levelChangeLogMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(logs) ? logs.get(0) : null;
    }

    public Store getStoreByCode(String storeCode) {
        StoreExample example = new StoreExample();
        example.createCriteria().andStoreCodeEqualTo(storeCode);
        example.setLimit(1);
        List<Store> storeList = storeMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(storeList) ? storeList.get(0) : null;
    }

    public Store getStoreById(Long storeId) {
        StoreExample example = new StoreExample();
        example.createCriteria().andStoreIdEqualTo(storeId);
        example.setLimit(1);
        List<Store> storeList = storeMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(storeList) ? storeList.get(0) : null;
    }

    private void addUserRelation(Long userId, Long storeId, Long companyId) {
        UserRelation userRelation = new UserRelation();
        userRelation.setCompanyId(companyId);
        userRelation.setStoreId(storeId);
        userRelation.setUserId(userId);
        userRelation.setStoreMgrId(userId);
        userRelation.setStoreSuperMgrId(0L);
        userRelationMapper.insertSelective(userRelation);
    }

    public UserRelation getUserRelation(Long userId) {
        UserRelationExample example = new UserRelationExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setLimit(1);
        List<UserRelation> relationList = userRelationMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(relationList) ? relationList.get(0) : null;
    }

    public UserRelation getUserRelationByInvitorId(Long invitorId) {
        UserRelationExample example = new UserRelationExample();
        example.createCriteria().andInvitorIdEqualTo(invitorId);
        example.setOrderByClause("create_time asc");
        example.setLimit(1);
        List<UserRelation> relationList = userRelationMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(relationList) ? relationList.get(0) : null;
    }

    public void updateUserRelation(Long userId, Store store) {
        UserRelation relation = new UserRelation();
        relation.setStoreId(store.getStoreId());
        relation.setCompanyId(store.getCompanyId());
        relation.setStoreMgrId(userId);
        relation.setStoreSuperMgrId(0L);
        relation.setUpdateTime(new Date());

        UserRelationExample example = new UserRelationExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userRelationMapper.updateByExampleSelective(relation, example);
    }

    public void updateUserRelation(Long userId) {
        UserRelation relation = new UserRelation();
        relation.setStoreMgrId(userId);
        relation.setUpdateTime(new Date());
        UserRelationExample example = new UserRelationExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userRelationMapper.updateByExampleSelective(relation, example);
    }

    public void updateChildRelation(Long userId, Store store) {
        UserRelation relation = new UserRelation();
        relation.setStoreId(store.getStoreId());
        relation.setCompanyId(store.getCompanyId());

        UserRelationExample example = new UserRelationExample();
        example.createCriteria().andInvitorIdEqualTo(userId);
        userRelationMapper.updateByExampleSelective(relation, example);
    }
}
