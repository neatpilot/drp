<%@page import="org.cn.pilot.drp.util.Constants"%>
<%@page import="org.cn.pilot.drp.basedata.manager.ClientManager"%>
<%@page import="org.cn.pilot.drp.basedata.domain.Client"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	Client region = ClientManager.getInstance().findClientOrRegionById(
			id);

	if (Constants.DELETE.equals(request.getParameter("command"))) {
		ClientManager.getInstance().deleteclientOrRegion(id);
%>
<script type="text/javascript">
			alert("删除成功");
			window.parent.clientTreeFrame.location.reload();
</script>
<%
	}
%>
<html>
<head>
<link rel="stylesheet" href="../style/drp.css" />
<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
<title>分销商维护</title>
<script type="text/javascript">
	function addRegion() {
		window.self.location = "client_node_add.jsp?pid=<%=id%>";
	}

	function modifyRegion() {
		window.self.location = "client_node_modify.jsp?id=<%=id%>";
	}

	function deleteRegion() {
		if(window.confirm("delete it?")){
			document.getElementById("clientForm").method = "POST";
			document.getElementById("clientForm").action = "client_node_crud.jsp";
			document.getElementById("clientForm").submit();
		}
	}

	function addClient() {
		window.self.location = "client_add.jsp?pid=<%=id%>";

	}
</script>
</head>

<body class="body1">
	<form id="clientForm" name="clientForm" method="post" action="">
		<input name="command" type="hidden" value="<%=Constants.DELETE%>"> 
		<input name="id" type="hidden" value="<%=id%>">
		<table width="95%" border="0" cellspacing="0" cellpadding="0" height="8">
			<tr>
				<td width="522" class="p1" height="2" nowrap="nowrap"><img src="../images/mark_arrow_02.gif" width="14" height="14" /> &nbsp; <b>基础数据管理&gt;&gt;分销商维护</b>
				</td>
			</tr>
		</table>
		<hr width="97%" align="center" size="0" />
		<p></p>
		<p></p>
		<table width="95%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="213">
					<div align="right">当前区域名称：</div>
				</td>
				<td width="410"><label> <input name="name" type="text" class="text1" id="name" " readonly="true" value="<%=region.getName()%>" />
				</label></td>
			</tr>
		</table>
		<p></p>
		<label> <br />
		</label>
		<hr />
		<p align="center">
			<input name="btnAddRegion" type="button" class="button1" id="btnAddRegion" onClick="addRegion()" value="添加区域" /> &nbsp; <input
				name="btnDeleteRegion" type="button" class="button1" id="btnDeleteRegion" value="删除区域" onClick="deleteRegion()" /> &nbsp; <input
				name="btnModifyRegion" type="button" class="button1" id="btnModifyRegion" onClick="modifyRegion()" value="修改区域" /> &nbsp; <input
				name="btnAddClient" type="button" class="button1" id="btnAddClient" onClick="addClient()" value="添加分销商" />
		</p>
	</form>
</body>
</html>
