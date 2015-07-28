package org.kkb.server.api.restassured.studentUser;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static com.jayway.restassured.path.json.JsonPath.with;


/**
 * 删除新添加的用户
 */
@Test
public class StudentUserZeleteTest {
    public static String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=1");

    //参数缺失---token不存在
    public void testWithNoToken(){
        String  id=String.valueOf(StudentUserAddTest.userId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/student_users/" + id);
        response.then().
                assertThat().statusCode(400);
    }

    //参数缺失 ---token不存在
    public void testWithErrorToken(){
        String  id=String.valueOf(StudentUserAddTest.userId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/student_users/" + id+"?access_token=456789rtyuio");
        response.then().
                assertThat().statusCode(401);
    }

    //token 没有权限
    private  void testWithInvalidToken(){
        String  id=String.valueOf(StudentUserAddTest.userId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/student_users/" + id+"/access_token=456789rtyuio");
        response.then().
                assertThat().statusCode(200).body("message", Matchers.equalTo("没有权限"));
    }

    //删除成功
    public void testSuc(){
        Response response=delete();
        String message=with(response.body().asString()).getString("message");

        if(message.contains("停用")){
            disable();
            response=delete();
            response.then().
                    assertThat().statusCode(200).body("message", Matchers.equalTo("success"));
        }else{
            response.then().
                    assertThat().statusCode(200).body("message", Matchers.equalTo("success"));
        }
    }
    private static Response delete(){
        String  id=String.valueOf(StudentUserAddTest.userId);
        Response response = TestConfig.getOrDeleteExecu("delete", "/student_users/"+id+"?access_token="+token);
        response.then().
                assertThat().statusCode(200);
        return response;
    }

    public   void disable(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("no","swang12345");
        jsonObject.put("name","swang");
        jsonObject.put("user_status","disable");

        String  id=String.valueOf(StudentUserAddTest.userId);

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .put("/student_users/"+id+"?access_token="+token);

        response.then().log().all().assertThat().statusCode(200).body("message", Matchers.equalTo("success"));
    }

}
