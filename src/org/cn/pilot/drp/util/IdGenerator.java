package org.cn.pilot.drp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdGenerator {
	/**
	 * �õ�idֵ��������;get id value and then increase it into databse
	 * @param tableName
	 * @return
	 * @throws ���´���;update error
	 */
	public static int generate(String tableName){
		int value = -1;
		String sql = "SELECT value FROM t_table_id WHERE table_name=? FOR UPDATE";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. get current value �õ���ǰID
			conn = DbUtil.getConnection();
			DbUtil.beginTransaction(conn);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();

			if(!rs.next()){
				throw new RuntimeException();
			}
			
			value = rs.getInt(1);
			
			//2. modify value++ to database �޸�
			value ++;
			modifyIdValue(value, tableName, conn);
			DbUtil.commitTransaction(conn);	
		} catch (SQLException e) {
			e.printStackTrace();
			DbUtil.rollbackTransaction(conn);
			throw new RuntimeException();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(pstmt);
			DbUtil.resetConnection(conn);
			DbUtil.close(conn);
			
		}
		
		return value;
	}
	
	/**
	 * ���¶�Ӧ���id��¼;update relative id value into the corresponding table
	 * @param idValue
	 * @param tableName
	 * @param conn
	 * @throws SQLException
	 */
	private static void modifyIdValue(int idValue, String tableName,Connection conn) throws SQLException{
		String sql = "UPDATE t_table_id SET value=? WHERE table_name=?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idValue);
			pstmt.setString(2, tableName);
			pstmt.executeUpdate();
		} finally{
			DbUtil.close(pstmt);
		}
		
	}
}
