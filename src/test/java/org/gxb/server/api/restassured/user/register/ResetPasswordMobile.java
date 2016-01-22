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
 * 移动端重置密码
 */
public class ResetPasswordMobile {
    Logger logger = LoggerFactory.getLogger(ResetPasswordMobile.class);
    String mobile;
    OperationTable op;

    @BeforeClass
    public void init(){
        op = new OperationTable();

    }
    @Test(description = "手机号不能为空",priority = 1)
    public void testWithEmptyMobile(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","");
        jo.put("password","123456");
        jo.put("code","123456");

        Response response = TestConfig.postOrPutExecu("post","user/reset_password_app" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号不能为空"))
        ;

    }
    @Test(description = "手机号不合法",priority = 2)
    public void testWithInvalidMobile(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","x");
        jo.put("password","123456");
        jo.put("code","123456");
        Response response = TestConfig.postOrPutExecu("post","user/reset_password_app" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号不合法"))
        ;

    }

    @Test(description = "密码不能为空",priority = 3)
    public void testWithEmptyPwd(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818020");
        jo.put("password","");
        jo.put("code","123456");
        Response response = TestConfig.postOrPutExecu("post","user/reset_password_app" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.password" , Matchers.equalTo("密码不能为空"))
        ;
    }

    @Test(description = "无效验证码",priority = 4)
    public void testWithInvalidVcode(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818028");
        jo.put("password","654312");
        jo.put("code","123456");
        Response response = TestConfig.postOrPutExecu("post","user/reset_password_app" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.code" , Matchers.equalTo("无效验证码"))
        ;
    }
    @Test(description = "验证码不正确",priority = 5)
    public void testWithErrdVcode(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18500818020");
        jo.put("password","654312");
        jo.put("code","12345x");
        //获取重置验证码
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/reset_pwd_mobileCode_app?mobile=18500818020");
        response.then().statusCode(200);
        //调重置接口
        response = TestConfig.postOrPutExecu("post","user/reset_password_app" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.code" , Matchers.equalTo("验证码不正确"))
        ;
    }
    @Test(description = "用户不存在",priority = 6)
    public void testErrorVCode(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","18600818020");
        jo.put("password","654312");
        jo.put("password","654312");

        Response response = TestConfig.postOrPutExecu("post","user/reset_password_app" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("用户不存在"))

        ;
    }


}
