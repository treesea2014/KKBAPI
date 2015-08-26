package org.kkb.server.api.restassured.LcmsUnit;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * 添加、修改、查询和删除单元
 * qfan
 */

public class LcmsUnitTest {

     static Integer Unit_id=0;

    //新增单元
    @Test(priority=1)
    public void testPostUnitWithoutToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","691");
        jsonObject.put("title","第八章 API自动化测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "8");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("post", "/courses/691/units?access_token=", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", equalTo("token不能为空"));

    }

    @Test(priority=2)
    public void testPostUnitWithIncorrectToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","691");
        jsonObject.put("title","第八章 API自动化测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "8");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("post", "/courses/691/units?access_token=f548b508f351231c6f6c8efa2cd44531", jsonObject);
        response.then().
                assertThat().statusCode(401).body("status", equalTo(false)).
                body("message", equalTo("无效的access_token"));

    }

    @Test(priority=3)
    public void testPostUnitWithCorrectToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","691");
        jsonObject.put("title","第八章 API自动化测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "8");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("post", "/courses/691/units?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(200).body("status", equalTo(true));

        String body=response.body().asString();

        Unit_id= JsonPath.with(body).get("data");
               if (Unit_id==0 ) throw new AssertionError();

    }

    //修改单元
    @Test(priority=4)
    public void testPutUnitWithoutToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","691");
        jsonObject.put("title","第八章 API自动化测试+测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "9");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("put", "/units/" + Unit_id +"?access_token=", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", equalTo("token不能为空"));

    }

    @Test(priority=5)
    public void testPutUnitWithIncorrectToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","691");
        jsonObject.put("title","第八章 API自动化测试+测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "8");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("put", "/units/" + Unit_id +"?access_token=f548b508f351231c6f6c8efa2cd44531", jsonObject);
        response.then().
                assertThat().statusCode(401).body("status", equalTo(false)).
                body("message", equalTo("无效的access_token"));

    }

    @Test(priority=6)
    public void testPutUnitWithCorrectToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","691");
        jsonObject.put("title","第八章 API自动化测试-测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "8");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("put", "/units/" + Unit_id +"?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(200).body("status", equalTo(true)).body("data", equalTo(1));

    }

    //查询单元

    @Test(priority=7)
    public void testGetUnitWithoutToken() {
        Response response = TestConfig.getOrDeleteExecu("get", "/courses/691/units?access_token=");
        response.then().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("token不能为空"));
    }

    @Test(priority=8)
    public void testGetUnitWithIncorrectToken() {
        Response response = TestConfig.getOrDeleteExecu("get", "/courses/691/units?access_token=f548b508f351231c6f6c8efa2cd44531");
        response.then().
                assertThat().statusCode(401).
                body("status", equalTo(false)).
                body("message", equalTo("无效的access_token"));
    }

    @Test(priority=9)
    public void testGetUnitWithCorrectToken() {
        Response response = TestConfig.getOrDeleteExecu("get", "/courses/691/units?access_token=f548b508f351231c6f6c8efa2cd4453b");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("data", Matchers.notNullValue());
    }


    //删除单元

    @Test(priority=10)
    public void testDeleteUnitWithoutToken() {
        Response response = TestConfig.getOrDeleteExecu("delete", "/units/" + Unit_id +"?access_token=");
        response.then().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("token不能为空"));
    }

    @Test(priority=11)
    public void testDeleteUnitWithIncorrectToken() {
        Response response = TestConfig.getOrDeleteExecu("delete", "/units/" + Unit_id +"?access_token=f548b508f351231c6f6c8efa2cd44531");
        response.then().
                assertThat().statusCode(401).
                body("status", equalTo(false)).
                body("message", equalTo("无效的access_token"));
    }

    @Test(priority=12)
    public void testDeleteUnitWithCorrectToken() {
        Response response = TestConfig.getOrDeleteExecu("delete", "/units/" + Unit_id +"?access_token=f548b508f351231c6f6c8efa2cd4453b");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true));
    }

    //删除不存在的单元
    @Test(priority=13)
    public void testDeleteUnitWithIncorrectID() {
        Response response = TestConfig.getOrDeleteExecu("delete", "/units/0000?access_token=f548b508f351231c6f6c8efa2cd4453b");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true));
    }

    //更新不存在的单元
    @Test(priority=14)
    public void testPutUnitWithIncorrectId(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","691");
        jsonObject.put("title","第八章 API自动化测试-测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "8");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("put", "/units/0000?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(200).body("status", equalTo(true)).body("data", equalTo(0));

    }

    //查询不存在的单元，规则未定待修改
    @Test(priority=15)
    public void testGetUnitWithIncorrectId(){

        Response response = TestConfig.getOrDeleteExecu("get", "/courses/000/units?access_token=f548b508f351231c6f6c8efa2cd4453b");
        ValidatableResponse body;
        body = response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("data", Matchers.equalTo(new JSONArray()));
    }

    //给不存在的课程ID添加单元，规则未定待修改
    @Test(priority=16)
    public void testPostUnitIncorrectId(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("course_id","000");
        jsonObject.put("title","第八章 API自动化测试");
        jsonObject.put("level", "low");
        jsonObject.put("unlock_at", "1439628265");
        jsonObject.put("start_at", "");
        jsonObject.put("end_at", "");
        jsonObject.put("deleted_at", "");
        jsonObject.put("position", "8");
        jsonObject.put("status", "active");
        jsonObject.put("created_at", "1436949865");
        jsonObject.put("updated_at", "1436949865");
        jsonObject.put("parent_id", "");

        Response response = TestConfig.postOrPutExecu("post", "/courses/000/units?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(200).body("status", equalTo(true));
    }
}


