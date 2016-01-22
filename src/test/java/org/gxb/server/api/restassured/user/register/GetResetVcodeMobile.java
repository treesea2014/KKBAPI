package org.gxb.server.api.restassured.user.register;

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
 * 获取移动端重置密码时验证码
 */
public class GetResetVcodeMobile {
    Logger logger = LoggerFactory.getLogger(GetResetVcodeMobile.class);
    String mobile;
    OperationTable op;


    @Test(description = "正常",priority = 1)
    public void test(){

        mobile = "18500818020";
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/reset_pwd_mobileCode_app?mobile="+mobile);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(true))
        ;
    }

    @Test(description = "手机号码不合法",priority = 2)
    public void testWithEmptyMobile(){

        mobile = "";
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/reset_pwd_mobileCode_app?mobile="+mobile);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号码不合法"))
        ;
    }
    @Test(description = "手机号码未注册",priority = 3)
    public void testWithNotExistMobile(){

        mobile = "15101179554";
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/reset_pwd_mobileCode_app?mobile="+mobile);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号码未注册"))
        ;
    }

}
