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
 * ----更改课件
 * http://192.168.30.33:8080/gxb-api/course/courseware/1?loginUserId=123456&tenantId=1
 *  course_chapter,course_courseware,document,itemid从course_item表中取到
 */
public class UpdateCourseware {
	private static Logger logger = LoggerFactory.getLogger(UpdateCourseware.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int coursewareId;
	private String userId;

	@BeforeMethod
	public void InitiaData() {
		coursewareId = 20;
		userId = "1020";
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("coursewareId", equalTo(coursewareId))
				.body("editorId", equalTo(Integer.parseInt(userId))).body("title", equalTo(title))
				.body("documentList.documentId", Matchers.hasItem(documentId));
	}

	@Test(priority = 2, description = "Courseware不存在")
	public void verifyNotExistCourseware() {
		coursewareId = 1;
		
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		String paramUrl = path + basePath + "/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1";
		System.out.println("======" + paramUrl);
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改测验接口##verifyNotExistQuiz##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课件不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "无效的Courseware")
	public void verifyInvalidCourseware() {
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + "q1" + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "无效的userid")
	public void verifyInvalidUserid() {
		userId = "qw1";
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	// failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserid() {
		userId = "";
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 6, description = "title为空")
	public void verifyEmptyTitle() {
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", null);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("title不能为空,"));
	}

	@Test(priority = 7, description = "title为空")
	public void verifyNullTitle() {
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("title不能为空,"));
	}

	@Test(priority = 8, description = "documentId为空")
	public void verifyEmptyDocumentId() {
		String title = "wtest1002";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", null);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("documentList不能为null,"));
	}

	@Test(priority = 9, description = "documentId为空")
	public void verifyNullDocumentId() {
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", null);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("coursewareId", equalTo(coursewareId))
				.body("title", equalTo(title)).body("editorId", equalTo(Integer.parseInt(userId)));
	}

	@Test(priority = 9, description = "documentId无效")
	public void verifyInvalidDocumentId() {
		String title = "wtest1002";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", "q1");
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	// failed
	@Test(priority = 10, description = "document表中documentId不存在")
	public void verifyNotExistDocumentId() {
		String title = "wtest1002";
		int documentId = 1;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("")).body("message",
				equalTo("questionid不存在"));
	}

	// failed
	@Test(priority = 11, description = "title为32位")
	public void verifyTitleLength() {
		String title = "te1111111111111111111111111111112";
		int documentId = 30;

		JSONArray jsonArray = new JSONArray();
		JSONObject documentList = new JSONObject();
		documentList.put("documentId", documentId);
		jsonArray.add(documentList);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("documentList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/courseware/" + coursewareId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title长度不能超出32位"));
	}
}
