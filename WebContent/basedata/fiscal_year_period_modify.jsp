<%@page import="org.cn.pilot.drp.basedata.manager.FiscalYearPeriodManager"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.cn.pilot.drp.basedata.domain.FiscalYearPeriod"%>
<%@ page language="java" contentType="text/html; charset=GB18030" pageEncoding="GB18030"%>
<%
	String command = request.getParameter("command");
	int id = Integer.parseInt(request.getParameter("id"));
	FiscalYearPeriod fiscalYearPeriod = null;
	if (command != null && command.equals("modify")) {
		fiscalYearPeriod = new FiscalYearPeriod();
		fiscalYearPeriod.setId(id);
		fiscalYearPeriod.setBeginDate(new SimpleDateFormat("yyyy-MM-dd").parse(request
				.getParameter("beginDate")));
		fiscalYearPeriod.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endDate")));
		fiscalYearPeriod.setPeriodSts(request.getParameter("periodSts") == null ? "N" : "Y");
		FiscalYearPeriodManager.getInstance().modifyFiscalYearPeriod(fiscalYearPeriod);
		out.println("提示：修改核算期间成功！");
	} else {
		fiscalYearPeriod = FiscalYearPeriodManager.getInstance().findFiscalYearPeriodById(id);
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>修改会计核算期间</title>
<link rel="stylesheet" href="../style/drp.css">
<link href="../style/JSCalendar.css" rel=stylesheet type=text/css>
<script src="../script/JSCalendar.js"></script>
<script src="../script/client_validate.js"></script>
<script type="text/javascript">
function modifyFiscalYearPeriod(){
	if (trim(document.getElementById("beginDate").value) > trim(document.getElementById("endDate").value)) {
		alert("核算日期必须小于等于结束日期！");
		document.getElementById("beginDate").focus();
		return;
	}
	document.getElementById("fiscalYearPeriodForm").method = "post";
	document.getElementById("fiscalYearPeriodForm").action = "fiscal_year_period_modify.jsp?command=modify&id=<%=id%>";
	document.getElementById("fiscalYearPeriodForm").submit();
	}
</script>
</head>

<body class="body1">
	<form name="fiscalYearPeriodForm" id="fiscalYearPeriodForm">
		<div align="center">
			<table width="95%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table width="95%" border="0" cellspacing="0" cellpadding="0" height="25">
				<tr>
					<td width="522" class="p1" height="25" nowrap><img src="../images/mark_arrow_03.gif" width="14" height="14">
						&nbsp; <b>基础数据管理&gt;&gt;会计核算期间维护&gt;&gt;修改</b></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="22%" height="29">
						<div align="right">核算年:&nbsp;</div>
					</td>
					<td width="78%"><input name="fiscalYear" type="text" class="text1" id="fiscalYear" size="10" maxlength="10"
						readonly="true" value="<%=fiscalYearPeriod.getFiscalYear()%>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">核算月:&nbsp;</div>
					</td>
					<td><input name="fiscalPeriod" type="text" class="text1" id="fiscalPeriod" size="10" maxlength="10"
						readonly="true" value="<%=fiscalYearPeriod.getFiscalPeriod()%>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>开始日期:&nbsp;
						</div>
					</td>
					<td><label> <input type="text" name="beginDate" size="10" maxlength="10" readonly="true"
							onClick=JSCalendar(this) value="<%=new SimpleDateFormat("yyyy-MM-dd").format(fiscalYearPeriod.getBeginDate())%>">
					</label></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>结束日期:&nbsp;
						</div>
					</td>
					<td><input name="endDate" type="text" id="endDate" onClick=JSCalendar(this) size="10" maxlength="10"
						readonly="true" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(fiscalYearPeriod.getEndDate())%>"></td>
				</tr>
				<tr>
					<td height="26">
						<div align="right">
							<font color="#FF0000">*</font>是否可用:&nbsp;
						</div>
					</td>
					<td><input name="periodSts" type="checkbox" class="checkbox1"
						<%=fiscalYearPeriod.getPeriodSts().equals("N") ? "" : "checked"%> id="periodSts" value="checkbox"></td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<div align="center">
				<input name="btnModify" class="button1" type="button" id="btnModify" value="修改" onclick="modifyFiscalYearPeriod()"> &nbsp;&nbsp;&nbsp;&nbsp; <input
					name="btnBack" class="button1" type="button" id="btnBack" value="返回" onClick="history.go(-1)">
			</div>
		</div>
	</form>
</body>
</html>
