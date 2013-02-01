package org.cn.pilot.drp.basedata.manager;

import java.sql.Connection;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.dao.factory.ItemDAOFactory;
import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.ApplicationException;
import org.cn.pilot.drp.util.DbUtil;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.XmlConfigReader;

public class ItemManagerImpl implements ItemManager {

	private ItemDAOFactory factory = null;

	@Override
	public void addItem(Item item) {
		String factoryClassName = XmlConfigReader.getInstance().getDAOFactory("item-dao-factory");
		try {
			factory = (ItemDAOFactory) Class.forName(factoryClassName).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ItemDAO itemDAO = factory.createItemDao();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyItem(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public Item findItemById(String itemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageModel findItemList(int pageNo, int pageSize, String condation) {
		// TODO Auto-generated method stub
		return null;
	}

}
