<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "SmartArchiving - 비밀번호 찾기"); %>
<div class="cont type2">
	<h2>비밀번호 찾기</h2>
	<div class="form-forgot-error" style="display:none;">
		<strong>전송 실패</strong>
		<p>입력한 이메일을 확인하세요.</p>
	</div>
	<div class="form-forgot-success" style="display:none;">
		<strong>초기화 메일을 전송했습니다.</strong>
		<p>메일을 확인하시고 24시간 이내에 비밀번호를 초기화하세요.</p>
	</div>
	<form id="form_forgot" action="#" autocomplete="off" onsubmit="return false;">
		<p class="desc mb16">가입하신 이메일을 입력하시면 비밀번호 초기화 안내를 전송합니다.</p>
		<div class="list_input">
			<label for="email">이메일</label>
			<input type="text" id="email" name="email" autocomplete="off"/>
		</div>
		<button type="submit" class="login_btn w100p bg_gradient">확인</button>
		<a href="${pageContext.request.contextPath}/login" class="login_btn w100p scolor mt8">로그인 페이지로</a>
	</form>
</div>
