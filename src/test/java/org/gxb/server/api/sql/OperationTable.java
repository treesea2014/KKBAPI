package org.gxb.server.api.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OperationTable {
	private String courseDBUrl = "jdbc:mysql://192.168.30.33:3306/course";

	
	public int updateCourseStatus(int courseid,int status) throws Exception {
		String sql = "select count(*) as totalcount from course where course_id = "+courseid+" and status = "+status+"";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count  = 0;

		try {
			dbc = new DBConnection(courseDBUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while(res.next())
			{
				count = res.getInt("totalcount");
			}
			System.out.println("select code success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
