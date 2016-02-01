package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api/class_assignment/7/submission/user?userId=183
 * 用户提交作业详情
 */
public class GetSubmissionOfAssignment {
    Integer assignmentId;
    Integer userId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        assignmentId  = 7;
        userId = 183;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/submission/user?userId="+userId);
        //Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("submittedId", Matchers.equalTo(assignmentId))
                .body("userId", Matchers.equalTo(userId))
                .body("body", Matchers.containsString("区"))
        ;
    }

    @Test(description = "assignmentId为X" , priority = 2)
    public void test02(){
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/x/submission/user?userId="+userId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "assignmentId为-1,返回为空" , priority = 3)
    public void test03(){
        assignmentId  = -1;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/submission/user?userId="+userId);
        //Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
       Assert.assertTrue(response.asString().length()==0);
    }

    @Test(description = "userId为X" , priority = 4)
    public void test04(){
        assignmentId = 7;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/submission/user?userId=x");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "userId为-1,返回为空" , priority = 5)
    public void test05(){
        assignmentId = 7;
        userId  = -1;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/submission/user?userId="+userId);
        //Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
        Assert.assertTrue(response.asString().length()==0);
    }
}
