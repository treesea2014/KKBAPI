package org.gxb.server.api.restassured.user.reset;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/21.
 * 移动端重置密码
 */
public class ResetPasswordTeacher {
    Logger logger = LoggerFactory.getLogger(ResetPasswordTeacher.class);
    String userId;

    @Test(description = "用户不存在",priority = 1)
    public void testWithEmptyMobile(){
        userId = "-1";
        Response response = TestConfig.getOrDeleteExecu("get","user/"+userId+"/reset_password/teacher");
        response.then().log().all().assertThat()
                .statusCode(400);

    }
    @Test(description = "手机号不合法",priority = 2)
    public void testWithInvalidMobile(){
        userId = "541548";
        Response response = TestConfig.getOrDeleteExecu("get","user/"+userId+"/reset_password/teacher");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status",Matchers.equalTo(true))
        ;


    }



}
