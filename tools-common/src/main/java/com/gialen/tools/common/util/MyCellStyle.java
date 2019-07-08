package com.gialen.tools.common.util;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther wong
 * @Date: 2019-06-24
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MyCellStyle {

    /**
     * 水平位置
     * ALIGN_GENERAL
     * ALIGN_LEFT
     * ALIGN_CENTER
     * ALIGN_RIGHT
     * ALIGN_FILL
     * ALIGN_JUSTIFY
     * ALIGN_CENTER_SELECTION
     * @return
     */
    short alignment() default CellStyle.ALIGN_LEFT;

    /**
     * 垂直位置
     * VERTICAL_TOP
     * VERTICAL_CENTER
     * VERTICAL_BOTTOM
     * VERTICAL_JUSTIFY
     * @return
     */
    short verticalAlignment() default CellStyle.VERTICAL_CENTER;

    /**
     * 是否加边框
     * @return
     */
    boolean isBorder() default true;

    /**
     * 边框颜色
     * @return
     */
    short borderColor() default HSSFColor.BLACK.index;

    /**
     * 字体大小
     * @return
     */
    short fontHeight() default 200;

    /**
     * 粗体
     * @return
     */
    boolean fontBold() default false;

    /**
     * 字体颜色
     * @return
     */
    short fontColor() default HSSFColor.BLACK.index;

    /**
     * 质重
     * @return
     */
    short fontWeight() default 50;

    /**
     * 列长度 （字符数的256倍数增长,所以范围再 1-10左右就有很大区别,-1则不处理)
     * @return
     */
    int columnWidth() default -1;

    /**
     * 单元格背景色
     * @return
     */
    short backgroundColor() default HSSFColor.WHITE.index;
}
