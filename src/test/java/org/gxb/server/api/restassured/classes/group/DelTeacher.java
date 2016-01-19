package org.gxb.server.api.restassured.classes.group;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class DelTeacher {
    private Logger logger = LoggerFactory.getLogger(DelTeacher.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;

    Integer groupTeacherId;
    @BeforeClass(description = "获取groupTeacherId")
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("teacherId", 888);
        jo.put("groupId", 999);
        Response response = TestConfig.postOrPutExecu("post",
                "/class_group_teacher?loginUserId=111&tenantId=222&classId=333", jo);

       groupTeacherId = response.jsonPath().get("groupTeacherId");

    }
    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("deleteFlag", 1);
        Response response = TestConfig.getOrDeleteExecu("del",
                "class_group_teacher/"+groupTeacherId+"?loginUserId=1");

        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;
    }



    @Test(description = "class_group_teacher已删除", priority = 2)
    public void testErrGroupId() {
        String response = HttpRequest.sendHttpDelete(Url+"/class_group_teacher/29?loginUserId=1");
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);
        logger.info("response:{},",response.toString());
        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"老师导学班组关联不存在");
        Assert.assertEquals(result.get("type"),"ServiceException");

    }

    @Test(description = "class_group_teacher已删除", priority = 3)
    public void testErrclass_group_teacher() {
        String response = HttpRequest.sendHttpDelete(Url+"/class_group_teacher/-1?loginUserId=1");
        response =  response.substring(response.indexOf("&")+1,response.length());
        JSONObject result = JSONObject.fromObject(response);
        logger.info("response:{},",response.toString());
        Assert.assertEquals(result.get("code"),1000);
        Assert.assertEquals(result.get("message"),"老师导学班组关联不存在");
        Assert.assertEquals(result.get("type"),"ServiceException");

    }

}
