package org.cn.pilot.drp.basedata.dao.factory;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;

/**
 * 物料工厂抽象;item DAO interface
 * 
 * @author Pilot
 *
 */
public interface ItemDAOFactory {
	
	/**
	 * 创建物料DAO; create item DAO
	 * @return
	 */
	public ItemDAO createItemDao();
}
