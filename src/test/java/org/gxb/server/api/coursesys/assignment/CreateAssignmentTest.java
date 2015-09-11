package org.gxb.server.api.coursesys.assignment;

import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

public class CreateAssignmentTest {
	private int userid = 933913;
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
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
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
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=test11", jsonObject);
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
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + null, jsonObject);
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
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=", jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}
	
	/*
	 * 验证units
	 */
	@Test(priority = 5)
	public void VerifyInvalidUnits() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + "t1" + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}
	
	/*
	 * 验证units
	 */
	@Test(priority = 6)
	public void VerifyEmptyUnits() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}
	
	/*
	 * 验证context_id    //failed
	 */
	@Test(priority = 7)
	public void VerifyInvalidContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "a1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id   //failed
	 */
	@Test(priority = 8)
	public void VerifyNegativeContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", -1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id   //failed
	 */
	@Test(priority = 9)
	public void VerifyNullContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", null);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id  //failed
	 */
	@Test(priority = 10)
	public void VerifyEmptyContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_type  //failed
	 */
	@Test(priority = 11)
	public void VerifyNullContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", null);
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_type  //failed
	 */
	@Test(priority = 12)
	public void VerifyEmptyContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	
	/*
	 * 验证title //failed
	 */
	@Test(priority = 13)
	public void VerifyNullTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", null);
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证title //failed
	 */
	@Test(priority = 14)
	public void VerifyEmptyTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证body   //failed
	 */
	@Test(priority = 15)
	public void VerifyNullBody() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", null);
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证body   //failed
	 */
	@Test(priority = 16)
	public void VerifyEmptyBody() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 17)
	public void VerifyInvalidChapterContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", "a1");
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 18)
	public void VerifyNegativeCContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", -1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 19)
	public void VerifyNullChapterContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", null);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 20)
	public void VerifyEmptyChapterContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-context_type
	 */
	@Test(priority = 21)
	public void VerifyNullChapterContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", null);
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter-context_type
	 */
	@Test(priority = 22)
	public void VerifyEmptyChapterContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("score", 10);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");
		
		JSONObject assetJsonObject = new JSONObject();		
		assetJsonObject.put("link", "testlink");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("level", "low");
		jsonObject.put("title", "英语作业");
		jsonObject.put("body", "背诵单词");
		jsonObject.put("due_at", "1390096800000");
		jsonObject.put("unlock_at", "1389751200000");
		jsonObject.put("lock_at", null);
		jsonObject.put("published_at", null);
		jsonObject.put("due_desc", null);
		jsonObject.put("unlock_desc", null);
		jsonObject.put("published_desc", null);
		jsonObject.put("score", 100);
		jsonObject.put("min_score", 60);
		jsonObject.put("max_score", 100);
		jsonObject.put("submission_types", "online_text");
		jsonObject.put("position", 1);
		jsonObject.put("status", "new");
		jsonObject.put("is_distribution", false);
		jsonObject.put("is_calculate_score", false);
		jsonObject.put("is_peer_review", false);
		jsonObject.put("is_anonymous", false);
		jsonObject.put("auto_distribution_num", 1);
		jsonObject.put("peer_review_started_desc", null);
		jsonObject.put("peer_review_ended_desc", null);
		jsonObject.put("peer_review_started_at", null);
		jsonObject.put("peer_review_ended_at", null);
		jsonObject.put("asset", assetJsonObject);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/assignments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
}
