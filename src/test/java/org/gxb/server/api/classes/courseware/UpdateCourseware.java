package org.gxb.server.api.classes.courseware;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateCourseware {
	private static Logger logger = LoggerFactory.getLogger(UpdateCourseware.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int loginUserId;
	private int coursewareId;

	@BeforeClass
	public void InitiaData() {
		int classId = 130;
		int unitId = 468;
		int itemId = 438;
		loginUserId = 3001;
		int tenantId = 3001;

		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);

		coursewareId = response.jsonPath().get("classCourseware.coursewareId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 70);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改courseware失败");
	}

	// failed
	@Test(priority = 2, description = "coursewareId不存在")
	public void verifyIvalidCoursewareId() {
		coursewareId = 999999;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 70);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		String url = path + basePath + "/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(url, coursewareJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改courseware接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "Courseware不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 70);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put", "/class_courseware/" + coursewareId + "?loginUserId=",
				coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}

	@Test(priority = 4, description = "title为空")
	public void verifyTitle_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 70);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", null);
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 5, description = "title长度为32")
	public void verifyTitle_002() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 70);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "test11111111111111111111111111112");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 6, description = "description为空")
	public void verifyNullDesc() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 70);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", null);
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改courseware失败");
	}

	@Test(priority = 7, description = "documentList为空")
	public void verifyNullDocumentList() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 70);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", null);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("documentList不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 8, description = "documentId为空")
	public void verifyDocumentId_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", null);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("documentId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 9, description = "document表中不存在该documentId")
	public void verifyDocumentId_002() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 10, description = "documentId无效")
	public void verifyDocumentId_003() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", "q1");

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", "test11");
		coursewareJson.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId, coursewareJson);

		if (response.getStatusCode() == 500) {
			logger.info("修改courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
}
