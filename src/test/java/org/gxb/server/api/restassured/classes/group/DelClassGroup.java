package org.gxb.server.api.restassured.classes.group;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
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

public class DelClassGroup {
    private Logger logger = LoggerFactory.getLogger(DelClassGroup.class);
   	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;


    @Test(description = "正常", priority = 1)
    public void test() {
        JSONArray delList = new JSONArray();
        delList.add(88);
        delList.add(89);
        Response response = TestConfig.postOrPutExecu("put",
                "/class_group/batch_delete?loginUserId=987654",delList);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;
    }
    @Test(description = "要删除的集合为空", priority = 2)
    public void testEmptyDelList() {
        JSONArray delList = new JSONArray();

        Response response =   TestConfig.postOrPutExecu("put",
                "/class_group/batch_delete?loginUserId=987654",delList);
        response.then().log().all().assertThat()
                .statusCode(500)
                 ;
    }

    @Test(description = "已经删除再次删除", priority = 3)
    public void testDeleted() {
        JSONArray delList = new JSONArray();
        delList.add(90);
        delList.add(91);
        Response response =   TestConfig.postOrPutExecu("put",
                "/class_group/batch_delete?loginUserId=987654",delList);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true")) ;

        response = TestConfig.postOrPutExecu("put",
                "/class_group/batch_delete?loginUserId=987654",delList);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("false")) ;
    }




}
