package org.cn.pilot.drp.basedata.dao.factory;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;
import org.cn.pilot.drp.basedata.dao.impl.ItemDAO4OracleImpl;

public class ItemDAOFactory4Oracle implements ItemDAOFactory {

	@Override
	public ItemDAO createItemDao() {
		return new ItemDAO4OracleImpl();
	}

}
