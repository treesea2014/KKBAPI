package org.gxb.server.api.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class OperationTable {
	public static ResourceBundle dbBundle = ResourceBundle.getBundle("db");
	public static final String dbUrl = dbBundle.getString("dbUrl");
	
	public int updateCourseStatus(int courseid,int status) throws Exception {
		String sql = "select count(*) as totalcount from course where course_id = "+courseid+" and status = "+status+"";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count  = 0;

		try {
			dbc = new DBConnection(dbUrl);
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
