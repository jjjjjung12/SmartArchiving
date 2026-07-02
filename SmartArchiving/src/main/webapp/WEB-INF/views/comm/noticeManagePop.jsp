<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "공지사항"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">공지사항</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<input type="hidden" name="corpValue" id="corpValue" value="<c:out value='${param.corp}'/>"/>
			<input type="hidden" name="serverValue" id="serverValue" value="<c:out value='${param.server}'/>"/>
			<div class="pop_body__scroll">
				<div id="noticeTableDivID">
					<table id="noticeJqGrid"></table>
					<div id="noticeJqGridPager"></div>
				</div>
				<div class="tar mt16">
					<label><input type="checkbox" id="chk_date"/> 하루동안 열지 않기</label>
				</div>
			</div>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){ $('#pop_close').on('click', function() { closePop(); }); });
</script>
<script src="${pageContext.request.contextPath}/js/comm/noticeManagePop.js"></script>
