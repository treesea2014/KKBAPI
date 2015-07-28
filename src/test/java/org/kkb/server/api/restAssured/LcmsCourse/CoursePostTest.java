package org.kkb.server.api.restassured.LcmsCourse;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by www on 2015/7/16.
 * 添加课程
 */

public class CoursePostTest {

    static Integer Course_id=0;
    String access_token=TestConfig.getTokenbyUserID();
    static Integer intro_id1=0;
    static Integer intro_id2=0;


    @Test(priority=1)
    public void testPostIntro(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject.put("name", "API自动测试讲师1");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("desc", "");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response response = TestConfig.postOrPutExecu("post", "tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                //log().all().
                assertThat().statusCode(200).body("message", equalTo("success"));

        String body=response.body().asString();

        intro_id1= JsonPath.with(body).get("data");
        if (intro_id1==0 ) throw new AssertionError();

        jsonObject.put("name", "API自动测试讲师2");
        jsonObject.put("title", "北京工业大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京工业大学经济管理学院讲师、副教授，北工大EMBA教育中心常务副主任、北工大MBA教育中心主任、北工大中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("desc", "");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        response = TestConfig.postOrPutExecu("post", "tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                //log().all().
                assertThat().statusCode(200).body("message", equalTo("success"));

        body=response.body().asString();

        intro_id2= JsonPath.with(body).get("data");
        if (intro_id2==0 ) throw new AssertionError();


    }

    //新增课程--无Token
    @Test(priority=2)
    public void testPostCourseWithoutToken(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","第八章 API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=", jsonObject);
        response.then().
                //log().all().
                        assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("token不能为空"));

    }

    //添加课程失败--错误的Token
    @Test(priority=3)
    public void testPostCourseWithIncorrectToken(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","第八章 API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=f548b508f351231c6f6c8efa2cd44531", jsonObject);
        response.then().
                //log().all().
                        assertThat().statusCode(401).body("status", equalTo(false)).
                body("message", equalTo("无效的access_token"));

    }

    //成功添加课程
    @Test(priority=4)
    public void testPostCourseWithCorrectToken(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","第八章 API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=" +access_token, jsonObject);
        response.then().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success"));

        String body=response.body().asString();

        Course_id= JsonPath.with(body).get("data");
        if (Course_id==0 ) throw new AssertionError();

    }

    //添加课程必填项--课程名称
    @Test(priority=5)
    public void testPostCourseWithoutName(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token="+access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程名称不能为空"));

    }

    //添加课程必填项--课程简介
    @Test(priority=6)
    public void testPostCourseWithoutIntro(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=" +access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程简介不能为空"));

    }

    //添加课程必填项--课程介绍
    @Test(priority=7)
    public void testPostCourseWithoutDesc(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=" +access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程介绍不能为空"));

    }

    //添加课程必填项--讲师
    @Test(priority=8)
    public void testPostCourseWithoutInstructorList(){

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

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=" +access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("课程讲师不能为空"));

    }

    //添加课程--讲师ID不存在
    @Test(priority=9)
    public void testPostCourseWithIncorrectInstructorID(){

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

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=" +access_token, jsonObject);
        response.then().
                log().all().
                        assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success")).
                body("data", equalTo(-2));

    }

}
