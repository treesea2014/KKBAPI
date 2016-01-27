package org.gxb.server.api.lms.post;

import static org.hamcrest.Matchers.equalTo;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * http://192.168.30.33:8080/gxb-api/post?filter=topicId:130,voteLogUserId=123456
 */
public class GetPost {
	private static Logger logger = LoggerFactory.getLogger(GetPost.class);
	private OperationTable operationTable = new OperationTable();
	private int topicId;

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		topicId = 130;
		int userid = 123456;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/post?filter=topicId:" + topicId + ",voteLogUserId=" + userid);

		if (response.getStatusCode() == 500) {
			logger.info("post查询接口##" + response.prettyPrint());
		}
		int expectCount = 0;
		
		try {
			expectCount = operationTable.selectPost(topicId,userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(expectCount))
				.body("dataList.tenantId", Matchers.hasItems(1))
				.body("dataList.postId", Matchers.hasItems(675446, 675447))
				.body("dataList.topicId", Matchers.hasItems(topicId))
				.body("dataList.userId", Matchers.hasItems(1300, 1)).body("dataList.classId", Matchers.hasItems(21));
	}
	
	@Test(priority = 2, description = "topicId不存在")
	public void verifyNotExistTopicId() {
		topicId = 999999;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/post?filter=topicId:" + topicId + ",voteLogUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("post查询接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
	
	//failed
	@Test(priority = 3, description = "topicid为空")
	public void verifyEmptyTopicId() {
		int userid = 123456;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/post?filter=topicId:,voteLogUserId=" + userid);

		if (response.getStatusCode() == 500) {
			logger.info("post查询接口##" + response.prettyPrint());
		}
		int expectCount = 0;
		try {
			expectCount = operationTable.selectPost(topicId,userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(expectCount));
	}
}
