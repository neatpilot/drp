package org.cn.pilot.drp.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.DbUtil;

/**
 * ����ThreadLocal�������ݿ�����Connection
 * 
 * @author Pilot
 * 
 */
public class ConnectionManager {
	private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>(); // �̹߳������

	/**
	 * �������ݿ�����
	 * 
	 * @return
	 */
	public static Connection getConnection() throws ApplicationException {
//		Connection conn = connLocal.get();
//		if (conn == null) {
//			// ThreadLocal��û��connection�����½�����
//			try {
//				JdbcConfig dbConfig = XmlConfigReader.getInstance().getJdbcConfig();
//				Class.forName(dbConfig.getDriverName());
//				conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
//				connLocal.set(conn);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//				// connection ��������ҵ���߼���
//				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
//			} catch (SQLException e) {
//				e.printStackTrace();
//				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
//			}
//		}
//		return conn;
		
		Connection conn = connLocal.get();
		if (conn == null) {
			// TODO DButil�����������ã��ʰѴ����ݿ����ӳػ��connection�Ĳ�������DBUtil
			conn = DbUtil.getConnection();
			connLocal.set(conn);
		}
		return conn;
	}

	/**
	 * �ر����ݿ�����
	 */
	public static void closeConnection() throws ApplicationException {
		Connection conn = connLocal.get();
		if (conn != null) {
			try {
				conn.close();
				connLocal.remove(); // remove�ٺ���Ϊconnection�رտ��ܻ����쳣
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			}
		}
	}

	public static void close(Connection conn) throws ApplicationException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("ϵͳ��������ϵϵͳ����Ա");
			}
		}
	}

	public static void close(PreparedStatement pstmt) throws ApplicationException {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			}
		}
	}

	public static void close(ResultSet rs) throws ApplicationException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			}
		}
	}

	/**
	 * �ֶ���������
	 * 
	 * @param conn
	 *            �ô����ݿ�����
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
			throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
		}
	}

	/**
	 * �ֶ��ύ����
	 * 
	 * @param conn
	 *            �ô����ݿ�����
	 */
	public static void commitTransaction(Connection conn) throws ApplicationException {
		try {
			if (conn != null) {
				if (!conn.getAutoCommit()) {
					// conn.setAutoCommit(true); ?? connection�رջ�����ô��
					conn.commit();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
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
			throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
		}
	}

	/**
	 * �ֶ������ύģʽ
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
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			}
		}
	}
}
