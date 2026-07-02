<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<span data-menu-folded="true" hidden></span>
<% request.setAttribute("pageTitle", "요청 현황"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>시작일</strong><input type="text" id="F_SP_START_TR_YMD" maxlength="8" placeholder="YYYYMMDD" style="width:120px;"/></li>
		<li><strong>종료일</strong><input type="text" id="F_SP_END_TR_YMD" maxlength="8" placeholder="YYYYMMDD" style="width:120px;"/></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
	</ul>
</div>

<input type="hidden" id="F_USER_CD" value="<%=session.getAttribute("usercd")%>"/>
<input type="hidden" id="F_USER_NM" readonly/>
<select id="F_APPROVAL_DIV_NM" name="F_APPROVAL_DIV_NM" style="display:none;">
	<%=codeClass.getComboBoxByCodeList("APPLY_DIV_CD", "", true) %>
</select>

<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover"><thead><tr>
				<th>요청일자</th><th>요청구분</th><th>요청ID</th><th>요청내용</th>
				<th>요청자ID</th><th>부서명</th><th>요청자</th>
				<th>1차 결재일</th><th>1차 결재상태</th>
				<th>2차 결재일</th><th>2차 결재상태</th>
				<th>3차 결재일</th><th>3차 결재상태</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>결재상태</h3></div>
		<div class="form_input form-grid-approve">
			<dl class="input_box"><dt><label for="F_FIRST_APPROVAL_LINE_USER_NM">1차 결재자명</label></dt><dd><input type="text" id="F_FIRST_APPROVAL_LINE_USER_NM" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_FIRST_APPROVAL_YN_NM">결재상태</label></dt><dd><input type="text" id="F_FIRST_APPROVAL_YN_NM" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_FIRST_APPROVAL_REJECT_DOCU">반려사유</label></dt><dd><input type="text" id="F_FIRST_APPROVAL_REJECT_DOCU" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_FIRST_APPROVAL_DATE">결재일</label></dt><dd><input type="text" id="F_FIRST_APPROVAL_DATE" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_SECOND_APPROVAL_LINE_USER_NM">2차 결재자명</label></dt><dd><input type="text" id="F_SECOND_APPROVAL_LINE_USER_NM" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_SECOND_APPROVAL_YN_NM">결재상태</label></dt><dd><input type="text" id="F_SECOND_APPROVAL_YN_NM" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_SECOND_APPROVAL_REJECT_DOCU">반려사유</label></dt><dd><input type="text" id="F_SECOND_APPROVAL_REJECT_DOCU" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_SECOND_APPROVAL_DATE">결재일</label></dt><dd><input type="text" id="F_SECOND_APPROVAL_DATE" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_THIRD_APPROVAL_LINE_USER_NM">3차 결재자명</label></dt><dd><input type="text" id="F_THIRD_APPROVAL_LINE_USER_NM" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_THIRD_APPROVAL_YN_NM">결재상태</label></dt><dd><input type="text" id="F_THIRD_APPROVAL_YN_NM" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_THIRD_APPROVAL_REJECT_DOCU">반려사유</label></dt><dd><input type="text" id="F_THIRD_APPROVAL_REJECT_DOCU" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="F_THIRD_APPROVAL_DATE">결재일</label></dt><dd><input type="text" id="F_THIRD_APPROVAL_DATE" readonly/></dd></dl>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/approve/userApproveStatSearch.js"></script>
