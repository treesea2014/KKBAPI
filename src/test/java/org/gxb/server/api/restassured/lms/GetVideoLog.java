package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by treesea on 16/1/28.
 * http://localhost:8080/gxb-api//learn/record/116/user?userId=0&mh=100&ch=20
 * 打开视频等的播放页初始化
 */
public class GetVideoLog {
    Integer classid;

    @Test(description = "正常" , priority = 1)
    public void test01(){
        classid  = 41;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.getOrDeleteExecu("get","/video/log/"+classid+"/init");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("ok", Matchers.equalTo(""))
        ;
    }


    @Test(description = "正常" , priority = 1)
    public void test02(){
        classid  = 12331;
        Map<String, Object> map = new HashMap<>();
        Response response = TestConfig.getOrDeleteExecu("get","/video/log/"+classid+"/init");
        Object o = response.jsonPath().get();
        response.then().log().all()
                .statusCode(200)
                .body("error", Matchers.notNullValue())
        ;
    }

}
