package org.gxb.server.api.coursesys.teachers;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.builder.ResponseBuilder;
import com.jayway.restassured.response.Response;

public class DeleteTutorTest {
	private int user_id = 909591;
	private int tenants_id = 10;
	private int classesid = 350;

	private String access_token = TestConfig.getTokenByUsidAndTeid(user_id, tenants_id);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + user_id + "&access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data", equalTo(0));
	}

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 2)
	public void VerifySameParams() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + user_id + "&access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data", equalTo(1));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyInvalidAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + user_id + "&access_token=" + "9e67ccc3705ff12e");
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyNullAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + user_id + "&access_token=" + null);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 5)
	public void VerifyEmptyAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + user_id + "&access_token=");
		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 6)
	public void VerifyInvalidClasses() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + "a1" + "/teachers?user_id=" + user_id + "&access_token=" + access_token);
		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 7)
	public void VerifyEmptyClasses() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + "/teachers?user_id=" + user_id + "&access_token=" + access_token);
		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 8)
	public void VerifyIsNotExistClasses() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + 999999 + "/teachers?user_id=" + user_id + "&access_token=" + access_token);
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data", equalTo(1));
	}

	/*
	 * 验证user_id
	 */
	@Test(priority = 9)
	public void VerifyInvalidUserid() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + "a1" + "&access_token=" + access_token);
		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证user_id
	 */
	@Test(priority = 10)
	public void VerifyEmptyUserid() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + "&access_token=" + access_token);
		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("userId不能为空"));
	}

	/*
	 * 验证user_id
	 */
	@Test(priority = 11)
	public void VerifyIsNotExistUserid() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/classes/" + classesid + "/teachers?user_id=" + 10 + "&access_token=" + access_token);
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data", equalTo(1));
	}
}
