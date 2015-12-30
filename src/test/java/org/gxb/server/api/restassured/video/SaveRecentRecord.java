package org.gxb.server.api.restassured.video;

import com.jayway.restassured.response.Response;

import net.sf.json.JSONObject;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.11.06
 * @decription 保存最近一次的学习 
 * http://123.57.210.46:8080/gxb-api/learn/log/111861/user?userId=1040379&schoolId=218&redirectUri=/classes/2086%23/chapters/111861
 */
public class SaveRecentRecord {

	@Test(description = "正常", priority = 1)
	public void test() {
		JSONObject jo = new JSONObject();
		Response response = TestConfig.postOrPutExecu("post", "/learn/log/111861/user?userId=1040379&schoolId=218&redirectUri=/classes/2086%23/chapters/111861", jo);
		response.then().log().all()
				.assertThat().statusCode(200);
		
	}
}
