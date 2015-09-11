package org.gxb.server.api.coursesys.topic;

import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class DeleteTopicTest {
	private int userid = 937295;
	private int tenantsid = 10;
	private int topicid = 7386;
	private String access_token = TestConfig.getTokenByUsidAndTeid(userid, tenantsid);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyDataOk() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + topicid + "?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + topicid + "?access_token=24a7b1798");

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + topicid + "?access_token=" + null);

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + topicid + "?access_token=");

		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}

	/*
	 * 验证videos
	 */
	@Test(priority = 5)
	public void VerifyInvalidVideos() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + "a1" + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证videos     //failed
	 */
	@Test(priority = 6)
	public void VerifyIsNotExistVideos() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + 999999 + "?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(false));
	}

	/*
	 * 验证videos
	 */
	@Test(priority = 7)
	public void VerifyNullVideos() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + null + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证videos
	 */
	@Test(priority = 8)
	public void VerifyEmptyVideos() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/topics/" + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}
}
