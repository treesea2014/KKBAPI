package org.gxb.server.api.createcourse;

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
 * ----启用课程
 * http://192.168.30.33:8080/gxb-api/course/1/status/start?loginUserId=123456
 * course course_unit course_item course_chapter course_quiz course_topic course_page course_courseware course_assignment
 * 状态: 10:未发布  20:预发布 30:已发布  40:停用
 * 未发布，已发布的可以再次发布,停用的不能发布
 * 停用状态可以启用后状态为未发布
 */

public class EnableCourseTest {
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private OperationTable operationTable = new OperationTable();
	private static HttpRequest httpRequest = new HttpRequest();
	private static Logger logger = LoggerFactory.getLogger(EnableCourseTest.class);
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int courseid;
	private int status = 10;
	private int disableStatus = 40;

	@BeforeMethod
	public void InitiaData() {
		courseid = 72;
		url = path + basePath + "/course/";
	}

	//failed  需验证
	@Test(priority = 1, description = "status为停用")
	public void verifyDisableStatus() {
		try {
			operationTable.updateCourseStatus(courseid, disableStatus);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + courseid + "/status/start?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("启用课程接口##verifyPublishedStatus##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(200);
			Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "message不正确");
			try {
				int courseStatus = operationTable.selectCourseStatus(courseid, status);
				int unitStatus = operationTable.selectCourseUnitStatus(courseid, status);
				int itemStatus = operationTable.selectCourseItemStatus(courseid, status);
				int chapterStatus = operationTable.selectCourseChapterStatus(courseid, status);
				int quizStatus = operationTable.selectCourseQuizStatus(courseid, status);
				int topicStatus = operationTable.selectCourseTopicStatus(courseid, status);
				int assignmentStatus = operationTable.selectCourseAssignmentStatus(courseid, status);
				int coursewareStatus = operationTable.selectCourseCoursewareStatus(courseid, status);
				int pageStatus = operationTable.selectCoursePageStatus(courseid, status);

				Assert.assertEquals(courseStatus, 1, "course启用状态未改变");
				Assert.assertEquals(unitStatus, 1, "unitStatus启用状态未改变");
				Assert.assertEquals(itemStatus, 1, "itemStatus启用状态未改变");
				Assert.assertEquals(chapterStatus, 1, "chapterStatus启用状态未改变");
				Assert.assertEquals(quizStatus, 1, "quizStatus启用状态未改变");
				Assert.assertEquals(topicStatus, 1, "topicStatus启用状态未改变");
				Assert.assertEquals(assignmentStatus, 1, "assignmentStatus启用状态未改变");
				Assert.assertEquals(coursewareStatus, 1, "coursewareStatus启用状态未改变");
				Assert.assertEquals(pageStatus, 1, "pageStatus启用状态未改变");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Test(priority = 2, description = "status为未发布")
	public void verifyUnpublishedStatus() {
		courseid = 3;
		String paramUrl = url + courseid + "/status/start?loginUserId=123456";
		String strMsg = httpRequest.SendHttpPut(paramUrl, null);
		
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("启用课程接口##verifyUnpublishedStatus##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程未停用", "message提示信息不正确");
			Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
		}
	}
	
	@Test(priority = 3, description = "status为已发布")
	public void verifyPublishedStatus() {
		courseid = 17;
		String paramUrl = url + courseid + "/status/start?loginUserId=123456";

		String strMsg = httpRequest.SendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("启用课程接口##verifyPublishedStatus##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程未停用", "message提示信息不正确");
			Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
		}
	}

	@Test(priority = 4, description = "status为已发布")
	public void verifyNotExistCourse() {
		courseid = 999999;
		String paramUrl = url + courseid + "/status/start?loginUserId=123456";

		String strMsg = httpRequest.SendHttpPut(paramUrl, null);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (data[0] == "500") {
			logger.info("启用课程接口##verifyNotExistCourse##" + strMsg);
		} else {
			Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
			Assert.assertEquals(jsonobject.get("message").toString(), "课程不存在", "message提示信息不正确");
			Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
		}
	}
	
	@Test(priority = 5, description = "course为字符串")
	public void verifyInvalidCourse() {
		Response response = TestConfig.postOrPutFileExecu("put",
				"/course/" + "qw12" + "/status/start?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("启用课程接口##verifyInvalidCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}

	@Test(priority = 6, description = "course为空")
	public void verifyEmptyCourse() {
		Response response = TestConfig.postOrPutFileExecu("put", "/status/start?loginUserId=123456");
		if (response.getStatusCode() == 500) {
			logger.info("发布课程接口##verifyEmptyCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(403);
		}
	}
}
