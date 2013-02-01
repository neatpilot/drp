package org.cn.pilot.drp.basedata.manager;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;

/**
 * 物料业务逻辑层接口
 * 
 * @author Administrator
 * 
 */
public interface ItemManager {

	/**
	 * 添加物料
	 * 
	 * @param item
	 * @throws ApplicationException
	 */
	public void addItem(Item item);

	/**
	 * 根据物料代码的集合删除
	 * 
	 * @param conn
	 * @param itemNos
	 */
	public void deleteItem(String[] itemNos);

	/**
	 * 修改物料
	 * 
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Item item);

	/**
	 * 根据物料代码查询
	 * 
	 * @param conn
	 * @param itemNo
	 * @return 如果存在返回Item对象，否则返回null
	 */
	public Item findItemById(String itemNo);

	/**
	 * 根据条件分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param condation
	 * @return
	 */
	public PageModel findItemList(int pageNo, int pageSize, String condation);
}
