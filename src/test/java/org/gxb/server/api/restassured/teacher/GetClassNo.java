package org.gxb.server.api.restassured.teacher;

import com.jayway.restassured.response.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 教学中心-班组-查询班次列表
 *             http://w-api-f2.kaikeba.cn/tenants/8/classes?access_token=
 *             1aace0664359768b607e63b55cd8ed72&curPage=1&pageSize=1
 * 
 */
public class GetClassNo {

	private final static Logger logger = LoggerFactory.getLogger(GetClassNo.class);
	private String tenantid = TestConfig.tenantid;
	private String token = TestConfig.getDefaultToken();

	@Test(description = "正常", priority = 1)
	public void test() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenants/" + tenantid + "/classes?access_token=" + token);
		response.then().log().all().assertThat().statusCode(200).body("status", equalTo(true)).
				// body("data.total", equalto(13)).
				body("data.pageList.id", Matchers.hasItems(2083))
				.body("data.pageList.name", Matchers.hasItems("响应式Web设计-单元得分"));

	}

	@Test(description = "token无效", priority = 2)
	public void testErrToken01() {
		Response response = TestConfig.getOrDeleteExecu("get", "/tenants/" + tenantid + "/classes?access_token=654");
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	@Test(description = "token为空", priority = 3)
	public void testErrToken02() {
		Response response = TestConfig.getOrDeleteExecu("get", "/tenants/" + tenantid + "/classes?access_token=");
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("access_token不能为空"));
	}

	@Test(description = "tenantid为99999999", priority = 4)
	public void testErrTenantId01() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenants/" + 999999999 + "/classes?access_token=" + token);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message", equalTo("租户不存在"));
	}

	@Test(description = "分页", priority = 5)
	public void testPage() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/tenants/" + 9999999 + "/classes?access_token=" + token + "&curPage=2&pageSize=5");
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data.curPage", equalTo(2))
				.body("data.pageSize", equalTo(5));
	}

}
