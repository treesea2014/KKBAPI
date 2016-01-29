package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by treesea on 16/1/28.
 * http://192.168.30.33:8080/gxb-api/classes/9999/submission?userId=826920&submittedType=Assignment
 * 用户提交的作业、讨论信息 test
 */
public class GetSubmission {
    Integer classid;
    Integer userId;
    String submittedType;

    @Test(description = "正常" , priority = 1)
    public void test01(){
        classid  = 2;
        userId = 826920;
        submittedType = "Assignment";
        Response response = TestConfig.getOrDeleteExecu("get","classes/"+classid+"/submission?userId="+userId+"&submittedType="+submittedType);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("submittedType", Matchers.hasItem(submittedType))
                .body("userId", Matchers.hasItem(userId))
                .body("contextId", Matchers.hasItem(classid))
                .body("body", Matchers.hasItem(Matchers.containsString("你")))
        ;
    }

    @Test(description = "classid = x" , priority = 2)
    public void test02(){
        classid  = 2;
        userId = 826920;
        submittedType = "Assignment";
        Response response = TestConfig.getOrDeleteExecu("get","classes/x/submission?userId="+userId+"&submittedType="+submittedType);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
                .body("message", Matchers.containsString("For input string"))
                .body("type", Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "classid = 9999,返回应为[]" , priority = 3)
    public void test03(){
        classid  = 99999;
        userId = 826920;
        submittedType = "Assignment";
        Response response = TestConfig.getOrDeleteExecu("get","classes/"+classid+"/submission?userId="+userId+"&submittedType="+submittedType);
        ArrayList o = (ArrayList)response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
        Assert.assertTrue(o.size()==0);
    }
}
