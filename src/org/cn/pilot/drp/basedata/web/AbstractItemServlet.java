package org.cn.pilot.drp.basedata.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cn.pilot.drp.basedata.manager.ItemManager;
import org.cn.pilot.drp.util.BeanFactory;

public class AbstractItemServlet extends HttpServlet {
	protected ItemManager itemManger;

	@Override
	public void init() throws ServletException {
		BeanFactory beanFactory = (BeanFactory) this.getServletContext().getAttribute("beanFactory");
		itemManger = (ItemManager) beanFactory.getServiceObject(org.cn.pilot.drp.basedata.manager.ItemManager.class);
	}
}
