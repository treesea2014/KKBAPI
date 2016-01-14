package org.gxb.server.api.classes.classes;

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
 * 192.168.30.33:8080/gxb-api/classes?loginUserId=123&tenantId=1
 */
public class AddClasses {
	private static Logger logger = LoggerFactory.getLogger(AddClasses.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int tenantId;
	private int userId;
	private int courseId;

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

		int classId = response.jsonPath().get("classId");

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("classId", equalTo(classId))
				.body("courseId", equalTo(courseId)).body("editorId", equalTo(userId))
				.body("tenantId", equalTo(tenantId)).body("className", equalTo("classes_test1001"))
				.body("intro", equalTo("intro_test1001")).body("classType", equalTo("10")).body("level", equalTo("10"))
				.body("useType", equalTo("10")).body("classInfo.classId", equalTo(classId))
				.body("classInfo.editorId", equalTo(userId)).body("classInfo.tenantId", equalTo(tenantId))
				.body("classInfo.description", equalTo("课程介绍_test1001"))
				.body("classCategories.classId", Matchers.hasItems(classId))
				.body("classCategories.userId", Matchers.hasItems(userId))
				.body("classCategories.tenantId", Matchers.hasItems(tenantId))
				.body("classCategories.categoryId", Matchers.hasItems(1))
				.body("classCategories.categoryName", Matchers.hasItems("categorie_test1001"))
				.body("classInstructors.classId", Matchers.hasItems(classId))
				.body("classInstructors.userId", Matchers.hasItems(userId))
				.body("classInstructors.tenantId", Matchers.hasItems(tenantId))
				.body("classInstructors.instructorId", Matchers.hasItems(1))
				.body("classInstructors.instructorName", Matchers.hasItems("测试讲师1"));
	}

	@Test(priority = 2, description = "无效的userid")
	public void verifyInvalidUserId_001() {
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

		Response response = TestConfig.postOrPutExecu("post", "/classes?loginUserId=" + "q1" + "&tenantId=" + tenantId,
				classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 3, description = "uersid为空")
	public void verifyInvalidUserId_002() {
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

		Response response = TestConfig.postOrPutExecu("post", "/classes?loginUserId=&tenantId=" + tenantId,
				classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}

	@Test(priority = 4, description = "无效的tenantid")
	public void verifyInvalidTenantId_001() {
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

		Response response = TestConfig.postOrPutExecu("post", "/classes?loginUserId=" + userId + "&tenantId=" + "q1",
				classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 5, description = "tenantid为空")
	public void verifyInvalidTenantId_002() {
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

		Response response = TestConfig.postOrPutExecu("post", "/classes?loginUserId=" + userId + "&tenantId=",
				classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}

	// failed
	@Test(priority = 6, description = "无效的usertype")
	public void verifyInvalidUserType_001() {
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
		classObject.put("useType", "1");
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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("此状态不存在"));
	}

	// pass
	@Test(priority = 7, description = "usertype为空")
	public void verifyInvalidUserType_002() {
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
		classObject.put("useType", null);
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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("useType不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 8, description = "无效的classtype")
	public void verifyInvalidClassType_001() {
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
		classObject.put("classType", "1");
		classObject.put("level", "10");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("无效的classtype"));
	}

	// failed
	@Test(priority = 9, description = "无效的classtype")
	public void verifyInvalidClassType_002() {
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
		classObject.put("classType", "q1");
		classObject.put("level", "10");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("无效的classtype"));
	}

	@Test(priority = 10, description = "classtype为空")
	public void verifyInvalidClassType_003() {
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
		classObject.put("classType", null);
		classObject.put("level", "10");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classType不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 11, description = "无效的level")
	public void verifyInvalidLevel_001() {
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
		classObject.put("level", "1");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("无效的classtype"));
	}

	// failed
	@Test(priority = 12, description = "无效的level")
	public void verifyInvalidLevel_002() {
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
		classObject.put("level", "q1");
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("无效的classtype"));
	}

	@Test(priority = 13, description = "level为空")
	public void verifyInvalidLevel_003() {
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
		classObject.put("level", null);
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		int classId = response.jsonPath().get("classId");
		int courseId = response.jsonPath().get("courseId");

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("classId", equalTo(classId))
				.body("courseId", equalTo(courseId)).body("editorId", equalTo(userId))
				.body("tenantId", equalTo(tenantId)).body("className", equalTo("classes_test1001"))
				.body("intro", equalTo("intro_test1001")).body("classType", equalTo("10")).body("level", equalTo("20"))
				.body("useType", equalTo("10")).body("classInfo.classId", equalTo(classId))
				.body("classInfo.editorId", equalTo(userId)).body("classInfo.tenantId", equalTo(tenantId))
				.body("classInfo.description", equalTo("课程介绍_test1001"))
				.body("classCategories.classId", Matchers.hasItems(classId))
				.body("classCategories.userId", Matchers.hasItems(userId))
				.body("classCategories.tenantId", Matchers.hasItems(tenantId))
				.body("classCategories.categoryId", Matchers.hasItems(1))
				.body("classCategories.categoryName", Matchers.hasItems("categorie_test1001"))
				.body("classInstructors.classId", Matchers.hasItems(classId))
				.body("classInstructors.userId", Matchers.hasItems(userId))
				.body("classInstructors.tenantId", Matchers.hasItems(tenantId))
				.body("classInstructors.instructorId", Matchers.hasItems(1))
				.body("classInstructors.instructorName", Matchers.hasItems("测试讲师1"));
	}

	@Test(priority = 14, description = "无效的courseId")
	public void verifyInvalidCourseId_001() {
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
		classObject.put("courseId", "q1");
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	@Test(priority = 15, description = "courseId不存在")
	public void verifyInvalidCourseId_002() {
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
		classObject.put("level", "q1");
		classObject.put("courseId", 999999);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		String paramUrl = path + basePath + "/classes?loginUserId=" + userId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, classObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("新增班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "没有当前班次", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 16, description = "courseId为空")
	public void verifyInvalidCourseId_003() {
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
		classObject.put("courseId", null);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("courseId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 17, description = "className为空")
	public void verifyInvalidClassName_001() {
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
		classObject.put("className", null);
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("courseName不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 18, description = "classname长度为32")
	public void verifyInvalidClassName_002() {
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
		classObject.put("className", "test11111111111111111111111111112");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("className字数限制在32字之内")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 19, description = "intro为空")
	public void verifyInvalidIntro_001() {
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
		classObject.put("intro", null);
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("intro不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	// failed
	@Test(priority = 20, description = "intro长度为64")
	public void verifyInvalidIntro_002() {
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
		classObject.put("intro", "test11111111111111111111111111112test1111111111111111111111111113");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("className字数限制在32字之内")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 21, description = "description为空")
	public void verifyInvalidDescription() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", null);

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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classInfo.description不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 22, description = "classInfo为空")
	public void verifyInvalidClassInfo() {
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
		classObject.put("classInfo", null);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classInfo不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 23, description = "categoryId为空")
	public void verifyInvalidCategoryId_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", null);
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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classCategories[0].categoryId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 24, description = "categoryId无效")
	public void verifyInvalidCategoryId_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "q1");
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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 25, description = "categoryName为空")
	public void verifyInvalidCategoryName_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", null);

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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classCategories[0].categoryName不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	//failed
	@Test(priority = 26, description = "categoryName长度为8")
	public void verifyInvalidCategoryName_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", "categorie");

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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("categoryName字数长度限制为8")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 27, description = "instructorId为空")
	public void verifyInvalidInstructorId_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", "categorie_test1001");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", null);
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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classInstructors[0].instructorId不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 28, description = "InstructorId无效")
	public void verifyInvalidInstructorId_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", "categorie_test1001");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "q1");
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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 29, description = "instructorName为空")
	public void verifyInvalidInstructorName_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", "test");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "1");
		instructorsObject.put("instructorName", null);

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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classInstructors[0].instructorName不能为空,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	//failed
	@Test(priority = 30, description = "InstructorName长度为1-15")
	public void verifyInvalidInstructorName_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", "categorie");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "1");
		instructorsObject.put("instructorName", "instructor_test1");

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

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("categoryName字数长度限制为15")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 31, description = "classCategories为空")
	public void verifyNullClassCategories() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", "test");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "1");
		instructorsObject.put("instructorName", "test");

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
		classObject.put("classCategories", null);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classCategories不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
	
	@Test(priority = 32, description = "classInstructors为空")
	public void verifyNullClassInstructors() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1001");	

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", 3);
		categoriesObject.put("categoryName", "test");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "1");
		instructorsObject.put("instructorName", "test");

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
		classObject.put("classInstructors", null);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + userId + "&tenantId=" + tenantId, classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("classInstructors不能为null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}
}
