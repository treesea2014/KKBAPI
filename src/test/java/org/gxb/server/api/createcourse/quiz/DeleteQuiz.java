package org.gxb.server.api.createcourse.quiz;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.sql.OperationTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

/*
 * ----根据id获取quiz信息（包含题目选项）
 * http://192.168.30.33:8080/gxb-api/course/quiz/2?loginUserId=1&tenantId=1
 * question,course_quiz,course_chapter,course_question_relate
 */
public class DeleteQuiz {	
	private static Logger logger = LoggerFactory.getLogger(DeleteQuiz.class);
	private OperationTable operationTable = new OperationTable();
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	public String url;
	private String quiz;
	private int flag = 1;

	@BeforeMethod
	public void InitiaData() {
		url = path + basePath + "/course/";
		quiz = "4";
	}
	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		try {
			operationTable.updateCourseQuiz(Integer.parseInt(quiz), flag);
			operationTable.updateCourseChapter(Integer.parseInt(quiz), flag,"Quiz");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// TODO Auto-generated catch block
			logger.info(e.toString());
		}
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除quiz接口##verifyCorrectParams##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(response.prettyPrint()), true, "删除quiz失败");
	}

	@Test(priority = 2, description = "Quiz已删除")
	public void verifyDeletedQuiz() {	
		String paramUrl = url  + "quiz/"+ quiz + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除quiz接口##verifyDeletedQuiz##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "quiz不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "Quiz不存在")
	public void verifyNotExistQuiz() {	
		quiz = "999999";
				
		String paramUrl = url  + "quiz/"+ quiz + "?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除quiz接口##verifyDeletedQuiz##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "quiz不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "无效的Quiz")
	public void verifyInvalidQuiz() {
		quiz = "q9";
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课程接口##verifyInvalidVideoTimeNode##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	@Test(priority = 5, description = "Quiz为空")
	public void verifyEmptyQuiz() {
		quiz = "";
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/quiz/" + quiz + "?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除课程接口##verifyInvalidVideoTimeNode##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
