<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "원천 작업 등록"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong><div class="select_box"><select id="C_SERVER_ID"><option value="0">서버를 선택하세요.</option></select></div></li>
		<li><strong>작업명</strong><input type="text" id="C_JOB_NM" placeholder="작업명 또는 ID"/></li>
		<li><span class="ir_btn">
			<input type="radio" name="C_USE_YN" id="C_USE_Y" value="Y"/><label for="C_USE_Y">사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_N" value="N"/><label for="C_USE_N">미사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_A" value="A"/><label for="C_USE_A">전체</label>
		</span></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
	</ul>
</div>

<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover"><thead><tr>
				<th>작업ID</th><th>작업명</th><th>사용</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>작업정보 상세</h3></div>
		<form id="SetJobForm" class="form_input">
			<input type="hidden" id="CRUD" value="C"/><input type="hidden" id="ROWID"/>
			<dl class="input_box"><dt><label for="F_JOB_CD">작업ID</label></dt><dd><input type="text" id="F_JOB_CD" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_JOB_NM">작업명</label></dt><dd><input type="hidden" id="F_JOB_ID"/><input type="text" id="F_JOB_NM" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_TABLE_CD">테이블</label></dt><dd class="disflex gap10">
				<input type="hidden" id="F_TABLE_ID"/><input type="text" id="F_TABLE_CD" readonly style="flex:1"/>
				<input type="text" id="F_TABLE_NM" readonly style="flex:1"/>
				<a href="javascript:void(0)" id="showAjaxModal" class="btn grey mid">검색</a>
			</dd></dl>
			<dl class="input_box"><dt><label for="F_SOURCE_PATH">소스경로</label></dt><dd><input type="text" id="F_SOURCE_PATH" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_SOURCE_FILE">소스파일</label></dt><dd><input type="text" id="F_SOURCE_FILE" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_INFO_PATH">인포경로</label></dt><dd><input type="text" id="F_INFO_PATH" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_INFO_FILE">인포파일</label></dt><dd><input type="text" id="F_INFO_FILE" readonly/></dd></dl>
			<dl class="input_box"><dt>작업시각</dt><dd class="disflex gap10">
				<div class="select_box"><select id="F_JOB_TM_HAF"><option value="0">오전</option><option value="1">오후</option></select></div>
				<div class="select_box"><select id="F_JOB_TM_MIN"></select></div>
				<div class="select_box"><select id="F_JOB_TM_SEC"></select></div>
			</dd></dl>
			<dl class="input_box"><dt><label for="F_SEPARATOR">구분자</label></dt><dd><div class="select_box"><select id="F_SEPARATOR">
				<option value="^$%@!">|$| (MULTI)</option><option value="|">| (VERTICAL BAR)</option>
				<option value=",">, (COMMA)</option><option value="~;">~; (TILDE/SEMICOLON)</option>
			</select></div></dd></dl>
			<dl class="input_box"><dt>스케줄적용</dt><dd class="ir_btn">
				<input type="radio" name="F_SCHEDULE_YN" value="Y" id="F_SCH_Y"/><label for="F_SCH_Y">예</label>
				<input type="radio" name="F_SCHEDULE_YN" value="N" id="F_SCH_N"/><label for="F_SCH_N">아니오</label>
			</dd></dl>
			<dl class="input_box"><dt>원시파일삭제</dt><dd class="ir_btn">
				<input type="radio" name="F_SOURCE_DEL_YN" value="Y" id="F_DEL_Y"/><label for="F_DEL_Y">예</label>
				<input type="radio" name="F_SOURCE_DEL_YN" value="N" id="F_DEL_N"/><label for="F_DEL_N">아니오</label>
			</dd></dl>
			<dl class="input_box"><dt>마지막컬럼 구분자</dt><dd class="ir_btn">
				<input type="radio" name="F_LAST_COL_YN" value="Y" id="F_LAST_Y"/><label for="F_LAST_Y">예</label>
				<input type="radio" name="F_LAST_COL_YN" value="N" id="F_LAST_N"/><label for="F_LAST_N">아니오</label>
			</dd></dl>
			<dl class="input_box"><dt>사용여부</dt><dd class="ir_btn">
				<input type="radio" name="F_USE_YN" value="Y" id="F_USE_Y2"/><label for="F_USE_Y2">예</label>
				<input type="radio" name="F_USE_YN" value="N" id="F_USE_N2"/><label for="F_USE_N2">아니오</label>
			</dd></dl>
		</form>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/job/srcJobMst.js"></script>
