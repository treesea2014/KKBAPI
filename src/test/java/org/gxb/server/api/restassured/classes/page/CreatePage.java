package org.gxb.server.api.restassured.classes.page;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.restassured.classes.item.CreateItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class CreatePage {
    private Logger logger = LoggerFactory.getLogger(CreateItem.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer unitId;
    Integer itemId;
    @BeforeClass(description = "获取itemid")
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("title", "测试单元xxsea");
        jo.put("unlockAt", "1450947971236");
        jo.put("endAt", "1450947971231");
        Response response = TestConfig.postOrPutExecu("post",
                "classes/1/unit?loginUserId=123&tenantId=1", jo);
        //创建unit后unitId
        unitId = response.jsonPath().get("unitId");

        JSONObject jo1 = new JSONObject();
        jo1.put("title", "测试TREeseaitemt");
        // jo.put("unlockAt", 1450947971236);
        response = TestConfig.postOrPutExecu("post",
                "classes/1/unit/"+unitId+"/item?loginUserId=123&tenantId=1", jo1);

        itemId = response.jsonPath().get("itemId");

    }
    @Test(description = "正常", priority = 1)
    public void test() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "我是classPage");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
        classPage.put("type", "Link");

        JSONObject jo = new JSONObject();
        jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("classId", equalTo(1))
                .body("unitId", equalTo(unitId))
                .body("itemId", equalTo(itemId))
                .body("contentType", equalTo("Page"))
                .body("title", equalTo("我是page标题"))
                .body("position", equalTo(888))
                .body("classPage.deleteFlag", equalTo(1))
                .body("classPage.title", equalTo("我是page标题"))
                .body("classPage.classId", equalTo(1))
                .body("classPage.userId", equalTo(123))
                .body("classPage.type", equalTo("Link"))
                .body("classPage.link", equalTo("www.test.ss"))
                .body("classPage.status", equalTo("10"))
                .body("classPage.editorId", equalTo(123));
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "我是classPage");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
        classPage.put("type", "Link");

        JSONObject jo = new JSONObject();
        //jo.put("title", "我是page标题");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
               ;
    }

    @Test(description = "title不能超长", priority = 3)
    public void testWithInvalidTitle02() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "我是classPage");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
        classPage.put("type", "Link");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试页面测试页面测试页面测试页面测试页面测试页面测试页面测试页面测试页面测测试页面试页面测试页面");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能超长,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "position不能为空", priority = 4)
    public void testWithInvalidPosition01() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "我是classPage");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
        classPage.put("type", "Link");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试页面");
        //jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("position不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }
    @Test(description = "classPage.title不能为空,", priority = 5)
    public void testWithInvalidPagetitle01() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        //classPage.put("title", "我是classPage");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
        classPage.put("type", "Link");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试页面");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classPage.title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }
    @Test(description = "page.title超长", priority = 6)
    public void testWithInvalidPagetitle02() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "测试页面测试页面测试页面测试页面测试页面测试页面测试页面测试页面测试页面测测试页面试页面测试页面");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
        classPage.put("type", "Link");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试页面");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classPage.title长度为1-32,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }


    @Test(description = "page.type不能为空", priority = 7)
    public void testWithInvalidPageType() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "测试页面");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
       // classPage.put("type", "Link");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试页面");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classPage.type不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "page.type类型错误", priority = 8)
    public void testWithInvalidPageType02() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "测试页面");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
         classPage.put("type", "xx");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试页面");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);

        String response = HttpRequest.sendHttpPost(Url+"/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/page?loginUserId=123&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);
        logger.info("response:{}",response.toString());

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals(result.get("message"),"类型错误");
        Assert.assertEquals(result.get("type"),"ServiceException");
    }


    @Test(description = "item 不存在", priority = 9)
    public void testWithNotExistUnit() {
        HashMap<String, String> classPage = new HashMap<String, String>();
        classPage.put("title", "测试页面");
        classPage.put("body", "测试内容");
        classPage.put("link", "www.test.ss");
        classPage.put("type", "Text");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试页面");
        jo.put("position", 888);
        jo.put("classPage",JSONObject.fromObject(classPage));
        logger.info("jo:{}",jo);
        logger.info("jo:{}",jo);
        String response = HttpRequest.sendHttpPost(Url+"/classes/1/unit/"+unitId+"/item/-1/chapter/page?loginUserId=123&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(1000,result.get("code"));
        Assert.assertEquals("item 不存在",result.get("message"));
        Assert.assertEquals("ServiceException",result.get("type"));

    }
}
