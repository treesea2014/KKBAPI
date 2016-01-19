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
import net.sf.json.JSONObject;

public class DeleteVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(DeleteVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int videoTimeNodeId;
	private int loginUserId;

	@BeforeClass
	public void InitiaData() {
		int classId = 130;
		int videoId = 8;
		loginUserId = 4020;
		int tenantId = 4020;

		int timeNode = 20;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timeNode", timeNode);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/video/" + videoId
				+ "/video_time_node?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		videoTimeNodeId = response.jsonPath().get("videoTimeNodeId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId);
		if (response.getStatusCode() == 500) {
			logger.info("删除视频下时间节点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "删除视频下时间节点失败");
	}

	@Test(priority = 2, description = "videoTimeNodeId已删除")
	public void verifyVideoTimeNodeId_001() {
		String paramUrl = path + basePath + "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除courseware接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "视频时间节点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "videoTimeNodeId不存在")
	public void verifyVideoTimeNodeId_002() {
		videoTimeNodeId = 999999;

		String paramUrl = path + basePath + "/class_video_time_node/" + videoTimeNodeId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除courseware接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "视频时间节点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 4, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/class_video_time_node/" + videoTimeNodeId + "?loginUserId=");
		if (response.getStatusCode() == 500) {
			logger.info("删除视频下时间节点接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
