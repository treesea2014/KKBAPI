package org.gxb.server.api.user.validatecode;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/validate_code/change_email
 */
public class ChangeEmail {
	private static Logger logger = LoggerFactory.getLogger(ChangeEmail.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int userId;
	private String encryptedPwd;

	@BeforeMethod
	public void InitiaData() {
		userId = 1272509;
		encryptedPwd = "123456789";
	}

	@Test(priority = 1, description = "正确的参数")
	public void VerifyCorrectParams() {
		String email = "test11@163.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data", equalTo(null));
	}
	
	//failed
	@Test(priority = 2, description = "userid为空")
	public void VerifyUserId_001() {
		String email = "test11@163.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", null);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.userId", equalTo("userId不能为"));
	}
	
	@Test(priority = 3, description = "userid不存在")
	public void VerifyUserId_002() {
		userId = 999999;
		String email = "test11@163.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		String paramUrl = path + basePath + "/validate_code/change_email";
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);	
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("发送修改邮箱邮件接口##" + strMsg);
		}
		
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "账号不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	//failed
	@Test(priority = 4, description = "userid的delete_flag = 0")
	public void VerifyUserId_003() {
		userId = 1272478;
		String email = "test11@163.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.userId", equalTo("userId不不存在"));
	}
	
	//failed
	@Test(priority = 5, description = "userid的status = 20")
	public void VerifyUserId_004() {
		userId = 541540;
		String email = "test11@163.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.userId", equalTo("userId不存在"));
	}
	
	@Test(priority = 6, description = "encrypted_password为空")
	public void VerifyEncryptedPwd_001() {
		String email = "test11@163.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", null);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.encrypted_password", equalTo("密码不能为空"));
	}
	
	@Test(priority = 7, description = "encrypted_password不正确")
	public void VerifyEncryptedPwd_002() {
		String email = "test11@163.com";
		encryptedPwd = "qwe123";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.encrypted_password", equalTo("密码不正确"));
	}
	
	//failed
	@Test(priority = 8, description = "email为空")
	public void VerifyEmail_001() {
		String email = "test11@163.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", null);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.email", equalTo("email不能为空"));
	}

	@Test(priority = 9, description = "email无效")
	public void VerifyEmail_002() {
		String email = "test11";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.email", equalTo("邮箱不合法"));
	}
	
	@Test(priority = 10, description = "email为原来的email")
	public void VerifyEmail_003() {
		String email = "wxxu@gaoxiaobang.com";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", userId);
		jsonObject.put("encrypted_password", encryptedPwd);
		jsonObject.put("email", email);

		Response response = TestConfig.postOrPutExecu("post", "/validate_code/change_email", jsonObject);
		if (response.getStatusCode() == 500) {
			logger.info("发送修改邮箱邮件接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("data", equalTo(null))
		.body("errorInfo.email", equalTo("新旧邮箱一致"));
	}
}
