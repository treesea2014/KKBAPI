package org.gxb.server.api.restassured.course.course;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author shdeng@gaoxiaobang.com
 * @version 1.0.0
 * @date 2015.11.06
 * @decription 功能描述：创建课程,包含基本信息 课程与类别关系 课程与讲师关系 资源库默认目录--基本信息修改接口
 * 涉及表：course course_info course_category course_instructor question_node resource_node
 * http://192.168.30.33:8080/gxb-api/course/2?loginUserId=123456&tenantId=1
 */
public class ModifyBasicInfo {
    Integer courseId ;
    @BeforeClass(description = "创建基本信息,获取courseId")
    public void CreateBasicInfo(){
            JSONObject jo = new JSONObject();
            HashMap<String, String> courseInfo = new HashMap<String, String>();
            courseInfo.put("description", "新建详细介绍");

            JSONArray categoryListArray = new JSONArray();
            HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
            categoryList.put("categoryId", 1);
            categoryListArray.add(JSONObject.fromObject(categoryList));

            JSONArray instructorListArray = new JSONArray();
            HashMap<String, Object> instructorList = new HashMap<String, Object>();
            instructorList.put("instructorId", 1);
            instructorList.put("name", "新建api测试");
            instructorListArray.add(JSONObject.fromObject(instructorList));

            jo.put("name", "新建课程名称");
            jo.put("intro", "新建课程简介");
            jo.put("courseInfo", JSONObject.fromObject(courseInfo));
            jo.put("tenantId", 1);
            jo.put("categoryList", categoryListArray);
            jo.put("instructorList", instructorListArray);

            Response response = TestConfig.postOrPutExecu("post",
                    "course?loginUserId=123456&tenantId=1", jo);
            courseId = response.getBody().jsonPath().get("courseId");
        }


    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍修改");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试修改");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "课程名称修改");
        jo.put("intro", "课程简介修改");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body("name", equalTo("课程名称修改"))
                .body("intro", equalTo("课程简介修改"))
                .body("tenantId", equalTo(1))
                .body("categoryList", Matchers.hasItem(categoryList));

    }

    @Test(description = "name 课程名称为空的时候", priority = 2)
    public void testWithInvaildName01() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍啊");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "");
        jo.put("intro", "课程简介");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("namelength must be between 1 and 32,"))
                .body("type", equalTo("MethodArgumentNotValidException"));

    }

    @Test(description = "name 课程名称为33超长的时候", priority = 3)
    public void testWithInvaildName02() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍啊");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "课程名称课程名称课程名称课程名称课程名称课程名称课程名称课程名称1");
        jo.put("intro", "课程简介");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("namelength must be between 1 and 32,"))
                .body("type", equalTo("MethodArgumentNotValidException"));

    }

    @Test(description = "intro 课程简介为空的时候", priority = 4)
    public void testWithInvaildIntro01() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍啊");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("intro课程介绍不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "intro 课程简介64超长的时候", priority = 5)
    public void testWithInvaildIntro02() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍啊");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简介课程简12223");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("intro长度需要在1和64之间,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "description 课程介绍为空的时候", priority = 6)
    public void testWithInvaildCourseInfo01() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("description不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "description 课程介绍2000超长的时候", priority = 7)
    public void testWithInvaildCourseInfo02() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍详细介绍");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("description长度在1-2000,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "CourseInfo 课程介绍为空的时候", priority = 8)
    public void testWithInvaildCourseInfo03() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
        ;
    }

    @Test(description = "categoryList categoryId为空的时候", priority = 9)
    public void testWithInvaildCategory01() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        //categoryList.put("categoryId", );
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("categoryId不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "categoryList 为空的时候", priority = 10)
    public void testWithInvaildCategory02() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍");

        JSONArray categoryListArray = new JSONArray();
    /*	 HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
		 categoryList.put("categoryId", 1); 
		 categoryListArray.add(JSONObject.fromObject(categoryList));*/

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
        ;
    }

    @Test(description = "instructorList instructorId为空的时候", priority = 11)
    public void testWithInvaildInstructorList01() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        //instructorList.put("instructorId", 1);
        instructorList.put("name", "api测试");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("categoryId不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"));
    }

    @Test(description = "instructorList name 为空的时候", priority = 12)
    public void testWithInvaildInstructorList02() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
        HashMap<String, Object> instructorList = new HashMap<String, Object>();
        instructorList.put("instructorId", 1);
        instructorList.put("name", "");
        instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
        ;
    }

    @Test(description = "instructorList  为空的时候", priority = 13)
    public void testWithInvaildInstructorList03() {
        JSONObject jo = new JSONObject();
        HashMap<String, String> courseInfo = new HashMap<String, String>();
        courseInfo.put("description", "详细介绍");

        JSONArray categoryListArray = new JSONArray();
        HashMap<String, Integer> categoryList = new HashMap<String, Integer>();
        categoryList.put("categoryId", 1);
        categoryListArray.add(JSONObject.fromObject(categoryList));

        JSONArray instructorListArray = new JSONArray();
//		 HashMap<String, Object> instructorList = new HashMap<String, Object>();  
//		 instructorList.put("instructorId", 1); 
//		 instructorList.put("name", ""); 
//		 instructorListArray.add(JSONObject.fromObject(instructorList));

        jo.put("name", "API我的课程");
        jo.put("intro", "课程简介课");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("put",
                "course/"+courseId+"?loginUserId=123456&tenantId=1", jo);

        response.then().log().all().assertThat()
                .statusCode(400)
        ;
    }
}
