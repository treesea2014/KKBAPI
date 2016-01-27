package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/5.
 * 学习进度
 * http://localhost:8080/gxb-api/classes/1606/schedule?userId=1004643
 */

public class GetClassSchedule {
    String classeId ;
    private Logger logger = LoggerFactory.getLogger(GetClassSchedule.class);



    @Test(description = "正常",priority = 1)
    public void test01(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "/classes/1606/schedule?userId=1004643");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(Matchers.equalTo("47")) ;
    }
    @Test(description = "userId为不存在",priority = 2)
    public void test02(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "/classes/1606/schedule?userId=-1");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(Matchers.equalTo("0"))
        ;
    }
    @Test(description = "userId为不存在",priority = 3)
    public void test03(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "/classes/-1/schedule?userId=-1");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(Matchers.equalTo("0"))
        ;
    }

}
