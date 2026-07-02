<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "테이블 정보"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">테이블 등록</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<form id="SetTableFormPop" class="form_input form_input--fill">
				<input type="hidden" id="CRUD_POP" value="C"/>
				<input type="hidden" id="F_SERVER_ID_POP"/>
				<dl class="input_box"><dt><label for="F_TABLE_ID_POP">테이블 ID</label></dt><dd>
					<input type="text" id="F_TABLE_ID_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_TABLE_CD_POP">영문명</label></dt><dd>
					<input type="text" id="F_TABLE_CD_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_TABLE_NM_POP">한글명</label></dt><dd>
					<input type="text" id="F_TABLE_NM_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_TABLE_JOIN_NM_POP">별칭(조인)</label></dt><dd>
					<input type="text" id="F_TABLE_JOIN_NM_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SAVE_PREQ_CD_POP">보관주기</label></dt><dd class="disflex gap10">
					<div class="select_box"><select id="F_SAVE_PREQ_CD_POP"><%=codeClass.getComboBoxByCodeList("SAVE_PREQ_CD", "", true) %></select></div>
					<input type="text" id="F_SAVE_PREQ_POP" placeholder="주기값" style="flex:1"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_EXP_PREQ_CD_POP">Export주기</label></dt><dd class="disflex gap10">
					<div class="select_box"><select id="F_EXP_PREQ_CD_POP"><%=codeClass.getComboBoxByCodeList("EXP_PREQ_CD", "", true) %></select></div>
					<input type="text" id="F_EXP_PREQ_POP" placeholder="주기값" style="flex:1"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DESCRIPTION_POP">설명</label></dt><dd>
					<textarea id="F_DESCRIPTION_POP" rows="2"></textarea>
				</dd></dl>
				<dl class="input_box"><dt>사용여부</dt><dd class="ir_btn">
					<input type="radio" name="F_USE_YN_POP" id="F_USE_Y_POP" value="Y"/><label for="F_USE_Y_POP">예</label>
					<input type="radio" name="F_USE_YN_POP" id="F_USE_N_POP" value="N"/><label for="F_USE_N_POP">아니오</label>
				</dd></dl>
			</form>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnSavePop" class="btn mcolor mid">저장</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/comm/tableInfoPop.js"></script>
