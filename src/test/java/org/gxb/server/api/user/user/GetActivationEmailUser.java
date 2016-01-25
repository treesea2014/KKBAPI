package org.gxb.server.api.user.user;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.user.validatecode.GetActivationEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/*
 * http://192.168.30.33:8080/gxb-api/validate_code/change_email
 * http://192.168.30.33:8080/gxb-api/user/activation?token=ca61bd8d787c4cb18201f3e2aca63679
 */
public class GetActivationEmailUser {
	private static Logger logger = LoggerFactory.getLogger(GetActivationEmail.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private String token;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		token = "a12280e141504598b20f24ef0adfd010";

		Response response = TestConfig.getOrDeleteExecu("get", "/user/activation?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	//failed
	@Test(priority = 2, description = "token为空")
	public void verifyToken_001() {
		token = null;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/activation?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false));
	}
	
	//failed
	@Test(priority = 3, description = "token无效")
	public void verifyToken_002() {
		token = "a12280e141504598b20f24ef";

		Response response = TestConfig.getOrDeleteExecu("get", "/user/activation?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false));
	}
}
