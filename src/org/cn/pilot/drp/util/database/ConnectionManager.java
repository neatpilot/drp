package org.cn.pilot.drp.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.DbUtil;

/**
 * 采用ThreadLocal管理数据库连接Connection
 * 
 * @author Pilot
 * 
 */
public class ConnectionManager {
	private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>(); // 线程共享变量

	/**
	 * 返回数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() throws ApplicationException {
//		Connection conn = connLocal.get();
//		if (conn == null) {
//			// ThreadLocal中没有connection，则新建放入
//			try {
//				JdbcConfig dbConfig = XmlConfigReader.getInstance().getJdbcConfig();
//				Class.forName(dbConfig.getDriverName());
//				conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
//				connLocal.set(conn);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//				// connection 管理是在业务逻辑层
//				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
//			} catch (SQLException e) {
//				e.printStackTrace();
//				throw new ApplicationException("『数据库』系统错误，请联系系统管理员");
//			}
//		}
//		return conn;
		
		Connection conn = connLocal.get();
		if (conn == null) {
			// TODO DButil有遗留的引用，故把从数据库连接池获得connection的操作放在DBUtil
			conn = DbUtil.getConnection();
			connLocal.set(conn);
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
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

	public static void close(Connection conn) throws ApplicationException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("系统错误，请联系系统管理员");
			}
		}
	}

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
