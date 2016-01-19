package org.gxb.server.api.classes.video;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class UpdateVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(UpdateVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int videoTimeNodeId;
	private int loginUserId;

	@BeforeMethod
	public void InitiaData() {
		videoTimeNodeId = 30;
		loginUserId = 4010;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int timeNode = 30;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改视频时间点失败");
	}
	
	@Test(priority = 2, description = "videoTimeNodeId不存在")
	public void verifyVideoTimeNodeId_001() {
		videoTimeNodeId = 999999;
		int timeNode = 30;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "修改视频时间点失败");
	}

	//failed
	@Test(priority = 3, description = "videoTimeNodeId的deleteflag=0")
	public void verifyVideoTimeNodeId_002() {
		videoTimeNodeId = 2;
		int timeNode = 30;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "修改视频时间点失败");
	}
	
	//failed
	@Test(priority = 4, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		int timeNode = 30;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 5, description = "timeNode为空")
	public void verifyInvalidTimeNode_001() {
		int timeNode = 30;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", null);

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("timeNode不能为null,")).body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 6, description = "timeNode无效")
	public void verifyInvalidTimeNode_002() {
		int timeNode = 30;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", "qw1");

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 7, description = "timeNode为0")
	public void verifyInvalidTimeNode_003() {
		int timeNode = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("timeNode最小不能小于1,")).body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 8, description = "timeNode为负数")
	public void verifyInvalidTimeNode_004() {
		int timeNode = -1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("put", "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改视频时间点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("timeNode最小不能小于1,")).body("type", equalTo("MethodArgumentNotValidException"));
	}
}
