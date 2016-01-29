package org.gxb.server.api.lms.user;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;

public class GetCategoryClass {
	private static Logger logger = LoggerFactory.getLogger(GetCategoryClass.class);
	private int categoryId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		categoryId = 20;

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(categoryId);
		
		Response response = TestConfig.getOrDeleteExecu("get", "/category/" + categoryId + "/class");

		if (response.getStatusCode() == 500) {
			logger.info("分类所属班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200)
				.body("dataList.classId", Matchers.hasItems(2051, 1845, 1879))
				.body("dataList.courseId", Matchers.hasItems(2024,1))
				.body("dataList.classCategories.categoryId", Matchers.hasItems(jsonArray));
	}
	
	@Test(priority = 2, description = "delete_flag = 0")
	public void verifyCategoryId_001() {
		categoryId = 460;

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(categoryId);
		
		Response response = TestConfig.getOrDeleteExecu("get", "/category/" + categoryId + "/class");

		if (response.getStatusCode() == 500) {
			logger.info("分类所属班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 2, description = "categoryId不存在")
	public void verifyCategoryId_002() {
		categoryId = 999999;

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(categoryId);
		
		Response response = TestConfig.getOrDeleteExecu("get", "/category/" + categoryId + "/class");

		if (response.getStatusCode() == 500) {
			logger.info("分类所属班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
}
