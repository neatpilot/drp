package org.cn.pilot.drp.basedata.dao.impl;

import java.sql.Connection;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;

/**
 * MySql DAO Implementation
 * 
 * @author Pilot
 * 
 */
public class ItemDAO4MySqlImpl implements ItemDAO {

	@Override
	public void addItem(Connection conn, Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteItem(Connection conn, String[] itemNos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyItem(Connection conn, Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public Item findItemById(Connection conn, String itemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageModel findItemList(Connection conn, int pageNo, int pageSize, String condation) {
		// TODO Auto-generated method stub
		return null;
	}

}
