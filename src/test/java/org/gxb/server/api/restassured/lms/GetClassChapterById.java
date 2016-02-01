package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by treesea on 16/1/28.
 * http://192.168.30.33:8080/gxb-api/class_chapter/42
 * chapterid获取chapter
 */
public class GetClassChapterById {
    Integer chapterId;


    @Test(description = "正常" , priority = 1)
    public void test01(){
        chapterId  = 42;
        Response response = TestConfig.getOrDeleteExecu("get","class_chapter/"+chapterId);
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("chapterId", Matchers.equalTo(chapterId))
                .body("title", Matchers.containsString("知识"))
        ;
    }

    @Test(description = "chapterId为X" , priority = 2)
    public void test02(){
        Response response = TestConfig.getOrDeleteExecu("get","class_chapter/x");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
                .body("message",Matchers.containsString("For"))
                .body("type",Matchers.containsString("Exception"))
        ;
    }

    @Test(description = "chapter为-1,返回为空" , priority = 3)
    public void test03(){
        chapterId  = -1;
        Response response = TestConfig.getOrDeleteExecu("get","class_chapter/"+chapterId);
        //Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
       Assert.assertTrue(response.asString().length()==0);
    }

}
