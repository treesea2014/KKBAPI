package org.gxb.server.api.restassured.user.register;

import com.jayway.restassured.response.Response;
import net.sf.json.JSONObject;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/22.
 */
public class binding {

    @Test(description = "手机号码不合法",priority = 1)
    public void testWithInvalidMobile(){
        JSONObject jo  = new JSONObject();
        jo.put("mobile","1850081802x");
        jo.put("encrypted_password","123456");
        jo.put("validateCode","123456");

        Response response = TestConfig.postOrPutExecu("post","/user/1272520/change_mobile" ,jo);
        response.then().log().all().assertThat()
                .statusCode(200)
                .body("status" , Matchers.equalTo(false))
                .body("errorInfo.mobile" , Matchers.equalTo("手机号码不合法"))
        ;

    }
}
