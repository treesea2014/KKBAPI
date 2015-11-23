package org.gxb.server.api.restassured.user;

import com.jayway.restassured.response.Response;

import net.sf.saxon.functions.Matches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 用户信息查询 by id
 * 123.57.210.46:8080/gxb-api/user/userId
 * 
 */
public class GetUserInfoById {
	


	@Test(description="正常" ,priority=1)
    public void test(){
		String  userId = "541513";
		Response response= TestConfig.getOrDeleteExecu("get","/user/"+userId );
        response.then()
       			.log().all()
               .assertThat().statusCode(200)
               .body("userId", equalTo(541513))
               .body("username", equalTo("yguo"))
               .body("students.userId", Matchers.hasItem(541513))
               .body("students.schoolId", Matchers.hasItem(247))
               .body("students.tenantId", Matchers.hasItem(223))
              ;
    }	
	
	@Test(description="非数字" ,priority=1)
    public void testWithInvaild(){
		String  userId = "x";
		Response response= TestConfig.getOrDeleteExecu("get","/user/"+userId );
        response.then()
       			.log().all()
               .assertThat().statusCode(400)
               .body("message", Matchers.containsString("NumberFormatException"))
             
              ;
    }	
	@Test(description="超长数字" ,priority=1)
    public void testWithBigNum(){
		String  userId = "99999999999999999999999999999";
		Response response= TestConfig.getOrDeleteExecu("get","/user/"+userId );
        response.then()
       			.log().all()
               .assertThat().statusCode(400)
               .body("message", Matchers.containsString("NumberFormatException"))
             
              ;
    }	

	@Test(description="不存在的用户信息" ,priority=1)
    public void testWithtExist(){
		String  userId = "-1";
		Response response= TestConfig.getOrDeleteExecu("get","/user/"+userId );
        response.then()
       			.log().all()
               .assertThat().statusCode(200)
             
              ;
    }	
	
}
