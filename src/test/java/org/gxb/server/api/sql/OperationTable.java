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
		String sql = "select count(1) as totalcount from course where course_id = " + courseid + " and status = "
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

	public int selectClass(String courseid, String classid) throws Exception {
		String sql;
		if (courseid == null && classid == null) {
			sql = "select count(1) as totalcount from class";
		} else {
			sql = "select count(1) as totalcount from class where course_id = '" + courseid + "' and class_id = '"
					+ classid + "'";
		}
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

	public Map<String,Integer> selectCourseChapter(int courseid, int flag) throws Exception {
		String sql = "select chapter.content_type as contenttype,count(1) as totalcount from  course_chapter chapter join course_item item on chapter.item_id =item.item_id"
				+ " and item.delete_flag= " + flag + " join course_unit unit on unit.unit_id = item.item_id"
				+ " and unit.delete_flag= " + flag + " where chapter.course_id= " + courseid
				+ "  and chapter.delete_flag =" + flag + " group by chapter.content_type";

		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		Map<String,Integer> hashMap = new HashMap<String,Integer>();

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			res = st.executeQuery(sql);
			while (res.next()) {
				hashMap.put(res.getString("contenttype"), res.getInt("totalcount"));
			}

			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashMap;
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

	public void updateClass(int classid, String status, String flag) throws Exception {
		String sql;
		if (flag == null) {
			sql = "update class set status = " + status + " where class_id = " + classid + "";
		} else {
			sql = "update class set delete_flag = '" + flag + "' where class_id = " + classid + "";
		}
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
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

	public void updateCourseQuiz(int quizid, int flag) throws Exception {
		String sql = "update course_quiz set delete_flag = " + flag + " where quiz_id = " + quizid + "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCourseAssignment(int assignmentid, int flag) throws Exception {
		String sql = "update course_assignment set delete_flag = " + flag + " where assignment_id = " + assignmentid
				+ "";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCourseChapter(int id, int flag, String contenttype) throws Exception {
		String sql = "update course_chapter set delete_flag = " + flag + " where content_id = " + id
				+ " and content_type='" + contenttype + "'";
		DBConnection dbc = null;
		Connection conn = null;
		Statement st = null;

		try {
			dbc = new DBConnection(dbUrl);
			conn = dbc.GetConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
