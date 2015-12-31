package org.gxb.server.api.restassured.course.course;

import java.util.HashMap;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.11.06
 * @decription 保存最近一次的学习
 * http://localhost:8088/gxb-api/course?loginUserId=123456&tenantId=1
 */
public class SaveBasicInfo {

	@Test(description = "正常", priority = 1)
	public void test() {
		JSONObject jo = new JSONObject();
		 HashMap<String, String> courseInfo = new HashMap<String, String>();  
		 courseInfo.put("description", "详细介绍啊");  
		 
		 JSONArray categoryListArray = new JSONArray();
		 HashMap<String, Integer> categoryList = new HashMap<String, Integer>();  
		 categoryList.put("categoryId", 1); 
		 categoryListArray.add(JSONObject.fromObject(categoryList));
		 
		 JSONArray instructorListArray = new JSONArray();
		 HashMap<String, Object> instructorList = new HashMap<String, Object>();  
		 instructorList.put("instructorId", 1); 
		 instructorList.put("name", "api测试"); 
		 instructorListArray.add(JSONObject.fromObject(instructorList));

		jo.put("name", "课程名称");
		jo.put("intro", "课程简介");
		jo.put("courseInfo", JSONObject.fromObject(courseInfo));
		jo.put("tenantId", 1);
		jo.put("categoryList", categoryListArray);
		jo.put("instructorList", instructorListArray);
		
		Response response = TestConfig.postOrPutExecu("post",
				"course?loginUserId=123456&tenantId=1", jo);
		response.then().log().all().assertThat()
				.statusCode(200)
				.body("name", equalTo("课程名称"))
				.body("intro", equalTo("课程简介"))
				.body("tenantId", equalTo(1))
				//.body("categoryList", Matchers.hasItem(categoryList))
		
				;

	}
}
