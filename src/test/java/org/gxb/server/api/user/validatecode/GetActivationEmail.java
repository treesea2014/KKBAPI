package org.gxb.server.api.user.validatecode;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

/*
 * http://192.168.30.33:8080/gxb-api/validate_code/activation_email?email=xli01@gaoxiaobang.com
 */
public class GetActivationEmail {
	private static Logger logger = LoggerFactory.getLogger(GetActivationEmail.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private String email;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		email = "wxxu@gaoxiaobang.com";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/activation_email?email=" + email);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	@Test(priority = 2, description = "email无效")
	public void verifyEmail_001() {
		email = "test";

		Response response = TestConfig.getOrDeleteExecu("get", "/validate_code/activation_email?email=" + email);

		if (response.getStatusCode() == 500) {
			logger.info("获取邮箱注册验证码接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("status", equalTo(false)).body("errorInfo.email", equalTo("邮箱不合法"));
	}
	
	@Test(priority = 3, description = "email不存在")
	public void verifyEmail_002() {
		email = "wx@gaoxiaobang.com";

		String paramUrl = path + basePath + "/validate_code/activation_email?email=" + email;
		String strMsg = httpRequest.sendHttpGet(paramUrl);	
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("获取邮箱注册验证码接口##" + strMsg);
		}
		
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "账号不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "email的status = 20")
	public void verifyEmail_003() {
		email = "yguo@kaikeba.com";

		String paramUrl = path + basePath + "/validate_code/activation_email?email=" + email;
		String strMsg = httpRequest.sendHttpGet(paramUrl);	
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("获取邮箱注册验证码接口##" + strMsg);
		}
		
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "账号已被激活", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
}
