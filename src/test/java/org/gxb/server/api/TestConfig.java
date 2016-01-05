package org.gxb.server.api;

import static com.jayway.restassured.RestAssured.given;

import java.util.ResourceBundle;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import net.sf.json.JSONObject;

/**
 * ws.wang
 */
public class TestConfig {
	public static ResourceBundle bundle = ResourceBundle.getBundle("api");
	// 请求地址
	public static final String path = bundle.getString("env");
	public static final String basePath = bundle.getString("apiBasePath");

	public static RequestSpecification requestSpecification() {
		if (path.contains("cn")) {
			return given().baseUri(path).and().header("Content-Type", "application/json;charset=utf-8").port(8080)
					.basePath(basePath);

		} else {
			return given().baseUri(path).and().header("Content-Type", "application/json;charset=utf-8").port(8080).basePath(basePath);
		}
	}

	public static Response getOrDeleteExecu(String type, String url) {
		if (type.equalsIgnoreCase("get")) {
			RequestSpecification requestSpecification = TestConfig.requestSpecification().contentType(ContentType.JSON);

			Response response = requestSpecification.when().get(url);
			return response;
		} else {
			RequestSpecification requestSpecification = TestConfig.requestSpecification().contentType(ContentType.JSON);

			Response response = requestSpecification.when().delete(url);
			return response;
		}
	}

	public static Response postOrPutExecu(String type, String url, Object file) {
		RequestSpecification requestSpecification = TestConfig.requestSpecification().body(file);
		Response response;
		if (type.equalsIgnoreCase("post")) {
			 response = requestSpecification.when().post(url);
		} else {
			 response = requestSpecification.when().put(url);
		}
		response.then().header("Content-type", "application/json;charset=UTF-8");
		return response;
	}
	
	public static Response postOrPutFileExecu(String type, String url) {
		RequestSpecification requestSpecification = TestConfig.requestSpecification();
		
		if (type.equalsIgnoreCase("post")) {
			Response response = requestSpecification.when().post(url);
			return response;
		} else {
			Response response = requestSpecification.when().put(url);
			return response;
		}
	}

}
