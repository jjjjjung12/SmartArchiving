<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "cnv 작업 이력"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>FROM</strong><input type="text" id="C_FROM" maxlength="8" style="width: 90%;height: 95%;" placeholder=""/></li>
		<li><strong>TO</strong><input type="text" id="C_TO" maxlength="8" style="width: 90%;height: 95%;" placeholder=""/></li>
		<li><strong>작업명</strong><input type="text" id="C_JOB_NM" style="width:90%; height:30px;" placeholder=" 작업명을 입력하세요."/></li>
		<li><strong>INFO FILE NM</strong><input type="text" id="C_INFO_FILE_NM" style="width:85%; height:30px;" placeholder=" 인포파일명을 입력하세요"/></li>
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
			<th>상태</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>상세정보</h3></div>
		<div class="form_input">
		<dl class="input_box"><dt><label for="f_name">CONVERT 파일명</label></dt><dd><input type="text" id="F_CONVERT_FILE"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">CONVERT크기</label></dt><dd><input type="text" id="F_CONVERT_SIZE"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">CONVERT레코드</label></dt><dd><input type="text" id="F_RECORD"  readonly/></dd></dl>
		<dl class="input_box"><dt><label for="f_name">등록시각</label></dt><dd><input type="text" id="F_REG_TM"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">완료시각</label></dt><dd><input type="text" id="F_END_TM"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">상태</label></dt><dd><input type="text" id="F_STAT_CD"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="f_name">메세지</label></dt><dd><input type="text" id="F_MSG"  readonly /></dd></dl>
		<dl class="input_box"><dt><label for="F_INFO_FILE">인포파일</label></dt><dd><input type="text" id="F_INFO_FILE" readonly /></dd></dl>
		<dl class="input_box"><dt><label for="F_INFO_FILE2">인포파일2</label></dt><dd><input type="text" id="F_INFO_FILE2" readonly /></dd></dl>
		</div>
		<input type="hidden" id="CRUD" value="C"/>
		<input type="hidden" id="ROWID" value=""/>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/job/cnvJobHistory.js"></script>
