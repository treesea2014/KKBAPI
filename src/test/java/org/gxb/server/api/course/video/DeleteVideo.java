package org.gxb.server.api.course.video;

import static org.hamcrest.Matchers.equalTo;
import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

/*
 * ----删除视频
 *http://192.168.30.33:8080/gxb-api/course/chapter/6/video?loginUserId=123456&tenantId=111
 * course_chapter video
 */
public class DeleteVideo {
	private static Logger logger = LoggerFactory.getLogger(DeleteVideo.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");
	private int chapterId;
	private int itemId;
	private int userId;

	@BeforeMethod
	public void InitiaData() {
		itemId = 210;
		userId = 2100;
	}
	
	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		String title = "章节2001";
		int position = 20;
		int contentId = 10;

		JSONObject chapterJson = new JSONObject();
		chapterJson.put("title", title);
		chapterJson.put("position", position);
		chapterJson.put("contentId", contentId);

		Response response = TestConfig.postOrPutExecu("post", "/course/item/" + itemId + "/video?loginUserId=" + userId,
				chapterJson);
		chapterId = response.jsonPath().get("chapterId");
		
		//删除video
		Response deleteResponse = TestConfig.getOrDeleteExecu("delete",
				"/course/chapter/" + chapterId + "/video?loginUserId=123456&tenantId=1");

		if (deleteResponse.getStatusCode() == 500) {
			logger.info("删除视频接口##" + response.prettyPrint());
		}

		deleteResponse.then().assertThat().statusCode(200);
		Assert.assertEquals(Boolean.parseBoolean(deleteResponse.prettyPrint()), true, "删除视频失败");
		
		//再次删除video
		String paramUrl = path + basePath + "/course/chapter/"+ chapterId + "/video?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除视频接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "chapter不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}

	@Test(priority = 2, description = "chapterid非video类型")
	public void verifyOtherChapter() {	
		chapterId = 210;
		
		String paramUrl = path + basePath + "/course/chapter/"+ chapterId + "/video?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除视频接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "不是视频不能删除", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 3, description = "chapter不存在")
	public void verifyNotExistChapter() {	
		chapterId = 999999;
		
		String paramUrl = path + basePath + "/course/chapter/"+ chapterId + "/video?loginUserId=1&tenantId=1";
		String strMsg = httpRequest.sendHttpDelete(paramUrl);
		String[] data = strMsg.split("&");
		JSONObject jsonobject = JSONObject.fromObject(data[1]);

		if (Integer.parseInt(data[0]) == 500) {
			logger.info("删除视频接口##" + strMsg);
		}

		Assert.assertEquals(jsonobject.get("code").toString(), "1000", "状态码不正确");
		Assert.assertEquals(jsonobject.get("message").toString(), "chapter不存在", "message提示信息不正确");
		Assert.assertEquals(jsonobject.get("type").toString(), "ServiceException", "type提示信息不正确");
	}
	
	@Test(priority = 4, description = "无效的chapter")
	public void verifyInvalidChapter() {
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/chapter/" + "q1" + "/video?loginUserId=123456&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
	
	//failed
	@Test(priority = 5, description = "userid为空")
	public void verifyEmptyUserId() {
		chapterId = 185;
		
		Response response = TestConfig.getOrDeleteExecu("delete",
				"/course/chapter/" + chapterId + "/video?loginUserId=&tenantId=1");

		if (response.getStatusCode() == 500) {
			logger.info("删除视频接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(400).body("type", equalTo("NumberFormatException"));
	}
}
