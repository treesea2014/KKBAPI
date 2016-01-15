package org.gxb.server.api.classes.classes;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.course.assignment.UpdateAssignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateClassesValidate {
	private static Logger logger = LoggerFactory.getLogger(UpdateAssignment.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int classId;
	private int loginUserId;

	@BeforeClass
	public void InitiaData() {
		loginUserId = 2012;
		
		int courseId = 705;
		int tenantId = 1;
		
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1002");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "2");
		categoriesObject.put("categoryName", "Spring");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "30");
		instructorsObject.put("instructorName", "数据");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("useType", "20");
		classObject.put("classType", "20");
		classObject.put("level", "30");
		classObject.put("courseId", courseId);
		classObject.put("className", "className_test1002");
		classObject.put("intro", "intro_test1002");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		classId = response.jsonPath().get("classId");
	}
	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validateType", 40);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/validate?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次学习授权接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次学习授权失败");
	}
	
	@Test(priority = 2, description = "validateType为空")
	public void verifyValidateType_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validateType", null);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/validate?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次学习授权接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("validateType不能为空,"));
	}
	
	//failed
	@Test(priority = 3, description = "validateType输入其他数据")
	public void verifyValidateType_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validateType", 50);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/validate?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次学习授权接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("validateType不能为空,"));
	}
	
	//failed
	@Test(priority = 4, description = "validateType无效")
	public void verifyValidateType_003() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validateType", "q1");

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/validate?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次学习授权接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("validateType不能为空,"));
	}
	
	//failed
	@Test(priority = 5, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validateType", 30);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/validate?loginUserId=", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次学习授权接口##" + response.prettyPrint());
		}
		
		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}

	@Test(priority = 6, description = "正确的参数")
	public void verifyEmptyClassId() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validateType", 40);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/validate?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次学习授权接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("HttpRequestMethodNotSupportedException"));
	}
	
	//failed
	@Test(priority = 7, description = "正确的参数")
	public void verifyNotExistClassId() {	
		classId = 99999;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validateType", 40);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/validate?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次学习授权接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("classes不存在,"));
	}
}
