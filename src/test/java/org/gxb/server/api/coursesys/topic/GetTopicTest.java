package org.gxb.server.api.coursesys.topic;

import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class GetTopicTest {
	private int userid = 932866;
	private int tenantsid = 10;
	private int topicid = 5;
	private String access_token = TestConfig.getTokenByUsidAndTeid(userid, tenantsid);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyDataOk() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + topicid + "?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true))
				.body("data.id", equalTo(topicid)).body("data.context_id", equalTo(1))
				.body("data.context_type", equalTo("Course")).body("data.title", equalTo("讨论：媒体查询"))
				.body("data.body", equalTo("<span>响应式设计是如何获得信息的，谈一下自己对媒体查询的理解。</span>"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + topicid + "?access_token=24a7b1798");

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + topicid + "?access_token=" + null);

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + topicid + "?access_token=");

		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}

	/*
	 * 验证videos
	 */
	@Test(priority = 5)
	public void VerifyInvalidVideos() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + "a1" + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证videos   
	 */
	@Test(priority = 6)
	public void VerifyIsNotExistVideos() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + 999999 + "?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}

	/*
	 * 验证videos
	 */
	@Test(priority = 7)
	public void VerifyNullVideos() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + null + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证videos
	 */
	@Test(priority = 8)
	public void VerifyEmptyVideos() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/topics/" + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}
}
