package org.gxb.server.api.classes.classes;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.course.video.AddVideo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@BeforeMethod
	public void InitiaData() {
		tenantId = 1;
		userId = 1001;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "章节2001";
		int position = 20;
		int contentId = 10;
		
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
		classObject.put("courseId", 1);
		classObject.put("className", "classes_test1001");
		classObject.put("intro", "intro_test1001");
		classObject.put("classInfo", classInfoObject);
		classObject.put("classCategories", categoriesArray);
		classObject.put("classInstructors", instructorsArray);


		Response response = TestConfig.postOrPutExecu("post", "/classes?loginUserId=" + userId + "&tenantId=" + tenantId,
				classObject);

		if (response.getStatusCode() == 500) {
			logger.info("新增班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

}
