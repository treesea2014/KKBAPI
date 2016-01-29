package org.gxb.server.api.lms.user;

import java.util.ResourceBundle;
import org.gxb.server.api.HttpRequest;
import org.gxb.server.api.TestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;


public class GetIdsUser {
	private static Logger logger = LoggerFactory.getLogger(GetIdsUser.class);
	public ResourceBundle bundle = ResourceBundle.getBundle("api");
	private static HttpRequest httpRequest = new HttpRequest();
	public String path = bundle.getString("env");
	public String basePath = "/" + bundle.getString("apiBasePath");

	@Test(priority = 1, description = "正确的参数")
	public void verifyCorrectParams() {
		int tenantId = 130;

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(1001);
		jsonArray.add(1002);

		Response response = TestConfig.postOrPutExecu("post", "/user/ids?tenantId=" + tenantId, jsonArray);

		if (response.getStatusCode() == 500) {
			logger.info("ids 获取user接口##" + response.prettyPrint());
		}

		response.then().assertThat().statusCode(200);
	}
}
