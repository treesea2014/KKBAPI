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
 * http://localhost:8080/gxb-api/classes/2/announcement
 * 班次下的课程公告
 */
public class GetAnnouncement {
    Integer classid;

    @Test(description = "正常" , priority = 1)
    public void test01(){
        classid  = 2;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classid+"/announcement");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("body", Matchers.hasItem(Matchers.containsString("<p>")))
                .body("contextType", Matchers.hasItem("Course"))
                .body("announcementId", Matchers.hasItem(12))
        ;
    }


    @Test(description = "内容为空" , priority = 2)
    public void test02(){
        classid  = 0;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.getOrDeleteExecu("get","/classes/"+classid+"/announcement");
        List o = (ArrayList)response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                ;
        Assert.assertTrue(o.size()==0);
    }
    @Test(description = "内容为空" , priority = 3)
    public void test03(){
        Response response = TestConfig.getOrDeleteExecu("get","/classes/x/announcement");
        response.then().log().all()
                .statusCode(400)
                .body("message" ,Matchers.containsString("For input string"))
                .body("type" ,Matchers.containsString("Exception"))
        ;
    }
}
