package org.gxb.server.api.lms.submission;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

public class GetSubmission {
	private static Logger logger = LoggerFactory.getLogger(GetSubmission.class);

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int submissionId = 2899349;

		Response getResponse = TestConfig.getOrDeleteExecu("get", "/submission/" + submissionId);

		if (getResponse.getStatusCode() == 500) {
			logger.info("id获取submission接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("submissionId", equalTo(submissionId))
				.body("contextId", equalTo(3276)).body("contextType", equalTo("Course"))
				.body("submittedId", equalTo(4230)).body("submittedType", equalTo("Assignment"))
				.body("assetList.title", Matchers.hasItems("assignment_title_001"))
				.body("assetList.type", Matchers.hasItems("AssignmentAttachment"));
	}
	
	@Test(priority = 1, description = "submissionId不存在")
	public void verifySubmissionId() {
		int submissionId = 10;

		Response getResponse = TestConfig.getOrDeleteExecu("get", "/submission/" + submissionId);

		if (getResponse.getStatusCode() == 500) {
			logger.info("id获取submission接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200);
	}
}
