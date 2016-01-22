package org.gxb.server.api.restassured.user.reset;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/22.
 * 发送重置密码图形验证码
 */
public class SendImageCode {

    String mobile;
    @Test(description = "正常",priority = 1)
    public void test(){
        String code;
        String token;
        mobile = "18500818020";
        //发送图形验证码
        Response response = TestConfig.getOrDeleteExecu("get","validate_code/reset_pwd_imageCode?key="+mobile+"&token");
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("code" , Matchers.notNullValue())
                .body("token" , Matchers.notNullValue())
       ;
        code = response.jsonPath().get("code");
        token = response.jsonPath().get("token");

        //检查图形验证码参数
        JSONObject jo  = new JSONObject();
        jo.put("key",mobile);
        jo.put("code",code);
        jo.put("token",token);
        //检查图形验证码
        response = TestConfig.postOrPutExecu("post","/user/check_reset_pwd_imageCode" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(true))
                .body("data.mobile" , Matchers.equalTo(mobile))
        ;


        //检查图形验证码参数
        JSONObject jo1  = new JSONObject();
        jo1.put("mobile",mobile);
        jo1.put("token",token);
        //检查图形验证码
        response = TestConfig.postOrPutExecu("post","validate_code/reset_pwd_mobileCode" ,jo1);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(true))
        ;
    }


}
