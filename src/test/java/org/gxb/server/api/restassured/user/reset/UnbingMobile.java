package org.gxb.server.api.restassured.user.reset;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/25.
 * 教师解绑学生手机
 * http://localhost:8080/gxb-api/user/541548/unbingmobile/teacher
 */
public class UnbingMobile {
    String userId;
    @Test(description = "正常" , priority = 1)
    public void test(){
        userId = "541548";
        Response response = TestConfig.getOrDeleteExecu("get","/user/"+userId+"/unbingmobile/teacher");
        response.then().assertThat()
                .statusCode(200)
                .body("status", Matchers.equalTo(true))
        ;
    }

    @Test(description = "用户不存在" , priority = 1)
    public void test01(){
        userId = "0";
        Response response = TestConfig.getOrDeleteExecu("get","/user/"+userId+"/unbingmobile/teacher");
        response.then().assertThat()
                .statusCode(200)
                .body("status", Matchers.equalTo(true))
        ;
    }
}
