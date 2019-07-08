package com.gialen.tools.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * MultipartFile 文档文件生成工厂
 *
 * @auther wong
 * @Date: 2019-06-28
 * @Version: 1.0
 */
@Slf4j
public class MultipartFileExcelFactory {

    private MultipartFileExcelFactory() {
    }

    private static MultipartFileExcelFactory instance;

    public static MultipartFileExcelFactory getInstance() {
        if (instance == null) {
            synchronized (MultipartFileExcelFactory.class) {
                if (instance == null) {
                    instance = new MultipartFileExcelFactory();
                }
            }
        }
        return instance;
    }

    private ExcelUtil excelUtil = new ExcelUtil();

    /**
     * EXCEL生成字典数据格式
     *
     * @param multipartFile
     * @return
     */
    public List<Map<String, String>> getDic(MultipartFile multipartFile) {
        List<Map<String, String>> datas;
        try (InputStream in = multipartFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(in)) {
            datas = excelUtil.generatorDic(workbook);
        } catch (Exception e) {
            log.error("", e);
            throw new IllegalArgumentException(e);
        }
        return datas;
    }

    /**
     * 获取XSS文档映射的内容数据
     *
     * @param multipartFile
     * @param clazz
     * @return
     */
    public <T> List<T> get(MultipartFile multipartFile, Class<T> clazz) {
        List<T> array = null;
        try (InputStream in = multipartFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(in)) {
            array = excelUtil.generator(workbook, clazz);
        } catch (Exception e) {
            log.error("", e);
        }
        return array;
    }

}
