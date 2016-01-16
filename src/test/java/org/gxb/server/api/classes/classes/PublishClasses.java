package org.gxb.server.api.classes.classes;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

/*
 * ----发布班次
 * http://192.168.30.33:8080/gxb-api/classes/3/publish?loginUserId=123
 * class class_set class_info class_chapter
 */

public class PublishClasses {
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	private static Logger logger = LoggerFactory.getLogger(PublishClasses.class);
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int classId;
	private int loginUserId;

	@BeforeMethod
	public void InitiaData() {
		loginUserId = 12;
	}

	// pass 需验证
	@Test(priority = 1, description = "正常参数")
	public void verifyPublishedStatus() {
		classId = 2;

		Response response = TestConfig.postOrPutFileExecu("put",
				"/classes/" + classId + "/publish?loginUserId=" + loginUserId);

		if (response.getStatusCode() == 500) {
			logger.info("发布班次接口##verifyPublishedStatus##" + response.prettyPrint());
		}
		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "发布班次失败");

	}

	@Test(priority = 2, description = "class不存在")
	public void verifyInvalidClass_001() {
		classId = 999999;

		String paramUrl = path + basePath + "/classes/" + classId + "/publish?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布班次接口##verifyDisableStatus##" + strMsg);
		}
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "不存在的class", "message提示信息不正确");
	}

	@Test(priority = 3, description = "教学模式未达到发布标准")
	public void verifyInvalidClass_002() {
		classId = 76;

		String paramUrl = path + basePath + "/classes/" + classId + "/publish?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布班次接口##verifyDisableStatus##" + strMsg);
		}
		
		Assert.assertEquals(jsonobject.get("message").toString(), "教学模式未达到发布标准", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "message提示信息不正确");
	}
	
	@Test(priority = 4, description = "考核设置未达到发布标准")
	public void verifyInvalidClass_003() {
		classId = 78;

		String paramUrl = path + basePath + "/classes/" + classId + "/publish?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布班次接口##verifyDisableStatus##" + strMsg);
		}
		
		Assert.assertEquals(jsonobject.get("message").toString(), "考核设置未达到发布标准", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "message提示信息不正确");
	}
	
	@Test(priority = 5, description = "学习授权未达到发布标准")
	public void verifyInvalidClass_004() {
		classId = 8;

		String paramUrl = path + basePath + "/classes/" + classId + "/publish?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布班次接口##verifyDisableStatus##" + strMsg);
		}
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "学习授权未达到发布标准", "message提示信息不正确");
	}
	
	@Test(priority = 6, description = "宣传信息未达到发布标准")
	public void verifyInvalidClass_005() {
		classId = 87;

		String paramUrl = path + basePath + "/classes/" + classId + "/publish?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布班次接口##verifyDisableStatus##" + strMsg);
		}
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "宣传信息未达到发布标准", "message提示信息不正确");
	}
	
	@Test(priority = 7, description = "课程内容未达到发布标准")
	public void verifyInvalidClass_006() {
		classId = 46;

		String paramUrl = path + basePath + "/classes/" + classId + "/publish?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布班次接口##verifyDisableStatus##" + strMsg);
		}
		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课程内容未达到发布标准", "message提示信息不正确");
	}
}
