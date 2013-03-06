<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%@ include file="/commonpage.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">

<title>添加用户</title>

<link rel="stylesheet" href="${pageContext.request.contextPath }/style/drp.css">
<script src="${pageContext.request.contextPath }/script/client_validate.js"></script>
<script type="text/javascript">
	function goBack() {
		window.self.location = "${pageContext.request.contextPath }/sysmgr/user_maint.jsp";
	}

	/* 		
	用户代码要有限制，至少4个字符，第1个字符必须是字母，其它字符可以是字母和数字 
	用户名称必须输入
	用户输入的密码至少6位
	为了更好的保护用户口令，以“*”方式显示
	只有系统管理员才可以添加用户，系统管理员只能有1个，系统缺省为root */
	function addUser() {
		/* var userIdField = document.getElementById("UserId");
		//1.1 user id not null
		if(trim(userIdField.value)==""){
			alert("用户ID不能为空");
			userIdField.focus();
			return;
		}
		//1.2 user id at least 4 character
		if(trim(userIdField.value).length<4){
			alert("用户ID至少4个字符");
			userIdField.focus();
			return;
		}
		
		//1.3 1st character must be character (not number)
		if(!(trim(userIdField.value).chatAt(0)>='a' && userIdField.value).chatAt(0)<='z')){
			alert("用户ID第一个为小写字母");
			userIdField.focus();
			return;
		} */

		// 1 user ID (4~6 characters long, 1st must be character)
		var userIdField = document.getElementById("userId");
		var re_userId = new RegExp(/^[a-zA-Z]{1}[a-zA-Z0-9]{3,5}$/);
		if (!re_userId.test(trim(userIdField.value))) {
			alert("用户代码（4-6位）第一位为字母，后面为字母和数字");
			userIdField.focus();
			return;
		}

		// 2 user name needed
		if (trim(document.getElementById("userName").value).length == 0) {
			alert("用户姓名不能为空");
			document.getElementById("userName").focus();
			return;
		}

		// 3 password at least 6 long
		if (trim(document.getElementById("password").value).length < 6) {
			alert("用户密码至少6位");
			document.getElementById("password").focus();
			return;
		}

		// 4 tel must be numbers if not null
		if (trim(document.getElementById("contactTel").value).length >= 1) {
			var re_tel = new RegExp(/^[0-9]{1,}$/);
			if (!re_tel.test(trim(document.getElementById("contactTel").value))) {
				alert("电话号码应全为数字");
				document.getElementById("contactTel").focus();
				return;
			}
		}

		// 5 email 
		if (trim(document.getElementById("email").value).length >= 1) {
			var re_email = new RegExp(
					/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/);
			if (!re_email.test(trim(document.getElementById("email").value))) {
				alert("email格式不规范");
				document.getElementById("email").focus();
				return;
			}
		}
		// submit form
		document.getElementById("userForm").action = "${pageContext.request.contextPath }/servlet/SysmgrServlet";
		document.getElementById("userForm").method = "post";
		document.getElementById("userForm").submit();

	}

	// automatically focus on userID when first loading this page (onload)
	function init() {
		document.getElementById("userId").focus();
	}

	// ajax find userID is used or not
	function validate(field) {
		if (trim(field.value).length > 0) {
			var xmlHttp = null;

	
	if (window.XMLHttpRequest) {
				// ff,chrome
				xmlHttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				// ie
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			// guarantee every url is different(ajax has problems with cache request)
			var url = "${pageContext.request.contextPath}/servlet/SysmgrServlet?userId=" + trim(field.value) + "&command=validate"+"&time="
					+ new Date().getTime();
			xmlHttp.open("GET", url, true);

			//callback
			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState == 4) {
					if (xmlHttp.status == 200) {
						if (trim(xmlHttp.responseText) != "") {
							// user ID exists!
							document.getElementById("spanUserId").innerHTML = "<font color='red'>"
									+ xmlHttp.responseText + "</font>";
						} else {
							// user ID not exist!
							document.getElementById("spanUserId").innerHTML = "";
						}
					} else {
						alert("request error, error status = " + xmlHttp.status);
					}
				}
			};

			xmlHttp.send(null);
		} else {
			// not valid input, not need to check from database
			document.getElementById("spanUserId").innerHTML = "";
		}
	}
</script>
</head>

<body class="body1" onload="init()">

	<form name="userForm" target="_self" id="userForm">
		<!--flag for normal or add action  -->
		<input type="hidden" name="command" value="${applicationScope.add }">
		<div align="center">
			<table width="95%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="95%" border="0" cellspacing="0" cellpadding="0" height="25">
				<tr>
					<td width="522" class="p1" height="25" nowrap><img
						src="${pageContext.request.contextPath }/images/mark_arrow_03.gif" width="14" height="14"> &nbsp; <b>系统管理&gt;&gt;用户维护&gt;&gt;添加</b></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="22%" height="29">
						<div align="right">
							<font color="#FF0000">*</font>用户代码:&nbsp;
						</div>
					</td>
					<td width="78%"><input name="userId" type="text" class="text1" id="userId" size="10" maxlength="10" value=""
						onBlur="validate(this)"> <span id="spanUserId"></span></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>用户名称:&nbsp;
						</div>
					</td>
					<td><input name="userName" type="text" class="text1" id="userName" size="20" maxlength="20" value=""></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>密码:&nbsp;
						</div>
					</td>
					<td><label> <input name="password" type="password" class="text1" id="password" size="20"
							maxlength="20">
					</label></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">联系电话:&nbsp;</div>
					</td>
					<td><input name="contactTel" type="text" class="text1" id="contactTel" size="20" maxlength="20" value=""></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">email:&nbsp;</div>
					</td>
					<td><input name="email" type="text" class="text1" id="email" size="20" maxlength="20" value=""></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<div align="center">
				<input name="btnAdd" class="button1" type="button" id="btnAdd" value="添加" onClick="addUser()">
				&nbsp;&nbsp;&nbsp;&nbsp; <input name="btnBack" class="button1" type="button" id="btnBack" value="返回"
					onClick="goBack()" />
			</div>
		</div>
	</form>
</body>
</html>