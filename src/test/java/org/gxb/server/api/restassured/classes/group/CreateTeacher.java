package org.gxb.server.api.restassured.classes.group;

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

public class CreateTeacher {
    private Logger logger = LoggerFactory.getLogger(CreateTeacher.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;


    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("teacherId", 888);
        jo.put("groupId", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher?loginUserId=111&tenantId=222&classId=333", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("deleteFlag", equalTo(1))
                .body("classId", equalTo(333))
                .body("userId", equalTo(111))
                .body("editorId", equalTo(111))
                .body("tenantId", equalTo(222))
                .body("teacherId", equalTo(888))
                .body("groupId", equalTo(999));
    }

    @Test(description = "teacherId为空", priority = 1)
    public void testErrTeacherId() {
        JSONObject jo = new JSONObject();
        //jo.put("teacherId", 888);
        jo.put("groupId", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher?loginUserId=111&tenantId=222&classId=333", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                ;
    }
    @Test(description = "teacherId为空", priority = 1)
    public void testErrGroupId() {
        JSONObject jo = new JSONObject();
        jo.put("teacherId", 888);
        //jo.put("groupId", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher?loginUserId=111&tenantId=222&classId=333", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
        ;
    }

}
