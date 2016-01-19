package org.gxb.server.api.classes.video;

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

public class AddVideoTimeNodeQuestion {
	private static Logger logger = LoggerFactory.getLogger(AddVideo.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int videoTimeNodeId;
	private int loginUserId;

	@BeforeClass
	public void InitiaData() {
		int classId = 130;
		int videoId = 10;
		loginUserId = 4010;
		int tenantId = 4010;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", 30);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		videoTimeNodeId = response.jsonPath().get("videoTimeNodeId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int questionId1 = 397;
		int questionId2 = 398;
		
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("questionId", questionId1);
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questionId", questionId2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		Response response = TestConfig.postOrPutExecu("post", "/class_video_time_node/" + videoTimeNodeId + "/question?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("更新视频时间点下的测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "更新视频时间点下的测验失败");
	}
	
	@Test(priority = 2, description = "videoTimeNodeId不存在")
	public void verifyVideoTimeNodeId_001() {
		videoTimeNodeId = 999999;
		
		int questionId1 = 397;
		int questionId2 = 398;
		
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("questionId", questionId1);
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questionId", questionId2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		String url = path + basePath + "/class_video_time_node/" + videoTimeNodeId + "/question?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(url, jsonArray);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除courseware接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "视频的时间点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "videoTimeNodeId的delete_flag=0")
	public void verifyVideoTimeNodeId_002() {
		videoTimeNodeId = 2;
		
		int questionId1 = 397;
		int questionId2 = 398;
		
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("questionId", questionId1);
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questionId", questionId2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		String url = path + basePath + "/class_video_time_node/" + videoTimeNodeId + "/question?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(url, jsonArray);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除courseware接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "视频的时间点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		int questionId1 = 397;
		int questionId2 = 398;
		
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("questionId", questionId1);
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questionId", questionId2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		Response response = TestConfig.postOrPutExecu("post", "/class_video_time_node/" + videoTimeNodeId + "/question?loginUserId=", jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("更新视频时间点下的测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	//failed
	@Test(priority = 5, description = "questionId为空")
	public void verifyQuestionId_001() {
		int questionId1 = 397;
		int questionId2 = 398;
		
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("questionId", questionId1);
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questionId", questionId2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		Response response = TestConfig.postOrPutExecu("post", "/class_video_time_node/" + videoTimeNodeId + "/question?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("更新视频时间点下的测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("questionId不能为空"));
	}
	
	//failed
	@Test(priority = 6, description = "questionId在question表中不存在")
	public void verifyQuestionId_002() {
		int questionId1 = 1;
		int questionId2 = 398;
		
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("questionId", questionId1);
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questionId", questionId2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		Response response = TestConfig.postOrPutExecu("post", "/class_video_time_node/" + videoTimeNodeId + "/question?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("更新视频时间点下的测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("questionId不存在"));
	}
	
	@Test(priority = 7, description = "questionId无效")
	public void verifyQuestionId_003() {
		int questionId1 = 397;
		int questionId2 = 398;
		
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("questionId", "q1");
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("questionId", questionId2);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);

		Response response = TestConfig.postOrPutExecu("post", "/class_video_time_node/" + videoTimeNodeId + "/question?loginUserId=" + loginUserId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("更新视频时间点下的测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
}
