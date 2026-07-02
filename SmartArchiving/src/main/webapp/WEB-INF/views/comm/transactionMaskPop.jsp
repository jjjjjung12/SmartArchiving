<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "마스킹 해제 요청"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">마스킹 해제 요청</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<form method="post" name="SetJobFormPop" id="SetJobFormPop" class="form_input form_input--fill">
				<input type="hidden" id="F_USER_ID_POP" name="F_USER_ID_POP" value="<c:out value='${param.userId}'/>"/>
				<dl class="input_box"><dt><label for="F_USER_CD_POP">사용자ID</label></dt><dd>
					<input type="text" id="F_USER_CD_POP" name="F_USER_CD_POP" value="<c:out value='${param.userCd}'/>" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_USER_NM_POP">성명</label></dt><dd>
					<input type="text" id="F_USER_NM_POP" name="F_USER_NM_POP" value="<c:out value='${param.userNm}'/>" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_TEAM_NM_POP">팀명</label></dt><dd>
					<input type="text" id="F_TEAM_NM_POP" name="F_TEAM_NM_POP" value="<c:out value='${param.teamNm}'/>" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPROVAL_REQ_ID_POP">결재요청ID</label></dt><dd>
					<input type="text" id="F_APPROVAL_REQ_ID_POP" name="F_APPROVAL_REQ_ID_POP" value="" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_GRADE_NM_POP">직급명</label></dt><dd>
					<input type="text" id="F_GRADE_NM_POP" name="F_GRADE_NM_POP" value="" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPLY_DIV_CD_POP">결재구분코드</label></dt><dd>
					<div class="select_box"><select id="F_APPLY_DIV_CD_POP" name="F_APPLY_DIV_CD_POP"><%=codeClass.getComboBoxByCodeList("APPLY_DIV_CD", "", true) %></select></div>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPROVAL_REQ_REASON_POP">결재요청사유</label></dt><dd>
					<input type="text" id="F_APPROVAL_REQ_REASON_POP" name="F_APPROVAL_REQ_REASON_POP" value=""/>
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
<script src="${pageContext.request.contextPath}/js/comm/transactionMaskPop.js"></script>
