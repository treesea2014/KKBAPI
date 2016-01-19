package org.gxb.server.api.classes.video;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;

/*
 * 192.168.30.33:8080/gxb-api/class_video_time_node?classId=1&videoId=2
 */
public class GetVideoTimeNodeList {
	private static Logger logger = LoggerFactory.getLogger(GetVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {	
		int classId = 130;
		int videoId = 10;
		JSONArray questionJson = new JSONArray();
		questionJson.add(397);
		questionJson.add(398);
		
		JSONArray jsonArray1 = new JSONArray();
		jsonArray1.add(1198);
		jsonArray1.add(1199);
		jsonArray1.add(1200);
		jsonArray1.add(1201);
		
		JSONArray jsonArray2 = new JSONArray();
		jsonArray2.add(1202);
		jsonArray2.add(1203);
		
		JSONArray jsonArray3 = new JSONArray();
		jsonArray3.add(jsonArray1);
		jsonArray3.add(jsonArray2);
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","class_video_time_node?classId=" + classId + "&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次的单个视频的所有时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("videoTimeNodeId", Matchers.hasItems(37,38,39,40,41,42))
		.body("classId", Matchers.hasItems(classId))
		.body("videoId", Matchers.hasItems(videoId))
		.body("timeNode", Matchers.hasItems(30))
		.body("questionList.questionId", Matchers.hasItems(questionJson))
		.body("questionList.optionList.questionOptionId", Matchers.hasItems(jsonArray3));
	}
	
	@Test(priority = 2, description = "classid不存在")
	public void verifyClassId_001() {	
		int classId = 9999;
		int videoId = 10;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","class_video_time_node?classId=" + classId + "&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次的单个视频的所有时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "classid为空")
	public void verifyClassId_002() {	
		int videoId = 10;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","class_video_time_node?classId=&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次的单个视频的所有时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 4, description = "videoId不存在")
	public void verifyVideoId_001() {	
		int classId = 130;
		int videoId = 9999;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","class_video_time_node?classId=" + classId + "&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次的单个视频的所有时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 5, description = "videoId为空")
	public void verifyVideoId_002() {	
		int classId = 130;
		int videoId = 9999;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","class_video_time_node?classId=" + classId + "&videoId=" );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次的单个视频的所有时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
