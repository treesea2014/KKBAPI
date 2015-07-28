package org.kkb.server.api.restassured.studentUser;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

/**
 *修改
 */
@Test
public class StudentUserUpdateTest {
    public static String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=1");
    //参数--token缺失
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("mobile","18888221212");

        String  id=String.valueOf(StudentUserAddTest.userId);

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .put("/student_users/" + id);
        response.then().assertThat().statusCode(400);
    }
    //参数--缺失id
    public void testWithNoId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("mobile","18888221212");

        String  id=String.valueOf(StudentUserAddTest.userId);

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .put("/student_users?access_token="+token);
        response.then().assertThat().statusCode(400);
    }

    //参数--token没有权限
    public void testWithInvalidToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("mobile","18888221212");

        String  id=String.valueOf(StudentUserAddTest.userId);

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .put("/student_users/" + id + "access_token=5678lkjhgf");
        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("没有权限"));
    }

    //启用--选择正在使用的用户
    public void testWithRestart(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email","835721919@qq.com");
        jsonObject.put("mobile","18888221212");
        jsonObject.put("status","restart");

        String  id=String.valueOf(StudentUserAddTest.userId);

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .put("/student_users/"+id+"access_token=??????????????????????????????");
        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("该用户处于启用状态"));
    }
    //停用--选择正在运行的用户
    public void testWithStop(){

    }
}
