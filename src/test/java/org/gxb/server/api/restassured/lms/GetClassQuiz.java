package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api/classes/2/quiz
 * 班次下测验
 */
public class GetClassQuiz {
    Integer classId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        classId  = 2;
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/quiz");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("deleteFlag", Matchers.hasItem(1))
                .body("classId", Matchers.hasItem(classId))
                .body("title", Matchers.hasItem(Matchers.containsString("测验")))
                .body("quizId", Matchers.hasItem(11));
    }

    @Test(description = "返回为空" , priority = 2)
    public void test02(){
        classId  = -1;
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/quiz");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
        Assert.assertTrue(((ArrayList)o).size()==0);
    }

    @Test(description = "classId为X" , priority = 3)
    public void test03(){
        Response response = TestConfig.getOrDeleteExecu("get","/classes/x/quiz");

        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("NumberFormatException"))
       ;
    }

}
