package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/5.
 * 班次信息
 * http://localhost:8080/gxb-api/classes/1
 */

public class GetClassInfoById {
    String classeId ;
    private Logger logger = LoggerFactory.getLogger(GetClassInfoById.class);



    @Test(description = "正常",priority = 1)
    public void test01(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("classId" , Matchers.equalTo(Integer.parseInt(classeId)))
        ;
    }
    @Test(description = "classid不存在",priority = 1)
    public void test02(){
        classeId = "-1";
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body( Matchers.equalTo(""))
        ;
    }
    @Test(description = "参数错误",priority = 1)
    public void test03(){
        classeId = "x";
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId);
        response.then().log().all().assertThat()
                .statusCode(400)
        ;
    }
}
