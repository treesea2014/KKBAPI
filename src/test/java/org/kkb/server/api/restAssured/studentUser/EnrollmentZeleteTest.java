package org.kkb.server.api.restAssured.studentUser;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;

/**
 * 删除课程
 */
public class EnrollmentZeleteTest {
    //参数---缺少token
    public void testWithNoToken(){
        String  id=String.valueOf(EnrollmentsAddTest.enrollmentId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/enrollments/" + id );
        response.then().
                assertThat().statusCode(400);
    }
    //token不正确
    public void testWithErrorToken(){
        String  id=String.valueOf(EnrollmentsAddTest.enrollmentId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/enrollments/" + id +"/access_token=678ojffghjkl");
        response.then().
                assertThat().statusCode(401);
    }

    //token 没有权限
    public void testWithInvalidToken(){
        String  id=String.valueOf(EnrollmentsAddTest.enrollmentId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/enrollments/" + id +"/access_token=678ojffghjkl");
        response.then().
                assertThat().statusCode(200).body("message",Matchers.equalTo("没有相应权限"));
    }

    //id不存在
    public void testWithErrorId(){
        String  id=String.valueOf(EnrollmentsAddTest.enrollmentId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/enrollments/0/access_token=678ojffghjkl");
        response.then().
                assertThat().statusCode(200).body("message",Matchers.equalTo("id不存在"));
    }
    //删除成功
    public void testSuc(){
        String  id=String.valueOf(EnrollmentsAddTest.enrollmentId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/enrollments/" + id +"/access_token=678ojffghjkl");
        response.then().
                assertThat().statusCode(200).body("message",Matchers.equalTo("success"));
    }


}
