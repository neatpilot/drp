package org.cn.pilot.drp.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cn.pilot.drp.basedata.manager.ClientManager;

public class ClientIdValidateServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=GB18030");
		String clientId = req.getParameter("clientId");
		boolean flag = ClientManager.getInstance().findClientByClientId(clientId);
		if (flag) {
			resp.getWriter().write("分销商代码存在;client id exists");
		}
	}

}
