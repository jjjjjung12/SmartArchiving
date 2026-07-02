<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String serverIp = request.getLocalAddr();
String sso_id = (String) session.getAttribute("sso_id");
%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<jsp:useBean id="utilClass" class="com.archiving.util.UtilClass" scope="page"/>
<% request.setAttribute("pageTitle", "사용자 권한 신청");%>
<div class="box_option">
	<ul class="option_list">
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor">결재신청</a></li>
		<li><span id="div_text" style="display:none;color:#e67e22;">결재 신청중</span></li>
		<li style="margin-left:auto;"><a href="${pageContext.request.contextPath}/login" id="login" class="btn grey mid">로그인</a></li>
	</ul>
</div>

<div class="mt32">
	<div class="tbl_box" style="padding:24px;">
		<div class="form_input">
			<input type="hidden" id="corpValue"/>
			<dl class="input_box"><dt><label for="F_USER_CD">사용자ID</label></dt><dd class="disflex gap10" style="flex-wrap:wrap;">
				<input type="text" id="F_USER_CD" value="<%=sso_id!=null?sso_id:""%>" readonly style="width:160px"/>
				<strong>성명</strong><input type="text" id="F_USER_NM" readonly style="width:120px"/>
				<strong>팀명</strong><input type="hidden" id="F_BRC"/><input type="text" id="F_BRMM" readonly style="width:160px"/>
			</dd></dl>
			<dl class="input_box"><dt>결재요청/직급</dt><dd class="disflex gap10" style="flex-wrap:wrap;">
				<input type="text" id="F_APPROVAL_REQ_ID" readonly style="width:160px" placeholder="결재요청ID"/>
				<input type="hidden" id="F_OFT_C"/><input type="text" id="F_OFT" readonly style="width:120px" placeholder="직급명"/>
				<strong>등록 IP</strong><input type="text" id="F_IP_ADDRESS" style="width:160px"/>
			</dd></dl>
			<dl class="input_box"><dt>결재구분/사유</dt><dd class="disflex gap10" style="flex-wrap:wrap;">
				<div class="select_box"><select id="F_APPLY_DIV_CD"><%=codeClass.getComboBoxByCodeList("APPLY_DIV_CD", "", true) %></select></div>
				<input type="text" id="F_APPROVAL_REQ_REASON" placeholder="결재요청사유" style="flex:1;min-width:200px"/>
			</dd></dl>
			<dl class="input_box"><dt>조회법인</dt><dd class="ir_btn">
				<input type="radio" name="F_COM" id="F_COM_1" value="1"/><label for="F_COM_1">은행</label>
				<input type="radio" name="F_COM" id="F_COM_2" value="2"/><label for="F_COM_2">상호</label>
				<input type="radio" name="F_COM" id="F_COM_3" value="3"/><label for="F_COM_3">은행/상호</label>
			</dd></dl>
			<dl class="input_box"><dt><label for="F_EXPIRE_DATE">만료일자</label></dt><dd>
				<div class="input_box date"><input type="text" id="F_EXPIRE_DATE" readonly/><button type="button" onclick="show_date_picker('#F_EXPIRE_DATE')"><i class="fa-solid fa-calendar"></i></button></div>
			</dd></dl>
			<dl class="input_box"><dt>1차 결재</dt><dd class="disflex gap10">
				<div class="select_box" style="flex:1"><select id="F_FIRST_USER_ID"><option value=""></option><%=utilClass.searchUserFirstLine((String)sso_id)%></select></div>
				<input type="text" id="F_FIRST_LINE" readonly style="width:200px"/>
			</dd></dl>
			<dl class="input_box"><dt>2차 결재</dt><dd class="disflex gap10">
				<div class="select_box" style="flex:1"><select id="F_SECOND_USER_ID"><option value=""></option><%=utilClass.searchUserSecondLine((String)sso_id)%></select></div>
				<input type="text" id="F_SECOND_LINE" readonly style="width:200px"/>
			</dd></dl>
		</div>
	</div>
</div>

<script>
var serverIP = "<%= serverIp%>";
if(serverIP.indexOf("16") > -1) $('#corpValue').val("BL");
else $('#corpValue').val("SB");
$(function(){
	$("#F_EXPIRE_DATE").datepicker({ dateFormat:'yymmdd', changeYear:true, changeMonth:true, showMonthAfterYear:true });
});
</script>
<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/auth/userAuthApplyLogin.js"></script>
