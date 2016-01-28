package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api//learn/record/116/user?userId=0&mh=100&ch=20
 * 打开视频等的播放页初始化
 */
public class PostLearnLog{
    Integer chapterId;
    Integer userId;
    @Test(description = "正常" , priority = 1)
    public void test01(){
        chapterId  = 41;
        userId = 999;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.postOrPutExecu("post","learn/log/"+chapterId+"/user?userId="+userId+"&redirectUri=class/123/&schoolId=1",new JSONObject());
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200);
    }
    @Test(description = "正常" , priority = 2)
    public void test02(){
        chapterId  = 41;
        userId = 0;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.postOrPutExecu("post","learn/log/"+chapterId+"/user?userId="+userId+"&redirectUri=class/123/&schoolId=1",new JSONObject());
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200);
    }

}
