package org.gxb.server.api.createcourse;

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

/*
 * ----修改时间点
 * http://192.168.30.33:8080/gxb-api/course/videoTimeNode/12?loginUserId=123456&tenantId=1
 * course_video_time_node
 */
public class UpdateVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(UpdateVideoTimeNode.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	JSONObject jsonObject = new JSONObject();
	public String url;
	private int videoTimeNode;
	private String userId;
	private String timeNode;

	@BeforeMethod
	public void InitiaData() {
		url = path + basePath + "/course/";
		videoTimeNode = 12;
		timeNode = "100";
		userId = "12";

		jsonObject.put("timeNode", timeNode);
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("videoTimeNodeId", equalTo(videoTimeNode))
				.body("editorId", equalTo(Integer.parseInt(userId))).body("timeNode", equalTo(Integer.parseInt(timeNode)));
	}
	
	@Test(priority = 2, description = "videoTimeNode不存在")
	public void verifyNotExistVideoTimeNode() {
		videoTimeNode = 1;

		String paramUrl = url + "videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1";
		String strMsg = httpRequest.sendHttpPut(paramUrl, jsonObject);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("视频时间点添加接口##verifyNotExistVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "时间点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "videoTimeNode无效")
	public void verifyInvalidVideoTimeNode() {
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + "qw12" + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 4, description = "videoTimeNode为空")
	public void verifyEmptyVideoTimeNode() {
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 5, description = "loginUserId无效")
	public void verifyInvalidLoginUserId() {
		userId = "qw12";
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	//failed
	@Test(priority = 6, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		userId = "";
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("userid不能为空"));
	}
	
	@Test(priority = 7, description = "timeNode无效")
	public void verifyInvalidTimeNode() {
		jsonObject.remove("timeNode");
		timeNode = "qw12";
		jsonObject.put("timeNode", timeNode);
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}

	@Test(priority = 8, description = "timeNode为空")
	public void verifyEmptyTimeNode() {		
		jsonObject.remove("timeNode");
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("timeNode不能为null,"));
	}
	
	@Test(priority = 9, description = "timeNode为0")
	public void verifyZeroTimeNode() {		
		jsonObject.remove("timeNode");
		timeNode = "0";
		jsonObject.put("timeNode", timeNode);
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("timeNode最小不能小于1,"));
	}
	
	@Test(priority = 10, description = "timeNode为负数")
	public void verifyNegativeTimeNode() {		
		jsonObject.remove("timeNode");
		timeNode = "-1";
		jsonObject.put("timeNode", timeNode);
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("timeNode最小不能小于1,"));
	}
	
	//failed
	@Test(priority = 11, description = "userid为0")
	public void verifyZeroUserid() {		
		userId = "0";
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("loginUserId最小不能小于1,"));
	}
	
	//failed
	@Test(priority = 10, description = "userid为负数")
	public void verifyNegativeUserid() {		
		userId = "-1";
		
		Response response = TestConfig.postOrPutExecu("put",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=" + userId + "&tenantId=1", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("时间点更改测验接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("MethodArgumentNotValidException"))
		.body("message", equalTo("loginUserId最小不能小于1,"));
	}
}
