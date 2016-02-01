package org.gxb.server.api.lms.topic;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;


public class GetUserClassTopic {
	private static Logger logger = LoggerFactory.getLogger(GetUserClassTopic.class);
	private int userId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		userId = 2016;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_topic/user?userId=" + userId + "&tenantId=1111");

		if (response.getStatusCode() == 500) {
			logger.info("用户发起的讨论查询接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(3)).body("dataList.topicId",
				Matchers.hasItems(20984, 20985, 20990));
	}
	
	@Test(priority = 2, description = "userId不存在")
	public void verifyUserId() {
		userId = 999111;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_topic/user?userId=" + userId + "&tenantId=1111");

		if (response.getStatusCode() == 500) {
			logger.info("用户发起的讨论查询接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
}
