package org.gxb.server.api.restassured.course.category;

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

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class GetCategory {
    Integer courseId ;
    JSONArray categoryListArray;
    JSONArray instructorListArray;

    private Logger logger = LoggerFactory.getLogger(GetCategory.class);



    @Test(description = "正常",priority = 1)
    public void test(){

        Response response = TestConfig.getOrDeleteExecu("get", "/tenant/111/category");
        Object o = response.jsonPath().get("childList.categoryName");
        o.toString();
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("categoryName", Matchers.hasItem("java相关"))
                .body("shortName", Matchers.hasItem("java"))
                .body("tenantId", Matchers.hasItem(111))

                ;
    }
    @Test(description = "内容为空",priority = 2)
    public void testWithErrorTenId(){

        Response response = TestConfig.getOrDeleteExecu("get", "/tenant/-1/category");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(Matchers.containsString(""))
        ;
    }


}
