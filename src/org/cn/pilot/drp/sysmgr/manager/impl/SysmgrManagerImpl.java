package org.cn.pilot.drp.sysmgr.manager.impl;

import java.util.List;

import org.cn.pilot.drp.sysmgr.dao.SysmgrDAO;
import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.sysmgr.manager.SysmgrManager;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.configuration.BeanFactory;
import org.cn.pilot.drp.util.exception.ApplicationException;
import org.cn.pilot.drp.util.exception.DAOException;

/**
 * 业务逻辑层实现类
 * 
 * @author Pilot
 * @version ---1.0 新建 ---[ Mar 5, 2013 12:49:26 AM ]
 */
public class SysmgrManagerImpl implements SysmgrManager {
	private SysmgrDAO sysmgrDAO = null;

	public SysmgrManagerImpl() {
		this.sysmgrDAO = (SysmgrDAO) BeanFactory.getInstance().getDAOObject(SysmgrDAO.class);
	}

	@Override
	public void addUser(User user) throws ApplicationException {
		if (null != sysmgrDAO.findUserById(user.getUserId())) {
			throw new ApplicationException("用户代码存在");
		}
		try {
			sysmgrDAO.addUser(user);
		} catch (DAOException e) {
			throw new ApplicationException("添加用户失败");
		}
	}

	@Override
	public void deleteUser(String[] userIds) throws ApplicationException {
		try {
			sysmgrDAO.deleteUser(userIds);
		} catch (DAOException e) {
			throw new ApplicationException("删除用户失败");
		}
	}

	@Override
	public void modifyUser(User user) throws ApplicationException {
		try {
			sysmgrDAO.modifyUser(user);
		} catch (DAOException e) {
			throw new ApplicationException("修改用户失败");
		}
	}

	@Override
	public PageModel<User> findUserList(int pageNo, int pageSize) throws ApplicationException {
		PageModel<User> userPageModel = null;
		try {
			List<User> userList = sysmgrDAO.findUserList(pageNo, pageSize);
			int totalRecords = sysmgrDAO.getTotalRecords();

			userPageModel = new PageModel<User>();
			userPageModel.setList(userList);
			userPageModel.setPageNo(pageNo);
			userPageModel.setPageSize(pageSize);
			userPageModel.setTotalRecords(totalRecords);
		} catch (Exception e) {
			throw new ApplicationException("查询用户失败");
		}
		return userPageModel;
	}

	@Override
	public User login(String userId, String password) throws ApplicationException {
		User user = null;
		try {
			user = sysmgrDAO.findUserById(userId);
			if (null == user) {
				throw new ApplicationException("用户不存在");
			} else if (!user.getPassword().equals(password)) {
				throw new ApplicationException("用户密码错误");
			}
		} catch (DAOException e) {
			throw new ApplicationException("用户登录失败");
		}
		return user;
	}

//	@Override
//	public void modifyUserPassword(String userId, String password) throws ApplicationException {
//		try {
//			sysmgrDAO.modifyUserPassword(userId, password);
//		} catch (DAOException e) {
//			throw new ApplicationException("修改用户密码失败");
//		}
//	}

	@Override
	public User findUserById(String userId) throws ApplicationException {
		User user;
		try {
			user = sysmgrDAO.findUserById(userId);
		} catch (DAOException e) {
			throw new ApplicationException("查询用户存在失败");
		}
		return user;
	}

}
