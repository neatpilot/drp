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
	 * �õ����ݿ�����<br>
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
			throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
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
	 * �ֶ�����������ʼ<br>
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
	 * �ֶ��ύ����<br>
	 * manually commit a transaction
	 * 
	 * @param conn
	 */
	public static void commitTransaction(Connection conn) {
		if (null != conn) {
			try {
				// ����������auto�ύ��ʽ���Ͳ�����һ��commit��
				if (!conn.getAutoCommit()) {
					conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ֶ��ع�����<br>
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
	 * �ֶ������ύģʽ<br>
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
