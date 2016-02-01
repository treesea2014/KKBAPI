package org.gxb.server.api.lms.submission;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class UpdatePeerReview {
	private static Logger logger = LoggerFactory.getLogger(UpdatePeerReview.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int peerReviewId = 4600;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("score", 70);
		jsonObject.put("gradedComment", "good");

		Response response = TestConfig.postOrPutExecu("put", "/peerReview/" + peerReviewId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改peerreview接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "修改peerreview失败");
	}
	
	//failed
	@Test(priority = 2, description = "peerReviewId不存在")
	public void verifyPeerReviewId() {
		peerReviewId = 99999;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("score", 70);
		jsonObject.put("gradedComment", "good");

		Response response = TestConfig.postOrPutExecu("put", "/peerReview/" + peerReviewId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改peerreview接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400);
	}
	
	//failed
	@Test(priority = 3, description = "score为负数")
	public void verifyScore_001() {
	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("score", -1);
		jsonObject.put("gradedComment", "good");

		Response response = TestConfig.postOrPutExecu("put", "/peerReview/" + peerReviewId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改peerreview接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400);
	}
	
	@Test(priority = 4, description = "score为其他类型")
	public void verifyScore_002() {
	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("score", "qw1212");
		jsonObject.put("gradedComment", "good");

		Response response = TestConfig.postOrPutExecu("put", "/peerReview/" + peerReviewId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改peerreview接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 5, description = "score为空")
	public void verifyScore_003() {
	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("score", null);
		jsonObject.put("gradedComment", "good");

		Response response = TestConfig.postOrPutExecu("put", "/peerReview/" + peerReviewId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改peerreview接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("InvalidFormatException"));
	}
	
	//failed
	@Test(priority = 6, description = "gradedComment为空")
	public void verifyGradedComment() {
	
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("score", 70);
		jsonObject.put("gradedComment", null);

		Response response = TestConfig.postOrPutExecu("put", "/peerReview/" + peerReviewId,
				jsonObject);

		if (response.getStatusCode() == 500) {
			logger.info("修改peerreview接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400);
	}
}
