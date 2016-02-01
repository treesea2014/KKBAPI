package org.gxb.server.api.lms.submission;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.course.quiz.AddQuiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/submission/deleteAsset
 */
public class SubmissionDeleteAsset {
	private static Logger logger = LoggerFactory.getLogger(SubmissionDeleteAsset.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "submission/deleteAsset失败");
	}
	
	@Test(priority = 2, description = "再次删除")
	public void verifyDeletedParams() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 3, description = "submissionId与submittedId不一致")
	public void verifySubmissionId_001() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899313);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	//failed
	@Test(priority = 4, description = "submissionId为空")
	public void verifySubmissionId_002() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", null);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 5, description = "contextType为空")
	public void verifyContextType_002() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", null);
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 6, description = "submittedId和submissionId不一致")
	public void verifySubmittedId_001() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 1);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 7, description = "assetId与submissionId不一致")
	public void verifyAssetId_001() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616572);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 8, description = "submittedId为空")
	public void verifySubmittedId_002() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", null);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 9, description = "submittedType为空")
	public void verifySubmittedType() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", null);
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 10, description = "userId为空")
	public void verifyUserId() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", null);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 11, description = "assetId为空")
	public void verifyAssetId_002() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", null);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 12, description = "title为空")
	public void verifyTitle() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", null);
		assetListObject.put("link", "da7c77cd6f5b44fd9fd2b6d190352407.xls");

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
	
	@Test(priority = 13, description = "link为空")
	public void verifyLink() {

		JSONObject assetListObject = new JSONObject();
		assetListObject.put("assetId", 616575);
		assetListObject.put("title", "学生信息批量导入模板");
		assetListObject.put("link", null);

		JSONArray assetListArray = new JSONArray();
		assetListArray.add(assetListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("submissionId", 2899309);
		jsonObject.put("contextType", "Course");
		jsonObject.put("submittedId", 9020);
		jsonObject.put("submittedType", "Assignment");
		jsonObject.put("userId", 1239036);
		jsonObject.put("assetList", assetListArray);

		Response response = TestConfig.postOrPutExecu("post", "/submission/deleteAsset",
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("submission/deleteAsset接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "submission/deleteAsset失败");
	}
}
