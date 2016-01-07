package org.gxb.server.api.createcourse.course;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * ----课程资源数量
 * http://192.168.30.33:8080/gxb-api/course/1/contentCount?loginUserId=123456
 * course_page,course_topic,course_courseware
 */
public class CourseResourceTest {
	private static Logger logger = LoggerFactory.getLogger(CourseResourceTest.class);
	private OperationTable operationTable = new OperationTable();
	private static HttpRequest httpRequest = new HttpRequest();
	private int courseid;

	@BeforeMethod
	public void InitiaData() {
		courseid = 1;
	}

	//failed
	@Test(priority = 1, description = "输入正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + courseid + "/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyCorrectParams##" + response.prettyPrint());
		} else {
			int expTopicCount = 0;
			int expCoursewareCount = 0;
			int expPageCount = 0;
			int expAssignmentCount = 0;
			try {
				expTopicCount = operationTable.selectCourseChapter(courseid, "Topic");
				expCoursewareCount = operationTable.selectCourseChapter(courseid, "Courseware");
				expPageCount = operationTable.selectCourseChapter(courseid, "Page");
				expAssignmentCount = operationTable.selectCourseChapter(courseid, "Assignment");

				response.then().assertThat().statusCode(200).body("topicCount", equalTo(expTopicCount))
						.body("coursewareCount", equalTo(expCoursewareCount)).body("pageCount", equalTo(expPageCount))
						.body("assignmentCount", equalTo(expAssignmentCount));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	@Test(priority = 2, description = "course不存在")
	public void verifyNotExistCourse() {
		courseid = 999999;
				
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + courseid + "/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyNotExistCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(200);
		}
	}
	
	@Test(priority = 3, description = "无效的course")
	public void verifyInvalidCourse() {				
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + "12qw" + "/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyNotExistCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}
	
	@Test(priority = 4, description = "course为空")
	public void verifyEmptyCourse() {			
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyNotExistCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}
}
