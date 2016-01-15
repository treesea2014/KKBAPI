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

/*
 * 192.168.30.33:8080/gxb-api/class_set/1?loginUserId=123
 * class_set
 */
public class UpdateClassesSetInfor {
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
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次考核信息失败");
	}
	
	@Test(priority = 2, description = "offlinePercent为空")
	public void verifyOfflinePercent_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", null);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("offlinePercent不能为null,"));
	}
	
	@Test(priority = 3, description = "offlinePercent为0")
	public void verifyOfflinePercent_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 0);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		String url = path + basePath + "/class_set/"  + classId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改班次考核信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "线上线下总占比必须为100%", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 4, description = "offlinePercent为-1")
	public void verifyOfflinePercent_003() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", -1);
		jsonObject.put("onlinePercent", 101);
		jsonObject.put("videoPercent", 11);
		jsonObject.put("quizPercent", 50);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 5, description = "offlinePercent无效")
	public void verifyOfflinePercent_004() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", "q1");
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 6, description = "onlinePercent为空")
	public void verifyOnlinePercent_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", null);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("onlinePercent不能为null,"));
	}
	
	@Test(priority = 7, description = "onlinePercent为0")
	public void verifyOnlinePercent_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 0);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		String url = path + basePath + "/class_set/"  + classId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改班次考核信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "线上线下总占比必须为100%", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 8, description = "onlinePercent无效")
	public void verifyOnlinePercent_003() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", "q1");
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 9, description = "videoPercent为空")
	public void verifyVideoPercent_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", null);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("videoPercent不能为null,"));
	}
	
	@Test(priority = 10, description = "videoPercent无效")
	public void verifyVideoPercent_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", "q1");
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 11, description = "quizPercent为空")
	public void verifyQuizPercent_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", null);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("quizPercent不能为null,"));
	}
	
	@Test(priority = 12, description = "quizPercent无效")
	public void verifyQuizPercent_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", "q1");
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 13, description = "assignmentPercent为空")
	public void verifyAssignmentPercent_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", null);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("assignmentPercent不能为null,"));
	}
	
	@Test(priority = 14, description = "assignmentPercent无效")
	public void verifyAssignmentPercent_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", "q1");
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 15, description = "topicPercent为空")
	public void verifyTopicPercent_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", null);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("topicPercent不能为null,"));
	}
	
	@Test(priority = 16, description = "topicPercent无效")
	public void verifyTopicPercent_002() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", "q1");
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}


		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 17, description = "setInfo为空")
	public void verifySetInfo_001() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", null);

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("setInfo不能为null,"));
	}
	
	@Test(priority = 18, description = "视频、测验、作业、讨论相加的占比必须和线上占比相同")
	public void verifyTotalPercent() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 20);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "前台展示_test1001");

		String url = path + basePath + "/class_set/"  + classId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改班次考核信息接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "视频、测验、作业、讨论相加的占比必须和线上占比相同", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 19, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("offlinePercent", 40);
		jsonObject.put("onlinePercent", 60);
		jsonObject.put("videoPercent", 10);
		jsonObject.put("quizPercent", 10);
		jsonObject.put("assignmentPercent", 20);
		jsonObject.put("topicPercent", 20);
		jsonObject.put("setInfo", "setInfo_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"/class_set/" + classId + "?loginUserId=", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
