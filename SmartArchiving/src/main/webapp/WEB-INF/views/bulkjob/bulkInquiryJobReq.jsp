<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "조회 요청"); %>
<div class="box_option box_option--actions-right">
	<ul class="option_list">
		
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnSaveExcel" class="btn mcolor mid">조회 대상 파일 업로드</a></li>
	</ul>
</div>
<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
			<th>START_DATE</th>
			<th>END_DATE</th>
			<th>ACCOUNT_NO</th>
			<th>USER_ID</th>
			<th>CUST_REG_NO</th>
			<th>TEL_NO</th>
			<th>USER_IP</th>
			<th>ORG_FILE_NM</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>
<script src="${pageContext.request.contextPath}/js/bulkjob/bulkInquiryJobReq.js"></script>
