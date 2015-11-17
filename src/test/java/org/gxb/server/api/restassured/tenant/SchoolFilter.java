package org.gxb.server.api.restassured.tenant;

import com.jayway.restassured.response.Response;

import net.sf.saxon.functions.Matches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 * @date 2015.09.06
 * @decription 租户学校查询
 * 123.57.210.46:8080/gxb-api/school?filter=tenantId:1
 * 
 */
public class SchoolFilter {
	
	private final static Logger logger = LoggerFactory.getLogger(SchoolFilter.class);


	@Test(description="正常" ,priority=1)
    public void test(){
		String  tenantId = "1";
		Response response= TestConfig.getOrDeleteExecu("get","/school?filter=tenantId:"+tenantId );
       response.then()
       			.log().all()
               .assertThat().statusCode(200)
               .body("filter.tenantId", equalTo(tenantId))
              // .body("dataList.tenantId", Matchers.arrayContaining(1))
              ;
    }	
	

	 

}
