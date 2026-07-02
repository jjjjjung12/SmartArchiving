<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "export 작업 조회"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>작업명</strong><input type="text" id="C_JOB_NM" style="width:200px;height:30px;" placeholder=" 작업명을 입력하세요."/></li>
		<li><span class="ir_btn"><input type="radio" name="C_USE_YN" id="C_USE_YN_0" value="Y"/><label for="C_USE_YN_0">사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_1" value="N"/><label for="C_USE_YN_1">미사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_2" value="A"/><label for="C_USE_YN_2">전체</label></span></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
		<th>데몬CD</th>
		<th>데몬명</th>
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
			<label for="F_JOB_NM" class="col-sm-3 control-label">작업명</label>
			<div class="col-sm-8">
			<input type="hidden" id="F_JOB_ID">
			<input type="text" id="F_JOB_NM" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_DAEMON_ID" class="col-sm-3 control-label">데몬ID</label>
			<div class="col-sm-8">
			<input type="text" id="F_DAEMON_ID" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_DAEMON_CD" class="col-sm-3 control-label">데몬CD</label>
			<div class="col-sm-8">
			<input type="text" id="F_DAEMON_CD" readonly >
			</div>
			</div>
			<div class="form-group">
			<label for="F_DAEMON_NM" class="col-sm-3 control-label">데몬명</label>
			<div class="col-sm-8">
			<input type="text" id="F_DAEMON_NM" readonly >
			</div>
			</div>
			<div class="form-group">
			<label for="F_SRC_TABLE_CD" class="col-sm-3 control-label">SRC 테이블</label>
			<div class="col-sm-4">
			<input type="hidden" id="F_SRC_TABLE_ID" readonly>
			<input type="text" id="F_SRC_TABLE_CD" readonly>
			</div>
			<div class="col-sm-4">
			<input type="text" id="F_SRC_TABLE_NM" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_DES_TABLE_CD" class="col-sm-3 control-label">DES 테이블</label>
			<div class="col-sm-4">
			<input type="hidden" id="F_DES_TABLE_ID" readonly>
			<input type="text" id="F_DES_TABLE_CD" readonly>
			</div>
			<div class="col-sm-4">
			<input type="text" id="F_DES_TABLE_NM" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_DES_FILE_PATH" class="col-sm-3 control-label">DES 파일경로</label>
			<div class="col-sm-8">
			<input type="text" id="F_DES_FILE_PATH" readonly >
			</div>
			</div>
			<div class="form-group">
			<label for="F_DES_FLAG_PATH" class="col-sm-3 control-label">DES FLAG경로</label>
			<div class="col-sm-8">
			<input type="text" id="F_DES_FLAG_PATH" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_SRC_SERVER_NM" class="col-sm-3 control-label">SRC 서버명</label>
			<div class="col-sm-8">
			<input type="hidden" id="F_SRC_SERVER_ID" >
			<input type="text" id="F_SRC_SERVER_NM" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_DES_SERVER_NM" class="col-sm-3 control-label">DES 서버명</label>
			<div class="col-sm-8">
			<input type="hidden" id="F_DES_SERVER_ID" >
			<input type="text" id="F_DES_SERVER_NM" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="F_BASE_DATE" class="col-sm-3 control-label">기준일자</label>
			<div class="col-sm-8">
			<input type="text" id="F_BASE_DATE" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">구분자</label>
			<div class="col-sm-8" >
			<select id="F_DELIMITER" disabled>
			<option value="^$%@!" >|$| &nbsp; (MULTI)</option>
			<option value="^$%@!" >^$%@! &nbsp; (MULTI)</option>
			<option value="|" >| &nbsp; (VERTICAL BAR)</option>
			<option value="," >, &nbsp; (COMMA)</option>
			<option value="~;">~; &nbsp; (TILDE/SEMICOLON)</option>
			</select>
			</div>
			</div>
			<div class="form-group">
			<label for="F_JOB_SCHEDULE" class="col-sm-3 control-label">작업스케쥴</label>
			<div class="col-sm-8">
			<input type="text" id="F_JOB_SCHEDULE" readonly >
			</div>
			</div>
			<div class="form-group">
			<label for="F_JOB_TYPE" class="col-sm-3 control-label">작업타입</label>
			<div class="col-sm-8">
			<input type="text" id="F_JOB_TYPE" readonly >
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">사용여부</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_USE_YN" name="F_USE_YN" value="Y" onclick="return false;">
			<label for="F_USE_YN">예</label>
			<input type="radio" id="F_USE_YN" name="F_USE_YN" value="N" onclick="return false;">
			<label for="F_USE_YN">아니오</label>
			</div>
			</div>
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/job/exportJobMst.js"></script>
