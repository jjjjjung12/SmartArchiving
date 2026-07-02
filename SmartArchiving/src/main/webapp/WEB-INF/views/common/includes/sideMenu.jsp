<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="menu_left" id="archive-sidebar">
	<div class="menu_tit bg_gradient">
		<span class="menu_tit__text">SmartArchiving</span>
		<button type="button" class="menu_toggle" title="메뉴 접기/펼치기" aria-label="메뉴 접기/펼치기">
			<i class="fa-solid fa-bars"></i>
		</button>
	</div>
	<ul class="gnb2" id="archive-main-menu"
		data-user-id="<%=session.getAttribute("userid")%>"
		data-user-cd="<%=session.getAttribute("usercd")%>"></ul>
</div>
