package org.gxb.server.api.user.user;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class GetChangeEmail {
	private static Logger logger = LoggerFactory.getLogger(GetChangeEmail.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private String token;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		token = "7a98287306584688b84fb675dc32a009";

		Response response = TestConfig.getOrDeleteExecu("get", "/user/change_email?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("更换邮箱接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	
	//failed
	@Test(priority = 2, description = "token为空")
	public void verifyToken_001() {
		token = null;

		Response response = TestConfig.getOrDeleteExecu("get", "/user/change_email?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("更换邮箱接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false));
	}
	
	//failed
	@Test(priority = 3, description = "token无效")
	public void verifyToken_002() {
		token = "a12280e141504598b20f24ef";

		Response response = TestConfig.getOrDeleteExecu("get", "/user/change_email?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("更换邮箱接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false));
	}
	
	//failed
	@Test(priority = 4, description = "token已使用")
	public void verifyToken_003() {
		token = "7a98287306584688b84fb675dc32a009";

		Response response = TestConfig.getOrDeleteExecu("get", "/user/change_email?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("更换邮箱接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false));
	}
	
	//failed
	@Test(priority = 5, description = "email未注册")
	public void verifyToken_004() {
		token = "7a98287306584688b84fb675dc32a009";

		Response response = TestConfig.getOrDeleteExecu("get", "/user/change_email?token=" + token);

		if (response.getStatusCode() == 500) {
			logger.info("更换邮箱接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false));
	}
}
