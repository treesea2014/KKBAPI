package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api/classes/2/chapter/expiring
 * 即将过期的chapter
 */
public class GetExpiringChapter {
    Integer classId;

    @Test(description = "正常" , priority = 1)
    public void test01(){
        classId  = 2;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/chapter/expiring");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("deleteFlag", Matchers.hasItem(1))
                .body("contentType",Matchers.hasItem("Assignment"))
                .body("classAssignment",Matchers.hasSize(1))
        ;
    }

    @Test(description = "数据为零" , priority = 2)
    public void test02(){
        classId  = 0;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classId+"/chapter/expiring");
        ArrayList o = (ArrayList)response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
           ;
        Assert.assertTrue(o.size()==0,"数据为零");
    }

}
