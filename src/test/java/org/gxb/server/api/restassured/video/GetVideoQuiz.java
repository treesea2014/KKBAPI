package org.gxb.server.api.restassured.video;

import com.jayway.restassured.response.Response;

import org.testng.annotations.Test;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 查询最近一次学习的记录 http://123.57.210.46:8080/gxb-api/class/2086/chapter/last?userId=1040379
 * 
 */
public class GetVideoQuiz {

	@Test(description = "正常", priority = 1)
	public void test() {
		String videoId = "91499";
		Response response = TestConfig.getOrDeleteExecu("get", "video/" + videoId + "/quizQuestion");
		response.then().log().all().assertThat().statusCode(200).body("contextId", Matchers.hasItem(91499))
				.body("contextType", Matchers.hasItem("Video"))
				.body("answerList.quizAnswerId", Matchers.hasItem(Matchers.hasItem(272411)))
				.body("answerList.quizQuestionId", Matchers.hasItem(Matchers.hasItem(72559)))
				.body("answerList.correct", Matchers.hasItem(Matchers.hasItem(true)));
	}

	@Test(description = "非数字", priority = 1)
	public void testWithInvaild() {
		String videoId = "x";
		Response response = TestConfig.getOrDeleteExecu("get", "video/" + videoId + "/quizQuestion");
		response.then().log().all()
					.assertThat().statusCode(400)
					.body("message",Matchers.containsString("NumberFormatException"));
	}

	@Test(description = "超长数字", priority = 1)
	public void testWithBigNum() {
		String videoId = "99999999999999999999999999999";
		Response response = TestConfig.getOrDeleteExecu("get", "video/" + videoId + "/quizQuestion");
		response.then().log().all()
					.assertThat().statusCode(400)
					.body("message",Matchers.containsString("NumberFormatException"));
		;
	}

	@Test(description = "不存在的用户信息", priority = 1)
	public void testWithtExist() {
		String videoId = "-1";
		Response response = TestConfig.getOrDeleteExecu("get", "video/" + videoId + "/quizQuestion");
		response.then().log().all()
					.assertThat().statusCode(200)
				;
	}

}
