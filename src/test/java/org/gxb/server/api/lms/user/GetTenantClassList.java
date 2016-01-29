package org.gxb.server.api.lms.user;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class GetTenantClassList {
	private static Logger logger = LoggerFactory.getLogger(GetTenantClassList.class);
	private int tenantId;
	private boolean needPage;

	@BeforeMethod
	public void InitiaData() {
		tenantId = 313;
		needPage = true;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {

		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenant/" + tenantId + "/class?tenantId=" + tenantId + "&needPage=" + needPage);

		if (response.getStatusCode() == 500) {
			logger.info("租户班次列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(58)).body("dataList.tenantId",
				Matchers.hasItems(tenantId));
	}
	
	@Test(priority = 2, description = "tenantId不存在")
	public void verifyTenantId_001() {
		tenantId = 999999;
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenant/" + tenantId + "/class?tenantId=" + tenantId + "&needPage=" + needPage);

		if (response.getStatusCode() == 500) {
			logger.info("租户班次列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
	
	@Test(priority = 3, description = "tenantId为空")
	public void verifyTenantId_002() {
	
		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenant/" + tenantId + "/class?tenantId=&needPage=" + needPage);

		if (response.getStatusCode() == 500) {
			logger.info("租户班次列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(58)).body("dataList.tenantId",
				Matchers.hasItems(tenantId));
	}
	
	@Test(priority = 4, description = "needPage为false")
	public void verifyNeedPage_001() {
		needPage = false;
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenant/" + tenantId + "/class?tenantId=" + tenantId + "&needPage=" + needPage);

		if (response.getStatusCode() == 500) {
			logger.info("租户班次列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("dataList.tenantId",
				Matchers.hasItems(tenantId));
	}
	
	@Test(priority = 5, description = "needPage为空")
	public void verifyNeedPage_002() {
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenant/" + tenantId + "/class?tenantId=" + tenantId + "&needPage=");

		if (response.getStatusCode() == 500) {
			logger.info("租户班次列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(58)).body("dataList.tenantId",
				Matchers.hasItems(tenantId));
	}
}
