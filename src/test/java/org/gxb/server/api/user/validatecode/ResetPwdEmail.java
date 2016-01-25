package org.gxb.server.api.user.validatecode;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.gxb.server.api.user.user.EmailRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/validate_code/reset_pwd_imageCode?token=
 * http://192.168.30.33:8080/gxb-api/user/check_reset_pwd_imageCode
 * http://192.168.30.33:8080/gxb-api/validate_code/reset_pwd_email
 */
public class ResetPwdEmail {
	private static Logger logger = LoggerFactory.getLogger(ResetPwdEmail.class);
	private String email;
	private String token;
	private String code;

	@BeforeMethod
	public void InitiaData() {
		email = "wxxu@gaoxiaobang.com";
		
		Response getResponse = TestConfig.getOrDeleteExecu("get", "/validate_code/reset_pwd_imageCode?token=");
		token = getResponse.jsonPath().get("token");
		code = getResponse.jsonPath().get("code");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", code);
		jsonObject.put("token", token);

		Response postResponse = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
	}

	@Test(priority = 1, description = "正确的参数")
	public void VerifyCorrectParams() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/reset_pwd_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("邮箱发送重置密码接口##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}

	//failed
	@Test(priority = 2, description = "email为空")
	public void VerifyEmail_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", null);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/reset_pwd_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("邮箱发送重置密码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.email", equalTo("邮箱不能为空"));
	}
	
	@Test(priority = 3, description = "email无效")
	public void VerifyEmail_002() {
		email = "test11";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/reset_pwd_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("邮箱发送重置密码接口##" + response.prettyPrint());
		}
		
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.email", equalTo("邮箱不合法"));
	}
	
	@Test(priority = 4, description = "email与token不一致")
	public void VerifyEmail_003() {
		email = "294911618@qq.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/reset_pwd_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("邮箱发送重置密码接口##" + response.prettyPrint());
		}
		
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.email", equalTo("邮箱与会话账号不一致"));
	}
	
	//failed
	@Test(priority = 5, description = "token为空")
	public void VerifyToken_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("token", null);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/reset_pwd_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("邮箱发送重置密码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.token", equalTo("token不能为空"));
	}
	
	@Test(priority = 6, description = "token无效")
	public void VerifyToken_002() {
		token =  "0e0186d37b254d699d13e48829ab8612";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("token", "test123456789");

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/reset_pwd_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("邮箱发送重置密码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.token", equalTo("无效的token或者会话过期"));
	}
}
