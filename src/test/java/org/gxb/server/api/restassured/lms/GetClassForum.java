package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api/class/2/forum
 * 班次下论坛
 */
public class GetClassForum {
    Integer classId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        classId  = 2;
        Response response = TestConfig.getOrDeleteExecu("get","class/"+classId+"/forum");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("deleteFlag", Matchers.hasItem(1))
                .body("classId", Matchers.hasItem(classId))
                .body("forumId", Matchers.hasItem(9217))
        ;
    }

    @Test(description = "classId为X" , priority = 2)
    public void test02(){
        Response response = TestConfig.getOrDeleteExecu("get","class/x/forum");
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
        Response response = TestConfig.getOrDeleteExecu("get","class/"+classId+"/forum");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
       Assert.assertTrue(((ArrayList)o).size()==0);
    }

}
