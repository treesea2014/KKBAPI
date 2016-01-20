package org.gxb.server.api.classes.resource;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.classes.video.AddVideo;
import org.hamcrest.Matchers;
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
public class AddQuestion {
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

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		int questionId = response.jsonPath().get("questionId");
		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("questionId", equalTo(questionId))
				.body("courseId", equalTo(classId)).body("userId", equalTo(loginUserId))
				.body("questionName", equalTo("题目_1001")).body("questionType", equalTo("multiple_choice"))
				.body("comment", equalTo("test1111")).body("level", equalTo("lower"))
				.body("optionList.questionId", Matchers.hasItems(questionId));
	}

	@Test(priority = 2, description = "classId无效")
	public void verifyClassId_001() {
		classId = 130;

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		String url = path + basePath + "/class/" + classId + "/question?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新增测验题接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "默认目录不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "classId不存在")
	public void verifyClassId_002() {
		classId = 999999;

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		String url = path + basePath + "/class/" + classId + "/question?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新增测验题接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "班次不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 4, description = "classId的status = 40")
	public void verifyClassId_003() {
		classId = 3;

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		String url = path + basePath + "/class/" + classId + "/question?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(url, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新增测验题接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "班次不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 5, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 6, description = "questionName为空")
	public void verifyQuestionName_001() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", null);
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("questionName不能为空,"));
	}
	
	//failed
	@Test(priority = 7, description = "questionName长度为32")
	public void verifyQuestionName_002() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "文档_test1001111111111111111113");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("questionName不能为空,"));
	}
	
	@Test(priority = 8, description = "questionType为空")
	public void verifyQuestionType_001() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", null);
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("questionType不能为空,"));
	}
	
	//failed
	@Test(priority = 9, description = "questionType为其他类型")
	public void verifyQuestionType_002() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("questionName不能为空,"));
	}
	
	@Test(priority = 10, description = "comment为空")
	public void verifyNullComment() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", null);
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("comment不能为空,"));
	}
	
	//failed
	@Test(priority = 11, description = "level为空")
	public void verifyLevel_001() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", null);
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("level不能为空,"));
	}
	
	@Test(priority = 12, description = "optionList为空")
	public void verifyOptionList() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", null);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("optionList不能为null,"));
	}
	
	//failed                                                                                       
	@Test(priority = 13, description = "content为空")
	public void verifyOptionContent() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", null);
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("optionList.content不能为空,"));
	}
	
	//failed
	@Test(priority = 14, description = "comment为空")
	public void verifyOptionComment() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", null);
		optionListObject.put("correct", 1);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("optionList.comment不能为空,"));
	}
	
	//failed
	@Test(priority = 15, description = "correct为空")
	public void verifyOptionCorrect1() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", null);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("optionList.correct不能为空,"));
	}
	
	//failed
	@Test(priority = 16, description = "correct输入除(0,1)外")
	public void verifyOptionCorrect2() {

		JSONObject optionListObject = new JSONObject();
		optionListObject.put("content", "A 123");
		optionListObject.put("comment", "A");
		optionListObject.put("correct", 2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(optionListObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("questionName", "题目_1001");
		jsonObject.put("questionType", "multiple_choice");
		jsonObject.put("comment", "test1111");
		jsonObject.put("level", "lower");
		jsonObject.put("optionList", jsonArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/class/" + classId + "/question?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增测验题接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("optionList.correct不能为空,"));
	}
}
