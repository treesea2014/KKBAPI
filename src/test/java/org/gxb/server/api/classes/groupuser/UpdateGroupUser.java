package org.gxb.server.api.classes.groupuser;

import java.util.ResourceBundle;
import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.classes.video.UpdateVideoTimeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class UpdateGroupUser {
	private static Logger logger = LoggerFactory.getLogger(UpdateVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int groupUserId;
	private int loginUserId;

	@BeforeMethod
	public void InitiaData() {
		groupUserId = 3002;
		loginUserId = 3002;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", 0);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班组用户失败");
	}
	
	@Test(priority = 2, description = "deleteflag = -1")
	public void verifyDeleteFlag_001() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", -1);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班组用户失败");
	}
	
	@Test(priority = 3, description = "deleteflag = 1")
	public void verifyDeleteFlag_002() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", 1);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改班组用户失败");
	}
	
	//failed
	@Test(priority = 4, description = "deleteflag为空")
	public void verifyDeleteFlag_003() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", null);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("deleteFlag不能为空"));
	}
	
	//failed
	@Test(priority = 5, description = "deleteflag除-1外")
	public void verifyDeleteFlag_004() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", -2);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("deleteFlag不能为空"));
	}
	
	//failed
	@Test(priority = 6, description = "deleteflag除0,1外")
	public void verifyDeleteFlag_005() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", 2);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("message", equalTo("deleteFlag不能为空"));
	}
	
	@Test(priority = 7, description = "deleteflag无效")
	public void verifyDeleteFlag_006() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", "1q1");

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	@Test(priority = 8, description = "groupUserId不存在")
	public void verifyGroupUserId() {
		groupUserId = 999999;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", 1);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=" + loginUserId, jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "修改班组用户失败");
	}
	
	//failed
	@Test(priority = 9, description = "loginUserId为空")
	public void verifyLoginUserId() {	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deleteFlag", 1);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/" + groupUserId + "?loginUserId=", jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
