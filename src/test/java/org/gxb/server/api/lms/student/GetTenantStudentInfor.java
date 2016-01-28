package org.gxb.server.api.lms.student;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

public class GetTenantStudentInfor {
	private static Logger logger = LoggerFactory.getLogger(GetTenantStudentInfor.class);
	private int userId;
	private int tenantId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		tenantId = 492;
		userId = 1239700;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/" + userId + "/" + tenantId + "/student");

		if (response.getStatusCode() == 500) {
			logger.info("userid 某个租户下学生详细信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("userId", equalTo(userId))
				.body("username", equalTo("gzqy20150525049")).body("studentList.studentId", Matchers.hasItems(363946))
				.body("studentList.userId", Matchers.hasItems(userId))
				.body("studentList.no", Matchers.hasItems("20150525049"))
				.body("studentList.name", Matchers.hasItems("郑敏"));
	}

	@Test(priority = 2, description = "userId不存在")
	public void verifyUserId_001() {
		tenantId = 492;
		userId = 1;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/" + userId + "/" + tenantId + "/student");

		if (response.getStatusCode() == 500) {
			logger.info("userid 某个租户下学生详细信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "tenantId不存在")
	public void verifyTenantId_001() {
		tenantId = 1239700;
		userId = 1239700;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/" + userId + "/" + tenantId + "/student");

		if (response.getStatusCode() == 500) {
			logger.info("userid 某个租户下学生详细信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4, description = "tenantId与userid不一致")
	public void verifyTenantId_002() {
		tenantId = 1;
		userId = 1239700;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/" + userId + "/" + tenantId + "/student");

		if (response.getStatusCode() == 500) {
			logger.info("userid 某个租户下学生详细信息接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("userId", equalTo(userId))
		.body("username", equalTo("gzqy20150525049")).body("name", equalTo("郑敏"));
	}
}
