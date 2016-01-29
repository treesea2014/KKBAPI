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
 * 更新学习记录
 */
public class PostLearnRecord {
    Integer chapterId;
    Integer userId;
    @Test(description = "正常" , priority = 1)
    public void test(){
        chapterId  = 108;
        userId = 2;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.postOrPutExecu("post","/learn/record/"+chapterId+"/user?userId="+userId+"&mh=100&ch=20",new JSONObject());
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
        ;
    }
    @Test(description = "正常" , priority = 1)
    public void testNullPoint(){
        chapterId  = 999991;
        userId = 2;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.postOrPutExecu("post","/learn/record/"+chapterId+"/user?userId="+userId+"&mh=100&ch=20",new JSONObject());
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(400)
        ;
    }

}
