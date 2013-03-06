<%@ include file="/commonpage.jsp"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>修改密码</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/drp.css">
<script src="${pageContext.request.contextPath}/script/client_validate.js"></script>
<script type="text/javascript">
	function modifyPasword() {
		if (document.getElementById("oldPassword").value.length < 6) {
			alert("原码不能小于6位字符！");
			document.getElementById("oldPassword").focus();
			return;
		}
		if (document.getElementById("spanOldPasswordCheckMsg").innerHTML != "") {
			alert("输入的密码与原密码不相同！");
			document.getElementById("oldPassword").focus();
			return;
		}
		if (document.getElementById("newPassword").value.length < 6) {
			alert("新密码不能小于6位字符！");
			document.getElementById("newPassword").focus();
			return;
		}
		if (document.getElementById("affirmNewPassowrd").value.length < 6) {
			alert("确认新密码不能小于6位字符！");
			document.getElementById("affirmNewPassowrd").focus();
			return;
		}
		if (document.getElementById("newPassword").value != document.getElementById("affirmNewPassowrd").value) {
			alert("新密码和确认新密码必须相同！");
			document.getElementById("affirmNewPassowrd").focus();
			return;
		}
		document.getElementById("userForm").method = "post";
		document.getElementById("userForm").action = "${pageContext.request.contextPath}/servlet/SysmgrServlet?command=modifyPassword";
		document.getElementById("userForm").submit();
	}

	function validateOldPassword() {
		var oldPassword = trim(document.getElementById("oldPassword").value);
		if (oldPassword.length > 0) {
			var xmlHttp = null;
			if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}

			var url = "${pageContext.request.contextPath}/servlet/SysmgrServlet?command=validatePassword&oldPassword="
					+ oldPassword + "&time=" + new Date().getTime();
			xmlHttp.open("GET", url, true);

			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState == 4) {
					if (xmlHttp.status == 200) {
						if (trim(xmlHttp.responseText) != "") {
							document.getElementById("spanOldPasswordCheckMsg").innerHTML = "<font color='red'>"
									+ xmlHttp.responseText + "</font>";
						} else {
							document.getElementById("spanOldPasswordCheckMsg").innerHTML = "";
						}
					} else {
						alert("request error, error status = " + xmlHttp.status);
					}
				}
			};
			xmlHttp.send(null);

		} else {
			document.getElementById("spanOldPasswordCheckMsg").innerHTML = "";
		}

	}
</script>
</head>

<body class="body1">
	<form name="userForm" id="userForm">
		<div align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" height="51">
				<tr>
					<td class="p1" height="16" nowrap>&nbsp;</td>
				</tr>
				<tr>
					<td class="p1" height="35" nowrap>&nbsp&nbsp <img
						src="${pageContext.request.contextPath}/images/mark_arrow_02.gif" width="14" height="14"> <b><strong>系统管理&gt;&gt;</strong>修改密码</b>
					</td>
				</tr>
			</table>
			<hr width="100%" align="center" size=0>
			<table width="50%" height="91" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30">
						<div align="right">
							<font color="#FF0000">*</font>原密码:
						</div>
					</td>
					<td><input name="oldPassword" type="password" class="text1" id="oldPassword" size="25"
						onblur="validateOldPassword()"><span id="spanOldPasswordCheckMsg"></td>
				</tr>
				<tr>
					<td height="27">
						<div align="right">
							<font color="#FF0000">*</font>新密码:
						</div>
					</td>
					<td><input name="newPassword" type="password" class="text1" id="newPassword" size="25"></td>
				</tr>
				<tr>
					<td height="34">
						<div align="right">
							<font color="#FF0000">*</font>确认密码:
						</div>
					</td>
					<td><input name="affirmNewPassowrd" type="password" class="text1" id="affirmNewPassowrd" size="25"></td>
				</tr>
			</table>
			<hr width="100%" align="center" size=0>
			<p>
				<input name="btnModify" class="button1" type="button" id="btnModify" value="修改" onClick="modifyPasword()">
				&nbsp; &nbsp;&nbsp;
			</p>
		</div>
	</form>
</body>
</html>
