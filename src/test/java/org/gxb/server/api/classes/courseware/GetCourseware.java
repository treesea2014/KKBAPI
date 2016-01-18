package org.gxb.server.api.classes.courseware;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetCourseware {
	private static Logger logger = LoggerFactory.getLogger(DeleteCourseware.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int loginUserId;
	private int coursewareId;

	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {	
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
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/class_courseware/" + coursewareId + "?loginUserId=12");
		if (getResponse.getStatusCode() == 500) {
			logger.info("获取courseware接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("coursewareId", equalTo(coursewareId))
		.body("userId", equalTo(loginUserId))
		.body("title", equalTo(title))
		.body("description", equalTo(desc))
		.body("documentList.documentId", Matchers.hasItems(documentId));
	}
	
	@Test(priority = 2, description = "coursewareId不存在")
	public void verifyNotExistCourseware() {	
		coursewareId = 999999;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/class_courseware/" + coursewareId + "?loginUserId=12");
		if (getResponse.getStatusCode() == 500) {
			logger.info("获取courseware接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "coursewareId无效")
	public void verifyInvalidCourseware() {	
	
		Response getResponse = TestConfig.getOrDeleteExecu("get","/class_courseware/" + "q1" + "?loginUserId=12");
		if (getResponse.getStatusCode() == 500) {
			logger.info("获取courseware接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
