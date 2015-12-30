package org.gxb.server.api.restassured.tenant;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 租户学校查询 by shortName 123.57.210.46:8080/gxb-api/school/buaa
 * 
 */
public class GetSchoolByShortName {

	@Test(description = "正常", priority = 1)
	public void test() {
		String shortName = "buaa";
		Response response = TestConfig.getOrDeleteExecu("get", "/school/" + shortName);
		response.then().log().all().assertThat().statusCode(200).body("shortname", equalTo(shortName))
				.body("name", equalTo("北京航空航天大学")).body("validateType", equalTo("email"))
				.body("website", equalTo("http://www.buaa.edu.cn/")).body("schoolHost.schoolName", equalTo(shortName));
	}

	@Test(description = "输入不存在的shortName", priority = 1)
	public void testWithInvaildName() {
		String shortName = "x";
		Response response = TestConfig.getOrDeleteExecu("get", "/school/" + shortName);
		response.then().log().all().assertThat().statusCode(200);
	}

	@Test(description = "输入超长的shortName", priority = 1)
	public void testWithLongName() {
		String shortName = "999999999999999999999";
		Response response = TestConfig.getOrDeleteExecu("get", "/school/" + shortName);
		response.then().log().all().assertThat().statusCode(200);
	}
}
