package org.gxb.server.api.restassured.course.course;

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
public class GetBasicInfo {
    Integer courseId ;
    JSONArray categoryListArray;
    JSONArray instructorListArray;

    private Logger logger = LoggerFactory.getLogger(GetBasicInfo.class);

    @Test(description = "创建基本信息,获取courseId",priority = 1)
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
                "course?loginUserId=123456&tenantId=1", jo);
        response.then().log().all();
        courseId = response.getBody().jsonPath().get("courseId");
        logger.info("courseId:{}",courseId);

    }

    @Test
    public void test(){


        logger.info("courseId:{}",courseId);

        Response response = TestConfig.getOrDeleteExecu("get", "course/"+courseId+"?loginUserId=654321&tenantId=2");
        logger.info("返回:{}",response);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("name", equalTo("查询课程名称"))
                .body("intro", equalTo("查询课程简介"))
                .body("tenantId", equalTo(1))
                .body("categoryList", Matchers.hasItem(categoryListArray))
                .body("editorId", equalTo(654321))


        ;
    }
}
