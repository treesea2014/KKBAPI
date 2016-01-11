package org.gxb.server.api.course.quiz;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.gxb.server.api.course.resourcevideo.GetResourceVideo;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * ----根据id获取quiz信息（包含题目选项）
 * http://192.168.30.33:8080/gxb-api/course/quiz/3?loginUserId=1&tenantId=1
 * question,question_option,course_quiz,course_chapter,course_question_relate
 */
public class GetQuiz {
	private static Logger logger = LoggerFactory.getLogger(GetQuiz.class);
	private String quiz;

	@BeforeMethod
	public void InitiaData() {
		quiz = "3";
	}
	
	
	@Test(priority = 1, description = "正确的参数")
	public void updateQuiz() {
		String userId = "1001";
		String title = "test1001";
		int questionid = 250;

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", questionid);
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("questionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/quiz/" + quiz + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("quizId", equalTo(Integer.parseInt(quiz)));
	}
	

	@Test(priority = 2, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("根据id获取quiz信息接口##verifyCorrectParams##" + response.prettyPrint());
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(687);
		jsonArray.add(688);
		jsonArray.add(689);
		jsonArray.add(690);

		response.then().assertThat().statusCode(200).body("quizId", equalTo(Integer.parseInt(quiz)))
				.body("deleteFlag", equalTo(1)).body("courseId", equalTo(1)).body("userId", equalTo(123456))
				.body("title", equalTo("test1001")).body("quizQuestionsCount", equalTo(1)).body("editorId", equalTo(1001))
				.body("questionList.questionId", Matchers.hasItems(250)).body("questionList.questionName", Matchers.hasItems("项目风险评估的依据包括哪些？"))
				.body("questionList.questionType", Matchers.hasItems("多选题")).body("questionList.courseId", Matchers.hasItems(1))
				.body("questionList.optionList.questionOptionId", Matchers.hasItems(jsonArray));
	}

	@Test(priority = 3, description = "Quiz不存在")
	public void verifyNotExistQuiz() {
		quiz = "999999";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("根据id获取quiz信息接口##verifyNotExistQuiz##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 4, description = "Quiz无效")
	public void verifyInvalidQuiz() {
		quiz = "99qw";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("根据id获取quiz信息接口##verifyInvalidQuiz##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 5, description = "Quiz为空")
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
