package org.gxb.server.api.createcourse;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * ----发布课程
 * http://localhost:8088/gxb-api/course/1/status/publish?loginUserId=123456
 * course
 * 状态: 10:未发布  20:预发布 30:已发布  40:停用
 * 未发布，已发布的可以再次发布,停用的不能发布
 * 停用状态可以启用后状态为未发布
 */

public class PublishCourseTest {
	private OperationTable operationTable = new OperationTable();
	private int courseid;
	private int status = 30;

	@BeforeMethod
	public void InitiaData() {
		courseid = 1;
	}

	@Test(priority = 1, description = "status为已发布")
	public void verifyPublishedStatus() {
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + courseid + "/status/publish?loginUserId=123456");
		response.then().assertThat().statusCode(200);

		try {
			int actualNum = operationTable.updateCourseStatus(courseid, status);
			Assert.assertEquals(actualNum, 1, "发布状态未改变");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(priority = 2, description = "status为未发布")
	public void verifyUnpublishedStatus() {
		courseid = 3;
		String path = "http://192.168.30.33:8080/gxb-api/course/" + courseid + "/status/publish?loginUserId=123456";

		given().expect().when().put(path).then().assertThat().statusCode(1000);

		// .body("type", equalTo("ServiceException")).body("message",
		// equalTo("课程封面未达到发布标准"));
		// Response response = TestConfig.postOrPutFileExecu("put", "/course/" +
		// courseid +"/status/publish?loginUserId=123456");
		// response.then().assertThat().statusCode(1000);

		try {
			int actualNum = operationTable.updateCourseStatus(courseid, status);
			Assert.assertEquals(actualNum, 1, "发布状态未改变");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(priority = 3, description = "course不存在")
	public void verifyIsNotExistCourse() {
		courseid = 999999;

		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + courseid + "/status/publish?loginUserId=123456");
		response.then().assertThat().statusCode(1000).body("message", equalTo("课程不能发布"));
		try {
			int actualNum = operationTable.updateCourseStatus(courseid, status);
			Assert.assertEquals(actualNum, 0, "发布状态未改变");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
