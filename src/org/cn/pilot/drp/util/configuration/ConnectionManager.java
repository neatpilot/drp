package org.cn.pilot.drp.util.configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.cn.pilot.drp.util.exception.ApplicationException;

/**
 * 采用ThreadLocal管理数据库连接Connection
 * 
 * @author Pilot
 * 
 */
public class ConnectionManager {
	private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>(); // 线程共享变量

	/**
	 * 返回数据库连接，并添加于ThreadLocal
	 * 
	 * @return
	 */
	public static Connection getConnection() throws ApplicationException {

		Connection conn = connLocal.get();
		if (conn == null) {
			try {
				// JNDI
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/drp");
				conn = dataSource.getConnection();
			} catch (NamingException e) {
				e.printStackTrace();
				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
			}
			connLocal.set(conn);
		}
		return conn;
	}

	/**
	 * 关闭数据库连接，并从ThreadLocal中移出
	 */
	public static void closeConnection() throws ApplicationException {
		Connection conn = connLocal.get();
		if (conn != null) {
			try {
				conn.close();
				connLocal.remove(); // remove再后，因为connection关闭可能会有异常
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
			}
		}
	}

	/**
	 * 关闭一次connection的连接
	 * 
	 * @param conn
	 * @throws ApplicationException
	 */
	//TODO 待废弃的方法（不应该再使用）
	public static void close(Connection conn) throws ApplicationException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
			}
		}
	}

	/**
	 * 关闭preparedStatement
	 * 
	 * @param pstmt
	 * @throws ApplicationException
	 */
	public static void close(PreparedStatement pstmt) throws ApplicationException {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
			}
		}
	}

	/**
	 * 关闭ResultSet
	 * 
	 * @param rs
	 * @throws ApplicationException
	 */
	public static void close(ResultSet rs) throws ApplicationException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
			}
		}
	}

	/**
	 * 手动开启事务
	 * 
	 * @param conn
	 *            该次数据库连接
	 */
	public static void beginTransaction(Connection conn) throws ApplicationException {
		try {
			if (conn != null) {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
		}
	}

	/**
	 * 手动提交事务
	 * 
	 * @param conn
	 *            该次数据库连接
	 */
	public static void commitTransaction(Connection conn) throws ApplicationException {
		try {
			if (conn != null) {
				if (!conn.getAutoCommit()) {
					// conn.setAutoCommit(true); ?? connection关闭会重置么？
					conn.commit();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
		}
	}

	/**
	 * 手动回滚事务
	 * 
	 * @param conn
	 * @throws ApplicationException
	 */
	public static void rollbackTransaction(Connection conn) throws ApplicationException {
		try {
			if (conn != null) {
				if (!conn.getAutoCommit()) {
					conn.rollback();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
		}
	}

	/**
	 * 手动重置提交模式
	 * 
	 * @param conn
	 */
	public static void resetConnection(Connection conn) throws ApplicationException {
		if (null != conn) {
			try {
				if (!conn.getAutoCommit()) {
					conn.setAutoCommit(true);
				} else {
					conn.setAutoCommit(false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
			}
		}
	}
}
