package com.gialen.tools.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author lupeibo
 * @date 2019-07-01
 */
public class TokenUtil {

    public static Cache<String, Long> tokenUserIdCache = CacheBuilder
            .newBuilder().maximumSize(4096)
            .expireAfterAccess(2, TimeUnit.DAYS)
            .build();


}
