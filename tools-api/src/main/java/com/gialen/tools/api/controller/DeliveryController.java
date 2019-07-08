package com.gialen.tools.api.controller;

import com.gialen.common.beantools.Copier;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.ResponseStatus;
import com.gialen.tools.api.vo.DeliveryExcelVo;
import com.gialen.tools.common.util.MultipartFileExcelFactory;
import com.gialen.tools.service.DeliveryService;
import com.gialen.tools.service.model.DelivertyModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 快递工具控制器
 * @author lupeibo
 * @date 2019-06-28
 */
@Slf4j
@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Resource
    private DeliveryService deliveryService;

    @RequestMapping("/importDeliveryCodePage")
    public String importDeliveryCodePage() {
        return "import_delivery_code";
    }

    @PostMapping("/importDeliveryCode")
    @ResponseBody
    public GLResponse<String> importDeliveryCode(@RequestParam("file") MultipartFile file) {
        log.info("批量导入物流单号...");
        List<DeliveryExcelVo> voList = MultipartFileExcelFactory.getInstance().get(file, DeliveryExcelVo.class);
        if(CollectionUtils.isEmpty(voList)) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "数据为空");
        }
        List<DelivertyModel> modelList = Lists.newArrayListWithCapacity(voList.size());
        voList.forEach(vo -> modelList.add(Copier.copy(vo, new DelivertyModel())));
        return deliveryService.batchUpdateDeliveryCode(modelList);
    }
}
