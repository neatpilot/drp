package org.cn.pilot.drp.util.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cn.pilot.drp.util.Constants;
import org.cn.pilot.drp.util.PLog;
import org.cn.pilot.drp.util.configuration.BeanFactory;

/**
 * @author Pilot
 * @version ---1.0 ��������ʼ������[ same as InitListener} ---
 */
public class InitServlet extends HttpServlet {

	/*
	 * TOMCAT��ʼ��ʱ�򴴽�����ҳ���б���BeanFactory��һ��ʵ��������ֱ�ӵ���
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		System.out.println("��ʼ��BeanFactory" + PLog.atLocation(this));
		BeanFactory beanFactory = BeanFactory.getInstance();
		this.getServletContext().setAttribute("beanFactory", beanFactory);

		// ���볣��
		getServletContext().setAttribute("add", Constants.ADD);
		getServletContext().setAttribute("delete", Constants.DELETE);
		getServletContext().setAttribute("modify", Constants.MODIFY);
		getServletContext().setAttribute("showAdd", Constants.SHOW_ADD);
		getServletContext().setAttribute("showModify", Constants.SHOW_MODIFY);
		getServletContext().setAttribute("audit", Constants.AUDIT);
	}

}
