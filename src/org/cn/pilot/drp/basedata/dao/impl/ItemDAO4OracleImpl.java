package org.cn.pilot.drp.basedata.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.configuration.ConnectionManager;
import org.cn.pilot.drp.util.datadict.domain.ItemCategory;
import org.cn.pilot.drp.util.datadict.domain.ItemUnit;
import org.cn.pilot.drp.util.exception.ApplicationException;

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
			throw new ApplicationException("添加物料失败");
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	@Override
	public void deleteItem(Connection conn, String[] itemNos) {
		StringBuffer sqlBuffer = new StringBuffer();
		for (int i = 0; i < itemNos.length; i++) {
			sqlBuffer.append("?,");
		}
		String sql = "DELETE FROM t_items WHERE item_no IN("
				+ sqlBuffer.toString().substring(0, sqlBuffer.length() - 1) + ")";
		System.out.println(this.getClass().getName() + ">>>>>>> sql:" + sql);

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < itemNos.length; i++) {
				pstmt.setString(i+1, itemNos[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("删除物料失败");
		} finally {
			ConnectionManager.close(pstmt);
		}

	}

	@Override
	public void modifyItem(Connection conn, Item item) {
		String sql = "UPDATE t_items SET item_name=?, spec=?, pattern=?, item_category_id=?, item_unit_id=?, file_name=? WHERE item_no=?";

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItemName());
			pstmt.setString(2, item.getSpec());
			pstmt.setString(3, item.getPattern());
			pstmt.setString(4, item.getItemCategory().getId());
			pstmt.setString(5, item.getItemUnit().getId());
			pstmt.setString(6, item.getFileName());
			pstmt.setString(7, item.getItemNo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("修改物料失败");
		} finally {
			ConnectionManager.close(pstmt);
		}

	}

	@Override
	public Item findItemById(Connection conn, String itemNo) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select a.item_no, a.item_name, a.spec, a.pattern, a.item_category_id, ")
				.append("b.name as item_category_name, a.item_unit_id, c.name as item_unit_name, a.file_name ")
				.append("from t_items a, t_data_dict b, t_data_dict c ")
				.append("where a.item_category_id=b.id and a.item_unit_id=c.id and a.item_no=?");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Item item = null;
		try {
			pstmt = conn.prepareStatement(sbSql.toString());
			pstmt.setString(1, itemNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				item = new Item();
				item.setItemNo(rs.getString("item_no"));
				item.setItemName(rs.getString("item_name"));
				item.setSpec(rs.getString("spec"));
				item.setPattern(rs.getString("pattern"));
				// 构造ItemCategory
				ItemCategory ic = new ItemCategory();
				ic.setId(rs.getString("item_category_id"));
				ic.setName(rs.getString("item_category_name"));
				item.setItemCategory(ic);

				// 构造ItemUnit
				ItemUnit iu = new ItemUnit();
				iu.setId(rs.getString("item_unit_id"));
				iu.setName(rs.getString("item_unit_name"));
				item.setItemUnit(iu);

				item.setFileName(rs.getString("file_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("物料查询出错，物料代码[" + itemNo + "]");
		} finally {
			ConnectionManager.close(rs);
			ConnectionManager.close(pstmt);
		}
		return item;
	}

	@Override
	public PageModel<Item> findItemList(Connection conn, int pageNo, int pageSize, String condition) {
		PageModel<Item> itemPageModel = new PageModel<Item>();
		// 构造SQL语句
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * ").append("from (").append("select i.*, rownum rn from (")
				.append("select a.item_no, a.item_name, a.spec, a.pattern, a.item_category_id, ")
				.append("b.name as item_category_name, a.item_unit_id, c.name as item_unit_name, a.file_name ")
				.append("from t_items a, t_data_dict b, t_data_dict c ")
				.append("where a.item_category_id=b.id and a.item_unit_id=c.id  ");
		if (condition != null && !"".equals(condition)) {
			sqlBuffer.append(" and (a.item_no like '" + condition + "%' or a.item_name like '" + condition + "%') ");
		}
		sqlBuffer.append(" order by a.item_no").append(") i where rownum<=? ").append(") ").append("where rn >? ");
		System.out.println(this.getClass().getName() + "-----" + sqlBuffer.toString());

		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			pstmt.setInt(1, pageNo * pageSize);
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			rs = pstmt.executeQuery();
			// 构造PageModel(list,pageNo,pageSize,totalCount)
			List<Item> itemsList = new ArrayList<Item>();
			while (rs.next()) {
				Item item = new Item();
				item.setItemNo(rs.getString("item_no"));
				item.setItemName(rs.getString("item_name"));
				item.setSpec(rs.getString("spec"));
				item.setPattern(rs.getString("pattern"));
				// 构造ItemCategory
				ItemCategory ic = new ItemCategory();
				ic.setId(rs.getString("item_category_id"));
				ic.setName(rs.getString("item_category_name"));
				item.setItemCategory(ic);

				// 构造ItemUnit
				ItemUnit iu = new ItemUnit();
				iu.setId(rs.getString("item_unit_id"));
				iu.setName(rs.getString("item_unit_name"));
				item.setItemUnit(iu);

				item.setFileName(rs.getString("file_name"));

				itemsList.add(item);
			}

			itemPageModel.setPageNo(pageNo);
			itemPageModel.setPageSize(pageSize);
			itemPageModel.setList(itemsList);
			// 根据条件取得记录数
			int totalRecords = getTotalRecords(conn, condition);
			itemPageModel.setTotalRecords(totalRecords);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException("分页查询失败");
		} finally {
			ConnectionManager.close(pstmt);
			ConnectionManager.close(rs);
		}

		return itemPageModel;
	}

	/**
	 * 被分页查询逻辑调用
	 * 
	 * @param conn
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	private int getTotalRecords(Connection conn, String condition) throws SQLException {
		int count = 0;
		String sql = "SELECT count(*) FROM t_items ";
		if (null != condition) {
			sql += "WHERE (item_no LIKE 'condition' OR item_name LIKE '" + condition + "%' )";
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			ConnectionManager.close(pstmt);
			ConnectionManager.close(rs);
		}

		return count;
	}

}
