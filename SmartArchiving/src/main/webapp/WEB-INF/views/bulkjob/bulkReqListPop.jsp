<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "대량조회 요청 목록"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">대량조회 요청 목록</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<div class="pop_body__scroll">
				<form method="post" name="SetBulkForm" id="SetBulkForm" class="validate">
					<input type="hidden" name="reqId" id="reqId" value="<c:out value='${param.reqId}'/>"/>
					<input type="hidden" name="bulkExcelJsonData" id="bulkExcelJsonData"/>
				</form>
				<div id="tableDivID1" style="width:100%;">
					<table id="jqGrid1" style="width:100%;"></table>
					<div id="jqGridPager1" style="width:100%;"></div>
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
<script src="${pageContext.request.contextPath}/js/bulkjob/bulkReqListPop.js"></script>
