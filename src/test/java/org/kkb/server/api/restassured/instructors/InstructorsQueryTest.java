package org.kkb.server.api.restassured.instructors;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

import org.kkb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * <p>建课-查询讲师
 * http://w-api-f1.kaikeba.cn/tenants/1/instructors?access_token=79415208f5cab7101d1fcadc922f6ef7
 * </p>
 * 
 */
public class InstructorsQueryTest {
	
	private final static Logger logger = LoggerFactory.getLogger(InstructorsQueryTest.class);
	String token = TestConfig.getTokenbyUserID();;
	String tenantId;
	int instructorId;
	
	//创建一个讲师 返回一个 讲师id
		@BeforeClass
	    public void testAddInstructors(){
		    JSONObject jsonObject = new JSONObject();
			jsonObject.put("avatar", "234.jpg");//头像
			jsonObject.put("name", "讲师姓名全量查询");//姓名
			jsonObject.put("title", "头衔");//职称
			jsonObject.put("intro", "介绍");//介绍
			jsonObject.put("desc", "描述");//
			jsonObject.put("sina_weibo", "sina_weibo@sina.com");
			jsonObject.put("tag", "老师");
			jsonObject.put("tx_weibo", "987@qq.com");
			Response response= TestConfig.postOrPutExecu("post","/tenants/1/instructors?access_token="+token, jsonObject);
	       logger.info(response.asString());
	       response.then().
	               assertThat().statusCode(200).
	               body("status",equalTo(true)).body("message", equalTo("success"));
	       String body=response.body().asString();
	       instructorId= JsonPath.with(body).get("data");
	    }
		
	@BeforeMethod
	public void initData() {
		tenantId = "1";
		token = TestConfig.getTokenbyUserID();
	}
	
	@Test(description="token无效",priority=1)
    public void testErrToken01(){
		token="111";
		Response response= TestConfig.getOrDeleteExecu("get","/tenants/"+tenantId+"/instructors?access_token="+token);
       logger.info(response.asString());
       response.then().
               assertThat().statusCode(401).
               body("status",equalTo(false)).body("message", equalTo("无效的access_token"));
    }
	
	@Test(description="token为空",priority=2)
    public void testErrToken02(){
		token="";
		Response response= TestConfig.getOrDeleteExecu("get","/tenants/"+tenantId+"/instructors?access_token="+token);
       logger.info(response.asString());
       response.then().
               assertThat().statusCode(400).
               body("status",equalTo(false)).body("message", equalTo("token不能为空"));
    }
	
	@Test(description="租户不存在",priority=3)
    public void testErrTenantId(){
		tenantId="99999";
		Response response= TestConfig.getOrDeleteExecu("get","/tenants/"+tenantId+"/instructors?access_token="+token);
       logger.info(response.asString());
       response.then().
               assertThat().statusCode(200).
               body("status",equalTo(true)).body("message", equalTo("success"))
               .body("data", equalTo(null));
    }
	
	@Test(description="正常" ,priority=4)
    public void test(){
		Response response= TestConfig.getOrDeleteExecu("get","/tenants/"+tenantId+"/instructors?access_token="+token);
		String json = response.asString();
        JsonPath jp = new JsonPath(json);
        Assert.assertTrue(jp.get("data.pageList.id").toString().contains("137"));;
       logger.info(response.asString());
       logger.info(response.jsonPath().get("data.pageList.id").toString());
       response.then().
               assertThat().statusCode(200).
               body("status",equalTo(true)).body("message", equalTo("success")).
               body("data.pageList.id", Matchers.hasItems(instructorId)).
               body("data.pageList.name", Matchers.hasItems("讲师姓名全量查询"));
    }
	
	
	 

}
