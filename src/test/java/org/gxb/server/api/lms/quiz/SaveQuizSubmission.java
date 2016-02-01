package org.gxb.server.api.lms.quiz;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/class_quiz/quiz/295/user?userId=931536
 */
public class SaveQuizSubmission {
	private static Logger logger = LoggerFactory.getLogger(SaveQuizSubmission.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int quizId;
	private int studentUserId;

	@BeforeMethod
	public void InitiaData() {
		quizId = 295;
		studentUserId = 931536;
	}
	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	@Test(priority = 2, description = "quizId不存在")
	public void verifyQuizId() {
		quizId = 1;
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		String paramUrl = path + basePath + "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("save quiz submission接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "测验不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "userId不存在")
	public void verifyUserId_001() {
		studentUserId = 999999;
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("不存在选课关系!"));
	}
	
	@Test(priority = 4, description = "userId属于其他的class")
	public void verifyUserId_002() {
		studentUserId = 1097217 ;
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("不存在选课关系!"));
	}
	
	@Test(priority = 5, description = "startTime为空")
	public void verifyStartTime_001() {
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", null);
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("时间参数错误!"));
	}
	
	@Test(priority = 6, description = "startTime无效")
	public void verifyStartTime_002() {
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", "123qwqwqzs");
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("时间参数错误!"));
	}
	
	//failed
	@Test(priority = 7, description = "answerData为空")
	public void verifyAnswerData_001() {
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", null);
		
		System.out.println("test====" + jsonObject);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("answerData不能为空"));
	}
	
	@Test(priority = 8, description = "question_id为空")
	public void verifyQuestionId_001() {
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", null);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("无效的参数,errorCode = 001!"));
	}
	
	@Test(priority = 9, description = "question_id无效")
	public void verifyQuestionId_002() {
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id","qw4623");
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("无效的参数,errorCode = 006!"));
	}
	
	//failed
	@Test(priority = 10, description = "text为空")
	public void verifyText_001() {
		
		JSONArray textArray = new JSONArray();
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("无效的参数,errorCode = 003!"));
	}
	
	@Test(priority = 11, description = "text无效")
	public void verifyText_002() {
		
		JSONArray textArray = new JSONArray();
		textArray.add("qw17938");
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 4623);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("无效的参数,errorCode = 0041!"));
	}
	
	@Test(priority = 12, description = "question_id与text不一致")
	public void verifyText_003() {
		
		JSONArray textArray = new JSONArray();
		textArray.add(17938);
		
		JSONObject answerDataObject = new JSONObject();
		answerDataObject.put("question_id", 115);
		answerDataObject.put("text", textArray);
		
		JSONArray answerDataArray = new JSONArray();
		answerDataArray.add(answerDataObject);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startTime", System.currentTimeMillis());
		jsonObject.put("answerData", answerDataArray);

		Response response = TestConfig.postOrPutExecu("post", "/class_quiz/quiz/" + quizId + "/user?userId=" + studentUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("save quiz submission接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("msg", equalTo("无效的参数,errorCode = 003!"));
	}
}
