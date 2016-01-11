package org.gxb.server.api.course.assignment;

import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class DeleteAssignment {
	private static Logger logger = LoggerFactory.getLogger(ResourceBundle.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public int flag = 1;
	public int userId;
	public int assignmentId;

	@BeforeMethod
	public void InitiaData() {
		assignmentId = 16;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		try {
			operationTable.updateCourseAssignment(assignmentId, flag);
			operationTable.updateCourseChapter(assignmentId, flag, "Assignment");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// TODO Auto-generated catch block
			logger.info(e.toString());
		}

		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/assignment/" + assignmentId + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除assignment接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "删除assignment失败");
	}
	
	@Test(priority = 2, description = "assignment已删除")
	public void verifyDeletedAssignment() {	
		String paramUrl = path + basePath  + "/course/assignment/"+ assignmentId + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除quiz接口##verifyDeletedQuiz##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "作业不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	
	@Test(priority = 3, description = "assignment已删除")
	public void verifyNotExistAssignment() {	
		assignmentId = 1;
		
		String paramUrl = path + basePath  + "/course/assignment/"+ assignmentId + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除quiz接口##verifyDeletedQuiz##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "作业不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "无效的assignment")
	public void verifyInvalidAssignment() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/assignment/" + "q1" + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除assignment接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 5, description = "无效的userid")
	public void verifyInvalidUserId() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/assignment/" + assignmentId + "?loginUserId=q1&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除assignment接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	//failed
	@Test(priority = 6, description = "userid为空")
	public void verifyEmptyUserId() {
		try {
			operationTable.updateCourseAssignment(assignmentId, flag);
			operationTable.updateCourseChapter(assignmentId, flag, "Assignment");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// TODO Auto-generated catch block
			logger.info(e.toString());
		}
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/assignment/" + assignmentId + "?loginUserId=&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除assignment接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("userid不能为空"));
	}
}
