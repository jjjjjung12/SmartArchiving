<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "권한 신청 상세"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">권한 신청 상세</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<div class="form_input form_input--fill">
				<input type="hidden" name="reqId" id="reqId" value="<c:out value='${param.reqId}'/>"/>
				<dl class="input_box"><dt><label for="F_USER_CD_POP">사용자ID</label></dt><dd>
					<input type="text" id="F_USER_CD_POP" name="F_USER_CD_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_USER_NM_POP">성명</label></dt><dd>
					<input type="text" id="F_USER_NM_POP" name="F_USER_NM_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_BRMM_POP">팀명</label></dt><dd>
					<input type="text" id="F_BRMM_POP" name="F_BRMM_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPROVAL_REQ_ID_POP">결재요청ID</label></dt><dd>
					<input type="text" id="F_APPROVAL_REQ_ID_POP" name="F_APPROVAL_REQ_ID_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_OFT_POP">직급명</label></dt><dd>
					<input type="text" id="F_OFT_POP" name="F_OFT_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_IP_ADDRESS_POP">등록 IP</label></dt><dd>
					<input type="text" id="F_IP_ADDRESS_POP" name="F_IP_ADDRESS_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_APPROVAL_REQ_REASON_POP">결재요청사유</label></dt><dd>
					<input type="text" id="F_APPROVAL_REQ_REASON_POP" name="F_APPROVAL_REQ_REASON_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_COM_CD_POP">조회법인</label></dt><dd>
					<input type="text" id="F_COM_CD_POP" name="F_COM_CD_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_EXPIRE_DATE_POP">만료일자</label></dt><dd>
					<input type="text" id="F_EXPIRE_DATE_POP" name="F_EXPIRE_DATE_POP" readonly/>
				</dd></dl>
			</div>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){ $('#pop_close').on('click', function() { closePop(); }); });
</script>
<script src="${pageContext.request.contextPath}/js/approve/userAuthApplyDetailPop.js"></script>
