package org.cn.pilot.drp.basedata.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.PageModel;

public class SearchItemServlet extends AbstractItemServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pageNoString = req.getParameter("pageNo");
		String condition = req.getParameter("condition");

		int pageSize = Integer.parseInt(this.getServletContext().getInitParameter("page-size"));

		int pageNo = 1;
		if (null != pageNoString && "" != pageNoString) {
			pageNo = Integer.parseInt(pageNoString);
		}

		PageModel<Item> itemPageModel = itemManger.findItemList(pageNo, pageSize, condition);
		req.setAttribute("itemPageModel", itemPageModel);
		req.getRequestDispatcher("/basedata/item_maint.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
