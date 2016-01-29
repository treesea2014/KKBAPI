package org.gxb.server.api.lms.user;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * http://192.168.30.33:8080/gxb-api/class_group_teacher/class/3046/user?userId=12500
 */
public class GetUserIsTeacher {
	private static Logger logger = LoggerFactory.getLogger(GetUserIsTeacher.class);
	private int classId;
	private int userId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		classId = 3046;
		userId = 12500;
	
		Response response = TestConfig.getOrDeleteExecu("get", "/class_group_teacher/class/" + classId + "/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户是否是导学老师接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200)
				.body("groupTeacherId", Matchers.hasItems(userId))
				.body("classId", Matchers.hasItems(classId))
				.body("teacherId", Matchers.hasItems(1242155));
	}
	
	@Test(priority = 2, description = "classId不存在")
	public void verifyClassId_001() {
		classId = 999999;
		userId = 12500;
	
		Response response = TestConfig.getOrDeleteExecu("get", "/class_group_teacher/class/" + classId + "/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户是否是导学老师接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "userId不存在")
	public void verifyUserId_001() {
		classId = 3046;
		userId = 999999;
	
		Response response = TestConfig.getOrDeleteExecu("get", "/class_group_teacher/class/" + classId + "/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户是否是导学老师接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4, description = "userId为空")
	public void verifyUserId_002() {
		classId = 3046;

		Response response = TestConfig.getOrDeleteExecu("get", "/class_group_teacher/class/" + classId + "/user?userId=");

		if (response.getStatusCode() == 500) {
			logger.info("用户是否是导学老师接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 5, description = "delete_flag = 0")
	public void verifyClassId_002() {
		classId = 1025;
		userId = 4;
	
		Response response = TestConfig.getOrDeleteExecu("get", "/class_group_teacher/class/" + classId + "/user?userId=" + userId);

		if (response.getStatusCode() == 500) {
			logger.info("用户是否是导学老师接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
