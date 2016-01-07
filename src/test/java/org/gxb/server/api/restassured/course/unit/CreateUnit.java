package org.gxb.server.api.restassured.course.unit;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.slf4j.Logger;
import org.testng.annotations.Test;


import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class CreateUnit {
    private Logger logger = LoggerFactory.getLogger(CreateUnit.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("title", "API测试第一章");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "course/232/unit?loginUserId=5", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("courseId", equalTo(232))
                .body("title", equalTo("API测试第一章"))
                .body("position", equalTo(999))
                .body("userId", equalTo(5))
                .body("editorId",equalTo(5))
                .body("status", equalTo("10"));
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "course/232/unit?loginUserId=5", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "title不能超长", priority = 3)
    public void testWithInvalidTitle02() {
        JSONObject jo = new JSONObject();
        jo.put("title",  "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题" +
                "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时候" +
                "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时候" +
                "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时候" +
                "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时候" +
                "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时候" +
                "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时候" +
                "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时候" +
                "候标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题超长的时");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "course/232/unit?loginUserId=5", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能超长,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "position不能为空", priority = 4)
    public void testWithInvalidPosition01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是标题");
        Response response = TestConfig.postOrPutExecu("post",
                "course/232/unit?loginUserId=5", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("position不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "课程不存在", priority = 5)
    public void testWithNotExistCourse() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是标题");
        jo.put("position", 999);

        String response = HttpRequest.sendHttpPost(Url+"course/-9/unit?loginUserId=123456", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("课程不存在",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));

    }
}
