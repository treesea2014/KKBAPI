package org.kkb.server.api.restAssured.studentUser;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 * 查询单个用户
 */
@Test
public class StudentUserSelectTest {
    public static String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=1");

    //参数--缺失token
    public void testWithNoToken() {
        String id = String.valueOf(StudentUserAddTest.userId);
        Response response = TestConfig.getOrDeleteExecu("get", "/student_users/" + id);
        response.then().
                assertThat().statusCode(400);
    }

    //参数--缺失id
    private void testWithNoId(){
        String id = String.valueOf(StudentUserAddTest.userId);
        Response response = TestConfig.getOrDeleteExecu("get", "/student_users?access_token=" +token );
        response.then().
                assertThat().statusCode(400);
    }

    //参数---token没有权限
    private void testWithInvalidToken(){
        String id = String.valueOf(StudentUserAddTest.userId);
        Response response = TestConfig.getOrDeleteExecu("get", "/student_users/" + id+"?access_token=tyuioghjkliuyt");
        response.then().
                assertThat().statusCode(200).body("message", Matchers.equalTo("没有权限"));
    }

    //参数---token不正确
    public void testWithErrorToken(){
        String id = String.valueOf(StudentUserAddTest.userId);
        System.out.println(id);
        Response response = TestConfig.getOrDeleteExecu("get", "/student_users/" + id+"?access_token=tyuioghjkliuyt");
        response.then().
                assertThat().statusCode(401);
    }

    //正确查找
    public void testSuc(){
        String id = String.valueOf(StudentUserAddTest.userId);
        System.out.println(id);

        Response response = TestConfig.getOrDeleteExecu("get", "/student_users/" + id+"?access_token="+token);
        response.then().
                assertThat().statusCode(200).body("data.name",Matchers.equalTo("swang"));
    }

}
