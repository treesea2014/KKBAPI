package org.gxb.server.api.restassured.classes.topic;

import com.jayway.restassured.response.Response;
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

public class CreateTopic {
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
        HashMap<String, String> classTopic = new HashMap<String, String>();
        classTopic.put("title", "测试讨论标题");
        classTopic.put("body", "测试讨论内容");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试讨论");
        jo.put("position", 888);
        jo.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("classId", equalTo(1))
                .body("unitId", equalTo(unitId))
                .body("itemId", equalTo(itemId))
                .body("contentType", equalTo("Topic"))
                .body("title", equalTo("测试讨论"))
                .body("position", equalTo(888))
                .body("classTopic.deleteFlag", equalTo(1))
                .body("classTopic.title", equalTo("测试讨论"))
                .body("classTopic.classId", equalTo(1))
                .body("classTopic.userId", equalTo(123))
                .body("classTopic.body", equalTo("测试讨论内容"))
                .body("classTopic.status", equalTo("10"))
                .body("classTopic.editorId", equalTo(123));
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        HashMap<String, String> classTopic = new HashMap<String, String>();
       classTopic.put("title", "测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论");
        classTopic.put("body", "测试讨论内容");


        JSONObject jo = new JSONObject();
        //jo.put("title", "测试讨论");
        jo.put("position", 888);
        jo.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
                ;
    }

    @Test(description = "title长度应该在1-32", priority = 3)
    public void testWithInvalidTitle02() {
        HashMap<String, String> classTopic = new HashMap<String, String>();
        classTopic.put("title", "测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论");
        classTopic.put("body", "测试讨论内容");


        JSONObject jo = new JSONObject();
        jo.put("title", "测试讨论");
        jo.put("position", 888);
        jo.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title长度应该在1-32,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "Position不能为空", priority = 4)
    public void testWithInvalidPosition01() {
        HashMap<String, String> classTopic = new HashMap<String, String>();
        classTopic.put("title", "测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论");
        classTopic.put("body", "测试讨论内容");


        JSONObject jo = new JSONObject();
        jo.put("title", "测试讨论");
        //jo.put("position", 888);
        jo.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("position不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "classTopic不能为null", priority = 5)
    public void testWithInvalidClassTopic() {
        JSONObject jo = new JSONObject();
        jo.put("title", "测试讨论");
        jo.put("position", 888);
        //jo.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classTopic不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "classTopic.title不能为空,", priority = 6)
    public void testWithInvalidClassTopicTitle01() {
        HashMap<String, String> classTopic = new HashMap<String, String>();
       // classTopic.put("title", "测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论");
        classTopic.put("body", "测试讨论内容");


        JSONObject jo = new JSONObject();
        jo.put("title", "测试讨论");
        jo.put("position", 888);
        jo.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classTopic.title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }
    @Test(description = "classTopic.body不能为空,", priority = 7)
    public void testWithInvalidClassTopicBody01() {
        HashMap<String, String> classTopic = new HashMap<String, String>();
        classTopic.put("title", "测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论测试讨论");
        //classTopic.put("body", "测试讨论内容");

        JSONObject jo = new JSONObject();
        jo.put("title", "测试讨论");
        jo.put("position", 888);
        jo.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classTopic.body不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

}
