package org.kkb.server.api.restAssured.studentUser;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;

/**
 * Created by pc on 15-6-10.
 */
public class StudentUserRestartTest {
    //参数--token=null
    public void testWithTokenNull(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(new JSONObject());
        Response response=requestSpecification.when()
                .put("/student_users/ify3456765432/access_token=null");
        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("没有权限"));
    }

    //参数---token不存在
    public void  testWithErrorToken(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(new JSONObject());
        Response response=requestSpecification.when()
                .put("/student_users/ify3456765432/access_token=2345678iuytre");
        response.then().assertThat().statusCode(401);
    }

    //参数---token没有权限
    public void testWithInvalidToken(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(new JSONObject());
        Response response=requestSpecification.when()
                .put("/student_users/ify3456765432/access_token=2345678iuytre");
        response.then().assertThat().statusCode(200).body("message",Matchers.equalTo("没有相应的权限"));
    }

    //参数--id不存在
    public void testWithErrorId(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(new JSONObject());
        Response response=requestSpecification.when()
                .put("/student_users/0/access_token=2345678iuytre");
        response.then().assertThat().statusCode(200).body("message",Matchers.equalTo("id不存在"));
    }

    //正确重置
    public void testSuc(){
        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(new JSONObject());
        Response response=requestSpecification.when()
                .put("/student_users/0/access_token=2345678iuytre");
        response.then().assertThat().statusCode(200).body("message",Matchers.equalTo("success"));
    }


    //登录，获取验证


}
