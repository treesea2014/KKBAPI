package org.gxb.server.api.restassured.course.unit;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class DelUnit {
    Integer intructorId;


    private Logger logger = LoggerFactory.getLogger(DelUnit.class);

    @BeforeClass(description = "创建讲师,获取intructorId")
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("title", "教授");
        jo.put("name", "邓树海");
        jo.put("intro", "著名学者");
        jo.put("avatar", "www.baidu.com");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "标签");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        intructorId = response.jsonPath().get("instructorId");
    }

    @Test(description = "正常", priority = 1)
    public void test() {
        Response response = TestConfig.getOrDeleteExecu("get", "instructor/" + intructorId + "?loginUserId=123456&tenantId=1");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("instructorId", equalTo(intructorId))
                .body("title", equalTo("教授"))
                .body("name", equalTo("邓树海"))
                .body("intro", equalTo("著名学者"))
                .body("avatar", equalTo("www.baidu.com"))
                .body("sinaWeibo", equalTo("shdeng@sina.com"))
                .body("description", equalTo("简单描述"))
                .body("wechat", equalTo("微信"))
                .body("tag", equalTo("标签"));
    }

    @Test(description = "intructorId不存在", priority = 2)
    public void testWithInvalidInstructorId01() {
        intructorId = -1 ;
        Response response = TestConfig.getOrDeleteExecu("get", "instructor/" + intructorId + "?loginUserId=123456&tenantId=1");
        response.then().log().all().assertThat()
                .statusCode(200);
    }

    @Test(description = "intructorId超长", priority = 3)
    public void testWithInvalidInstructorId02() {
        Response response = TestConfig.getOrDeleteExecu("get", "instructor/ 9999999999999999999?loginUserId=123456&tenantId=1");
        response.then().log().all().assertThat()
                .statusCode(400) ;
    }
}
