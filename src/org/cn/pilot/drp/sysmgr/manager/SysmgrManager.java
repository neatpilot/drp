package org.cn.pilot.drp.sysmgr.manager;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.exception.ApplicationException;

/**
 * 业务逻辑层接口
 * 
 * @author Pilot
 * @version 1.0 3/5/2013 新建
 */
public interface SysmgrManager {
	/**
	 * 增加用户
	 * 
	 * @param user
	 */
	public void addUser(User user) throws ApplicationException;;

	/**
	 * 删除用户
	 * 
	 * @param userIds
	 * @throws ApplicationException
	 */
	public void deleteUser(String[] userIds) throws ApplicationException;

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @throws ApplicationException
	 */
	public void modifyUser(User user) throws ApplicationException;

	/**
	 * 查询用户
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return 封装的PageModel，里面的list可能会没有值，但不是null
	 * @throws ApplicationException
	 */
	public PageModel<User> findUserList(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * 用户登录
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws ApplicationException
	 */
	public User login(String userId, String password) throws ApplicationException;

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param password
	 * @throws ApplicationException
	 */
//	public void modifyUserPassword(String userId, String password) throws ApplicationException;

	/**
	 * 根据ID来查找用户
	 * 
	 * @param userId
	 * @return
	 * @throws ApplicationException
	 */
	public User findUserById(String userId) throws ApplicationException;
}
