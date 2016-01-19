package org.gxb.server.api.classes.groupuser;

import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.classes.courseware.DeleteCourseware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;

public class BatchDeleteGroupUser {
	private static Logger logger = LoggerFactory.getLogger(DeleteCourseware.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(9667);
		jsonArray.add(9668);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/batch_delete?loginUserId=12", jsonArray);
		if (response.getStatusCode() == 500) {
			logger.info("批量删除班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "批量删除班组用户失败");
	}
	
	@Test(priority = 2, description = "loginUserId为空")
	public void verifyEmptyLoginUserId() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(9667);
		jsonArray.add(9668);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/batch_delete?loginUserId=", jsonArray);
		if (response.getStatusCode() == 500) {
			logger.info("批量删除班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}

	//failed
	@Test(priority = 3, description = "group_user_id的delete_flag=0")
	public void verifyGroupUserId_001() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(9667);
		jsonArray.add(9668);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/batch_delete?loginUserId=12", jsonArray);
		if (response.getStatusCode() == 500) {
			logger.info("批量删除班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "批量删除班组用户失败");
	}

	@Test(priority = 4, description = "group_user_id不存在")
	public void verifyGroupUserId_002() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(999999);

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/batch_delete?loginUserId=12", jsonArray);
		if (response.getStatusCode() == 500) {
			logger.info("批量删除班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "批量删除班组用户失败");
	}
	
	//failed
	@Test(priority = 5, description = "group_user_id为空")
	public void verifyGroupUserId_003() {
		JSONArray jsonArray = new JSONArray();

		Response response = TestConfig.postOrPutExecu("put", "/class_group_user/batch_delete?loginUserId=12", jsonArray);
		if (response.getStatusCode() == 500) {
			logger.info("批量删除班组用户接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), false, "批量删除班组用户失败");
	}
	
}
