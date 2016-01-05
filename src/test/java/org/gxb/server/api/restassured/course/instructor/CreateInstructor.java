package org.gxb.server.api.restassured.course.instructor;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

public class CreateInstructor {
    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("title", "教授");
        jo.put("name", "邓树海");
        jo.put("intro", "著名学者");
        jo.put("avatar", "www.baidu.com");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "微信");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("name", equalTo("邓树海"))
                .body("title", equalTo("教授"))
                .body("intro", equalTo("著名学者"))
                .body("description", equalTo("简单描述"))
                .body("avatar", equalTo("www.baidu.com"))
                .body("sinaWeibo", equalTo("shdeng@sina.com"))
                .body("wechat", equalTo("微信"))
                .body("tag", equalTo("微信"));
    }
    @Test(description = "title职称输入为空", priority = 2)
    public void testWithInvalidTitle01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "");
        jo.put("name", "邓树海");
        jo.put("intro", "著名学者");
        jo.put("avatar", "www.baidu.com");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "微信");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("titlemay not be empty,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "title职称输入为超长33", priority = 3)
    public void testWithInvalidTitle02() {
        JSONObject jo = new JSONObject();
        jo.put("title", "头衔头衔头衔头衔头衔头衔头衔头衔头衔头衔头衔头衔头衔头衔头衔12");
        jo.put("name", "邓树海");
        jo.put("intro", "著名学者");
        jo.put("avatar", "www.baidu.com");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "微信");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title 超长"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "name教师姓名输入为空", priority = 4)
    public void testWithInvalidName01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "头衔");
        jo.put("name", "");
        jo.put("intro", "著名学者");
        jo.put("avatar", "www.baidu.com");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "微信");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("namemay not be empty,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "intro输入为空", priority = 5)
    public void testWithInvalidItro01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "教授");
        jo.put("name", "邓树海");
        jo.put("intro", "");
        jo.put("avatar", "www.baidu.com");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "微信");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("intromay not be empty,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "intro输入为超长65", priority = 6)
    public void testWithInvalidIntro02() {
        JSONObject jo = new JSONObject();
        jo.put("title", "教授");
        jo.put("name", "邓树海");
        jo.put("intro", "著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者著名学者");
        jo.put("avatar", "www.baidu.com");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "微信");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("intro超长"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "avatar输入为空", priority = 7)
    public void testWithInvalidntro01() {
        JSONObject jo = new JSONObject();
        jo.put("title", "教授");
        jo.put("name", "邓树海");
        jo.put("intro", "简介");
        jo.put("avatar", "");
        jo.put("sinaWeibo", "shdeng@sina.com");
        jo.put("description", "简单描述");
        jo.put("wechat", "微信");
        jo.put("tag", "微信");

        Response response = TestConfig.postOrPutExecu("post",
                "instructor?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("avatarmay not be empty,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }
}
