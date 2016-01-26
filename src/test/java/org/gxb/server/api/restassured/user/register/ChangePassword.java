package org.gxb.server.api.restassured.user.register;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by treesea on 16/1/21.
 */
public class ChangePassword {
    Logger logger = LoggerFactory.getLogger(ChangePassword.class);

    @Test(description = "正常" , priority = 1)
    public void test(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","111111");
        jo.put("newPassword","123456");
        jo.put("confirmPassword","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(true))
                .body("errorInfo" , Matchers.equalTo(new HashMap()));
        jo.clear();

        jo.put("oldPassword","123456");
        jo.put("newPassword","111111");
        jo.put("confirmPassword","111111");

        response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(true))
                .body("errorInfo" , Matchers.equalTo(new HashMap()))
        ;
    }

    @Test(description = "原密码不能为空" , priority = 2)
    public void testEmptyOldPwd(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","");
        jo.put("newPassword","123456");
        jo.put("confirmPassword","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.oldPassword" , Matchers.equalTo("原密码不能为空"));


    }

    @Test(description = "新密码不能为空" , priority = 3)
    public void testEmptyNewPwd(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","111111");
        jo.put("newPassword","");
        jo.put("confirmPassword","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.newPassword" , Matchers.equalTo("新密码不能为空"));
    }

    @Test(description = "确认密码不能为空" , priority = 4)
    public void testEmptyConfirmPwd(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","111111");
        jo.put("newPassword","123456");
        jo.put("confirmPassword","");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.confirmPassword" , Matchers.equalTo("确认密码不能为空"));
    }

    @Test(description = "新旧密码不能一致" , priority = 5)
    public void testNewSameWithOld(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","111111");
        jo.put("newPassword","111111");
        jo.put("confirmPassword","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.newPassword" , Matchers.equalTo("新旧密码不能一致"));
    }

    @Test(description = "确认密码不一致" , priority = 6 )
    public void testNewSameWithConfirm(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","123456");
        jo.put("newPassword","111111");
        jo.put("confirmPassword","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.confirmPassword" , Matchers.equalTo("确认密码不一致"));
    }

    @Test(description = "原密码不正确" , priority = 7)
    public void testErrOldPassword(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","123456");
        jo.put("newPassword","111111");
        jo.put("confirmPassword","111111");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.oldPassword" , Matchers.equalTo("原密码不正确"));
    }


    @Test(description = "新密码长度小于6" , priority = 8)
    public void testShortNewPwd(){
        JSONObject jo  = new JSONObject();
        jo.put("oldPassword","111111");
        jo.put("newPassword","1");
        jo.put("confirmPassword","1");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        logger.info("status",response.jsonPath().get("status").toString());
        //判断新密码长度小于6,是否修改成功
        if("true".equals(response.jsonPath().get("status").toString())){
            //将密码改回111111
            jo.clear();
            jo.put("oldPassword","1");
            jo.put("newPassword","111111");
            jo.put("confirmPassword","111111");

            response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);

            response.then().log().all().assertThat()
                    .statusCode(200)
                    .body("status" , Matchers.equalTo(true))
                    .body("errorInfo" , Matchers.equalTo(new HashMap()))
            ;
            Assert.fail("判断新密码长度小于6,居然可以修改成功?!");
        }
    }

    @Test(description = "新密码长度大于16" , priority = 9)
    public void testLongNewPwd(){
        JSONObject jo  = new JSONObject();
        boolean success = true;
        jo.put("oldPassword","111111");
        jo.put("newPassword","11111111111111111");
        jo.put("confirmPassword","11111111111111111");

        Response response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);
        //判断新密码长度小于6,是否修改成功
        if("true".equals(response.jsonPath().get("status").toString())){
            //后续处理将密码改回
            jo.clear();
            jo.put("oldPassword","11111111111111111");
            jo.put("newPassword","111111");
            jo.put("confirmPassword","111111");

            response = TestConfig.postOrPutExecu("post","/user/1272515/password" ,jo);

            response.then().log().all().assertThat()
                    .statusCode(200)
                    .body("status" , Matchers.equalTo(true))
                    .body("errorInfo" , Matchers.equalTo(new HashMap()))
            ;
            Assert.fail("判断新密码长度大于16,居然可以修改成功?!");

        }

    }
}
