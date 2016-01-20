package org.gxb.server.api.classes.resource;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;

import org.apache.poi.hssf.record.formula.functions.Count;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.classes.video.AddVideo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/resources/class/168/document?loginUserId=123456&tenantId=1
 */
public class AddDocument {
	private static Logger logger = LoggerFactory.getLogger(AddVideo.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int classId;
	private int loginUserId;
	private int tenantId;
	private int courseId;

	@BeforeClass
	public void InitiaData() {
		courseId = 705;
		loginUserId = 5001;
		tenantId = 5001;

		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test5001");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "3");
		categoriesObject.put("categoryName", "MyBatis");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "240");
		instructorsObject.put("instructorName", "instructor_test5001");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("useType", "10");
		classObject.put("classType", "10");
		classObject.put("level", "10");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test5001");
		classObject.put("intro", "intro_test5001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		classId = response.jsonPath().get("classId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "文档_test1001";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("filePath", filePath);

		Response response = TestConfig.postOrPutExecu("post",
				"/resources/class/" + classId + "/document?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
				jsonObject);

		int documentId = response.jsonPath().get("documentId");
		if (response.getStatusCode() == 500) {
			logger.info("新增文档接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("documentId", equalTo(documentId))
				.body("courseId", equalTo(classId)).body("title", equalTo(title)).body("tenantId", equalTo(tenantId))
				.body("userId", equalTo(loginUserId)).body("filePath", equalTo(filePath));
	}
	
	@Test(priority = 2, description = "classId无效")
	public void verifyClassId_001() {
		classId = 130;
		
		String title = "文档_test1001";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("filePath", filePath);

		String url = path + basePath + "/resources/class/" + classId + "/document?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改教学模式信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "默认目录不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "classId不存在")
	public void verifyClassId_002() {
		classId = 999999;
		
		String title = "文档_test1001";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("filePath", filePath);

		String url = path + basePath + "/resources/class/" + classId + "/document?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改教学模式信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "班次不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 4, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		String title = "文档_test1001";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("filePath", filePath);

		Response response = TestConfig.postOrPutExecu("post",
				"/resources/class/" + classId + "/document?loginUserId=&tenantId=" + tenantId,
				jsonObject);

		int documentId = response.jsonPath().get("documentId");
		if (response.getStatusCode() == 500) {
			logger.info("新增文档接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	//failed
	@Test(priority = 5, description = "tenantId为空")
	public void verifyEmptyTenantId() {
		String title = "文档_test1001";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("filePath", filePath);

		Response response = TestConfig.postOrPutExecu("post",
				"/resources/class/" + classId + "/document?loginUserId=" + loginUserId + "&tenantId=",
				jsonObject);

		int documentId = response.jsonPath().get("documentId");
		if (response.getStatusCode() == 500) {
			logger.info("新增文档接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 6, description = "title为空")
	public void verifyTitle_001() {
		String title = "文档_test1001";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", null);
		jsonObject.put("filePath", filePath);

		Response response = TestConfig.postOrPutExecu("post",
				"/resources/class/" + classId + "/document?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增文档接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为空,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 7, description = "title为空")
	public void verifyTitle_002() {
		String title = "文档_test1001111111111111111113";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("filePath", filePath);

		Response response = TestConfig.postOrPutExecu("post",
				"/resources/class/" + classId + "/document?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增文档接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为空,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 8, description = "filePath为空")
	public void verifyFilePath() {
		String title = "文档_test1001";
		String filePath = "/users/user/document/test1001.pdf";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("filePath", null);

		Response response = TestConfig.postOrPutExecu("post",
				"/resources/class/" + classId + "/document?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增文档接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("filePath不能为空,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
}
