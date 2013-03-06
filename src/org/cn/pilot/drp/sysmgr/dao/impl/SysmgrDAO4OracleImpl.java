package org.cn.pilot.drp.sysmgr.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cn.pilot.drp.sysmgr.dao.SysmgrDAO;
import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.configuration.ConnectionManager;
import org.cn.pilot.drp.util.exception.DAOException;

public class SysmgrDAO4OracleImpl implements SysmgrDAO {

	@Override
	public void addUser(User user) throws DAOException {
		String sql = "insert into t_user (user_id, user_name, password, contact_tel, email, create_date) values(?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getContactTel());
			pstmt.setString(5, user.getEmail());
			pstmt.setTimestamp(6, new Timestamp(new Date().getTime()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	@Override
	public void deleteUser(String[] userIds) throws DAOException {
		StringBuilder sqlSB = new StringBuilder();
		for (int i = 0; i < userIds.length; i++) {
			sqlSB.append("?,");
		}
		String sql = "DELETE FROM t_user WHERE user_id IN (" + sqlSB.substring(0, sqlSB.length() - 1) + ")";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < userIds.length; i++) {
				pstmt.setString(i + 1, userIds[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	@Override
	public void modifyUser(User user) throws DAOException {
		String sql = "UPDATE t_user SET user_name=?, password=?, contact_tel=?, email=? WHERE user_id=?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getContactTel());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getUserId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

//	@Override
//	public void modifyUserPassword(String userId, String password) throws DAOException {
//		String sql = "update t_user set password=? where user_id=?";
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try {
//			conn = ConnectionManager.getConnection();
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, password);
//			pstmt.setString(2, userId);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DAOException(e);
//		} finally {
//			ConnectionManager.close(pstmt);
//		}
//	}

	@Override
	public User findUserById(String userId) throws DAOException {
		String sql = "select user_id, user_name, password, contact_tel, email, create_date from t_user where user_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = ConnectionManager.getConnection();
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
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
		}
		return user;
	}

	@Override
	public List<User> findUserList(int pageNo, int pageSize) throws DAOException {
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
		List<User> userList = null;

		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			pstmt.setInt(1, pageNo * pageSize);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();

			userList = new ArrayList<User>();
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

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
		}
		return userList;
	}

	@Override
	public int getTotalRecords() throws DAOException {
		String sql = "SELECT count(*) FROM t_user WHERE user_id<>'root'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int count = 0;
		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			ConnectionManager.close(pstmt);
			ConnectionManager.close(rs);
		}
		return count;
	}
}
