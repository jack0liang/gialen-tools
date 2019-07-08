package com.gialen.tools.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther wong
 * @Date: 2019-06-21
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface ToCell {

    /**
     * 表格元素的列下标位置
     * @return
     */
    int position() default  0;

}
