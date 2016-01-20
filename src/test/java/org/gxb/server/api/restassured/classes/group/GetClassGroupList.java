package org.gxb.server.api.restassured.classes.group;

import com.jayway.restassured.response.Response;
import org.gxb.server.api.TestConfig;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

/**
 * Created by treesea on 16/1/20.
 */
public class GetClassGroupList {
    @Test(description = "正常" ,priority = 1)
    public void test(){
        Response response = TestConfig.getOrDeleteExecu("get",
                "/class_group?filter=classId:1,query:班组&paging=curPage:1,pageSize:2");
        response.then().log().all().assertThat().statusCode(200)
                .body("paging.curPage", Matchers.equalTo(1))
                .body("paging.pageSize", Matchers.equalTo(2))
                .body("filter.query", Matchers.equalTo("班组"))
                .body("filter.classId", Matchers.equalTo("1"))
                .body("dataList.groupName", Matchers.hasItem(Matchers.containsString("班组")))
                .body("dataList.teacherUserList.name", Matchers.hasItem(Matchers.hasItem("张金霞")))
        ;
    }
    @Test(description = "没有filter" ,priority = 2)
    public void testWithoutFilter(){
        Response response = TestConfig.getOrDeleteExecu("get",
                "/class_group?paging=curPage:2,pageSize:50");
        response.then().log().all().assertThat().statusCode(200)
                .body("paging.curPage", Matchers.equalTo(2))
                .body("paging.pageSize", Matchers.equalTo(50))
                //.body("filter.query", Matchers.equalTo("班组"))
                //.body("filter.classId", Matchers.equalTo("1"))
                .body("dataList.groupName", Matchers.hasItem(Matchers.containsString("默认分组")))
                //.body("dataList.teacherUserList.name", Matchers.hasItem(Matchers.hasItem("默认分组")))
        ;
    }



}
