package org.gxb.server.api.lms.quiz;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;

public class GetClassUserQuiz {
	private static Logger logger = LoggerFactory.getLogger(GetClassUserQuiz.class);

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int classId = 3045;
		int userId = 1239036;

		JSONArray classArray = new JSONArray();
		classArray.add(classId);

		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classId + "/quiz/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("班次下某个用户的测验提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("submissionId", Matchers.hasItems(938682))
				.body("contextId", Matchers.hasItems(classId)).body("submittedType", Matchers.hasItems("Quiz"))
				.body("userId", Matchers.hasItems(userId))
				.body("quizSubmissionList.contextId", Matchers.hasItems(classArray));
	}
	
	@Test(priority = 2, description = "classId不存在")
	public void verifyClassId() {
		int classId = 2533;
		int userId = 1239036;

		JSONArray classArray = new JSONArray();
		classArray.add(classId);

		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classId + "/quiz/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("班次下某个用户的测验提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "userId不存在")
	public void verifyUserId_001() {
		int classId = 3045;
		int userId = 1115230;

		JSONArray classArray = new JSONArray();
		classArray.add(classId);

		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classId + "/quiz/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("班次下某个用户的测验提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4, description = "userId为空")
	public void verifyUserId_002() {
		int classId = 3045;
		int userId = 1239036;

		JSONArray classArray = new JSONArray();
		classArray.add(classId);

		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classId + "/quiz/user?userId=");

		if (response.getStatusCode() == 500) {
			logger.info("班次下某个用户的测验提交接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
