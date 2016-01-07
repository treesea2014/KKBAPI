package org.gxb.server.api.createcourse.course;

import static com.jayway.restassured.RestAssured.given;
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
 * ----发布课程
 * http://localhost:8088/gxb-api/course/1/status/publish?loginUserId=123456
 * course
 * 状态: 10:未发布  20:预发布 30:已发布  40:停用
 * 未发布，已发布的可以再次发布,停用的不能发布
 * 停用状态可以启用后状态为未发布
 */

public class PublishCourseTest {
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private OperationTable operationTable = new OperationTable();
	private static HttpRequest httpRequest = new HttpRequest();
	private static Logger logger = LoggerFactory.getLogger(PublishCourseTest.class);
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int courseid;
	private int status = 30;

	@BeforeMethod
	public void InitiaData() {
		courseid = 1;
		url = path + basePath + "/course/";
	}

	//failed 需验证
	@Test(priority = 1, description = "status为已发布")
	public void verifyPublishedStatus() {
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + courseid + "/status/publish?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("发布课程接口##verifyPublishedStatus##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(200);
			try {
				int actualNum = operationTable.selectCourseStatus(courseid, status);
				Assert.assertEquals(actualNum, 1, "发布状态未改变");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test(priority = 2, description = "status为未发布")
	public void verifyUnpublishedStatus() {
		courseid = 3;
		String paramUrl = url + courseid + "/status/publish?loginUserId=123456";

		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布课程接口##verifyUnpublishedStatus##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程封面未达到发布标准", "message提示信息不正确");
		}
	}

	@Test(priority = 3, description = "status为停用")
	public void verifyDisableStatus() {
		courseid = 18;
		String paramUrl = url + courseid + "/status/publish?loginUserId=123456";

		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布课程接口##verifyDisableStatus##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程不是未发布或者发布状态", "message提示信息不正确");
		}
	}

	@Test(priority = 4, description = "course不存在")
	public void verifyIsNotExistCourse() {
		courseid = 999999;
		String paramUrl = url + courseid + "/status/publish?loginUserId=123456";

		String strMsg = httpRequest.sendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("发布课程接口##verifyIsNotExistCourse##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程不存在", "message提示信息不正确");
		}
	}

	@Test(priority = 5, description = "course为字符串")
	public void verifyInvalidCourse() {
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + "qw12" + "/status/publish?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("发布课程接口##verifyInvalidCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}

	@Test(priority = 6, description = "course为空")
	public void verifyEmptyCourse() {
		Response response = TestConfig.postOrPutFileExecu("put", "/course/status/publish?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("发布课程接口##verifyEmptyCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(403);
		}
	}

}
