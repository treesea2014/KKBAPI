package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api/classes/1/chapter/45/submission?userId=183
 * chapterid userid 获取submission test
 */
public class GetSubmissionOfChaper {
    Integer classId;
    Integer chapterId;
    Integer userId;

    @Test(description = "正常" , priority = 1)
    public void test01(){
        classId  = 1;
        userId = 183;
        chapterId = 45;
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/chapter/"+chapterId+"/submission?userId="+userId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("userId", Matchers.hasItem(userId));
    }

    @Test(description = "classId为X" , priority = 2)
    public void test02(){

        userId = 183;
        chapterId = 45;
        Response response = TestConfig.getOrDeleteExecu("get","/classes/x/chapter/"+chapterId+"/submission?userId="+userId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
        ;
    }

    @Test(description = "chapter为X,返回为空" , priority = 3)
    public void test03(){
        classId  = 1;
        userId = 78787;
        chapterId = 45;
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/chapter/x/submission?userId="+userId);
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "userId为X,返回为空" , priority = 4)
    public void test04(){
        classId  = 1;
        userId = 78787;
        chapterId = 45;
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/chapter/x/submission?userId=x");
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "返回为空" , priority = 5)
    public void test05(){
        classId  = 111;
        userId = 78787;
        chapterId = 45;
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/chapter/"+chapterId+"/submission?userId="+userId);
        response.then().log().all()
                .statusCode(200)
        ;
    }

}
