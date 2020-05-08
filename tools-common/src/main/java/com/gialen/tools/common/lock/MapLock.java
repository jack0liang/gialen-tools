package com.gialen.tools.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单机并发提交锁。如果是多机器部署则需要用分布式redis锁来处理
 */
@Slf4j
@Component
public class MapLock {

    /**
     * 线程安全锁的容器
     * Key   : 锁ID
     * Value : 超时时间 单位毫秒
     */
    private final Map<String, Long> object = new ConcurrentHashMap<>();

    public boolean tryLock(String key, Long value) {
        if (!object.containsKey(key)) {
            synchronized (object) {
                if (!object.containsKey(key)) {
                    object.put(key, System.currentTimeMillis() + value);
                    return true;
                }
            }
        }

        /*
         * 存在锁的情况下则需要判断锁是否过期，并且过期后重新给新线程加锁，以防死锁
         */
        long timeout = object.get(key);
        if (timeout < System.currentTimeMillis()) {
            // 释放超时锁，同时加新锁
            synchronized (object) {
                long t = object.get(key);
                if (t < System.currentTimeMillis()) {
                    object.put(key, System.currentTimeMillis() + value);
                    return true;
                }
            }
        }
        return false;
    }

    public void unlock(String key) {
        if (!StringUtils.isEmpty(key)) {
            object.remove(key);
        }
    }

}
