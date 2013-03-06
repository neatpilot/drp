package org.cn.pilot.drp.sysmgr.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.sysmgr.manager.SysmgrManager;
import org.cn.pilot.drp.util.exception.ApplicationException;
import org.cn.pilot.drp.util.servlet.BaseServlet;

/**
 * @author Pilot
 * @version ---1.0 登录servlet，需要在AuthRoleFilter中配置 ---[ Mar 6, 2013 1:42:03 AM ] -->
 */
public class LoginServlet extends BaseServlet {
	private SysmgrManager sysmgrManager;

	@Override
	public void init() throws ServletException {
		sysmgrManager = (SysmgrManager) getBeanFactory().getServiceObjectWithDynamicProxy(SysmgrManager.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String captcha = req.getParameter("captcha");
		if (!captcha.trim().equalsIgnoreCase((String) req.getSession().getAttribute("rand"))) {
			req.setAttribute("error", "验证码错误");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		} else {
			try {
				User user = sysmgrManager.login(userId, password);

				req.getSession().setAttribute("user_info", user);
				resp.sendRedirect(req.getContextPath() + "/main.jsp");
			} catch (ApplicationException e) {
				if ("用户不存在".equals(e.getMessage()) || "用户密码错误".equals(e.getMessage())) {
					req.setAttribute("error", e.getMessage());
					req.getRequestDispatcher("/login.jsp").forward(req, resp);
				}else{
					throw e;
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
