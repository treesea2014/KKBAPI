package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/5.
 * 班次信息
 * http://localhost:8080/gxb-api/classes/1
 */

public class GetClassInfoFilter {
    String classeId ;
    private Logger logger = LoggerFactory.getLogger(GetClassInfoFilter.class);



    @Test(description = "正常",priority = 1)
    public void test01(){
        classeId = "2";
        Response response = TestConfig.getOrDeleteExecu("get", "classes?filter=useType:20,courseId:1,tenantId:1,status:40,tenantId:1,className:响应式Web设计");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body( "dataList.useType", Matchers.hasItem("20"))
                .body( "dataList.status", Matchers.hasItem("40"))
                .body( "dataList.tenantId", Matchers.hasItem(1))
                .body( "dataList.className", Matchers.hasItem("响应式Web设计"))        ;
    }

}
