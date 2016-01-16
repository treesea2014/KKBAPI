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
 * 192.168.30.33:8080/gxb-api/class_set/118
 */
public class GetClassesSetInfor {
	private static Logger logger = LoggerFactory.getLogger(GetClassesSetInfor.class);
	private int classId;


	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		classId = 118;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_set/" + classId);

		if (response.getStatusCode() == 500) {
			logger.info("获取班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("classId", equalTo(classId))
				.body("userId", equalTo(1))
				.body("editorId", equalTo(1)).body("tenantId", equalTo(111))
				.body("offlinePercent", equalTo(0)).body("onlinePercent", equalTo(100))
				.body("videoPercent", equalTo(50)).body("quizPercent", equalTo(20)).body("assignmentPercent", equalTo(20))
				.body("topicPercent", equalTo(10));
	}

	@Test(priority = 2, description = "classes不存在")
	public void verifyNotExistClasses() {
		classId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_set/" + classId);

		if (response.getStatusCode() == 500) {
			logger.info("获取班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "classes无效")
	public void verifyInvalidClasses() {
		classId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_set/" + "q1");

		if (response.getStatusCode() == 500) {
			logger.info("获取班次考核信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
