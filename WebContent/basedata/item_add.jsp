<%@page import="org.cn.pilot.drp.util.datadict.domain.ItemUnit"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.cn.pilot.drp.util.datadict.manager.DataDictManager"%>
<%@page import="java.util.List"%>
<%@page import="org.cn.pilot.drp.util.datadict.domain.ItemCategory"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<base href="<%=basePath%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>�������</title>
<link rel="stylesheet" href="style/drp.css">
<script src="script/client_validate.js"></script>
<script type="text/javascript">
	function validateForm(){
		var message = "";
		if (trim(document.getElementById("itemNo").value) == "") {
			message = "���ϴ���Ϊ�գ����޸ģ�";
		}
		if (trim(document.getElementById("itemName").value) == "") {
			message += "\n��������Ϊ�գ����޸ģ�";
		}
		if (message != "") {
			alert(message);
			return false;
		}
		return true;	
	}
</script>
</head>

<body class="body1">
	<form name="itemForm" id="itemForm" action="servlet/item/AddItemServlet" method="post" submit="validateForm()">
		<div align="center">
			<table width="95%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="95%" border="0" cellspacing="0" cellpadding="0" height="25">
				<tr>
					<td width="522" class="p1" height="25" nowrap><img src="images/mark_arrow_03.gif" width="14" height="14"> &nbsp; <b>�������ݹ���&gt;&gt;����ά��&gt;&gt;���</b>
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="22%" height="29">
						<div align="right">
							<font color="#FF0000">*</font>���ϴ���:&nbsp;
						</div>
					</td>
					<td width="78%"><input name="itemNo" type="text" class="text1" id="itemNo" size="10" maxlength="10"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>��������:&nbsp;
						</div>
					</td>
					<td><input name="itemName" type="text" class="text1" id="itemName" size="20" maxlength="20"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">���Ϲ��:&nbsp;</div>
					</td>
					<td><label> <input name="spec" type="text" class="text1" id="spec" size="20" maxlength="20">
					</label></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">�����ͺ�:&nbsp;</div>
					</td>
					<td><input name="pattern" type="text" class="text1" id="pattern" size="20" maxlength="20"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>���:&nbsp;
						</div>
					</td>
					<td><select name="category" class="select1" id="category">
							<%
								List<ItemCategory> itemCategoryList = DataDictManager.getInstance().getItemCategoryList();
								for (Iterator<ItemCategory> iter = itemCategoryList.iterator(); iter.hasNext();) {
									ItemCategory itemCategory = iter.next();
							%>
							<option value="<%=itemCategory.getId()%>"><%=itemCategory.getName()%></option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>������λ:&nbsp;
						</div>
					</td>
					<td><select name="unit" class="select1" id="unit">
							<%
								List<ItemUnit> itemUnitList = DataDictManager.getInstance().getItemUnitList();
								for (Iterator<ItemUnit> iter = itemUnitList.iterator(); iter.hasNext();) {
									ItemUnit itemUnit = iter.next();
							%>
							<option value="<%=itemUnit.getId()%>"><%=itemUnit.getName()%></option>
							<%
								}
							%>
					</select></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<div align="center">
				<input name="btnAdd" class="button1" type="submit" id="btnAdd" value="���"> &nbsp;&nbsp;&nbsp;&nbsp; 
				<input name="btnBack" class="button1" type="button" id="btnBack" value="����" onClick="history.go(-1)">
			</div>
		</div>
	</form>
</body>
</html>
