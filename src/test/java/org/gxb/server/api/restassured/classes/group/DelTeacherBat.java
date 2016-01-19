package org.gxb.server.api.restassured.classes.group;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
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

public class DelTeacherBat {
    private Logger logger = LoggerFactory.getLogger(DelTeacherBat.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;

    Integer groupTeacherId;
    Integer groupTeacherId2;

    @BeforeClass(description = "获取groupTeacherId")
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("teacherId", 8888);
        jo.put("groupId", 9999);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher?loginUserId=111&tenantId=222&classId=333", jo);
       groupTeacherId = response.jsonPath().get("teacherId");

       response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher?loginUserId=111&tenantId=222&classId=333", jo);
        groupTeacherId2 = response.jsonPath().get("teacherId");


    }
    @Test(description = "正常", priority = 1)
    public void test() {
        JSONArray teachers = new JSONArray();
        teachers.add(groupTeacherId);
        teachers.add(groupTeacherId2);
        logger.info("teachers:{},",teachers.toString());

        Response response = TestConfig.postOrPutExecu("put",
                "/class_group_teacher/batch_delete?loginUserId=111&classId=333",teachers);
        logger.info("response:{},",response.jsonPath().toString());
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;
    }



    @Test(description = "class_group_teacher不存在", priority = 2)
    public void testTeachersNotExist() {
        JSONArray teachers = new JSONArray();
        teachers.add(-8);
        teachers.add(-9);
        logger.info("teachers:{},",teachers.toString());
        Response response = TestConfig.postOrPutExecu("put",
                "/class_group_teacher/batch_delete?loginUserId=111&classId=333",teachers);
        logger.info("response:{},",response.jsonPath().toString());
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("false")) ;

    }

    @Test(description = "多次删除", priority = 3)
    public void testErrclass_group_teacher() {
        JSONArray teachers = new JSONArray();
        teachers.add(groupTeacherId);
        teachers.add(groupTeacherId2);
        logger.info("teachers:{},",teachers.toString());

        Response response = TestConfig.postOrPutExecu("put",
                "/class_group_teacher/batch_delete?loginUserId=111&classId=333",teachers);
        logger.info("response:{},",response.jsonPath().toString());
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;


        //再次删除
        response = TestConfig.postOrPutExecu("put",
                "/class_group_teacher/batch_delete?loginUserId=111&classId=333",teachers);
        logger.info("response:{},",response.jsonPath().toString());
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("false")) ;

    }

}
