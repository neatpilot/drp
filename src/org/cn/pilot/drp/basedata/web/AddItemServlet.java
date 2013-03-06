package org.cn.pilot.drp.basedata.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.basedata.domain.Item;
import org.cn.pilot.drp.basedata.manager.ItemManager;
import org.cn.pilot.drp.basedata.manager.ItemManagerImpl;
import org.cn.pilot.drp.util.datadict.domain.ItemCategory;
import org.cn.pilot.drp.util.datadict.domain.ItemUnit;
import org.cn.pilot.drp.util.exception.ApplicationException;

public class AddItemServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// get form info
		String itemNo = req.getParameter("itemNo");
		String itemName = req.getParameter("itemName");
		String spec = req.getParameter("spec");
		String pattern = req.getParameter("pattern");
		String category = req.getParameter("category");
		String unit = req.getParameter("unit");

		// 构造Item对象
		Item item = new Item();
		item.setItemNo(itemNo);
		item.setItemName(itemName);
		item.setSpec(spec);
		item.setPattern(pattern);

		// 构造物料类别
		ItemCategory itemCategory = new ItemCategory();
		itemCategory.setId(category);
		item.setItemCategory(itemCategory);

		// 构造物料单位
		ItemUnit itemUnit = new ItemUnit();
		itemUnit.setId(unit);
		item.setItemUnit(itemUnit);

		// 添加
		ItemManager itemManager = new ItemManagerImpl();
		try {
			itemManager.addItem(item);
		} catch (ApplicationException e) {
			throw e;
		}

		resp.sendRedirect(req.getContextPath() + "/servlet/item/SearchItemServlet");
	}

}
