package org.gxb.server.api.lms.user;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateCategoryClass {
	private static Logger logger = LoggerFactory.getLogger(UpdateCategoryClass.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int categoryId;
	private int loginUserId;
	private int tenantId;

	@BeforeMethod
	public void InitiaData() {
		categoryId = 460;
		loginUserId = 1300;
		tenantId = 8;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(4899);
		jsonArray.add(4900);

		Response response = TestConfig.postOrPutExecu("put",
				"/category/" + categoryId + "/class?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("保存category对应班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "保存category对应班次失败");
	}
	
	@Test(priority = 2, description = "categoryId不存在")
	public void verifyCategoryId() {
		categoryId = 999999;
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(4899);
		jsonArray.add(4900);

		String paramUrl = path + basePath + "/category/" + categoryId + "/class?loginUserId=" + loginUserId + "&tenantId=" + tenantId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonArray);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("保存category对应班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "category不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	//failed
	@Test(priority = 3, description = "loginUserId为空")
	public void verifyLoginUserId() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(4899);
		jsonArray.add(4900);

		Response response = TestConfig.postOrPutExecu("put",
				"/category/" + categoryId + "/class?loginUserId=&tenantId=" + tenantId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("保存category对应班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400);
	}
	
	//failed
	@Test(priority = 4, description = "tenantId为空")
	public void verifyTenantId() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(4899);
		jsonArray.add(4900);

		Response response = TestConfig.postOrPutExecu("put",
				"/category/" + categoryId + "/class?loginUserId=" + loginUserId + "&tenantId=", jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("保存category对应班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "保存category对应班次失败");
	}
	
	//failed
	@Test(priority = 5, description = "classid为空")
	public void verifyClassId_001() {
		JSONArray jsonArray = new JSONArray();

		Response response = TestConfig.postOrPutExecu("put",
				"/category/" + categoryId + "/class?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("保存category对应班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "保存category对应班次失败");
	}
	
	//failed
	@Test(priority = 6, description = "classid不存在")
	public void verifyClassId_002() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(1);
		jsonArray.add(4900);

		Response response = TestConfig.postOrPutExecu("put",
				"/category/" + categoryId + "/class?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("保存category对应班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 7, description = "正确的参数")
	public void verifyClassId_003() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("q1");
		jsonArray.add(4900);

		Response response = TestConfig.postOrPutExecu("put",
				"/category/" + categoryId + "/class?loginUserId=" + loginUserId + "&tenantId=" + tenantId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("保存category对应班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
}
