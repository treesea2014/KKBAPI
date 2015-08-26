package org.kkb.server.api.restassured.quizzes;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * ws.wang
 *
 */
public class QuizzesPutTest {

	String quizzesID = "1682";
	String accesstoken = "24a7b17982d28e8b35527108946dab17";
	JSONObject jsonObject = new JSONObject();

	@BeforeMethod
	public void initData() {
		quizzesID = "1682";
		accesstoken = "24a7b17982d28e8b35527108946dab17";
		
		jsonObject.put("id", 1683);
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "123123作业：编写4代码（满分60000分）");
		jsonObject.put("desc", "<span>请编写一个媒体表达式，针对屏幕最大分辨率为768px的时候和320px的时候分别加载不同的CSS文件。</span>");
		jsonObject.put("quiz_data", null);
		jsonObject.put("score", 2);
		jsonObject.put("assignment_id", null);
		jsonObject.put("shuffle_answers", false);
		jsonObject.put("show_correct_answers", false);
		jsonObject.put("time_limit", 100);
		jsonObject.put("allowed_attempts", 3);
		jsonObject.put("scoring_policy", null);
		jsonObject.put("quiz_type", null);
		jsonObject.put("lock_at", null);
		jsonObject.put("unlock_at", null);
		jsonObject.put("deleted_at", null);
		jsonObject.put("due_at", null);
		jsonObject.put("quiz_questions_count", null);
		jsonObject.put("published_at", null);
		jsonObject.put("last_edited_at", null);
		jsonObject.put("anonymous_submissions", null);
		jsonObject.put("hide_results", null);
		jsonObject.put("one_question_at_a_time", null);
		jsonObject.put("level", null);
		jsonObject.put("position", null);
		jsonObject.put("status", null);
		jsonObject.put("created_at", null);
		jsonObject.put("updated_at", null);
		jsonObject.put("pass_percent", null);
	}

	
	@Test(description = "quizzesID不存在")
	public void testErrQuizzId01() {
		quizzesID = "9999";

		Response response = TestConfig.postOrPutExecu("put", "/quizzes/" + quizzesID + "/?access_token=" + accesstoken,jsonObject);
		response.then().assertThat().
				statusCode(200).
				body("status", equalTo(false)).
				body("message",equalTo("修改失败"));

	}
	@Test(description = "quizzesID非法无效")
	public void testErrQuizzId02() {
		quizzesID = "-1";
		Response response = TestConfig.postOrPutExecu("put", "/quizzes/" + quizzesID + "/?access_token=" + accesstoken,jsonObject);
		response.then().assertThat().
				statusCode(404);

	}
	
	@Test(description = "token非法无效")
	public void testErrToken01() {
		accesstoken = "12354";
		Response response = TestConfig.postOrPutExecu("put", "/quizzes/" + quizzesID + "/?access_token=" + accesstoken,jsonObject);
		response.then().assertThat().
				statusCode(401).
				body("status", equalTo(false)).
				body("message",equalTo("无效的access_token"));

	}
	@Test(description = "token为空")
	public void testErrToken02() {
		accesstoken = "";
		Response response = TestConfig.postOrPutExecu("put", "/quizzes/" + quizzesID + "/?access_token=" + accesstoken,jsonObject);
		response.then().assertThat().
				statusCode(400).
				body("status", equalTo(false)).
				body("message",equalTo("token不能为空"));

	}

	@Test(description = "全部为空")
	public void testBody01() {
		quizzesID = "1682";
		Response response = TestConfig.postOrPutExecu("put", "/quizzes/" + quizzesID + "/?access_token=" + accesstoken,
				jsonObject);
		response.then().assertThat().statusCode(200).body("id", equalTo(quizzesID));
	
	}

}
