<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<span data-menu-folded="true" hidden></span>
<% request.setAttribute("pageTitle", "관리자 결재"); %>
<div class="box_option">
	<ul class="option_list">
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
	</ul>
</div>

<input type="hidden" id="F_USER_CD_SET"/>
<input type="hidden" id="F_USER_NM_SET"/>
<input type="hidden" id="F_APPROVAL_REQ_ID"/>
<input type="hidden" id="F_APPROVAL_DIV_CD"/>
<input type="hidden" id="F_APPROVAL_LINE_USER_ID"/>
<input type="hidden" id="F_APPROVAL_LINE_INDEX"/>
<input type="hidden" id="F_PROGRAM_ID"/>
<input type="hidden" id="F_PROGRAM_NM"/>
<input type="hidden" id="F_BRC"/>
<input type="hidden" id="F_BRNM"/>
<input type="hidden" id="F_OFT_C"/>
<input type="hidden" id="F_OFT"/>
<input type="hidden" id="F_APPROVAL_REQ_DOCU"/>
<input type="hidden" id="F_APPROVAL_DATE"/>
<input type="hidden" id="F_REQ_DATE"/>
<input type="hidden" id="F_IP_ADDRESS"/>
<input type="hidden" id="F_AUTH"/>
<input type="hidden" id="F_COM_CD"/>
<input type="hidden" id="F_EXPIRE_DATE"/>
<input type="hidden" id="F_APPROVAL_YN_RESULT"/>
<select id="F_APPROVAL_DIV_NM" name="F_APPROVAL_DIV_NM" style="display:none;">
	<%=codeClass.getComboBoxByCodeList("APPLY_DIV_CD", "", true) %>
</select>

<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover"><thead><tr>
				<th>요청ID</th><th>요청구분</th><th>요청사유</th><th>요청자ID</th>
				<th>부서명</th><th>요청자</th><th>요청일자</th><th>결재자</th><th>결재상태</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>결재 상세정보</h3></div>
		<div class="form_input">
			<dl class="input_box"><dt><label for="F_APPROVAL_YN">결재</label></dt><dd>
				<select id="F_APPROVAL_YN" name="F_APPROVAL_YN">
					<option value=""></option>
					<option value="Y">승인</option>
					<option value="N">반려</option>
				</select>
			</dd></dl>
			<dl class="input_box input_box--full"><dt><label for="F_APPROVAL_REJECT_DOCU">반려사유</label></dt><dd>
				<input type="text" id="F_APPROVAL_REJECT_DOCU"/>
			</dd></dl>
		</div>
		<a href="javascript:void(0)" id="btnSave" class="btn mcolor mid mt16">결재</a>
	</div>
</div>
<script>
	var sessionGroupId="<%=session.getAttribute("groupid")%>";
	var sessionUsercd="<%=session.getAttribute("usercd")%>";
	var sessionUsernm="<%=session.getAttribute("username")%>";
</script>
<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/approve/userApproveProcAdmin.js"></script>
