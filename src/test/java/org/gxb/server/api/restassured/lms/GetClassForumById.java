package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/28.
 *http://localhost:8080/gxb-api/forum/4
 * ID获取板块信息
 */
public class GetClassForumById {
    Integer forumId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        forumId  = 2;
        Response response = TestConfig.getOrDeleteExecu("get","/forum/"+forumId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("deleteFlag", Matchers.equalTo(1))
                .body("forumId", Matchers.equalTo(forumId))
        ;
    }

    @Test(description = "forumId为X" , priority = 2)
    public void test02(){
        Response response = TestConfig.getOrDeleteExecu("get","/forum/x");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "forumId -1,返回为空" , priority = 3)
    public void test03(){
        forumId  = -1;
        Response response = TestConfig.getOrDeleteExecu("get","/forum/"+forumId);
       // Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
       Assert.assertTrue(response.asString().length()==0);
    }

}
