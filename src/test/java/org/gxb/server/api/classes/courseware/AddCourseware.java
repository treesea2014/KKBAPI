package org.gxb.server.api.classes.courseware;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 127.0.0.1:8080/gxb-api/classes/1/unit/5/item/8/chapter/courseware?loginUserId=123&tenantId=1
 * document(document_id),数据添加成功后，设计的表：class_chapter,class_courseware,class_document_relate
 */
public class AddCourseware {
	private static Logger logger = LoggerFactory.getLogger(AddCourseware.class);
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
		loginUserId = 3001;
		tenantId = 3001;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);

		int chapterId = response.jsonPath().get("chapterId");
		int contentId = response.jsonPath().get("contentId");
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("itemId", equalTo(itemId)).body("unitId", equalTo(unitId))
				.body("classId", equalTo(classId)).body("userId", equalTo(loginUserId))
				.body("tenantId", equalTo(tenantId)).body("chapterId", equalTo(chapterId))
				.body("contentId", equalTo(contentId)).body("contentType", equalTo(contentType))
				.body("title", equalTo(title)).body("position", equalTo(position))
				.body("classCourseware.coursewareId", equalTo(contentId)).body("classCourseware.title", equalTo(title))
				.body("classCourseware.classId", equalTo(classId)).body("classCourseware.userId", equalTo(loginUserId))
				.body("classCourseware.tenantId", equalTo(tenantId)).body("classCourseware.description", equalTo(desc))
				.body("classCourseware.documentList.documentId", Matchers.hasItems(documentId));
	}
	
	//failed
	@Test(priority = 2, description = "classes不存在")
	public void verifyNotExistClasses() {
		classId = 999999;
		
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		String paramUrl = 	path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "class不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	//failed
	@Test(priority = 3, description = "unit不存在")
	public void verifyNotExistUnit() {
		unitId = 999999;
		
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		String paramUrl = 	path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "unit不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 4, description = "item不存在")
	public void verifyNotExistItem() {
		itemId = 999999;
		
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		String paramUrl = 	path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "item不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 5, description = "unit不属于该class")
	public void verifyInvalidUnit() {
		unitId = 340;
		
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		String paramUrl = 	path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "item不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//faided
	@Test(priority = 6, description = "item不属于该unit")
	public void verifyInvalidItem_001() {
		itemId = 368;
		
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		String paramUrl = 	path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "item不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//faided
	@Test(priority = 7, description = "item不属于该class")
	public void verifyInvalidItem_002() {
		itemId = 435;
		
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		String paramUrl = 	path + basePath + "/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
				+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, chapterJson);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新建课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "item不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	
	@Test(priority = 8, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=&tenantId=" + tenantId,
						chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 9, description = "tenantId为空")
	public void verifyEmptyTenantId() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=",
						chapterJson);

		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 10, description = "contentType为空")
	public void verifyNullContentType() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", null);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);

		int chapterId = response.jsonPath().get("chapterId");
		int contentId = response.jsonPath().get("contentId");
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("itemId", equalTo(itemId)).body("unitId", equalTo(unitId))
				.body("classId", equalTo(classId)).body("userId", equalTo(loginUserId))
				.body("tenantId", equalTo(tenantId)).body("chapterId", equalTo(chapterId))
				.body("contentId", equalTo(contentId)).body("contentType", equalTo(contentType))
				.body("title", equalTo(title)).body("position", equalTo(position))
				.body("classCourseware.coursewareId", equalTo(contentId)).body("classCourseware.title", equalTo(title))
				.body("classCourseware.classId", equalTo(classId)).body("classCourseware.userId", equalTo(loginUserId))
				.body("classCourseware.tenantId", equalTo(tenantId)).body("classCourseware.description", equalTo(desc))
				.body("classCourseware.documentList.documentId", Matchers.hasItems(documentId));
	}
	
	@Test(priority = 11, description = "title为空")
	public void verifyTitle_001() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", null);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为空,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 12, description = "title长度为32")
	public void verifyTitle_002() {
		String title = "test11111111111111111111111111112";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为空,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 13, description = "position为空")
	public void verifyPosition_001() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", null);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("position不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 14, description = "position无效")
	public void verifyPosition_002() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", "q1");
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 15, description = "classCourseware为空")
	public void verifyNullClassCourseware() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", null);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classCourseware不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 16, description = "Courseware.title为空")
	public void verifyCoursewareTitle_001() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", null);
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classCourseware.title不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 17, description = "Courseware.title长度为32")
	public void verifyCoursewareTitle_002() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "test11111111111111111111111111112");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("title不能为空,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 18, description = "Courseware.description为空")
	public void verifyNullCoursewareDesc() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", null);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		
		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		int chapterId = response.jsonPath().get("chapterId");
		int contentId = response.jsonPath().get("contentId");
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("itemId", equalTo(itemId)).body("unitId", equalTo(unitId))
		.body("classId", equalTo(classId)).body("userId", equalTo(loginUserId))
		.body("tenantId", equalTo(tenantId)).body("chapterId", equalTo(chapterId))
		.body("contentId", equalTo(contentId)).body("contentType", equalTo(contentType))
		.body("title", equalTo(title)).body("position", equalTo(position))
		.body("classCourseware.coursewareId", equalTo(contentId)).body("classCourseware.title", equalTo(title))
		.body("classCourseware.classId", equalTo(classId)).body("classCourseware.userId", equalTo(loginUserId))
		.body("classCourseware.tenantId", equalTo(tenantId))
		.body("classCourseware.documentList.documentId", Matchers.hasItems(documentId));
	}
	
	@Test(priority = 19, description = "documentList为空")
	public void verifyNullDocumentList() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", null);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classCourseware.documentList不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 20, description = "documentId为空")
	public void verifyDocumentId_001() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", null);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("documentId不能为null,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	//failed
	@Test(priority = 21, description = "documentId在document中不存在")
	public void verifyDocumentId_002() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 1;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("documentId不存在,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 22, description = "documentId无效")
	public void verifyDocumentId_003() {
		String title = "课件_test1001";
		String contentType = "Courseware";
		String desc = "courseware_description_test1001";
		int documentId = 69;
		int position = 0;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", "q1");

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "courseware_title_test1001");
		coursewareJson.put("description", desc);
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("contentType", contentType);
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("classCourseware", coursewareJson);

		Response response = TestConfig
				.postOrPutExecu("post",
						"/classes/" + classId + "/unit/" + unitId + "/item/" + itemId
								+ "/chapter/courseware?loginUserId=" + loginUserId + "&tenantId=" + tenantId,
						chapterJson);
		
		if (response.getStatusCode() == 500) {
			logger.info("新建课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
}
