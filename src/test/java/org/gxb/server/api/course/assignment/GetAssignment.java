package org.gxb.server.api.course.assignment;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetAssignment {
	private static Logger logger = LoggerFactory.getLogger(GetAssignment.class);
	private int userId;
	private int assignmentId;

	@BeforeMethod
	public void InitiaData() {
		assignmentId = 30;
		userId = 2002;
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "章节2001";
		String infor = "good2001";
		String link = "www.baidu2001.com";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("link", link);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);

		JSONObject assignmentJson = new JSONObject();
		assignmentJson.put("title", title);
		assignmentJson.put("body", infor);
		assignmentJson.put("assetsList", jsonArray);

		Response response = TestConfig.postOrPutExecu("put",
				"/course/assignment/" + assignmentId + "?loginUserId=" + userId + "&tenantId=1", assignmentJson);

		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/assignment/" + assignmentId + "?loginUserId=123456");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取assignment接口##verifyCorrectParams##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("assignmentId", equalTo(assignmentId))
				.body("deleteFlag", equalTo(1)).body("courseId", equalTo(2)).body("editorId", equalTo(userId))
				.body("title", equalTo(title)).body("body", equalTo(infor)).body("assetsList.link", Matchers.hasItems(link));
	}
	
	@Test(priority = 2, description = "Assignment不存在")
	public void verifyNotExistAssignment() {
		assignmentId = 1;
				
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/assignment/" + assignmentId + "?loginUserId=123456");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取assignment接口##verifyCorrectParams##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}

	@Test(priority = 3, description = "Assignment无效")
	public void verifyInvalidAssignment() {
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/course/assignment/" + "q1" + "?loginUserId=123456");

		if (getResponse.getStatusCode() == 500) {
			logger.info("根据id获取assignment接口##verifyCorrectParams##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
