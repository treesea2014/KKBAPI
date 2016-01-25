package org.gxb.server.api.user.user;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class EmailRegister {
	private static Logger logger = LoggerFactory.getLogger(EmailRegister.class);
	private String email;
	private String validateCode;

	@BeforeMethod
	public void InitiaData() {
		// 获取email验证码
		email = "test1001@gaoxiaobang.com";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/email_register?email=" + email);
		validateCode = response.jsonPath().get("data");
	}

	@Test(priority = 1, description = "正确的参数")
	public void VerifyCorrectParams() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "123456789");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data.email", equalTo(email))
				.body("data.status", equalTo(10));
	}

	//failed
	@Test(priority = 2, description = "已经注册过的邮箱")
	public void VerifyRegisteredEmail() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "123456789");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(true)).body("data.email", equalTo(email))
				.body("data.status", equalTo(10));
	}
	
	//failed
	@Test(priority = 3, description = "email为空")
	public void VerifyEmail_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", null);
		jsonObject.put("encrypted_password", "123456789");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
				.body("message", equalTo("email不能为空"));
	}
	
	//failed
	@Test(priority = 4, description = "email无效")
	public void VerifyEmail_002() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", "123@123.com");
		jsonObject.put("encrypted_password", "123456789");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.email", equalTo("邮箱不正确"));
	}
	

	//failed
	@Test(priority = 5, description = "encrypted_password为空")
	public void VerifyEncryptedPwd_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "123456789");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("encrypted_password不能为空"));
	}

	//failed
	@Test(priority = 6, description = "encrypted_password长度小于6")
	public void VerifyEncryptedPwd_002() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "12345");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("encrypted_password不能为空"));
	}

	//failed
	@Test(priority = 7, description = "encrypted_password大于16")
	public void VerifyEncryptedPwd_003() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "11111111111111112");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("encrypted_password不能为空"));
	}
	
	//failed
	@Test(priority = 8, description = "encrypted_password为中文")
	public void VerifyEncryptedPwd_004() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "好的");
		jsonObject.put("validateCode", validateCode);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("encrypted_password不能为空"));
	}
	
	//failed
	@Test(priority = 9, description = "validateCode为空")
	public void VerifyValidateCode_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "123456789");
		jsonObject.put("validateCode", null);

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("validateCode不能为空"));
	}
	
	@Test(priority = 10, description = "validateCode无效")
	public void VerifyValidateCode_002() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", email);
		jsonObject.put("encrypted_password", "123456789");
		jsonObject.put("validateCode", "qw12");

		Response response = TestConfig.postOrPutExecu("post", "/user/register", jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.code", equalTo("验证码不正确"));
	}
}
