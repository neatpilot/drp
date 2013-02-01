package org.cn.pilot.drp.basedata.dao.dao;

import java.sql.Connection;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;

/**
 * 物料数据访问接口
 * 
 * @author Administrator
 * 
 */
public interface ItemDAO {

	/**
	 * 添加物料
	 * 
	 * @param item
	 * @throws ApplicationException
	 */
	public void addItem(Connection conn, Item item);

	/**
	 * 根据物料代码的集合删除
	 * 
	 * @param conn
	 * @param itemNos
	 */
	public void deleteItem(Connection conn, String[] itemNos);

	/**
	 * 修改物料
	 * 
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Connection conn, Item item);

	/**
	 * 根据物料代码查询
	 * 
	 * @param conn
	 * @param itemNo
	 * @return 如果存在返回Item对象，否则返回null
	 */
	public Item findItemById(Connection conn, String itemNo);

	/**
	 * 根据条件分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param condation
	 * @return
	 */
	public PageModel findItemList(Connection conn, int pageNo, int pageSize, String condation);
}