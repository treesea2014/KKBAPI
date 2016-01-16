package org.gxb.server.api.classes.classes;

import static org.hamcrest.Matchers.equalTo;
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
 * 192.168.30.33:8080/gxb-api/classes/110/basic
 */
public class GetClassesBasicInfor {
	private static Logger logger = LoggerFactory.getLogger(GetClassesBasicInfor.class);
	private int classId;
	private int loginUserId;

	@BeforeMethod
	public void InitiaData() {
		loginUserId = 2005;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int tenantId = 15;
		int courseId = 705;

		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1005");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "3");
		categoriesObject.put("categoryName", "MyBatis");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "100");
		instructorsObject.put("instructorName", "test_1005");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("useType", "30");
		classObject.put("classType", "10");
		classObject.put("level", "10");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1005");
		classObject.put("intro", "intro_test1005");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		classId = response.jsonPath().get("classId");

		Response getResponse = TestConfig.getOrDeleteExecu("get", "/classes/" + classId + "/basic");

		if (getResponse.getStatusCode() == 500) {
			logger.info("获取单个班次扩展信息接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("classId", equalTo(classId))
				.body("courseId", equalTo(courseId)).body("userId", equalTo(loginUserId))
				.body("editorId", equalTo(loginUserId)).body("tenantId", equalTo(tenantId))
				.body("className", equalTo("classes_test1005")).body("intro", equalTo("intro_test1005"))
				.body("classType", equalTo("10")).body("level", equalTo("10")).body("useType", equalTo("30"))
				.body("classInfo.description", equalTo("课程介绍_test1005"))
				.body("classCategories.categoryId", Matchers.hasItems(3))
				.body("classCategories.categoryName", Matchers.hasItems("MyBatis"))
				.body("classInstructors.instructorId", Matchers.hasItems(100))
				.body("classInstructors.instructorName", Matchers.hasItems("test_1005"));
	}

	@Test(priority = 2, description = "classes不存在")
	public void verifyNotExistClasses() {
		classId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classId + "/basic");

		if (response.getStatusCode() == 500) {
			logger.info("获取单个班次扩展信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "classes无效")
	public void verifyInvalidClasses() {
		classId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + "q1" + "/basic");

		if (response.getStatusCode() == 500) {
			logger.info("获取单个班次扩展信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
