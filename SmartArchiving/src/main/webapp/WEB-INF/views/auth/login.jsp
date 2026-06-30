<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SmartArchiving Login</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body class="bg-light">
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String ssoId = (String) session.getAttribute("sso_id");
if (ssoId == null) {
    ssoId = "";
}
%>

<div class="container">
    <div class="row justify-content-center align-items-center min-vh-100">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body p-4">
                    <h4 class="text-center mb-4">SmartArchiving</h4>

                    <c:if test="${not empty param.message}">
                        <div class="alert alert-${param.result == 'success' ? 'success' : 'danger'} py-2">
                            <c:out value="${param.message}"/>
                        </div>
                    </c:if>

                    <form id="form_login" action="${ctx}/login" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="mb-3">
                            <label for="username" class="form-label">사용자 ID</label>
                            <input type="text" class="form-control" id="username" name="username"
                                   value="<%=ssoId%>"
                                   autocomplete="username" required autofocus>
                        </div>
                        <div class="mb-3" id="password_group">
                            <label for="password" class="form-label">비밀번호</label>
                            <input type="password" class="form-control" id="password" name="password"
                                   autocomplete="current-password">
                        </div>
                        <button type="submit" class="btn btn-primary w-100">로그인</button>
                        <button type="button" id="sso_login" class="btn btn-outline-secondary w-100 mt-2">SSO 로그인</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    var sessionSsoId = "<%=ssoId%>";
    var ctx = "${ctx}";
</script>
<script src="${ctx}/js/auth/login.js"></script>
</body>
</html>
