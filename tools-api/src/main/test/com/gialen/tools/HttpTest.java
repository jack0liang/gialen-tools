package com.gialen.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.gialen.common.model.PageRequest;
import com.gialen.tools.api.ToolsApiApplication;
import com.gialen.tools.common.util.HttpUtil;
import com.gialen.tools.dao.entity.tools.XiaojiaoData;
import com.gialen.tools.dao.repository.tools.XiaojiaoDataMapper;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.model.StoreUserWithDrawRespModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wong
 * @Date: 2020-03-31
 * @Version: 1.0
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ToolsApiApplication.class})
public class HttpTest {
    @Autowired
    XiaojiaoDataMapper xiaojiaoDataMapper;

    @Test
    public void test() throws Exception {
        String url="http://10.255.1.55:1088/tools/dataTools/getDataList";
        Date start = DateUtils.parseDate("2020-10-01","yyyy-MM-dd");
        for(int i=0;i<31;i++) {
            String dataDate = DateFormatUtils.format(start,"yyyy-MM-dd");
            XiaojiaoData data = new XiaojiaoData();
            data.setDataDate(dataDate);
            Map<String, String> params = new HashMap<>();
            params.put("dataType", "1");
            params.put("startTime", start.getTime() + "");
            start = DateUtils.addDays(start, 1);
            params.put("endTime", start.getTime() + "");
            String json = HttpUtil.sendGet(url, params, null);
            JSONObject jo = JSON.parseObject(json).getJSONArray("data").getJSONObject(0);
            System.out.println(dataDate+"\t"+jo);
            JSONArray ja = jo.getJSONArray("items");
            data.setSaleTotal(ja.getJSONObject(0).get("value").toString());
            data.setSaleTotalRate(ja.getJSONObject(0).get("relativeRatio").toString());
            data.setSaleBiggift(ja.getJSONObject(1).get("value").toString());
            data.setSaleBiggiftRate(ja.getJSONObject(1).get("relativeRatio").toString());
            data.setSalePutong(ja.getJSONObject(2).get("value").toString());
            data.setSalePutongRate(ja.getJSONObject(2).get("relativeRatio").toString());
            data.setSaleZhekou(ja.getJSONObject(3).get("value").toString());
            data.setSaleZhekouRate(ja.getJSONObject(3).get("relativeRatio").toString());

            params.put("dataType", "2");
            json = HttpUtil.sendGet(url, params, null);
            jo = JSON.parseObject(json).getJSONArray("data").getJSONObject(0);
            System.out.println(dataDate+"\t"+jo);
            ja = jo.getJSONArray("items");
            data.setUvMiniapp(ja.getJSONObject(0).get("value").toString());
            data.setUvMiniappRate(ja.getJSONObject(0).get("relativeRatio").toString());
            data.setUvApp(ja.getJSONObject(1).get("value").toString());
            data.setUvAppRate(ja.getJSONObject(1).get("relativeRatio").toString());

            params.put("dataType", "3");
            json = HttpUtil.sendGet(url, params, null);
            jo = JSON.parseObject(json).getJSONArray("data").getJSONObject(0);
            System.out.println(dataDate+"\t"+jo);
            ja = jo.getJSONArray("items");
            data.setOrderMiniapp(ja.getJSONObject(0).get("value").toString());
            data.setOrderMiniappRate(ja.getJSONObject(0).get("relativeRatio").toString());
            data.setOrderApp(ja.getJSONObject(1).get("value").toString());
            data.setOrderAppRate(ja.getJSONObject(1).get("relativeRatio").toString());
            data.setOrderH5(ja.getJSONObject(2).get("value").toString());
            data.setOrderH5Rate(ja.getJSONObject(2).get("relativeRatio").toString());

            params.put("dataType", "4");
            json = HttpUtil.sendGet(url, params, null);
            jo = JSON.parseObject(json).getJSONArray("data").getJSONObject(0);
            System.out.println(dataDate+"\t"+jo);
            ja = jo.getJSONArray("items");
            data.setOrderCreateNum(ja.getJSONObject(0).get("value").toString());
            data.setOrderCreateNumRate(ja.getJSONObject(0).get("relativeRatio").toString());
            data.setPaySuccNum(ja.getJSONObject(1).get("value").toString());
            data.setPaySuccNumRate(ja.getJSONObject(1).get("relativeRatio").toString());
            data.setPaySuccRate(ja.getJSONObject(2).get("value").toString());
            data.setPaySuccRateRate(ja.getJSONObject(2).get("relativeRatio").toString());
            data.setZhuanghuaRate(ja.getJSONObject(3).get("value").toString());
            data.setZhuanghuaRateRate(ja.getJSONObject(3).get("relativeRatio").toString());

            params.put("dataType", "5");
            json = HttpUtil.sendGet(url, params, null);
            jo = JSON.parseObject(json).getJSONArray("data").getJSONObject(0);
            System.out.println(dataDate+"\t"+jo);
            ja = jo.getJSONArray("items");
            data.setUserNewStore(ja.getJSONObject(0).get("value").toString());
            data.setUserNewStoreRate(ja.getJSONObject(0).get("relativeRatio").toString());
            data.setUserNewVip(ja.getJSONObject(1).get("value").toString());
            data.setUserNewVipRate(ja.getJSONObject(1).get("relativeRatio").toString());
            data.setNewOrderNum(ja.getJSONObject(2).get("value").toString());
            data.setNewOrderNumRate(ja.getJSONObject(2).get("relativeRatio").toString());
            data.setOldOrderNum(ja.getJSONObject(3).get("value").toString());
            data.setOldOrderNumRate(ja.getJSONObject(3).get("relativeRatio").toString());

            System.out.println(JSON.toJSON(data));

            xiaojiaoDataMapper.insertSelective(data);
        }

    }

}
