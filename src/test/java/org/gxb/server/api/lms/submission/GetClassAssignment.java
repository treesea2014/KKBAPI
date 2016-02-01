package org.gxb.server.api.lms.submission;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class GetClassAssignment {
	private static Logger logger = LoggerFactory.getLogger(GetClassAssignment.class);

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int classId = 2;
		int assignmentId = 7;

		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classId + "/Assignment/" + assignmentId);

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据内容获取chapter－item—unit接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("classId", equalTo(classId))
				.body("itemList.classId", Matchers.hasItems(classId));
	}
	
	@Test(priority = 2, description = "class不存在")
	public void verifyClass() {
		int classId = 9999;
		int assignmentId = 7;

		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classId + "/Assignment/" + assignmentId);

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据内容获取chapter－item—unit接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "assignmentId不存在")
	public void verifyAssignmentId() {
		int classId = 2;
		int assignmentId = 9999;

		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classId + "/Assignment/" + assignmentId);

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据内容获取chapter－item—unit接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
}
