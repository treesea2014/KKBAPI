package org.gxb.server.api.lms.quiz;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;

public class GetUserClassQuiz {
	private static Logger logger = LoggerFactory.getLogger(GetUserClassQuiz.class);

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int quizId = 10416;
		int userId = 1239037;

		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);

		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get", "/class_quiz/" + quizId + "/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户某个测验的提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("submittedId", equalTo(quizId))
				.body("submittedType", equalTo("Quiz")).body("userId", equalTo(userId))
				.body("quizSubmissionList.quizId", Matchers.hasItems(quizId));
	}
	
	@Test(priority = 2, description = "quizId不存在")
	public void verifyQuizId() {
		int quizId = 10407;
		int userId = 1239037;

		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);

		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get", "/class_quiz/" + quizId + "/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户某个测验的提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "userId不存在")
	public void verifyUserId_001() {
		int quizId = 10416;
		int userId = 632467;

		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);

		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get", "/class_quiz/" + quizId + "/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户某个测验的提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4, description = "userId为空")
	public void verifyUserId_002() {
		int quizId = 10416;
		int userId = 632467;

		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);

		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get", "/class_quiz/" + quizId + "/user?userId=");

		if (response.getStatusCode() == 500) {
			logger.info("用户某个测验的提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
