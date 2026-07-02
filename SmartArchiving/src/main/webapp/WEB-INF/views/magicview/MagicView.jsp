<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "조회화면"); %>
<div class="box_option">
	<ul class="option_list">
		
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="export" class="btn grey mid">Excel 저장</a></li>
	</ul>
</div>
<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
			<th>거래년월일</th>
			<th>인스턴스ID</th>
			<th>거래추적번호</th>
			<th>사용자ID</th>
			<th>사용자명</th>
			<th>고객식별자번호</th>
			<th>접속IP</th>
			<th>화면서비스ID</th>
			<th>화면서비스명</th>
			<th>요청구분</th>
			<th>오류코드</th>
			<th>GUID번호</th>
			<th>업무서비스ID</th>
			<th>거래ID</th>
			<th>거래명</th>
			<th>전문ID</th>
			<th>기관ID</th>
			<th>매체구분코드</th>
			<th>전문</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>
<script src="${pageContext.request.contextPath}/js/magicview/MagicView.js"></script>
