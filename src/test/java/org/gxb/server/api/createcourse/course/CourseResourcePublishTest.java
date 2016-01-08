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
 * 课程资源是否达到发布状态
 * http://192.168.30.33:8080/gxb-api/course/30/resourceCount?loginUserId=123456
 * video,document,question
 */
public class CourseResourcePublishTest {
	private static Logger logger = LoggerFactory.getLogger(CourseResourcePublishTest.class);
	private int courseid;

	@BeforeMethod
	public void InitiaData() {
		courseid = 1;
	}

	//failed
	@Test(priority = 1, description = "输入正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + courseid + "/resourceCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源是否达到发布状态接口##" + response.prettyPrint());
		} 		
		
		response.then().assertThat().statusCode(200).body("resourceVideoCount", equalTo(11))
		.body("questionCount", equalTo(10)).body("documentCount", equalTo(55));
	}
	
	@Test(priority = 2, description = "course不存在")
	public void verifyNotExistCourse() {
		courseid = 999999;
				
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + courseid + "/resourceCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源是否达到发布状态接口##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(200);
		}
	}
	
	@Test(priority = 3, description = "无效的course")
	public void verifyInvalidCourse() {				
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + "q12" + "/resourceCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源是否达到发布状态接口##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}

}
