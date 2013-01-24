<%@ page import="org.cn.pilot.drp.sysmgr.manager.UserManager"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%
	//test
	String userId = request.getParameter("userId");
	if (UserManager.getInstance().findUserById(userId) != null) {
		out.print("user id exist!!!");
	};
%>