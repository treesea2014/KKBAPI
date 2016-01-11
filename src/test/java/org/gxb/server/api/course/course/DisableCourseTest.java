package org.gxb.server.api.course.course;

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
 * ----停用课程
 * http://192.168.30.33:8080/gxb-api/course/2/status/disable?loginUserId=123456
 * course course_unit course_item course_chapter course_quiz course_topic course_page course_courseware course_assignment
 * 状态: 10:未发布  20:预发布 30:已发布  40:停用
 * 未发布，已发布的可以再次发布,停用的不能发布
 * 停用状态可以启用后状态为未发布
 */
public class DisableCourseTest {
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private OperationTable operationTable = new OperationTable();
	private static HttpRequest httpRequest = new HttpRequest();
	private static Logger logger = LoggerFactory.getLogger(DisableCourseTest.class);
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int courseid;
	private int status;
	private int disableStatus = 40;

	@BeforeMethod
	public void InitiaData() {
		courseid = 17;
		status = 30;
		url = path + basePath + "/course/";
	}

	@Test(priority = 1, description = "status为已发布")
	public void verifyPublishedStatus() {
		try {
			operationTable.updateCourseStatus(courseid, status, null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + courseid + "/status/disable?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("停用课程接口##verifyPublishedStatus##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(200);
			Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "message不正确");
			try {
				int courseStatus = operationTable.selectCourseStatus(courseid, disableStatus);
				Assert.assertEquals(courseStatus, 1, "course启用状态未改变");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test(priority = 2, description = "status为未发布")
	public void verifyUnpublishedStatus() {
		courseid = 11;
		status = 10;

		try {
			operationTable.updateCourseStatus(courseid, status, null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + courseid + "/status/disable?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("停用课程接口##verifyUnpublishedStatus##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(200);
			Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "message不正确");
			try {
				int courseStatus = operationTable.selectCourseStatus(courseid, disableStatus);
				Assert.assertEquals(courseStatus, 1, "course启用状态未改变");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test(priority = 3, description = "status为已停用")
	public void verifyDisableStatus() {
		courseid = 71;
		String paramUrl = url + courseid + "/status/disable?loginUserId=123456";

		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("停用课程接口##verifyDisableStatus##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程已停用", "message提示信息不正确");
			Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
		}
	}

	@Test(priority = 4, description = "status为已发布")
	public void verifyNotExistCourse() {
		courseid = 999999;
		String paramUrl = url + courseid + "/status/disable?loginUserId=123456";

		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("停用课程接口##verifyNotExistCourse##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程不存在", "message提示信息不正确");
			Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
		}
	}

	@Test(priority = 5, description = "course为字符串")
	public void verifyInvalidCourse() {
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + "qw12" + "/status/disable?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("停用课程接口##verifyInvalidCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}

	@Test(priority = 6, description = "course为空")
	public void verifyEmptyCourse() {
		Response response = TestConfig.postOrPutFileExecu("put", "/status/disable?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("停用课程接口##verifyEmptyCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(403);
		}
	}
}
