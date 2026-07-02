<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String username = (String) session.getAttribute("username");
String usercd   = (String) session.getAttribute("usercd");
String approwait = (String) session.getAttribute("approwait");
String brnm = session.getAttribute("brnm") != null ? (String) session.getAttribute("brnm") : "";
if (username == null || username.isEmpty()) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
%>
<header id="header" class="header">
	<div class="wrapper flex_between">
		<div class="heager_left">
			<div class="logo"><a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.svg" alt="SmartArchiving"></a></div>
		</div>
		<div class="header_func">
			<div class="user">
				<span><%=username%> (<%=usercd%>)</span>
				<% if (!brnm.isEmpty()) { %> | <%=brnm%><% } %>
				<% if (approwait != null && !approwait.isEmpty() && !"0".equals(approwait)) { %>
				<a href="${pageContext.request.contextPath}/userApproveProc" class="lbtn mcolor" style="margin-left:10px;"><i class="fa-solid fa-bell"></i> <%=approwait%></a>
				<% } %>
				<a href="javascript:void(0);" onclick="ArchiveApp.logout();" class="lbtn grey" style="margin-left:10px;">로그아웃</a>
			</div>
		</div>
	</div>
</header>
