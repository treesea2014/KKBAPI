package org.gxb.server.api.createcourse.course;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.createcourse.videotimenode.DeleteVideoTimeNode;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

/*
 * ----删除课程
 * http://192.168.30.33:8080/gxb-api/course/20?loginUserId=123456&tenantId=1
 * course,class
 */
public class DeleteCourse {
	private static Logger logger = LoggerFactory.getLogger(DeleteVideoTimeNode.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int courseid;

	@BeforeMethod
	public void InitiaData() {
		url = path + basePath + "/course/";
		courseid = 30;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() throws Exception {
		operationTable.updateCourseStatus(courseid, 0, "1");
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/" + courseid + "?loginUserId=1&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课程接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "删除课程删除失败");
	}
	
	@Test(priority = 2, description = "course已经删除")
	public void verifyDeletedVideoTimeNode() {
		String paramUrl = url  + courseid + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除课程接口##verifyDeletedVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课程不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "course不存在")
	public void verifyNotExistVideoTimeNode() {
		courseid = 999999;
		
		String paramUrl = url  + courseid + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除课程接口##verifyDeletedVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "课程不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "无效的course")
	public void verifyInvalidVideoTimeNode() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/" + "qw12" + "?loginUserId=1&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课程接口##verifyInvalidVideoTimeNode##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 5, description = "course为空")
	public void verifyEmptyVideoTimeNode() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/?loginUserId=1&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课程接口##verifyEmptyVideoTimeNode##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("HttpRequestMethodNotSupportedException"));
	}
	
	@Test(priority = 6, description = "此课程已开该班次")
	public void verifyCourseOpenedClass() {
		courseid = 4;
		
		String paramUrl = url  + courseid + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除课程接口##verifyDeletedVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "已开班次不能删除", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	//failed
	@Test(priority = 7, description = "loginuserid为空")
	public void verifyEmptyUserId() {
		courseid = 31;
	
		String paramUrl = url  + courseid + "?loginUserId=&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除课程接口##verifyDeletedVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "400", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "loginUserId不能为空", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
}
