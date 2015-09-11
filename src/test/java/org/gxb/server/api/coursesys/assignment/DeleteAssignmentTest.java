package org.gxb.server.api.coursesys.assignment;

import static org.hamcrest.Matchers.equalTo;

import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class DeleteAssignmentTest {
	private int userid = 930789;
	private int tenantsid = 20;
	private int assignmentid = 2615 ;
	private String access_token = TestConfig.getTokenByUsidAndTeid(userid, tenantsid);
	
	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyDataOk() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/"+ assignmentid +"?access_token=" + access_token);

		 response.then().
		 assertThat().statusCode(200).
		 body("status", equalTo(true));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/"+ assignmentid +"?access_token=qwqe1212");

		 response.then().
		 assertThat().statusCode(401).
		 body("status", equalTo(false)).
		 body("message", equalTo("无效的access_token"));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/"+ assignmentid +"?access_token=" + null);

		 response.then().
		 assertThat().statusCode(401).
		 body("status", equalTo(false)).
		 body("message", equalTo("无效的access_token"));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/"+ assignmentid +"?access_token=");

		 response.then().
		 assertThat().statusCode(400).
		 body("status", equalTo(false)).
		 body("message", equalTo("token不能为空"));
	}
	/*
	 * 验证pages
	 */
	@Test(priority = 5)
	public void VerifyInvalidPageid() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/"+ "123qwew" +"?access_token=" + access_token);
		 
		 response.then().
		 assertThat().statusCode(404);
	}

	/*
	 * 验证pages
	 */
	@Test(priority = 6)
	public void VerifyNullPageid() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/"+ null +"?access_token=" + access_token);

		 response.then().
		 assertThat().statusCode(404);
	}
	
	/*
	 * 验证pages
	 */
	@Test(priority = 7)
	public void VerifyEmptyPageid() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/" +"?access_token=" + access_token);

		 response.then().
		 assertThat().statusCode(404);
	}
	
	/*
	 * 验证pages     //failed
	 */
	@Test(priority = 8)
	public void VerifyIsNotExistPageid() {
		 Response response = TestConfig.getOrDeleteExecu("delete", "/assignments/"+ 999999 +"?access_token=" + access_token);

		 response.then().
		 assertThat().statusCode(200).body("status", equalTo(false));
	}
}