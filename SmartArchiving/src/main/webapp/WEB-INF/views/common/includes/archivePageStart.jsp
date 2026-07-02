<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String username = (String) session.getAttribute("username");
String usercd   = (String) session.getAttribute("usercd");
String userid   = (String) session.getAttribute("userid");
String groupid  = (String) session.getAttribute("groupid");
String sso_id   = (String) session.getAttribute("sso_id");
String approwait = (String) session.getAttribute("approwait");
String brnm = session.getAttribute("brnm") != null ? (String) session.getAttribute("brnm") : "";
boolean isLoginPage = Boolean.TRUE.equals(request.getAttribute("loginPage"));
if (!isLoginPage && (username == null || username.isEmpty())) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
String pageTitle = (String) request.getAttribute("pageTitle");
if (pageTitle == null) pageTitle = "SmartArchiving";
boolean menuFolded = Boolean.TRUE.equals(request.getAttribute("menuFolded"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/views/common/includes/archiveHead.jsp" %>
<title><%=pageTitle%></title>
</head>
<body>
<% if (!isLoginPage) { %>
<div class="popup_bg" style="display:none;"></div>
<div id="wrap">
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
<div id="container">
<div class="contents">
<div class="wrapper_cont disflex<%= menuFolded ? " menu-folded" : "" %>">
<%@ include file="/WEB-INF/views/common/includes/sideMenu.jsp" %>
<div class="sub_contarea">
<% } %>
