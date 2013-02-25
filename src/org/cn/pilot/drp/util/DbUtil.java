package org.cn.pilot.drp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbUtil {

	/**
	 * 得到数据库连接<br>
	 * get the database connection<br>
	 * 
	 * @return
	 */
	public static Connection getConnection() {

		// Connection conn = null;
		//
		// try {
		// JdbcConfig jdbcConfig = XmlConfigReader.getInstance().getJdbcConfig();
		// Class.forName(jdbcConfig.getDriverName());
		// conn = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUser(), jdbcConfig.getPassword());
		// } catch (SQLException | ClassNotFoundException e) {
		// e.printStackTrace();
		// }
		//
		// return conn;

		Connection conn = null;

		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/drp");
			conn = ds.getConnection();

			// Context envContext = (Context)initContext.lookup("java:/comp/env");
			// DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
		}

		return conn;
	}

	/**
	 * close PreparedStatement
	 * 
	 * @param pstmt
	 */
	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		getConnection();
	}

	/**
	 * 手动设置事务起始<br>
	 * manually set a transaction
	 * 
	 * @param conn
	 */
	public static void beginTransaction(Connection conn) {
		if (null != conn) {
			try {
				if (conn.getAutoCommit()) {
					conn.setAutoCommit(false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 手动提交事务<br>
	 * manually commit a transaction
	 * 
	 * @param conn
	 */
	public static void commitTransaction(Connection conn) {
		if (null != conn) {
			try {
				// 如果本身就是auto提交方式，就不用再一次commit了
				if (!conn.getAutoCommit()) {
					conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 手动回滚事务<br>
	 * manually rollback
	 * 
	 * @param conn
	 */
	public static void rollbackTransaction(Connection conn) {
		if (null != conn) {
			try {
				if (!conn.getAutoCommit()) {
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 手动重置提交模式<br>
	 * manually reset to a default connection
	 * 
	 * @param conn
	 */
	public static void resetConnection(Connection conn) {
		if (null != conn) {
			try {
				if (!conn.getAutoCommit()) {
					conn.setAutoCommit(true);
				} else {
					conn.setAutoCommit(false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
