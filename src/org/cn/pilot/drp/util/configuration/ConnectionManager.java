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
 * ����ThreadLocal�������ݿ�����Connection
 * 
 * @author Pilot
 * 
 */
public class ConnectionManager {
	private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>(); // �̹߳������

	/**
	 * �������ݿ����ӣ��������ThreadLocal
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
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			}
			connLocal.set(conn);
		}
		return conn;
	}

	/**
	 * �ر����ݿ����ӣ�����ThreadLocal���Ƴ�
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

	/**
	 * �ر�һ��connection������
	 * 
	 * @param conn
	 * @throws ApplicationException
	 */
	//TODO �������ķ�������Ӧ����ʹ�ã�
	public static void close(Connection conn) throws ApplicationException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			}
		}
	}

	/**
	 * �ر�preparedStatement
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
				throw new ApplicationException("�����ݿ⡻ϵͳ��������ϵϵͳ����Ա");
			}
		}
	}

	/**
	 * �ر�ResultSet
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

	/**
	 * �ֶ��ع�����
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
