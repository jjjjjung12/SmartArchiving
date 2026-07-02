<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>결재 처리</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
String approvalLineUserId = (String) session.getAttribute("approval_line_user_id");
String username = (String) session.getAttribute("username");
String brnm = (String) session.getAttribute("brnm");
%>
<div class="container py-5">
    <div class="card mx-auto" style="max-width: 640px;">
        <div class="card-body">
            <h5 class="mb-3">결재 대기 처리</h5>
            <p>사용자: <%=username == null ? "" : username%></p>
            <p>부점: <%=brnm == null ? "" : brnm%></p>
            <p>결재라인: <%=approvalLineUserId == null ? "" : approvalLineUserId%></p>
            <p class="text-muted mb-0">결재 처리 화면은 추후 MAGICARCHIVE 기능 이관 예정입니다.</p>
        </div>
    </div>
</div>
</body>
</html>
