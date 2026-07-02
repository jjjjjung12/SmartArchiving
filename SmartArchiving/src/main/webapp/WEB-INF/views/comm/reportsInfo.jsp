<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "조회화면 관리"); %>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong>
			<div class="select_box"><select id="C_SERVER_ID"><option value="0">서버를 선택하세요.</option></select></div>
		</li>
		<li><span class="ir_btn">
			<input type="radio" name="C_USE_YN" id="C_USE_Y" value="Y"/><label for="C_USE_Y">사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_N" value="N"/><label for="C_USE_N">미사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_A" value="A" checked="checked"/><label for="C_USE_A">전체</label>
		</span></li>
	</ul>
</div>

<div class="disflex gap24 mt32" style="align-items:flex-start;">
	<div style="flex:0 0 42%;min-width:360px;">
		<div class="tit mb16"><h3>조회화면 목록</h3></div>
		<ul class="option_list mb10">
			<li><a href="javascript:void(0)" id="btnReportQuery" class="btn mcolor">조회</a></li>
			<li><a href="javascript:void(0)" id="btnReportAdd" class="btn mcolor mid">추가</a></li>
			<li><a href="javascript:void(0)" id="btnReportSave" class="btn mcolor mid">저장</a></li>
			<li><a href="javascript:void(0)" id="btnReportDel" class="btn red mid">삭제</a></li>
		</ul>
		<div class="tbl_box">
			<table class="tbl02 txthover"><thead><tr>
				<th>ID</th><th>영문명</th><th>이름</th><th>타입</th>
			</tr></thead><tbody id="gridBodyReport"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page_report"></div></div>

		<form id="SetReportForm" class="form_input mt20">
			<input type="hidden" id="CRUD1" value="C"/><input type="hidden" id="ROWID1"/>
			<dl class="input_box"><dt><label for="F_REPORT_ID">화면 ID</label></dt><dd><input type="text" id="F_REPORT_ID" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_REPORT_CD">영문명</label></dt><dd><input type="text" id="F_REPORT_CD"/></dd></dl>
			<dl class="input_box"><dt><label for="F_REPORT_NM">한글명</label></dt><dd><input type="text" id="F_REPORT_NM"/></dd></dl>
			<dl class="input_box"><dt>화면 타입</dt><dd class="ir_btn">
				<input type="radio" name="F_REPORT_TYPE" id="F_REPORT_JSON" value="J"/><label for="F_REPORT_JSON">JSON</label>
				<input type="radio" name="F_REPORT_TYPE" id="F_REPORT_FLAT" value="F"/><label for="F_REPORT_FLAT">FLAT</label>
				<input type="radio" name="F_REPORT_TYPE" id="F_REPORT_STAT" value="S"/><label for="F_REPORT_STAT">STAT</label>
			</dd></dl>
			<dl class="input_box"><dt><label for="F_DESCRIPTION">설명</label></dt><dd><input type="text" id="F_DESCRIPTION"/></dd></dl>
			<dl class="input_box"><dt><label for="F_DETAIL_PAGE">상세 페이지</label></dt><dd><input type="text" id="F_DETAIL_PAGE"/></dd></dl>
		</form>

		<div class="mt24 mb10">
			<label class="black"><input type="checkbox" id="JOIN_CHECKBOX"/> JOIN 설정</label>
		</div>

		<div class="tit mb16"><h3>테이블 목록</h3></div>
		<ul class="option_list mb10">
			<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		</ul>
		<div class="tbl_box">
			<table class="tbl02 txthover"><thead><tr>
				<th>ID</th><th>영문명</th><th>이름</th><th>별칭</th>
			</tr></thead><tbody id="gridBodyTable"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page_table"></div></div>

		<div class="tit mb16 mt24"><h3>테이블 컬럼</h3></div>
		<div class="tbl_box">
			<table class="tbl02 txthover"><thead><tr>
				<th>코드</th><th>컬럼명</th><th>타입</th><th>길이</th>
			</tr></thead><tbody id="gridBodyTableAttr"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page_tattr"></div></div>
	</div>

	<div style="flex:1;min-width:0;">
		<div class="flex_between mb16">
			<h3 class="tit">조회화면 상세</h3>
			<ul class="option_list" style="margin:0;">
				<li><a href="javascript:void(0)" id="btnAttrAdd" class="btn mcolor mid">추가</a></li>
				<li><a href="javascript:void(0)" id="btnAttrSave" class="btn mcolor mid">저장</a></li>
				<li><a href="javascript:void(0)" id="btnAttrDel" class="btn red mid">삭제</a></li>
			</ul>
		</div>
		<div class="tbl_box">
			<table class="tbl02 txthover"><thead><tr>
				<th>코드</th><th>컬럼명</th><th>타입</th><th>길이</th><th>순서</th><th>조건</th><th>출력</th>
			</tr></thead><tbody id="gridBody2"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page2"></div></div>

		<form id="SetATTRForm" class="form_input mt24">
			<input type="hidden" id="CRUD2" value="AU"/><input type="hidden" id="ROWID2"/>
			<input type="hidden" id="F_TABLE_ID"/>
			<dl class="input_box"><dt><label for="F_ATTR_CD">컬럼코드</label></dt><dd><input type="text" id="F_ATTR_CD"/></dd></dl>
			<dl class="input_box"><dt><label for="F_ATTR_NM">컬럼명</label></dt><dd><input type="text" id="F_ATTR_NM"/></dd></dl>
			<dl class="input_box"><dt><label for="F_ATTR_TYPE_CD">타입</label></dt><dd>
				<input type="hidden" id="F_ATTR_TYPE_NM"/>
				<div class="select_box"><select id="F_ATTR_TYPE_CD"><%=codeClass.getComboBoxByCodeList("ATTR_TYPE_CD", "", true) %></select></div>
			</dd></dl>
			<dl class="input_box"><dt><label for="F_ATTR_SIZE">길이</label></dt><dd><input type="text" id="F_ATTR_SIZE"/></dd></dl>
			<dl class="input_box"><dt><label for="F_DECIMAL_SIZE">소수자리</label></dt><dd><input type="text" id="F_DECIMAL_SIZE" value="0"/></dd></dl>
			<dl class="input_box"><dt><label for="F_SORT_INDEX">순서</label></dt><dd><input type="text" id="F_SORT_INDEX"/></dd></dl>
			<dl class="input_box"><dt><label for="F_WHERE_INDEX">조건순서</label></dt><dd><input type="text" id="F_WHERE_INDEX"/></dd></dl>
			<dl class="input_box"><dt><label for="F_OUTPUT_INDEX">출력순서</label></dt><dd><input type="text" id="F_OUTPUT_INDEX"/></dd></dl>
			<dl class="input_box"><dt>출력여부</dt><dd class="ir_btn">
				<input type="radio" name="F_OUTPUT_YN" id="F_OUTPUT_Y" value="Y"/><label for="F_OUTPUT_Y">출력</label>
				<input type="radio" name="F_OUTPUT_YN" id="F_OUTPUT_N" value="N"/><label for="F_OUTPUT_N">출력안함</label>
			</dd></dl>
			<dl class="input_box"><dt>날짜컬럼</dt><dd class="ir_btn">
				<input type="radio" name="F_DATE_TYPE_YN" id="F_DATE_Y" value="Y"/><label for="F_DATE_Y">예</label>
				<input type="radio" name="F_DATE_TYPE_YN" id="F_DATE_N" value="N"/><label for="F_DATE_N">아니오</label>
			</dd></dl>
			<dl class="input_box"><dt>시간컬럼</dt><dd class="ir_btn">
				<input type="radio" name="F_TIME_TYPE_YN" id="F_TIME_Y" value="Y"/><label for="F_TIME_Y">예</label>
				<input type="radio" name="F_TIME_TYPE_YN" id="F_TIME_N" value="N"/><label for="F_TIME_N">아니오</label>
			</dd></dl>
			<dl class="input_box"><dt><label for="F_TABLE_CD">테이블명</label></dt><dd><input type="text" id="F_TABLE_CD" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_TABLE_ALIAS_NM">테이블 별칭</label></dt><dd><input type="text" id="F_TABLE_ALIAS_NM"/></dd></dl>
			<dl class="input_box"><dt><label for="F_JOIN_TABLE_CD">조인 테이블</label></dt><dd><input type="text" id="F_JOIN_TABLE_CD"/></dd></dl>
			<dl class="input_box"><dt><label for="F_JOIN_TABLE_ALIAS_NM">조인 별칭</label></dt><dd><input type="text" id="F_JOIN_TABLE_ALIAS_NM"/></dd></dl>
			<dl class="input_box"><dt><label for="F_JOIN_COLUMN">조인 컬럼</label></dt><dd><input type="text" id="F_JOIN_COLUMN"/></dd></dl>
			<input type="hidden" id="F_TABLE_NM"/>
		</form>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/comm/reportsInfo.js"></script>
