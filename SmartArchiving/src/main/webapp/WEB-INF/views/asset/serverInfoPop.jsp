<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "서버 정보"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">서버 등록</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<form id="SetServerFormPop" class="form_input form_input--fill">
				<input type="hidden" id="CRUD_POP" value="C"/>
				<dl class="input_box"><dt><label for="F_SERVER_ID_POP">서버ID</label></dt><dd>
					<input type="text" id="F_SERVER_ID_POP" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SERVER_NM_POP">서버명</label></dt><dd>
					<input type="text" id="F_SERVER_NM_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SERVER_IP_POP">서버IP</label></dt><dd>
					<input type="text" id="F_SERVER_IP_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SERVER_CLASS_CD_POP">종류</label></dt><dd>
					<div class="select_box"><select id="F_SERVER_CLASS_CD_POP"><%=codeClass.getComboBoxByCodeList("SERVER_CLASS_CD", "", true) %></select></div>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SERVER_DESC_POP">설명</label></dt><dd>
					<textarea id="F_SERVER_DESC_POP" rows="3"></textarea>
				</dd></dl>
				<dl class="input_box"><dt>사용여부</dt><dd class="ir_btn">
					<input type="radio" name="F_USE_YN_POP" id="F_USE_Y_POP" value="Y"/><label for="F_USE_Y_POP">사용</label>
					<input type="radio" name="F_USE_YN_POP" id="F_USE_N_POP" value="N"/><label for="F_USE_N_POP">미사용</label>
				</dd></dl>
			</form>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnSavePop" class="btn mcolor mid">저장</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/asset/serverInfoPop.js"></script>
