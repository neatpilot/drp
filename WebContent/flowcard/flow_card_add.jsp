<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%@ include file="/commonpage.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>��������ά��</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/drp.css">
<link href="${pageContext.request.contextPath}/style/JSCalendar.css" rel=stylesheet type=text/css>
<script src="${pageContext.request.contextPath}/script/JSCalendar.js"></script>
<script src="${pageContext.request.contextPath}/script/client_validate.js"></script>
<script type="text/javascript">
	var rowIndex = 0;

	function selectAimClient(index) {
		window.open('${pageContext.request.contextPath}/flowcard/aim_client_select.jsp?index=' + index, '��ѡ���跽�ͻ�', 'width=700, height=400, scrollbars=no');
	}

	function selectItem(index) {
		window.open('${pageContext.request.contextPath}/flowcard/item_select.jsp?index=' + index, '��ѡ������', 'width=700, height=400, scrollbars=no');
	}

	function addOneLineOnClick() {
		var row=tblFlowCardDetail.insertRow(tblFlowCardDetail.rows.length);
		var col = row.insertCell(0);
		col.innerHTML = "<input type=\"hidden\" name=\"aimInnerId\" id=\"aimInnerId\"><input readonly=\"true\" maxLength=6 size=6 name=aimId><input type=button  value =...   name=btnSelectAimClient index=\""+ rowIndex +"\" onclick=\"selectAimClient(this.index)\">";
		col = row.insertCell(1);
		col.innerHTML = "<input id=aimName name=aimName size=25 maxlength=25 readonly=\"true\">";
		col = row.insertCell(2);
		col.innerHTML = "<input readonly=\"true\" maxLength=6 size=6 name=itemNo><input type=button  value =...   name=btnSelectItem index=\""+ rowIndex +"\" onclick=\"selectItem(this.index)\">";
		col = row.insertCell(3);
		col.innerHTML = "<input id=itemName name=itemName size=25 maxlength=25 readonly=\"true\">";		
		col = row.insertCell(4);
		col.innerHTML = "<input id=spec name=spec size=10 maxlength=10 readonly=\"true\">";
		col = row.insertCell(5);
		col.innerHTML = "<input id=pattern name=pattern size=10 maxlength=10 readonly=\"true\">";
		col = row.insertCell(6);
		col.innerHTML = "<input id=unit name=unit size=4 maxlength=4 readonly=\"true\">";
		col = row.insertCell(7);
		col.innerHTML = "<input id=qty name=qty size=6 maxlength=6>";
		col = row.insertCell(8);
		col.innerHTML = "<input type='button' value='ɾ��' id=btnDeleteLine name=btnDeleteLine onclick=\"return DeleteRow('row" + rowIndex + "')\">";
		row.setAttribute("id", "row" + rowIndex);
		rowIndex++;
	}

	function DeleteRow(rowTag) {
		var i = tblFlowCardDetail.rows(rowTag).rowIndex;
		var j;
		for (j = i; j <= rowIndex; j++) {
			tblFlowCardDetail.rows(j).cells(0).all("btnSelectAimClient").index--;
			tblFlowCardDetail.rows(j).cells(2).all("btnSelectItem").index--;
		}
		tblFlowCardDetail.deleteRow(i);
		rowIndex--;
	}

	function addFlowCard() {

	}

	function goBack() {
		window.self.location = "${pageContext.request.contextPath}/servlet/flowcard/FlowCardServlet";
	}
</script>
</head>

	<body class="body1">
		<div align="center">
			<form name="flowCardAddForm" method="post" action="${pageContext.request.contextPath}/servlet/flowcard/FlowCardServlet">
				<input type="hidden" name="command" value="${add }">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>�����̿�����&gt;&gt;����ά��&gt;&gt;����</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="15%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>���������̴���:&nbsp;
							</div>
						</td>
						<td width="16%">
							<input name="clientInnerId" type="hidden" id="clientInnerId">
							<input name="clientId" type="text" class="text1" id="clientId"
								size="10" maxlength="10" readonly="true">
							<input name="btnSelectClient" type="button" id="btnSelectClient"
								value="..." class="button1"
								onClick="window.open('${pageContext.request.contextPath}/flowcard/client_select.jsp', 'ѡ�������', 'width=700, height=400, scrollbars=no')">
						</td>
						<td width="16%">
							<div align="right">
								��������������:&nbsp;
							</div>
						</td>
						<td width="29%">
							<input name="clientName" type="text" class="text1"
								id="clientName" size="40" maxlength="40" readonly="true">
						</td>
						<td width="7%">
							&nbsp;
						</td>
						<td width="17%">
							<label></label>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0"
					name="tblFlowCardDetail" id="tblFlowCardDetail">
					<tr>
						<td nowrap>
							<div align="left">
								<font color="#FF0000">*</font>�跽�ͻ�����
							</div>
						</td>
						<td nowrap>
							<div align="left">
								�跽�ͻ�����
							</div>
						</td>
						<td nowrap>
							<div align="left">
								<font color="#FF0000">*</font>���ϴ���
							</div>
						</td>
						<td nowrap>
							<div align="left">
								��������
							</div>
						</td>
						<td nowrap>
							���
						</td>
						<td nowrap>
							�ͺ�
						</td>
						<td nowrap>
							������λ
						</td>
						<td nowrap>
							<font color="#FF0000">*</font>��������
						</td>
						<td nowrap>
							<div align="left">
								ɾ��
							</div>
						</td>
					</tr>
				</table>
				<p>
					<input name="btnAddLine" type="button" id="btnAddLine"
						onClick="addOneLineOnClick()" value="����һ��">
					<input name="btnSave" type="submit" id="btnSave" value="����">
					<input name="btnBack" type="button" id="btnBack" onClick="goBack()"
						value="����">
				</p>
				<p>
					&nbsp;
				</p>
				<p>
					&nbsp;
				</p>
			</form>
			<p>
				&nbsp;
			</p>
		</div>
	</body>
</html>