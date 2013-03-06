<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%@ include file="/commonpage.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>�޸��û�</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/style/drp.css">
<script src="${pageContext.request.contextPath }/script/client_validate.js"></script>
<script type="text/javascript">
	function goBack() {
		window.self.location = "${pageContext.request.contextPath }/sysmgr/user_maint.jsp";
	}

	function modifyUser() {
		// 1 user ID (4~6 characters long, 1st must be character)
		var userIdField = document.getElementById("userId");
		var re_userId = new RegExp(/^[a-zA-Z]{1}[a-zA-Z0-9]{3,5}$/);
		if (!re_userId.test(trim(userIdField.value))) {
			alert("�û����루4-6λ����һλΪ��ĸ������Ϊ��ĸ������");
			userIdField.focus();
			return;
		}

		// 2 user name needed
		if (trim(document.getElementById("userName").value).length == 0) {
			alert("�û���������Ϊ��");
			document.getElementById("userName").focus();
			return;
		}

		// 3 password at least 6 long
		if (trim(document.getElementById("password").value).length < 6) {
			alert("�û���������6λ");
			document.getElementById("password").focus();
			return;
		}

		// 4 tel must be numbers if not null
		if (trim(document.getElementById("contactTel").value).length >= 1) {
			var re_tel = new RegExp(/^[0-9]{1,}$/);
			if (!re_tel.test(trim(document.getElementById("contactTel").value))) {
				alert("�绰����ӦȫΪ����");
				document.getElementById("contactTel").focus();
				return;
			}
		}

		// 5 email 
		if (trim(document.getElementById("email").value).length >= 1) {
			var re_email = new RegExp(
					/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/);
			if (!re_email.test(trim(document.getElementById("email").value))) {
				alert("email��ʽ���淶");
				document.getElementById("email").focus();
				return;
			}
		}

		document.getElementById("userForm").method = "post";
		document.getElementById("userForm").action = "${pageContext.request.contextPath }/servlet/SysmgrServlet?command=${applicationScope.modify}";
		document.getElementById("userForm").submit();
	}
</script>

</head>

<body class="body1">
	<form name="userForm" id="userForm">
		<div align="center">
			<table width="95%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="95%" border="0" cellspacing="0" cellpadding="0" height="25">
				<tr>
					<td width="522" class="p1" height="25" nowrap><img
						src="${pageContext.request.contextPath }/images/mark_arrow_03.gif" width="14" height="14"> &nbsp; <b>ϵͳ����&gt;&gt;�û�ά��&gt;&gt;�޸�</b>
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="22%" height="29">
						<div align="right">�û�����:&nbsp;</div>
					</td>
					<td width="78%"><input name="userId" type="text" class="text1" id="userId" size="10" maxlength="10"
						readonly="true" value="${user.userId }"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>�û�����:&nbsp;
						</div>
					</td>
					<td><input name="userName" type="text" class="text1" id="userName" size="20" maxlength="20"
						value="${user.userName }"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>����:&nbsp;
						</div>
					</td>
					<td><label> <input name="password" type="password" class="text1" id="password" size="20"
							maxlength="20" value="${user.password }">
					</label></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">��ϵ�绰:&nbsp;</div>
					</td>
					<td><input name="contactTel" type="text" class="text1" id="contactTel" size="20" maxlength="20"
						value="${user.contactTel }"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">email:&nbsp;</div>
					</td>
					<td><input name="email" type="text" class="text1" id="email" size="20" maxlength="20" value="${user.email }">
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<div align="center">
				<input name="btnModify" class="button1" type="button" id="btnModify" value="�޸�" onClick="modifyUser()">
				&nbsp;&nbsp;&nbsp;&nbsp; <input name="btnBack" class="button1" type="button" id="btnBack" value="����"
					onClick="goBack()" />
			</div>
		</div>
	</form>
</body>
</html>