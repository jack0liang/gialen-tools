package com.gialen.tools;

import com.gialen.common.model.GLResponse;
import com.gialen.common.model.PageRequest;
import com.gialen.tools.api.ToolsApiApplication;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.service.StoreManagerService;
import com.gialen.tools.service.model.StoreUserWithDrawRespModel;
import com.gialen.tools.service.model.UserAchievementModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnection;
import org.apache.http.client.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wong
 * @Date: 2020-03-31
 * @Version: 1.0
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ToolsApiApplication.class})
public class test {

    @Autowired
    private StoreManagerService storeManagerService;

    @Test
    public void get(){

        StoreUserWithDrawRespModel glResponse = storeManagerService.getStoreUserWithdrawList(3700L,new PageRequest(1,10));

        System.out.println(glResponse);
    }


    public static void main(String[] args) {




    }

}
