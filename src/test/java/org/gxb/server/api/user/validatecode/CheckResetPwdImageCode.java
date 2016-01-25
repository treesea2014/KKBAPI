package org.gxb.server.api.user.validatecode;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/validate_code/reset_pwd_imageCode?token=
 * http://192.168.30.33:8080/gxb-api/user/check_reset_pwd_imageCode
 */
public class CheckResetPwdImageCode {
	private static Logger logger = LoggerFactory.getLogger(CheckResetPwdImageCode.class);
	private String email;
	private String token;
	private String code;

	@BeforeMethod
	public void InitiaData() {
		email = "wxxu@gaoxiaobang.com";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/reset_pwd_imageCode?token=");
		token = response.jsonPath().get("token");
		code = response.jsonPath().get("code");
	}

	@Test(priority = 1, description = "正确的参数")
	public void VerifyCorrectParams() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", code);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data.email", equalTo(email));
	}
	
	//failed
	@Test(priority = 2, description = "key为空")
	public void VerifyKey_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", null);
		jsonObject.put("code", code);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data.email", equalTo(email));
	}
	
	
	@Test(priority = 3, description = "key无效")
	public void VerifyKey_002() {
		email = "test";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", code);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.key", equalTo("暂时只支持手机、邮箱重置密码"));
	}
	
	@Test(priority = 4, description = "key不存在")
	public void VerifyKey_003() {
		email = "test@gaoxiaobang.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", code);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.key", equalTo("用户不存在"));
	}
	
	//failed
	@Test(priority = 5, description = "code为空")
	public void VerifyCode_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", null);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data.email", equalTo(email));
	}
	
	@Test(priority = 6, description = "code无效")
	public void VerifyCode_002() {
		code = "7FQW12";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", code);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.code", equalTo("验证码错误"));
	}
	
	//failed
	@Test(priority = 7, description = "token为空")
	public void VerifyToken_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", code);
		jsonObject.put("token", null);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data.email", equalTo(email));
	}
	
	@Test(priority = 8, description = "token为空")
	public void VerifyToken_002() {
		token = "2855b000d557498881f25d25cc4fb012";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", email);
		jsonObject.put("code", code);
		jsonObject.put("token", token);

		Response response = TestConfig.postOrPutExecu("post", "/user/check_reset_pwd_imageCode", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("验证图片验证码是否正确接口##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.token", equalTo("无效的token或者会话过期"));
	}
}
