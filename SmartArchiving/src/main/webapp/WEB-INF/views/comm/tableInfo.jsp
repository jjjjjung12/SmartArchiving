<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "테이블 관리"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong>
			<div class="select_box"><select id="C_SERVER_ID"><option value="*" selected="selected">서버전체</option></select></div>
		</li>
		<li><strong>테이블명</strong><input type="text" id="C_TABLE_NM" placeholder="한글명/영문명" maxlength="100"/></li>
		<li><span class="ir_btn">
			<input type="radio" name="C_USE_YN" id="C_USE_Y" value="Y" checked="checked"/><label for="C_USE_Y">사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_N" value="N"/><label for="C_USE_N">미사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_A" value="A"/><label for="C_USE_A">전체</label>
		</span></li>
		<li style="margin-left:auto"><a href="javascript:void(0)" id="btnQuery" class="btn mcolor mid">조회</a></li>
	</ul>
</div>

<div class="table-info-layout mt32">
<div class="table-info-top">
	<div class="master-panel table-info-list-panel">
		<div class="tit mb16"><h3>테이블 목록</h3></div>
		<div class="flex_between list-toolbar">
			<span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span>
			<div class="list-toolbar__actions">
				<a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">테이블추가</a>
				<a href="javascript:void(0)" id="btnDelete" class="btn red mid">테이블삭제</a>
			</div>
		</div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover table-boardlist textcut boardlist"><thead><tr>
				<th class="col-chk"><input type="checkbox" id="chkAllTable" title="현재 페이지 전체 선택"/></th>
				<th class="col-id">ID</th>
				<th class="col-cd">영문명</th>
				<th class="col-nm tal_cut">이름</th>
				<th class="col-use">사용</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>

	<div class="table-info-detail-panel">
		<div class="tit"><h3>테이블 상세</h3></div>
		<div class="table-info-detail-body">
			<p id="tableDetailEmpty" class="table-info-empty">테이블을 선택하거나 [테이블추가]를 눌러 주세요.</p>
			<form id="SetTableForm" class="form_input form_input--fill table-info-detail-form">
				<input type="hidden" id="CRUD1" value="C"/>
				<input type="hidden" id="F_SERVER_ID"/>
				<dl class="input_box"><dt><label for="F_TABLE_ID">테이블 ID</label></dt><dd>
					<input type="text" id="F_TABLE_ID"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_TABLE_CD">영문명</label></dt><dd>
					<input type="text" id="F_TABLE_CD"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_TABLE_NM">한글명</label></dt><dd>
					<input type="text" id="F_TABLE_NM"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_TABLE_JOIN_NM">별칭(조인)</label></dt><dd>
					<input type="text" id="F_TABLE_JOIN_NM"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SAVE_PREQ_CD">보관주기</label></dt><dd class="disflex gap10">
					<div class="select_box"><select id="F_SAVE_PREQ_CD"><%=codeClass.getComboBoxByCodeList("SAVE_PREQ_CD", "", true) %></select></div>
					<input type="text" id="F_SAVE_PREQ" placeholder="주기값" style="flex:1"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_EXP_PREQ_CD">Export주기</label></dt><dd class="disflex gap10">
					<div class="select_box"><select id="F_EXP_PREQ_CD"><%=codeClass.getComboBoxByCodeList("EXP_PREQ_CD", "", true) %></select></div>
					<input type="text" id="F_EXP_PREQ" placeholder="주기값" style="flex:1"/>
				</dd></dl>
				<dl class="input_box input_box--grow input_box--desc"><dt><label for="F_DESCRIPTION">설명</label></dt><dd>
					<textarea id="F_DESCRIPTION" rows="3"></textarea>
				</dd></dl>
				<dl class="input_box input_box--use-yn"><dt>사용여부</dt><dd class="ir_btn">
					<input type="radio" name="F_USE_YN" id="F_USE_Y" value="Y"/><label for="F_USE_Y">예</label>
					<input type="radio" name="F_USE_YN" id="F_USE_N" value="N"/><label for="F_USE_N">아니오</label>
				</dd></dl>
				<div class="table-info-detail-actions">
					<a href="javascript:void(0)" id="btnTableSave" class="btn mcolor mid">저장</a>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="table-info-bottom table-info-bottom--hidden" id="tableInfoColumnPanel">
	<div class="tit mb16"><h3>컬럼 목록</h3></div>
	<div class="flex_between list-toolbar mt16">
		<span class="black">총 <strong class="pcolor" id="totalCnt2">0</strong>건</span>
		<div class="list-toolbar__actions">
			<a href="javascript:void(0)" id="btnAttrAdd" class="btn mcolor mid">추가</a>
			<a href="javascript:void(0)" id="btnAttrDelete" class="btn red mid">삭제</a>
			<a href="javascript:void(0)" id="btnExpExcel" class="btn grey mid">Excel</a>
<!-- 			<a href="javascript:void(0)" id="btnImpExcel" class="btn grey mid">가져오기</a> -->
<!-- 			<a href="javascript:void(0)" id="btnAttrRenum" class="btn grey mid">순서재정렬</a> -->
		</div>
	</div>
	<p id="attrEmptyHint" class="table-info-empty">테이블을 선택하면 컬럼 목록이 표시됩니다.</p>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover attr-boardlist textcut boardlist"><thead><tr>
			<th class="col-chk"><input type="checkbox" id="chkAllAttr" title="현재 페이지 전체 선택"/></th>
			<th class="col-cd">코드</th>
			<th class="col-nm tal_cut">컬럼명</th>
			<th class="col-type">타입</th>
			<th class="col-size">길이</th>
			<th class="col-order">순서</th>
			<th class="col-where">조건</th>
			<th class="col-output">출력</th>
			<th class="col-null">Null</th>
			<th class="col-date">날짜</th>
			<th class="col-time">시간</th>
		</tr></thead><tbody id="gridBody2"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page2"></div></div>
	<input type="file" id="btnImpExcelFile" accept=".xlsx,.xls" style="display:none"/>
</div>
</div>

<script src="${pageContext.request.contextPath}/js/lib/xlsx.full.min.js"></script>
<script src="${pageContext.request.contextPath}/js/comm/tableInfo.js"></script>
