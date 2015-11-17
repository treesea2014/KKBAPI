package org.gxb.server.api.restassured.teacher;

import com.jayway.restassured.response.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 教学中心-班组-查询班次下用户
 *             http://w-api-f2.kaikeba.cn/teams/8/user?access_token=
 *             1aace0664359768b607e63b55cd8ed72
 * 
 */
public class GetClassTeamUsers {

	private final static Logger logger = LoggerFactory.getLogger(GetClassTeamUsers.class);
	private String teamid = "8";
	private String token = TestConfig.getDefaultToken();

	@Test(description = "正常", priority = 1)
	public void test() {
		Response response = TestConfig.getOrDeleteExecu("get", "/teams/" + teamid + "/user?access_token=" + token);

		response.then().
				// log().all().
				assertThat().statusCode(200).body("status", equalTo(true))
				.body("data.pageList.team_id", Matchers.hasItems(8))
				.body("data.pageList.cmsStudent.id", Matchers.hasItem(169912));

	}

	@Test(description = "token无效", priority = 2)
	public void testErrToken01() {
		Response response = TestConfig.getOrDeleteExecu("get", "/teams/" + teamid + "/user?access_token=xxx");
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	@Test(description = "token为空", priority = 3)
	public void testErrToken02() {
		Response response = TestConfig.getOrDeleteExecu("get", "/teams/" + teamid + "/user?access_token=");
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("access_token不能为空"));
	}

	@Test(description = "teamid为99999999", priority = 4)
	public void testErrTeamid01() {
		Response response = TestConfig.getOrDeleteExecu("get", "/teams/" + teamid + "/user?access_token=" + token);
		response.then().assertThat().statusCode(404).body("status", equalTo(false)).body("message", equalTo("班组不存在"));
	}

	@Test(description = "分页", priority = 5)
	public void testPage() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/teams/" + teamid + "/user?access_token=" + token + "&curPage=2&pageSize=5");
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data.curPage", equalTo(2))
				.body("data.pageSize", equalTo(5));
	}

}
