<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "작업 등록"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong><div class="select_box"><select id="C_SERVER_ID">
										<option value="0">서버를 선택하세요.</option>
									</select></div></li>
		<li><strong>작업명</strong><input type="text" id="C_JOB_NM" style="width:200px;height:30px;" placeholder=" 작업명 또는 ID를 입력하세요."/></li>
		<li><span class="ir_btn"><input type="radio" name="C_USE_YN" id="C_USE_YN_0" value="Y"/><label for="C_USE_YN_0">사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_1" value="N"/><label for="C_USE_YN_1">미사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_2" value="A"/><label for="C_USE_YN_2">전체</label></span></li>
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
		<th>작업ID</th>
		<th>작업명</th>
		<th>사용</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>작업정보 상세</h3></div>
		<form id="SetJobForm" class="form_input detail-form">
			<input type="hidden" id="CRUD" name="CRUD" value="C">
			<input type="hidden" id="ROWID" name="ROWID" >
			<div class="form-group">
			<label for="F_JOB_CD" class="col-sm-3 control-label">작업ID</label>
			<div class="col-sm-8">
			<input type="text" id="F_JOB_CD" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_JOB_NM" class="col-sm-3 control-label">작업명</label>
			<div class="col-sm-8">
			<input type="hidden" id="F_JOB_ID" readonly>
			<input type="text" id="F_JOB_NM" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_TABLE_CD" class="col-sm-3 control-label">테이블</label>
			<div class="col-sm-9 searchBox">
			<!-- button type="button" class="btn btn-default btn-icon" style="width:150px;" > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="entypo-search"></i></button -->
			<input type="hidden" id="F_TABLE_ID" readonly>
			<input type="text" id="F_TABLE_CD" readonly>
			<a href="javascript:void(0)" id="showAjaxModal" class="btn mcolor mid searchBox_mid" title="테이블 검색"><i class="fas fa-magnifying-glass"></i></a>
			<input type="text" id="F_TABLE_NM" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_SOURCE_PATH" class="col-sm-3 control-label">소스경로</label>
			<div class="col-sm-8">
			<input type="text" id="F_SOURCE_PATH" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_SOURCE_FILE" class="col-sm-3 control-label">소스파일</label>
			<div class="col-sm-8">
			<input type="text" id="F_SOURCE_FILE" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_INFO_PATH" class="col-sm-3 control-label">인포경로</label>
			<div class="col-sm-8">
			<input type="text" id="F_INFO_PATH" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_INFO_FILE" class="col-sm-3 control-label">인포파일</label>
			<div class="col-sm-8">
			<input type="text" id="F_INFO_FILE" >
			</div>
			</div>
			<!-- <div class="form-group">
			<label class="col-sm-3 control-label">Time Picker (Dropdown)</label>
			<div class="col-sm-3">
			<input type="text" data-template="dropdown" data-show-seconds="false" data-default-time="11:25 AM" data-show-meridian="true" data-minute-step="5" />
			</div>
			</div> -->
			<div class="form-group">
			<label for="F_JOB_TM_HAF" class="col-sm-3 control-label">작업시각</label>
			<div class="col-sm-3" >
			<select id="F_JOB_TM_HAF" >
			<option value="0">오전</option>
			<option value="1">오후</option>
			</select>
			</div>
			<div class="col-sm-3" >
			<select id="F_JOB_TM_MIN" >
			<option value="00">00</option>
			<option value="01">01</option>
			<option value="02">02</option>
			<option value="03">03</option>
			<option value="04">04</option>
			<option value="05">05</option>
			<option value="06">06</option>
			<option value="07">07</option>
			<option value="08">08</option>
			<option value="09">09</option>
			<option value="10">10</option>
			<option value="11">11</option>
			</select>
			</div>
			<div class="col-sm-3" >
			<select id="F_JOB_TM_SEC" >
			<option value="00">00</option>
			<option value="05">05</option>
			<option value="10">10</option>
			<option value="15">15</option>
			<option value="20">20</option>
			<option value="25">25</option>
			<option value="30">30</option>
			<option value="35">35</option>
			<option value="40">40</option>
			<option value="45">45</option>
			<option value="50">50</option>
			<option value="55">55</option>
			</select>
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">구분자</label>
			<div class="col-sm-8" >
			<select id="F_SEPARATOR" >
			<option value="^$%@!" >^$%@! &nbsp; (MULTI)</option>
			<option value="|" >| &nbsp; (VERTICAL BAR)</option>
			<option value="," >, &nbsp; (COMMA)</option>
			<option value="~;">~; &nbsp; (TILDE/SEMICOLON)</option>
			</select>
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">스케쥴적용</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_SCHEDULE_YN" name="F_SCHEDULE_YN" value="Y">
			<label for="F_SCHEDULE_YN">예</label>
			<input type="radio" id="F_SCHEDULE_YN" name="F_SCHEDULE_YN" value="N">
			<label for="F_SCHEDULE_YN">아니오</label>
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">원시파일삭제</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_SOURCE_DEL_YN" name="F_SOURCE_DEL_YN" value="Y">
			<label for="F_SOURCE_DEL_YN">예</label>
			<input type="radio" id="F_SOURCE_DEL_YN" name="F_SOURCE_DEL_YN" value="N">
			<label for="F_SOURCE_DEL_YN">아니오</label>
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">마지막컬럼 구분자여부</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_LAST_COL_YN" name="F_LAST_COL_YN" value="Y">
			<label for="F_LAST_COL_YN">예</label>
			<input type="radio" id="F_LAST_COL_YN" name="F_LAST_COL_YN" value="N">
			<label for="F_LAST_COL_YN">아니오</label>
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">사용여부</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_USE_YN" name="F_USE_YN" value="Y">
			<label for="F_USE_YN">예</label>
			<input type="radio" id="F_USE_YN" name="F_USE_YN" value="N">
			<label for="F_USE_YN">아니오</label>
			</div>
			</div>
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/job/jobInfo.js"></script>
