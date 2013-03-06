package org.cn.pilot.drp.sysmgr.manager;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.exception.ApplicationException;

/**
 * ҵ���߼���ӿ�
 * 
 * @author Pilot
 * @version 1.0 3/5/2013 �½�
 */
public interface SysmgrManager {
	/**
	 * �����û�
	 * 
	 * @param user
	 */
	public void addUser(User user) throws ApplicationException;;

	/**
	 * ɾ���û�
	 * 
	 * @param userIds
	 * @throws ApplicationException
	 */
	public void deleteUser(String[] userIds) throws ApplicationException;

	/**
	 * �޸��û�
	 * 
	 * @param user
	 * @throws ApplicationException
	 */
	public void modifyUser(User user) throws ApplicationException;

	/**
	 * ��ѯ�û�
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return ��װ��PageModel�������list���ܻ�û��ֵ��������null
	 * @throws ApplicationException
	 */
	public PageModel<User> findUserList(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * �û���¼
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws ApplicationException
	 */
	public User login(String userId, String password) throws ApplicationException;

	/**
	 * �޸��û�����
	 * 
	 * @param userId
	 * @param password
	 * @throws ApplicationException
	 */
//	public void modifyUserPassword(String userId, String password) throws ApplicationException;

	/**
	 * ����ID�������û�
	 * 
	 * @param userId
	 * @return
	 * @throws ApplicationException
	 */
	public User findUserById(String userId) throws ApplicationException;
}
