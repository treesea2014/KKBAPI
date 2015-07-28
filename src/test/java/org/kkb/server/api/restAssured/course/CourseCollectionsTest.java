package org.kkb.server.api.restassured.course;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * 用户收藏课程、取消收藏，查询收藏接口
 * qfan
 */
@Test
public class CourseCollectionsTest {
    //缺少token获取收藏状态
    public void testGetWithoutToken(){
        Response response= TestConfig.getOrDeleteExecu("get", "/classes/526/collections?access_token=");
        response.then().
                assertThat().statusCode(400).
                body("message",equalTo("token不能为空"));
    }

    //使用错误的token获取收藏状态
    public void testGetWithIncorrectToken(){
        Response response= TestConfig.getOrDeleteExecu("get", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c99");
        response.then().
                assertThat().statusCode(401).
                body("status", equalTo(false)).
                body("message",equalTo("无效的access_token")) ;
    }

    //正确获取收藏状态——未收藏
    public void testGetUncollected(){
        Response response= TestConfig.getOrDeleteExecu("get", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c96");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message",equalTo("success")).
                body("data", equalTo(false))
        ;
    }

    //缺少token--修改为已收藏状态
    public void testPostWithoutToken(){

        JSONObject jsonObject=new JSONObject();

        Response response = TestConfig.postOrPutExecu("post", "/classes/526/collections?access_token=", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", equalTo("token不能为空"));
    }

    //错误的token--修改为已收藏状态
    public void testPostWithIncorrectToken(){

        JSONObject jsonObject=new JSONObject();

        Response response = TestConfig.postOrPutExecu("post", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c99", jsonObject);
        response.then().
                assertThat().statusCode(401).
                body("status", equalTo(false)).
                body("message", equalTo("无效的access_token")) ;
    }

    //正确修改为已收藏状态
    public void testPostWithCorrectToken(){

        JSONObject jsonObject=new JSONObject();

        Response response = TestConfig.postOrPutExecu("post", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c96", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message",equalTo("success")) ;

        //正确获取收藏状态——已收藏
        response= TestConfig.getOrDeleteExecu("get", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c96");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message",equalTo("success")).
                body("data", equalTo(true));
    }

    //已修改为已收藏状态-再次修改
    public void testPostWithCorrectToken2(){

        JSONObject jsonObject=new JSONObject();

        Response response = TestConfig.postOrPutExecu("post", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c96", jsonObject);
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message",equalTo("success")).body("data", equalTo("已收藏,不能重复收藏")) ;
    }


    //缺少token-修改为取消收藏状态
    public void testDeleteWithoutToken(){

        Response response = TestConfig.getOrDeleteExecu("delete", "/classes/526/collections?access_token=");
        response.then().
                assertThat().statusCode(400).body("message", equalTo("token不能为空"));
    }

    //错误的token修改为取消收藏状态
    public void testDeleteWithIncorrectToken(){

        Response response = TestConfig.getOrDeleteExecu("delete", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c99");
        response.then().
                assertThat().statusCode(401).
                body("status", equalTo(false)).
                body("message", equalTo("无效的access_token")) ;
    }

    //正确修改为取消收藏状态
    public void testDeleteWithCorrectToken(){

        Response response = TestConfig.getOrDeleteExecu("delete", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c96");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message",equalTo("success")) ;
    }

    //正确获取收藏状态——未收藏
    public void testGetUncollected2(){
        Response response= TestConfig.getOrDeleteExecu("get", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c96");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message",equalTo("success")).
                body("data", equalTo(false))
        ;
    }

    //修改为取消收藏状态-重复操作
    public void testDeleteWithCorrectToken2(){

        Response response = TestConfig.getOrDeleteExecu("delete", "/classes/526/collections?access_token=9fd75fc6994c9dce896379e2a0cf0c96");
        response.then().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message",equalTo("success")).
                body("data", equalTo("未收藏,不能删除"))
        ;
    }
}
