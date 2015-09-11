package org.gxb.server.api.coursesys.pages;

import static org.hamcrest.Matchers.equalTo;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class CreatePageTest {
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
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post", "/units/" + unitid + "/pages?access_token=qwe123",
				jsonObject);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post", "/units/" + unitid + "/pages?access_token=" + null,
				jsonObject);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post", "/units/" + unitid + "/pages?access_token=", jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}

	/*
	 * 验证units
	 */
	@Test(priority = 5)
	public void VerifyInvalidUnitid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + "qwe123" + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证units
	 */
	@Test(priority = 6)
	public void VerifyEmptyUnitid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post", "/units/" + "/pages?access_token=" + access_token,
				jsonObject);
		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证context_id  //faild
	 */
	@Test(priority = 7)
	public void VerifyInvalidContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "qwe1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id  //faild
	 */
	@Test(priority = 8)
	public void VerifyNullContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", null);
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id  //faild
	 */
	@Test(priority = 9)
	public void VerifyEmptyContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_type
	 */
//	@Test(priority = 10)
//	public void VerifyInvalidContexttype() {
//		JSONObject chapterjsonObject = new JSONObject();
//		chapterjsonObject.put("position", 1);
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("context_id", "1");
//		jsonObject.put("context_type", "Course11");
//		jsonObject.put("title", "title111111");
//		jsonObject.put("chapter", chapterjsonObject);
//
//		Response response = TestConfig.postOrPutExecu("post",
//				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
//		response.then().assertThat().statusCode(400).body("status", equalTo(false));
//	}

	/*
	 * 验证context_type  //faild
	 */
	@Test(priority = 11)
	public void VerifyNullContexttype() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", null);
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_type  //faild
	 */
	@Test(priority = 12)
	public void VerifyEmptyContexttype() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证title   //faild
	 */
	@Test(priority = 13)
	public void VerifyNullTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", null);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}

	/*
	 * 验证title
	 */
	@Test(priority = 14)  //faild
	public void VerifyEmptyTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter  //faild
	 */
	@Test(priority = 15)
	public void VerifyInvalidChapter() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", "qw11");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter //faild
	 */
	@Test(priority = 16)
	public void VerifyNullChapter() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", null);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter //faild
	 */
	@Test(priority = 17)
	public void VerifyEmptyChapter() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter  //faild
	 */
	@Test(priority = 18)
	public void VerifyPositionIsNegative() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", -1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("title", "title111111");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/pages?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
}
