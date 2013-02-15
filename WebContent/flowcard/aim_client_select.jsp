<%@page import="org.cn.pilot.drp.basedata.manager.ClientManager"%>
<%@page import="org.cn.pilot.drp.util.PageModel"%>
<%@page import="org.cn.pilot.drp.basedata.domain.AimClient"%>
<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ page import="java.util.*" %>    
<%
	int index = Integer.parseInt(request.getParameter("index"));
	
	String queryStr = request.getParameter("aimclientIdOrName");
	if (queryStr == null){
		queryStr= "";
	}
 
	int pageNo = 1;
	int pageSize = 10;
	String strPageNo = request.getParameter("pageNo");
	if (strPageNo != null && !strPageNo.equals("")) {
		pageNo = Integer.parseInt(strPageNo);
	}
	PageModel pageModel = ClientManager.getInstance().findAllAimClient(pageNo, pageSize, queryStr);
%>            
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>��ѡ���跽�ͻ�</title>
<link rel="stylesheet" href="../style/drp.css">
<script src="../script/client_validate.js"></script>
<script type="text/javascript">
	function topPage() {
		window.self.location = "aim_client_select.jsp?index=<%=index%>&pageNo=<%=pageModel.getTopPageNo()%>";
	}
	
	function previousPage() {
		window.self.location = "aim_client_select.jsp?index=<%=index%>&pageNo=<%=pageModel.getPreviousPageNo()%>";
	}
	
	function nextPage() {
		window.self.location = "aim_client_select.jsp?index=<%=index%>&pageNo=<%=pageModel.getNextPageNo()%>";
	}
	
	function bottomPage() {
		window.self.location = "aim_client_select.jsp?index=<%=index%>&pageNo=<%=pageModel.getBottomPageNo()%>";
	}
	
	function queryClient() {
		with(document.getElementById("clientForm")) {
			method = "post";
			action = "aim_client_select.jsp?index=<%=index%>";
			submit();
		}
	}
	
	<%--
	function doubleclick(aimInnerId, aimId, aimName) {
		var rowLength = window.opener.document.all.tblFlowCardDetail.rows.length
		//alert("rowLength=" + rowLength);
		if (rowLength == 2) {
			window.opener.document.all.aimInnerId.value = aimInnerId;
			window.opener.document.all.aimId.value = aimId;
			window.opener.document.all.aimName.value = aimName;
		} else {
			window.opener.document.all.aimInnerId[<%=index%>].value = aimInnerId;
			window.opener.document.all.aimId[<%=index%>].value = aimId;
			window.opener.document.all.aimName[<%=index%>].value = aimName;
		}
		window.close();
	}
	--%>

	function doubleclick(aimInnerId, aimId, aimName) {
		window.opener.document.getElementsByName("aimInnerId")[<%=index%>].value = aimInnerId;
		window.opener.document.getElementsByName("aimId")[<%=index%>].value = aimId;
		window.opener.document.getElementsByName("aimName")[<%=index%>].value = aimName;
		window.close();
	}
	
	function selectOk(clientId, clientName) {
		////
		////
		window.close();
	}
	
</script>	
</head>

<body  class="body1">
<form name="clientForm" >
  <div align="center"> 
    <table width="95%" border="0" cellspacing="0" cellpadding="0" height="34">
      <tr> 
        <td width="522" class="p1" height="34" nowrap><img src="../images/search.gif" width="32" height="32">&nbsp;<b>��ѡ�������</b></td>
      </tr>
    </table>
    <hr width="100%" align="center" size=0>
    <table width="95%" border="0" cellpadding="0" cellspacing="0">
      <tr> 
        <td width="17%" height="29"> <div align="left">�跽�ͻ�����/����:</div></td>
        <td width="57%"><input name="aimclientIdOrName" type="text"  class="text1" id="aimclientIdOrName" value="<%=queryStr %>" size="50" maxlength="50"> 
        </td>
        <td width="26%"> <div align="left"> 
            <input name="btnQuery" type="button" class="button1" id="btnQuery"  value="��ѯ" onclick="queryClient()">
          </div></td>
      </tr>
      <tr> 
        <td height="16"> 
          <div align="right"></div></td>
        <td>&nbsp; </td>
        <td><div align="right"></div></td>
      </tr>
    </table>
    
  </div>
  <table width="95%" border="0" cellspacing="0" cellpadding="0"  class="rd1" align="center">
    <tr> 
      <td nowrap height="10" class="p2">�跽�ͻ���Ϣ</td>
      <td nowrap  height="10" class="p3">&nbsp;</td>
    </tr>
  </table>
  <table width="95%" border="1" cellspacing="0" cellpadding="0" align="center" class="table1">
    <tr> 
      <td class="rd6">ѡ��</td>
      <td  class="rd6">�跽�ͻ�����</td>
      <td  class="rd6">�跽�ͻ�����</td>
      <td  class="rd6">�跽�ͻ�����</td>
    </tr>
    <%
    	List clientList = pageModel.getList();
    	for (Iterator iter = clientList.iterator(); iter.hasNext();) {
    		AimClient aimClient = (AimClient)iter.next();    		
    %>
    <tr > 
      <td class="rd8" ><input type="radio" name="selectFlag" value="radiobutton" ondblclick="doubleclick('<%=aimClient.getId() %>', '<%=aimClient.getClientTemiId()%>', '<%=aimClient.getName()%>')"></td>
      <td  class="rd8" ><%=aimClient.getClientTemiId() %></td>
      <td  class="rd8" ><%=aimClient.getName() %></td>
      <td  class="rd8" ><%=aimClient.getClientTemilevelName() %></td>
    </tr>
    <%
    	}
    %>
  </table>
  <table width="95%" height="30" border="0" align="center" cellpadding="0" cellspacing="0" class="rd1">
    <tr> 
      <td  nowrap class="rd19" height="2" width="36%"> <div align="left"><font color="#FFFFFF">&nbsp;��&nbsp<%=pageModel.getTotalPages() %>&nbspҳ</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#FFFFFF">��ǰ��</font>&nbsp<font color="#FF0000"><%=pageModel.getPageNo() %></font>&nbsp<font color="#FFFFFF">ҳ</font> 
        </div></td>
      <td  nowrap class="rd19" width="64%"> <div align="right"> 
        <input name="btnTopPage" class="button1" type="button" id="btnTopPage" value="|&lt;&lt; "  title="��ҳ" onClick="topPage()">
        <input name="btnPreviousPage" class="button1" type="button" id="btnPreviousPage" value=" &lt;  "  title="��ҳ" onClick="previousPage()">
        <input name="btnNext" class="button1" type="button" id="btnNext" value="  &gt; "  title="��ҳ" onClick="nextPage()">
        <input name="btnBottomPage" class="button1" type="button" id="btnBottomPage" value=" &gt;&gt;|"  title="βҳ" onClick="bottomPage()">
          <input name="btnOk" class="button1" type="button" id="btnOk" value="ȷ��" onclick="selectOk()">
          <input name="btnClose" class="button1" type="button" id="btnClose" value="�ر�" onClick="window.close()">
        </div></td>
    </tr>
  </table>
</form>
</body>
</html>