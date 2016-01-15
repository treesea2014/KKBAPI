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

//failed
public class UpdateClassesExtendInfo {
	private static Logger logger = LoggerFactory.getLogger(AddClassesInfor.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int classId;
	private int courseId;
	private int loginUserId;
	private int tenantId;
	

	@BeforeMethod
	public void InitiaData() {
		tenantId = 12;
		loginUserId = 2001;
		courseId = 705;
		
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
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("post",
				"/classes?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

	    classId = response.jsonPath().get("classId");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次扩展信息失败");
	}
	
	@Test(priority = 2, description = "loginUserId为空")
	public void verifyInvalidUserId_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=&tenantId=" + tenantId, classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 3, description = "loginUserId无效")
	public void verifyInvalidUserId_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=" + "qw1" + "&tenantId=" + tenantId, classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 4, description = "tenantId为空")
	public void verifyInvalidTenantId_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=" + loginUserId + "&tenantId=", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 5, description = "tenantId无效")
	public void verifyInvalidTenantId_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=" + loginUserId + "&tenantId=qw1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	//failed
	@Test(priority = 6, description = "courseid为空")
	public void verifyInvalidCourseId_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", null);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"))
		.body("message", equalTo("courseId不能为空"));
	}
	
	//failed
	@Test(priority = 7, description = "courseid不存在")
	public void verifyInvalidCourseId_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", 999999);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 8, description = "courseid无效")
	public void verifyInvalidCourseId_003() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", "qw1");
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=" + loginUserId + "&tenantId=" + tenantId, classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 8, description = "className为空")
	public void verifyInvalidClassName_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", null);
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 9, description = "classname长度超出32")
	public void verifyInvalidClassName_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "test11111111111111111111111111112");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 10, description = "intro为空")
	public void verifyInvalidIntro_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", null);
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 11, description = "intro长度为64")
	public void verifyInvalidIntro_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "test11111111111111111111111111112test1111111111111111111111111113");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 12, description = "creditScore为空")
	public void verifyInvalidCreditScore_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", null);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 13, description = "creditScore无效")
	public void verifyInvalidCreditScore_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", "qw1");
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 14, description = "creditScore为负数")
	public void verifyInvalidCreditScore_003() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", -1);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 15, description = "creditScore为0")
	public void verifyInvalidCreditScore_004() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 0);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 16, description = "classType为空")
	public void verifyInvalidClassType_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", null);
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 17, description = "classType无效")
	public void verifyInvalidClassType_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "50");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 18, description = "classType无效")
	public void verifyInvalidClassType_003() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "q20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 19, description = "userType为空")
	public void verifyInvalidUserType_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", null);
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 20, description = "userType无效")
	public void verifyInvalidUserType_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "50");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 21, description = "userType无效")
	public void verifyInvalidUserType_003() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "q10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 21, description = "classInfo为空")
	public void verifyNullClassInfo() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", null);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 22, description = "classCategories为空")
	public void verifyNullClassCategories() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", null);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 23, description = "classInstructors为空")
	public void verifyNullClassInstructors() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", null);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 24, description = "description为空")
	public void verifyInvalidDescription() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", null);
		classInfoObject.put("teachPlan", "班次计划_test1003");

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 25, description = "teachPlan为空")
	public void verifyInvalidTeachPlan() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", null);

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
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 26, description = "categoryId为空")
	public void verifyInvalidCategoryId_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", null);
		categoriesObject.put("categoryName", "SpringMVC");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "85");
		instructorsObject.put("instructorName", "qaz");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 27, description = "categoryId无效")
	public void verifyInvalidCategoryId_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "q4");
		categoriesObject.put("categoryName", "SpringMVC");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "85");
		instructorsObject.put("instructorName", "qaz");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 28, description = "categoryName为空")
	public void verifyInvalidCategoryName_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "4");
		categoriesObject.put("categoryName", null);

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "85");
		instructorsObject.put("instructorName", "qaz");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 29, description = "categoryName长度为8")
	public void verifyInvalidCategoryName_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "4");
		categoriesObject.put("categoryName", "categorie");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "85");
		instructorsObject.put("instructorName", "qaz");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 30, description = "instructorId为空")
	public void verifyInvalidInstructorId_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "4");
		categoriesObject.put("categoryName", "SpringMVC");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", null);
		instructorsObject.put("instructorName", "qaz");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 31, description = "instructorId无效")
	public void verifyInvalidInstructorId_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "4");
		categoriesObject.put("categoryName", "SpringMVC");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "q85");
		instructorsObject.put("instructorName", "qaz");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 32, description = "instructorName为空")
	public void verifyInvalidInstructorName_001() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "4");
		categoriesObject.put("categoryName", "SpringMVC");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "85");
		instructorsObject.put("instructorName", null);

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 33, description = "InstructorName长度为1-15")
	public void verifyInvalidInstructorName_002() {
		JSONObject classInfoObject = new JSONObject();
		classInfoObject.put("description", "课程介绍_test1003");
		classInfoObject.put("teachPlan", "班次计划_test1003");

		JSONObject categoriesObject = new JSONObject();
		categoriesObject.put("categoryId", "4");
		categoriesObject.put("categoryName", "SpringMVC");

		JSONArray categoriesArray = new JSONArray();
		categoriesArray.add(categoriesObject);

		JSONObject instructorsObject = new JSONObject();
		instructorsObject.put("instructorId", "85");
		instructorsObject.put("instructorName", "instructor_test1");

		JSONArray instructorsArray = new JSONArray();
		instructorsArray.add(instructorsObject);

		JSONObject classObject = new JSONObject();
		classObject.put("courseId", courseId);
		classObject.put("className", "classes_test1003");
		classObject.put("intro", "intro_test1003");
		classObject.put("creditScore", 80.5);
		classObject.put("classType", "20");
		classObject.put("useType", "10");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);

		Response response = TestConfig.postOrPutExecu("put",
				"classes/" + classId + "/class_extend_info?loginUserId=1&tenantId=1", classObject);

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
}
