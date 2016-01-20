package org.gxb.server.api.restassured.classes.group;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class ModifyClassGroup {
    private Logger logger = LoggerFactory.getLogger(ModifyClassGroup.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;



    @Test(description = "正常", priority = 1)
    public void test() {
        JSONObject jo = new JSONObject();
        jo.put("deleteFlag", 1);
        jo.put("groupName", "班组名称api");
        jo.put("userNum", 999);
        jo.put("status", -1);

        Response response = TestConfig.postOrPutExecu("put",
                "class_group/3?loginUserId=654321", jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;
    }
    @Test(description = "groupId已删除deleteFlag:0", priority = 2)
    public void testDeletedGroup01() {
        JSONObject jo = new JSONObject();
        jo.put("deleteFlag", 1);
        jo.put("groupName", "班组名称api");
        jo.put("userNum", 999);

        Response response = TestConfig.postOrPutExecu("put",
                "class_group/28?loginUserId=654321", jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("false")) ;
    }

    @Test(description = "groupId已删除deleteFlag:-1", priority = 2)
    public void testDeletedGroup02() {
        JSONObject jo = new JSONObject();
        jo.put("deleteFlag", 1);
        jo.put("groupName", "班组名称api");
        jo.put("userNum", 999);

        Response response = TestConfig.postOrPutExecu("put",
                "class_group/29?loginUserId=654321", jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("false")) ;
    }

    @Test(description = "groupId不存在", priority = 3)
    public void testNotExistGroup() {
        JSONObject jo = new JSONObject();
        jo.put("deleteFlag", 1);
        jo.put("groupName", "班组名称api");
        jo.put("userNum", 999);

        Response response = TestConfig.postOrPutExecu("put",
                "class_group/-1?loginUserId=654321", jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("false")) ;
    }

}
