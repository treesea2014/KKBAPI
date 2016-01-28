package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/5.
 * 获取用户学习记录
 * http://localhost:8080/gxb-api/learn/record/108/user?userId=0
 */

public class GetLearnRecord {
    Integer userId ;
    Long chapterId ;
    private Logger logger = LoggerFactory.getLogger(GetLearnRecord.class);



    @Test(description = "正常",priority = 1)
    public void test01(){
        chapterId = 108L;
        userId = 0;
        Response response = TestConfig.getOrDeleteExecu("get", "learn/record/"+chapterId+"/user?userId="+userId);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("chapterId",Matchers.equalTo(chapterId))
                .body("userId",Matchers.equalTo(userId))
                .body("videoLength",Matchers.notNullValue())

        ;
    }

    @Test(description = "userId=x",priority = 2)
    public void test02(){
        chapterId = 108L;
        Response response = TestConfig.getOrDeleteExecu("get", "learn/record/"+chapterId+"/user?userId=x");
        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message",Matchers.containsString("For input string"))
                .body("code",Matchers.equalTo(400))
                .body("type",Matchers.equalTo("NumberFormatException"))
        ;
    }

    @Test(description = "userId=x",priority = 2)
    public void test03(){
        chapterId = 999999999999999999L;
        Response response = TestConfig.getOrDeleteExecu("get", "learn/record/"+chapterId+"/user?userId=x");
        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message",Matchers.containsString("For input string"))
                .body("code",Matchers.equalTo(400))
                .body("type",Matchers.equalTo("NumberFormatException"))
        ;
    }

}
