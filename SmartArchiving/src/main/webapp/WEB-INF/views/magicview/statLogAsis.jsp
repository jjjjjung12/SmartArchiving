<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "조회화면"); request.setAttribute("menuFolded", Boolean.TRUE); %>
<span data-menu-folded="true" hidden></span>
<div class="box_option box_option--actions-right">
	<ul class="option_list">
		
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="export" class="btn grey mid">Excel 저장</a></li>
	</ul>
</div>
<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
			<th>서비스ID</th>
			<th>서비스명</th>
			<th>총건수</th>
			<th>정상건수</th>
			<th>오류건수</th>
			<th>파트너건수</th>
			<th>Windows</th>
			<th>Android</th>
			<th>Ios</th>
			<th>Mac</th>
			<th>Linux</th>
			<th>기타</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>
<script src="${pageContext.request.contextPath}/js/magicview/statLogAsis.js"></script>
