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

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.*;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class GetCourseIist {
    Integer courseId ;
    JSONArray categoryListArray;
    JSONArray instructorListArray;

    private Logger logger = LoggerFactory.getLogger(GetCourseIist.class);

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

        jo.put("name", "treesea查询课程名称1");
        jo.put("intro", "查询课程简介");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 111);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("post",
                "course?loginUserId=123456&tenantId=111", jo);
        response.then().log().all();
        courseId = response.getBody().jsonPath().get("courseId");
        logger.info("courseId:{}",courseId);

    }

    @Test(description = "正常",priority = 1)
    public void test(){
       HashMap<String, Object> categoryList = new HashMap<String, Object>();

        Response response = TestConfig.getOrDeleteExecu("get", "course?filter=query:treesea查询课程名称1,status:10&loginUserId=123456&tenantId=111&sort=-courseId");
        logger.info("courseId,{}",response.jsonPath().get("dataList.courseId").toString());
        ArrayList o = new ArrayList();
        o.add(1);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("paging.curPage", equalTo(1))
                .body("sortList.sort", hasItem("userId"))
                .body("sortList.order", hasItem("DESC"))
                .body("filter.tenantId", equalTo(111))
                .body("filter.query", equalTo("treesea查询课程名称1"))

                .body("dataList.courseId", hasItem(courseId))
                .body("dataList.name", hasItem("treesea查询课程名称1"))
                .body("dataList.intro", hasItem("查询课程简介"))
                .body("dataList.tenantId", hasItem(111))
                .body("dataList.instructorList.instructorId", Matchers.hasItems(o));
    }


}
