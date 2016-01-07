package org.gxb.server.api.restassured.course.unit;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class ModifyUnit {
    Integer unitId;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ModifyUnit.class);
    @BeforeClass
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("title", "API测试第一章");
        jo.put("position", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "course/232/unit?loginUserId=5", jo);
        unitId  = response.jsonPath().get("unitId");

    }

    @Test
    public void test(){
        JSONObject jo = new JSONObject();
        jo.put("title", "API测试第一章修改");
        jo.put("position", 888);
        Response response = TestConfig.postOrPutExecu("put",
                "course/unit/"+unitId+"?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("courseId", equalTo(232))
                .body("title", equalTo("API测试第一章修改"))
                .body("position", equalTo(888))
                .body("userId", equalTo(5))
                .body("editorId",equalTo(5))
                .body("status", equalTo("10"));
    }

}
