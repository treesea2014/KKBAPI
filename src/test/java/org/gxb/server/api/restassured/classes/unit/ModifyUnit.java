package org.gxb.server.api.restassured.classes.unit;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;
/**
 * 班次创建单元
 * 192.168.30.33:8080/gxb-api/classes/1/unit?loginUserId=123&tenantId=1
 * 涉及表class_unit
 */

public class ModifyUnit {
    private Logger logger = LoggerFactory.getLogger(CreateUnit.class);
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
        jo.put("title", "测试单元xxsea");
        jo.put("unlockAt", "1450947971236");
        jo.put("endAt", "1450947971231");
        Response response = TestConfig.postOrPutExecu("put",
                "/class_unit/"+unitId+"?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body( equalTo("true"));

    }
    @Test(description = "title超长", priority = 2)
    public void testWithInvalidTitle01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "32位字符校验32位字符校验32位字符校验32位字符校验32位字");
        jo.put("unlockAt", "1450947971236");
        jo.put("endAt", "1450947971231");
        Response response = TestConfig.postOrPutExecu("put",
                "/class_unit/"+unitId+"?loginUserId=123&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空,title长度应该在1-32之间"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }


}
