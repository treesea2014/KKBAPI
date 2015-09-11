package org.gxb.server.api.coursesys.topic;

import static org.hamcrest.Matchers.equalTo;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;

public class ModifyTopicTest {
	private int userid = 1856;
	private int tenantsid = 10;
	private int topicid = 1346;
	private String access_token = TestConfig.getTokenByUsidAndTeid(userid, tenantsid);

	/*
	 * 输入正确的参数
	 */
	@Test(priority = 1)
	public void VerifyCorrectData() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true)).body("data", equalTo(1));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 2)
	public void VerifyInvalidAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=adf11", jsonObject);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 3)
	public void VerifyNullAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + null, jsonObject);
		response.then().assertThat().statusCode(401).body("status", equalTo(false)).body("message",
				equalTo("无效的access_token"));
	}
	
	/*
	 * 验证access_token
	 */
	@Test(priority = 4)
	public void VerifyEmptyAccesstoken() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=", jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false)).body("message",
				equalTo("token不能为空"));
	}
	
	/*
	 * 验证topics
	 */
	@Test(priority = 5)
	public void VerifyInvalidTopicid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + "a1" + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}
	
	/*
	 * 验证topics
	 */
	@Test(priority = 6)
	public void VerifyEmptyTopicid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + null + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(404);
	}
	
	/*
	 * 验证context_id   //failed
	 */
	@Test(priority = 7)
	public void VerifyInvalidContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", "a1");
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id  //failed
	 */
	@Test(priority = 8)
	public void VerifyNullContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", null);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id  //failed
	 */
	@Test(priority = 9)
	public void VerifyEmptyContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_id   //failed
	 */
	@Test(priority = 10)
	public void VerifyNegativeContextid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", -1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_type  //failed
	 */
	@Test(priority = 11)
	public void VerifyNullContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", null);
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证context_type  //failed
	 */
	@Test(priority = 12)
	public void VerifyEmptyContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证title  //failed
	 */
	@Test(priority = 13)
	public void VerifyNullTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", null);
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证title  //failed
	 */
	@Test(priority = 14)
	public void VerifyEmptyTitle() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证body  //failed
	 */
	@Test(priority = 15)
	public void VerifyNullBody() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", null);
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证body    //failed
	 */
	@Test(priority = 16)
	public void VerifyEmptyBody() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 17)
	public void VerifyNullCContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", null);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 18)
	public void VerifyEmptyCContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 19)
	public void VerifyInvalidCContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", "a1");
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	
	/*
	 * 验证chapter-context_id  //failed
	 */
	@Test(priority = 20)
	public void VerifyNegativeCContextId() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", -1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	
	/*
	 * 验证chapter-context_type  //failed
	 */
	@Test(priority = 21)
	public void VerifyNullCContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", null);
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证chapter-context_type  //failed
	 */
	@Test(priority = 22)
	public void VerifyEmptyCContextType() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证userid  //failed
	 */
	@Test(priority = 23)
	public void VerifyNullUserid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", null);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证userid  //failed
	 */
	@Test(priority = 24)
	public void VerifyEmptyUserid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证userid  //failed
	 */
	@Test(priority = 25)
	public void VerifyIsNotExistUserid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", 999999);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(200).body("status", equalTo(true));
	}
	
	/*
	 * 验证userid  //failed
	 */
	@Test(priority = 26)
	public void VerifyInvalidUserid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", "a1");
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + topicid + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400).body("status", equalTo(false));
	}
	
	/*
	 * 验证topics
	 */
	@Test(priority = 27)
	public void VerifyIsNotExistTopicid() {
		JSONObject chapterjsonObject = new JSONObject();
		chapterjsonObject.put("context_id", 1);
		chapterjsonObject.put("context_type", "Course");
		chapterjsonObject.put("score", 100);
		chapterjsonObject.put("position", 1);
		chapterjsonObject.put("status", "active");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("context_id", 1);
		jsonObject.put("context_type", "Course");
		jsonObject.put("user_id", userid);
		jsonObject.put("forum_id", null);
		jsonObject.put("editor_id", 754);
		jsonObject.put("title", "web前端");
		jsonObject.put("body", "在网站的网页设计中有很多的类型");
		jsonObject.put("permalink", null);
		jsonObject.put("score", 100);
		jsonObject.put("position", 1);
		jsonObject.put("status", "online");
		jsonObject.put("chapter", chapterjsonObject);

		Response response = TestConfig.postOrPutExecu("put",
				"/topics/" + 999999 + "?access_token=" + access_token, jsonObject);
		response.then().assertThat().statusCode(400);
	}
}
