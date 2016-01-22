package org.gxb.server.api.restassured.user.register;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by treesea on 16/1/21.
 * 获取手机注册验证码
 * http://192.168.30.33:8080/gxb-api/validate_code/mobile_register?mobile=18638097289
 */
public class GetValidateCodeMobile {
    String mobile;
    OperationTable op;

    @BeforeClass
    public void init(){
        op = new OperationTable();

    }

    @Test(description = "正常" ,priority = 1)
    public void test(){
        op.delRegisteredMobile("18500818020");
        mobile = "18500818020";
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/mobile_register?mobile=" + mobile);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(true))
                .body("errorInfo" , Matchers.equalTo(new HashMap()))
        ;

    }
    @Test(description = "手机号码不合法",priority = 2)
    public void testWithInvalidMobile(){
        mobile = "1850081802x";
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/mobile_register?mobile=" + mobile);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号码不合法"))
        ;

    }
    @Test(description = "手机已被注册",priority = 3)
    public void testAlreadyExist(){
        mobile = "18500818023";
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/mobile_register?mobile=" + mobile);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机已被注册"))
        ;

    }


}
