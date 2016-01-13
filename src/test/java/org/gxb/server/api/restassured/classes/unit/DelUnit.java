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
 * unit删除接口 192.168.30.33:8080/gxb-api/class_unit/305?loginUserId=123 del
 */
public class DelUnit {
    private Logger logger = LoggerFactory.getLogger(DelUnit.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
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


    @Test(description = "正常删除")
    public void test(){

      Response response =  TestConfig.getOrDeleteExecu("del","/class_unit/"+unitId+"?loginUserId=123");
        response.then().log().all()
                .assertThat().statusCode(200)
                .body(Matchers.equalTo("true"));
    }

    @Test(description = "不存在的unit")
    public void testUnitNotExist(){

        String response = HttpRequest.sendHttpDelete(Url+"/class_unit/-1?loginUserId=123");
        response = response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("type"),"ServiceException");
        Assert.assertEquals(result.get("message"),"不存在的unit");
    }



}
