package org.gxb.server.api.course.videotimenode;

import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import com.jayway.restassured.response.Response;

/*
 * ----查询时间点
 * http://192.168.30.33:8080/gxb-api/course/videoTimeNode/6?loginUserId=123456&tenantId=1
 * course_video_time_node
 */
public class GetVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(GetVideoTimeNode.class);
	private String videoTimeNode;

	@BeforeMethod
	public void InitiaData() {
		videoTimeNode = "10";
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询时间点接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("videoTimeNodeId", equalTo(Integer.parseInt(videoTimeNode)))
				.body("videoId", equalTo(5)).body("courseId", equalTo(1)).body("timeNode", equalTo(2))
				.body("userId", equalTo(1)).body("questionCount", equalTo(38)).body("editorId", equalTo(1));
	}
	
	@Test(priority = 2, description = "videoTimeNode不存在")
	public void verifyNotExistVideoTimeNode() {
		videoTimeNode = "999999";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询时间点接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "无效的videoTimeNode")
	public void verifyInvalidVideoTimeNode() {
		videoTimeNode = "9qw";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询时间点接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 4, description = "videoTimeNode为空")
	public void verifyEmptyVideoTimeNode() {
		videoTimeNode = "";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询时间点接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
