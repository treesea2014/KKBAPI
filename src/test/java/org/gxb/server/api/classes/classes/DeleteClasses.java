package org.gxb.server.api.classes.classes;

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
 * 删除班次
 * http://192.168.30.33:8080/gxb-api/classes/1/?loginUserId=123
 * classes
 */
public class DeleteClasses {
	private static Logger logger = LoggerFactory.getLogger(DeleteClasses.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int tenantId;
	private int userId;
	private int courseId;
	private int classId;

	@BeforeMethod
	public void InitiaData() {
		tenantId = 1;
		userId = 1001;
		courseId = 705;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {		
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "1");
		categoriesObject.put("categoryName", "categorie_test1001");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "1");
		instructorsObject.put("instructorName", "instructor_test1001");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("useType", "10");
		classObject.put("classType", "10");
		classObject.put("level", "10");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		classId = response.jsonPath().get("classId");
		
		//删除班次
		Response deleteResponse = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classId + "?loginUserId=" + userId);

		if (deleteResponse.getStatusCode() == 500) {
			logger.info("删除班次接口##" + response.prettyPrint());
		}

		deleteResponse.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(deleteResponse.prettyPrint()), true, "删除课程删除失败");
		
		//删除已删除的班次
		String paramUrl = path + basePath + "/classes/"  + classId + "?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "不存在的class", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	
	@Test(priority = 2, description = "course不存在")
	public void verifyNotExistCourse() {
		classId = 999999;
		
		String paramUrl = path + basePath + "/classes/"  + classId + "?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "不存在的class", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "无效的classes")
	public void verifyInvalidCourse() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + "q1" + "?loginUserId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("删除班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	
	@Test(priority = 4, description = "已发布状态")
	public void verifyPublishedClass() {	
		classId = 46;
		
		String paramUrl = path + basePath + "/classes/"  + classId + "?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "只有未发布的内容才能删除", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 5, description = "已关闭状态")
	public void verifyClosedClass() {	
		classId = 3;
		
		String paramUrl = path + basePath + "/classes/"  + classId + "?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "只有未发布的内容才能删除", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 6, description = "loginuserid为空")
	public void verifyEmptyUserId() {	
		classId = 10;
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classId + "?loginUserId=");

		if (response.getStatusCode() == 500) {
			logger.info("删除班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
