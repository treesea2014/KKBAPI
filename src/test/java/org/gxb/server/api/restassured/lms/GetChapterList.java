package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/5.
 * 获取最新的学习记录
 * http://localhost:8080/gxb-api/classes/134/chapter/last?userId=931973
 */

public class GetChapterList {
    Integer classeId ;
    String contentType;
    private Logger logger = LoggerFactory.getLogger(GetChapterList.class);



    @Test(description = "正常",priority = 1)
    public void test01(){
        classeId = 2;
        contentType = "Topic";
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId+"/unit?contentType="+contentType);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("classId",Matchers.hasItem(classeId))
                .body("itemList.chapterList.contentType",Matchers.hasItem(Matchers.hasItem(Matchers.hasItem(contentType))))
                .body("itemList.deleteFlag",Matchers.hasItem(Matchers.hasItem(1)))
        ;
    }
    @Test(description = "Topic",priority = 2)
    public void test02(){
        classeId = -1;
        contentType = "Topic";
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId+"/unit?contentType="+contentType);
        response.then().log().all().assertThat()
                .statusCode(200)
        ;
    }

    @Test(description = "contentType为空",priority = 3)
    public void test03(){
        classeId = 2;
        contentType = "";
        Response response = TestConfig.getOrDeleteExecu("get", "classes/"+classeId+"/unit?contentType="+contentType);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("classId",Matchers.hasItem(classeId))
                .body("itemList.chapterList.contentType",Matchers.hasItem(Matchers.hasItem(Matchers.hasItem("Video"))))
                .body("itemList.chapterList.contentType",Matchers.hasItem(Matchers.hasItem(Matchers.hasItem("Assignment"))))
                .body("itemList.chapterList.contentType",Matchers.hasItem(Matchers.hasItem(Matchers.hasItem("Topic"))))
                .body("itemList.chapterList.contentType",Matchers.hasItem(Matchers.hasItem(Matchers.hasItem("Quiz"))))
                .body("itemList.chapterList.contentType",Matchers.hasItem(Matchers.hasItem(Matchers.hasItem("Page"))))
                .body("itemList.deleteFlag",Matchers.hasItem(Matchers.hasItem(1)))
        ;
    }
}
