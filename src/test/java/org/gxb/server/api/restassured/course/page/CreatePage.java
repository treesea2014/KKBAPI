package org.gxb.server.api.restassured.course.page;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class CreatePage {
    private Logger logger = LoggerFactory.getLogger(CreatePage.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer courseId;
    Integer unitId;
    Integer itemId;
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

        courseId = response.jsonPath().get("courseId");


        JSONObject jo1 = new JSONObject();
        jo1.put("title", "API测试第一章");
        jo1.put("position", 999);
        response = TestConfig.postOrPutExecu("post",
                "course/"+courseId+"/unit?loginUserId=5", jo1);
        unitId = response.jsonPath().get("unitId");

        JSONObject jo2 = new JSONObject();
        jo2.put("title", "第一节xxx");
        jo2.put("position", 999);
         response = TestConfig.postOrPutExecu("post",
                "course/unit/"+unitId+"/item?loginUserId=1234", jo2);
        itemId = response.jsonPath().getInt("itemId");
    }

    @Test(description = "正常", priority = 1)
    public void test() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("courseId", equalTo(courseId))
                .body("unitId", equalTo(unitId))
                .body("itemId", equalTo(itemId))
                .body("contentType", equalTo("Page"))
                .body("title", equalTo("我是page标题"))
                .body("position", equalTo(888))
                .body("page.courseId", equalTo(courseId))
                .body("page.title", equalTo("我是page标题"))
                .body("page.userId", equalTo(123456))
                .body("page.editorId", equalTo(123456));
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        //jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "title不能超长", priority = 3)
    public void testWithInvalidTitle02() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

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
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));

        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能超长,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "position不能为空", priority = 4)
    public void testWithInvalidPosition01() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        //jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("position不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }
    @Test(description = "page.title不能为空", priority = 5)
    public void testWithInvalidPagetitle01() {
        HashMap<String, String> page = new HashMap<String, String>();
        //page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("page.title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }
    @Test(description = "page.title超长", priority = 6)
    public void testWithInvalidPagetitle02() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "32位字符校验32位字符校验32位字符校验32位字符校验32位字");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("page.title超长,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }


    @Test(description = "page.type不能为空", priority = 7)
    public void testWithInvalidPageType() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
       // page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("page.type不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "page.type类型错误", priority = 8)
    public void testWithInvalidPageType02() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "我是page.title");
        page.put("type", "type类型错误");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);

        String response = HttpRequest.sendHttpPost(Url+"course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals(result.get("message"),"类型错误");
        Assert.assertEquals(result.get("type"),"ServiceException");


    }

    @Test(description = "page.body不能为空", priority = 9)
    public void testWithInvalidPageBody01() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        //page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        String response = HttpRequest.sendHttpPost(Url+"course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"内容不能为空");
        Assert.assertEquals(result.get("type"),"ServiceException");

    }

    @Test(description = "page.body超长", priority = 9)
    public void testWithInvalidPageBody02() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("page.body不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "item 不存在", priority = 9)
    public void testWithNotExistUnit() {
        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        String response = HttpRequest.sendHttpPost(Url+"course/item/-1/page?loginUserId=123456&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("item 不存在",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));

    }
}
