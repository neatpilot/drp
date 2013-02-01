<%@page import="org.cn.pilot.drp.util.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.cn.pilot.drp.util.datadict.manager.DataDictManager"%>
<%@page import="org.cn.pilot.drp.util.datadict.domain.*"%>
<%@page import="org.cn.pilot.drp.basedata.domain.Client"%>
<%@page import="org.cn.pilot.drp.basedata.manager.ClientManager"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	Client client = ClientManager.getInstance().findClientOrRegionById(id);
	
	if(Constants.MODIFY.equals(request.getParameter("command"))){
		client.setAddress(request.getParameter("address"));
		client.setBankAcctNo(request.getParameter("bankAcctNo"));
		ClientLevel cl = new ClientLevel();
		cl.setId(request.getParameter("clientLevel"));
		client.setClientLevel(cl);
		client.setContactTel(request.getParameter("contactTel"));
		client.setName(request.getParameter("clientName"));
		client.setZipCode(request.getParameter("zipCode"));
		
		ClientManager.getInstance().modifyClientOrRegion(client);
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>�޸ķ�����</title>
<link rel="stylesheet" href="../style/drp.css">
<script src="../script/client_validate.js"></script>
<script language="javascript">
	function validateForm(form) {
		if (trim(form.clientName.value) == "") {
			alert("���������Ʋ���Ϊ�գ�");
			return false;
		}
		return true;
	}
</script>
</head>

<body class="body1">
	<form name="clientForm" target="_self" id="clientForm" method="post" action="client_modify.jsp" onsubmit="validateForm(this)">
	<input name="id" type="hidden"  value="<%=id %>">
	<input name="command" type="hidden" value="<%=Constants.MODIFY%>">
		<div align="center">
			<table width="95%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="95%" border="0" cellspacing="0" cellpadding="0" height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap><img src="../images/mark_arrow_03.gif" width="14" height="14"> &nbsp; <b>�������ݹ���&gt;&gt;������ά��&gt;&gt;�޸ķ�����</b>
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="22%" height="29">
						<div align="right">�����̴���:&nbsp;</div>
					</td>
					<td width="78%"><input name="clientId" type="text" class="text1" id="clientId" size="10" maxlength="10" readonly="true"
						value="<%=client.getClientId()%>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>����������:&nbsp;
						</div>
					</td>
					<td><input name="clientName" type="text" class="text1" id="clientName" size="40" maxlength="40" value="<%=client.getName()%>"></td>
				</tr>
				<tr>
					<td height="15">
						<div align="right">
							<font color="#FF0000">*</font>����������:&nbsp;
						</div>
					</td>
					<td><select name="clientLevel" class="select1" id="clientLevel">
							<%
								List<ClientLevel> list = DataDictManager.getInstance().getClientLevelList();
								for (Iterator<ClientLevel> iter = list.iterator(); iter.hasNext();) {
									String selectedStr = "";
									ClientLevel oneInstance = iter.next();
									if (oneInstance.getId().equals(client.getClientLevel().getId())) {
										selectedStr = "selected";
									}
							%>
							<option <%=selectedStr%> value="<%=oneInstance.getId()%>">
								<%=oneInstance.getName()%>
							</option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">�����ʺ�:&nbsp;</div>
					</td>
					<td><input name="bankAcctNo" type="text" class="text1" id="bankAcctNo" size="10" maxlength="10" value="<%=client.getBankAcctNo()%>">
					</td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">��ϵ�绰:&nbsp;</div>
					</td>
					<td><input name="contactTel" type="text" class="text1" id="contactTel" size="10" maxlength="10" value="<%=client.getContactTel()%>">
					</td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">��ַ:&nbsp;</div>
					</td>
					<td><input name="address" type="text" class="text1" id="address" size="10" maxlength="10" value="<%=client.getAddress()%>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">�ʱ�:&nbsp;</div>
					</td>
					<td><input name="zipCode" type="text" class="text1" id="zipCode" size="10" maxlength="10" value="<%=client.getZipCode()%>"></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<div align="center">
				<input name="btnModify" class="button1" type="submit" id="btnModify" value="�޸�"> &nbsp;&nbsp;&nbsp;&nbsp; <input name="btnBack"
					class="button1" type="button" id="btnBack" value="����" onClick="self.location='client_modify.jsp?id=<%=id%>'" />
			</div>
		</div>
	</form>
</body>
</html>
