package org.gxb.server.api.createcourse.topic;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;


public class GetTopic {
	private static Logger logger = LoggerFactory.getLogger(GetTopic.class);
	private int userId;
	private int topicId;

	@BeforeMethod
	public void InitiaData() {
		topicId = 94;
		userId = 2011;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/topic/" + topicId + "?loginUserId=123456");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取topic接口##verifyCorrectParams##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("topicId", equalTo(topicId))
				.body("deleteFlag", equalTo(1)).body("courseId", equalTo(1)).body("editorId", equalTo(userId))
				.body("title", equalTo("章节2001")).body("body", equalTo("testok"));
	}
	
	@Test(priority = 2, description = "topic不存在")
	public void verifyNotExistAssignment() {
		topicId = 1;
				
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/topic/" + topicId + "?loginUserId=123456");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取topic接口##verifyCorrectParams##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "topic无效")
	public void verifyInvalidAssignment() {
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/topic/" + "q1" + "?loginUserId=123456");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取topic接口##verifyCorrectParams##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
