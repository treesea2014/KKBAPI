package org.gxb.server.api.classes.classes;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*
 * 192.168.30.33:8080/gxb-api/class_info/1?loginUserId=123
 * 修改班次附加信息
 */
public class UpdateClassesInfor {
	private static Logger logger = LoggerFactory.getLogger(UpdateClassesInfor.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int classId;
	private int loginUserId;
	

	@BeforeClass
	public void InitiaData() {
		int tenantId = 12;
		loginUserId = 2002;
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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", "班次封面_test1001");
		jsonObject.put("promotionalVideo", "班次宣传视频_test1001");
		jsonObject.put("promotionalVideoSrt", "班次宣传视频字幕_test1001");
		jsonObject.put("teachPlan", "教学安排_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"class_info/" + classId + "?loginUserId=" + loginUserId , jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("修改班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次附加信息失败");
	}
	
	@Test(priority = 2, description = "classid无效")
	public void verifyClassId_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", "班次封面_test1001");
		jsonObject.put("promotionalVideo", "班次宣传视频_test1001");
		jsonObject.put("promotionalVideoSrt", "班次宣传视频字幕_test1001");
		jsonObject.put("teachPlan", "教学安排_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"class_info/q1?loginUserId=" + loginUserId , jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("修改班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 3, description = "classid不存在")
	public void verifyClassId_002() {
		classId = 999999;
				
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", "班次封面_test1001");
		jsonObject.put("promotionalVideo", "班次宣传视频_test1001");
		jsonObject.put("promotionalVideoSrt", "班次宣传视频字幕_test1001");
		jsonObject.put("teachPlan", "教学安排_test1001");

		String paramUrl = path + basePath + "/class_info/" + classId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("修改班次附加信息##" + strMsg);
		}
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "班次附加信息不存在", "message提示信息不正确");
	}
	
	//failed
	@Test(priority = 4, description = "loginUserId为空")
	public void verifyLoginUserId() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", "班次封面_test1001");
		jsonObject.put("promotionalVideo", "班次宣传视频_test1001");
		jsonObject.put("promotionalVideoSrt", "班次宣传视频字幕_test1001");
		jsonObject.put("teachPlan", "教学安排_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"class_info/" + classId + "?loginUserId=" , jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("修改班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 5, description = "classCover为空")
	public void verifyClassCover() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", null);
		jsonObject.put("promotionalVideo", "班次宣传视频_test1001");
		jsonObject.put("promotionalVideoSrt", "班次宣传视频字幕_test1001");
		jsonObject.put("teachPlan", "教学安排_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"class_info/" + classId + "?loginUserId=" + loginUserId , jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("修改班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次附加信息失败");
	}
	
	@Test(priority = 6, description = "promotionalVideo为空")
	public void verifyPromotionalVideo_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", "班次封面_test1001");
		jsonObject.put("promotionalVideo", null);
		jsonObject.put("promotionalVideoSrt", "班次宣传视频字幕_test1001");
		jsonObject.put("teachPlan", "教学安排_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"class_info/" + classId + "?loginUserId=" + loginUserId , jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("修改班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次附加信息失败");
	}
	
	@Test(priority = 7, description = "promotionalVideoSrt为空")
	public void verifyPromotionalVideoSrt_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", "班次封面_test1001");
		jsonObject.put("promotionalVideo", "班次宣传视频_test1001");
		jsonObject.put("promotionalVideoSrt", null);
		jsonObject.put("teachPlan", "教学安排_test1001");

		Response response = TestConfig.postOrPutExecu("put",
				"class_info/" + classId + "?loginUserId=" + loginUserId , jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("修改班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次附加信息失败");
	}
	
	@Test(priority = 8, description = "teachPlan为空")
	public void verifyTeachPlan_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("classCover", "班次封面_test1001");
		jsonObject.put("promotionalVideo", "班次宣传视频_test1001");
		jsonObject.put("promotionalVideoSrt", "班次宣传视频字幕_test1001");
		jsonObject.put("teachPlan", null);

		Response response = TestConfig.postOrPutExecu("put",
				"class_info/" + classId + "?loginUserId=" + loginUserId , jsonObject);
		
		if (response.getStatusCode() == 500) {
			logger.info("修改班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班次附加信息失败");
	}
}
