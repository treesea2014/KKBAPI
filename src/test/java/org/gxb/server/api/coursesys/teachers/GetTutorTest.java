package org.gxb.server.api.coursesys.teachers;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class GetTutorTest {
	private int user_id = 591001;
	private int tenants_id = 10;

	private String access_token = TestConfig.getTokenByUsidAndTeid(user_id, tenants_id);

	/*
	 * query根据邮箱查询
	 */
	@Test(priority = 1)
	public void VerifyQueryByEmail() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=" + access_token + "&query=" + "2024984302@qq.com");

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data.id", Matchers.hasItem(152402)).body("data.name", Matchers.hasItem("周海亮"));
	}
	
	/*
	 * query根据手机号查询
	 */
	@Test(priority = 2)
	public void VerifyQueryByMobile() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=" + access_token + "&query=" + "13133341836");

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
		.body("data.id", Matchers.hasItem(917635)).body("data.name", Matchers.hasItem("刘继文"));
	}
	
	/*
	 * query根据id查询
	 */
	@Test(priority = 3)
	public void VerifyQueryById() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=" + access_token + "&query=" + user_id);

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
		.body("data.id", Matchers.hasItem(user_id)).body("data.name", Matchers.hasItem("黄正奇"));
	}
	
	/*
	 * query根据name查询
	 */
	@Test(priority = 4)
	public void VerifyQueryByName() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=" + access_token + "&query=" + "马江超");

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
		.body("data.id", Matchers.hasItem(252252)).body("data.name", Matchers.hasItem("马江超"));
	}
	
	/*
	 * query根据name查询
	 */
	@Test(priority = 5)
	public void VerifyNullQuery() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=" + access_token + "&query=");

		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message", equalTo("filter不能为空"));
	}
	
	/*
	 * query根据name查询
	 */
	@Test(priority = 6)
	public void VerifyIsNotExistQuery() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=" + access_token + "&query=" + 999999);

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
		.body("data", Matchers.emptyCollectionOf(GetTutorTest.class));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 7)
	public void VerifyInvalidAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=test123"  + "&query=" + user_id);

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 8)
	public void VerifyNullAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token=" + null + "&query=" + user_id);

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 9)
	public void VerifyEmptyAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/users?access_token="  + "&query=" + user_id);

		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}
}
