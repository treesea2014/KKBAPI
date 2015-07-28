package org.kkb.server.api.restassured.studentUser;

import com.jayway.restassured.response.Response;
import org.kkb.server.api.TestConfig;

/**
 * 获取用户列表
 */
public class StudentUserListTest {
    //token 缺失
    public void testWithNoToken(){
        String url="/student_users?curPage=1&pageSize=10&q=&sortKey= &order=asc";
        Response response = TestConfig.getOrDeleteExecu("get", url);
        response.then().
                assertThat().statusCode(400);
    }

    //token 不存在
    public void testWithErrortoken(){
        String url="/student_users?access_token=5678rtyui&curPage=1&pageSize=10&q=&sortKey=id&order=asc";
        Response response = TestConfig.getOrDeleteExecu("get", url);
        response.then().
                assertThat().statusCode(401);
    }
    //根据id，进行升序排序
    public void testWithIdAsc(){
        String url="/student_users?access_token=5678rtyui&curPage=1&pageSize=10&q=&sortKey=id&order=asc";
        Response response = TestConfig.getOrDeleteExecu("get", url);
        response.then().
                assertThat().statusCode(200);
        //判断 data.size《=total
    }



}
