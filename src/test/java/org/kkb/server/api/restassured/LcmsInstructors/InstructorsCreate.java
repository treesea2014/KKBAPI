package org.kkb.server.api.restassured.LcmsInstructors;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by www on 2015/7/27.
 * 创建讲师
 */
public class InstructorsCreate {

    static Integer Course_id=0;

    static String access_token= TestConfig.getTokenbyUserID();

    //新增讲师--无Token
    @Test(priority=1)
    public void testPostInstructorsWithoutToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化POST");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("desc", "");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");


        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/instructors?access_token=", jsonObject);
        response.then().
                assertThat().statusCode(400).body("message", equalTo("token不能为空"));

    }

    @Test(priority=2)
    public void testPostInstructorsWithIncorrectToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "苏平");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("desc", "");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");


        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/instructors?access_token=f548b508f351231c6f6c8efa2cd44531", jsonObject);
        response.then().
                assertThat().statusCode(401).body("message", equalTo("无效的access_token"));

    }

    @Test(priority=3)
    public void testPostInstructorsWithCorrectToken(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "API自动测试");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("desc", "");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");


        Response response = TestConfig.postOrPutExecu("post", "tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                assertThat().statusCode(200).body("message", equalTo("success"));

    }

    //必输项校验——讲师姓名
    @Test(priority=4)
    public void testPostInstructorsWithoutName(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");


        Response response = TestConfig.postOrPutExecu("post", "tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("讲师姓名不能为空")
                );

    }

    //必输项校验——讲师介绍
    @Test(priority=5)
    public void testPostInstructorsWithoutDesc(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "苏平");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");


        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message",equalTo("讲师介绍不能为空")
                );

    }

    //必输项校验——讲师头衔
    @Test(priority=6)
    public void testPostInstructorsWithoutIntro(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "苏平");
        jsonObject.put("title", "");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");


        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message",equalTo("讲师头衔不能为空"));

    }

    //必输项校验——讲师头像
    @Test(priority=7)
    public void testPostInstructorsWithoutAvatar(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "苏平");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("avatar","");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");


        Response response = TestConfig.postOrPutExecu("post", "/tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message",equalTo("照片不能为空"));

    }

}
