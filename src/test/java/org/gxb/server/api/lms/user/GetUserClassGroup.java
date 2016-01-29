package org.gxb.server.api.lms.user;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;


public class GetUserClassGroup {
	private static Logger logger = LoggerFactory.getLogger(GetUserClassGroup.class);

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int classId = 2293;
		int loginUserId = 1087770;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_group_user/class/" + classId + "/user?loginUserId=" + loginUserId);

		if (response.getStatusCode() == 500) {
			logger.info("获取用户班组接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("groupUserId", equalTo(17089))
				.body("classId", equalTo(classId)).body("studentUserId", equalTo(loginUserId));
	}
	
	@Test(priority = 2, description = "classId不存在")
	public void verifyClassId() {
		int classId = 1087770;
		int loginUserId = 1087770;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_group_user/class/" + classId + "/user?loginUserId=" + loginUserId);

		if (response.getStatusCode() == 500) {
			logger.info("获取用户班组接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "loginUserId不存在")
	public void verifyLoginUserId_001() {
		int classId = 2293;
		int loginUserId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_group_user/class/" + classId + "/user?loginUserId=" + loginUserId);

		if (response.getStatusCode() == 500) {
			logger.info("获取用户班组接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4, description = "delete_flag=0")
	public void verifyLoginUserId_002() {
		int classId = 2078;
		int loginUserId = 1053405;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/class_group_user/class/" + classId + "/user?loginUserId=" + loginUserId);

		if (response.getStatusCode() == 500) {
			logger.info("获取用户班组接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
