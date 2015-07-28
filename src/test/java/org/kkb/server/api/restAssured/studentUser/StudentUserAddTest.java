package org.kkb.server.api.restassured.studentUser;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;
import org.hamcrest.Matchers;
import org.kkb.server.api.TestConfig;
import org.kkb.server.api.util.PLSql;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 新增用户
 */
@Test
public class StudentUserAddTest {
    public static String token=TestConfig.getToken("kauth/authorize?uid=812277&cid=www&tenant_id=1");
    public static int userId;
    //参数缺少token
    public void testWithNoToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("no","1234");
        jsonObject.put("name","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/student_users");
        response.then().assertThat().statusCode(400);
    }

    //参数-缺少no
    public void testWithNoNo(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/student_users?access_token="+token);
        response.then().assertThat().statusCode(400);
    }

    //参数-缺少name
    public void testWithNoName(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("no","1234");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/student_users?access_token="+token);
        response.then().assertThat().statusCode(400);
    }

    //token不存在
    public void testWithErrorToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("no","1234");
        jsonObject.put("name","swang");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/student_users?access_token=4567890pfghjk");
        response.then().assertThat().statusCode(401);
    }

    //token 没有权限
    private void testWithInvalidToken(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("no","1234");
        jsonObject.put("name","swang");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/student_users");
        response.then().assertThat().statusCode(200).body("message",Matchers.equalTo("没有权限"));

        //验证添加是否可以登录，需要验证

    }

    //添加成功
    public void testSuc() throws SQLException {
        String no=getNo();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("no",no);
        jsonObject.put("name","swang");

        RequestSpecification requestSpecification= TestConfig.requestSpecification();
        requestSpecification .contentType(ContentType.JSON).body(jsonObject);
        Response response=requestSpecification.when()
                .post("/student_users?access_token="+token);
        response.then().assertThat().statusCode(200).body("message",Matchers.equalTo("success"));

        ResultSet rs= PLSql.select("select * from students where no='"+no+"' and name ='swang'","cms_production");
        while (rs.next())
        {

            userId=rs.getInt(1);
            System.out.print(rs.getInt(1));
        }

    }

    private static String getNo(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        return time;
    }

}
