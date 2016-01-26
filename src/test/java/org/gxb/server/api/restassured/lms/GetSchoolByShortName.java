package org.gxb.server.api.restassured.lms;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

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
		String shortName = "dgut";
		Response response = TestConfig.getOrDeleteExecu("get", "/school/" + shortName);
		response.then().log().all().assertThat()
				.statusCode(200)
				.body("shortname", equalTo(shortName))
				.body("name", equalTo("东莞理工学院"))
				.body("validateType", equalTo("email"))
				.body("website", equalTo("http://www.dgut.edu.cn/ "))
				.body("schoolHost.schoolName", equalTo(shortName));
	}

	@Test(description = "输入不存在的shortName", priority = 2)
	public void testWithInvaildName() {
		String shortName = "x";
		Response response = TestConfig.getOrDeleteExecu("get", "/school/" + shortName);
		response.then().log().all().assertThat()
				.statusCode(200)
				.body(Matchers.equalTo(""))
		;
	}

	@Test(description = "输入超长的shortName", priority = 3)
	public void testWithLongName() {
		String shortName = "999999999999999999999";
		Response response = TestConfig.getOrDeleteExecu("get", "/school/" + shortName);
		response.then().log().all().assertThat().statusCode(200);
	}

	@Test(description = "不输入shortName", priority = 4)
	public void testWithoutShortName() {
		Response response = TestConfig.getOrDeleteExecu("get", "/school/");
		response.then().log().all().assertThat()
				.statusCode(200)
				.body("paging.curPage", Matchers.equalTo(1))
				.body("paging.pageSize", Matchers.equalTo(10))
				.body("dataList.name", Matchers.hasItem("北京工业职业技术学院"))
				.body("dataList.category", Matchers.hasItem("junior"))
				.body("dataList.shortname", Matchers.hasItem("bgy"))
		;
	}
}
