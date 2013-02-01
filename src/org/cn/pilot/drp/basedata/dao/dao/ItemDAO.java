package org.cn.pilot.drp.basedata.dao.dao;

import java.sql.Connection;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;

/**
 * �������ݷ��ʽӿ�
 * 
 * @author Administrator
 * 
 */
public interface ItemDAO {

	/**
	 * �������
	 * 
	 * @param item
	 * @throws ApplicationException
	 */
	public void addItem(Connection conn, Item item);

	/**
	 * �������ϴ���ļ���ɾ��
	 * 
	 * @param conn
	 * @param itemNos
	 */
	public void deleteItem(Connection conn, String[] itemNos);

	/**
	 * �޸�����
	 * 
	 * @param conn
	 * @param item
	 */
	public void modifyItem(Connection conn, Item item);

	/**
	 * �������ϴ����ѯ
	 * 
	 * @param conn
	 * @param itemNo
	 * @return ������ڷ���Item���󣬷��򷵻�null
	 */
	public Item findItemById(Connection conn, String itemNo);

	/**
	 * ����������ҳ��ѯ
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param condation
	 * @return
	 */
	public PageModel findItemList(Connection conn, int pageNo, int pageSize, String condation);
}