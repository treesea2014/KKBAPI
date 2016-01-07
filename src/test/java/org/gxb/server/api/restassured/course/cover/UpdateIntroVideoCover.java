package org.gxb.server.api.restassured.course.cover;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author shdeng@gaoxiaobang.com
 * @version 1.0.0
 * @date 2015.11.06
 * @decription 更新课程封面 涉及表：course
 * http://192.168.30.33:8080/gxb-api//course/2/introVideoCover?loginUserId=123456
 */
public class UpdateIntroVideoCover {
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;

    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("introVideoCover", "123.jpg");
        Response response = TestConfig.postOrPutExecu("put",
                "/course/2/introVideoCover?loginUserId=123456", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true"));
    }
    @Test(description = "课程介绍封面不能为空", priority = 2)
    public void testWithEmptyCover() {
        JSONObject jo = new JSONObject();
        jo.put("introVideoCover", "");

        String response = HttpRequest.sendHttpPut(Url + "/course/2/introVideoCover?loginUserId=123456", jo);
        response = response.substring(response.indexOf("&") + 1, response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("type"),"ServiceException");
        Assert.assertEquals(result.get("message"),"课程介绍封面不能为空");
    }

    @Test(description = "课程不存在", priority = 3)
    public void testWithCourseNotExist() {
        JSONObject jo = new JSONObject();
        jo.put("introVideoCover", "123.jpg");

        String response = HttpRequest.sendHttpPut(Url + "/course/-1/introVideoCover?loginUserId=123456", jo);
        response = response.substring(response.indexOf("&") + 1, response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("type"),"ServiceException");
        Assert.assertEquals(result.get("message"),"课程不存在");
    }


}
