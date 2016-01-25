package org.gxb.server.api.user.validatecode;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;


public class GetEmailValidateCode {
	private static Logger logger = LoggerFactory.getLogger(GetEmailValidateCode.class);
	private String email;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		email = "test1001@gaoxiaobang.com";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/email_register?email=" + email);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	@Test(priority = 2, description = "email无效")
	public void verifyEmail_001() {
		email = "test1001";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/email_register?email=" + email);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.email", equalTo("邮箱不合法"));
	}
	
	@Test(priority = 3, description = "email输入手机号")
	public void verifyEmail_002() {
		email = "test1001";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/email_register?email=" + email);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.email", equalTo("邮箱不合法"));
	}
	
	@Test(priority = 4, description = "email为空")
	public void verifyEmail_003() {
		email = "";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/email_register?email=" + email);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.email", equalTo("邮箱不合法"));
	}
}
