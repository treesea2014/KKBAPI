package org.gxb.server.api.restassured.unit;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 租户学校查询 by shortName 123.57.210.46:8080/gxb-api/school/buaa
 * 
 */
public class GetContent {

	@Test(description = "正常", priority = 1)
	public void test() {
		int classId = 2367;
		int topicId = 10548;
		Response response = TestConfig.getOrDeleteExecu("get", "/class/" + classId + "/Topic/" + topicId);
		response.then().log().all().assertThat().statusCode(200).body("courseId", equalTo(classId))
				.body("chapterList.contentType", Matchers.hasItem("Topic"))
				.body("chapterList.contentId", Matchers.hasItem(topicId));
	}

	@Test(description = "classId为非数字字符", priority = 1)
	public void testWithErrClassId() {
		String classId = "x";
		int topicId = 10548;
		Response response = TestConfig.getOrDeleteExecu("get", "/class/" + classId + "/Topic/" + topicId);
		response.then().log().all().assertThat().statusCode(400)

		;
	}

	@Test(description = "classId为超长数字", priority = 1)
	public void testWithBigClassId() {
		String classId = "9999999999999999999999999";
		int topicId = 10548;
		Response response = TestConfig.getOrDeleteExecu("get", "/class/" + classId + "/Topic/" + topicId);
		response.then().log().all().assertThat().statusCode(400)

		;
	}

	@Test(description = "classId为不存在", priority = 1)
	public void testWithNotClassId() {
		String classId = "-1";
		int topicId = 10548;
		Response response = TestConfig.getOrDeleteExecu("get", "/class/" + classId + "/Topic/" + topicId);
		response.then().log().all().assertThat().statusCode(200);
	}

	@Test(description = "topic为非数字字符", priority = 1)
	public void testWithErrTopicId() {
		int classId = 2367;
		String topicId = "x";
		Response response = TestConfig.getOrDeleteExecu("get", "/class/" + classId + "/Topic/" + topicId);
		response.then().log().all().assertThat().statusCode(400)

		;
	}

	@Test(description = "topic为超长数字", priority = 1)
	public void testWithBigTopic() {
		int classId = 2367;
		String topicId = "999999999999999999999999999";
		Response response = TestConfig.getOrDeleteExecu("get", "/class/" + classId + "/Topic/" + topicId);
		response.then().log().all().assertThat().statusCode(400)

		;
	}

	@Test(description = "topic为不存在", priority = 1)
	public void testWithNotTopic() {
		int classId = 2367;
		String topicId = "-1";
		Response response = TestConfig.getOrDeleteExecu("get", "/class/" + classId + "/Topic/" + topicId);
		response.then().log().all().assertThat().statusCode(200);
	}

}
