package com.gialen.tools.common.util;

/**
 * @author lupeibo
 * @date 2019-05-23
 */

import com.google.common.collect.Lists;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class CsvUtil {

    public static List<List<String>> readCsv(String path) {
        List<List<String>> list = Lists.newArrayList();
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, "UTF-8"), CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 1);
            String[] strs;
            while ((strs = csvReader.readNext()) != null) {
                List<String> rowDatas = Arrays.asList(strs);
                if(CollectionUtils.isNotEmpty(rowDatas)) {
                    list.add(rowDatas);
                }
                //测试用
//                if(list.size() >= 20) {
//                    return list;
//                }
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {

        String filePath = "/Users/lupeibo/Downloads/sendmsg.csv";

        List<List<String>> datas = readCsv(filePath);
        int rowNumber = 1;
        StringBuilder sb = new StringBuilder();
        for (List<String> row : datas) {
            if(rowNumber == 100)
                break;
            String outerUserId = row.get(0);
            if(StringUtils.isBlank(outerUserId)) {
                continue;
            }
            String phone = row.get(1);
            if(rowNumber == 1) {
                sb.append(phone);
            } else {
                sb.append(",").append(phone);
            }

            rowNumber++;
        }
        System.out.println(sb.toString());
    }

}
