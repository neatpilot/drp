package org.cn.pilot.drp.basedata.manager;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;

/**
 * ����ҵ���߼���ӿ�
 * 
 * @author Administrator
 * 
 */
public interface ItemManager {

	/**
	 * �������
	 * 
	 * @param item
	 * @throws ApplicationException
	 */
	public void addItem(Item item);

	/**
	 * �������ϴ���ļ���ɾ��
	 * 
	 * @param conn
	 * @param itemNos
	 */
	public void deleteItem(String[] itemNos);

	/**
	 * �޸�����
	 * 
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Item item);

	/**
	 * �������ϴ����ѯ
	 * 
	 * @param conn
	 * @param itemNo
	 * @return ������ڷ���Item���󣬷��򷵻�null
	 */
	public Item findItemById(String itemNo);

	/**
	 * ����������ҳ��ѯ
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param condation
	 * @return
	 */
	public PageModel findItemList(int pageNo, int pageSize, String condation);
}
