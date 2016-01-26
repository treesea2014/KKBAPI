package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 租户学校查询 123.57.210.46:8080/gxb-api/school?filter=tenantId:1
 * 
 */
public class GetSchoolFilter {

	private final static Logger logger = LoggerFactory.getLogger(GetSchoolFilter.class);

	@Test(description = "正常", priority = 1)
	public void test() {
		String tenantId = "1";
		ArrayList tenant = new ArrayList();
		tenant.add(1);
		Response response = TestConfig.getOrDeleteExecu("get", "/school?filter=tenantId:" + tenantId);

		response.then().log().all().assertThat()
				.statusCode(200)
				.body("filter.tenantId", equalTo(tenantId))
				.body("dataList.tenantId", equalTo(tenant));
	}

	@Test(description = "没有tenantId", priority = 2)
	public void testWithoutTenantId() {

		Response response = TestConfig.getOrDeleteExecu("get", "/school?filter=tenantId:");
		response.then().log().all().assertThat().statusCode(200).body("dataList.name", Matchers.hasItem("北京工业职业技术学院"))
				.body("dataList.tenantId", Matchers.hasItem(2));
	}

	@Test(description = "输入不存在的tenantId", priority = 3)
	public void testWithNotExistTenantId() {
		String tenantId = "-1";
		Response response = TestConfig.getOrDeleteExecu("get", "/school?filter=tenantId:" + tenantId);
		response.then().log().all().assertThat().statusCode(200);
	}

	@Test(description = "tenantId为非数字类型时", priority = 4)
	public void testWithInvaild() {

		Response response = TestConfig.getOrDeleteExecu("get", "/school?filter=tenantId:X");
		response.then().log().all().assertThat().statusCode(500)
				.body("message", Matchers.containsString("NumberFormatException"))
				.body("type", equalTo("MyBatisSystemException"));
	}

	@Test(description = "tenantId为超长时", priority = 5)
	public void testWithBigNum() {

		Response response = TestConfig.getOrDeleteExecu("get", "/school?filter=tenantId:99999999999999999999999999");
		response.then().log().all().assertThat().statusCode(400)
				//.body("message", Matchers.containsString("NumberFormatException"))
				.body("type", containsString("Exception"));
	}

}
