package org.gxb.server.api.classes.classes;

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

/*
 * ----修改教学模式信息
 * http://192.168.30.33:8080/gxb-api/classes/1/teach_mode?loginUserId=123
 * class 
 */
public class UpdateTeachMode {
	private static Logger logger = LoggerFactory.getLogger(UpdateTeachMode.class);
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
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改教学模式信息失败");
	}
	
	@Test(priority = 2, description = "startAt为空")
	public void verifyInvalidStartAt_001() {		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", null);
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		String url = path + basePath + "/classes/"  + classId + "/teach_mode?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改教学模式信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "开课时间必填", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "startAt无效")
	public void verifyInvalidStartAt_002() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", "qw12");
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 4, description = "startAt为0")
	public void verifyInvalidStartAt_003() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", 0);
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 5, description = "startAt为负数")
	public void verifyInvalidStartAt_004() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", -1);
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 6, description = "concludeAt为空")
	public void verifyInvalidConcludeAt_001() {		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", null);
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		String url = path + basePath + "/classes/"  + classId + "/teach_mode?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改教学模式信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "结课时间必填", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 7, description = "concludeAt无效")
	public void verifyInvalidConcludeAt_002() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", "qw1");
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 8, description = "concludeAt为0")
	public void verifyInvalidConcludeAt_003() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", 0);
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 9, description = "concludeAt为负数")
	public void verifyInvalidConcludeAt_004() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", -1);
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 10, description = "teachMode为空")
	public void verifyInvalidTeachMode_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", null);
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("teachMode不能为空,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 11, description = "teachMode无效")
	public void verifyInvalidTeachMode_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "50");
		jsonObject.put("studyHour", 6);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400)
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 12, description = "studyHour无效")
	public void verifyInvalidStudyHour_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", null);

		String url = path + basePath + "/classes/"  + classId + "/teach_mode?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改教学模式信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课程学时必填", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 13, description = "studyHour无效")
	public void verifyInvalidStudyHour_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", "qw1");

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 14, description = "studyHour无效")
	public void verifyInvalidStudyHour_003() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 0);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException")).body("message", equalTo("studyHour大于0"));
	}
	
	//failed
	@Test(priority = 15, description = "studyHour无效")
	public void verifyInvalidStudyHour_004() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", -1);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException")).body("message", equalTo("studyHour大于0"));
	}
	
	//failed
	@Test(priority = 15, description = "studyHour输入小数")
	public void verifyInvalidStudyHour_005() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("concludeAt", String.valueOf(System.currentTimeMillis()));
		jsonObject.put("teachMode", "20");
		jsonObject.put("studyHour", 0.001);

		Response response = TestConfig.postOrPutExecu("put",
				"/classes/" + classId + "/teach_mode?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改教学模式信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException")).body("message", equalTo("studyHour大于0"));
	}
}
