<%@page import="org.cn.pilot.drp.util.Constants"%>
<%@page import="org.cn.pilot.drp.basedata.domain.Client"%>
<%@page import="org.cn.pilot.drp.basedata.manager.ClientManager"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	Client region = ClientManager.getInstance().findClientOrRegionById(id);
	if(Constants.MODIFY.equals(request.getParameter("command"))){
		region.setName(request.getParameter("name"));
		ClientManager.getInstance().modifyClientOrRegion(region);
	}
%>
<html>
<head>
<link rel="stylesheet" href="../style/drp.css" />
<script src="../script/client_validate.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
<title>�޸�����ڵ�</title>
<script type="text/javascript">
	function modifyRegion() {
		if (trim(document.getElementById("name").value) == "") {
			alert("�������Ʋ���Ϊ�գ�");
			document.getElementById("name").focus();
			return;
		}
		
// 		document.getElementById("regionForm").method = "post";
// 		document.getElementById("regionForm").action = "client_node_modify.jsp";
// 		document.getElementById("regionForm").submit();
		document.forms[0].method = "post";
		document.forms[0].action = "client_node_modify.jsp";
		document.forms[0].submit();
	}
	
	function goBack() {
		window.self.location = "client_node_crud.jsp?id=<%=id%>";
	}
	
	function init(){
		document.getElementById("name").focus();
	}
	
</script>
</head>

<body class="body1" onload="init()">
	<form id="regionForm" name="regionForm" method="post" action="">
		<input name="command" type="hidden" value="<%=Constants.MODIFY%>"> 
		<input name="id" type="hidden" value="<%=id%>">
		<table width="95%" border="0" cellspacing="0" cellpadding="0" height="8">
			<tr>
				<td width="522" class="p1" height="2" nowrap="nowrap"><img src="../images/mark_arrow_03.gif" width="14" height="14" /> &nbsp; <b>�������ݹ���&gt;&gt;������ά��&gt;&gt;�޸�����ڵ�</b>
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
				<td width="410"><label> <input name="name" type="text" class="text1" id="name" value="<%=region.getName()%>" />
				</label></td>
			</tr>
		</table>
		<p></p>
		<label> <br />
		</label>
		<hr />
		<p align="center">
			<input name="btnModify" class="button1" type="button" id="btnModify" value="�޸�" onClick="modifyRegion()" /> &nbsp;&nbsp;&nbsp;&nbsp; <input
				name="btnModify" class="button1" type="button" id="btnModify" value="����" onclick="goBack()" />
		</p>
	</form>
</body>
</html>
