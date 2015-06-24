package org.kkb.server.api.restAssured.studentUser;

import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;

/**
 * 获取学校所有班次
 */
public class TenantsGetAllClassTest {
    //参数--缺少参数
    public void testWithNoToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "/tenants/0" );
        response.then().
                assertThat().statusCode(400);
    }

    //参数--token不存在
    public void testWithErrorToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "/tenants/0?access_token=45678olkjhgfd" );
        response.then().
                assertThat().statusCode(401);
    }
    //参数--没有权限
    public void testWithInvalidToken(){
        Response response = TestConfig.getOrDeleteExecu("get", "/tenants/0?access_token=45678olkjhgfd" );
        response.then().
                assertThat().statusCode(200).body("message" ,Matchers.equalTo("没有相应权限"));
    }

    //成功获取
    public void testSuc(){
        Response response = TestConfig.getOrDeleteExecu("get", "/tenants/1?access_token=45678olkjhgfd" );
        response.then().
                assertThat().statusCode(200).body("message" ,Matchers.equalTo("没有相应权限"));
    }


}
