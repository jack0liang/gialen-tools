package com.gialen.tools.common.util;

/**
 * @author lupeibo
 * @date 2019-05-22
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 请求资源接口
 */
@Slf4j
public class HttpUtil {

    /**
     * 向指定URL发起Get请求，参数为params
     * @param url
     * @param params
     * @return
     */
    public static String sendGet(String url, Map<String,String> params, Map<String,String> headers) throws Exception {
        StringBuffer result = new StringBuffer();// 查询结果
        BufferedReader in = null;
        OutputStreamWriter out = null;
        try {
            String paramsString=handleParams(params);//处理参数为字符串
            HttpURLConnection conn=getConnObject(url,"GET",paramsString,headers); //根据请求方式和url获得相应的conn对象
            if(conn==null){
                log.error("url处理异常！");
                return "";
            }
            conn.connect();
            // 获取URLConnection对象对应的输出流
            /*out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // flush输出流的缓冲
            out.flush();*/
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e){
            log.error(url+"响应异常",e);
            throw new IOException();
        } catch (Exception e) {
            log.error("GET请求错误！",e);
        } finally{ //使用finally块来关闭输出流、输入流
            try{
                /*if(out!=null){
                    out.close();
                }*/
                if(in!=null){
                    in.close();
                }
            } catch(IOException ex){
                log.error("服务器异常",ex);
            }
        }
        return result.toString();
    }

    /**
     * 向指定URL发起post请求，参数为params
     * @param url
     * @param params
     * @return
     */
    public static String sendPost(String url, Map<String,Object> params) throws Exception {
        StringBuffer result = new StringBuffer();// 查询结果
        BufferedReader in = null;
        OutputStreamWriter out = null;
        try {
            String paramsString=handleParamsToJson(params);//处理参数为字符串
            //log.info("post params ：" +  paramsString);
            HttpURLConnection conn=getConnObject(url,"POST",paramsString,null); //根据请求方式和url获得相应的conn对象
            if(conn==null){
                log.error("url处理异常！");
                return "";
            }
            conn.connect();

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(paramsString);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应

            in = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e){
            log.error(url+"响应异常",e);
            throw new IOException();
        } catch (Exception e) {
            log.error("POST请求错误！",e);
        } finally{ //使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            } catch(IOException ex){
                log.error("服务器异常",ex);
            }
        }
        return result.toString();
    }
    /**
     * 根据请求方式和url获得相应的conn对象
     * @param url
     * @param method
     * @return
     */
    private static HttpURLConnection getConnObject(String url,String method,String params, Map<String, String> headers) {
        HttpURLConnection conn=null;
        try {
            if(method.equals("GET")) {
                if(params!=""){
                    url=url+"?"+params;
                }
                URL realUrl = new URL(url);

                conn =(HttpURLConnection) realUrl.openConnection();
                conn.setRequestMethod("GET");

            }else if(method.equals("POST")) {
                URL realUrl = new URL(url);
                conn =(HttpURLConnection) realUrl.openConnection();
                conn.setRequestMethod("POST");
            }else {
                throw new RuntimeException("不能处理的请求方式");//接口处理异常
            }
            if(MapUtils.isNotEmpty(headers)) {
                if(headers.containsKey("Cookie")) {
                    conn.setRequestProperty("Cookie", headers.get("Cookie"));
                }
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置连接超时和读取超时
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            //conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        }catch (MalformedURLException e){
            log.error("无效url！",e);
        }catch (IOException e){
            log.error("url资源获取异常！",e);
        }catch (Exception e){
            log.error("不能处理的请求方式！");
        }
        return conn;
    }

    /**
     * Handle request params
     * @param params
     * @return
     */
    private static String handleParams(Map<String,String>params)
    {
        // 发送请求参数
        if (params != null) {
            StringBuilder param = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if(param.length()>0){
                    param.append("&");
                }
                param.append(entry.getKey());
                param.append("=");
                param.append(entry.getValue());
            }
            return param.toString();
        }else {
            return "";
        }
    }

    private static String handleParamsToJson(Map<String,Object> params) {
        // 发送请求参数
        if (MapUtils.isNotEmpty(params)) {
            String jsonString = JSON.toJSONString(params);
            return jsonString;
        }
        return "";
    }

    public static void main(String[] args) {
        String url = "http://cs-jiaomigo.gialen.com/admin/virtualctr/putCoin";
        Map<String, String> params = Maps.newHashMap();
        params.put("coinId","24");
        params.put("mobiles","12345678912,12345678900,13360099962");
        params.put("userType","VIP");
        Map<String, String> headers = Maps.newHashMap();
       // headers.put("Cookie", "JSESSIONID=1CD9FA0005025F315937F7693EE5F05E");
        List<String> errorList = Lists.newArrayList();
        try {
            String result = HttpUtil.sendGet(url, params,headers);
            if(result.startsWith("<!DOCTYPE html>")) {
                log.error("JSESSIONID失效");
                return;
            }
            if(StringUtils.isNotBlank(result)) {
                JSONObject json = JSON.parseObject(result);
                int status = json.getIntValue("status");
                String msg = json.getString("message");
                if(status != 0) {
                    log.error("status=1, msg=" + msg);
                    errorList.add(msg);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}