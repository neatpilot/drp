<%@	page import="org.cn.pilot.sysmgr.manager.UserManager"%>
<%@	page import="org.cn.pilot.sysmgr.domain.User"%>
<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<%
	request.setCharacterEncoding("GB18030");

	String userId = "";
	String userName = "";
	String contactTel = "";
	String email = "";
	// differentiate a request is by clicking add buttion or not
	if ("add".equals(request.getParameter("command"))) {
		if (UserManager.getInstance().findUserById(
				request.getParameter("userId")) == null) {
			User user = new User();
			user.setUserId(request.getParameter("userId"));
			user.setUserName(request.getParameter("userName"));
			user.setPassword(request.getParameter("password"));
			user.setContactTel(request.getParameter("contactTel"));
			user.setEmail(request.getParameter("email"));

			//add user into database
			UserManager.getInstance().addUser(user);
		} else {
			userId = request.getParameter("userId");
			userName = request.getParameter("userName");
			contactTel = request.getParameter("contactTel");
			email = request.getParameter("email");
			out.println("�û������Ѿ����ڣ�����=��"
					+ request.getParameter("userId") + "��");
		}

	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">

<title>�����û�</title>

<link rel="stylesheet" href="../style/drp.css">
<script src="../script/client_validate.js"></script>
<script type="text/javascript">
	function goBack() {
		window.self.location = "user_maint.html"
	}

	/* 		
	�û�����Ҫ�����ƣ�����4���ַ�����1���ַ���������ĸ�������ַ���������ĸ������ 
	�û����Ʊ�������
	�û��������������6λ
	Ϊ�˸��õı����û�����ԡ�*����ʽ��ʾ
	ֻ��ϵͳ����Ա�ſ��������û���ϵͳ����Աֻ����1����ϵͳȱʡΪroot */
	function addUser() {
		/* var userIdField = document.getElementById("UserId");
		//1.1 user id not null
		if(trim(userIdField.value)==""){
			alert("�û�ID����Ϊ��");
			userIdField.focus();
			return;
		}
		//1.2 user id at least 4 character
		if(trim(userIdField.value).length<4){
			alert("�û�ID����4���ַ�");
			userIdField.focus();
			return;
		}
		
		//1.3 1st character must be character (not number)
		if(!(trim(userIdField.value).chatAt(0)>='a' && userIdField.value).chatAt(0)<='z')){
			alert("�û�ID��һ��ΪСд��ĸ");
			userIdField.focus();
			return;
		} */

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
		// submit form
		document.getElementById("userForm").action = "user_add.jsp";
		document.getElementById("userForm").method = "post";
		document.getElementById("userForm").submit();

	}

	// automatically focus on userID when first loading this page (onload)
	function init() {
		document.getElementById("userId").focus();
	}
</script>

</head>

<body class="body1" onload="init()">

	<form name="userForm" target="_self" id="userForm">
		<!- flag for normal or add action ->
		<input type="hidden" name="command" value="add">
		<div align="center">
			<table width="95%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="25">
				<tr>
					<td width="522" class="p1" height="25" nowrap><img
						src="../images/mark_arrow_03.gif" width="14" height="14">
						&nbsp; <b>ϵͳ����&gt;&gt;�û�ά��&gt;&gt;����</b></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="22%" height="29">
						<div align="right">
							<font color="#FF0000">*</font>�û�����:&nbsp;
						</div>
					</td>
					<td width="78%"><input name="userId" type="text" class="text1"
						id="userId" size="10" maxlength="10" value="<%=userId %>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>�û�����:&nbsp;
						</div>
					</td>
					<td><input name="userName" type="text" class="text1"
						id="userName" size="20" maxlength="20" value="<%=userName %>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>����:&nbsp;
						</div>
					</td>
					<td><label> <input name="password" type="password"
							class="text1" id="password" size="20" maxlength="20">
					</label></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">��ϵ�绰:&nbsp;</div>
					</td>
					<td><input name="contactTel" type="text" class="text1"
						id="contactTel" size="20" maxlength="20" value="<%=contactTel %>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">email:&nbsp;</div>
					</td>
					<td><input name="email" type="text" class="text1" id="email"
						size="20" maxlength="20" value="<%=email %>"></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<div align="center">
				<input name="btnAdd" class="button1" type="button" id="btnAdd"
					value="����" onClick="addUser()"> &nbsp;&nbsp;&nbsp;&nbsp; <input
					name="btnBack" class="button1" type="button" id="btnBack"
					value="����" onClick="goBack()" />
			</div>
		</div>
	</form>
</body>
</html>