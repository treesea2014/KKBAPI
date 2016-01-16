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
 * 192.168.30.33:8080/gxb-api/class_info/118
 */
public class GetClassesInfor {
	private static Logger logger = LoggerFactory.getLogger(GetClassesInfor.class);
	private int classId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		classId = 118;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_info/" + classId);

		if (response.getStatusCode() == 500) {
			logger.info("获取单个班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("classId", equalTo(classId)).body("userId", equalTo(1))
				.body("editorId", equalTo(1)).body("tenantId", equalTo(111))
				.body("description", equalTo("<p>laowang测试课程</p>")).body("teachPlan", equalTo("<p>laowang测试课程</p>"));
	}

	@Test(priority = 2, description = "classes不存在")
	public void verifyNotExistClasses() {
		classId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_info/" + classId);

		if (response.getStatusCode() == 500) {
			logger.info("获取班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "classes无效")
	public void verifyInvalidClasses() {
		Response response = TestConfig.getOrDeleteExecu("get", "/class_info/" + "q1");

		if (response.getStatusCode() == 500) {
			logger.info("获取班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "classes为空")
	public void verifyEmptyClasses() {
		Response response = TestConfig.getOrDeleteExecu("get", "/class_info/");

		if (response.getStatusCode() == 500) {
			logger.info("获取班次附加信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(404);
	}
}
