package org.gxb.server.api.lms.submission;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.course.quiz.AddQuiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateSubmission {
	private static Logger logger = LoggerFactory.getLogger(AddQuiz.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int tenantId = 1200;
	private int submissionId;

	@BeforeClass
	public void InitiaData() {
//		JSONObject assetListObject = new JSONObject();
//		assetListObject.put("title", "assignment_title_001");
//		assetListObject.put("link", "www.testing.com");
//
//		JSONArray assetListArray = new JSONArray();
//		assetListArray.add(assetListObject);
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("contextId", 3276);
//		jsonObject.put("contextType", "Course");
//		jsonObject.put("submittedId", 4230);
//		jsonObject.put("submittedType", "Assignment");
//		jsonObject.put("userId", 123456);
//		jsonObject.put("assetList", assetListArray);
//
//		Response response = TestConfig.postOrPutExecu("post", "/submission?tenantId=" + tenantId, jsonObject);
//		submissionId = response.jsonPath().get("submissionId");
		submissionId = 7480;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改 submission失败");
	}

	@Test(priority = 2, description = "contextId为空")
	public void verifyContextId_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", null);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 3, description = "contextId不存在")
	public void verifyContextId_002() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 999999);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 4, description = "contextType为空")
	public void verifyContextType_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", null);
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextType不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 5, description = "contextType为其他类型")
	public void verifyContextType_002() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Class");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextType不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 6, description = "contextType无效")
	public void verifyContextType_003() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course11");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextType不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 7, description = "submittedId为空")
	public void verifySubmittedId_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", null);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("submittedId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 8, description = "submittedId不存在")
	public void verifySubmittedId_002() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 1);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("submittedId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 9, description = "submittedType为空")
	public void verifySubmittedType_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", null);
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("submittedType不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 10, description = "submittedType无效")
	public void verifySubmittedType_002() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Page11");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("submittedType不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 11, description = "userId为空")
	public void verifyUserId_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", null);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("userId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 12, description = "userId不存在")
	public void verifyUserId_002() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("userId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 13, description = "assetList为空")
	public void verifyAssetList_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", null);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("assetList不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 14, description = "title为空")
	public void verifyTitle_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 15, description = "title长度为32")
	public void verifyTitle_002() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "test11111111111111111111111111112");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 16, description = "link为空")
	public void verifyLink_001() {
		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", null);

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/" + submissionId + "?tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改 submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("link不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 17, description = "submissionId不存在")
	public void verifySubmissionId() {
		submissionId = 10;

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("title", "assignment_title_001");
		assetListObject.put("link", "www.testing.com");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 4230);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 123456);
		jsonObject.put("assetList", assetListArray);

		String paramUrl = path + basePath + "/submission/" + submissionId + "?tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPut(paramUrl,jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改 submission接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "submission不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

}
