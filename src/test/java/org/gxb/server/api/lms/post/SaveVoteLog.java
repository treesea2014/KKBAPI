package org.gxb.server.api.lms.post;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/voteLog
 */
public class SaveVoteLog {
	private static Logger logger = LoggerFactory.getLogger(SaveVoteLog.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int contextId = 675446;
		int userId = 1311;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		int voteLogId = response.jsonPath().get("voteLogId");

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("voteLogId", equalTo(voteLogId))
				.body("contextId", equalTo(contextId)).body("contextType", equalTo(contextType))
				.body("userId", equalTo(userId)).body("up", equalTo(up));
	}
	
	@Test(priority = 2, description = "contextId为空")
	public void verifyContextId_001() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", null);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextId不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 3, description = "contextId不存在")
	public void verifyContextId_002() {
		int contextId = 1;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextId不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 4, description = "contextId的delete_flag=0")
	public void verifyContextId_003() {
		int contextId = 11;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextId不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 5, description = "contextId无效")
	public void verifyContextId_004() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", "qw1");
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 6, description = "contextType为空")
	public void verifyContextType_001() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", null);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextType不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 6, description = "contextType为其他类型")
	public void verifyContextType_002() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Topic";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		String paramUrl = path + basePath + "/voteLog";
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("回复点赞接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "contextType 错误", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 7, description = "contextType无效的类型")
	public void verifyContextType_003() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post1";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		String paramUrl = path + basePath + "/voteLog";
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("回复点赞接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "contextType 错误", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 8, description = "userId为空")
	public void verifyUserId_001() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", null);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("userId不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 9, description = "userId无效")
	public void verifyUserId_002() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", "q123");
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 10, description = "userId不存在")
	public void verifyUserId_003() {
		int contextId = 675446;
		int userId = 1;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 11, description = "up为空")
	public void verifyUp_001() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("up不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 12, description = "up输入除0,1外的数据")
	public void verifyUp_002() {
		int contextId = 675446;
		int userId = 1310;
		int up = -1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("up不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 13, description = "up无效")
	public void verifyUp_003() {
		int contextId = 675446;
		int userId = 1310;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", "q1");

		Response response = TestConfig.postOrPutExecu("post", "/voteLog", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("回复点赞接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 14, description = "再次回复点赞")
	public void verifyVoteLog() {
		int contextId = 675446;
		int userId = 1311;
		int up = 1;
		String contextType = "Post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", contextId);
		jsonObject.put("contextType", contextType);
		jsonObject.put("userId", userId);
		jsonObject.put("up", up);

		String paramUrl = path + basePath + "/voteLog";
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("回复点赞接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "已经点过", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
}
