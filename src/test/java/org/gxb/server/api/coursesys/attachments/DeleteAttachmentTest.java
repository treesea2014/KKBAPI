package org.gxb.server.api.coursesys.attachments;

import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class DeleteAttachmentTest {
	private int userid = 930742;
	private int tenantsid = 20;
	private int attachmentid = 5;
	private String access_token = TestConfig.getTokenByUsidAndTeid(userid, tenantsid);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyDataOk() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/attachments/" + 3 + "?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/attachments/" + attachmentid + "?access_token=qwqe1212");

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/attachments/" + attachmentid + "?access_token=" + null);

		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		Response response = TestConfig.getOrDeleteExecu("delete", "/attachments/" + attachmentid + "?access_token=");

		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}

	/*
	 * 验证attachments
	 */
	@Test(priority = 5)
	public void VerifyInvalidAttachmentid() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/attachments/" + "123qwew" + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证attachments
	 */
	@Test(priority = 6)
	public void VerifyNullAttachmentid() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/attachments/" + null + "?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证attachments
	 */
	@Test(priority = 7)
	public void VerifyEmptyAttachmentid() {
		Response response = TestConfig.getOrDeleteExecu("delete", "/attachments/?access_token=" + access_token);

		response.then().assertThat().statusCode(404);
	}

	/*
	 * 验证attachments
	 */
	@Test(priority = 8)
	public void VerifyIsNotExistAttachmentid() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/attachments/" + 99999999 + "?access_token=" + access_token);

		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}

}
