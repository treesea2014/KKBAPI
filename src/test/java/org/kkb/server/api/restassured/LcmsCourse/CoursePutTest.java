package org.kkb.server.api.restassured.LcmsCourse;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by www on 2015/7/17.
 * 修改课程信息
 */
public class CoursePutTest {

    static Integer Course_id=0;
    static Integer intro_id1=0;
    static Integer intro_id2=0;
    String access_token=TestConfig.getTokenbyUserID();

   // 创建一个新的课程进行修改，返回创建成功后的课程id
    @BeforeClass
    public void tesCreateCourse() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //创建一个讲师
        jsonObject.put("name", "API自动测试讲师1");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任");
        jsonObject.put("desc", "描述");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "sina_weibo");
        jsonObject.put("tag", "tag");
        jsonObject.put("tx_weibo", "tx_weibo");

        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                log().all().
                assertThat().statusCode(200).body("message", equalTo("success"));
        String body=response.body().asString();
        intro_id1= JsonPath.with(body).get("data");
        if (intro_id1==0 ) throw new AssertionError("讲师1添加失败！");
        //再创建一个讲师
        jsonObject.put("name", "API自动测试讲师2");
        jsonObject.put("title", "北京工业大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京工业大学经济管理学院讲师、副教授，北工大EMBA教育中心常务副主任、北工大MBA教育中心主任、北工大中澳国际MBA项目主任经管学院学生职业发展中心主任");
        jsonObject.put("desc", "");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        response = TestConfig.postOrPutExecu("post", "/tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
              //  log().all().
                assertThat().statusCode(200).body("message", equalTo("success"));

        body=response.body().asString();

        intro_id2= JsonPath.with(body).get("data");
        if (intro_id2==0 ) throw new AssertionError("讲师添2加失败！");
        
        //创建课程
        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);
        jsonObject.clear();
        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","第八章 API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        response = TestConfig.postOrPutExecu("post", "/courses?access_token="+access_token, jsonObject);
        response.then().
        	//log().all().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success"));

         body = response.body().asString();
         //返回创建课程的id
        Course_id = JsonPath.with(body).get("data");
        if (Course_id == 0) throw new AssertionError();
    }
    //添加课程-正常
    @Test(priority=2)
    public void testPutCourseWithCorrectToken() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name", "API自动化测试课程PUTPUT");
        jsonObject.put("intro", "第八章 API自动化测试PUTPUT");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。PUTPUT");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/" + Course_id + "?access_token="+access_token,  jsonObject);
        response.then().log().all().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success"));

    }
    //检查修改是否成功
    @Test(priority=3)
    public void testGetCourseWithToken() {

        Response response = TestConfig.getOrDeleteExecu("get", "/courses/" + Course_id + "?access_token="+access_token);
        response.then().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success")).
                body("data.id", equalTo(Course_id)).
                body(containsString("API自动化测试课程PUTPUT"));

    }
    //token不能为空
    @Test(priority=4)
    public void testPutCourseWithoutToken() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
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

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
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

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token="+access_token, jsonObject);
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

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token="+access_token, jsonObject);
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

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id2);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token="+access_token, jsonObject);
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

        //jsonObject_sub.put("id", intro_id1);
        //jsonArray.add(jsonObject_sub);
        //jsonObject_sub.put("id", intro_id2);
        //jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token="+access_token, jsonObject);
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
        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token="+access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("讲师ID不存在")).
                body("data", equalTo(-1));

    }
    //修改课程--重复的讲师
    @Test(priority=11)
    public void testPutCourseWithRepeatInstructors(){

        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject_sub=new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", intro_id1);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name","API自动化测试课程");
        jsonObject.put("intro","API自动化测试");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("put", "/courses/"+ Course_id +"?access_token="+access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).body("status", equalTo(false)).
                body("message", equalTo("重复的讲师")).
                body("data", equalTo(-1));
;

    }
}

