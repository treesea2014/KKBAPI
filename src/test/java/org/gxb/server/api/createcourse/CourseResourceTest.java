package org.gxb.server.api.createcourse;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

/*
 * ----发布课程
 * http://localhost:8088/gxb-api/course/1/status/publish?loginUserId=123456
 * course
 * 状态: 10:未发布  20:预发布 30:已发布  40:停用
 */
public class CourseResourceTest {

	private OperationTable operationTable =  new OperationTable();
	private int courseid;


	@BeforeMethod
	public void InitiaData() {
		courseid = 10;
	}

	@Test(priority = 1, description = "输入正确的参数")
	public void verifyCorrectParams() {
		 Response response = TestConfig.getOrDeleteExecu("get", "/gxb-api/course/"+ courseid +"/contentCount?loginUserId=123456");
		response.then().assertThat().statusCode(200).body("message", equalTo(""));
	}
}
