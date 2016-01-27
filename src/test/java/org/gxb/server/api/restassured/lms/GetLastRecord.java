package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/5.
 * 获取最新的学习记录
 * http://localhost:8080/gxb-api/classes/134/chapter/last?userId=931973
 */

public class GetLastRecord {
    String classeId ;
    private Logger logger = LoggerFactory.getLogger(GetLastRecord.class);



    @Test(description = "正常",priority = 1)
    public void test01(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "/classes/134/chapter/last?userId=931973");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("contextType",Matchers.equalTo("chapter")) ;
    }

    @Test(description = "classesId为-1,不存在",priority = 2)
    public void test02(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "/classes/-1/chapter/last?userId=931973");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(Matchers.equalTo("")) ;
    }

    @Test(description = "userId为-1,不存在",priority = 2)
    public void test03(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "/classes/134/chapter/last?userId=-1");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(Matchers.equalTo("")) ;
    }

}
