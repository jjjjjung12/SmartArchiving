<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "SQL주문"); %>
<div class="box_option">
	<ul class="option_list">
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnSQLTest" class="btn grey mid">테스트</a></li>
		<li><a href="javascript:void(0)" id="btnSqlSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnNewSave" class="btn mcolor mid">다른작업으로저장</a></li>
	</ul>
</div>

<form id="SetScheduleForm" class="master-detail mt32">
	<div class="master-panel">
		<div class="tit mb16"><h3>SQL</h3></div>
		<textarea id="F_SRC_SQL" name="F_SRC_SQL" rows="14" class="sql-editor"> select * from nhk.tb_ef_il_lm_message_log</textarea>
		<div class="mt16">
			<div class="flex_between mb10"><span class="black">총 <strong class="pcolor" id="sqlResultCount">0</strong>건</span></div>
			<div class="tbl_box">
				<table class="tbl02 txthover">
					<thead id="sqlResultHead"><tr></tr></thead>
					<tbody id="sqlResultBody"></tbody>
				</table>
			</div>
			<div class="paging mt16"><div class="wr_page" id="sqlResultPaging"></div></div>
		</div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>작업 상세</h3></div>
		<input type="hidden" id="CRUD" value="C"/>
		<input type="hidden" id="ROWID"/>
		<div class="form_input detail-form">
			<dl class="input_box"><dt><label for="F_JOB_ID">작업코드</label></dt><dd>
				<div class="select_box"><select id="F_JOB_ID">
					<option value="10">로그헤더 로드작업</option>
					<option value="11">로그헤더 로드작업2</option>
					<option value="12">로그헤더 로드작업 상호</option>
					<option value="13">로그헤더 로드작업 비로그인</option>
					<option value="14">로그데이타 로그작업 상호</option>
					<option value="15">로그데이타 로그작업 비로그인</option>
					<option value="16">통계로그</option>
				</select></div>
			</dd></dl>
			<dl class="input_box"><dt><label for="F_JOB_NM">작업명</label></dt><dd><input type="text" id="F_JOB_NM"/></dd></dl>
			<dl class="input_box"><dt><label for="F_DESCRIPTION">설명</label></dt><dd><textarea id="F_DESCRIPTION" rows="3"></textarea></dd></dl>
			<dl class="input_box"><dt><label for="F_JOB_PATH">작업경로</label></dt><dd><input type="text" id="F_JOB_PATH" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_JOB_STATUS_CD">작업상태</label></dt><dd>
				<div class="select_box"><select id="F_JOB_STATUS_CD" name="F_JOB_STATUS_CD">
					<%=codeClass.getComboBoxByCodeList("JOB_STATUS_CD", "", true) %>
				</select></div>
			</dd></dl>
			<dl class="input_box"><dt>접속테스트</dt><dd><a href="javascript:void(0)" id="btnConnTest" class="btn grey mid">접속테스트</a></dd></dl>
		</div>
	</div>
</form>

<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/job/onDemand.js"></script>
