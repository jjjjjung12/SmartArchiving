<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "마스킹 해제 이력 조회"); %>
<div class="box_option box_option--actions-right">
	<ul class="option_list">
		
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
	</ul>
</div>
<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
			<th>사용자ID</th>
			<th>사용자명</th>
			<th>요청일자</th>
			<th>결재요청ID</th>
			<th>처리일자</th>
			<th>프로그램ID</th>
			<th>프로그램명</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>
<script src="${pageContext.request.contextPath}/js/comm/maskingHistory.js"></script>
