<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
session.removeAttribute("username");
session.removeAttribute("userid");
session.removeAttribute("usercd");
session.removeAttribute("groupid");
String sso_id = (String) session.getAttribute("sso_id");
String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta name="_contextPath" content="<%=ctx%>"/>
<title>SmartArchiving - 로그인</title>
<link rel="stylesheet" href="<%=ctx%>/css/css.css"/>
<link rel="stylesheet" href="<%=ctx%>/css/archive-grid.css"/>
<link rel="stylesheet" href="<%=ctx%>/css/fontawesome6.4.0/fontawesomecss/all.min.css"/>
<script src="<%=ctx%>/js/jquery-3.7.1.min.js"></script>
<script>var ctx = '<%=ctx%>';</script>
</head>
<body class="login-page-body">

<div id="wrap" class="login-wrap">
	<div class="wrapper_cont login-wrapper">
		<div class="login_area">
			<section class="tac">
				<div class="login_logo mb24">
					<a href="<%=ctx%>/" class="login-brand" title="SmartArchiving">
						<span class="login-brand__title">Smart<span class="login-brand__accent">Archiving</span></span>
					</a>
				</div>
				<div class="login_box tal">
