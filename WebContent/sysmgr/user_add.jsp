<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%@ include file="/commonpage.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">

<title>����û�</title>

<link rel="stylesheet" href="${pageContext.request.contextPath }/style/drp.css">
<script src="${pageContext.request.contextPath }/script/client_validate.js"></script>
<script type="text/javascript">
	function goBack() {
		window.self.location = "${pageContext.request.contextPath }/sysmgr/user_maint.jsp";
	}

	/* 		
	�û�����Ҫ�����ƣ�����4���ַ�����1���ַ���������ĸ�������ַ���������ĸ������ 
	�û����Ʊ�������
	�û��������������6λ
	Ϊ�˸��õı����û�����ԡ�*����ʽ��ʾ
	ֻ��ϵͳ����Ա�ſ�������û���ϵͳ����Աֻ����1����ϵͳȱʡΪroot */
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
						src="${pageContext.request.contextPath }/images/mark_arrow_03.gif" width="14" height="14"> &nbsp; <b>ϵͳ����&gt;&gt;�û�ά��&gt;&gt;���</b></td>
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
					<td width="78%"><input name="userId" type="text" class="text1" id="userId" size="10" maxlength="10" value=""
						onBlur="validate(this)"> <span id="spanUserId"></span></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>�û�����:&nbsp;
						</div>
					</td>
					<td><input name="userName" type="text" class="text1" id="userName" size="20" maxlength="20" value=""></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>����:&nbsp;
						</div>
					</td>
					<td><label> <input name="password" type="password" class="text1" id="password" size="20"
							maxlength="20">
					</label></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">��ϵ�绰:&nbsp;</div>
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
				<input name="btnAdd" class="button1" type="button" id="btnAdd" value="���" onClick="addUser()">
				&nbsp;&nbsp;&nbsp;&nbsp; <input name="btnBack" class="button1" type="button" id="btnBack" value="����"
					onClick="goBack()" />
			</div>
		</div>
	</form>
</body>
</html>