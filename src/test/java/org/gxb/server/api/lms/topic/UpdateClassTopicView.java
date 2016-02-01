package org.gxb.server.api.lms.topic;

import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;


public class UpdateClassTopicView {
	private static Logger logger = LoggerFactory.getLogger(UpdateClassTopicView.class);
	private int topicId = 18030;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {

		Response response = TestConfig.postOrPutFileExecu("put", "/class_topic/" + topicId + "/view");

		if (response.getStatusCode() == 500) {
			logger.info("更新topic viewcount接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "更新topic viewcount失败");
	}
	
	@Test(priority = 2, description = "topicId不存在")
	public void verifyTopicId() {
		topicId = 1;
		
		Response response = TestConfig.postOrPutFileExecu("put", "/class_topic/" + topicId + "/view");

		if (response.getStatusCode() == 500) {
			logger.info("更新topic viewcount接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "更新topic viewcount失败");
	}
}
