<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.cn.pilot.drp.basedata.domain.Item"%>
<%@page import="org.cn.pilot.drp.util.PageModel"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String condition = request.getParameter("condition")==null?"":request.getParameter("condition");
	
	PageModel<Item> itemPageModel = (PageModel<Item>) request.getAttribute("itemPageModel");
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>����ά��</title>
<link rel="stylesheet" href="style/drp.css">
<script src="script/windows.js"></script>
<script type="text/javascript">
	function addItem() {
		window.self.location = "<%=basePath%>servlet/item/ShowAddItemServlet";
	}

	function modifyItem() {
		var selectFlagList = document.getElementsByName("selectFlag");
		document.get
		var count = 0;
		var index = 0;
		for(var i = 0 ; i<selectFlagList.length;i++){
			if(selectFlagList[i].checked){
				count ++;
				index = i;
			}
		}
		
		if (count == 0) {
			alert("PLEASE CHOOSE ONE ITEM��");
			return;
		}
		if (count > 1) {
			alert("ONLY ONE ITEM ALLOWED��");
			return;
		}
		
		window.self.location="<%=basePath%>servlet/item/ShowModifyItemServlet?itemNo="+selectFlagList[index].value;
	}

	function deleteItem() {
		var selectFlagList = document.getElementsByName("selectFlag");
		var count = 0;
		for(var i = 0 ; i<selectFlagList.length;i++){
			if(selectFlagList[i].checked){
				count ++;
			}
		}
		
		if (count == 0) {
			alert("PLEASE AT LEAST CHOOSE ONE ITEM��");
			return;
		}
		
		if(window.confirm("ȷ��ɾ��ѡ����")){
			document.getElementById("itemForm").action="<%=basePath%>servlet/item/DeleteItemServlet";
			document.getElementById("itemForm").method = "post";
			document.getElementById("itemForm").submit();
		}
	}

	function topPage() {
		window.self.location = "<%=basePath%>servlet/item/SearchItemServlet?pageNo=<%=itemPageModel.getTopPageNo()%>&condition=<%=condition%>";
	}

	function previousPage() {
		window.self.location = "<%=basePath%>servlet/item/SearchItemServlet?pageNo=<%=itemPageModel.getPreviousPageNo()%>&condition=<%=condition%>";
	}

	function nextPage() {
		window.self.location = "<%=basePath%>servlet/item/SearchItemServlet?pageNo=<%=itemPageModel.getNextPageNo()%>&condition=<%=condition%>";
	}

	function bottomPage() {
		window.self.location = "<%=basePath%>servlet/item/SearchItemServlet?pageNo=<%=itemPageModel.getBottomPageNo()%>&condition=<%=condition%>";
	}

	function checkAll() {

	}

	function uploadPic4Item() {
		window.self.location = "item_upload.html";
	}
	
	function searchItemByCondition(){
		document.getElementById("itemForm").action="<%=basePath%>servlet/item/SearchItemServlet";
		document.getElementById("itemForm").method = "post";
		document.getElementById("itemForm").submit();
	}
</script>
</head>

<body class="body1">
	<form name="itemForm" id="itemForm">
		<div align="center">
			<table width="95%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="95%" border="0" cellspacing="0" cellpadding="0" height="8">
				<tr>
					<td width="522" class="p1" height="2" nowrap><img src="images/mark_arrow_02.gif" alt="��" width="14" height="14"> &nbsp; <b>�������ݹ���&gt;&gt;����ά��</b>
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="17%" height="29">
						<div align="left">���ϴ���/����:</div>
					</td>
					<td width="57%"><input name="condition" type="text" class="text1" id="condition" size="50" maxlength="50" value="<%= condition %>"></td>
					<td width="26%">
						<div align="left">
							<input name="btnQuery" type="button" class="button1" id="btnQuery" value="��ѯ" onclick="searchItemByCondition()">
						</div>
					</td>
				</tr>
				<tr>
					<td height="16">
						<div align="right"></div>
					</td>
					<td>&nbsp;</td>
					<td>
						<div align="right"></div>
					</td>
				</tr>
			</table>

		</div>
		<table width="95%" border="0" cellspacing="0" cellpadding="0" class="rd1" align="center">
			<tr>
				<td nowrap height="10" class="p2">������Ϣ</td>
				<td nowrap height="10" class="p3">&nbsp;<font color="red"><%=request.getParameter("errorMessage") == null ? "" : new String(request.getParameter(
					"errorMessage").getBytes("ISO-8859-1"), "GB18030")%></font></td>
			</tr>
		</table>
		<table width="95%" border="1" cellspacing="0" cellpadding="0" align="center" class="table1">
			<tr>
				<td width="35" class="rd6"><input type="checkbox" name="ifAll" onClick="checkAll()"></td>
				<td width="155" class="rd6">���ϴ���</td>
				<td width="155" class="rd6">��������</td>
				<td width="155" class="rd6">���Ϲ��</td>
				<td width="155" class="rd6">�����ͺ�</td>
				<td width="138" class="rd6">���</td>
				<td width="101" class="rd6">������λ</td>
			</tr>
			
				<%
					List<Item> itemsList = itemPageModel.getList();
					for(Iterator<Item> iter = itemsList.iterator();iter.hasNext();){
						Item item = iter.next();
				%>
			<tr>
				<td class="rd8"><input type="checkbox" name="selectFlag" class="checkbox1" value="<%= item.getItemNo() %>"></td>
				<td class="rd8"><a href="#" onClick="window.open('item_detail.html', '������ϸ��Ϣ', 'width=400, height=400, scrollbars=no')"><%= item.getItemNo() %></a></td>
				<td class="rd8"><%= item.getItemName() %></td>
				<td class="rd8"><%= item.getSpec() %></td>
				<td class="rd8"><%= item.getPattern() %></td>
				<td class="rd8"><%= item.getItemCategory().getName() %></td>
				<td class="rd8"><%= item.getItemUnit().getName() %></td>
			</tr>
				<%
					}
				%>

		</table>
		<table width="95%" height="30" border="0" align="center" cellpadding="0" cellspacing="0" class="rd1">
			<tr>
				<td nowrap class="rd19" height="2" width="36%">
					<div align="left">
						<font color="#FFFFFF">&nbsp;��<%=itemPageModel.getTotalPages() %>ҳ</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color="#FFFFFF">��ǰ��</font>&nbsp <font color="#FF0000"><%=itemPageModel.getPageNo() %></font>&nbsp
						<font color="#FFFFFF">ҳ</font>
					</div>
				</td>
				<td nowrap class="rd19" width="64%">
					<div align="right">
						<input name="btnTopPage" class="button1" type="button" id="btnTopPage" value="|&lt;&lt; " title="��ҳ" onClick="topPage()"> <input
							name="btnPreviousPage" class="button1" type="button" id="btnPreviousPage" value=" &lt;  " title="��ҳ" onClick="previousPage()"> <input
							name="btnNextPage" class="button1" type="button" id="btnNextPage" value="  &gt; " title="��ҳ" onClick="nextPage()"> <input
							name="btnBottomPage" class="button1" type="button" id="btnBottomPage" value=" &gt;&gt;|" title="βҳ" onClick="bottomPage()"> <input
							name="btnAdd" type="button" class="button1" id="btnAdd" value="���" onClick="addItem()"> <input name="btnDelete" class="button1"
							type="button" id="btnDelete" value="ɾ��" onClick="deleteItem()"> <input name="btnModify" class="button1" type="button" id="btnModify"
							value="�޸�" onClick="modifyItem()"> <input name="btnUpload" class="button1" type="button" id="btnUpload" value="�ϴ�ͼƬ"
							onClick="uploadPic4Item()">
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
