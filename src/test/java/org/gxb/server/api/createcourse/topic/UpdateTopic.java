package org.gxb.server.api.createcourse.topic;

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
import net.sf.json.JSONObject;

public class UpdateTopic {
	private static Logger logger = LoggerFactory.getLogger(UpdateTopic.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int topicId;
	private int userId;

	@BeforeMethod
	public void InitiaData() {
		topicId = 94;
		userId = 2211;
	}
	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "章节topic2001";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", infor);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + topicId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("topicId", equalTo(topicId)).body("title", equalTo(title))
				.body("body", equalTo(infor)).body("editorId", equalTo(userId));
	}

	@Test(priority = 2, description = "assignment不存在")
	public void verifyInvalidAssignment_001() {
		topicId = 1;
				
		String title = "章节topic2001";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", infor);


		String paramUrl = path + basePath  + "/course/topic/" + topicId + "?loginUserId=" + userId + "&tenantId=1";
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("更改topic接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课件不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "assignment无效")
	public void verifyInvalidAssignment_002() {
		String title = "章节topic2001";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", infor);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + "qw1" + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 4, description = "userid无效")
	public void verifyInvalidUserId_001() {				
		String title = "章节topic2001";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", infor);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + topicId + "?loginUserId=" + "qw1" + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	//failed
	@Test(priority = 5, description = "userid为空")
	public void verifyInvalidUserId_002() {				
		String title = "章节topic2001";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", infor);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + topicId + "?loginUserId=&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 6, description = "title为空")
	public void verifyInvalidTitle_001() {
		String title = "章节topic2001";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", null);
		jsonObject.put("body", infor);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + topicId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}


		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException")).body("message", equalTo("title不能为空,"));
	}
	
	//failed
	@Test(priority = 7, description = "title长度为32")
	public void verifyInvalidTitle_002() {
		String title = "test11111111111111111111111111111";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", infor);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + topicId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("title长度为32位"));
	}
	
	@Test(priority = 8, description = "body为空")
	public void verifyInvalidBody_001() {
		String title = "test11";
		String infor = "topic2001";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", null);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + topicId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("body不能为空,"));
	}
	
	//failed
	@Test(priority = 9, description = "body长度为400")
	public void verifyInvalidBody_002() {
		String title = "test11";
		String infor = "test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test11111111111111111111111111111test1111111111111111111111111111111112";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", title);
		jsonObject.put("body", infor);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/topic/" + topicId + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("更改topic接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("title长度为32位"));
	}
}
