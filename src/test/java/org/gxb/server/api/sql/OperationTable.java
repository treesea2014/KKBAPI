package org.gxb.server.api.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OperationTable {
	public static ResourceBundle dbBundle = ResourceBundle.getBundle("db");
	public static final String dbUrl = dbBundle.getString("dbUrl");

	public int selectCourseStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course where course_id = " + courseid + " and status = "
				+ status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public Map<String, Object> selectCourseChapter(int courseid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select content_type, count(*) as totalcount from course_chapter where course_id = " + courseid
				+ " group by content_type;";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				map.put(res.getString("content_type"), res.getInt("totalcount"));
			}
			System.out.println("select course_chapter success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public int selectCourseChapter(int courseid, String contentType) throws Exception {
		String sql = "select count(*) as totalcount from course_chapter where course_id = " + courseid
				+ " and content_type = '" + contentType + "'";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_chapter success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public void updateCourseStatus(int courseid, int status, String flag) throws Exception {
		String sql;
		if (flag == null) {
			sql = "update course set status = " + status + " where course_id = " + courseid + "";
		} else {
			sql = "update course set delete_flag = '" + flag + "' where course_id = " + courseid + "";
		}
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("update course success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int selectCourseUnitStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_unit where course_id = " + courseid + " and status = "
				+ status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_unit success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCourseItemStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_item where course_id = " + courseid + " and status = "
				+ status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_item success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCourseChapterStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_chapter where course_id = " + courseid
				+ " and status = " + status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_chapter success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCourseQuizStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_quiz where course_id = " + courseid + " and status = "
				+ status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_quiz success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCourseTopicStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_topic where course_id = " + courseid + " and status = "
				+ status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_topic success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCourseAssignmentStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_assignment where course_id = " + courseid
				+ " and status = " + status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_assignment success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCourseCoursewareStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_courseware where course_id = " + courseid
				+ " and status = " + status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_courseware success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCoursePageStatus(int courseid, int status) throws Exception {
		String sql = "select count(*) as totalcount from course_page where course_id = " + courseid + " and status = "
				+ status + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			System.out.println("select course_page success!");
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int selectCourseQuestionRelate(String contextId, String contextType, String questionid1, String questionid2)
			throws Exception {
		String sql = "select count(*) as totalCount from course_question_relate where context_id ='" + contextId
				+ "' and context_type='" + contextType + "' and question_id in( '" + questionid1 + "' , '" + questionid2
				+ "');";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		int count = 0;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				count = res.getInt("totalcount");
			}
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
