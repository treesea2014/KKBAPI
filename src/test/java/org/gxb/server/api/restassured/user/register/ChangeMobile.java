package org.gxb.server.api.restassured.user.register;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by treesea on 16/1/21.
 */
public class ChangeMobile {
    Logger logger = LoggerFactory.getLogger(ChangeMobile.class);
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

            Response response = TestConfig.postOrPutExecu("post","/user/1272520/change_mobile" ,jo);
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

        Response response = TestConfig.postOrPutExecu("post","/user/1272520/change_mobile" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号码不合法"))
        ;

    }

    @Test(description = "验证码不正确",priority = 2)
    public void testErrorVCode(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818029");
        jo.put("encrypted_password","");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272520/change_mobile" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.code" , Matchers.equalTo("验证码不正确"))
                .body("errorInfo.encrypted_password" , Matchers.equalTo("密码不能为空"))
        ;
    }

    @Test(description = "新旧手机号码一致,修改失败",priority = 3)
    public void testNewSameOld(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818050");
        jo.put("encrypted_password","123456");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272520/change_mobile" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("新旧手机号码一致,修改失败"))
        ;

    }

    @Test(description = "密码错误",priority = 4)
    public void testErrPassword(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818051");
        jo.put("encrypted_password","654312");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272520/change_mobile" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.encrypted_password" , Matchers.equalTo("密码错误"))
        ;
    }

    @Test(description = "用户不存在",priority = 5)
    public void testErrUser(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818051");
        jo.put("encrypted_password","654312");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/-1/change_mobile" ,jo);
        response.then().log().all().assertThat()
                .statusCode(400)
                ;
    }
}
