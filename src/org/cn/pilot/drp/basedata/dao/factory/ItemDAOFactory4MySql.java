package org.cn.pilot.drp.basedata.dao.factory;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.dao.impl.ItemDAO4MySqlImpl;

public class ItemDAOFactory4MySql implements ItemDAOFactory {

	@Override
	public ItemDAO createItemDao() {
		return new ItemDAO4MySqlImpl();
	}

}
