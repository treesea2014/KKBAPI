package org.kkb.server.api;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.with;

/**
 * ws.wang
 */
public class TestConfig {

    public static final String path="http://w-api-f1.kaikeba.cn";
    //public static final String path="http://release-api.kaikeba.cn";
    //public static final String path="http://w-api-f2.kaikeba.cn";

    public static RequestSpecification requestSpecification(){
        if(path.contains("cn")){
            return given()
                    .baseUri(path).and()
                    .header("Content-Type","application/json;charset=utf-8")
                    .port(80);

        }else {

            return given()
                    .baseUri(path).and()
                    .header("Content-Type","application/json")
                    .port(443);
        }
    }

    public static String getToken(String path){
        ///kauth/authorize?uid=812277&cid=www&tenant_id=0
        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        Response response=requestSpecification.when()
                .get(path);

        String token=with(response.body().asString()).get("access_token");
        return token;
    }

    public static String getTokenbyUserID(){
        ///kauth/authorize?uid=812277&cid=www&tenant_id=0

        //String user_name="fanqi2015";
        //String email="52282661";
        //String password="52282661";

        Integer user_id=632467; //普通用户

        RequestSpecification requestSpecification= TestConfig.requestSpecification();

        String path="/kauth/authorize?uid=" + user_id.toString() + "&cid=www&tenant_id=1";

        Response response=TestConfig.getOrDeleteExecu("get", path);

        String token=with(response.body().asString()).get("access_token");
        return token;
    }

    public static Response getOrDeleteExecu(String type,String url){
        if(type.equalsIgnoreCase("get")){
            RequestSpecification requestSpecification= TestConfig.requestSpecification().contentType(ContentType.JSON);

            Response response=requestSpecification.when()
                    .get(url);
            return response;
        }else{
            RequestSpecification requestSpecification= TestConfig.requestSpecification().contentType(ContentType.JSON);

            Response response=requestSpecification.when()
                    .delete(url);
            return response;
        }
    }

    public static Response postOrPutExecu(String type,String url,JSONObject jsonObject){
        if(type.equalsIgnoreCase("post")){

            RequestSpecification requestSpecification= TestConfig.requestSpecification().body(jsonObject);
            Response response=requestSpecification.when()
                    .post(url);
            return response;
        }else{
            RequestSpecification requestSpecification= TestConfig.requestSpecification().body(jsonObject);

            Response response=requestSpecification.when()
                    .put(url);
            return response;
        }
    }


}
