<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "작업 이력"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong><div class="select_box"><select id="C_SERVER_ID">
										<option value="0">서버를 선택하세요.</option>
									</select></div></li>
		<li><strong>FROM</strong><input type="text" id="C_FROM" placeholder=""/></li>
		<li><strong>TO</strong><input type="text" id="C_TO" placeholder=""/></li>
		<li><strong>작업명</strong><input type="text" id="C_JOB_NM" style="width:97%; height:30px;" placeholder=" 작업명 또는 ID를 입력하세요."/></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">재작업</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
			<th>작업명</th>
			<th>scan작업일시</th>
			<th>scan상태</th>
			<th>load작업일시</th>
			<th>load상태</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>상세정보</h3></div>
		<div class="form_input">
		<dl class="input_box"><dt><label for="f_name">소스파일명</label></dt><dd><input type="text" id="F_SOURCE_FILE"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">SCAN크기</label></dt><dd><input type="text" id="F_SOURCE_SIZE"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">SCAN레코드</label></dt><dd><input type="text" id="F_SCAN_RECORD"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">등록시각</label></dt><dd><input type="text" id="F_SCAN_REG_TM"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">완료시각</label></dt><dd><input type="text" id="F_SCAN_END_TM"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">상태</label></dt><dd><input type="text" id="F_SCAN_STAT_CD"  readonly /></dd></dl>
		</div>
		<input type="hidden" id="CRUD" value="C"/>
		<input type="hidden" id="ROWID" value=""/>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/job/convJobHistory.js"></script>
