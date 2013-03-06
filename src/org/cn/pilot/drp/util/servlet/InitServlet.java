package org.cn.pilot.drp.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cn.pilot.drp.util.Constants;
import org.cn.pilot.drp.util.PLog;
import org.cn.pilot.drp.util.configuration.BeanFactory;

/**
 * @author Pilot
 * @version ---1.0 服务器初始化参数[ same as InitListener} ---
 */
public class InitServlet extends HttpServlet {

	/*
	 * TOMCAT初始的时候创建，在页面中保存BeanFactory的一个实例，可以直接调用
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		System.out.println("初始化BeanFactory" + PLog.atLocation(this));
		BeanFactory beanFactory = BeanFactory.getInstance();
		this.getServletContext().setAttribute("beanFactory", beanFactory);

		// 加入常量
		getServletContext().setAttribute("add", Constants.ADD);
		getServletContext().setAttribute("delete", Constants.DELETE);
		getServletContext().setAttribute("modify", Constants.MODIFY);
		getServletContext().setAttribute("showAdd", Constants.SHOW_ADD);
		getServletContext().setAttribute("showModify", Constants.SHOW_MODIFY);
		getServletContext().setAttribute("audit", Constants.AUDIT);
	}

}
