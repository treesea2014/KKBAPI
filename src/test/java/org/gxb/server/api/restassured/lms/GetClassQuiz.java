package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/28.
 * http://192.168.30.33:8080/gxb-api/classes/2/quiz
 * chapterid userid 获取submission test
 */
public class GetClassQuiz {
    Integer classId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        classId  = 2;
        Response response = TestConfig.getOrDeleteExecu("get","classes/"+classId+"/quiz");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("classId", Matchers.hasItem(classId))
                .body("title", Matchers.hasItem(Matchers.containsString("测验")))
        ;
    }

    @Test(description = "classId为X" , priority = 2)
    public void test02(){
        Response response = TestConfig.getOrDeleteExecu("get","classes/x/quiz");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "chapter为-1,返回为空" , priority = 3)
    public void test03(){
        classId  = -1;
        Response response = TestConfig.getOrDeleteExecu("get","classes/"+classId+"/quiz");
        ArrayList o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
        Assert.assertTrue(o.size()==0);
    }

}
