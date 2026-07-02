<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<tiles:importAttribute name="layoutMode" ignore="true"/>
<c:if test="${empty layoutMode}"><c:set var="layoutMode" value="main"/></c:if>
<!DOCTYPE html>
<html lang="ko">
<head>
	<tiles:insertAttribute name="tiles_head"/>
</head>
<c:choose>

	<%-- 로그인 --%>
	<c:when test="${layoutMode == 'login'}">
<%
session.removeAttribute("username");
session.removeAttribute("userid");
session.removeAttribute("usercd");
session.removeAttribute("groupid");
String sso_id = (String) session.getAttribute("sso_id");
%>
<body class="login-page-body">
<div id="wrap" class="login-wrap">
	<div class="wrapper_cont login-wrapper">
		<div class="login_area">
			<section class="tac">
				<div class="login_logo mb24">
					<a href="${pageContext.request.contextPath}/" class="login-brand" title="SmartArchiving">
						<span class="login-brand__title">Smart<span class="login-brand__accent">Archiving</span></span>
					</a>
				</div>
				<div class="login_box tal">
					<tiles:insertAttribute name="tiles_content"/>
				</div>
			</section>
		</div>
	</div>
</div>
<div class="loading_bg" id="loginLoading"><div class="loading"></div></div>
<script>var sessionSsoId="<%=sso_id != null ? sso_id : ""%>"; var archiveCtx = ctx;</script>
<script src="${pageContext.request.contextPath}/js/auth/login.js"></script>
</body>
	</c:when>

	<%-- 팝업 / 비로그인 shell --%>
	<c:when test="${layoutMode == 'minimal'}">
<body class="popup-body">
<tiles:insertAttribute name="tiles_content"/>
<div class="loading_bg"><div class="loading"></div></div>
</body>
	</c:when>

	<%-- 기본: header + menu + (optional) footer --%>
	<c:otherwise>
<%
String username = (String) session.getAttribute("username");
if (username == null || username.isEmpty()) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<body>
<div class="popup_bg" style="display:none;"></div>
<div id="wrap">
	<tiles:insertAttribute name="tiles_header" ignore="true"/>
	<div id="container">
		<div class="contents">
			<div class="wrapper_cont disflex" id="archive-wrapper">
				<tiles:insertAttribute name="tiles_menu" ignore="true"/>
				<div class="sub_contarea">
					<nav class="breadcrumb-nav" id="archive-breadcrumb" aria-label="breadcrumb"></nav>
					<tiles:insertAttribute name="tiles_content"/>
				</div>
			</div>
		</div>
	</div>
	<tiles:insertAttribute name="tiles_footer" ignore="true"/>
</div>
<div class="loading_bg"><div class="loading"></div></div>
<script>
(function () {
	var flag = document.querySelector('.sub_contarea [data-menu-folded="true"]');
	var wrap = document.getElementById('archive-wrapper');
	if (flag && wrap) wrap.classList.add('menu-folded');
})();
</script>
</body>
	</c:otherwise>

</c:choose>
</html>