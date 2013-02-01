package org.cn.pilot.drp.basedata.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.DbUtil;
import org.cn.pilot.drp.util.PageModel;

/**
 * Oracle DAO Implementation
 * 
 * @author Pilot
 * 
 */
public class ItemDAO4OracleImpl implements ItemDAO {

	@Override
	public void addItem(Connection conn, Item item) {
		String sql = "INSERT INTO t_items (item_no, item_name, spec, pattern, item_category_id, item_unit_id) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItemNo());
			pstmt.setString(2, item.getItemName());
			pstmt.setString(3, item.getSpec());
			pstmt.setString(4, item.getPattern());
			pstmt.setString(5, item.getItemCategory().getId());
			pstmt.setString(6, item.getItemUnit().getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("ÃÌº”ŒÔ¡œ ß∞‹");
		}finally {
			DbUtil.close(pstmt);
		}
	}

	@Override
	public void deleteItem(Connection conn, String[] itemNos) {

	}

	@Override
	public void modifyItem(Connection conn, Item item) {

	}

	@Override
	public Item findItemById(Connection conn, String itemNo) {
		return null;
	}

	@Override
	public PageModel findItemList(Connection conn, int pageNo, int pageSize, String condation) {
		return null;
	}

}
