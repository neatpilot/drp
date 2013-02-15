package org.cn.pilot.drp.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cn.pilot.drp.util.BeanFactory;
import org.cn.pilot.drp.util.Constants;

public class InitServlet extends HttpServlet {

	/*
	 * TOMCAT��ʼ��ʱ�򴴽�����ҳ���б���BeanFactory��һ��ʵ��������ֱ�ӵ���
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		BeanFactory beanFactory = BeanFactory.getInstance();
		this.getServletContext().setAttribute("beanFactory", beanFactory);

		// ���볣��
		getServletContext().setAttribute("add", Constants.ADD);
		getServletContext().setAttribute("del", Constants.DELETE);
		getServletContext().setAttribute("modify", Constants.MODIFY);
		getServletContext().setAttribute("showAdd", Constants.SHOW_ADD);
		getServletContext().setAttribute("audit", Constants.AUDIT);
	}

}
