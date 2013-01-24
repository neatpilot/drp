package org.cn.pilot.drp.sysmgr.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.DbUtil;
import org.cn.pilot.drp.util.PageModel;

/**
 * singleton pattern
 * 
 * @author Pilot
 * 
 */
public class UserManager {

	private static UserManager instance = new UserManager();

	private UserManager() {

	}

	public static UserManager getInstance() {
		return instance;
	}

	public void addUser(User user) {
		String sql = "insert into t_user (user_id, user_name, password, contact_tel, email, create_date) values(?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getContactTel());
			pstmt.setString(5, user.getEmail());
			// put into current time
			// same --> pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
			// commit
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	/**
	 * delete users together
	 * 
	 * @param userIds
	 */
	public void deleteUser(String[] userIds) {
		StringBuilder sqlSB = new StringBuilder();
		for (int i = 0; i < userIds.length; i++) {
			sqlSB.append("?,");
		}
		String sql = "DELETE FROM t_user WHERE user_id IN (" + sqlSB.substring(0, sqlSB.length() - 1) + ")";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < userIds.length; i++) {
				pstmt.setString(i + 1, userIds[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	/**
	 * modify user
	 * 
	 * @param user
	 */
	public void modifyUser(User user) {
		String sql = "UPDATE t_user SET user_name=?, password=?, contact_tel=?, email=? WHERE user_id=?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getContactTel());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getUserId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
	}

	/**
	 * return a User by String userId
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserById(String userId) {
		String sql = "select user_id, user_name, password, contact_tel, email, create_date from t_user where user_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setContactTel(rs.getString("contact_tel"));
				user.setEmail(rs.getString("email"));
				user.setCreateDate(rs.getTimestamp("create_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}

		return user;
	}

	/**
	 * query user list per page
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageModel<User> findUserList(int pageNo, int pageSize) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT user_id,user_name,password,contact_tel,email,create_date FROM");
		sqlBuffer.append("    (SELECT ROWNUM rn,user_id,user_name,password,contact_tel,email,create_date FROM");
		sqlBuffer
				.append("        (SELECT user_id,user_name,password,contact_tel,email,create_date FROM t_user WHERE user_id<>'root' order by user_id) ");
		sqlBuffer.append("    WHERE ROWNUM<=?)");
		sqlBuffer.append("WHERE rn>?");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PageModel<User> pageModel = null;

		try {
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			pstmt.setInt(1, pageNo * pageSize);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();

			List<User> userList = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setContactTel(rs.getString("contact_tel"));
				user.setEmail(rs.getString("email"));
				user.setCreateDate(rs.getTimestamp("create_date"));
				userList.add(user);
			}

			pageModel = new PageModel<User>();
			pageModel.setList(userList);
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setTotalRecords(getTotalRecords(conn));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.close(conn);
		}
		return pageModel;
	}

	/**
	 * get total records in t_user, only called by findUserList
	 * 
	 * @param conn
	 * @return
	 */
	private int getTotalRecords(Connection conn) {
		String sql = "SELECT count(*) FROM t_user WHERE user_id<>'root'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(pstmt);
			DbUtil.close(rs);
		}
		return count;
	}

	/**
	 * user login<br>
	 * if user id is not correct, throw UserNotFoundException<br>
	 * if user password is not correct, throw PasswordNotCorrectException
	 * @param userId
	 * @param password
	 * @return
	 */
	public User login(String userId, String password) {
		User user = findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("用户代码不正确");
		}

		if (!password.equals(user.getPassword())) {
			throw new PasswordNotCorrectException("用户密码不正确");
		}

		return user;
	}

	public static void main(String[] args) {
		// User user = new User();
		// user.setContactTel("312532");
		// user.setEmail("np@gmail.com");
		// user.setPassword("1111");
		// user.setUserId("A000");
		// user.setUserName("pilot");
		//
		// UserManager.getInstance().addUser(user);
		String[] userIds = { "1", "2", "3" };
		StringBuilder sqlSB = new StringBuilder();
		for (String id : userIds) {
			sqlSB.append(id + ",");
		}
		String sql = "DELETE FROM t_user WHERE user_id in (" + sqlSB.substring(0, sqlSB.length() - 1) + ")";
		System.out.println(sql);
	}
}
