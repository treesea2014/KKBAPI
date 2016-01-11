package org.gxb.server.api.course.topic;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

/*
 * ----保存课程topic
 * http://192.168.30.33:8080/gxb-api/course/item/186/topic?loginUserId=123456&tenantId=1
 *  course_chapter,course_topic,itemid从course_item表中取到
 */
public class AddTopic {
	private static Logger logger = LoggerFactory.getLogger(AddTopic.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int itemId;
	private int userId;

	@BeforeMethod
	public void InitiaData() {
		itemId = 201;
		userId = 2011;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("itemId", equalTo(itemId)).body("courseId", equalTo(1))
				.body("contentType", equalTo("Topic")).body("title", equalTo(title))
				.body("position", equalTo(position)).body("editorId", equalTo(userId))
				.body("topic.editorId", equalTo(userId)).body("topic.title", equalTo(title))
				.body("topic.body", equalTo(infor));
	}

	@Test(priority = 2, description = "itemId不存在")
	public void verifyNotExistItem() {
		itemId = 1;

		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		String paramUrl =  path + basePath + "/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=12";
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("保存课程topic接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "item 不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "无效的itemid")
	public void verifyInvalidItemId() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + "qw12" + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "无效的userid")
	public void verifyInvalidUserId() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + "q12" + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	// failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserId() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 6, description = "title为空")
	public void verifyNullTitle() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", null);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title不能为空,"));
	}

	// failed
	@Test(priority = 6, description = "title长度为32位")
	public void verifyTitleLength() {
		String title = "test11111111111111111111111111111";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title长度为32位"));
	}

	@Test(priority = 7, description = "position无效")
	public void verifyInvalidPosition_001() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", "qw12");
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	// failed
	@Test(priority = 7, description = "position为0")
	public void verifyInvalidPosition_002() {
		String title = "章节2001";
		String infor = "testok";
		int position = 0;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	// failed
	@Test(priority = 8, description = "position为负数")
	public void verifyInvalidPosition_003() {
		String title = "章节2001";
		String infor = "testok";
		int position = -1;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	@Test(priority = 9, description = "position为空")
	public void verifyInvalidPosition_004() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", null);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 10, description = "title为空")
	public void verifyInvalidTopicTitle_001() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", null);
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);


		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("topic.title不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 11, description = "title长度为32")
	public void verifyInvalidTopicTitle_002() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "test11111111111111111111111111111");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("topic.title长度为32位"));
	}

	// failed
	@Test(priority = 12, description = "body长度为400")
	public void verifyInvalidTopicBody_001() {
		String title = "章节2001";
		String infor = "test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test1111111111111111111111111111111112";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", infor);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("body长度为400位"));
	}

	@Test(priority = 13, description = "body为空")
	public void verifyInvalidTopicBody_002() {
		String title = "章节2001";
		String infor = "testok";
		int position = 20;

		JSONObject topicJson = new JSONObject();
		topicJson.put("title", "topic2001");
		topicJson.put("body", null);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("topic", topicJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/topic?loginUserId=" + userId + "&tenantId=1", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("保存课程topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("topic.body不能为空,"));
	}
}
