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
 * @decription 教学中心-班组-查询班次下班组列表
 *             http://w-api-f2.kaikeba.cn/classes/2083/teams?access_token=
 *             1aace0664359768b607e63b55cd8ed72
 * 
 */
public class GetClassTeam {

	private final static Logger logger = LoggerFactory.getLogger(GetClassTeam.class);
	private String classid = "2083";
	private String token = TestConfig.getDefaultToken();

	@Test(description = "正常", priority = 1)
	public void test() {
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classid + "/teams?access_token=" + token);
		ArrayList data = new ArrayList();
		data.add(149);
		data.add(150);
		response.then().
				// log().all().
				assertThat().statusCode(200).body("status", equalTo(true))
				.body("data.pageList.team_id", Matchers.hasItems(13))
				.body("data.pageList.teamTeacherList.id", Matchers.hasItem(data));

	}

	@Test(description = "token无效", priority = 2)
	public void testErrToken01() {
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classid + "/teams?access_token=xxx");
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}

	@Test(description = "token为空", priority = 3)
	public void testErrToken02() {
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + classid + "/teams?access_token=");
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("access_token不能为空"));
	}

	@Test(description = "classid为99999999", priority = 4)
	public void testErrClassid01() {
		Response response = TestConfig.getOrDeleteExecu("get", "/classes/" + 99999 + "/teams?access_token=xxx");
		response.then().assertThat().statusCode(404).body("status", equalTo(false)).body("message", equalTo("课程不存在"));
	}

	@Test(description = "分页", priority = 5)
	public void testPage() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classid + "/teams?access_token=" + token + "&curPage=2&pageSize=5");
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data.curPage", equalTo(2))
				.body("data.pageSize", equalTo(5));
	}

}
