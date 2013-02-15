package org.cn.pilot.drp.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.cn.pilot.drp.basedata.manager.FiscalYearPeriodManager;

public class FiscalYearPeriodValidateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=GB18030");
		int fiscalYear = Integer.parseInt(req.getParameter("fiscalYear"));
		int fiscalPeriod = Integer.parseInt(req.getParameter("fiscalPeriod"));
		
		if(null!=FiscalYearPeriodManager.getInstance().findFiscalYearPeriod(fiscalYear, fiscalPeriod)){
			resp.getWriter().print("会计核算期存在");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
