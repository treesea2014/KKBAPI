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
 */
public class CourseGetTest {

    static Integer Course_id=0;
    String access_token=TestConfig.getTokenbyUserID();
    static Integer intro_id=0;

    @Test(priority=1)
    public void testGetCourseWithCorrectToken() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject_sub = new JSONObject();
        JSONArray jsonArray = new JSONArray();




        jsonObject_sub.put("id", 76);
        jsonArray.add(jsonObject_sub);
        jsonObject_sub.put("id", 78);
        jsonArray.add(jsonObject_sub);

        jsonObject.put("name", "API自动化测试课程GET");
        jsonObject.put("intro", "第八章 API自动化测试GET");
        jsonObject.put("desc", "本课程介绍了阿里云产品基础架构、ECS 产品概念和功能、ECS运维管理和API编程接口。GET");
        jsonObject.put("instructorList", jsonArray);

        Response response = TestConfig.postOrPutExecu("post", "/courses?access_token=" + access_token, jsonObject);
        response.then().
                log().all().
                assertThat().statusCode(200).body("status", equalTo(true)).
                body("message", equalTo("success"));

        String body = response.body().asString();

        Course_id = JsonPath.with(body).get("data");
        if (Course_id == 0) throw new AssertionError();

        response = TestConfig.getOrDeleteExecu("get", "courses?access_token=" + access_token );
        response.then().
                log().all().
                assertThat().statusCode(200).body("status", equalTo(false)).
                body("message", equalTo("list course error"));

    }

        @Test(priority = 2)
        public void testGetByIDCourseWithCorrectToken () {

            Response response = TestConfig.getOrDeleteExecu("get", "/courses/" + Course_id + "?access_token="+access_token);
            response.then().
                    log().all().
                    assertThat().statusCode(200).body("status", equalTo(true)).
                    body("message", equalTo("success")).
                    body(containsString("API自动化测试课程GET"));

        }


        @Test(priority = 3)
        public void testGetCourseWithoutToken () {

            Response response = TestConfig.getOrDeleteExecu("get", "/courses?access_token=");
            response.then().
                    assertThat().statusCode(400).body("status", equalTo(false)).
                    body("message", equalTo("token不能为空"));
        }

            @Test(priority = 4)
            public void testGetByIDCourseWithoutToken() {


                Response response = TestConfig.getOrDeleteExecu("get", "/courses/" + Course_id + "?access_token=");
                response.then().
                        assertThat().statusCode(400).body("status", equalTo(false)).
                        body("message", equalTo("token不能为空"));

            }

            @Test(priority = 5)
            public void testGetCourseWithIncorrectToken () {

                Response response = TestConfig.getOrDeleteExecu("get", "/courses?access_token=f548b508f351231c6f6c8efa2cd44531");
                response.then().
                        assertThat().statusCode(401).body("status", equalTo(false)).
                        body("message", equalTo("无效的access_token"));
            }

            @Test(priority = 6)
            public void testGetByIDCourseWithIncorrectToken () {
                Response response = TestConfig.getOrDeleteExecu("get", "/courses/" + Course_id + "?access_token=f548b508f351231c6f6c8efa2cd44531");
                response.then().
                        assertThat().statusCode(401).body("status", equalTo(false)).
                        body("message", equalTo("无效的access_token"));


            }

            @Test(priority = 7)
            public void testGetByPagesCourse () {
                Response response = TestConfig.getOrDeleteExecu("get", "courses?access_token=" + access_token + "&curPage=1&pageSize=6");
                response.then().
                        log().all().
                        assertThat().statusCode(200).body("status", equalTo(true)).
                        body("message", equalTo("success")).
                        body(containsString("startRow")).
                        body(containsString("endRow"));
            }


    }