package org.gxb.server.api.createcourse;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/*
 * ----根据id获取quiz信息（包含题目选项）
 * http://192.168.30.33:8080/gxb-api/course/quiz/3?loginUserId=1&tenantId=1
 * question,question_option,course_quiz,course_chapter,course_question_relate
 */
public class GetQuiz {
	private static Logger logger = LoggerFactory.getLogger(GetResourceVideo.class);
	private String quiz;

	@BeforeMethod
	public void InitiaData() {
		quiz = "3";
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("根据id获取quiz信息接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("quizId", equalTo(Integer.parseInt(quiz)))
				.body("deleteFlag", equalTo(1)).body("courseId", equalTo(1)).body("userId", equalTo(123456))
				.body("title", equalTo("test1001")).body("quizQuestionsCount", equalTo(1)).body("editorId", equalTo(1001))
				.body("questionList.questionId", Matchers.hasItems(250)).body("questionList.questionName", Matchers.hasItems("项目风险评估的依据包括哪些？"))
				.body("questionList.questionType", Matchers.hasItems("多选题")).body("questionList.courseId", Matchers.hasItems(1));
//				.body("questionList.optionList.questionOptionId", Matchers.arrayContaining(687,688,689,670));
//		.body("questionList.optionList.questionId", Matchers.hasItems(250))"[687,688,689,670]"
//		.body("questionList.optionList.content", Matchers.hasItems("已识别的风险","项目的进展情况","项目的性质和规模","数据的准确性和可靠性"))
	}

	@Test(priority = 2, description = "Quiz不存在")
	public void verifyNotExistQuiz() {
		quiz = "999999";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("根据id获取quiz信息接口##verifyNotExistQuiz##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "Quiz无效")
	public void verifyInvalidQuiz() {
		quiz = "99qw";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("根据id获取quiz信息接口##verifyInvalidQuiz##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "Quiz为空")
	public void verifyEmptyQuiz() {
		quiz = "";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("根据id获取quiz信息接口##verifyEmptyQuiz##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

}
