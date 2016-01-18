package org.gxb.server.api.course.quiz;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.course.videotimenode.UpdateVideoTimeNode;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * ----修改测验
 * http://192.168.30.33:8080/gxb-api/course/quiz/3?loginUserId=123456&tenantId=1
 * course_quiz,course_chapter(content_id=quiz_id),course_question_relate
 */
public class UpdateQuiz {
	private static Logger logger = LoggerFactory.getLogger(UpdateQuiz.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int quizId;
	private String userId;

	@BeforeMethod
	public void InitiaData() {
		url = path + basePath + "/course/";
		quizId = 30;
		userId = "1001";
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
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
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("quizId", equalTo(quizId))
				.body("editorId", equalTo(Integer.parseInt(userId))).body("courseId", equalTo(1))
				.body("title", equalTo(title)).body("questionList.questionId", Matchers.hasItem(questionid));
	}

	@Test(priority = 2, description = "quiz不存在")
	public void verifyNotExistQuiz() {
		quizId = 999999;
		String title = "test1001";
		int questionid = 250;

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", questionid);
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("questionList", jsonArray);

		String paramUrl = url + "quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1";
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改测验接口##verifyNotExistQuiz##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "quiz不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "无效的quiz")
	public void verifyInvalidQuiz() {
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
				"/course/quiz/" + "q1" + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyInvalidQuiz##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "无效的userid")
	public void verifyInvalidUserid() {
		userId = "qw1";
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
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyInvalidUserid##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	// failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserid() {
		userId = "";
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
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyEmptyUserid##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 6, description = "title为空")
	public void verifyEmptyTitle() {
		int questionid = 250;

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", questionid);
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyEmptyTitle##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("titlemay not be empty,"));
	}
	
	@Test(priority = 7, description = "title为空")
	public void verifyNullTitle() {
		int questionid = 250;

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", questionid);
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", null);
		jsonObject.put("questionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyNullTitle##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("titlemay not be empty,"));
	}
	
	@Test(priority = 8, description = "questionId为空")
	public void verifyEmptyQuestionId() {
		String title = "test1001";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyEmptyQuestionId##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("questionListmay not be null,"));
	}
	
	@Test(priority = 9, description = "questionId为空")
	public void verifyNullQuestionId() {
		String title = "test1001";

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", null);
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("questionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyNullQuestionId##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("questionList[0].questionIdmay not be null,"));
	}
	
	@Test(priority = 9, description = "questionId无效")
	public void verifyInvalidQuestionId() {
		String title = "test1001";

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", "qw12");
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("questionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyInvalidQuestionId##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//pass
	@Test(priority = 10, description = "questionId不存在")
	public void verifyNotExistQuestionId() {
		int questionid = 1;
		String title = "test1001";

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", questionid);
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("questionList", jsonArray);

		String paramUrl = path + basePath + "/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1";
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建测验接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "question不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//pass
	@Test(priority = 11, description = "title为32位")
	public void verifyTitleLength() {
		String title = "te1111111111111111111111111111112";
		int questionid = 250;

		JSONArray jsonArray = new JSONArray();
		JSONObject questionList = new JSONObject();
		questionList.put("questionId", questionid);
		jsonArray.add(questionList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("questionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/quiz/" + quizId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title长度需要在0和32之间,"));
	}
}
