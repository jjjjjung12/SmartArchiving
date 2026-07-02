<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "조회요청"); %>
<div class="box_option">
	<ul class="option_list">
		
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnSaveExcel" class="btn mcolor mid">엑셀업로드</a></li>
	</ul>
</div>
<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
			<th>조회시작일</th>
			<th>조회종료일</th>
			<th>조회조건</th>
			<th>조회값</th>
			<th>영장번호</th>
			<th>배치여부</th>
			<th>결재여부</th>
			<th>다운로드파일</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>
<script src="${pageContext.request.contextPath}/js/bulkjob/searchBulkInquiryJobReq.js"></script>
