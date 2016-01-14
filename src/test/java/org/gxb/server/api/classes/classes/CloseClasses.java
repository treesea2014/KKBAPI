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
 * 关闭班次
 * http://192.168.30.33:8080/gxb-api/classes/4/close?loginUserId=123
 * classes(已经结课的班次才能关闭，根据结课时间判断：当前时间大于结课时间就算已经结课啊)
 */
public class CloseClasses {
	private static Logger logger = LoggerFactory.getLogger(CloseClasses.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int userId;
	private int classId;

	@BeforeMethod
	public void InitiaData() {
		userId = 1001;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {		
		classId = 82;
		
		try {
			operationTable.updateClass(classId, null, "1");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//关闭班次
		Response deleteResponse = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classId + "/close?loginUserId=" + userId);

		if (deleteResponse.getStatusCode() == 500) {
			logger.info("关闭班次接口##" + deleteResponse.prettyPrint());
		}

		deleteResponse.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(deleteResponse.prettyPrint()), true, "关闭班次失败");
	}

	//failed
	@Test(priority = 2, description = "class已经关闭")
	public void verifyClosedClasses() {
		classId = 82;
		
		String paramUrl = path + basePath + "/classes/"  + classId + "/close?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("关闭班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "该班次已关闭", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "class不存在")
	public void verifyNotExistCourse() {
		classId = 999999;
		
		String paramUrl = path + basePath + "/classes/"  + classId + "/close?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("关闭班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "不存在的class", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "无效的classes")
	public void verifyInvalidCourse() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + "q1" + "/close?loginUserId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("关闭班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	
	@Test(priority = 5, description = "此班次未结课")
	public void verifyNoClass() {	
		classId = 12;
		
		String paramUrl = path + basePath + "/classes/"  + classId + "/close?loginUserId=" + userId;
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("关闭班次接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "只有已结课的班次才能关闭", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}


	@Test(priority = 6, description = "loginuserid为空")
	public void verifyEmptyUserId() {	
		classId = 4;
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classId + "/close?loginUserId=");

		if (response.getStatusCode() == 500) {
			logger.info("关闭班次接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
