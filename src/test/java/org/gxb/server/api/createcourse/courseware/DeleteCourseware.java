package org.gxb.server.api.createcourse.courseware;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 删除课件信息
 * http://192.168.30.33:8080/gxb-api/course/courseware/1?loginUserId=123456&tenantId=1
 *  course_chapter,course_courseware,document,itemid从course_item表中取到
 */
public class DeleteCourseware {
	private static Logger logger = LoggerFactory.getLogger(ResourceBundle.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public int flag = 1;
	public int userId;
	public int coursewareId;
	private int itemId;

	@BeforeMethod
	public void InitiaData() {
		coursewareId = 16;
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

		//删除课件
		Response deleteResponse = TestConfig.getOrDeleteExecu("delete",
				"/course/courseware/" + coursewareId + "?loginUserId=123456&tenantId=1");

		if (deleteResponse.getStatusCode() == 500) {
			logger.info("删除课件接口##" + deleteResponse.prettyPrint());
		}

		deleteResponse.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(deleteResponse.prettyPrint()), true, "删除courseware失败");
		
		//再次删除课件
		String paramUrl = path + basePath  + "/course/courseware/"+ coursewareId + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课件不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 2, description = "coursewareId不存在")
	public void verifyNotExistCourseware() {	
		coursewareId = 1;
		
		String paramUrl = path + basePath  + "/course/courseware/"+ coursewareId + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除课件接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课件不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "无效的coursewareId")
	public void verifyInvalidCourseware() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/courseware/" + "q1" + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 4, description = "无效的userid")
	public void verifyInvalidUserId() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/courseware/" + coursewareId + "?loginUserId=q1&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	//failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserId() {
		coursewareId = 29;
				
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/courseware/" + coursewareId + "?loginUserId=&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("userid不能为空"));
	}
}
