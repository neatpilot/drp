package org.cn.pilot.drp.basedata.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.util.datadict.domain.ItemCategory;
import org.cn.pilot.drp.util.datadict.domain.ItemUnit;

public class ModifyItemServlet extends AbstractItemServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// form data
		String itemNo = req.getParameter("itemNo");
		String itemName = req.getParameter("itemName");
		String spec = req.getParameter("spec");
		String pattern = req.getParameter("pattern");
		String category = req.getParameter("category");
		String unit = req.getParameter("unit");

		// Item
		Item item = new Item();
		item.setItemNo(itemNo);
		item.setItemName(itemName);
		item.setSpec(spec);
		item.setPattern(pattern);

		// ItemCategory
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setId(category);
		item.setItemCategory(itemCategory);

		// ItemUnit
		ItemUnit itemUnit = new ItemUnit();
		itemUnit.setId(unit);
		item.setItemUnit(itemUnit);

		itemManger.modifyItem(item);

		resp.sendRedirect(req.getContextPath() + "/servlet/item/SearchItemServlet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
