package org.gxb.server.api.restassured.video;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 查询最近一次学习的记录
 *             http://123.57.210.46:8080/gxb-api/class/2086/chapter/last?userId=
 *             1040379
 * 
 */
public class GetRecentRecord {

	@Test(description = "正常", priority = 1)
	public void test() {
		int classId = 1715;
		int userId = 1005883;

		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(200).body("contextId", equalTo(82197))
				.body("classId", Matchers.equalTo(classId)).body("userId", Matchers.equalTo(userId));
	}

	@Test(description = "classId非数字", priority = 2)
	public void testWithInvaild() {
		String classId = "x";
		int userId = 1005883;
		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(400).body("message",
				Matchers.containsString("NumberFormatException"))

		;
	}

	@Test(description = "classId超长数字", priority = 3)
	public void testWithBigNum() {
		String classId = "99999999999999999999999999";
		int userId = 1005883;
		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(400).body("message",
				Matchers.containsString("NumberFormatException"));
	}

	@Test(description = "classId不存在", priority = 4)
	public void testWithtExist() {
		String classId = "-1";
		int userId = 1005883;
		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(200);
	}

	@Test(description = "userId非数字", priority = 5)
	public void testWithInvaildUserId() {
		String userId = "x";
		int classId = 1715;
		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(400).body("message",
				Matchers.containsString("NumberFormatException"))

		;
	}

	@Test(description = "userId超长数字", priority = 6)
	public void testWithBigNumUserId() {
		String userId = "9999999999999999999999";
		int classId = 1715;
		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(400).body("message",
				Matchers.containsString("NumberFormatException"));
	}

	@Test(description = "userId不存在", priority = 7)
	public void testWithtExistUserId() {
		String userId = "-1";
		int classId = 1715;
		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(200);
	}

	@Test(description = "userId为空", priority = 8)
	public void testWithtNullUserId() {
		String userId = "";
		int classId = 1715;
		Response response = TestConfig.getOrDeleteExecu("get", "class/" + classId + "/chapter/last?userId=" + userId);
		response.then().log().all().assertThat().statusCode(200);
	}
}
