<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "작업 이력(추출대상일기준)"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>FROM</strong><input type="text" id="C_FROM" maxlength="8" style="width: 90%;height: 95%;" placeholder=""/></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
			<th>작업명</th>
			<th>작업일시</th>
			<th>시작일시</th>
			<th>종료일시</th>
			<th>DES작업일자</th>
			<th>작업파일명</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>상세정보</h3></div>
		<div class="form_input">
		<dl class="input_box"><dt><label for="f_name">작업명</label></dt><dd><input type="hidden" id="F_JOB_SEQ"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">SRC레코드</label></dt><dd><input type="text" id="F_SRC_DATA_ROWS"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">DES레코드</label></dt><dd><input type="text" id="F_DES_DATA_ROWS"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">DES작업일자</label></dt><dd><input type="text" id="F_DES_JOBTIME"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">작업파일명</label></dt><dd><input type="text" id="F_DES_JOBFILE"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">등록시각</label></dt><dd><input type="text" id="F_START_DATETIME"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">완료시각</label></dt><dd><input type="text" id="F_END_DATETIME"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">상태</label></dt><dd><input type="text" id="F_JOB_STTS"  readonly /></dd></dl>
		</div>
		<input type="hidden" id="CRUD" value="C"/>
		<input type="hidden" id="ROWID" value=""/>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/bulkjob/exportDateHistory.js"></script>
