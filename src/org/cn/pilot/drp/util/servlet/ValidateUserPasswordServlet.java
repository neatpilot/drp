package org.cn.pilot.drp.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.sysmgr.domain.User;

public class ValidateUserPasswordServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=GB18030");
		String oldPassword = req.getParameter("oldPassword");
		User user = (User) req.getSession().getAttribute("user_info");
		if(!user.getPassword().equals(oldPassword)){
			resp.getWriter().print("password DONOT match");
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
