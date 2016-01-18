package org.gxb.server.api.classes.video;

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

/*
 * 192.168.30.33:8080/gxb-api/classes/1/unit/5/item/8/chapter/video?loginUserId=123&tenantId=1
 */
public class AddVideo {
	private static Logger logger = LoggerFactory.getLogger(AddVideo.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int classId;
	private int unitId;
	private int itemId;
	private int loginUserId;
	private int tenantId;

	@BeforeMethod
	public void InitiaData() {
		classId = 130;
		unitId = 468;
		itemId = 438;
		loginUserId = 4001;
		tenantId = 4001;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		int chapterId = response.jsonPath().get("chapterId");

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("chapterId", equalTo(chapterId))
				.body("itemId", equalTo(itemId)).body("unitId", equalTo(unitId)).body("classId", equalTo(classId))
				.body("userId", equalTo(loginUserId)).body("contentId", equalTo(contentId))
				.body("contentType", equalTo(contentType)).body("title", equalTo(title))
				.body("position", equalTo(position));
	}

	@Test(priority = 2, description = "contentType为空")
	public void verifyContentType_001() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", null);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentType不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 3, description = "contentType为其他类型")
	public void verifyContentType_002() {
		String title = "video_title_test1001";
		String contentType = "Quiz";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		String paramUrl = path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建video接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "chapter的类型异常, ContentType : " + contentType + "",
				"message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 4, description = "contentType无效类型")
	public void verifyContentType_003() {
		String title = "video_title_test1001";
		String contentType = "Video1";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		String paramUrl = path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建video接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "chapter的类型异常, ContentType : " + contentType + "",
				"message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 5, description = "contentId为空")
	public void verifyContentId_001() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", null);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 6, description = "contentId在video表中不存在")
	public void verifyContentId_002() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 9999;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentId不存在,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 7, description = "contentId无效")
	public void verifyContentId_003() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", "q1");
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	@Test(priority = 8, description = "title为空")
	public void verifyTitle_001() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", null);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	//failed
	@Test(priority = 9, description = "title长度为32")
	public void verifyTitle_002() {
		String title = "test11111111111111111111111111112";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("contentType不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 10, description = "position为空")
	public void verifyPosition_001() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", null);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 11, description = "position无效")
	public void verifyPosition_002() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", "q1");

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type",
				equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 12, description = "classes不存在")
	public void verifyInvalidClasses() {
		classId = 999999;
		
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		int chapterId = response.jsonPath().get("chapterId");

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("chapterId", equalTo(chapterId))
				.body("itemId", equalTo(itemId)).body("unitId", equalTo(unitId)).body("classId", equalTo(classId))
				.body("userId", equalTo(loginUserId)).body("contentId", equalTo(contentId))
				.body("contentType", equalTo(contentType)).body("title", equalTo(title))
				.body("position", equalTo(position));
	}
	
	//failed
	@Test(priority = 13, description = "unit不存在")
	public void verifyInvalidUnit() {
		unitId = 999999;
		
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		int chapterId = response.jsonPath().get("chapterId");

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("chapterId", equalTo(chapterId))
				.body("itemId", equalTo(itemId)).body("unitId", equalTo(unitId)).body("classId", equalTo(classId))
				.body("userId", equalTo(loginUserId)).body("contentId", equalTo(contentId))
				.body("contentType", equalTo(contentType)).body("title", equalTo(title))
				.body("position", equalTo(position));
	}
	
	//failed
	@Test(priority = 14, description = "item不存在")
	public void verifyInvalidItem() {
		itemId = 999999;
		
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}


		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 15, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=&tenantId=" + tenantId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 16, description = "tenantId为空")
	public void verifyEmptyTenantId() {
		String title = "video_title_test1001";
		String contentType = "Video";
		int contentId = 10;
		int position = 1;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contentType", contentType);
		jsonObject.put("contentId", contentId);
		jsonObject.put("title", title);
		jsonObject.put("position", position);

		Response response = TestConfig.postOrPutExecu("post", "/classes/" + classId + "/unit/" + unitId + "/item/"
				+ itemId + "/chapter/video?loginUserId=" + loginUserId + "&tenantId=", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("新建video接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
