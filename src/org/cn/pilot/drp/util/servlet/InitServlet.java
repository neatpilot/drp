package org.cn.pilot.drp.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cn.pilot.drp.util.BeanFactory;

public class InitServlet extends HttpServlet {

	/* TOMCAT��ʼ��ʱ�򴴽�����ҳ���б���BeanFactory��һ��ʵ��������ֱ�ӵ���
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		BeanFactory beanFactory = BeanFactory.getInstance();
		this.getServletContext().setAttribute("beanFactory", beanFactory);
	}

}
