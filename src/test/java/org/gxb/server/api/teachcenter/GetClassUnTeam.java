package org.gxb.server.api.teachcenter;

import com.jayway.restassured.response.Response;

import org.kkb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 教学中心-班组-查询班次列表
 * http://w-api-f2.kaikeba.cn/classes/2083?access_token=1aace0664359768b607e63b55cd8ed72
 * 
 */
public class GetClassUnTeam {
	
	private final static Logger logger = LoggerFactory.getLogger(GetClassUnTeam.class);
	private int classid =2083;

	private String token = TestConfig.getDefaultToken();


	@Test(description="正常" ,priority=1)
    public void test(){
		Response response= TestConfig.getOrDeleteExecu("get","/classes/"+classid+"?access_token="+token);
       response.then().
       			//log().all().
               assertThat().statusCode(200).
               body("status",equalTo(true)).
               body("message",equalTo("success")).
               body("data.id",equalTo(classid)).
               body("data.name", equalTo("响应式Web设计-单元得分")).
               body("data.course.id",equalTo(1)).
               body("data.course.name",equalTo("响应式Web设计")).
               body("data.course.instructorList.id",Matchers.hasItem(1)).
               body("data.course.instructorList.name",Matchers.hasItem("马鉴"))
               ;
       
      
    }	
	
	@Test(description="token无效",priority=2)
    public void testErrToken01(){
		Response response= TestConfig.getOrDeleteExecu("get","/classes/"+classid+"?access_token=xx");
       response.then().
               assertThat().statusCode(401).
               body("status",equalTo(false)).body("message", equalTo("无效的access_token"));
    }
	@Test(description="token为空",priority=3)
    public void testErrToken02(){
		Response response= TestConfig.getOrDeleteExecu("get","/classes/"+classid+"?access_token=");
       response.then().
               assertThat().statusCode(401).
               body("status",equalTo(false)).body("message", equalTo("access_token不能为空"));
    }
	
	@Test(description="classid为99999999",priority=4)
    public void testErrclassid01(){
		Response response= TestConfig.getOrDeleteExecu("get","/classes/"+classid+"?access_token="+token);
       response.then().
               assertThat().statusCode(401).
               body("status",equalTo(false)).body("message", equalTo("班次信息不存在"));
    }


	
	 

}
