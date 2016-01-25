package org.gxb.server.api.user.user;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

public class GetClassGroupUser {
	private static Logger logger = LoggerFactory.getLogger(GetClassGroupUser.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int tenantId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		tenantId = 123;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_group_user/tenant/" + tenantId + "?paging=pageSize:10");

		if (response.getStatusCode() == 500) {
			logger.info("获取学生选课列表接口##" + response.prettyPrint());
		}

		int expectCount = 0;
		try {
			expectCount = operationTable.selectUserInfor(tenantId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.then().assertThat().statusCode(200).body("paging.total", equalTo(expectCount));
	}
	
	@Test(priority = 2, description = "正确的参数")
	public void verifyInvalidTenantId() {
		Response response = TestConfig.getOrDeleteExecu("get", "/class_group_user/tenant/" + "qw1" + "?paging=pageSize:10");

		if (response.getStatusCode() == 500) {
			logger.info("获取学生选课列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
