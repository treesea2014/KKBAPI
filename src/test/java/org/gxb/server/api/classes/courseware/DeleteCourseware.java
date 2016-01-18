package org.gxb.server.api.classes.courseware;

import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DeleteCourseware {
	private static Logger logger = LoggerFactory.getLogger(DeleteCourseware.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int loginUserId;
	private int coursewareId;

	@BeforeClass
	public void InitiaData() {
		int classId = 130;
		int unitId = 468;
		int itemId = 438;
		loginUserId = 3001;
		int tenantId = 3001;

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

		coursewareId = response.jsonPath().get("classCourseware.coursewareId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {

		Response response = TestConfig.getOrDeleteExecu("delete","/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId);
		if (response.getStatusCode() == 500) {
			logger.info("删除courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改courseware失败");
		
		//再次删除
		String paramUrl = path + basePath  + "/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除courseware接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课件不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 2, description = "status=30")
	public void verifyCourseware_001() {
		coursewareId = 1;
		
		//再次删除
		String paramUrl = path + basePath  + "/class_courseware/" + coursewareId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除courseware接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "只有未发布的Chapter才能删除", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "coursewareId无效")
	public void verifyCourseware_002() {	
		Response response = TestConfig.getOrDeleteExecu("delete","/class_courseware/" + "q1" + "?loginUserId=" + loginUserId);
		if (response.getStatusCode() == 500) {
			logger.info("删除courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 4, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {	
		Response response = TestConfig.getOrDeleteExecu("delete","/class_courseware/" + coursewareId + "?loginUserId=");
		
		if (response.getStatusCode() == 500) {
			logger.info("删除courseware接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
