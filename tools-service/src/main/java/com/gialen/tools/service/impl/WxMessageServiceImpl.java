package com.gialen.tools.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gialen.tools.common.util.CsvUtil;
import com.gialen.tools.common.util.HttpUtil;
import com.gialen.tools.service.WxMessageService;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.ResponseStatus;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lupeibo
 * @date 2019-05-23
 */
@Slf4j
@Service
public class WxMessageServiceImpl implements WxMessageService {

    public static String sendWxMsgUrl = "https://app.pinquest.cn/gialenx/SendTemplateMsg";

    private String templateId = "1Dk9pdqeqVUG2kCaM7HYMiA-eM8cH5pfK1Nu-Aaupfc";

    private String filePath = "/Users/lupeibo/Downloads/sendmsg.csv";

    private String page = "/pages/index/index";

    private String keyword1 = "WIS隐形水润面膜";

    private String keyword2 = "2019-08-16";

    private String keyword3 = "您的预约即将过期，请及时查看！";

    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Override
    public GLResponse sendWxMsgUrl() {
        List<List<String>> datas = CsvUtil.readCsv(filePath);
        if(CollectionUtils.isEmpty(datas)) {
            log.error("csv数据为空");
            return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(),"csv数据为空");
        }
        List<String> phoneList = Lists.newArrayList();

        datas.forEach(row -> phoneList.add(row.get(0)));

        Map<String, String> keywordsMap = Maps.newHashMap();
        keywordsMap.put("keyword1", keyword1);
        keywordsMap.put("keyword2", keyword2);
        keywordsMap.put("keyword3", keyword3);

        for(String phone : phoneList) {
            if(StringUtils.isBlank(phone)) {
                continue;
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("templateId", templateId);
            params.put("page", page);
            params.put("phone", phone);
            params.put("keywords", keywordsMap);
            threadPool.execute(() -> executeSendMsg(params));
        }
        return GLResponse.succ("发送成功");
    }

    private void executeSendMsg(Map<String, Object> params) {
        try {
            String result = HttpUtil.sendPost(sendWxMsgUrl, params);
            log.info("response : {}", result);
            if (StringUtils.isNotBlank(result)) {
                JSONObject json = JSON.parseObject(result);
                int code = json.getIntValue("errcode");
                String msg = json.getString("errmsg");
                if (code != 0) {
                    log.error(String.format("发送失败! phone=%s, errmsg=%s", params.get("phone"), msg));
                } else {
                    log.info(String.format("发送微信模版消息成功！phone=%s, templateId=%s", params.get("phone"), templateId));
                }
            }
        } catch (Exception e) {
            log.error(String.format("phone=%s, templateId=%s, 发送微信模版消息接口异常：%s",
                    params.get("phone"), templateId, e.getMessage()));
        }
    }
}
