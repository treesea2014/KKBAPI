package org.gxb.server.api.restassured.classes.topic;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.restassured.classes.item.CreateItem;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class DelTopic {
    private Logger logger = LoggerFactory.getLogger(CreateItem.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer unitId;
    Integer topicId;
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

        HashMap<String, String> classTopic = new HashMap<String, String>();
        classTopic.put("title", "测试讨论标题");
        classTopic.put("body", "测试讨论内容");

        JSONObject jo2 = new JSONObject();
        jo2.put("title", "测试讨论");
        jo2.put("position", 888);
        jo2.put("classTopic",JSONObject.fromObject(classTopic));
        logger.info("jo:{}",jo);
        response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/"+unitId+"/item/"+itemId+"/chapter/topic?loginUserId=123&tenantId=1", jo2);

        topicId =  response.jsonPath().get("classTopic.topicId");

    }

    @Test(description = "正常删除",priority = 1)
    public void test(){

        Response response =  TestConfig.getOrDeleteExecu("del","/class_topic/"+topicId+"?loginUserId=123");
        response.then().log().all()
                .assertThat().statusCode(200)
                .body(Matchers.equalTo("true"));
    }

    @Test(description = "页面不存在",priority = 2)
    public void testWithItemIdNotExist(){

        String response = HttpRequest.sendHttpDelete(Url+"/class_topic/-1?loginUserId=123");
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"页面不存在");
        Assert.assertEquals(result.get("type"),"ServiceException");
    }


}
