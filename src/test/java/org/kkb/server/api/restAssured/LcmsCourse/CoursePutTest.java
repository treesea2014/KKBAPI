package org.kkb.server.api.restassured.LcmsCourse;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by www on 2015/7/17.
 * 修改课程信息
 */
public class CoursePutTest {

    static Integer Course_id=0;

    @Test(priority=1)
    public void testPostCourseWithCorrectToken() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name", "API自动化测试课程PUT");
        jsonObject.put("intro", "第八章 API自动化测试PUT");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。PUT");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().log().all().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success"));

        String body = response.body().asString();

        Course_id = JsonPath.with(body).get("data");
        if (Course_id == 0) throw new AssertionError();
    }

    @Test(priority=2)
    public void testPutCourseWithCorrectToken() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name", "API自动化测试课程PUTPUT");
        jsonObject.put("intro", "第八章 API自动化测试PUTPUT");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。PUTPUT");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/" + Course_id + "?access_token=f548b508f351231c6f6c8efa2cd4453b",  jsonObject);
        response.then().log().all().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success"));

    }

    @Test(priority=3)
    public void testGetCourseWithToken() {

        Response response = TestConfig.getOrDeleteExecu("get", "/courses/" + Course_id + "?access_token=f548b508f351231c6f6c8efa2cd4453b");
        response.then().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success")).and().
                body(containsString("API自动化测试课程PUTPUT"));

    }

    @Test(priority=4)
    public void testPutCourseWithoutToken() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name", "API自动化测试课程PUTPUT");
        jsonObject.put("intro", "第八章 API自动化测试PUTPUT");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。PUTPUT");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/" + Course_id + "?access_token=", jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("token不能为空"));
    }

    @Test(priority=5)
    public void testPutCourseWithIncorrectToken() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name", "API自动化测试课程PUTPUT");
        jsonObject.put("intro", "第八章 API自动化测试PUTPUT");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。PUTPUT");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/" + Course_id + "?access_token=f548b508f351231c6f6c8efa2cd44531", jsonObject);
        response.then().
                assertThat().statusCode(401).body("status", equalTo(false)).
                body("message", equalTo("无效的access_token"));
    }

    //修改课程必填项--课程名称
    @Test(priority=6)
    public void testPutCourseWithoutName(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程名称不能为空"));

    }

    //修改课程必填项--课程简介
    @Test(priority=7)
    public void testPutCourseWithoutIntro(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程简介不能为空"));

    }

    //添加课程必填项--课程介绍
    @Test(priority=8)
    public void testPutCourseWithoutDesc(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程介绍不能为空"));

    }

    //添加课程必填项--讲师
    @Test(priority=9)
    public void testPutCourseWithoutInstructorList(){

        JSONObject jsonObject=new JSONObject();
        //JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        //jsonObject_sub.put("id", 76);
        //jsonArray.add(jsonObject_sub);
        //jsonObject_sub.put("id", 78);
        //jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程讲师不能为空"));

    }

    //添加课程--讲师ID不存在
    @Test(priority=10)
    public void testPutCourseWithIncorrectInstructorID(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", 0);
        jsonArray.add(jsonObject_sub);
        //jsonObject_sub.put("id", 78);
        //jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token=f548b508f351231c6f6c8efa2cd4453b", jsonObject);
        response.then().
                //log().all().
                        assertThat().statusCode(100).body("status", equalTo(true)).
                body("message", equalTo("success")).
                body("data", equalTo(-1));

    }

}

