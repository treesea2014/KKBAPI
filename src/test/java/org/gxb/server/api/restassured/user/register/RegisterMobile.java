package org.gxb.server.api.restassured.user.register;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by treesea on 16/1/21.
 * 获取手机注册验证码
 * http://192.168.30.33:8080/gxb-api/validate_code/mobile_register?mobile=18638097289
 */
public class RegisterMobile {
    String mobile;
    OperationTable op;

    @BeforeClass
    public void init(){
        op = new OperationTable();

    }
/*
    正常无法拿到手机验证码 无法完成正常流程
        JSONObject jo  = new JSONObject();
        jo.put("mobile","1850081802x");
        jo.put("encrypted_password","123456");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/register" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号码不合法"));
                }
        */
    @Test(description = "手机号码不合法",priority = 1)
    public void testWithInvalidMobile(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","1850081802x");
        jo.put("encrypted_password","123456");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/register" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号码不合法"))
        ;

    }
    @Test(description = "手机已被注册",priority = 2)
    public void testAlreadyExist(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818023");
        jo.put("encrypted_password","123456");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/register" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机已被注册"))
        ;

    }
    @Test(description = "验证码不正确",priority = 3)
    public void testErrorVCode(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818029");
        jo.put("encrypted_password","123456");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/register" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.code" , Matchers.equalTo("验证码不正确"))
        ;

    }


}