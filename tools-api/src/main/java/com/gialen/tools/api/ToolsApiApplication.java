package com.gialen.tools.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
//@EnableScheduling
@ComponentScan(basePackages = {"com.gialen.tools"})
public class ToolsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApiApplication.class, args);
    }

}
