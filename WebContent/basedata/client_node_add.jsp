<%@page import="org.cn.pilot.drp.basedata.manager.ClientManager"%>
<%@page import="org.cn.pilot.drp.basedata.domain.Client"%>
<%@page import="org.cn.pilot.drp.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%
	String pid = request.getParameter("pid");

	if(Constants.ADD.equals(request.getParameter("command"))){
		Client region = new Client();
		region.setPid(Integer.parseInt(pid));
		region.setName(request.getParameter("name"));
		region.setIsClient(Constants.NO_FLAG);
		region.setIsLeaf(Constants.YES_FLAG);
		ClientManager.getInstance().addClientOrRegion(region);
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" href="../style/drp.css" />
		<script src="../script/client_validate.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
		<title>�������ڵ�</title>
		<script type="text/javascript">
			function validateForm(form){
				if (trim(form.name.value).length == 0) {
					alert("�������Ʋ���Ϊ�գ�");
					form.name.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>

	<body class="body1">
		<form name="regionForm" method="post" action="client_node_add.jsp" onsubmit="validateForm(this)">
		<input name="command" type="hidden" value="<%=Constants.ADD %>" />
		<input name="pid" type="hidden" value="<%=pid %>" />
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap="nowrap">
						<img src="../images/mark_arrow_03.gif" width="14" height="14" />
						&nbsp;
						<b>�������ݹ���&gt;&gt;������ά��&gt;&gt;�������ڵ�</b>
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size="0" />
			<p></p>
			<p></p>
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="213">
						<div align="right">
							<font color="#FF0000">*</font>�������ƣ�
						</div>
					</td>
					<td width="410">
						<label>
							<input name="name" type="text" class="text1" id="name" />
						</label>
					</td>
				</tr>
			</table>
			<p></p>
			<label>
				<br />
			</label>
			<hr />
			<p align="center">
				<input name="btnAdd" class="button1" type="submit" value="���" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnBack" class="button1" type="button" value="����"
					onclick="window.self.location='client_node_crud.jsp?id=<%=pid %>'" />
			</p>
		</form>
	</body>
</html>
