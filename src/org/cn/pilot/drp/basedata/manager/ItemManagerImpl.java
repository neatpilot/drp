package org.cn.pilot.drp.basedata.manager;

import java.sql.Connection;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.configuration.BeanFactory;
import org.cn.pilot.drp.util.configuration.ConnectionManager;
import org.cn.pilot.drp.util.exception.ApplicationException;

public class ItemManagerImpl implements ItemManager {

	private ItemDAO itemDAO = null;

	public ItemManagerImpl() {
		itemDAO = (ItemDAO) BeanFactory.getInstance().getDAOObject(org.cn.pilot.drp.basedata.dao.dao.ItemDAO.class);
	}

	@Override
	public void addItem(Item item) {
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			if (itemDAO.findItemById(conn, item.getItemNo()) != null) {
				throw new ApplicationException("物料代码已经存在，代码=" + item.getItemNo() + "");
			}
			itemDAO.addItem(conn, item);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}

	}

	@Override
	public void deleteItem(String[] itemNos) {
		Connection conn = null;

		try {
			conn = ConnectionManager.getConnection();
			itemDAO.deleteItem(conn, itemNos);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
	}

	@Override
	public void modifyItem(Item item) {
		Connection conn = null;

		try {
			conn = ConnectionManager.getConnection();
			itemDAO.modifyItem(conn, item);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}

	}

	@Override
	public Item findItemById(String itemNo) {
		Item item = null;
		Connection conn = null;

		try {
			conn = ConnectionManager.getConnection();
			item = itemDAO.findItemById(conn, itemNo);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		return item;
	}

	@Override
	public PageModel<Item> findItemList(int pageNo, int pageSize, String condition) {
		PageModel<Item> itemPageModel = null;
		Connection conn = null;

		try {
			conn = ConnectionManager.getConnection();
			itemPageModel = itemDAO.findItemList(conn, pageNo, pageSize, condition);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		return itemPageModel;
	}

}
