package org.gxb.server.api.coursesys.video;

import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

public class CreateVideoTest {
	private int userid = 930766;
	private int tenantsid = 10;
	private int unitid = 20;
	private String access_token = TestConfig.getTokenByUsidAndTeid(userid, tenantsid);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyCorrectData() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=24a7b179", jsonObject);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + null, jsonObject);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=", jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}

	/*
	 * 验证units
	 */
	@Test(priority = 5)
	public void VerifyInvalidUnitid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + "test11" + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证units
	 */
	@Test(priority = 6)
	public void VerifyEmptyUnitid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}
	
	/*
	 * 验证title
	 */
	@Test(priority = 7)
	public void VerifyNullTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", null);
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证title
	 */
	@Test(priority = 8)
	public void VerifyEmptyTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}

	
	/*
	 * 验证chapter-context_id  //faild
	 */
	@Test(priority = 9)
	public void VerifyInvalidContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", "test1");
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-context_id  //faild
	 */
	@Test(priority = 10)
	public void VerifyNullContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", null);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-context_id  //faild
	 */
	@Test(priority = 11)
	public void VerifyEmptyContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-context_type  //faild
	 */
	@Test(priority = 12)
	public void VerifyNullContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", null);
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-context_type  //faild
	 */
	@Test(priority = 13)
	public void VerifyEmptyContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-score  //faild
	 */
	@Test(priority = 14)
	public void VerifyInvalidScore() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", "test10");
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-score  //faild
	 */
	@Test(priority = 15)
	public void VerifyNullScore() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", null);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-score //faild
	 */
	@Test(priority = 16)
	public void VerifyEmptyScore() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-score //faild
	 */
	@Test(priority = 17)
	public void VerifyNegativeScore() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", -1);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	/*
	 * 验证chapter-position //faild
	 */
	@Test(priority = 18)
	public void VerifyInvalidPosition() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", "test1");
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-position //faild
	 */
	@Test(priority = 19)
	public void VerifyNullPosition() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", null);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-position //faild
	 */
	@Test(priority = 20)
	public void VerifyEmptyPosition() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 80);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-position //faild
	 */
	@Test(priority = 21)
	public void VerifyNegativePosition() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", -1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-status  //faild
	 */
	@Test(priority = 22)
	public void VerifyNullStatus() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", null);
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-status  //faild
	 */
	@Test(priority = 23)
	public void VerifyEmptyStatus() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-context_id  //faild
	 */
	@Test(priority = 24)
	public void VerifyNegativeContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", -1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title333333");
		jsonObject.put("quiz_questions_count", 1);
		jsonObject.put("position", 1);
		jsonObject.put("status", null);
		jsonObject.put("persistent_ids", "54f567cf7823de406838f704");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/videos?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
}
