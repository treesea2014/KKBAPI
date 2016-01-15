package org.gxb.server.api.restassured.classes.item;

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

public class CreateItem {
    private Logger logger = LoggerFactory.getLogger(CreateItem.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer unitId;
    @BeforeClass(description = "正常")
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("title", "测试单元xxsea");
        jo.put("unlockAt", "1450947971236");
        jo.put("endAt", "1450947971231");
        Response response = TestConfig.postOrPutExecu("post",
                "classes/1/unit?loginUserId=123&tenantId=1", jo);

        unitId = response.jsonPath().get("unitId");

    }

    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("title", "测试TREeseaitemt");
       // jo.put("unlockAt", 1450947971236);
        Response response = TestConfig.postOrPutExecu("post",
                "classes/1/unit/"+unitId+"/item?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("deleteFlag", equalTo(1))
                .body("unitId", equalTo(unitId))
                .body("classId", equalTo(1))
                .body("userId", equalTo(123))
                .body("editorId", equalTo(123))
                .body("title", equalTo("测试TREeseaitemt"))
                .body("status", equalTo("10"))
        ;
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "classes/1/unit/"+unitId+"/item?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "title不能超长", priority = 3)
    public void testWithInvalidTitle02() {
        JSONObject jo = new JSONObject();
        jo.put("title",  "标题超长的时候标题超长的时候标题超长的时候标题超长的时候标题时候标题");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "classes/1/unit/"+unitId+"/item?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能超长,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }



    @Test(description = "unit不存在", priority = 5)
    public void testWithNotExistUnit() {
        JSONObject jo = new JSONObject();
        jo.put("title", "我是标题");

        String response = HttpRequest.sendHttpPost(Url+"classes/1/unit/-1/item?loginUserId=123&tenantId=1", jo);
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"unit不存在");
        Assert.assertEquals(result.get("type"),"ServiceException");

    }
}
