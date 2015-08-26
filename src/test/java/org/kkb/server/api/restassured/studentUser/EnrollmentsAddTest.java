package org.kkb.server.api.restassured.studentUser;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;


/**
 * 新增课程
 */
public class EnrollmentsAddTest {
    public static int enrollmentId;
    //缺少token
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id","1234");
        jsonObject.put("class_id","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/enrollments?access_token=null");
        response.then().assertThat().statusCode(400);

    }

    //token不正确
    public void testWithErrorToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id","1234");
        jsonObject.put("class_id","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/enrollments?access_token=asdfasd3rsdf");
        response.then().assertThat().statusCode(401);
    }

    //token权限不够
    public void testWithInvalidToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id","1234");
        jsonObject.put("class_id","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/enrollments?access_token=asdfasd3rsdf");
        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("没有权限"));
    }

    //缺少userId参数
    public void testWithNoUserId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("class_id","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/enrollments?access_token=asdfasd3rsdf");
        response.then().assertThat().statusCode(400);
    }

    //缺少ClassId
    public void testWithNoClassId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/enrollments?access_token=asdfasd3rsdf");
        response.then().assertThat().statusCode(400);
    }

    //classId不属于这个租户
    public void testWithErrorClassId(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id","1234");
        jsonObject.put("class_id","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/enrollments?access_token=asdfasd3rsdf");
        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("该租户没有这个学校"));
    }

    //添加成功
    public void testSuc(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_id","1234");
        jsonObject.put("class_id","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        requestSpecification .contentType(ContentType.JSON).body(jsonObject);

        Response response=requestSpecification.when()
                .post("/enrollments?access_token=asdfasd3rsdf");
        response.then().assertThat().statusCode(200).
                body("message", Matchers.equalTo("success"));

        //通过数据库，获取enrollmentId
        enrollmentId=0;

   }
}
