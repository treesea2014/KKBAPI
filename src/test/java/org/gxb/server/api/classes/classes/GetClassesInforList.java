package org.gxb.server.api.classes.classes;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * http://192.168.30.33:8080/gxb-api/classes?filter=status:10,className:3&paging=curPage:2,pageSize:2&loginUserId=123456&tenantId=1
 * class
 */
public class GetClassesInforList {
	private static Logger logger = LoggerFactory.getLogger(GetClassesInforList.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	
	//failed 没有根据新建，修改时间进行排序
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes?filter=status:10,className:classes&paging=curPage:1,pageSize:6&loginUserId=12&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询班次信息列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(4))
		.body("dataList.classId", Matchers.hasItems(70,74,82,83))
		.body("dataList.courseId", Matchers.hasItems(1,1,705,705))
		.body("dataList.userId", Matchers.hasItems(1001)).body("dataList.editorId", Matchers.hasItems(1001))
		.body("dataList.tenantId", Matchers.hasItems(1)).body("dataList.className", Matchers.hasItems("classes_test1001"))
		.body("dataList.intro", Matchers.hasItems("intro_test1001")).body("dataList.classType", Matchers.hasItems("10"))
		.body("dataList.useType", Matchers.hasItems("10")).body("dataList.status", Matchers.hasItems("10"));
	}
	
	@Test(priority = 2, description = "className不存在")
	public void verifyInvalidClassName_001() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes?filter=status:10,className:classestest&paging=curPage:1,pageSize:6&loginUserId=12&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询班次信息列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
	
	@Test(priority = 3, description = "classname为空")
	public void verifyInvalidClassName_002() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes?filter=status:10,className:&paging=curPage:1,pageSize:6&loginUserId=12&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询班次信息列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(54));
	}
	
	@Test(priority = 4, description = "status不存在")
	public void verifyInvalidStatus_001() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes?filter=status:50,className:classes&paging=curPage:1,pageSize:6&loginUserId=12&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询班次信息列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(0));
	}
	
	@Test(priority = 5, description = "status为空")
	public void verifyInvalidStatus_002() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes?filter=status:,className:s&paging=curPage:1,pageSize:6&loginUserId=12&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询班次信息列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(5));
	}
	
	@Test(priority = 6, description = "status为30")
	public void verifyInvalidStatus_003() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes?filter=status:30,className:&paging=curPage:1,pageSize:6&loginUserId=12&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询班次信息列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(3));
	}
	
	@Test(priority = 7, description = "status为40")
	public void verifyInvalidStatus_004() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/classes?filter=status:40,className:&paging=curPage:1,pageSize:6&loginUserId=12&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("查询班次信息列表接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200).body("paging.total", equalTo(2));
	}
}
