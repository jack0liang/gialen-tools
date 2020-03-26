package com.gialen.tools.api;

import com.gialen.common.annotation.GialenApp;
import com.gialen.rpc.dubbo.GialenDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"com.gialen.tools"})
@GialenDubbo(scanBasePackages = {"com.gialen.tools"})
public class ToolsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApiApplication.class, args);
    }

}
