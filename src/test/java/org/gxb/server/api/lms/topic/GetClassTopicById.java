package org.gxb.server.api.lms.topic;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

public class GetClassTopicById {
	private static Logger logger = LoggerFactory.getLogger(GetClassTopicById.class);
	private int topicId;
	private int classId;
	private int forumId;

	@BeforeClass
	public void InitiaData() {
		classId = 5166;
		forumId = 11620;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classId", classId);
		jsonObject.put("title", "topic_title_101");
		jsonObject.put("forumId", forumId);
		jsonObject.put("body", "topic_body_1001");

		Response response = TestConfig.postOrPutExecu("post", "/class_topic?loginUserId=2016&tenantId=21", jsonObject);

		topicId = response.jsonPath().get("topicId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get", "/class_topic/" + topicId);

		if (response.getStatusCode() == 500) {
			logger.info("id获取topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("topicId", equalTo(topicId)).body("classId", equalTo(classId))
				.body("forumId", equalTo(forumId)).body("title", equalTo("topic_title_101"))
				.body("body", equalTo("topic_body_1001"));
	}
	
	@Test(priority = 2, description = "topicId不存在")
	public void verifyTopicId_001() {
		topicId = 1;
		
		Response response = TestConfig.getOrDeleteExecu("get", "/class_topic/" + topicId);

		if (response.getStatusCode() == 500) {
			logger.info("id获取topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 2, description = "delete_flag = 0")
	public void verifyTopicId_002() {
		topicId = 15227;
		
		Response response = TestConfig.getOrDeleteExecu("get", "/class_topic/" + topicId);

		if (response.getStatusCode() == 500) {
			logger.info("id获取topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
