<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "SmartArchiving - 로그인");
%>
<div class="cont type2">
	<h2>로그인</h2>
	<div class="form-login-error" style="display: none;">
		<strong>로그인 실패</strong>
		<p>아이디 또는 비밀번호를 확인하세요.</p>
	</div>
	<form id="form_login" action="#" autocomplete="off"
		onsubmit="return false;">
		<input type="hidden" id="F_USER_SSO_ID" />
		<div class="list_input">
			<label for="username">아이디</label> <input type="text" id="username"
				name="username" autocomplete="off" />
		</div>
		<div class="list_input">
			<label for="password">비밀번호</label> <input type="password"
				id="password" name="password" autocomplete="off" />
		</div>
		<button type="submit" class="login_btn w100p bg_gradient">로그인</button>
		<button type="button" id="sso_login"
			class="login_btn w100p scolor mt8">SSO 로그인</button>
		<%-- 비밀번호 찾기(현재 미사용): <a href="${pageContext.request.contextPath}/forgot-password" class="link_forgot mt8">비밀번호 찾기</a> --%>
	</form>
</div>

