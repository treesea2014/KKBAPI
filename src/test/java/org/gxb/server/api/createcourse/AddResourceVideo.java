package org.gxb.server.api.createcourse;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

/*
 * ----视频时间点添加
 * http://192.168.30.33:8080/gxb-api/course/resourceVideo/7?loginUserId=123456
 * course_video_timenode video
 */
public class AddResourceVideo {
	private static Logger logger = LoggerFactory.getLogger(CourseResourceTest.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private String videoId;
	private String loginUserId;
	JSONObject jsonObject = new JSONObject();

	@BeforeMethod
	public void InitiaData() {
		url = path + basePath + "/course/";
		videoId = "1";
		loginUserId = "1";

		jsonObject.put("timeNode", "11");
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("deleteFlag", equalTo(1)).body("videoId", equalTo(1))
				.body("courseId", equalTo(1)).body("userId", equalTo(1)).body("editorId", equalTo(1));
	}

	@Test(priority = 2, description = "videoid不存在")
	public void verifyNotExistVideo() {
		videoId = "999999";

		String paramUrl = url + "resourceVideo/" + videoId + "?loginUserId=" + loginUserId;
		String strMsg = httpRequest.sendHttpPost(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "视频不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 3, description = "无效的videoid")
	public void verifyInvalidVideo() {
		videoId = "qw12";

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	@Test(priority = 4, description = "videoid为空")
	public void verifyEmptyVideo() {
		videoId = "";

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("HttpRequestMethodNotSupportedException"));
	}

	@Test(priority = 5, description = "无效的loginUserId")
	public void verifyInvalidUserId() {
		loginUserId = "qw12";

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}

	// failed
	@Test(priority = 6, description = "loginUserId为空")
	public void verifyEmptyUserId() {
		loginUserId = "";

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo(""));
	}

	// failed
	@Test(priority = 6, description = "loginUserId为0")
	public void verifyZeroUserId() {
		loginUserId = "0";

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo(""));
	}

	// failed
	@Test(priority = 7, description = "loginUserId为负数")
	public void verifyNegativeUserId() {
		loginUserId = "-1";

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo(""));

	}

	@Test(priority = 8, description = "timeNode为负数")
	public void verifyNegativeTimeNode() {
		jsonObject.put("timeNode", -1);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400)
				.body("message", equalTo("timeNodemust be greater than or equal to 1,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 9, description = "timeNode为0")
	public void verifyZeroTimeNode() {
		jsonObject.put("timeNode", 0);

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400)
				.body("message", equalTo("timeNodemust be greater than or equal to 1,"))
				.body("type", equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 10, description = "timeNode为空")
	public void verifyEmptyTimeNode() {
		jsonObject.remove("timeNode");

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("timeNodemay not be null,")).body("type",
				equalTo("MethodArgumentNotValidException"));
	}

	@Test(priority = 11, description = "timeNode为字符串")
	public void verifyInvalidTimeNode() {
		jsonObject.put("timeNode", "qw12");

		Response response = TestConfig.postOrPutExecu("post",
				"/course/resourceVideo/" + videoId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideo##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400)
				.body("message",
						Matchers.containsString(
								"Can not construct instance of int from String value 'qw12': not a valid Integer value"))
				.body("type", equalTo("InvalidFormatException"));
	}
}
