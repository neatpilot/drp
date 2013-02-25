package org.cn.pilot.drp.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RoleAuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
//		System.out.println("uri = " + req.getRequestURI());
//		System.out.println("url = " + req.getRequestURL().toString());
//		System.out.println("ServletPath = "+req.getServletPath());
//		System.out.println("ContextPath = "+req.getContextPath());
		
		///要的是 /xxx.jsp这样。不能有http，工程路径什么的。
		String requestURI = req.getRequestURI().substring(req.getRequestURI().indexOf("/", 1), req.getRequestURI().length());
		//System.out.println("requestURI=" + requestURI);
		if (!"/login.jsp".equals(requestURI) && !"/servlet/AuthImageServlet".equals(requestURI)) {
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("user_info") == null) {
				res.sendRedirect(req.getContextPath() + "/login.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
