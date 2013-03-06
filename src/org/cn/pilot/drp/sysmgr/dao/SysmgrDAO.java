package org.cn.pilot.drp.sysmgr.dao;

import java.util.List;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.exception.DAOException;

/**
 * @author Pilot
 * @version 1.0 3/4/2013 ����SysmgrDAO
 * 
 */
public interface SysmgrDAO {
	/**
	 * �����û�
	 * 
	 * @param user
	 * @throws DAOException
	 */
	public void addUser(User user) throws DAOException;

	/**
	 * ɾ���û�
	 * 
	 * @param userIds
	 * @throws DAOException
	 */
	public void deleteUser(String[] userIds) throws DAOException;

	/**
	 * �޸��û�
	 * 
	 * @param user
	 * @throws DAOException
	 */
	public void modifyUser(User user) throws DAOException;

	/**
	 * �޸��û�����
	 * 
	 * @param userId
	 * @param password
	 * @throws DAOException
	 */
	//public void modifyUserPassword(String userId, String password) throws DAOException;

	/**
	 * �����û�id�����û�
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public User findUserById(String userId) throws DAOException;

	/**
	 * ��ҳ��ѯ�û�
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws DAOException
	 */
	public List<User> findUserList(int pageNo, int pageSize) throws DAOException;

	/**
	 * �����ܼ�¼��(��ȥroot)
	 * 
	 * @param conn
	 * @return
	 * @throws DAOException
	 */
	public int getTotalRecords() throws DAOException;
}
