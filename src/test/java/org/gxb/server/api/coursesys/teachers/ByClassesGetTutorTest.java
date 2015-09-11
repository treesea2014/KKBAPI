package org.gxb.server.api.coursesys.teachers;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class ByClassesGetTutorTest {
	private int user_id = 713216;
	private int tenants_id = 10;
	private int classesid = 2;

	private String access_token = TestConfig.getTokenByUsidAndTeid(user_id, tenants_id);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classesid + "/teachers?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data.id", Matchers.hasItem(76)).body("data.name", Matchers.hasItem("田鲁"))
				.body("data.id", Matchers.hasItem(470)).body("data.name", Matchers.hasItem("郭靖"))
				.body("data.id", Matchers.hasItem(754)).body("data.name", Matchers.hasItem("欧岩亮"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classesid + "/teachers?access_token=test123");

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classesid + "/teachers?access_token=" + null);

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classesid + "/teachers?access_token=");

		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 5)
	public void VerifyInvalidClasses() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + "a1" + "/teachers?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 6)
	public void VerifyEmptyClasses() {
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + "/teachers?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 7)
	public void VerifyIsNotExistClasses() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + 999999 + "/teachers?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data", Matchers.emptyCollectionOf(ByClassesGetTutorTest.class));
	}
}
