<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "사용자 그룹 등록"); %>
<div class="box_option box_option--actions-right">
	<ul class="option_list">
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">추가</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnSaveMember" class="btn grey mid">그룹멤버저장</a></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
	</ul>
</div>

<div class="master-detail master-detail--side mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">그룹 목록 · 총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover"><thead><tr>
				<th>그룹ID</th><th>그룹명</th><th>사용여부</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
		<form id="SetGroupForm" class="form_input mt24">
			<input type="hidden" id="CRUD1" value="U"/><input type="hidden" id="ROWID1"/>
			<dl class="input_box"><dt><label for="F_USER_GRP_ID">그룹ID</label></dt><dd><input type="text" id="F_USER_GRP_ID"/></dd></dl>
			<dl class="input_box"><dt><label for="F_USER_GRP_NM">그룹명</label></dt><dd><input type="text" id="F_USER_GRP_NM"/></dd></dl>
			<dl class="input_box"><dt><label for="F_DESCRIPTION">설명</label></dt><dd><textarea id="F_DESCRIPTION" rows="3"></textarea></dd></dl>
			<dl class="input_box"><dt>사용여부</dt><dd class="ir_btn">
				<input type="radio" name="F_USE_YN" id="F_USE_Y" value="Y"/><label for="F_USE_Y">사용</label>
				<input type="radio" name="F_USE_YN" id="F_USE_N" value="N"/><label for="F_USE_N">미사용</label>
			</dd></dl>
		</form>
	</div>
	<div class="detail-panel" style="display:flex;flex-direction:column;gap:24px;">
		<div>
			<div class="tit mb16"><h3>미소속 사용자</h3></div>
			<div class="tbl_box"><table class="tbl02 txthover"><thead><tr><th>사용자명</th></tr></thead><tbody id="gridBody2"></tbody></table></div>
			<div class="paging mt16"><div class="wr_page2"></div></div>
		</div>
		<div>
			<div class="tit mb16"><h3>그룹 사용자</h3></div>
			<div class="tbl_box"><table class="tbl02 txthover"><thead><tr><th>사용자명</th></tr></thead><tbody id="gridBody3"></tbody></table></div>
			<div class="paging mt16"><div class="wr_page3"></div></div>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/user/userGroup.js"></script>
