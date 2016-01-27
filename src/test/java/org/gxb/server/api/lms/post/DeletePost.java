package org.gxb.server.api.lms.post;

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

public class DeletePost {
	private static Logger logger = LoggerFactory.getLogger(DeletePost.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public int postId;


	@BeforeClass
	public void InitiaData() {
		int topicId = 130;
		int userId = 1300;
		String message = "test_post";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topicId", topicId);
		jsonObject.put("userId", userId);
		jsonObject.put("message", message);

		Response response = TestConfig.postOrPutExecu("post", "/post", jsonObject);

		postId = response.jsonPath().get("postId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {

		Response response = TestConfig.getOrDeleteExecu("delete",
				"/post/" + postId);

		if (response.getStatusCode() == 500) {
			logger.info("删除回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "删除回复失败");
	}
	
	//failed
	@Test(priority = 2, description = "postId已删除")
	public void verifyPostId_001() {

		Response response = TestConfig.getOrDeleteExecu("delete",
				"/post/" + postId);

		if (response.getStatusCode() == 500) {
			logger.info("删除回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(1000);
	}
	
	//failed
	@Test(priority = 3, description = "postId不存在")
	public void verifyPostId_002() {
		postId = 1;
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/post/" + postId);

		if (response.getStatusCode() == 500) {
			logger.info("删除回复接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(1000);
	}
}
