package org.gxb.server.api.lms.user;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

public class GetUserClassInfo {
	private static Logger logger = LoggerFactory.getLogger(GetUserClassInfo.class);
	private int userId;
	private int tenantId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		tenantId = 313;
		userId = 632467;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/" + userId + "/classInfo?tenantId=" + tenantId);

		if (response.getStatusCode() == 500) {
			logger.info("用户学分课 辅修课 收藏课数量接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("minorCount", equalTo(8))
				.body("collectionCount", equalTo(1)).body("creditCount", equalTo(1));
	}
	
	@Test(priority = 2, description = "userId不存在")
	public void verifyUserId() {
		tenantId = 313;
		userId = 1;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/" + userId + "/classInfo?tenantId=" + tenantId);

		if (response.getStatusCode() == 500) {
			logger.info("用户学分课 辅修课 收藏课数量接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("minorCount", equalTo(0))
				.body("collectionCount", equalTo(0)).body("creditCount", equalTo(0));
	}
	
	@Test(priority = 3, description = "tenantId不存在")
	public void verifyTenantId() {
		tenantId = 632467;
		userId = 632467;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/" + userId + "/classInfo?tenantId=" + tenantId);

		if (response.getStatusCode() == 500) {
			logger.info("用户学分课 辅修课 收藏课数量接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("minorCount", equalTo(0))
		.body("collectionCount", equalTo(0)).body("creditCount", equalTo(0));
	}
}
