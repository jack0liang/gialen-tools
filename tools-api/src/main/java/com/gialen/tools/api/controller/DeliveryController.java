package com.gialen.tools.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 快递工具控制器
 * @author lupeibo
 * @date 2019-06-28
 */
@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @RequestMapping("/importDeliveryCodePage")
    public String importDeliveryCodePage() {
        return "import_delivery_code";
    }

    @RequestMapping("/importDeliveryCode")
    public String importDeliveryCode(@RequestParam("file") MultipartFile file) {
        return null;
    }
}
