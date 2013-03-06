package org.cn.pilot.drp.util.datadict.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cn.pilot.drp.util.configuration.ConnectionManager;
import org.cn.pilot.drp.util.datadict.domain.ClientLevel;
import org.cn.pilot.drp.util.datadict.domain.ItemCategory;
import org.cn.pilot.drp.util.datadict.domain.ItemUnit;

public class DataDictManager {

	private static DataDictManager instance = new DataDictManager();

	private DataDictManager() {

	}

	public static DataDictManager getInstance() {
		return instance;
	}

	/**
	 * return a list of client level
	 * 
	 * @return
	 */
	public List<ClientLevel> getClientLevelList() {
		String sql = "SELECT id,name FROM t_data_dict WHERE category= 'A'";
		List<ClientLevel> list = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<ClientLevel>();
			while (rs.next()) {
				ClientLevel cl = new ClientLevel();
				cl.setId(rs.getString("id"));
				cl.setName(rs.getString("name"));
				list.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
			ConnectionManager.close(conn);
		}

		return list;
	}

	/**
	 * return a list of item category
	 * 
	 * @return
	 */
	public List<ItemCategory> getItemCategoryList() {
		String sql = "SELECT id,name FROM t_data_dict WHERE category= 'C'";
		List<ItemCategory> list = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemCategory>();
			while (rs.next()) {
				ItemCategory cl = new ItemCategory();
				cl.setId(rs.getString("id"));
				cl.setName(rs.getString("name"));
				list.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
			ConnectionManager.close(conn);
		}

		return list;
	}

	/**
	 * return a list of item category
	 * 
	 * @return
	 */
	public List<ItemUnit> getItemUnitList() {
		String sql = "SELECT id,name FROM t_data_dict WHERE category= 'D'";
		List<ItemUnit> list = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemUnit>();
			while (rs.next()) {
				ItemUnit cl = new ItemUnit();
				cl.setId(rs.getString("id"));
				cl.setName(rs.getString("name"));
				list.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
			ConnectionManager.close(conn);
		}

		return list;
	}
}
