package org.gxb.server.api.coursesys.attachments;

import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class CreateAttachmentTest {
	private int userid = 278269;
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
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
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
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=12qwe", jsonObject);
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
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + null, jsonObject);
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
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=", jsonObject);
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
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + "qwe123" + "/attachments?access_token=" + access_token, jsonObject);
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
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units//attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证viewable_id  //faild
	 */
	@Test(priority = 7)
	public void VerifyInvalidContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", "qwe28");
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证viewable_id  //faild
	 */
	@Test(priority = 8)
	public void VerifyNullContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", null);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证viewable_id  //faild
	 */
	@Test(priority = 9)
	public void VerifyEmptyContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}

	/*
	 * 验证viewable_type  //faild
	 */
	@Test(priority = 10)
	public void VerifyNullContexttype() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", null);
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证viewable_type  //faild
	 */
	@Test(priority = 11)
	public void VerifyEmptyContexttype() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证title   //faild
	 */
	@Test(priority = 12)
	public void VerifyNullTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", null);
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}

	/*
	 * 验证title
	 */
	@Test(priority = 13)  //faild
	public void VerifyEmptyTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证chapter  //faild
	 */
	@Test(priority = 14)
	public void VerifyInvalidChapter() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", "qa1");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter //faild
	 */
	@Test(priority = 15)
	public void VerifyNullChapter() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", null);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter //faild
	 */
	@Test(priority = 16)
	public void VerifyEmptyChapter() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter //faild
	 */
	@Test(priority = 17)
	public void VerifyPositionIsNegative() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", -1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证link //faild
	 */
	@Test(priority = 18)
	public void VerifyNullLink() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", null);
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证link //faild
	 */
	@Test(priority = 19)
	public void VerifyEmptyLink() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("type", "Attachment");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证type //faild
	 */
	@Test(priority = 20)
	public void VerifyNullType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("type", null);
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证type //faild
	 */
	@Test(priority = 21)
	public void VerifyEmptyType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("position", 1);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("viewable_id", 28);
		jsonObject.put("viewable_type", "Course");
		jsonObject.put("title", "title1122");
		jsonObject.put("link", "link11");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("post",
				"/units/" + unitid + "/attachments?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
}
