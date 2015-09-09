package org.kkb.server.api.restassured.instructors;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

import org.kkb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * <p>建课-新增讲师
 * http://w-api-f1.kaikeba.cn/instructors/1?access_token=79415208f5cab7101d1fcadc922f6ef7
 * </p>
 * 
 */
public class InstructorsModify {
	
	private final static Logger logger = LoggerFactory.getLogger(InstructorsModify.class);
	JSONObject jsonObject = new JSONObject();
	String token =TestConfig.getTokenbyUserID();
	int instructorId;
	
	//创建一个讲师 返回一个 讲师id
	@BeforeClass
    public void testAddInstructors(){
	    logger.info("request Body:{}，{}",jsonObject.toString());
	    jsonObject.put("avatar", "234.jpg");//头像
		jsonObject.put("name", "讲师姓名");//姓名
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
		token =TestConfig.getTokenbyUserID();
		jsonObject.clear();
		jsonObject.put("avatar", "修改234.jpg");//头像
		jsonObject.put("name", "修改讲师姓名");//姓名
		jsonObject.put("title", "修改头衔");//职称
		jsonObject.put("intro", "修改介绍");//介绍
		jsonObject.put("desc", "修改描述");//
		jsonObject.put("sina_weibo", "修改sina_weibo@sina.com");
		jsonObject.put("tag", "修改老师");
		jsonObject.put("tx_weibo", "修改987@qq.com");

	}

	
	@Test(description="正常" ,priority=1)
    public void test(){
	    logger.info("request Body:{}，{}",jsonObject.toString());
		Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
       logger.info(response.asString());
       response.then().
               assertThat().statusCode(200).
               body("status",equalTo(true)).body("message", equalTo("success"));
    }
	
	 
	@Test(description="token为空",priority=2)
    public void testErrToken01(){
		token="";
		Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
       logger.info(response.asString());
       response.then().
               assertThat().statusCode(400).
               body("status",equalTo(false)).body("message", equalTo("token不能为空"));
    }
	
	
	@Test(description="token无效",priority=3)
    public void testErrToken02(){
		token="111";
		Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
       logger.info(response.asString());
       response.then().
               assertThat().statusCode(401).
               body("status",equalTo(false)).body("message", equalTo("无效的access_token"));
    }
	
	
	@Test(description="照片不能为空",priority=4)
	 public void testNoAvatar(){
		   jsonObject.remove("avatar");
	       logger.info("request Body:{}",jsonObject.toString());
			Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
	       logger.info(response.asString());
	       response.then().
	               assertThat().statusCode(400).
	               body("status",equalTo(false)).body("message", equalTo("照片不能为空"));
	   }
	
	@Test(description="讲师姓名为空",priority=5)
	 public void testNoName(){
		   jsonObject.remove("name");
	       logger.info("request Body:{}",jsonObject.toString());
			Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
	       logger.info("response:{}",response.asString());
	       response.then().
	               assertThat().statusCode(400).
	               body("status",equalTo(false)).body("message", equalTo("讲师姓名不能为空"));
	   }
	@Test(description="讲师介绍为空",priority=6)
	 public void testNoInstro(){
		   jsonObject.remove("intro");
	       logger.info("request Body:{}",jsonObject.toString());
			Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
	       logger.info("response:{}",response.asString());
	       response.then().
	               assertThat().statusCode(400).
	               body("status",equalTo(false)).body("message", equalTo("讲师介绍不能为空"));
	   }
	@Test(description="讲师头衔为空",priority=7)
	 public void testNoTitle(){
		   jsonObject.remove("title");
	       logger.info("request Body:{}",jsonObject.toString());
			Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
	       logger.info("response:{}",response.asString());
	       response.then().
	               assertThat().statusCode(400).
	               body("status",equalTo(false)).body("message", equalTo("讲师头衔不能为空"));
	   }
	@Test(description="租户不存在",priority=8)
	 public void testNoTenant(){
		instructorId = 999999;
	       logger.info("request Body:{}",jsonObject.toString());
			Response response= TestConfig.postOrPutExecu("put","/instructors/"+instructorId+"?access_token="+token, jsonObject);
	       logger.info("response:{}",response.asString());
	       response.then().
	               assertThat().statusCode(400).
	               body("status",equalTo(false)).body("message", equalTo("讲师头衔不能为空"));
	   }
}
