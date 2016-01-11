package org.gxb.server.api.createcourse.quiz;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
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
 * ----添加测验
 * http://192.168.30.33:8080/gxb-api/course/quiz/3?loginUserId=123456&tenantId=1
 * course_quiz,course_chapter(content_id=quiz_id),course_question_relate
 */
public class AddQuiz {
	private static Logger logger = LoggerFactory.getLogger(AddQuiz.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int itemId;
	private int userId;

	@BeforeMethod
	public void InitiaData() {
		itemId = 262;
		userId = 2602;
	}

	// failed
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("itemId", equalTo(itemId)).body("courseId", equalTo(483))
				.body("contentType", equalTo("Quiz")).body("title", equalTo(title)).body("position", equalTo(position))
				.body("editorId", equalTo(userId)).body("quiz.editorId", equalTo(userId))
				.body("quiz.title", equalTo(title)).body("quiz.courseId", equalTo(483))
				.body("quiz.questionList.questionId", Matchers.hasItems(questionId));
	}

	@Test(priority = 2, description = "itemId不存在")
	public void verifyNotExistItem() {
		itemId = 1;

		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		String paramUrl = path + basePath + "/course/item/" + itemId + "/quizImport?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建测验接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "item 不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "无效的itemid")
	public void verifyInvalidItemId() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + "qw12" + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "无效的userid")
	public void verifyInvalidUserId() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + "q12", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	// failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserId() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post", "/course/item/" + itemId + "/quizImport?loginUserId=",
				chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 6, description = "title为空")
	public void verifyNullTitle() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", null);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title不能为空,"));
	}

	// failed
	@Test(priority = 6, description = "title长度为32位")
	public void verifyTitleLength() {
		String title = "test11111111111111111111111111111";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title长度为32位"));
	}

	@Test(priority = 7, description = "position无效")
	public void verifyInvalidPosition_001() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", "q1");
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	// failed
	@Test(priority = 7, description = "position为0")
	public void verifyInvalidPosition_002() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	// failed
	@Test(priority = 8, description = "position为负数")
	public void verifyInvalidPosition_003() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = -1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	@Test(priority = 9, description = "position为空")
	public void verifyInvalidPosition_004() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", null);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 10, description = "title为空")
	public void verifyInvalidQuizTitle_001() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", null);
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("quiz.title不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 11, description = "title长度为32")
	public void verifyInvalidQuizTitle_002() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "test11111111111111111111111111111");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title长度为32位"));
	}

	@Test(priority = 12, description = "documentId为空")
	public void verifyInvalidQuestionId_001() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", null);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("quiz.questionList[0].questionId不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 13, description = "documentId无效")
	public void verifyInvalidQuestionId_002() {
		String title = "test_测验2001";
		int questionId = 262;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", "q1");

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	// failed
	@Test(priority = 14, description = "questionId在question表中不存在")
	public void verifyInvalidQuestionId_003() {
		String title = "test_测验2001";
		int questionId = 1;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionId", questionId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject quizJson = new JSONObject();
		quizJson.put("title", "web前端11");
		quizJson.put("questionList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("quiz", quizJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/quizImport?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("documentId不存在"));
	}

}
