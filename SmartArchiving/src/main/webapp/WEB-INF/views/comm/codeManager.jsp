<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "코드 관리"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>그룹코드/명</strong><input type="text" id="C_GROUP_NM" placeholder="코드 또는 이름" maxlength="100"/></li>
		<li><span class="ir_btn">
			<input type="radio" name="C_USE_YN" id="C_USE_Y" value="Y" checked="checked"/><label for="C_USE_Y">사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_N" value="N"/><label for="C_USE_N">미사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_A" value="A"/><label for="C_USE_A">전체</label>
		</span></li>
		<li style="margin-left:auto"><a href="javascript:void(0)" id="btnQuery" class="btn mcolor mid">조회</a></li>
	</ul>
</div>

<div class="table-info-layout code-manager-layout mt32">
<div class="table-info-top code-group-top">
	<div class="master-panel table-info-list-panel">
		<div class="tit mb16"><h3>코드 목록</h3></div>
		<div class="flex_between list-toolbar">
			<span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span>
			<div class="list-toolbar__actions">
				<a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">그룹추가</a>
				<a href="javascript:void(0)" id="btnDelete" class="btn red mid">그룹삭제</a>
			</div>
		</div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover code-group-boardlist textcut boardlist"><thead><tr>
				<th class="col-chk"><input type="checkbox" id="chkAllGroup" title="현재 페이지 전체 선택"/></th>
				<th class="col-cd">코드</th>
				<th class="col-nm tal_cut">이름</th>
				<th class="col-use">사용</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>

	<div class="table-info-detail-panel code-group-detail-panel">
		<div class="tit"><h3>그룹 상세</h3></div>
		<div class="table-info-detail-body">
			<p id="groupDetailEmpty" class="table-info-empty">그룹을 선택하거나 [그룹추가]를 눌러 주세요.</p>
			<form id="SetTableForm" class="form_input form_input--fill table-info-detail-form">
				<input type="hidden" id="CRUD1" value="C"/>
				<dl class="input_box"><dt><label for="F_GROUP_CD">그룹코드</label></dt><dd>
					<input type="text" id="F_GROUP_CD" style="text-transform:uppercase;"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_GROUP_NM">그룹코드명</label></dt><dd>
					<input type="text" id="F_GROUP_NM"/>
				</dd></dl>
				<dl class="input_box input_box--use-yn"><dt>사용여부</dt><dd class="ir_btn">
					<input type="radio" name="F_USE_YN" id="F_USE_Y" value="Y"/><label for="F_USE_Y">예</label>
					<input type="radio" name="F_USE_YN" id="F_USE_N" value="N"/><label for="F_USE_N">아니오</label>
				</dd></dl>
				<div class="table-info-detail-actions">
					<a href="javascript:void(0)" id="btnGroupSave" class="btn mcolor mid">저장</a>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="table-info-top code-detail-top table-info-section--hidden" id="codeDetailPanel">
	<div class="master-panel table-info-list-panel code-detail-list-panel">
		<div class="tit mb16"><h3>상세코드</h3></div>
		<div class="flex_between list-toolbar">
			<span class="black">총 <strong class="pcolor" id="totalCnt2">0</strong>건</span>
			<div class="list-toolbar__actions">
				<a href="javascript:void(0)" id="btnAttrAdd" class="btn mcolor mid">추가</a>
				<a href="javascript:void(0)" id="btnAttrDelete" class="btn red mid">삭제</a>
			</div>
		</div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover code-detail-boardlist textcut boardlist"><thead><tr>
				<th class="col-chk"><input type="checkbox" id="chkAllDetail" title="현재 페이지 전체 선택"/></th>
				<th class="col-cd">코드</th>
				<th class="col-nm tal_cut">코드명</th>
				<th class="col-use">사용</th>
			</tr></thead><tbody id="gridBody2"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page2"></div></div>
	</div>

	<div class="table-info-detail-panel code-detail-detail-panel">
		<div class="tit"><h3>상세코드 상세</h3></div>
		<div class="table-info-detail-body">
			<p id="detailFormEmpty" class="table-info-empty">상세코드를 선택하거나 [추가]를 눌러 주세요.</p>
			<form id="SetATTRForm" class="form_input form_input--fill table-info-detail-form code-detail-detail-form">
				<input type="hidden" id="CRUD2" value="CD"/>
				<dl class="input_box"><dt><label for="F_DETAIL_CD">코드</label></dt><dd>
					<input type="text" id="F_DETAIL_CD" style="text-transform:uppercase;"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DETAIL_NM">코드명</label></dt><dd>
					<input type="text" id="F_DETAIL_NM"/>
				</dd></dl>
				<dl class="input_box input_box--use-yn"><dt>사용여부</dt><dd class="ir_btn">
					<input type="radio" name="F_USE_SUB_YN" id="F_USE_SUB_Y" value="Y"/><label for="F_USE_SUB_Y">예</label>
					<input type="radio" name="F_USE_SUB_YN" id="F_USE_SUB_N" value="N"/><label for="F_USE_SUB_N">아니오</label>
				</dd></dl>
				<div class="table-info-detail-actions">
					<a href="javascript:void(0)" id="btnDetailSave" class="btn mcolor mid">저장</a>
				</div>
			</form>
		</div>
	</div>
</div>
</div>

<script src="${pageContext.request.contextPath}/js/comm/codeManager.js"></script>
