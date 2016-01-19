package org.gxb.server.api.classes.video;

import java.util.ResourceBundle;
import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * 192.168.30.33:8080/gxb-api/classes/video?classId=130&videoId=10
 */
public class GetClassesVideoDetail {
	private static Logger logger = LoggerFactory.getLogger(GetVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {	
		int classId = 130;
		int videoId = 10;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/classes/video?classId=" + classId + "&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("courseId", equalTo(1))
		.body("videoId", equalTo(videoId))
		.body("title", equalTo("军事理论绪论"))
		.body("classTimeNodeList.videoTimeNodeId", Matchers.hasItems(39,40,41,42,37,38))
		.body("classTimeNodeList.classId", Matchers.hasItems(130))
		.body("classTimeNodeList.videoId", Matchers.hasItems(10))
		.body("classTimeNodeList.timeNode", Matchers.hasItems(30));
	}
	
	//failed
	@Test(priority = 2, description = "classid不存在")
	public void verifyClassId_001() {	
		int classId = 999999;
		int videoId = 10;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/classes/video?classId=" + classId + "&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3, description = "classid为空")
	public void verifyClassId_002() {
		int classId = 999999;
		int videoId = 10;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/classes/video?classId=&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
	
	@Test(priority = 4, description = "videoId不存在")
	public void verifyVideoId_001() {	
		int classId = 130;
		int videoId = 9999;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/classes/video?classId=" + classId + "&videoId=" + videoId );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 5, description = "videoId为空")
	public void verifyVideoId_002() {	
		int classId = 130;
		
		Response getResponse = TestConfig.getOrDeleteExecu("get","/classes/video?classId=" + classId + "&videoId=" );
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(400).body("type", equalTo("NullPointerException"));
	}
}
