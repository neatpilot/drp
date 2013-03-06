<%@ include file="/commonpage.jsp"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>用户维护</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/drp.css">
<script type="text/javascript">
	function addUser() {
		window.self.location = "${pageContext.request.contextPath}/servlet/SysmgrServlet?command=${applicationScope.showAdd}";
	}

	function modifyUser() {
		var selectFlags = document.getElementsByName("selectFlag");
		var count = 0;
		var index = 0;
		for ( var i = 0; i < selectFlags.length; i++) {
			if (selectFlags[i].checked) {
				index = i;
				count++;
			}
		}

		if (count == 0) {
			alert("请选择用户");
			return;
		} else if (count > 1) {
			alert("只能选择一个用户");
			return;
		}

		var userId = selectFlags[index].value;
		window.self.location = "${pageContext.request.contextPath}/servlet/SysmgrServlet?command=${applicationScope.showModify}&userId=" + userId;
	}

	function deleteUser() {
		// check if a user is selected or not
		var selectFlags = document.getElementsByName("selectFlag");
		var hasSelected = false;
		for ( var i = 0; i < selectFlags.length; i++) {
			if (selectFlags[i].checked) {
				hasSelected = true;
				break;
			}
		}

		if (!hasSelected) {
			alert("请选择要删除的用户");
			return;
		}

		if (window.confirm("确认删除？")) {
			document.getElementById("userForm").method = "post";
			document.getElementById("userForm").action = "${pageContext.request.contextPath}/servlet/SysmgrServlet?command=${applicationScope.delete}";
			document.getElementById("userForm").submit();
		}
	}

	function checkAll(field) {
		var selectFlags = document.getElementsByName("selectFlag");
		for ( var i = 0; i < selectFlags.length; i++) {
			selectFlags[i].checked = field.checked;
		}
	}

	function topPage() {
		window.self.location = "${pageContext.request.contextPath}/servlet/SysmgrServlet?pageNo=${userList.topPageNo}";
	}

	function previousPage() {
		window.self.location = "${pageContext.request.contextPath}/servlet/SysmgrServlet?pageNo=${userList.previousPageNo}";
	}

	function nextPage() {
		window.self.location = "${pageContext.request.contextPath}/servlet/SysmgrServlet?pageNo=${userList.nextPageNo}";
	}

	function bottomPage() {
		window.self.location = "${pageContext.request.contextPath}/servlet/SysmgrServlet?pageNo=${userList.bottomPageNo}";
	}
</script>
</head>

<body class="body1">
	<form name="userform" id="userform">
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0" height="35">
				<tr>
					<td class="p1" height="18" nowrap>&nbsp;</td>
				</tr>
				<tr>
					<td width="522" class="p1" height="17" nowrap><img
						src="${pageContext.request.contextPath}/images/mark_arrow_02.gif" width="14" height="14"> &nbsp; <b>系统管理&gt;&gt;用户维护</b></td>
				</tr>
			</table>
			<hr width="100%" align="center" size=0>
		</div>
		<table width="95%" height="20" border="0" align="center" cellspacing="0" class="rd1" id="toolbar">
			<tr>
				<td width="49%" class="rd19"><font color="#FFFFFF">查询列表</font></td>
				<td width="27%" nowrap class="rd16">
					<div align="right"></div>
				</td>
			</tr>
		</table>
		<table width="95%" border="1" cellspacing="0" cellpadding="0" align="center" class="table1">
			<tr>
				<td width="55" class="rd6"><input type="checkbox" name="ifAll" onClick="checkAll(this)"></td>
				<td width="119" class="rd6">用户代码</td>
				<td width="152" class="rd6">用户名称</td>
				<td width="166" class="rd6">联系电话</td>
				<td width="150" class="rd6">email</td>
				<td width="153" class="rd6">创建日期</td>
			</tr>
			<!-- JSTL 动态添加 -->
			<c:choose>
				<c:when test="${empty userList or empty userList.list or fn:length(userList.list) eq 0 }">
					<td colspan="6" class="rd6">没有符合条件的数据</td>
				</c:when>
				<c:otherwise>
					<c:forEach items="${userList.list }" var="list">
						<tr>
							<td class="rd8"><input type="checkbox" name="selectFlag" class="checkbox1" value="${list.userId }">
							</td>
							<td class="rd8">${list.userId }</td>
							<td class="rd8">${list.userName }</td>
							<td class="rd8">${list.contactTel }</td>
							<td class="rd8">${list.email }</td>
							<td class="rd8"><fmt:formatDate value="${list.createDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>

		</table>
		<table width="95%" height="30" border="0" align="center" cellpadding="0" cellspacing="0" class="rd1">
			<tr>
				<td nowrap class="rd19" height="2">
					<div align="left">
						<font color="#FFFFFF">&nbsp;共&nbsp${userList.totalPages }&nbsp页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font
							color="#FFFFFF">当前第</font>&nbsp <font color="#FF0000">${userList.pageNo }</font>&nbsp <font color="#FFFFFF">页</font>
					</div>
				</td>
				<td nowrap class="rd19">
					<div align="right">
						<input name="btnTopPage" class="button1" type="button" id="btnTopPage" value="|&lt;&lt; " title="首页"
							onClick="topPage()"> <input name="btnPreviousPage" class="button1" type="button" id="btnPreviousPage"
							value=" &lt;  " title="上页" onClick="previousPage()"> <input name="btnNextPage" class="button1"
							type="button" id="btnNextPage" value="  &gt; " title="下页" onClick="nextPage()"> <input
							name="btnBottomPage" class="button1" type="button" id="btnBottomPage" value=" &gt;&gt;|" title="尾页"
							onClick="bottomPage()"> <input name="btnAdd" type="button" class="button1" id="btnAdd" value="添加"
							onClick="addUser()"> <input name="btnDelete" class="button1" type="button" id="btnDelete" value="删除"
							onClick="deleteUser()"> <input name="btnModify" class="button1" type="button" id="btnModify" value="修改"
							onClick="modifyUser()">
					</div>
				</td>
			</tr>
		</table>
		<p>&nbsp;</p>
	</form>
</body>
</html>
