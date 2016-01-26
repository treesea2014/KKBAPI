package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/5.
 * token获取信息
 */
public class GetToken {
    String token ;
    private Logger logger = LoggerFactory.getLogger(GetToken.class);



    @Test(description = "正常",priority = 1)
    public void test(){

        Response response = TestConfig.getOrDeleteExecu("get", "token/"+token+"?type=client");
        response.then().log().all().assertThat()
                .statusCode(200)
                ;
    }


}
