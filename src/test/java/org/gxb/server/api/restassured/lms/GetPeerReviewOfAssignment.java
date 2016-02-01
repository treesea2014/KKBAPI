package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import com.sun.istack.internal.NotNull;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api/class_assignment/7/submission/user?userId=183
 * 用户提交作业详情
 */
public class GetPeerReviewOfAssignment {
    Integer assignmentId;
    Integer userId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        assignmentId  = 3303;
        userId = 6790;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/peerReview?userId="+userId);

        response.then().log().all()
                .statusCode(200)
                .body("assignmentId", Matchers.hasItem(assignmentId))
                .body("reviewUserId", Matchers.hasItem(userId))
        ;
    }

    @Test(description = "assignmentId为X" , priority = 2)
    public void test02(){
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/x/peerReview?userId="+userId);
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
        userId = 1;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/peerReview?userId="+userId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
        System.out.print(response.asString());
        Assert.assertTrue(((ArrayList)o).size()==0);
    }

    @Test(description = "userId为X" , priority = 4)
    public void test04(){
        assignmentId = 7;

        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/peerReview?userId=x");
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
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId+"/peerReview?userId="+userId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
        Assert.assertTrue(((ArrayList)o).size()==0);
    }
}
