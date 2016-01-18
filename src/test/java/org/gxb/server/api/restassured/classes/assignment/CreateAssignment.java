package org.gxb.server.api.restassured.classes.assignment;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.restassured.classes.item.CreateItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class CreateAssignment {
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
    @Test(description = "正常", priority = 1,alwaysRun=true)
    public void test() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();
        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业");
        classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业");
        jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("classId", equalTo(1))
                .body("unitId", equalTo(unitId))
                .body("itemId", equalTo(itemId))
                .body("contentType", equalTo("Assignment"))
                .body("classAssignment.title", equalTo("测试作业"))
                .body("classAssignment.body", equalTo("测试内容"))
                .body("classAssignment.gradingStandard", equalTo("测试得分点"))
                .body("classAssignment.status", equalTo("10"))
                .body("classAssignment.classAssetList.title", hasItem("测试作业附件1"))
                .body("classAssignment.classAssetList.type", hasItem("AssignmentAttachment"))
        ;
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业");
        classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        //jo.put("title", "测试作业");
        jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
                ;
    }

    @Test(description = "title长度应该在1-32", priority = 3)
    public void testWithInvalidTitle02() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业");
        classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业");
        jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);
        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title长度应该在1-32,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "Position不能为空", priority = 4)
    public void testWithInvalidPosition01() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业");
        classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业测试作业测试作业测试作业");
        //jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("position不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "classAssignmentc不能为null", priority = 5)
    public void testWithInvalidClassAssignment() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业");
        classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业测试作业测试作业测试作业");
        jo.put("position", 888);
        //jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classAssignment不能为null,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }
    @Test(description = "classAssignment.gradingStandard不能为空", priority = 6)
    public void testWithInvalidGradingStandard() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业");
        classAssignment.put("body","测试内容");
        //classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业测试作业测试作业测试作业");
        jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classAssignment.gradingStandard不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }
    @Test(description = "classAssignment.title不能为空", priority = 7)
    public void testWithInvalidClassAssignmentTitle01() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        //classAssignment.put("title","测试作业");
        classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业测试作业测试作业测试作业");
        jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classAssignment.title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }
    @Test(description = "classAssignment.title长度1-32", priority = 8)
    public void testWithInvalidClassAssignmentTitle02() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业测试作业");
        classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业测试作业测试作业测试作业");
        jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classAssignment.title长度1-32,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }
    @Test(description = "classAssignment.body不能为空,", priority = 9)
    public void testWithInvalidClassAssignmentody() {
        JSONArray classAssetList = new JSONArray();
        JSONObject classAssignment = new JSONObject();

        HashMap<String, String> asset1 = new HashMap<String, String>();
        asset1.put("title", "测试作业附件1");
        asset1.put("link", "http://www.baidu.com/123");

        HashMap<String, String> asset2 = new HashMap<String, String>();
        asset2.put("title", "测试作业附件1");
        asset2.put("link", "http://www.baidu.com/123");

        classAssetList.add(asset1);
        classAssetList.add(asset2);

        classAssignment.put("title","测试作业");
        //classAssignment.put("body","测试内容");
        classAssignment.put("gradingStandard","测试得分点");
        classAssignment.put("classAssetList",classAssetList);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试作业测试作业测试作业测试作业");
        jo.put("position", 888);
        jo.put("classAssignment",classAssignment);

        logger.info("jo:{}",jo);
        Response response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/assignment?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("classAssignment.body不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }
}
