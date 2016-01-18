package org.gxb.server.api.course.course;

import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;

/*
 * ----课程资源数量
 * http://192.168.30.33:8080/gxb-api/course/1/contentCount?loginUserId=123456
 * course_page,course_topic,course_courseware
 */
public class CourseResourceTest {
	private static Logger logger = LoggerFactory.getLogger(CourseResourceTest.class);
	private OperationTable operationTable = new OperationTable();
	private int courseid;
	private int flag = 1;

	@BeforeMethod
	public void InitiaData() {
		courseid = 1;
	}

	// pass
	@Test(priority = 1, description = "输入正确的参数")
	public void verifyCorrectParams() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + courseid + "/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyCorrectParams##" + response.prettyPrint());
		}
		List<Integer> arrayList = new ArrayList<Integer>();

		try {
			Map<String, Integer> hashmap = operationTable.selectCourseChapter(courseid, flag);
			Iterator iter = hashmap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString();
				int val = Integer.parseInt(entry.getValue().toString());
				arrayList.add(val);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.then().assertThat().statusCode(200).body("pageCount", equalTo(arrayList.get(0)))
				.body("assignmentCount", equalTo(arrayList.get(1))).body("videoCount", equalTo(arrayList.get(2)));

	}

	@Test(priority = 2, description = "course不存在")
	public void verifyNotExistCourse() {
		courseid = 999999;

		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + courseid + "/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyNotExistCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(200);
		}
	}

	@Test(priority = 3, description = "无效的course")
	public void verifyInvalidCourse() {
		Response response = TestConfig.getOrDeleteExecu("get",
				"/course/" + "12qw" + "/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyNotExistCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}

	@Test(priority = 4, description = "course为空")
	public void verifyEmptyCourse() {
		Response response = TestConfig.getOrDeleteExecu("get", "/course/contentCount?loginUserId=123456");

		if (response.getStatusCode() == 500) {
			logger.info("课程资源数量接口##verifyNotExistCourse##" + response.prettyPrint());
		} else {
			response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
		}
	}
}
