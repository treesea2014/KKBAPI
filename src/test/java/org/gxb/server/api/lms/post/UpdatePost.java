package org.gxb.server.api.lms.post;

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

//failed
public class UpdatePost {
	private static Logger logger = LoggerFactory.getLogger(UpdatePost.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int postId;

	@BeforeClass
	public void InitiaData() {
		int topicId = 130;
		int userId = 1300;
		String message = "test_post_1300";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("post", "/post", jsonObject);

		postId = response.jsonPath().get("postId");
	}
	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int topicId = 135;
		int userId = 1305;
		String message = "test_post_1305";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("put", "/post/" + postId, jsonObject);

		int postId = response.jsonPath().get("postId");

		if (response.getStatusCode() == 500) {
			logger.info("修改回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("postId", equalTo(postId)).body("topicId", equalTo(topicId))
				.body("userId", equalTo(userId)).body("tenantId", equalTo(1)).body("message", equalTo(message))
				.body("classId", equalTo(21));
	}

	@Test(priority = 2, description = "topicId为空")
	public void verifyTopicId_001() {
		int topicId = 130;
		int userId = 1300;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", null);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("put", "/post/" + postId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("topicId不能为null,"));
	}

	@Test(priority = 3, description = "topicId不存在")
	public void verifyTopicId_002() {
		int topicId = 1;
		int userId = 1300;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		String paramUrl = path + basePath + "/post/" + postId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("修改回复接口接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "topic不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 4, description = "userId为空")
	public void verifyUserId_001() {
		int topicId = 130;
		int userId = 1300;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", null);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("put", "/post/" + postId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("userId不能为null,"));
	}

	//failed
	@Test(priority = 5, description = "userId不存在")
	public void verifyUserId_002() {
		int topicId = 130;
		int userId = 1;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("put", "/post/" + postId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("topicId不能为null,"));
	}
	
	@Test(priority = 6, description = "message为空")
	public void verifyMessage_001() {
		int topicId = 131;
		int userId = 1301;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", null);

		Response response = TestConfig.postOrPutExecu("put", "/post/" + postId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("message不能为null,"));
	}
	
	//failed
	@Test(priority = 7, description = "postId不存在")
	public void verifyPostId_001() {
		postId = 999999;
		
		int topicId = 131;
		int userId = 1301;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("put", "/post/" + postId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("回复不存在"));
	}
	
	//failed
	@Test(priority = 8, description = "postId已结回复过此帖")
	public void verifyPostId_002() {
		postId = 386786;
		
		int topicId = 131;
		int userId = 1301;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("put", "/post/" + postId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("回复的回复不能修改"));
	}
}
