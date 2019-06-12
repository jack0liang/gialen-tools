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

/**
 * @author lupeibo
 * @date 2019-05-23
 */
@Slf4j
@Service
public class WxMessageServiceImpl implements WxMessageService {

    public static String sendWxMsgUrl = "https://app.pinquest.cn/gialenx/SendTemplateMsg";

    private String templateId = "y_ZW7P_QDr41ePD9JQXLar0u8FDjRh8-kAWFkaFSTz0";

    private String filePath = "/Users/lupeibo/Downloads/sendmsg.csv";

    private String page = "/pages/product/coupons/coupons";

    private String keyword1 = "您的全部爱心已兑换成娇币，到账啦！";

    private String keyword2 = "可在小程序底部菜单“我的-我的娇币”查看娇币金额！娇币可用于商城免费购物，直接抵现金！马上去查看使用>>";

    @Override
    public GLResponse sendWxMsgUrl() {
        List<List<String>> datas = CsvUtil.readCsv(filePath);
        if(CollectionUtils.isEmpty(datas)) {
            log.error("csv数据为空");
            return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(),"csv数据为空");
        }
        List<String> phoneList = Lists.newArrayList();

        datas.forEach(row -> phoneList.add(row.get(0)));

        for(String phone : phoneList) {
            if(StringUtils.isBlank(phone)) {
                continue;
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("templateId", templateId);
            params.put("page", page);
            params.put("phone", phone);
            Map<String, String> keywordsMap = Maps.newHashMap();
            keywordsMap.put("keyword1", keyword1);
            keywordsMap.put("keyword2", keyword2);
            params.put("keywords", keywordsMap);
            try {
                String result = HttpUtil.sendPost(sendWxMsgUrl, params);
                if (StringUtils.isNotBlank(result)) {
                    JSONObject json = JSON.parseObject(result);
                    int code = json.getIntValue("errcode");
                    String msg = json.getString("errmsg");
                    if (code != 0) {
                        log.error(String.format("发送失败! phone=%s, errmsg=%s", phone, msg));
                    } else {
                        log.info(String.format("发送微信模版消息成功！phone=%s, templateId=%s", phone, templateId));
                    }
                }
            } catch (Exception e) {
                log.error(String.format("phone=%s, templateId=%s, 发送微信模版消息接口异常：%s",
                        phone, templateId, e.getMessage()));
            }
        }
        return GLResponse.succ("发送成功");
    }
}
