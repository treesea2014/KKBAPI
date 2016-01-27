package org.gxb.server.api.lms.post;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * http://192.168.30.33:8080/gxb-api/class_topic/user/post?userId=1300
 */
public class GetUserPost {
	private static Logger logger = LoggerFactory.getLogger(GetUserPost.class);
	private OperationTable operationTable = new OperationTable();
	private int userId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		userId = 1300;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_topic/user/post?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户回复的讨论接口##" + response.prettyPrint());
		}

		int expectCount = 0;
		
		try {
			expectCount = operationTable.selectClassTopic(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.then().assertThat().statusCode(200)
		.body("paging.total", equalTo(expectCount))
		.body("dataList.topicId", Matchers.hasItems(130))
		.body("dataList.classId", Matchers.hasItems(21))
		.body("dataList.forumId", Matchers.hasItems(32));
	}
	
	@Test(priority = 2, description = "userId不存在")
	public void verifyNotExistTopicId() {
		userId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_topic/user/post?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户回复的讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
	
	@Test(priority = 3, description = "userId为空")
	public void verifyEmptyTopicId() {

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_topic/user/post?userId=");

		if (response.getStatusCode() == 500) {
			logger.info("用户回复的讨论接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
}
