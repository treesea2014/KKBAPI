package org.gxb.server.api.classes.groupuser;

import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.gxb.server.api.TestConfig;
import org.gxb.server.api.classes.video.GetVideoTimeNode;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/*
 * 192.168.30.33:8080/gxb-api/class_group_user?filter=classId:1,query:1&paging=curPage:1,pageSize:6
 */
public class GetGroupUser {
	private static Logger logger = LoggerFactory.getLogger(GetVideoTimeNode.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public int classId;
	public String query;

	@BeforeMethod
	public void InitiaData() {
		classId = 1;
		query = "1";
	}

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(2))
				.body("dataList.groupUserId", Matchers.hasItems(331850, 6))
				.body("dataList.classId", Matchers.hasItems(1)).body("dataList.studentUserId", Matchers.hasItems(2, 7))
				.body("dataList.student.studentId", Matchers.hasItems(2))
				.body("dataList.student.no", Matchers.hasItems("2"))
				.body("dataList.user.name", Matchers.hasItems("shuhai22e"))
				.body("dataList.user.mobile", Matchers.hasItems("111"))
				.body("dataList.user.email", Matchers.hasItems("xli01@gaoxiaobang.com"));
	}

	@Test(priority = 2, description = "classId不存在")
	public void verifyClassId_001() {
		classId = 999999;

		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}

	@Test(priority = 3, description = "classId为空")
	public void verifyClassId_002() {
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(2));
	}
	
	@Test(priority = 4, description = "根据学号查询")
	public void verifyQuery_001() {
		query = "2";
		
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200)
				.body("dataList.groupUserId", Matchers.hasItems(331850, 6))
				.body("dataList.classId", Matchers.hasItems(1)).body("dataList.studentUserId", Matchers.hasItems(2, 7))
				.body("dataList.student.studentId", Matchers.hasItems(2))
				.body("dataList.student.no", Matchers.hasItems("2"))
				.body("dataList.user.name", Matchers.hasItems("shuhai22e"))
				.body("dataList.user.mobile", Matchers.hasItems("111"))
				.body("dataList.user.email", Matchers.hasItems("xli01@gaoxiaobang.com"));
	}
	
	@Test(priority = 5, description = "根据name查询")
	public void verifyQuery_002() {
		query = "姓名";
		
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(1))
				.body("dataList.groupUserId", Matchers.hasItems(331850))
				.body("dataList.classId", Matchers.hasItems(1)).body("dataList.studentUserId", Matchers.hasItems(2))
				.body("dataList.student.studentId", Matchers.hasItems(2))
				.body("dataList.student.no", Matchers.hasItems("2"))
				.body("dataList.user.name", Matchers.hasItems("测试真是姓名2"))
				.body("dataList.user.mobile", Matchers.hasItems("wang01"))
				.body("dataList.user.email", Matchers.hasItems("770608728@qq.com"));
	}
	
	@Test(priority = 6, description = "根据mobile查询")
	public void verifyQuery_003() {
		query = "111";
		
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(1))
				.body("dataList.groupUserId", Matchers.hasItems(6))
				.body("dataList.classId", Matchers.hasItems(1)).body("dataList.studentUserId", Matchers.hasItems(7))
				.body("dataList.user.name", Matchers.hasItems("shuhai22e"))
				.body("dataList.user.mobile", Matchers.hasItems("111"))
				.body("dataList.user.email", Matchers.hasItems("xli01@gaoxiaobang.com"));
	}
	
	@Test(priority = 7, description = "根据email查询")
	public void verifyQuery_004() {
		query = "xli01";
		
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(1))
		.body("dataList.groupUserId", Matchers.hasItems(6))
		.body("dataList.classId", Matchers.hasItems(1)).body("dataList.studentUserId", Matchers.hasItems(7))
		.body("dataList.user.name", Matchers.hasItems("shuhai22e"))
		.body("dataList.user.mobile", Matchers.hasItems("111"))
		.body("dataList.user.email", Matchers.hasItems("xli01@gaoxiaobang.com"));
	}
	
	@Test(priority = 8, description = "query不存在")
	public void verifyQuery_005() {
		query = "1qwqw";
		
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
	
	@Test(priority = 9, description = "query为空")
	public void verifyQuery_006() {
		query = "";
		
		Response getResponse = TestConfig.getOrDeleteExecu("get",
				"/class_group_user?filter=classId:" + classId + ",query:" + query + "&paging=curPage:1,pageSize:6");
		if (getResponse.getStatusCode() == 500) {
			logger.info("查询班次下视频详情包括时间点接口##" + getResponse.prettyPrint());
		}

		getResponse.then().assertThat().statusCode(200).body("paging.total", equalTo(3));
	}
}
