package org.gxb.server.api.restassured.classes.unit;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class DelUnit {
    Integer unitId;
    private Logger logger = LoggerFactory.getLogger(DelUnit.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
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
         unitId = response.jsonPath().get("unitId");

    }

    @Test(description = "正常删除")
    public void test(){

      Response response =  TestConfig.getOrDeleteExecu("del","course/unit/"+unitId+"?loginUserId=123456");
        response.then().log().all()
                .assertThat().statusCode(200)
                .body(Matchers.equalTo("true"));
    }

    @Test(description = "章节不存在")
    public void testUnitNotExist(){

        String response = HttpRequest.sendHttpDelete(Url+"/course/unit/-1?loginUserId=123456");
        response = response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("type"),"ServiceException");
        Assert.assertEquals(result.get("message"),"章不存在");
    }

    @Test(description = "281已发布不能删除")
    public void testUnitPublished(){

        String response = HttpRequest.sendHttpDelete(Url+"/course/unit/281?loginUserId=123456");
        response = response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("type"),"ServiceException");
        Assert.assertEquals(result.get("message"),"已发布不能删除");
    }
}
