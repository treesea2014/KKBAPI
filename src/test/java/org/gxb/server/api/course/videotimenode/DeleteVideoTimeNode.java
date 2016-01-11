package org.gxb.server.api.course.videotimenode;

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
 * ----删除时间点
 * http://192.168.30.33:8080/gxb-api/course/videoTimeNode/1?loginUserId=123456
 * course_video_time_node
 */
public class DeleteVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(DeleteVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private int videoTimeNode;

	@BeforeMethod
	public void InitiaData() {
		url = path + basePath + "/course/";
		videoTimeNode = 11;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/videoTimeNode/" + videoTimeNode + "?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("删除时间点接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "删除时间点删除失败");
	}
	
	@Test(priority = 2, description = "videoTimeNode已经删除")
	public void verifyDeletedVideoTimeNode() {
		String paramUrl = url + "videoTimeNode/" + videoTimeNode + "?loginUserId=123456";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除时间点接口##verifyDeletedVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "时间点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "videoTimeNode不存在")
	public void verifyNotExistVideoTimeNode() {
		videoTimeNode = 1;
		
		String paramUrl = url + "videoTimeNode/" + videoTimeNode + "?loginUserId=123456";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除时间点接口##verifyNotExistVideoTimeNode##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "时间点不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "无效的videoTimeNode")
	public void verifyInvalidVideoTimeNode() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/videoTimeNode/" + "qw12" + "?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("删除时间点接口##verifyInvalidVideoTimeNode##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 5, description = "videoTimeNode为空")
	public void verifyEmptyVideoTimeNode() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/videoTimeNode/?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("删除时间点接口##verifyEmptyVideoTimeNode##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
