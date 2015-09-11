package org.gxb.server.api.coursesys.teachers;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.builder.ResponseBuilder;
import com.jayway.restassured.response.Response;

public class AddTutorTest {
	private int user_id = 930749;
	private int tenants_id = 10;
	private int classesid = 1;

	private String access_token = TestConfig.getTokenByUsidAndTeid(user_id, tenants_id);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyCorrectParams() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" + user_id
						+ "&access_token=" + access_token)
				.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data", equalTo(0));
	}

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 2)
	public void VerifySameParams() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" + user_id
						+ "&access_token=" + access_token)
				.then().assertThat().statusCode(200).body("status", equalTo(true)).body("message", equalTo("success"))
				.body("data", equalTo(2));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyInvalidAccesstoken() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" + user_id
						+ "&access_token=" + "79415208f5cab710")
				.then().assertThat().statusCode(401).body("status", equalTo(false))
				.body("message", equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyNullAccesstoken() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" + user_id
						+ "&access_token=" + null)
				.then().assertThat().statusCode(401).body("status", equalTo(false))
				.body("message", equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 5)
	public void VerifyEmptyAccesstoken() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" + user_id
						+ "&access_token=")
				.then().assertThat().statusCode(400).body("status", equalTo(false))
				.body("message", equalTo("token不能为空"));
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 6)
	public void VerifyInvalidClasses() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + "a1" + "/teachers?user_id=" + user_id
						+ "&access_token=" + access_token)
				.then().assertThat().statusCode(404);
	}

	/*
	 * 验证classes
	 */
	@Test(priority = 7)
	public void VerifyEmptyClasses() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/"  + "/teachers?user_id=" + user_id
						+ "&access_token=" + access_token)
				.then().assertThat().statusCode(404);
	}
	
	/*
	 * 验证classes
	 */
	@Test(priority = 8)
	public void VerifyIsNotExistClasses() {
		given().expect().when()
		.post("http://w-api-f2.kaikeba.cn/classes/" + 999999 + "/teachers?user_id=" + user_id
				+ "&access_token=" + access_token)
		.then().assertThat().statusCode(200).body("status", equalTo(false)).body("message", equalTo("save error"));
	}
	
	/*
	 * 验证user_id
	 */
	@Test(priority = 9)
	public void VerifyInvalidUserid() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" + "a1"
						+ "&access_token=" + access_token)
				.then().assertThat().statusCode(404);
	}
	
	/*
	 * 验证user_id
	 */
	@Test(priority = 10)
	public void VerifyEmptyUserid() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" 
						+ "&access_token=" + access_token)
				.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message", equalTo("userId不能为空"));
	}
	
	/*
	 * 验证user_id    //failed
	 */
	@Test(priority = 11)
	public void VerifyIsNotExistUserid() {
		given().expect().when()
				.post("http://w-api-f2.kaikeba.cn/classes/" + classesid + "/teachers?user_id=" + 999999
						+ "&access_token=" + access_token)
				.then().assertThat().statusCode(200).body("status", equalTo(false)).body("message", equalTo("user_id不存在"));
	}
}
