import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.table.DefaultTableModel;

class DBData {
	private static String readUsersSQL = "SELECT * FROM classbook.users order by user_name asc, full_name asc;";
	private static String readUsersByIdSQL = "SELECT * FROM classbook.users where id=";
	private static String checkUserExists = "SELECT * FROM classbook.users where user_name='";
	private static String checkGradeExists = "SELECT true FROM classbook.grades where course_id=? and student_id=? and date=? ;";
	private static String insertGradeSQL = "INSERT INTO GRADES (course_id, student_id, date, grade, lector_id) VALUES (?,?,?,?,?)";
	private static String getGradeInfoById = "SELECT * FROM classbook.grades where id=?";
	private static String deleteGradeSQL = "DELETE FROM GRADES WHERE ID=?";
	private static String[] insertUsersSQLStr = {
			"INSERT INTO classbook.users (user_name, role, full_name, job_position, email, active) values('", "','",
			"','", "','", "','", "','", "');" };
	private static String createUsersSQL[] = { "CREATE USER '", "'@'%' IDENTIFIED WITH mysql_native_password BY '",
			"';" };
	private static String updateGradesSQL = "UPDATE classbook.grades  set course_id=?, student_id=?, date=?, grade=?, lector_id=? where classbook.grades.id=?;";
	private static String renameUserSQL[] = { "RENAME USER ", " TO " };
	private static String checkRolesAdminRightsByUserSQL = "SELECT * FROM classbook.user_roles where role='";
	private static String grantAdminSQL[] = { "GRANT ALL PRIVILEGES on *.* TO '", "'@'%';" };
	private static String grantGrantOptionSQL[] = { "GRANT GRANT OPTION on *.* TO '", "'@'%';" };
	private static String grantUserSQL[] = { "GRANT SELECT, INSERT, UPDATE, DELETE ON classbook.* TO '", "'@'%';" };
	private static String dropUserSQL = "DROP USER '";
	private static String deleteUserSQLStr = "DELETE from classbook.users where id=";
	private static String readUserStatusByNameSQL = "SELECT active FROM classbook.users where user_name='";
	private static String readRoleStatusByNameSQL = "SELECT active FROM classbook.user_roles where role='";
	private static String readUserRightsByNameSQL = "SELECT \r\n" + "    classbook.user_roles.data,\r\n"
			+ "    classbook.user_roles.reports,\r\n" + "    classbook.user_roles.administration\r\n" + "FROM\r\n"
			+ "    classbook.users\r\n" + "        JOIN\r\n"
			+ "    classbook.user_roles ON classbook.users.role = classbook.user_roles.role\r\n" + "WHERE\r\n"
			+ "    classbook.users.user_name = '";
	private static String readRoles = "SELECT * FROM classbook.user_roles;";
	private static String readActiveRoles = "SELECT * FROM classbook.user_roles where active='Да';";
	private static String readRolesByName = "SELECT * FROM classbook.user_roles where role='";
	private static String readStudentsSQL = "SELECT * FROM classbook.students order by full_name asc;";
	private static String readLectorsSQL = "SELECT * FROM classbook.lectors order by classbook.lectors.full_name asc;";
	private static String readLectorByIdSQL = "SELECT * FROM classbook.lectors where id = ?";
	private static String readStudentByIdSQL = "SELECT * FROM classbook.students where id = ?";
	private static String readCoursesSQL = "SELECT * FROM classbook.courses left outer join classbook.lectors on  classbook.courses.lector_id=classbook.lectors.id order by course_name asc;";
	private static String readGradesSQL = "SELECT * FROM classbook.grades \r\n" + 
			" left outer join classbook.students on classbook.grades.student_id=classbook.students.id\r\n" + 
			" left outer join  classbook.courses on classbook.grades.course_id=classbook.courses.id\r\n" + 
			" left outer join  classbook.lectors on classbook.grades.lector_id=classbook.lectors.id order by classbook.students.full_name asc, classbook.courses.course_name asc;";
	private static String readStudentsByCourseIdSQL = "SELECT * FROM classbook.students_courses\r\n" + 
			"JOIN classbook.students ON classbook.students.id=classbook.students_courses.student_id\r\n" + 
			"WHERE course_id=?;"; 
	private static String readStudentCourses = "SELECT * FROM classbook.students_courses\r\n"
			+ "join classbook.students on classbook.students_courses.student_id=classbook.students.id\r\n"
			+ "join classbook.courses on classbook.students_courses.course_id=classbook.courses.id\r\n"
			+ "join classbook.lectors on classbook.courses.lector_id=classbook.lectors.id order by classbook.courses.course_name asc, classbook.students.full_name asc;";
	private static String readCoursesStudentsIds = "SELECT * FROM classbook.students_courses where id=";
	private static String readStudentsIds = "SELECT * FROM classbook.students_courses where course_id=";
	private static String checkStudentCourses[] = { "SELECT * FROM classbook.students_courses where student_id=",
			" and course_id=", ";" };
	private static String getCourseByIdSQLStr = "select * from classbook.courses where id = ?";
	private static String userSQLStr = "SELECT * FROM classbook.users where user_name = ?";
	private static String deleteCourseSQLStr = "DELETE from classbook.courses where id = ";
	private static String deleteRolesSQLStr = "DELETE from classbook.user_roles where role = '";
	private static String[] updateRoleSQLStr = { "UPDATE classbook.user_roles set role='", "', data='", "', reports='",
			"', administration='", "', active='", "' where role='", "';" };
	private static String deleteLectorSQLStr = "DELETE from classbook.lectors where id = ";
	private static String deleteStudentSQLStr = "DELETE from classbook.students where id = ";
	private static String deleteCoursesStudentsSQLStr = "DELETE from classbook.students_courses where id = ";
	private static String[] insertCoursesStudentsSQLStr = {
			"insert into classbook.students_courses (student_id,course_id) values(", ",", ");" };
	private static String[] insertLectorSQLStr = {
			"insert into classbook.lectors (full_name, EGN, phone, email) values('", "','", "','", "','", "');" };
	private static String[] insertStudentSQLStr = {
			"insert into classbook.students (full_name, EGN, phone, address, email) values('", "','", "','", "','",
			"','", "');" };
	private static String[] insertRolesSQLStr = {
			"insert into classbook.user_roles (role, data, reports, administration, active) values('", "','", "','",
			"','", "','", "');" };
	private static String[] checkLectorSQLStr = { "SELECT full_name FROM classbook.lectors where EGN ='", "';" };
	private static String[] checkStudentSQLStr = { "SELECT full_name FROM classbook.students where EGN ='", "';" };
	private static String[] insertCourseSQLStr = {
			"insert into classbook.courses (course_name,start_date,end_date,lector_id) values('", "','", "','", "',",
			");" };
	private static String[] checkOtherLectorsSQLStr = { "SELECT * FROM classbook.lectors where EGN='", "' and id<>",
			";" };
	private static String[] checkOtherUsersSQLStr = { "SELECT * FROM classbook.users where user_name='", "' and id<>",
			";" };
	private static String[] checkOtherStudentsSQLStr = { "SELECT * FROM classbook.students where EGN='", "' and id<>",
			";" };
	private static String[] checkOtherRolesSQLStr = { "SELECT * FROM classbook.user_roles where role<>'",
			"' and role='", "';" };
	private static String checkRolesSQLStr = "SELECT * FROM classbook.user_roles where role='";
	private static String[] checkCourseSQLStr = { "SELECT course_name FROM classbook.courses where course_name ='",
			"' and start_date = '", "' and end_date = '", "' and lector_id = ", ";" };
	private static String[] updateLectorSQLStr = { "update classbook.lectors set full_name='", "', EGN='", "', phone='",
			"', email='", "' where id=", ";" };
	private static String[] updateUserSQLStr = { "update classbook.users set user_name='", "', role='",
			"', full_name='", "', job_position='", "', email='", "', active='", "' where id=", ";" };
	private static String[] updatePwdSQLStr = { "ALTER USER '", "' IDENTIFIED BY '", "';" };
	private static String[] updateStudentSQLStr = { "update classbook.students set full_name='", "', EGN='",
			"', phone='", "', address='", "', email='", "' where id=", ";" };
	private static String[] updateCourseSQLStr = { "update classbook.courses set course_name ='", "', start_date ='",
			"', end_date= '", "', lector_id=", " where id=", ";" };
	private static String[] checkOtherCoursesSQLStr = { "SELECT * FROM classbook.courses where course_name='",
			"' and start_date='", "' and end_date='", "' and lector_id='", "' and id<>", ";" };
	protected static String logoutSQLStr = "insert into log_in_out (\r\n" + "classbook.log_in_out.user, \r\n"
			+ "classbook.log_in_out.user_role, \r\n" + "classbook.log_in_out.action, \r\n"
			+ "classbook.log_in_out.date_time) \r\n" + "values(\r\n"
			+ "(SELECT SUBSTRING_INDEX(current_user(),'@',1)), \r\n"
			+ "(select classbook.users.role from classbook.users where classbook.users.user_name=(SELECT SUBSTRING_INDEX(current_user(),'@',1))), \r\n"
			+ "\"O\", CURRENT_TIMESTAMP);";
	protected static String loginSQLStr = "insert into log_in_out (\r\n" + "classbook.log_in_out.user, \r\n"
			+ "classbook.log_in_out.user_role, \r\n" + "classbook.log_in_out.action, \r\n"
			+ "classbook.log_in_out.date_time) \r\n" + "values(\r\n"
			+ "(SELECT SUBSTRING_INDEX(current_user(),'@',1)), \r\n"
			+ "(select classbook.users.role from classbook.users where classbook.users.user_name=(SELECT SUBSTRING_INDEX(current_user(),'@',1))), \r\n"
			+ "\"L\", CURRENT_TIMESTAMP);";
	protected static String findAvgCourse ="SELECT avg(grade) FROM classbook.grades where course_id=?";
	protected static String findAvgStudent ="SELECT avg(grade) FROM classbook.grades where student_id=?";
	protected static String findAvgCourseStudent = "SELECT avg(grade) FROM classbook.grades where student_id=? and course_id=?;";
	protected static String readCoursesStudentsById = "SELECT * FROM classbook.students_courses where id=?;";
	private static String convDateBG(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.format(date);
	}

	@SuppressWarnings("unused")
	protected static void logLogin() {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(loginSQLStr);
			stmt.close();
		} catch (SQLException e) {
			// clearCredentials();
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					"Проблем при опит за запис на лог за вход!");
			// e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	protected static void logLogout() {
		if (Global.logged == true) {
			try {
				Statement stmt = Global.conn.createStatement();
				int rs = stmt.executeUpdate(logoutSQLStr);
				stmt.close();
				// clearCredentials();
				Global.logged = false;
			} catch (SQLException e) {
				Global.logWriter(e,
						DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
						"Проблем при опит за запис на лог за изход!");
				Global.logged = true;
				// e.printStackTrace();
			}
		} else {
			Global.logWriter("Няма логнат потребител за запис на събитие за изход в базата");
		}
	}
	
//	PrepareStatement
	protected static float getAvgCourse(int course_id) {
		float result=0;
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(findAvgCourse);
			stmt.setInt(1, course_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getFloat(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}
	
//	PrepareStatement
	protected static float getAvgStudent(int student_id) {
		float result=0;
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(findAvgStudent);
			stmt.setInt(1, student_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getFloat(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}
	
//	PrepareStatement
	protected static float getAvgCourseStudent(int student_id, int course_id) {
		float result=0;
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(findAvgCourseStudent);
			stmt.setInt(1, student_id);
			stmt.setInt(2, course_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result = rs.getFloat(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}


	protected static void getUsers(DefaultTableModel model) {
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUsersSQL);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7) });
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	protected static String[] getUserById(int id) {
		String[] result = new String[6];
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUsersByIdSQL + id + ";");
			while (rs.next()) {
				result[0] = rs.getString(2);
				result[1] = rs.getString(4);
				result[2] = rs.getString(5);
				result[3] = rs.getString(6);
				result[4] = rs.getString(3);
				result[5] = rs.getString(7);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// Проверка за съществуващ вече в базата потребител
	protected static boolean checkUserExists(String user_name) {
		String data = "";
		boolean result = false;
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkUserExists + user_name + "';");
			while (rs.next()) {
				data = rs.getString(2);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		if (!data.equals("")) {
			result = true;
		}
		return result;
	}

	@SuppressWarnings("unused")
	protected static void deleteUser(int id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(dropUserSQL + getUserById(id)[0] + "';");
			stmt.close();
			stmt = Global.conn.createStatement();
			rs = stmt.executeUpdate(deleteUserSQLStr + id + ";");
			stmt.close();
			Global.logWriter(Global.actMesssage2 + " с ID: " + id);
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void insertUsers(String user_name, String role, String full_name, String job_position,
			String email, String active, String password) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(insertUsersSQLStr[0] + user_name + insertUsersSQLStr[1] + role
					+ insertUsersSQLStr[2] + full_name + insertUsersSQLStr[3] + job_position + insertUsersSQLStr[4]
					+ email + insertUsersSQLStr[5] + active + insertUsersSQLStr[6]);
			stmt.close();
			stmt = Global.conn.createStatement();
			rs = stmt.executeUpdate(createUsersSQL[0] + user_name + createUsersSQL[1] + password + createUsersSQL[2]);
			stmt.close();
			stmt = Global.conn.createStatement();
			if (getRolesRightsByUser(role)) {
				rs = stmt.executeUpdate(grantAdminSQL[0] + user_name + grantAdminSQL[1]);
				stmt.close();
				stmt = Global.conn.createStatement();
				rs = stmt.executeUpdate(grantGrantOptionSQL[0] + user_name + grantGrantOptionSQL[1]);
				stmt.close();
			} else {
				rs = stmt.executeUpdate(grantUserSQL[0] + user_name + grantUserSQL[1]);
				stmt.close();
			}
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	protected static boolean checkDriver() {
		boolean result = false;
		try {
			Class.forName(Global.driver);
			result = true;
		} catch (ClassNotFoundException ex) {
			Global.logWriter(ex,
					DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					"Проблем с откриването на инсталиран JDBC драйвер в системата!");
			result = false;
		}
		return result;
	}

	protected static boolean checkOldPassword(String user, String pwd) {
		boolean result = false;
		try {
			Global.conn = DriverManager
					.getConnection(Global.connString1 + user + Global.connString2 + pwd + Global.connString3);
			DBData.logLogin();
			result = true;
		} catch (SQLException ex) {
			result = false;
			Global.logWriter(ex,
					DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage21);
		}
		return result;
	}
	
	protected static boolean login(String user, String pwd) {
		boolean result = false;
		try {
			Global.conn = DriverManager
					.getConnection(Global.connString1 + user + Global.connString2 + pwd + Global.connString3);
			DBData.logLogin();
			result = true;
		} catch (SQLException ex) {
			result = false;
			Global.logWriter(ex,
					DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage6);
		}
		return result;
	}

	protected static boolean getUserStatus(String user_name) {
		String result = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUserStatusByNameSQL + user_name + "';");
			while (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		if (result.toLowerCase().equals("да")) {
			return true;
		}
		return false;
	}

	protected static boolean getRoleStatus(String user_role) {
		String result = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readRoleStatusByNameSQL + user_role + "';");
			while (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		if (result.toLowerCase().equals("да")) {
			return true;
		}
		return false;
	}

	protected static String[] readUserRights(String user_name) {
		String[] result = new String[3];
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readUserRightsByNameSQL + user_name + "';");
			while (rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);
				result[2] = rs.getString(3);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// prepareStatement
	protected static String[] getLoggedUser(String user_name) {
		String[] result = new String[6];
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(userSQLStr);
			stmt.setString(1, user_name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result[1] = rs.getString(2);
				result[2] = rs.getString(3);
				result[3] = rs.getString(4);
				result[4] = rs.getString(5);
				result[5] = rs.getString(6);
			}
			rs.close();
			stmt.close();
			result[0] = "-1";
		} catch (SQLException e) {
			result[0] = "1";
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// prepareStatement
	protected static String[] getLectorById(String id) {
		String[] result = new String[4];
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(readLectorByIdSQL);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result[0] = rs.getString(2);
				result[1] = rs.getString(3);
				result[2] = rs.getString(4);
				result[3] = rs.getString(5);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// prepareStatement
	protected static String[] getStudentById(String id) {
		String[] result = new String[5];
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(readStudentByIdSQL);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result[0] = rs.getString(2);
				result[1] = rs.getString(3);
				result[2] = rs.getString(4);
				result[3] = rs.getString(5);
				result[4] = rs.getString(6);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// Проверка за съществуващ вече в базата лектор
	protected static String checkLector(String EGN) {
		String results = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkLectorSQLStr[0] + EGN + checkLectorSQLStr[1]);
			while (rs.next()) {
				results = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}

	// Проверка за съществуващ вече в базата студент
	protected static String checkStudent(String EGN) {
		String results = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkStudentSQLStr[0] + EGN + checkStudentSQLStr[1]);
			while (rs.next()) {
				results = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}

	// Проверка за съществуващ вече в базата лектор измежду останалите
	protected static String checkOtherLectors(int id, String EGN) {
		String results = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					checkOtherLectorsSQLStr[0] + EGN + checkOtherLectorsSQLStr[1] + id + checkOtherLectorsSQLStr[2]);
			while (rs.next()) {
				results = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}

	// Проверка за съществуващ вече в базата потребител измежду останалите
	protected static String checkOtherUsers(int id, String user_name) {
		String results = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					checkOtherUsersSQLStr[0] + user_name + checkOtherUsersSQLStr[1] + id + checkOtherUsersSQLStr[2]);
			while (rs.next()) {
				results = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}

	// Проверка за съществуващ вече в базата студент измежду останалите
	protected static String checkOtherStudents(int id, String EGN) {
		String results = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					checkOtherStudentsSQLStr[0] + EGN + checkOtherStudentsSQLStr[1] + id + checkOtherStudentsSQLStr[2]);
			while (rs.next()) {
				results = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}

	protected static void getLectors(DefaultTableModel model) {
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readLectorsSQL);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), "*********" + rs.getString(3).substring(8),
						rs.getString(4), rs.getString(5) });
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	// Връща id и име на лектор за Combobox-а
	protected static LinkedHashMap<String, String> getLectors() {
		LinkedHashMap<String, String> lectors = new LinkedHashMap<String, String>();
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readLectorsSQL);
			while (rs.next()) {
				lectors.put(rs.getString(2) + " / " + rs.getString(4) + " / " + rs.getString(5), rs.getString(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return lectors;
	}

	@SuppressWarnings("unused")
	protected static void insertStudents(String full_name, String EGN, String phone, String address, String email) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(insertStudentSQLStr[0] + full_name + insertStudentSQLStr[1] + EGN
					+ insertStudentSQLStr[2] + phone + insertStudentSQLStr[3] + address + insertStudentSQLStr[4] + email
					+ insertStudentSQLStr[5]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void insertCoursesStudents(int student_id, int course_id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(insertCoursesStudentsSQLStr[0] + student_id + insertCoursesStudentsSQLStr[1]
					+ course_id + insertCoursesStudentsSQLStr[2]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void insertLectors(String full_name, String EGN, String phone, String email) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(insertLectorSQLStr[0] + full_name + insertLectorSQLStr[1] + EGN
					+ insertLectorSQLStr[2] + phone + insertLectorSQLStr[3] + email + insertLectorSQLStr[4]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void updateLector(int id, String full_name, String EGN, String phone, String email) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(
					updateLectorSQLStr[0] + full_name + updateLectorSQLStr[1] + EGN + updateLectorSQLStr[2] + phone
							+ updateLectorSQLStr[3] + email + updateLectorSQLStr[4] + id + updateLectorSQLStr[5]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void updateUser(String user_name, String role, String full_name, String job_position, String email,
			String active, String password, int id, String beforeUser) {
		try {
			if (!password.equals("")) {
				Statement stmt = Global.conn.createStatement();
				int rs = stmt.executeUpdate(
						updatePwdSQLStr[0] + beforeUser + updatePwdSQLStr[1] + password + updatePwdSQLStr[2]);
				stmt.close();
			}
			if (!beforeUser.equals(user_name)) {
				Statement stmt = Global.conn.createStatement();
				int rs = stmt.executeUpdate(renameUserSQL[0] + beforeUser + renameUserSQL[1] + user_name + ";");
				stmt.close();
			}
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(updateUserSQLStr[0] + user_name + updateUserSQLStr[1] + role
					+ updateUserSQLStr[2] + full_name + updateUserSQLStr[3] + job_position + updateUserSQLStr[4] + email
					+ updateUserSQLStr[5] + active + updateUserSQLStr[6] + id + updateUserSQLStr[7]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}
	
	@SuppressWarnings("unused")
	protected static void changeUserPassword(String user_name, String password) {
		try {
				Statement stmt = Global.conn.createStatement();
				int rs = stmt.executeUpdate(
						updatePwdSQLStr[0] + user_name + updatePwdSQLStr[1] + password + updatePwdSQLStr[2]);
				stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}
	
	@SuppressWarnings("unused")
	protected static void updateStudent(int id, String full_name, String EGN, String phone, String address,
			String email) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(updateStudentSQLStr[0] + full_name + updateStudentSQLStr[1] + EGN
					+ updateStudentSQLStr[2] + phone + updateStudentSQLStr[3] + address + updateStudentSQLStr[4] + email
					+ updateStudentSQLStr[5] + id + updateStudentSQLStr[6]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void deleteLector(int id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(deleteLectorSQLStr + id);
			stmt.close();
			Global.logWriter(Global.actMesssage2 + " с ID: " + id);
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void deleteStudent(int id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(deleteStudentSQLStr + id);
			stmt.close();
			Global.logWriter(Global.actMesssage2 + " с ID: " + id);
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void deleteCoursesStudents(int id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(deleteCoursesStudentsSQLStr + id);
			stmt.close();
			Global.logWriter(Global.actMesssage8 + " с ID: " + id);
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	// Връща наименование, начална дата, крайна дата, и id на лектора. Изпозлва се
	// за да визуализира данни в прозореца за редакция
	// prepareStatement
	protected static String[] getCourseById(int id) {
		String[] results = new String[4];
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(getCourseByIdSQLStr);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results[0] = rs.getString(2);
				results[1] = rs.getString(3);
				results[2] = rs.getString(4);
				results[3] = rs.getString(5);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}
	
	// prepareStatement
	protected static String[] getGradeInfoById(int grade_id) {
		String[] results = new String[5];
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(getGradeInfoById);
			stmt.setInt(1, grade_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				results[0] = rs.getString(2);
				results[1] = rs.getString(3);
				results[2] = rs.getString(4);
				results[3] = rs.getString(5);
				results[4] = rs.getString(6);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}
	
	// Проверка за съществуващ вече в базата курс
	protected static String checkCourse(String course_name, String start_date, String end_date, String lector_id) {
		String results = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkCourseSQLStr[0] + course_name + checkCourseSQLStr[1] + start_date
					+ checkCourseSQLStr[2] + end_date + checkCourseSQLStr[3] + lector_id + checkCourseSQLStr[4]);
			while (rs.next()) {
				results = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}

	// Проверка за съществуващ вече в базата курс измежду останалите
	protected static String checkOtherCourses(int id, String course_name, String start_date, String end_date,
			String lector_id) {
		String results = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkOtherCoursesSQLStr[0] + course_name + checkOtherCoursesSQLStr[1]
					+ start_date + checkOtherCoursesSQLStr[2] + end_date + checkOtherCoursesSQLStr[3] + lector_id
					+ checkOtherCoursesSQLStr[4] + id + checkOtherCoursesSQLStr[5]);
			while (rs.next()) {
				results = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return results;
	}

	protected static void getCourses(DefaultTableModel model) {
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readCoursesSQL);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), convDateBG(rs.getDate(3)),
						convDateBG(rs.getDate(4)), rs.getString(7), rs.getString(9), rs.getString(10),
						rs.getString(5) });
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	protected static void getStudents(DefaultTableModel model) {
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readStudentsSQL);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), "*********" + rs.getString(3).substring(8),
						rs.getString(4), rs.getString(5), rs.getString(6) });
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	protected static void getGrades(DefaultTableModel model) {
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readGradesSQL);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(8), "*********" + rs.getString(9).substring(8),
						rs.getString(14), rs.getString(5), convDateBG(rs.getDate(4)), rs.getString(19),
						rs.getString(21) });
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	// prepareStatement
	protected static boolean checkGradeExists(int course_id, int student_id, String date) {
		boolean result = false;
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(checkGradeExists);
			stmt.setInt(1, course_id);
			stmt.setInt(2, student_id);
			stmt.setString(3, date);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals("1")) {
					result = true;
				}

			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// prepareStatement
	protected static void insertGrades(int course_id, int student_id, String date, String grade, int lector_id) {
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(insertGradeSQL);
			stmt.setInt(1, course_id);
			stmt.setInt(2, student_id);
			stmt.setString(3, date);
			stmt.setString(4, grade);
			stmt.setInt(5, lector_id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	// prepareStatement
	protected static void deleteGrades(int id) {
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(deleteGradeSQL);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}
	
	// prepareStatement	
	protected static void updateGrades(int course_id, int student_id, String date, String grade,
			int lector_id, int id) {
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(updateGradesSQL);
			stmt.setInt(1, course_id);
			stmt.setInt(2, student_id);
			stmt.setString(3, date);
			stmt.setString(4, grade);
			stmt.setInt(5, lector_id);
			stmt.setInt(6, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}
	
	// prepareStatement
	protected static Integer [] getCoursesStudentsById(int id) {
		Integer result []= new Integer[2];
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(readCoursesStudentsById);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result[0] = rs.getInt(2);
				result[1] = rs.getInt(3);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	protected static void getCoursesStudents(DefaultTableModel model) {
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readStudentCourses);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(11), convDateBG(rs.getDate(12)),
						convDateBG(rs.getDate(13)), rs.getString(5), "*********" + rs.getString(6).substring(8),
						rs.getString(16), "*********" + rs.getString(17).substring(8) });
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	protected static ArrayList<Integer> getStudentsIdsCS(int course_id) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readStudentsIds + course_id + ";");
			while (rs.next()) {
				result.add(rs.getInt(2));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}
	
	
	
	// prepareStatement
	protected static void getStudentsByCourseIdSQL(DefaultTableModel model, int course_id) {
		try {
			PreparedStatement stmt = Global.conn.prepareStatement(readStudentsByCourseIdSQL);
			stmt.setInt(1, course_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt(4), rs.getString(5), "*********" + rs.getString(6).substring(8),
						rs.getString(7), rs.getString(8), rs.getString(9)});
			}
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	// Връща Id-то на курса в зависимост от избрание ред в таблица Курсове студенти
	protected static Integer getCourseIdCS(int courses_students_id) {
		Integer result = -1;
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readCoursesStudentsIds + courses_students_id + ";");
			while (rs.next()) {
				result = rs.getInt(3);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	protected static boolean checkCoursesStudents(int student_id, int course_id) {
		boolean result = false;
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					checkStudentCourses[0] + student_id + checkStudentCourses[1] + course_id + checkStudentCourses[2]);
			if (rs.next())
				return true;
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	protected static String[] getRolesByName(String role) {
		String[] result = new String[5];
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readRolesByName + role + "';");
			while (rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);
				result[2] = rs.getString(3);
				result[3] = rs.getString(4);
				result[4] = rs.getString(5);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	protected static boolean getRolesRightsByUser(String role_name) {
		String data = "";
		boolean result = false;
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkRolesAdminRightsByUserSQL + role_name + "';");
			while (rs.next()) {
				data = rs.getString(4);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		if (data.trim().equals("Да")) {
			result = true;
		}
		return result;
	}

	protected static void getRoles(DefaultTableModel model) {
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readRoles);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5) });
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}

	}

	// За попълване на ComboBox в екрана за администриране на потребители
	protected static ArrayList<String> getRoles() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(readActiveRoles);
			while (rs.next()) {
				result.add(rs.getString(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// Проверка за съществуваща вече в базата роля измежду останалите
	protected static String checkOtherRoles(String role, String before_role) {
		String result = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkOtherRolesSQLStr[0] + before_role + checkOtherRolesSQLStr[1] + role
					+ checkOtherRolesSQLStr[2]);
			while (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	// Проверка за съществуваща вече в базата роля измежду останалите
	protected static String checkRoles(String role) {
		String result = "";
		try {
			Statement stmt = Global.conn.createStatement();
			ResultSet rs = stmt.executeQuery(checkRolesSQLStr + role + "';");
			while (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
		return result;
	}

	@SuppressWarnings("unused")
	protected static void insertRoles(String role, String data, String reports, String administration, String active) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(insertRolesSQLStr[0] + role + insertRolesSQLStr[1] + data + insertRolesSQLStr[2]
					+ reports + insertRolesSQLStr[3] + administration + insertRolesSQLStr[4] + active
					+ insertRolesSQLStr[5]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void updateRole(String role, String data, String reports, String administration, String active,
			String beforeRole) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(updateRoleSQLStr[0] + role + updateRoleSQLStr[1] + data + updateRoleSQLStr[2]
					+ reports + updateRoleSQLStr[3] + administration + updateRoleSQLStr[4] + active
					+ updateRoleSQLStr[5] + beforeRole + updateRoleSQLStr[6]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void deleteRole(String role) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(deleteRolesSQLStr + role + "';");
			stmt.close();
			Global.logWriter(Global.actMesssage7 + ": " + role);
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void insertCourse(String course_name, String start_date, String end_date, String lector_id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(insertCourseSQLStr[0] + course_name + insertCourseSQLStr[1] + start_date
					+ insertCourseSQLStr[2] + end_date + insertCourseSQLStr[3] + lector_id + insertCourseSQLStr[4]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void updateCourse(int id, String course_name, String start_date, String end_date,
			String lector_id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(updateCourseSQLStr[0] + course_name + updateCourseSQLStr[1] + start_date
					+ updateCourseSQLStr[2] + end_date + updateCourseSQLStr[3] + lector_id + updateCourseSQLStr[4] + id
					+ updateCourseSQLStr[5]);
			stmt.close();
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

	@SuppressWarnings("unused")
	protected static void deleteCourse(int id) {
		try {
			Statement stmt = Global.conn.createStatement();
			int rs = stmt.executeUpdate(deleteCourseSQLStr + id);
			stmt.close();
			Global.logWriter(Global.actMesssage2 + " с ID: " + id);
		} catch (SQLException e) {
			Global.logWriter(e, DBData.class + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",
					Global.errMesssage1);
		}
	}

}
