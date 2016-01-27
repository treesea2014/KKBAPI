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
 * http://192.168.30.33:8080/gxb-api/report/post
 */
//failed
public class SaveReportPost {
	private static Logger logger = LoggerFactory.getLogger(SaveVoteLog.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "举报post失败");
	}
	
	@Test(priority = 2, description = "contextId为空")
	public void verifyContextId_001() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", null);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextId不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 3, description = "contextId不存在")
	public void verifyContextId_002() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 999999);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextId不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 4, description = "contextId无效")
	public void verifyContextId_003() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", "12qw");
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 5, description = "contextType为空")
	public void verifyContextType_001() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", null);
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextType不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 6, description = "contextType为class")
	public void verifyContextType_002() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Class");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextType不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 7, description = "contextType无效")
	public void verifyContextType_003() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course11");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contextType不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 8, description = "contentId为空")
	public void verifyContentId_001() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", null);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentId不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 9, description = "contentId不存在")
	public void verifyContentId_002() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 1);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentId不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 10, description = "contentId无效")
	public void verifyContentId_003() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", "qw12");
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 11, description = "contentType为空")
	public void verifyContentType_001() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", null);
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentType不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 12, description = "contentType为其他类型")
	public void verifyContentType_002() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Topic");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentType不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 13, description = "contentType无效")
	public void verifyContentType_003() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post11");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentType不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 14, description = "userId为空")
	public void verifyUserId_001() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", null);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("userId不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 14, description = "userId不存在")
	public void verifyUserId_002() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 1);
		jsonObject.put("reason", "reason_test1001");

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("userId不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 15, description = "reason为空")
	public void verifyReason_001() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contextId", 3276);
		jsonObject.put("contextType", "Course");
		jsonObject.put("contentId", 675447);
		jsonObject.put("contentType", "Post");
		jsonObject.put("user_id", 2345);
		jsonObject.put("reason", null);

		Response response = TestConfig.postOrPutExecu("post", "/report/post", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("举报post接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("reason不能为null,"))
		.body("type", equalTo("MethodArgumentNotValidException"));
	}
}
