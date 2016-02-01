package org.gxb.server.api.lms.topic;

import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import static org.hamcrest.Matchers.equalTo;


public class SaveClassTopic {
	private static Logger logger = LoggerFactory.getLogger(SaveClassTopic.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int classId = 5166;
		int forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", "topic_title");
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", "topic_body");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);
		
		int topicId = response.jsonPath().get("topicId");

		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("topicId", equalTo(topicId))
		.body("classId", equalTo(classId)).body("userId", equalTo(2016))
		.body("tenantId", equalTo(21)).body("forumId", equalTo(forumId))
		.body("title", equalTo("topic_title")).body("body", equalTo("topic_body"));
	}
	
	//failed
	@Test(priority = 2, description = "classId不存在")
	public void verifyClassId_001() {
		int classId = 111199;
		int forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", "topic_title");
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", "topic_body");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400);
	}
	
	//failed
	@Test(priority = 3, description = "classId为空")
	public void verifyClassId_002() {
		int classId = 5166;
		int forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", null);
		jsonObject.put("title", "topic_title");
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", "topic_body");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);
		
		int topicId = response.jsonPath().get("topicId");

		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400);
	}
	
	@Test(priority = 4, description = "title为空")
	public void verifyTitle_001() {
		int classId = 5166;
		int forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", null);
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", "topic_body");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);
		

		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为空,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 5, description = "title长度为32位")
	public void verifyTitle_002() {
		int classId = 5166;
		int forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", "111111111111111111111111111111112");
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", "topic_body");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title长度为32,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 7, description = "forumId不存在")
	public void verifyForumId_001() {
		int classId = 5166;
		int forumId = 119999;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", "topic_title");
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", "topic_body");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400);
	}
	
	//failed
	@Test(priority = 8, description = "forumId为空")
	public void verifyForumId_002() {
		int classId = 5166;
		int forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", "topic_title");
		jsonObject.put("forumId", null);
		jsonObject.put("body", "topic_body");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);


		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 9, description = "body为空")
	public void verifyBody() {
		int classId = 5166;
		int forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", "topic_title");
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", null);

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("用户创建讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("body不能为空,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
}
