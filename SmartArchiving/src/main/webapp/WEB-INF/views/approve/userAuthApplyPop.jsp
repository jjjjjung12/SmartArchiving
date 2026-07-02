<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "권한 신청"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop" id="main_title">권한 신청</h2>
			<span id="sub_title" class="sr-only">권한 신청</span>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<form method="post" name="applyForm" id="applyForm" class="form_input form_input--fill" enctype="multipart/form-data">
				<input type="hidden" id="F_REQ_DATE" name="F_REQ_DATE"/>
				<input type="hidden" id="F_PAGE_NAME" name="F_PAGE_NAME" value="<c:out value='${param.pageName}'/>"/>
				<input type="hidden" id="F_GUBUN" name="F_GUBUN" value="<c:out value='${param.gubun}'/>"/>
				<dl class="input_box"><dt><label for="F_USER_CD_POP">사용자ID</label></dt><dd>
					<input type="text" id="F_USER_CD_POP" name="F_USER_CD_POP" value="<c:out value='${param.userCd}'/>" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_USER_NM_POP">성명</label></dt><dd>
					<input type="text" id="F_USER_NM_POP" name="F_USER_NM_POP" value="<c:out value='${param.userNm}'/>" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_BRMM_POP">팀명</label></dt><dd>
					<input type="hidden" id="F_BRC_POP" name="F_BRC_POP"/>
					<input type="text" id="F_BRMM_POP" name="F_BRMM_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPROVAL_REQ_ID_POP">결재요청ID</label></dt><dd>
					<input type="text" id="F_APPROVAL_REQ_ID_POP" name="F_APPROVAL_REQ_ID_POP" value="" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_OFT_POP">직급명</label></dt><dd>
					<input type="hidden" id="F_OFT_C_POP" name="F_OFT_C_POP"/>
					<input type="text" id="F_OFT_POP" name="F_OFT_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPLY_DIV_CD_POP">결재구분코드</label></dt><dd>
					<div class="select_box"><select id="F_APPLY_DIV_CD_POP" name="F_APPLY_DIV_CD_POP"><%=codeClass.getComboBoxByCodeList("APPLY_DIV_CD", "", true) %></select></div>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPROVAL_REQ_REASON_POP">결재요청사유</label></dt><dd>
					<input type="text" id="F_APPROVAL_REQ_REASON_POP" name="F_APPROVAL_REQ_REASON_POP" value=""/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_FIRST_USER_ID_POP">1차 결재</label></dt><dd>
					<input type="hidden" id="F_FIRST_USER_NM_POP" name="F_FIRST_USER_NM_POP" value=""/>
					<div class="select_box"><select id="F_FIRST_USER_ID_POP" name="F_FIRST_USER_ID_POP"><option value=""></option></select></div>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SECOND_USER_ID_POP">2차 결재</label></dt><dd>
					<input type="hidden" id="F_SECOND_USER_NM_POP" name="F_SECOND_USER_NM_POP" value=""/>
					<div class="select_box"><select id="F_SECOND_USER_ID_POP" name="F_SECOND_USER_ID_POP"><option value=""></option></select></div>
				</dd></dl>
			</form>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnSavePop" class="btn mcolor mid">저장</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){ $('#pop_close').on('click', function() { closePop(); }); });
</script>
<script src="${pageContext.request.contextPath}/js/approve/userAuthApplyPop.js"></script>
