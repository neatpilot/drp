package org.cn.pilot.drp.basedata.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.util.datadict.domain.ItemCategory;
import org.cn.pilot.drp.util.datadict.domain.ItemUnit;
import org.cn.pilot.drp.util.datadict.manager.DataDictManager;

public class ShowAddItemServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<ItemCategory> itemCategoryList = DataDictManager.getInstance().getItemCategoryList();
		List<ItemUnit> itemUnitList = DataDictManager.getInstance().getItemUnitList();
		
		req.setAttribute("itemCategoryList", itemCategoryList);
		req.setAttribute("itemUnitList", itemUnitList);
		
		req.getRequestDispatcher("/basedata/item_add.jsp").forward(req, resp);
	}

	
}
