<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>
	<%
		Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (errorCode == 404) {
			response.sendRedirect(request.getContextPath() + "/404.jsp");
		} else if (errorCode == 500) {
			response.sendRedirect(request.getContextPath() + "/500.jsp");
		}
		// 补充字节，error.jsp大小最好大于1024字节
		// error.jsp should be more than 1025 Bytes	
		// 补充字节，error.jsp大小最好大于1024字节
		// error.jsp should be more than 1025 Bytes	
		// 补充字节，error.jsp大小最好大于1024字节
		// error.jsp should be more than 1025 Bytes	
		// 补充字节，error.jsp大小最好大于1024字节
		// error.jsp should be more than 1025 Bytes
		// 补充字节，error.jsp大小最好大于1024字节
		// error.jsp should be more than 1025 Bytes	
		// 补充字节，error.jsp大小最好大于1024字节
		// error.jsp should be more than 1025 Bytes
	%>
</body>
</html>