package org.gxb.server.api.restassured.classes.page;

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
public class DelPage {
    private Logger logger = LoggerFactory.getLogger(CreatePage.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer courseId;
    Integer unitId;
    Integer itemId;
    Integer pageId;
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
                "course?loginUserId=123456&tenantId=1", jo);

        courseId = response.jsonPath().get("courseId");


        JSONObject jo1 = new JSONObject();
        jo1.put("title", "API测试第一章");
        jo1.put("position", 999);
        response = TestConfig.postOrPutExecu("post",
                "course/"+courseId+"/unit?loginUserId=5", jo1);
        unitId = response.jsonPath().get("unitId");

        JSONObject jo2 = new JSONObject();
        jo2.put("title", "第一节xxx");
        jo2.put("position", 999);
        response = TestConfig.postOrPutExecu("post",
                "course/unit/"+unitId+"/item?loginUserId=1234", jo2);
        itemId = response.jsonPath().getInt("itemId");

        HashMap<String, String> page = new HashMap<String, String>();
        page.put("title", "页面21");
        page.put("type", "Text");
        page.put("body", "页面21");

        JSONObject jo3 = new JSONObject();
        jo3.put("title", "我是page标题");
        jo3.put("position", 888);
        jo3.put("page",JSONObject.fromObject(page));
        logger.info("jo:{}",jo);
        response = TestConfig.postOrPutExecu("post",
                "course/item/"+itemId+"/page?loginUserId=123456&tenantId=1", jo3);
        pageId = response.jsonPath().get("page.pageId");
    }


    @Test(description = "正常删除",priority = 1)
    public void test(){

        Response response =  TestConfig.getOrDeleteExecu("del","course/page/"+pageId+"?loginUserId=123456&tenantId=111");
        response.then().log().all()
                .assertThat().statusCode(200)
                .body(Matchers.equalTo("true"));
    }

    @Test(description = "pageId",priority = 2)
    public void testWithItemIdNotExist(){

        String response = HttpRequest.sendHttpDelete(Url+"/course/page/-1?loginUserId=123456&tenantId=111");
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);

        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"页面不存在");
        Assert.assertEquals(result.get("type"),"ServiceException");
    }


}
