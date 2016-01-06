package org.gxb.server.api.createcourse;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * ----添加时间点
 * http://192.168.30.33:8080/gxb-api/course/videoTimeNode/1?loginUserId=123456
 * question,course_video_time_node,course_question_relate
 */
public class AddVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(AddVideoTimeNode.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private String videoTimeNode;
	private String loginUserId;
	private String questionId1;
	private String questionId2;
	private String contextType = "VideoTimeNode";

	@BeforeMethod
	public void InitiaData() {
		url = path + basePath + "/course/";
		videoTimeNode = "10";
		loginUserId = "10";
		questionId1 = "230";
		questionId2 = "231";
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray =  new JSONArray();
		
		jsonObject1.put("questionId", questionId1);
		jsonObject2.put("questionId", questionId2);
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		
		Response response = TestConfig.postOrPutExecu("post",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("时间点添加测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		try {
			int actualcount = operationTable.selectCourseQuestionRelate(videoTimeNode, contextType, questionId1, questionId2);
			Assert.assertEquals(2, actualcount, "course_question_relate查询数据不正确！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(priority = 2, description = "videoTimeNode不存在")
	public void verifyNotExistVideoTimeNode() {
		videoTimeNode = "1";
		
		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray =  new JSONArray();
		
		jsonObject1.put("questionId", questionId1);
		jsonObject2.put("questionId", questionId2);
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		String paramUrl = url + "videoTimeNode/" + videoTimeNode + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonArray);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "时间点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "无效的videoTimeNode")
	public void verifyInvalidVideo() {
		videoTimeNode = "qw12";
		
		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray =  new JSONArray();
		
		jsonObject1.put("questionId", questionId1);
		jsonObject2.put("questionId", questionId2);
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "videoTimeNode为空")
	public void verifyEmptyVideo() {
		videoTimeNode = "";
		
		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray =  new JSONArray();
		
		jsonObject1.put("questionId", questionId1);
		jsonObject2.put("questionId", questionId2);
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		

		Response response = TestConfig.postOrPutExecu("post",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("HttpRequestMethodNotSupportedException"));
	}
	
	//failed
	@Test(priority = 5, description = "QuestionId不存在")
	public void verifyNotExistQuestionId() {
		questionId1 = "1";		
		
		JSONObject jsonObject1 = new JSONObject();
		JSONArray jsonArray =  new JSONArray();		
		jsonObject1.put("questionId", questionId1);
		jsonArray.add(jsonObject1);

		String paramUrl = url + "videoTimeNode/" + videoTimeNode + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonArray);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "questionId1不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
		
		try {
			int actualcount = operationTable.selectCourseQuestionRelate(videoTimeNode, contextType, questionId1, questionId2);
			Assert.assertEquals(1, actualcount, "course_question_relate查询数据不正确！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test(priority = 6, description = "无效的QuestionId")
	public void verifyInvalidQuestionId() {
		questionId1 = "qw1";		

		JSONObject jsonObject1 = new JSONObject();
		JSONArray jsonArray =  new JSONArray();		
		jsonObject1.put("questionId", questionId1);
		jsonArray.add(jsonObject1);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	@Test(priority = 7, description = "QuestionId为空")
	public void verifyNullQuestionId() {		
		JSONObject jsonObject1 = new JSONObject();
		JSONArray jsonArray =  new JSONArray();		
		jsonObject1.put("questionId", null);
		jsonArray.add(jsonObject1);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyEmptyQuestionId##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "message不正确");
	}

}
