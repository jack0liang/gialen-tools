package com.gialen.tools.api.controller;

import com.gialen.tools.service.DataToolsService;
import com.gialen.common.model.GLResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/dataTools")
public class DataToolsController {

    @Resource
    private DataToolsService dataToolsService;

    @RequestMapping("/getDataList")
    public GLResponse getDataList(@RequestParam(name = "startTime", required = false) Long startTime,
                                  @RequestParam(name = "endTime", required = false) Long endTime,
                                  @RequestParam(name = "dataType", required = false) Byte dataType) {
        String startTimeStr = DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss");
        String endTimeStr = DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss");
        log.info("小娇数坊: startTime=" + startTimeStr + ", endTime=" + endTimeStr + ",dataType=" + dataType);
        try {
            return dataToolsService.getDataList(startTime, endTime, dataType);
        } catch (Exception e) {
            log.error("小娇数坊数据查询异常！",e);
            return GLResponse.fail("小娇数坊数据查询异常!");
        }
    }

    @RequestMapping("/test")
    public String test() {
        log.info("测试接口");
        String test ="<table>" +
                     "<tr><td>订单号<td/><td stype='width:50px;'>姓名<td/><td>手机号<td/><td>身份<td/><td>商品<td/><td>店主佣金<td/></tr>"+
                    "</tr><td>1905282058760620<td/><td><td/><td>15140246626<td/><td>VIP<td/>" +
                "<td>邦9号仙人掌冰沙胶奶茶杯晒后修复冰感镇定保湿补水降温200g<td/><td>2.7<td/></tr>" +
                "</tr><td>1905282058760620<td/><td><td/><td>15140246626<td/><td>VIP<td/>" +
                "<td>单件包邮】玥之秘水晶防晒喷雾SPF50+PA+++ 180ml（加赠一瓶50ml）<td/><td>2.7<td/></tr>" +
                    "</table>";
        return test;
    }

}
