package com.gialen.tools.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelParse {

    static String barcode = "110900055," +
            "702000018," +
            "602200235," +
            "601300063," +
            "702000391," +
            "109520004," +
            "119120001," +
            "111320040," +
            "702000012," +
            "602300152," +
            "611120025," +
            "109110842," +
            "111320146," +
            "611100085," +
            "111320187," +
            "111620028," +
            "114410108," +
            "108120082," +
            "303220085," +
            "108210053," +
            "302510356," +
            "302420010," +
            "108210070," +
            "303210047," +
            "108110082," +
            "302440001," +
            "601200022," +
            "601200081," +
            "902201100," +
            "301630016," +
            "301620022," +
            "301660035," +
            "301630015," +
            "301650016," +
            "309100005," +
            "120200053," +
            "301610070,";

    public static void main(String[] args) {
        String filePath = "/Users/lupeibo/Downloads/1558540800.csv";

        List<List<String>> data = getData(filePath);
        int rowNumber = 1;
        StringBuilder sb = new StringBuilder();
        for (List<String> row : data) {
            if(rowNumber == 100)
                return;
            String phone = row.get(1);
            if(rowNumber == 1) {
                sb.append(phone);
            } else {
                sb.append(",").append(phone);
            }

//            if(CollectionUtils.isEmpty(row)) {
//                System.out.println("第" + rowNumber + "行数据为空");
//                continue;
//            }
//            String outUserIdStr = row.get(0);
//            if(StringUtils.isBlank(outUserIdStr)) {
//                System.out.println("第" + rowNumber + "行外部用户id为空");
//                continue;
//            }
//            if(!NumberUtils.isDigits(outUserIdStr)) {
//                System.out.println("第" + rowNumber + "行外部用户id数据格式错误");
//                continue;
//            }
//            String phone = row.get(1);
//            if(StringUtils.isBlank(phone)) {
//                System.out.println("第" + rowNumber + "行手机号为空");
//                continue;
//            }
//            String scoreStr = row.get(2);
//            if(StringUtils.isBlank(scoreStr)) {
//                System.out.println("第" + rowNumber + "行用户爱心值为空");
//                continue;
//            }
//            if(!NumberUtils.isDigits(outUserIdStr)) {
//                System.out.println("第" + rowNumber + "行用户爱心值格式错误");
//                continue;
//            }
//            Long outerUserId = Long.parseLong(outUserIdStr);
//            Integer score = Integer.parseInt(scoreStr);

//            System.out.println("outerUserId=" + outerUserId + ", phone=" + phone + ", score=" + score);
            rowNumber++;
        }
        System.out.println(sb.toString());
    }

    public static List<List<String>> getData(String filePath) {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<List<String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<List<String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 0; i < rownum; i++) {
                List<String> rowdata = new ArrayList<>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = "";
                        try {
                            cellData = row.getCell(j).getStringCellValue();
                        } catch (Exception e) {
//                            e.printStackTrace();
//                            System.out.println(row.getCell(j));
                        }
                        if(StringUtils.isNotBlank(cellData.trim()))
                            rowdata.add(cellData.trim());
                    }
                } else {
                    break;
                }
                list.add(rowdata);
            }
        }
        return list;
    }


    //读取excel
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString) || ".csv".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

}

