package org.gxb.server.api.restassured.course.item;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by treesea on 16/1/5.
 * 查询课程信息接口
 */
public class DelItem {
    private Logger logger = LoggerFactory.getLogger(CreateItem.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer unitId;
    Integer itemId;


    @BeforeClass(description = "课程结构course-unit－item－chapter")
    public void init() {
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

        jo.put("name", "课程名称");
        jo.put("intro", "课程简介");
        jo.put("courseInfo", JSONObject.fromObject(courseInfo));
        jo.put("tenantId", 1);
        jo.put("categoryList", categoryListArray);
        jo.put("instructorList", instructorListArray);

        Response response = TestConfig.postOrPutExecu("post",
                "course?loginUserId=123456&tenantId=111", jo);
        //获取课程Id
        Integer courseId = response.jsonPath().get("courseId");


        JSONObject jo1 = new JSONObject();
        jo1.put("title", "API测试第一章");
        jo1.put("position", 999);
        response = TestConfig.postOrPutExecu("post",
                "course/" + courseId + "/unit?loginUserId=5", jo1);
        //获取章节Id
        unitId = response.jsonPath().get("unitId");

        JSONObject jo2 = new JSONObject();
        jo2.put("title", "API测试第一节");
        jo2.put("position", 999);
        response = TestConfig.postOrPutExecu("post",
                "course/unit/"+unitId+"/item?loginUserId=123456", jo2);
        //获取itemId
        itemId = response.jsonPath().get("itemId");
    }

    @Test(description = "正常删除",priority = 1)
    public void test(){

        Response response =  TestConfig.getOrDeleteExecu("del","/course/item/"+itemId+"?loginUserId=123456");
        response.then().log().all()
                .assertThat().statusCode(200)
                .body(Matchers.equalTo("true"));
    }
    @Test(description = "itemId不存在",priority = 2)
    public void testWithItemIdNotExist(){

        String response = HttpRequest.sendHttpDelete(Url+"/course/item/-7?loginUserId=123456");
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"item不存在");
        Assert.assertEquals(result.get("type"),"ServiceException");
    }


}
