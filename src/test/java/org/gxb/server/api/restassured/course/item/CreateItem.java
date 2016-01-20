package org.gxb.server.api.restassured.course.item;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class CreateItem {
    private Logger logger = LoggerFactory.getLogger(CreateItem.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer unitId;
    @BeforeClass(description = "课程结构course-unit－item－chapter")
    public void init() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍啊");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "课程名称");
        jo.put("intro", "课程简介");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("post",
                "course?loginUserId=123456&tenantId=111", jo);

       Integer courseId = response.jsonPath().get("courseId");


        JSONObject jo1 = new JSONObject();
        jo1.put("title", "API测试第一章");
        jo1.put("position", 999);
        response = TestConfig.postOrPutExecu("post",
                "course/"+courseId+"/unit?loginUserId=5", jo1);
        unitId = response.jsonPath().get("unitId");
    }

    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("title", "第一节xxx");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "course/unit/"+unitId+"/item?loginUserId=1234", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("editorId", equalTo(1234))
                .body("title", equalTo("第一节xxx"))
                .body("position", equalTo(999))
                ;
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "course/unit/"+unitId+"/item?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title长度需要在0和32之间,"))
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
                "course/unit/"+unitId+"/item?loginUserId=123456", jo);

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
                "course/unit/"+unitId+"/item?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("position不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "章不存在", priority = 5)
    public void testWithNotExistUnit() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是标题");
        jo.put("position", 999);

        String response = HttpRequest.sendHttpPost(Url+"/course/unit/-1/item?loginUserId=123456", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("章不存在",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));

    }
}
