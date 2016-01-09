package org.gxb.server.api.createcourse.courseware;

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
 * ----保存课件
 * http://192.168.30.33:8080/gxb-api/course/item/4/courseware?loginUserId=123456
 *  course_chapter,course_courseware,document,itemid从course_item表中取到
 */
public class AddCourseware {
	private static Logger logger = LoggerFactory.getLogger(AddCourseware.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int itemId;
	private int userId;

	@BeforeMethod
	public void InitiaData() {
		itemId = 200;
		userId = 2001;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("itemId", equalTo(itemId)).body("courseId", equalTo(2))
				.body("contentType", equalTo("Courseware")).body("title", equalTo(title))
				.body("position", equalTo(position)).body("editorId", equalTo(userId))
				.body("courseware.editorId", equalTo(userId)).body("courseware.title", equalTo(title))
				.body("courseware.courseId", equalTo(2)).body("courseware.documentList.documentId", Matchers.hasItems(documentId));
	}

	@Test(priority = 2, description = "itemId不存在")
	public void verifyNotExistItem() {
		itemId = 1;

		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		String paramUrl = 	path + basePath + "/course/item/" + itemId + "/courseware?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "item 不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "无效的itemid")
	public void verifyInvalidItemId() {
		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + "qw12" + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "无效的userid")
	public void verifyInvalidUserId() {
		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + "q12", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	// failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserId() {
		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=", chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 6, description = "title为空")
	public void verifyNullTitle() {
		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", null);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title不能为空,"));
	}

	// failed
	@Test(priority = 6, description = "title长度为32位")
	public void verifyTitleLength() {
		String title = "test11111111111111111111111111111";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title长度为32位"));
	}

	@Test(priority = 7, description = "position无效")
	public void verifyInvalidPosition_001() {
		String title = "test11";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", "q1");
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	// failed
	@Test(priority = 7, description = "position为0")
	public void verifyInvalidPosition_002() {
		String title = "test11";
		int documentId = 1001;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	// failed
	@Test(priority = 8, description = "position为负数")
	public void verifyInvalidPosition_003() {
		String title = "test11";
		int documentId = 1001;
		int position = -1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能小于1"));
	}

	@Test(priority = 9, description = "position为空")
	public void verifyInvalidPosition_004() {
		String title = "test11";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", null);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 10, description = "title为空")
	public void verifyInvalidCourseTitle_001() {
		String title = "test11";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", null);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("courseware.title不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 11, description = "title长度为32")
	public void verifyInvalidCourseTitle_002() {
		String title = "test11";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "test11111111111111111111111111111");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);


		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title长度为32位"));
	}
	

	@Test(priority = 12, description = "documentId为空")
	public void verifyInvalidDocumentId_001() {
		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", null);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId , chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("itemId", equalTo(itemId)).body("courseId", equalTo(2))
		.body("contentType", equalTo("Courseware")).body("title", equalTo(title))
		.body("position", equalTo(position)).body("editorId", equalTo(userId))
		.body("courseware.editorId", equalTo(userId)).body("courseware.title", equalTo(title))
		.body("courseware.courseId", equalTo(2));
	}

	@Test(priority = 13, description = "documentId无效")
	public void verifyInvalidDocumentId_002() {
		String title = "课件1001";
		int documentId = 1001;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", "qw1");

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 14, description = "documentId在document表中不存在")
	public void verifyInvalidDocumentId_003() {
		String title = "课件1001";
		int documentId = 1;
		int position = 11;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("documentId不存在"));
	}

}
