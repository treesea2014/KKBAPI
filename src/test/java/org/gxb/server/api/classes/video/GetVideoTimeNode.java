package org.gxb.server.api.classes.video;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.classes.courseware.DeleteCourseware;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

import net.sf.json.JSONArray;

/*
 * 192.168.30.33:8080/gxb-api/class_video_time_node/30
 * class_video_time_node,class_question_relate,question,question_option
 */
public class GetVideoTimeNode {
	private static Logger logger = LoggerFactory.getLogger(GetVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {	
		int videoTimeNodeId = 29;
		
		JSONArray jsonArray1 = new JSONArray();
		jsonArray1.add(785);
		jsonArray1.add(786);
		jsonArray1.add(787);
		jsonArray1.add(788);
		
		JSONArray jsonArray2 = new JSONArray();
		jsonArray2.add(789);
		jsonArray2.add(790);
		jsonArray2.add(791);
		jsonArray2.add(792);
		
		JSONArray jsonArray3 = new JSONArray();
		jsonArray3.add(793);
		jsonArray3.add(794);
		jsonArray3.add(795);
		jsonArray3.add(796);
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/class_video_time_node/" + videoTimeNodeId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询单个视频时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("videoTimeNodeId", equalTo(videoTimeNodeId))
		.body("videoId", equalTo(1))
		.body("timeNode", equalTo(11))
		.body("questionCount", equalTo(3))
		.body("questionList.questionId", Matchers.hasItems(277,278,279))
		.body("questionList.optionList.questionOptionId", Matchers.hasItems(jsonArray1,jsonArray2,jsonArray3));
	}
	
	@Test(priority = 2, description = "videoTimeNode不存在")
	public void verifyvideoTimeNode_001() {	
		int videoTimeNodeId = 999999;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/class_video_time_node/" + videoTimeNodeId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询单个视频时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "videoTimeNode无效")
	public void verifyvideoTimeNode_002() {	
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/class_video_time_node/" + "q1" );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询单个视频时间点详情（包括题目选项）接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
