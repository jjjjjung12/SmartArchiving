<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "Excel 저장"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">Excel 저장</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<div class="form_input form_input--fill">
				<input type="hidden" id="F_PAGE_NAME_POP_EXCEL" value="<c:out value='${param.pageName}'/>"/>
				<input type="hidden" id="F_USER_ID_POP_EXCEL" value="<c:out value='${param.userId}'/>"/>
				<input type="hidden" id="F_USER_CD_POP_EXCEL" value="<c:out value='${param.userCd}'/>"/>
				<input type="hidden" id="F_USER_NM_POP_EXCEL" value="<c:out value='${param.userNm}'/>"/>
				<input type="hidden" id="F_BRC_POP_EXCEL" value=""/>
				<input type="hidden" id="F_BRMM_POP_EXCEL" value=""/>
				<input type="hidden" id="F_REG_DATE_POP_EXCEL" value=""/>
				<input type="hidden" id="gubun" value="<c:out value='${param.gubun}'/>"/>
				<dl class="input_box"><dt><label for="F_DOWN_CD_POP_EXCEL">요청구분</label></dt><dd>
					<div class="select_box"><select id="F_DOWN_CD_POP_EXCEL"><%=codeClass.getComboBoxByCodeList("DOWNLOAD_CD", "", true) %></select></div>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_REQ_NUM_POP_EXCEL">요청/영장번호</label></dt><dd>
					<input type="text" id="F_REQ_NUM_POP_EXCEL" value=""/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_REQ_REASON_POP_EXCEL">요청사유</label></dt><dd>
					<input type="text" id="F_REQ_REASON_POP_EXCEL" readonly/>
				</dd></dl>
			</div>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnSavePop" class="btn mcolor mid">요청</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){
	$("#F_REG_DATE_POP_EXCEL").datepicker({ dateFormat:'yymmdd', changeYear:true, changeMonth:true, showMonthAfterYear:true });
	$('#F_REG_DATE_POP_EXCEL').datepicker('setDate', 'today');
	$('#pop_close').on('click', function() { closePop(); });
});
</script>
<script src="${pageContext.request.contextPath}/js/comm/downloadReqPop.js"></script>
