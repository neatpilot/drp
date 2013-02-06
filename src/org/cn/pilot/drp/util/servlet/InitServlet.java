package org.cn.pilot.drp.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cn.pilot.drp.util.BeanFactory;

public class InitServlet extends HttpServlet {

	/* TOMCAT初始的时候创建，在页面中保存BeanFactory的一个实例，可以直接调用
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		BeanFactory beanFactory = BeanFactory.getInstance();
		this.getServletContext().setAttribute("beanFactory", beanFactory);
	}

}
