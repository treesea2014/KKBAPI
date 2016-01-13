package org.gxb.server.api.restassured.classes.page;

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

public class ModifyPage {
    private Logger logger = LoggerFactory.getLogger(CreatePage.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer courseId;
    Integer unitId;
    Integer itemId;
    Integer pageId;
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
                "course?loginUserId=123456&tenantId=1", jo);

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

        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo3 = new JSONObject();
        jo3.put("title", "我是page标题");
        jo3.put("position", 888);
        jo3.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo3);
        pageId = response.jsonPath().get("page.pageId");
    }

    @Test(description = "正常", priority = 1)
    public void test01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        jo.put("type", "Text");
        jo.put("body","我是page修改body");
        logger.info("jo:{},{}",jo,pageId);
        Response response = TestConfig.postOrPutExecu("put",
                "/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("pageId", equalTo(pageId))
                .body("title", equalTo("我是page标题修改"))
                .body("body", equalTo("我是page修改body"))
                .body("type", equalTo("Text"))
                .body("editorId", equalTo(654321))
              ;
    }
    @Test(description = "正常", priority = 1)
    public void test02() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        jo.put("type", "Link");
        jo.put("link","我是page修改link");
        logger.info("jo:{},{}",jo,pageId);
        Response response = TestConfig.postOrPutExecu("put",
                "/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("pageId", equalTo(pageId))
                .body("title", equalTo("我是page标题修改"))
                .body("link", equalTo("我是page修改link"))
                .body("type", equalTo("Link"))
                .body("editorId", equalTo(654321))
        ;
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        JSONObject jo = new JSONObject();
       // jo.put("title", "我是page标题修改");
        jo.put("type", "Text");
        jo.put("body","我是page修改body");
        logger.info("jo:{},{}",jo,pageId);
        Response response = TestConfig.postOrPutExecu("put",
                "/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
                ;
    }

    @Test(description = "title长度应该为1-32", priority = 2)
    public void testWithInvalidTitle02() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改我是page标题修改我是page标题修改我是");
        jo.put("type", "Text");
        jo.put("body","我是page修改body");
        logger.info("jo:{},{}",jo,pageId);
        Response response = TestConfig.postOrPutExecu("put",
                "/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title长度应该为1-32"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "type不能为空", priority = 3)
    public void testWithInvalidType01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        //jo.put("type", "Text");
        jo.put("body","我是page修改body");
        logger.info("jo:{},{}",jo,pageId);
        Response response = TestConfig.postOrPutExecu("put",
                "/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("type不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "类型错误", priority = 4)
    public void testWithInvalidType02() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        jo.put("type", "xx");
        jo.put("body","我是page修改body");
        logger.info("jo:{},{}",jo,pageId);
        String response = HttpRequest.sendHttpPut(Url+"/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"类型错误");
        Assert.assertEquals(result.get("type"),"ServiceException");
    }
    @Test(description = "body不能为空", priority = 5)
    public void testWithInvalidBody01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        jo.put("type", "Text");
        //jo.put("body","我是page修改body");
        logger.info("jo:{},{}",jo,pageId);
        String response = HttpRequest.sendHttpPut(Url+"/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("内容不能为空",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));

    }
    @Test(description = "body超长", priority = 6)
    public void testWithInvalidBody02() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        jo.put("type", "Text");
        jo.put("body","32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字32位字符校验32位字符校验32位字符校验32位字符校验32位字");
        logger.info("jo:{},{}",jo,pageId);
        Response response = TestConfig.postOrPutExecu("put",
                "/course/page/"+pageId+"?loginUserId=654321&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("body超长,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "link不能为空", priority = 7)
    public void testWithInvalidLink01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        jo.put("type", "Link");
        //jo.put("link","我是page修改body");
        logger.info("jo:{},{}",jo,pageId);
        String response = HttpRequest.sendHttpPut(Url+"/course/page/-1?loginUserId=654321&tenantId=111", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("链接不能为空",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));
        ;
    }

    @Test(description = "pageId 不存在", priority = 9)
    public void testWithNotExistPage() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题修改");
        jo.put("type", "Link");
        jo.put("link","我是page修改body");
        logger.info("jo:{}",jo);
        String response = HttpRequest.sendHttpPut(Url+"/course/page/-1?loginUserId=654321&tenantId=111", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("页面不存在",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));

    }
}
