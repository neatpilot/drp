package org.cn.pilot.drp.basedata.manager;

import java.sql.Connection;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.BeanFactory;
import org.cn.pilot.drp.util.DbUtil;
import org.cn.pilot.drp.util.PageModel;

public class ItemManagerImpl implements ItemManager {

	private ItemDAO itemDAO = null;

	public ItemManagerImpl() {
		itemDAO = (ItemDAO) BeanFactory.getInstance().getDAOObject(org.cn.pilot.drp.basedata.dao.dao.ItemDAO.class);
	}

	@Override
	public void addItem(Item item) {
		Connection conn = null;
		try {
			conn = DbUtil.getConnection();
			if (itemDAO.findItemById(conn, item.getItemNo()) != null) {
				throw new ApplicationException("物料代码已经存在，代码=" + item.getItemNo() + "");
			}
			itemDAO.addItem(conn, item);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}

	}

	@Override
	public void deleteItem(String[] itemNos) {
		Connection conn = null;

		try {
			conn = DbUtil.getConnection();
			itemDAO.deleteItem(conn, itemNos);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}
	}

	@Override
	public void modifyItem(Item item) {
		Connection conn = null;

		try {
			conn = DbUtil.getConnection();
			itemDAO.modifyItem(conn, item);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}

	}

	@Override
	public Item findItemById(String itemNo) {
		Item item = null;
		Connection conn = null;

		try {
			conn = DbUtil.getConnection();
			item = itemDAO.findItemById(conn, itemNo);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}
		return item;
	}

	@Override
	public PageModel<Item> findItemList(int pageNo, int pageSize, String condition) {
		PageModel<Item> itemPageModel = null;
		Connection conn = null;

		try {
			conn = DbUtil.getConnection();
			itemPageModel = itemDAO.findItemList(conn, pageNo, pageSize, condition);
		} catch (ApplicationException e) {
			throw e;
		} finally {
			DbUtil.close(conn);
		}
		return itemPageModel;
	}

}
