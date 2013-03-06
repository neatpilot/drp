package org.cn.pilot.drp.sysmgr.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.sysmgr.manager.SysmgrManager;
import org.cn.pilot.drp.util.Constants;
import org.cn.pilot.drp.util.PageModel;
import org.cn.pilot.drp.util.exception.ApplicationException;
import org.cn.pilot.drp.util.servlet.BaseServlet;

/**
 * @author Pilot
 * @version ---1.0 用户管理流转Servlet ---[ Mar 5, 2013 5:17:14 PM ] -->
 */
public class SysmgrServlet extends BaseServlet {
	private SysmgrManager sysmgrManager;

	private int pageSize;

	@Override
	public void init() throws ServletException {
		sysmgrManager = (SysmgrManager) getBeanFactory().getServiceObjectWithDynamicProxy(SysmgrManager.class);
		pageSize = Integer.parseInt(this.getInitParameter("page_size"));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO 采取策略模式处理多分支; need to be optimized by Strategy Pattern
		if (Constants.ADD.equals(getCommand())) {
			add(req, resp);
		} else if (Constants.SHOW_ADD.equals(getCommand())) {
			showAdd(req, resp);
		} else if ("validate".equals(getCommand())) {
			validateUser(req, resp);
		} else if (Constants.SHOW_MODIFY.equals(getCommand())) {
			showModify(req, resp);
		} else if (Constants.MODIFY.equals(getCommand())) {
			modifyUser(req, resp);
		} else if (Constants.DELETE.equals(getCommand())) {
			deleteUser(req, resp);
		} else if ("modifyPassword".equals(getCommand())) {
			modifyPassword(req, resp);
		} else if ("validatePassword".equals(getCommand())) {
			validatePassword(req, resp);
		} else {
			search(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo = 1;
		if (null != request.getParameter("pageNo")) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		PageModel<User> userList = sysmgrManager.findUserList(pageNo, pageSize);
		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/sysmgr/user_maint.jsp").forward(request, response);
	}

	private void showAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/sysmgr/user_add.jsp");
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String contactTel = request.getParameter("contactTel");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		user.setPassword(password);
		user.setContactTel(contactTel);
		user.setEmail(email);

		sysmgrManager.addUser(user);

		response.sendRedirect(request.getContextPath() + "/servlet/SysmgrServlet");
	}

	private void validateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		boolean userExisted = false;
		if (null != sysmgrManager.findUserById(userId)) {
			userExisted = true;
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (userExisted == true) {
			out.write("用户存在");
		} else {
			out.write("");
		}
	}

	private void showModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		User user = sysmgrManager.findUserById(userId);
		request.setAttribute("user", user);
		request.getRequestDispatcher("/sysmgr/user_modify.jsp").forward(request, response);
	}

	private void modifyUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setUserId(request.getParameter("userId"));
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		user.setContactTel(request.getParameter("contactTel"));
		user.setEmail(request.getParameter("email"));

		sysmgrManager.modifyUser(user);
		response.sendRedirect(request.getContextPath() + "/servlet/SysmgrServlet");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] userIds = request.getParameterValues("selectFlag");
		sysmgrManager.deleteUser(userIds);

		response.sendRedirect(request.getContextPath() + "/servlet/SysmgrServlet");
	}

	private void modifyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newPassword = request.getParameter("newPassword");
		newPassword = newPassword.trim();
		User user = (User) request.getSession().getAttribute("user_info");
		user.setPassword(newPassword);
		try {
			sysmgrManager.modifyUser(user);

		} catch (ApplicationException e) {
			throw new ApplicationException("修改密码失败");
		} finally {
			// 修改session
			user.setPassword(newPassword);
			request.getSession().removeAttribute("user_info");
			request.getSession().setAttribute("user_info", user);
		}
		response.sendRedirect(request.getContextPath() + "/servlet/SysmgrServlet");
	}

	private void validatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oldPassword = request.getParameter("oldPassword");

		response.setContentType("text/html;charset=UTF-8");

		User user = (User) request.getSession().getAttribute("user_info");

		if (!user.getPassword().equals(oldPassword)) {
			response.getWriter().write("原密码错误");
		}
	}
}
