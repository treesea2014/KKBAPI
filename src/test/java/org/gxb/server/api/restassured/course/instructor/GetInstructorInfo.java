package org.gxb.server.api.restassured.course.instructor;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;


import static org.hamcrest.Matchers.equalTo;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class GetInstructorInfo {
    Integer courseId ;
    JSONArray categoryListArray;
    JSONArray instructorListArray;

    private Logger logger = LoggerFactory.getLogger(GetInstructorInfo.class);

    @BeforeClass(description = "创建基本信息,获取courseId")
    public void CreateBasicInfo(){
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "查询详细介绍");

         categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

         instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "查询api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "查询课程名称");
        jo.put("intro", "查询课程简介");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("post",
                "course?loginUserId=654321&tenantId=1", jo);
        response.then().log().all();
        courseId = response.getBody().jsonPath().get("courseId");
        logger.info("courseId:{}",courseId);

    }

    @Test(description = "正常",priority = 1)
    public void test(){
       HashMap<String, Object> categoryList = new HashMap<String, Object>();
        categoryList.put("categoryId", 1);
        categoryList.put("categoryName", "java相关");
        Response response = TestConfig.getOrDeleteExecu("get", "course/"+courseId+"?loginUserId=654321&tenantId=2");

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("name", equalTo("查询课程名称"))
                .body("intro", equalTo("查询课程简介"))
                .body("tenantId", equalTo(1))
                .body("categoryList", Matchers.hasItem(categoryList))
                .body("editorId", equalTo(654321)) ;
    }

    @Test(description = "正常",priority = 2)
    public void testWithInvalidCourseId01(){
        HashMap<String, Object> categoryList = new HashMap<String, Object>();
        categoryList.put("categoryId", 1);
        categoryList.put("categoryName", "java相关");
        Response response = TestConfig.getOrDeleteExecu("get", "course/-1?loginUserId=654321&tenantId=2");

        response.then().log().all().assertThat()
                .statusCode(200);
    }

    @Test(description = "正常",priority = 3)
    public void testWithInvalidCourseId(){
        Response response = TestConfig.getOrDeleteExecu("get", "course/"+2+"?loginUserId=654321&tenantId=2");

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("name", equalTo("我的课程"))
                .body("intro", equalTo("简介"))
                .body("tenantId", equalTo(111))
                .body("editorId", equalTo(541513))
                .body("courseInfo.description", equalTo( "详细介绍啊"))
                .body("categoryList.categoryName", Matchers.hasItem("Spring"))
                .body("classList.className", Matchers.hasItem("188"))

        ;
    }
}
