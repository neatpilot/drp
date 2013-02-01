package org.cn.pilot.drp.basedata.dao.factory;

import org.cn.pilot.drp.basedata.dao.dao.ItemDAO;

/**
 * ���Ϲ�������;item DAO interface
 * 
 * @author Pilot
 *
 */
public interface ItemDAOFactory {
	
	/**
	 * ��������DAO; create item DAO
	 * @return
	 */
	public ItemDAO createItemDao();
}
