package org.gxb.server.api.restassured.classes.quiz;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.gxb.server.api.restassured.classes.item.CreateItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class ModifyQuiz {
    private Logger logger = LoggerFactory.getLogger(CreateItem.class);
    public static ResourceBundle bundle = ResourceBundle.getBundle("api");
    // 请求地址
    public static final String path = bundle.getString("env");
    public static final String basePath = bundle.getString("apiBasePath");
    String Url = path + basePath;
    Integer unitId;
    Integer itemId;
    Integer quizId;

    @BeforeClass(description = "获取quizId")
    public void init() {
        JSONObject jo = new JSONObject();
        jo.put("title", "测试单元xxsea");
        jo.put("unlockAt", "1450947971236");
        jo.put("endAt", "1450947971231");
        Response response = TestConfig.postOrPutExecu("post",
                "classes/1/unit?loginUserId=123&tenantId=1", jo);
        //创建unit后unitId
        unitId = response.jsonPath().get("unitId");

        JSONObject jo1 = new JSONObject();
        jo1.put("title", "测试TREeseaitemt");
        // jo.put("unlockAt", 1450947971236);
        response = TestConfig.postOrPutExecu("post",
                "classes/1/unit/" + unitId + "/item?loginUserId=123&tenantId=1", jo1);
        //创建itemId后itemId
        itemId = response.jsonPath().get("itemId");

        JSONArray questionList = new JSONArray();
        HashMap<String, Object> question = new HashMap<String, Object>();
        question.put("questionId", 1);
        questionList.add(question);

        HashMap<String, Object> classQuiz = new HashMap<String, Object>();
        classQuiz.put("title", "测试测验标题");
        classQuiz.put("questionList", questionList);

        JSONObject jo2 = new JSONObject();
        jo2.put("title", "测试测验标题");
        jo2.put("position", 888);
        jo2.put("classQuiz", JSONObject.fromObject(classQuiz));

        response = TestConfig.postOrPutExecu("post",
                "/classes/1/unit/" + unitId + "/item/" + itemId + "/chapter/quiz?loginUserId=123&tenantId=1", jo2);
        //创建quizId后quizId
        quizId = response.jsonPath().get("classQuiz.quizId");
    }

    @Test(description = "正常", priority = 1)
    public void test() {
        JSONArray questionList = new JSONArray();
        HashMap<String, Object> question = new HashMap<String, Object>();
        question.put("questionId", 1);
        question.put("questionId", 2);
        question.put("questionId", 3);
        questionList.add(question);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试测验修改标题");
        jo.put("description", "测试测验内容修改");
        jo.put("questionList",questionList);
        logger.info("jo:{}", jo);
        Response response = TestConfig.postOrPutExecu("put",
                "class_quiz/"+quizId+"?loginUserId=123", jo);

        response.then().log().all().assertThat()
                .statusCode(200)
                .body(equalTo("true"));
    }

    @Test(description = "title不能为空", priority = 2)
    public void testWithInvalidTitle01() {
        JSONArray questionList = new JSONArray();
        HashMap<String, Object> question = new HashMap<String, Object>();
        question.put("questionId", 1);
        question.put("questionId", 2);
        question.put("questionId", 3);
        questionList.add(question);

        JSONObject jo = new JSONObject();
        jo.put("title", "");
        jo.put("description", "测试测验内容修改");
        jo.put("questionList",questionList);
        logger.info("jo:{}", jo);
        Response response = TestConfig.postOrPutExecu("put",
                "class_quiz/"+quizId+"?loginUserId=123", jo);
        logger.info("response:{}", response.toString());

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title不能为空"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "title长度应该在1-32", priority = 3)
    public void testWithInvalidTitle02() {
        JSONArray questionList = new JSONArray();
        HashMap<String, Object> question = new HashMap<String, Object>();
        question.put("questionId", 1);
        question.put("questionId", 2);
        question.put("questionId", 3);
        questionList.add(question);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题测试测验修改标题");
        jo.put("description", "测试测验内容修改");
        jo.put("questionList",questionList);
        logger.info("jo:{}", jo);
        Response response = TestConfig.postOrPutExecu("put",
                "class_quiz/"+quizId+"?loginUserId=123", jo);
        logger.info("response:{}", response.toString());

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("title长度应该在1-32,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "description不能为空", priority = 4)
    public void testWithInvalidDescription() {
        JSONArray questionList = new JSONArray();
        HashMap<String, Object> question = new HashMap<String, Object>();
        question.put("questionId", 1);
        question.put("questionId", 2);
        question.put("questionId", 3);
        questionList.add(question);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试测验修改标题");
        jo.put("description", "测试测验内容修改");
        jo.put("questionList",questionList);
        logger.info("jo:{}", jo);
        Response response = TestConfig.postOrPutExecu("put",
                "class_quiz/"+quizId+"?loginUserId=123", jo);
        logger.info("response:{}", response.toString());

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("description不能为空"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "questionList不能为空", priority = 5)
    public void testWithInvalidClassTopic() {
        JSONArray questionList = new JSONArray();
        HashMap<String, Object> question = new HashMap<String, Object>();
        question.put("questionId", 1);
        question.put("questionId", 2);
        question.put("questionId", 3);
        questionList.add(question);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试测验修改标题");
        jo.put("description", "测试测验内容修改");
        jo.put("questionList","");
        logger.info("jo:{}", jo);
        Response response = TestConfig.postOrPutExecu("put",
                "class_quiz/"+quizId+"?loginUserId=123", jo);
        logger.info("response:{}", response.toString());

        response.then().log().all().assertThat()
                .statusCode(400)
                .body("message", equalTo("questionList不能为空,"))
                .body("type", equalTo("MethodArgumentNotValidException"))
        ;
    }

    @Test(description = "body为1-20000", priority = 5)
    public void testWithNotExist() {
        JSONArray questionList = new JSONArray();
        HashMap<String, Object> question = new HashMap<String, Object>();
        question.put("questionId", 1);
        question.put("questionId", 2);
        question.put("questionId", 3);
        questionList.add(question);

        JSONObject jo = new JSONObject();
        jo.put("title", "测试测验修改标题");
        jo.put("description", "测试测验内容修改");
        //jo.put("questionList",questionList);
        logger.info("jo:{}", jo);

        String response = HttpRequest.sendHttpPut(Url + "class_quiz/-1?loginUserId=123", jo);
        response = response.substring(response.indexOf("&") + 1, response.length());
        JSONObject result = JSONObject.fromObject(response);
        logger.info("response:{},", response.toString());
        Assert.assertEquals(result.get("code"), 1000);
        Assert.assertEquals(result.get("message"), "测验不存在");
        Assert.assertEquals(result.get("type"), "ServiceException");
    }


}
