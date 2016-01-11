package org.gxb.server.api.course.video;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.course.topic.AddTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

/*
 * ----修改视频
 * http://192.168.30.33:8080/gxb-api/course/chapter/240/video?loginUserId=123456&tenantId=111
 * course_chapter video
 */
public class UpdateVideo {
	private static Logger logger = LoggerFactory.getLogger(UpdateVideo.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int chapterId;
	private int userId;

	@BeforeMethod
	public void InitiaData() {
		chapterId = 240;
		userId = 2400;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("chapterId", equalTo(chapterId)).body("title", equalTo(title))
				.body("position", equalTo(position)).body("editorId", equalTo(userId))
				.body("contentId", equalTo(contentId));
	}

	@Test(priority = 2, description = "Chapter不存在")
	public void verifyNotExistChapter() {
		chapterId = 1;

		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		String paramUrl = path + basePath + "/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111";
		String strMsg = httpRequest.sendHttpPut(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改视频接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "chapter不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "无效的Chapter")
	public void verifyInvalidChapter() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + "q1" + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "无效的userid")
	public void verifyInvalidUserId() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + "q1" + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	// failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserId() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 6, description = "title为空")
	public void verifyNullTitle() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", null);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title不能为空,"));
	}

	// failed
	@Test(priority = 6, description = "title长度为32位")
	public void verifyTitleLength() {
		String title = "test11111111111111111111111111111";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title长度为32位"));
	}

	@Test(priority = 7, description = "position无效")
	public void verifyInvalidPosition_001() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", "q1");
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	// failed
	@Test(priority = 7, description = "position为0")
	public void verifyInvalidPosition_002() {
		String title = "test_video_1001";
		int position = 0;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	// failed
	@Test(priority = 8, description = "position为负数")
	public void verifyInvalidPosition_003() {
		String title = "test_video_1001";
		int position = -1;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	@Test(priority = 9, description = "position为空")
	public void verifyInvalidPosition_004() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", null);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 10, description = "contentId无效")
	public void verifyInvalidContentid_001() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", "q1");

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	// failed
	@Test(priority = 11, description = "contentId在video中不存在")
	public void verifyInvalidContentid_002() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 999999;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("videoId不存在"));
	}

	@Test(priority = 12, description = "contentId为空")
	public void verifyInvalidContentid_003() {
		String title = "test_video_1001";
		int position = 11;
		int contentId = 5;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", null);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/chapter/" + chapterId + "/video?loginUserId=" + userId + "&tenantId=111", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("contentId不能为null,"));
	}
}
