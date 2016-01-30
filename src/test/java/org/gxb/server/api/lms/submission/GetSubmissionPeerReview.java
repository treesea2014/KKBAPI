package org.gxb.server.api.lms.submission;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

public class GetSubmissionPeerReview {
	private static Logger logger = LoggerFactory.getLogger(GetSubmissionPeerReview.class);

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int submissionId = 33850;

		Response response = TestConfig.getOrDeleteExecu("get", "/submission/" + submissionId + "/peerReview");

		if (response.getStatusCode() == 500) {
			logger.info("提交的互评列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("submissionId", Matchers.hasItems(submissionId))
				.body("peerReviewId", Matchers.hasItems(13,14,15,16)).body("assignmentId", Matchers.hasItems(3303));
	}
	
	@Test(priority = 2, description = "submissionId不存在")
	public void verifySubmissionId() {
		int submissionId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get", "/submission/" + submissionId + "/peerReview");

		if (response.getStatusCode() == 500) {
			logger.info("提交的互评列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
