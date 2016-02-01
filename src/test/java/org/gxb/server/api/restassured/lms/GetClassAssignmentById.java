package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api/class_assignment/11
 * assignmentId获取chapter
 */
public class GetClassAssignmentById {
    Integer assignmentId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        assignmentId  = 11;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("assignmentId", Matchers.equalTo(assignmentId))
                .body("deleteFlag", Matchers.equalTo(1))
                .body("title", Matchers.containsString("作业"))
        ;
    }

    @Test(description = "assignmentId为X" , priority = 2)
    public void test02(){
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/x");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "chapter为-1,返回为空" , priority = 3)
    public void test03(){
        assignmentId  = -1;
        Response response = TestConfig.getOrDeleteExecu("get","class_assignment/"+assignmentId);
        //Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
       Assert.assertTrue(response.asString().length()==0);
    }

}
