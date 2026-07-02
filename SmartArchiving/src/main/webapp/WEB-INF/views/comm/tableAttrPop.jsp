<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "컬럼 정보"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">컬럼 등록</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<div class="pop_body__scroll">
			<form id="SetAttrFormPop" class="form_input form_input--fill">
				<input type="hidden" id="CRUD_POP" value="AC"/>
				<input type="hidden" id="F_TABLE_ID_POP"/>
				<dl class="input_box"><dt><label for="F_ATTR_CD_POP">컬럼코드</label></dt><dd>
					<input type="text" id="F_ATTR_CD_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_ATTR_NM_POP">컬럼명</label></dt><dd>
					<input type="text" id="F_ATTR_NM_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_ATTR_TYPE_CD_POP">타입</label></dt><dd>
					<div class="select_box"><select id="F_ATTR_TYPE_CD_POP"><%=codeClass.getComboBoxByCodeList("ATTR_TYPE_CD", "", true) %></select></div>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_ATTR_SIZE_POP">길이</label></dt><dd>
					<input type="text" id="F_ATTR_SIZE_POP" inputmode="numeric" autocomplete="off" maxlength="9"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_ATTR_ORDER_POP">순서</label></dt><dd>
					<input type="text" id="F_ATTR_ORDER_POP" inputmode="numeric" autocomplete="off" maxlength="9"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_WHERE_INDEX_POP">조건순서</label></dt><dd>
					<input type="text" id="F_WHERE_INDEX_POP" inputmode="numeric" autocomplete="off" maxlength="9"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_OUTPUT_INDEX_POP">출력순서</label></dt><dd>
					<input type="text" id="F_OUTPUT_INDEX_POP" inputmode="numeric" autocomplete="off" maxlength="9"/>
				</dd></dl>
				<dl class="input_box"><dt>Null</dt><dd class="ir_btn">
					<input type="radio" name="F_ATTR_NULL_YN_POP" id="F_ATTR_NULL_Y_POP" value="Y"/><label for="F_ATTR_NULL_Y_POP">예</label>
					<input type="radio" name="F_ATTR_NULL_YN_POP" id="F_ATTR_NULL_N_POP" value="N"/><label for="F_ATTR_NULL_N_POP">아니오</label>
				</dd></dl>
				<dl class="input_box"><dt>날짜컬럼</dt><dd class="ir_btn">
					<input type="radio" name="F_DATE_TYPE_YN_POP" id="F_DATE_Y_POP" value="Y"/><label for="F_DATE_Y_POP">예</label>
					<input type="radio" name="F_DATE_TYPE_YN_POP" id="F_DATE_N_POP" value="N"/><label for="F_DATE_N_POP">아니오</label>
				</dd></dl>
				<dl class="input_box"><dt>시간컬럼</dt><dd class="ir_btn">
					<input type="radio" name="F_TIME_TYPE_YN_POP" id="F_TIME_Y_POP" value="Y"/><label for="F_TIME_Y_POP">예</label>
					<input type="radio" name="F_TIME_TYPE_YN_POP" id="F_TIME_N_POP" value="N"/><label for="F_TIME_N_POP">아니오</label>
				</dd></dl>
			</form>
			</div>
			<div class="tac popup_btn_div">
				<button type="button" id="btnSavePop" class="btn mcolor mid">저장</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/comm/tableAttrPop.js"></script>
