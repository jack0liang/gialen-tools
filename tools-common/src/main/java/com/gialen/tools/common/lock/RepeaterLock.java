package com.gialen.tools.common.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wong
 * @Date: 2020-01-06
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RepeaterLock {

    /**
     * 前缀KEY
     *
     * @return
     */
    String keyPrefix() default "LOCK_KEY_DEFAULT_";

    /**
     * 字段名, 取指定字段的值作为Key后缀
     *
     * @return
     */
    String fieldName();

    /**
     * key锁的超时机制
     *
     * @return
     */
    long timeout() default 2000;

}
