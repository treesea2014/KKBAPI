package org.kkb.server.api.restassured.LcmsInstructors;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.kkb.server.api.TestConfig;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by www on 2015/7/27.
 */
public class InstructorsUpdate {

    String access_token= TestConfig.getTokenbyUserID();
    static Integer instructor_id =0;

    @Test(priority=1)
    public void testUpdatetInstructors(){

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化PUT");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response response = TestConfig.postOrPutExecu("post", "tenants/1/instructors?access_token=" + access_token, jsonObject);
        response.then().
                //log().all().
                assertThat().statusCode(200).body("message", equalTo("success"));

        String body = response.body().asString();

        instructor_id = JsonPath.with(body).get("data");
        if (instructor_id == 0) throw new AssertionError();

        jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化PUTPUT");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员PUT");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        response = TestConfig.postOrPutExecu("put", "/instructors/" + instructor_id + "?access_token=" + access_token, jsonObject);
        response.then().
                //log().all().
                assertThat().statusCode(200).
                body("status", equalTo(true)).
                body("message", equalTo("success")).
                body("data",equalTo(0));

    }

    @Test(priority=2)
    public void testUpdatetInstructorsWithoutToken() {

        JSONObject jsonObject=new JSONObject();

        jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化PUTPUT");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员PUT");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response  response = TestConfig.postOrPutExecu("put", "/instructors/" + instructor_id + "?access_token=", jsonObject);
        response.then().
                //log().all().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("token不能为空"));

    }

    @Test(priority=3)
    public void testUpdatetInstructorsWithIncorrectToken() {

        JSONObject jsonObject=new JSONObject();

        jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化PUTPUT");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员PUT");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response  response = TestConfig.postOrPutExecu("put", "/instructors/" + instructor_id + "?access_token=123456789", jsonObject);
        response.then().
                //log().all().
                assertThat().statusCode(401).
                body("status", equalTo(false)).
                body("message", equalTo("无效的access_token"));

    }

    @Test(priority=4)
    public void testUpdatetInstructorsWithoutName() {

        JSONObject jsonObject=new JSONObject();

        jsonObject=new JSONObject();

        jsonObject.put("name", "");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员PUT");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response  response = TestConfig.postOrPutExecu("put", "/instructors/" + instructor_id + "?access_token=" + access_token, jsonObject);
        response.then().
                //log().all().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("讲师姓名不能为空"));

    }

    @Test(priority=5)
    public void testUpdatetInstructorsWithoutTitle() {

        JSONObject jsonObject=new JSONObject();

        jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化PUTPUT");
        jsonObject.put("title", "");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员PUT");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response  response = TestConfig.postOrPutExecu("put", "/instructors/" + instructor_id + "?access_token=" + access_token, jsonObject);
        response.then().
                log().all().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("讲师头衔不能为空"));

    }

    @Test(priority=6)
    public void testUpdatetInstructorsWithoutIntro() {

        JSONObject jsonObject=new JSONObject();

        jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化PUTPUT");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("intro", "");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员PUT");
        jsonObject.put("avatar","234.jpg");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response  response = TestConfig.postOrPutExecu("put", "/instructors/" + instructor_id + "?access_token=" + access_token, jsonObject);
        response.then().
                log().all().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("讲师介绍不能为空"));

    }

    @Test(priority=7)
    public void testUpdatetInstructorsWithoutAvatar() {

        JSONObject jsonObject=new JSONObject();

        jsonObject=new JSONObject();

        jsonObject.put("name", "API自动化PUTPUT");
        jsonObject.put("title", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("intro", "北京航空航天大学经济管理学院讲师、副教授PUT");
        jsonObject.put("desc","北京航空航天大学经济管理学院讲师、副教授，北航EMBA教育中心常务副主任、北航MBA教育中心主任、北航中澳国际MBA项目主任经管学院学生职业发展中心主任\\r\\n国家一级职业指导师、心理咨询师，GCDF（全球职业规划师），中科院心理所EAP签约心理咨询师，北京市教委高校青年教师教学技能培训专家组成员PUT");
        jsonObject.put("avatar","");
        jsonObject.put("sina_weibo", "");
        jsonObject.put("tag", "");
        jsonObject.put("tx_weibo", "");

        Response  response = TestConfig.postOrPutExecu("put", "/instructors/" + instructor_id + "?access_token=" + access_token, jsonObject);
        response.then().
                log().all().
                assertThat().statusCode(400).
                body("status", equalTo(false)).
                body("message", equalTo("照片不能为空"));

    }
}
