<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "스케쥴관리"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>작업명</strong><input type="text" id="C_JOB_NM" placeholder="작업명또는 ID를 입력하세요."/></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnHelp" class="btn grey mid" onclick="window.open('${pageContext.request.contextPath}/helpSchedule','helpSchedule','width=860,height=680,scrollbars=yes,resizable=yes')">도움말</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>상세정보</h3></div>
		<div class="form_input">
		<dl class="input_box"><dt><label for="f_name">작업코드</label></dt><dd><input type="text" id="F_JOB_CD" /></dd></dl>
		<dl class="input_box"><dt><label for="f_orgNm">작업명</label></dt><dd><input type="text" id="F_JOB_NM"   /></dd></dl>
		<dl class="input_box"><dt><label for="f_orgNm">스케쥴</label></dt><dd><input type="text" id="F_JOB_SCHEDULE"   /></dd></dl>
		<dl class="input_box"><dt><label for="f_orgNm">설명</label></dt><dd><textarea rows="2" data-stylesheet-url="assets/css/wysihtml5-color.css" name="F_DESCRIPTION" id="F_DESCRIPTION" /></dd></dl>
		<dl class="input_box"><dt><label for="f_orgNm">작업CLASS</label></dt><dd><input type="text" id="F_JOB_CLASS"   /></dd></dl>
		</div>
		<input type="hidden" id="CRUD" value="C"/>
		<input type="hidden" id="ROWID" value=""/>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/job/scheduleInfo.js"></script>
