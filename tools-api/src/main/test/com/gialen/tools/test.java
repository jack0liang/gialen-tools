package com.gialen.tools;

import com.gialen.common.model.GLResponse;
import com.gialen.tools.api.ToolsApiApplication;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.service.StoreManagerService;
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

        GLResponse<UserAchievementModel> glResponse = storeManagerService.getCurMonthUserAchievement(7885194L, UserTypeEnum.STORE_MANAGER);

        System.out.println(glResponse.getData().toString());
    }


    public static void main(String[] args) {




    }

}
