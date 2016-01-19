package org.gxb.server.api.classes.video;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

/*
 * 192.168.30.33:8080/gxb-api/classes/1/video/2/video_time_node?loginUserId=123&tenantId=1
 */
public class AddVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(AddVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int classId;
	private int videoId;
	private int loginUserId;
	private int tenantId;

	@BeforeMethod
	public void InitiaData() {
		classId = 130;
		videoId = 8;
		loginUserId = 4002;
		tenantId = 4002;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int timeNode = 10;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		int videoTimeNodeId = response.jsonPath().get("videoTimeNodeId");

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("videoTimeNodeId", equalTo(videoTimeNodeId))
				.body("classId", equalTo(classId)).body("userId", equalTo(loginUserId))
				.body("tenantId", equalTo(tenantId)).body("videoId", equalTo(videoId))
				.body("timeNode", equalTo(timeNode));
	}

	// failed
	@Test(priority = 2, description = "classes不存在")
	public void verifyNotExistClasses() {
		classId = 999999;
		int timeNode = 10;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo(""));
	}

	// failed
	@Test(priority = 3, description = "video不存在")
	public void verifyNotExistVideo() {
		videoId = 999999;
		int timeNode = 10;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo(""));
	}

	@Test(priority = 4, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		int timeNode = 10;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes/" + classId + "/video/" + videoId + "/video_time_node?loginUserId=&tenantId=" + tenantId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}

	@Test(priority = 5, description = "tenantId为空")
	public void verifyEmptyTenantId() {
		int timeNode = 10;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}

	@Test(priority = 6, description = "timeNode为空")
	public void verifyTimeNode_001() {
		int timeNode = 10;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", null);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("timeNode不能为null,"));
	}

	@Test(priority = 7, description = "timeNode无效")
	public void verifyTimeNode_002() {
		int timeNode = 10;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", "q1");

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 8, description = "timeNode为0")
	public void verifyTimeNode_003() {
		int timeNode = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("timeNode最小不能小于1,"));
	}
	
	@Test(priority = 9, description = "timeNode为负数")
	public void verifyTimeNode_004() {
		int timeNode = -1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增视频中测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("timeNode最小不能小于1,"));
	}
}
