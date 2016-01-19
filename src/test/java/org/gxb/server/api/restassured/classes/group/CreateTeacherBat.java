package org.gxb.server.api.restassured.classes.group;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class CreateTeacherBat {
    private Logger logger = LoggerFactory.getLogger(CreateTeacherBat.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;


    @Test(description = "正常", priority = 1)
    public void test() {
        JSONArray teacher = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("teacherId", 888);
        jo.put("groupId", 999);
        teacher.add(jo);
        jo.clear();
        jo.put("teacherId", 111);
        jo.put("groupId", 222);
        teacher.add(jo);
        logger.info("teacherList:{}",teacher);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher/list?loginUserId=1&tenantId=1&classId=1", teacher);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body( equalTo("true"))
                ;
    }

    @Test(description = "teacherId为空", priority = 1)
    public void testErrTeacherId() {
        JSONArray teacher = new JSONArray();
        JSONObject jo = new JSONObject();
        //jo.put("teacherId", 888);
        jo.put("groupId", 999);
        teacher.add(jo);
        jo.clear();
        jo.put("teacherId", 111);
        jo.put("groupId", 222);
        teacher.add(jo);
        logger.info("teacherList:{}",teacher);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher/list?loginUserId=1&tenantId=1&classId=1", teacher);

        response.then().log().all().assertThat()
                .statusCode(400)
                ;
    }
    @Test(description = "groupId为空", priority = 1)
    public void testErrGroupId() {
        JSONArray teacher = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("teacherId", 888);
        //jo.put("groupId", 999);
        teacher.add(jo);
        jo.clear();
        jo.put("teacherId", 111);
        jo.put("groupId", 222);
        teacher.add(jo);
        logger.info("teacherList:{}",teacher);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher/list?loginUserId=1&tenantId=1&classId=1", teacher);

        response.then().log().all().assertThat()
                .statusCode(400)
        ;
    }

}
