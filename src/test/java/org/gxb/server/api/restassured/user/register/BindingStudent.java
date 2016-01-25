package org.gxb.server.api.restassured.user.register;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/22.
 */
public class BindingStudent {

    @Test(description = "输入为空",priority = 1)
    public void test01(){
        JSONObject jo  = new JSONObject();
        Response response = TestConfig.postOrPutExecu("post","/user/3/binding" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.no" , Matchers.equalTo("输入的学号有误"))
                .body("errorInfo.name" , Matchers.equalTo("输入的姓名有误"))
        ;
    }

    @Test(description = "学校尚未导入您的学生信息",priority = 2)
    public void test02(){
        JSONObject jo  = new JSONObject();
        jo.put("no","32x");
        jo.put("tenantId","32");
        jo.put("name","32");

        Response response = TestConfig.postOrPutExecu("post","/user/3/binding" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.no" , Matchers.equalTo("学校尚未导入您的学生信息"));
    }

    @Test(description = "该身份已被其他用户认证，如有疑问请联系客服。",priority = 3)
    public void test03(){
        JSONObject jo  = new JSONObject();
        jo.put("no","3");
        jo.put("tenantId","1");
        jo.put("name","张山");

        Response response = TestConfig.postOrPutExecu("post","/user/3/binding" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.no" , Matchers.equalTo("该身份已被其他用户认证，如有疑问请联系客服。"));
    }

    @Test(description = "用户不存在",priority = 3)
    public void test04(){
        JSONObject jo  = new JSONObject();
        jo.put("no","11111111");
        jo.put("tenantId","111");
        jo.put("name","梁美英");

        Response response = TestConfig.postOrPutExecu("post","/user/0/binding" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.no" , Matchers.equalTo("用户不存在"));
    }
}
