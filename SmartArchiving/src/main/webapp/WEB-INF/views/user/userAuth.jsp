<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "그룹 권한 관리"); %>
<div class="box_option box_option--actions-right">
	<ul class="option_list">
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
	</ul>
</div>

<div class="master-detail master-detail--side mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">그룹 · 총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover"><thead><tr>
				<th>그룹ID</th><th>그룹명</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
		<form id="SetGroupForm" class="mt16">
			<input type="hidden" id="CRUD1" value="U"/>
			<input type="hidden" id="C_USERGRP"/>
		</form>
	</div>
	<div class="detail-panel" style="display:flex;flex-direction:column;gap:24px;">
		<div>
			<div class="tit mb16"><h3>미할당 메뉴</h3></div>
			<div class="tbl_box"><table class="tbl02 txthover"><thead><tr><th>메뉴명</th><th>URL</th></tr></thead><tbody id="gridBody2"></tbody></table></div>
			<div class="paging mt16"><div class="wr_page2"></div></div>
		</div>
		<div>
			<div class="tit mb16"><h3>할당 메뉴</h3></div>
			<div class="tbl_box"><table class="tbl02 txthover"><thead><tr><th>메뉴명</th><th>URL</th></tr></thead><tbody id="gridBody3"></tbody></table></div>
			<div class="paging mt16"><div class="wr_page3"></div></div>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/user/userAuth.js"></script>
