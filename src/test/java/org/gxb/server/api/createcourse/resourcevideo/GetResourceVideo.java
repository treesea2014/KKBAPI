package org.gxb.server.api.createcourse.resourcevideo;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/*
 * ----视频时间点添加
 * http://192.168.30.33:8080/gxb-api/course/resourceVideo/7?loginUserId=123456
 * course_video_time_node video
 */
public class GetResourceVideo {
	private static Logger logger = LoggerFactory.getLogger(GetResourceVideo.class);
	private String videoId;

	@BeforeMethod
	public void InitiaData() {
		videoId = "1";
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/resourceVideo/" + videoId + "?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("id获取视频信息（包含时间点）接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("videoId", equalTo(Integer.parseInt(videoId)))
				.body("deleteFlag", equalTo(1)).body("courseId", equalTo(1)).body("userId", equalTo(123456))
				.body("title", equalTo("测试视频")).body("fileSize", equalTo(100000)).body("srtSize", equalTo(100))
				.body("timeNodeList.videoTimeNodeId", Matchers.hasItems(14, 7, 8, 13, 9, 12, 15, 6));
	}

	@Test(priority = 2, description = "resourceVideo不存在")
	public void verifyNotExistResourceVideo() {
		videoId = "999999";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/resourceVideo/" + videoId + "?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("id获取视频信息（包含时间点）接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "resourceVideo无效")
	public void verifyInvalidResourceVideo() {
		videoId = "99qw";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/resourceVideo/" + videoId + "?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("id获取视频信息（包含时间点）接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "resourceVideo为空")
	public void verifyEmptyResourceVideo() {
		videoId = "";
		
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/resourceVideo/" + videoId + "?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("id获取视频信息（包含时间点）接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
