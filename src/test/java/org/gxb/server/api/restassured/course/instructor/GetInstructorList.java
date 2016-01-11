package org.gxb.server.api.restassured.course.instructor;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class GetInstructorList {
    Integer intructorId;


    private Logger logger = LoggerFactory.getLogger(GetInstructorList.class);

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
                "instructor?loginUserId=123456&tenantId=111", jo);

        intructorId = response.jsonPath().get("instructorId");
    }

    @Test(description = "正常", priority = 1)
    public void test() {
        Response response = TestConfig.getOrDeleteExecu("get", "/instructor?loginUserId=123456&tenantId=111");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("paging.curPage", equalTo(1))
                .body("filter.tenantId", equalTo(111))
                .body("dataList.instructorId", hasItem(intructorId))
                .body("dataList.tenantId", hasItem(111))
        ;
    }

    @Test(description = "错误的tenantId", priority = 2)
    public void testWith() {
        Response response = TestConfig.getOrDeleteExecu("get", "/instructor?loginUserId=123456&tenantId=-111");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("paging.curPage", equalTo(0))
                .body("filter.tenantId", equalTo(-111))
                .body("dataList",equalTo(null));
    }

}
