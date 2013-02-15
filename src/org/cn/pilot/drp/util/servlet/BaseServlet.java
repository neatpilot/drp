package org.cn.pilot.drp.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.sysmgr.domain.User;
import org.cn.pilot.drp.util.BeanFactory;

/**
 * ¹«¹²servlet³éÏó
 * 
 * @author Pilot
 * 
 */
public class BaseServlet extends HttpServlet {

	private User user;

	private BeanFactory beanFactory;

	private String command;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		command = req.getParameter("command");
		user = (User) req.getSession().getAttribute("user_info");
		super.service(req, resp);
	}

	public User getUser() {
		return user;
	}

	public BeanFactory getBeanFactory() {
		beanFactory = (BeanFactory) this.getServletContext().getAttribute("beanFactory");
		return beanFactory;
	}

	public String getCommand() {
		return command;
	}

}
