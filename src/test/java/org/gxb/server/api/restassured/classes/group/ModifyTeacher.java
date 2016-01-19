package org.gxb.server.api.restassured.classes.group;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class ModifyTeacher {
    private Logger logger = LoggerFactory.getLogger(ModifyTeacher.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;

    Integer groupTeacherId;
    @BeforeClass(description = "获取groupTeacherId")
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("teacherId", 888);
        jo.put("groupId", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher?loginUserId=111&tenantId=222&classId=333", jo);

       groupTeacherId = response.jsonPath().get("groupTeacherId");

    }
    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("deleteFlag", 1);
        Response response = TestConfig.postOrPutExecu("put",
                "/class_group_teacher/"+groupTeacherId+"?loginUserId=987", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;
    }

    @Test(description = "只更新editId", priority = 1)
    public void testditId() {
        JSONObject jo = new JSONObject();
        Response response = TestConfig.postOrPutExecu("put",
                "/class_group_teacher/"+groupTeacherId+"?loginUserId=987", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;

    }

    @Test(description = "class_group_teacher已删除", priority = 1)
    public void testErrGroupId() {
        JSONObject jo = new JSONObject();
        Response response = TestConfig.postOrPutExecu("put",
                "/class_group_teacher/29?loginUserId=987", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("false")) ;

    }

}
