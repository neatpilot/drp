package org.cn.pilot.drp.basedata.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteItemServlet extends AbstractItemServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] itemNos = req.getParameterValues("selectFlag");
		itemManger.deleteItem(itemNos);

		resp.sendRedirect(req.getContextPath() + "/servlet/item/SearchItemServlet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
