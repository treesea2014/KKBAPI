package org.gxb.server.api.classes.classes;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 192.168.30.33:8080/gxb-api/classes/110
 */
public class GetSingleClasses {
	private static Logger logger = LoggerFactory.getLogger(GetSingleClasses.class);
	private OperationTable operationTable = new OperationTable();
	private int classId;
	private int loginUserId;

	@BeforeMethod
	public void InitiaData() {
		loginUserId = 2002;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int tenantId = 12;
		int courseId = 705;

		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "4");
		categoriesObject.put("categoryName", "SpringMVC");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "85");
		instructorsObject.put("instructorName", "qaz");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("useType", "10");
		classObject.put("classType", "20");
		classObject.put("level", "30");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1004");
		classObject.put("intro", "intro_test1004");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		classId = response.jsonPath().get("classId");

		Response getResponse = TestConfig.getOrDeleteExecu("get", "/classes/" + classId);

		if (getResponse.getStatusCode() == 500) {
			logger.info("获取单个班次接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("classId", equalTo(classId))
				.body("courseId", equalTo(courseId)).body("userId", equalTo(loginUserId))
				.body("editorId", equalTo(loginUserId)).body("tenantId", equalTo(tenantId))
				.body("className", equalTo("classes_test1004")).body("intro", equalTo("intro_test1004"))
				.body("classType", equalTo("20")).body("level", equalTo("30")).body("useType", equalTo("10"))
				.body("status", equalTo("10"));
	}

	@Test(priority = 2, description = "classes不存在")
	public void verifyNotExistClasses() {
		classId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classId);

		if (response.getStatusCode() == 500) {
			logger.info("获取单个班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "classes无效")
	public void verifyInvalidClasses() {
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + "q1");

		if (response.getStatusCode() == 500) {
			logger.info("获取单个班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "classes为空")
	public void verifyEmptyClasses() {
		int expectedCount = 0;
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/");

		if (response.getStatusCode() == 500) {
			logger.info("获取单个班次接口##" + response.prettyPrint());
		}

		try {
			expectedCount = operationTable.selectClass(null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.then().assertThat().statusCode(200).body("paging.total", equalTo(expectedCount));
	}
}
