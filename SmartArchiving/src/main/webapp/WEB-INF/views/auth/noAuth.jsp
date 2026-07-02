<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "권한 없음"); %>
<div class="mt32" style="text-align:center;padding:80px 20px;">
	<p style="font-size:48px;color:#e74c3c;margin-bottom:16px;"><i class="fa-solid fa-triangle-exclamation"></i></p>
	<p style="font-size:18px;color:#333;">화면에 대한 권한이 없습니다.</p>
	<p style="margin-top:24px;"><a href="${pageContext.request.contextPath}/" class="btn mcolor">홈으로</a></p>
</div>
