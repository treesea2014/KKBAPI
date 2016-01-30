package org.gxb.server.api.lms.quiz;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import static org.hamcrest.Matchers.equalTo;

public class GetPaperClassQuiz {
	private static Logger logger = LoggerFactory.getLogger(GetPaperClassQuiz.class);
	private int quizId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		quizId = 11770;

		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);

		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get", "/class_quiz/" + quizId + "/paper");

		if (response.getStatusCode() == 500) {
			logger.info("class_quiz/6/paper接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("quizId", equalTo(quizId)).body("classId", equalTo(5114))
				.body("tenantId", equalTo(313))
				.body("questionList.questionId", Matchers.hasItems(102025, 102023, 102024, 102022))
				.body("questionList.courseId", Matchers.hasItems(3346));
	}
	
	@Test(priority = 2, description = "quizId不存在")
	public void verifyQuizId() {
		quizId = 1;

		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);

		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get", "/class_quiz/" + quizId + "/paper");

		if (response.getStatusCode() == 500) {
			logger.info("class_quiz/6/paper接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
