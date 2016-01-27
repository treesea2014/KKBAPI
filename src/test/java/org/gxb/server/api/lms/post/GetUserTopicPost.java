package org.gxb.server.api.lms.post;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;

/*
 * http://192.168.30.33:8080/gxb-api/class_topic/130/post/user?userId=1300
 */
public class GetUserTopicPost {
	private static Logger logger = LoggerFactory.getLogger(GetUserTopicPost.class);
	private int topicId;
	private int userId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		topicId = 130;
		userId = 1300;

		JSONArray topicJson = new JSONArray();
		topicJson.add(topicId);

		JSONArray userJson = new JSONArray();
		userJson.add(userId);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_topic/" + topicId + "/post/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户某个讨论的直接回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("topicId", equalTo(topicJson)).body("userId",
				equalTo(userJson));
	}
	
	@Test(priority = 2, description = "topicId不存在")
	public void verifyTopicId() {
		topicId = 1;
		userId = 1300;

		JSONArray topicJson = new JSONArray();
		topicJson.add(topicId);

		JSONArray userJson = new JSONArray();
		userJson.add(userId);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_topic/" + topicId + "/post/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户某个讨论的直接回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "userId为空")
	public void verifyUserId_001() {
		topicId = 130;

		JSONArray topicJson = new JSONArray();
		topicJson.add(topicId);

		JSONArray userJson = new JSONArray();
		userJson.add(userId);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_topic/" + topicId + "/post/user?userId=");

		if (response.getStatusCode() == 500) {
			logger.info("用户某个讨论的直接回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4, description = "userId不存在")
	public void verifyUserId_002() {
		topicId = 130;
		userId = 999999;

		JSONArray topicJson = new JSONArray();
		topicJson.add(topicId);

		JSONArray userJson = new JSONArray();
		userJson.add(userId);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_topic/" + topicId + "/post/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户某个讨论的直接回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
