package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/5.
 * 获取最新的学习记录
 * http://localhost:8080/gxb-api/classes/134/chapter/last?userId=931973
 */

public class GetChapterSchedule {
    Integer classeId ;
    Integer userId;
    private Logger logger = LoggerFactory.getLogger(GetChapterSchedule.class);



    @Test(description = "正常",priority = 1)
    public void test01(){
        classeId = 1606;
        userId = 1004643;
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId+"/chapter/schedule?userId="+userId);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("classId",Matchers.hasItem(classeId))
                .body("userId",Matchers.hasItem(userId))
        ;
    }
    @Test(description = "classeId = -1",priority = 2)
    public void test02(){
        classeId = -1;
        userId = 1004643;
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId+"/chapter/schedule?userId="+userId);
        Object o = response.body().jsonPath().get();
        Assert.assertEquals(o,new ArrayList(),"返回为一个空List");
        response.then().log().all().assertThat()
                .statusCode(200);
    }

    @Test(description = "classeId = -1",priority = 2)
    public void test03(){
        classeId = 1606;
        userId = -1004643;
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId+"/chapter/schedule?userId="+userId);
        Object o = response.body().jsonPath().get();
        Assert.assertEquals(o,new ArrayList(),"返回为一个空List");
        response.then().log().all().assertThat()
                .statusCode(200)
        ;
    }
}
