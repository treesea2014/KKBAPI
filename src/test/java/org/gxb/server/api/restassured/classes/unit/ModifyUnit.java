package org.gxb.server.api.restassured.classes.unit;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class ModifyUnit {
    Integer unitId;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ModifyUnit.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    @BeforeClass
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("title", "API测试第一章");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "course/232/unit?loginUserId=5", jo);
        unitId  = response.jsonPath().get("unitId");

    }

    @Test(description = "正常",priority = 1)
    public void test(){
        JSONObject jo = new JSONObject();
        jo.put("title", "API测试第一章修改");
        jo.put("position", 888);
        Response response = TestConfig.postOrPutExecu("put",
                "course/unit/"+unitId+"?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("title", equalTo("API测试第一章修改"))
                .body("position", equalTo(888))
                .body("editorId",equalTo(123456));
    }

    @Test(description = "title为空",priority = 2)
    public void testWithInvalidTitle01(){
        JSONObject jo = new JSONObject();
        jo.put("title", "");
        jo.put("position", 888);
        Response response = TestConfig.postOrPutExecu("put",
                "course/unit/"+unitId+"?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "title长度超长",priority = 3)
    public void testWithInvalidTitle02(){
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
        Response response = TestConfig.postOrPutExecu("put",
                "course/unit/"+unitId+"?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title长度,"))
                .body("potypesition", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "position不能为空", priority = 4)
    public void testWithInvalidPosition01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是标题");
        Response response = TestConfig.postOrPutExecu("put",
                "course/unit/"+unitId+"?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("position不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "章不存在", priority = 5)
    public void testWithNotExistCourse() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是标题");
        jo.put("position", 999);

        String response = HttpRequest.sendHttpPut(Url+"/course/unit/-1?loginUserId=123456", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("章不存在",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));

    }

    @Test(description = "已发布不能修改", priority = 5)
    public void testWithublished() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是标题");
        jo.put("position", 999);

        String response = HttpRequest.sendHttpPut(Url+"/course/unit/281?loginUserId=123456", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"已发布不能修改");
        Assert.assertEquals(result.get("type"),"ServiceException");

    }

}
