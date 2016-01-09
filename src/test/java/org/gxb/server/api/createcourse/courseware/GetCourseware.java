package org.gxb.server.api.createcourse.courseware;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 根据id获取课件信息
 * http://192.168.30.33:8080/gxb-api/course/courseware/20?loginUserId=123456&tenantId=1
 *  course_chapter,course_courseware,document,itemid从course_item表中取到
 */
public class GetCourseware {
	private static Logger logger = LoggerFactory.getLogger(AddCourseware.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int itemId;
	private int userId;
	private int coursewareId;

	@BeforeMethod
	public void InitiaData() {
		itemId = 200;
		userId = 2001;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "课件-courseware-1001";
		int documentId = 36;
		int position = 3;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("documentId", documentId);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject coursewareJson = new JSONObject();
		coursewareJson.put("title", "web前端11");
		coursewareJson.put("documentList", jsonArray);

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("courseware", coursewareJson);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/item/" + itemId + "/courseware?loginUserId=" + userId, chapterJson);

		coursewareId = response.jsonPath().get("courseware.coursewareId");
		
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/courseware/" + coursewareId + "?loginUserId=123456&tenantId=1");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取课件信息##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("coursewareId", equalTo(coursewareId))
				.body("deleteFlag", equalTo(1)).body("courseId", equalTo(2)).body("editorId", equalTo(userId))
				.body("title", equalTo(title)).body("documentList.documentId", Matchers.hasItems(documentId)).body("documentList.courseId", Matchers.hasItems(1))
				.body("documentList.title", Matchers.hasItems("军事理论绪论文档")).body("documentList.position", Matchers.hasItems(2));

	}
	
	@Test(priority = 2, description = "courseware不存在")
	public void verifyNotExistCourseware() {
		coursewareId = 1;
				
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/courseware/" + coursewareId + "?loginUserId=123456&tenantId=1");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取课件信息##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "courseware无效")
	public void verifyInvalidCourseware() {
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/courseware/" + "q1" + "?loginUserId=123456&tenantId=1");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取课件信息##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
