package org.gxb.server.api.lms.user;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

public class GetTenantCategoryList {
	private static Logger logger = LoggerFactory.getLogger(GetTenantCategoryList.class);
	private int tenantId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		tenantId = 313;
		
		Response response = TestConfig.getOrDeleteExecu("get", "/tenant/" + tenantId + "/category");

		if (response.getStatusCode() == 500) {
			logger.info("租户下分类列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("categoryId", Matchers.hasItems(77, 83, 84, 320, 321, 461))
				.body("tenantId", Matchers.hasItems(tenantId));
	}
	
	@Test(priority = 2, description = "tenantId不存在")
	public void verifyTenantId() {
		tenantId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/tenant/" + tenantId + "/category");

		if (response.getStatusCode() == 500) {
			logger.info("租户下分类列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
