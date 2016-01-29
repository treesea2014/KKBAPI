package org.gxb.server.api.lms.quiz;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;

public class GetClassesVideoQuiz {
	private static Logger logger = LoggerFactory.getLogger(GetClassesVideoQuiz.class);
	private int classId;
	private int videoId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		classId = 3435;
		videoId = 113602;
		
		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);
		
		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classId + "/video/" + videoId + "/quizQuestion");

		if (response.getStatusCode() == 500) {
			logger.info("视频下测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("videoTimeNodeId", Matchers.hasItems(102136))
				.body("classId", Matchers.hasItems(classId)).body("videoId", Matchers.hasItems(videoId))
				.body("questionList.questionId", Matchers.hasItems(questionArray))
				.body("questionList.courseId", Matchers.hasItems(courseArray));
	}
	
	@Test(priority = 2, description = "classId与videoId不一致")
	public void verifyClassId_001() {
		classId = 1602;
		videoId = 113602;
		
		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);
		
		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classId + "/video/" + videoId + "/quizQuestion");

		if (response.getStatusCode() == 500) {
			logger.info("视频下测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "classId不存在")
	public void verifyClassId_002() {
		classId = 113602;
		videoId = 113602;
		
		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);
		
		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classId + "/video/" + videoId + "/quizQuestion");

		if (response.getStatusCode() == 500) {
			logger.info("视频下测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 4, description = "videoId不存在")
	public void verifyClassId() {
		classId = 3435;
		videoId = 3435;
		
		JSONArray questionArray = new JSONArray();
		questionArray.add(92008);
		
		JSONArray courseArray = new JSONArray();
		courseArray.add(2725);

		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes/" + classId + "/video/" + videoId + "/quizQuestion");

		if (response.getStatusCode() == 500) {
			logger.info("视频下测验接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
