package org.cn.pilot.drp.sysmgr.dao;

import java.util.List;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.exception.DAOException;

/**
 * @author Pilot
 * @version 1.0 3/4/2013 建立SysmgrDAO
 * 
 */
public interface SysmgrDAO {
	/**
	 * 增加用户
	 * 
	 * @param user
	 * @throws DAOException
	 */
	public void addUser(User user) throws DAOException;

	/**
	 * 删除用户
	 * 
	 * @param userIds
	 * @throws DAOException
	 */
	public void deleteUser(String[] userIds) throws DAOException;

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @throws DAOException
	 */
	public void modifyUser(User user) throws DAOException;

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param password
	 * @throws DAOException
	 */
	//public void modifyUserPassword(String userId, String password) throws DAOException;

	/**
	 * 根据用户id查找用户
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public User findUserById(String userId) throws DAOException;

	/**
	 * 分页查询用户
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws DAOException
	 */
	public List<User> findUserList(int pageNo, int pageSize) throws DAOException;

	/**
	 * 返回总记录数(除去root)
	 * 
	 * @param conn
	 * @return
	 * @throws DAOException
	 */
	public int getTotalRecords() throws DAOException;
}
