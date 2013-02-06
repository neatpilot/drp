package org.cn.pilot.drp.basedata.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.datadict.domain.ItemCategory;
import org.cn.pilot.drp.util.datadict.domain.ItemUnit;
import org.cn.pilot.drp.util.datadict.manager.DataDictManager;

public class ShowModifyItemServlet extends AbstractItemServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String itemNo = req.getParameter("itemNo");

		Item item = itemManger.findItemById(itemNo);

		req.setAttribute("item", item);

		List<ItemCategory> itemCategoryList = DataDictManager.getInstance().getItemCategoryList();

		List<ItemUnit> itemUnitList = DataDictManager.getInstance().getItemUnitList();

		req.setAttribute("itemCategoryList", itemCategoryList);
		req.setAttribute("itemUnitList", itemUnitList);
		req.getRequestDispatcher("/basedata/item_modify.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
