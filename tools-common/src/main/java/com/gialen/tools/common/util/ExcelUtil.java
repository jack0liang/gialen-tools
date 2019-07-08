package com.gialen.tools.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 解决实体对象列表和文档数据的相互映射
 * 文档快速生成、解析的通用解决方案
 *
 * @auther wong
 * @Date: 2019-06-21
 * @Version: 1.0
 */
@Slf4j
public class ExcelUtil<T> {

    /**
     * 生成实体列表
     *
     * @param workbook 文档对象
     * @param clazz    文档要映射的class
     * @return
     */
    public List<Object> generator(Workbook workbook, Class clazz) {
        Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        int position = (AnnotationUtils.findAnnotation(clazz, SheetTitle.class) != null) ? 1 : 0;
        int lastRowNum = sheet.getLastRowNum();

        List<Object> datas = new ArrayList<>();
        while (position <= lastRowNum) {
            datas.add(getDataByRow(clazz, sheet.getRow(position)));
            position++;
        }
        return datas;
    }

    /**
     * 给文档追加对应的数据列表
     *
     * @param workbook 文档对象
     * @param clzzs    数据实例列表
     */
    public void appendData(Workbook workbook, List<T> clzzs) throws IllegalAccessException {
        Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        int startPosition = sheet.getLastRowNum() + 1;
        generaterCellValue(sheet, clzzs, startPosition);
    }

    /**
     * 生成新的文档
     *
     * @param clzzs 数据实例列表
     */
    public Workbook generator(List<T> clzzs) {
        if (CollectionUtils.isEmpty(clzzs)) {
            return null;
        }

        int position = 0;
        Workbook workbook = null;
        try {

            //创建新文档
            workbook = new XSSFWorkbook();

            //创建表格
            Sheet sheet1 = workbook.createSheet("sheet1");

            //创建title
            generateTitle(workbook, sheet1, clzzs.get(0).getClass(), position++);

            //处理属性字段
            generaterCellValue(sheet1, clzzs, position++);
        } catch (Exception e) {
            log.error("", e);
        }
        return workbook;
    }


    /**
     * 生成字典类型数据结构
     * map格式 ：key = w+列号， value=cellTitle:columnValue
     *
     * @param workbook
     * @return
     */
    public List<Map<String, String>> generatorDic(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());

        int startRow = sheet.getLastRowNum();
        if (startRow <= 1) {
            return null;
        }

        List<Map<String, String>> datas = new ArrayList<>();
        Row titleRow = sheet.getRow(0);
        int culumnSum = titleRow.getLastCellNum();
        for (int i = 1; i < startRow; i++) {
            Map<String, String> dic = new LinkedHashMap<>();

            Row valueRow = sheet.getRow(i);
            for (int j = 0; j < culumnSum; j++) {
                String key = "w" + j;

                String title = getCellValueString(titleRow.getCell(j));
                if (StringUtils.isEmpty(title)) {
                    continue;
                }

                String value = getCellValueString(valueRow.getCell(j));
                if (StringUtils.isEmpty(value)) {
                    continue;
                }

                dic.put(key, title.trim() + ":" + value.trim());
            }

            datas.add(dic);
        }
        return datas;
    }

    // ---------------------------------------------------------------//

    /**
     * 将行数据转换为对象实例
     *
     * @param clazz
     * @param row
     * @return
     */
    private Object getDataByRow(final Class clazz, final Row row) {
        Object data = null;
        try {
            data = Class.forName(clazz.getName()).newInstance();
            Field[] fields = clazz.getDeclaredFields();
            List<Field> array = withoutAnontation(fields);
            array.sort(Comparator.comparingInt(this::getPosition));

            //给字段赋值
            if (!CollectionUtils.isEmpty(array)) {
                for (int i = 0; i < array.size(); i++) {
                    setFieldByType(array.get(i), data, row.getCell(i));
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }

        return data;
    }

    private void setFieldByType(Field field, Object obj, Cell cell) throws IllegalAccessException {
        if(null == cell) {
            return;
        }
        String value = cell.getStringCellValue();
        if (StringUtils.isEmpty(value)) {
            return;
        }

        field.setAccessible(true);
        Class<?> type = field.getType();
        if (type.isAssignableFrom(String.class)) {
            field.set(obj, value);
        } else if (type.isAssignableFrom(Integer.class)) {
            field.set(obj, Integer.parseInt(value));
        } else if (type.isAssignableFrom(Short.class)) {
            field.set(obj, Short.parseShort(value));
        } else if (type.isAssignableFrom(Boolean.class)) {
            field.set(obj, Boolean.parseBoolean(value));
        } else if (type.isAssignableFrom(Byte.class)) {
            field.set(obj, Byte.parseByte(value));
        } else if (type.isAssignableFrom(Long.class)) {
            field.set(obj, Long.parseLong(value));
        } else if (type.isAssignableFrom(Float.class)) {
            field.set(obj, Float.parseFloat(value));
        } else if (type.isAssignableFrom(BigDecimal.class)) {
            field.set(obj, new BigDecimal(value));
        } else if (type.isAssignableFrom(Double.class)) {
            field.set(obj, Double.parseDouble(value));
        }
    }

    /**
     * 获取元素内容字符
     *
     * @param cell
     * @return
     */
    private String getCellValueString(Cell cell) {
        Object value = null;
        int type = cell.getCellType();
        switch (type) {
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = null;
        }
        return value == null ? null : value.toString();
    }

    /**
     * 生成title
     *
     * @param workbook
     * @param clz
     */
    private void generateTitle(Workbook workbook, Sheet sheet, Class clz, int position) {
        SheetTitle title = AnnotationUtils.findAnnotation(clz, SheetTitle.class);
        if (null != title) {
            String[] titles = title.value();
            Row row = sheet.createRow(position);

            MyCellStyle myCellStyle = AnnotationUtils.findAnnotation(clz, MyCellStyle.class);
            CellStyle cellStyle = getTitleCellStyle(workbook, myCellStyle);
            for (int i = 0; i < titles.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(titles[i]);
                if (cellStyle != null) {
                    cell.setCellStyle(cellStyle);
                    if (-1 != myCellStyle.columnWidth()) {
                        sheet.setColumnWidth(i, titles[i].getBytes().length * 256 * myCellStyle.columnWidth());
                    }
                }
            }
        }
    }

    private CellStyle getTitleCellStyle(Workbook workbook, MyCellStyle myCellStyle) {
        if (null == myCellStyle) {
            return null;
        }

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(myCellStyle.alignment());
        cellStyle.setVerticalAlignment(myCellStyle.verticalAlignment());
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(myCellStyle.backgroundColor());
        if (myCellStyle.isBorder()) {
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setTopBorderColor(myCellStyle.borderColor());
            cellStyle.setBottomBorderColor(myCellStyle.borderColor());
            cellStyle.setLeftBorderColor(myCellStyle.borderColor());
            cellStyle.setRightBorderColor(myCellStyle.borderColor());
        }

        Font font = workbook.createFont();
        font.setBold(myCellStyle.fontBold());
        font.setBoldweight(myCellStyle.fontWeight());
        font.setFontHeight(myCellStyle.fontHeight());
        font.setColor(myCellStyle.fontColor());
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 生成字段值
     *
     * @param sheet
     * @param clzs
     * @param position
     * @throws IllegalAccessException
     */
    private void generaterCellValue(Sheet sheet, List<T> clzs, int position) throws IllegalAccessException {
        Row row;
        for (T clzz : clzs) {
            Field[] fields = clzz.getClass().getDeclaredFields();
            List<Field> array = withoutAnontation(fields);
            array.sort(Comparator.comparingInt(this::getPosition));
            if (array.size() > 0) {
                row = sheet.createRow(position++);
                for (int i = 0; i < array.size(); i++) {
                    Field field = array.get(i);
                    field.setAccessible(true);
                    Object object = field.get(clzz);
                    Cell cell = row.createCell(i);
                    String value = "";
                    if (object != null) {
                        value = object.toString();
                        cell.setCellValue(value);
                    }

                    MyCellStyle myCellStyle = field.getAnnotation(MyCellStyle.class);
                    if (myCellStyle != null) {
                        CellStyle cellStyle = getTitleCellStyle(sheet.getWorkbook(), myCellStyle);
                        if (cellStyle != null) {
                            cell.setCellStyle(cellStyle);
                            if (-1 != myCellStyle.columnWidth()) {
                                sheet.setColumnWidth(i, value.getBytes().length * 256 * myCellStyle.columnWidth());
                            }
                        }
                    }
                }
            }
        }
    }

    private List<Field> withoutAnontation(Field[] fields) {
        List<Field> fieldArray = new ArrayList<>();
        for (Field field : fields) {
            if (-1 == getPosition(field)) {
                continue;
            }
            fieldArray.add(field);
        }
        return fieldArray;
    }


    private int getPosition(Field field) {
        ToCell ca = field.getAnnotation(ToCell.class);
        if (ca == null) {
            return -1;
        }
        return ca.position();
    }

    /**
     * 取出每列的值
     *
     * @param xCell 列
     * @return
     */
    private static String getValue(XSSFCell xCell) {
        if (xCell == null) {
            return "";
        } else if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xCell.getBooleanCellValue());
        } else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(xCell.getNumericCellValue());
        } else {
            return String.valueOf(xCell.getStringCellValue());
        }
    }

}
