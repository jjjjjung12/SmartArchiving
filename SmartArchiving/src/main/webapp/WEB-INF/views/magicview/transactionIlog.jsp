<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="utilClass" class="com.archiving.util.UtilClass" scope="page"/>
<% request.setAttribute("pageTitle", "통합로그 조회"); request.setAttribute("menuFolded", Boolean.TRUE); %>
<span data-menu-folded="true" hidden></span>
<div class="box_option">
	<ul class="option_list">
		<li><strong>기간</strong>
			<div class="disflex align_center gap10">
				<div class="input_box date"><input type="text" id="F_SP_START_TR_YMD" maxlength="8" readonly/><button type="button" onclick="show_date_picker('#F_SP_START_TR_YMD')"><i class="fa-solid fa-calendar"></i></button></div>
				<input type="text" id="F_SP_TR_STIME" value="0000" maxlength="4" style="width:60px" onclick="fn_timePicker(this)"/>
				<span>~</span>
				<div class="input_box date"><input type="text" id="F_SP_END_TR_YMD" maxlength="8" readonly/><button type="button" onclick="show_date_picker('#F_SP_END_TR_YMD')"><i class="fa-solid fa-calendar"></i></button></div>
				<input type="text" id="F_SP_TR_ETIME" maxlength="4" style="width:60px" onclick="fn_timePicker(this)"/>
			</div>
		</li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="export" class="btn grey mid">Excel 저장</a></li>
	</ul>
</div>

<div class="box_option mt10">
	<ul class="option_list" style="flex-wrap:wrap;">
		<li><strong>채널</strong><input type="text" id="F_LOG_CR_INFO"/></li>
		<li><strong>화면ID</strong><input type="text" id="F_SP_SCRN_ID" maxlength="10"/></li>
		<li><strong>전문ID</strong><input type="text" id="F_SP_MESSAGE_ID"/></li>
		<li><strong>고객번호</strong><input type="text" id="F_SP_REG_CUST_NO"/></li>
		<li><strong>사용자ID</strong><input type="text" id="F_SP_USER_ID"/></li>
		<li><strong>전화번호</strong><input type="text" id="F_TEL_NO"/></li>
		<li><strong>출금계좌</strong><input type="text" id="F_TR_ACNO"/></li>
		<li><strong>입금계좌</strong><input type="text" id="F_IN_ACNO"/></li>
		<li><strong>전문일련</strong><input type="text" id="F_SP_MESSAGE_SER_NO"/></li>
		<li><strong>IP</strong><input type="text" id="F_SP_IN_IP"/></li>
		<li><strong>MAC</strong><input type="text" id="F_MAC"/></li>
	</ul>
</div>

<input type="hidden" id="corpValue" value="<c:out value='${param.corp}'/>"/>
<input type="hidden" id="serverValue" value="<c:out value='${param.server}'/>"/>
<select id="F_USER_LINE" style="display:none"><%=utilClass.searchUserFirstLine((String)session.getAttribute("usercd"))%></select>
<select id="F_USER_SECOND_LINE" style="display:none"><%=utilClass.searchUserSecondLine((String)session.getAttribute("usercd"))%></select>
<input type="hidden" id="H_CORP"/><input type="hidden" id="H_MESSAGE_SER_NO"/><input type="hidden" id="H_TRX_DTIME"/>
<input type="hidden" id="H_MESSSAGE_ID"/><input type="hidden" id="H_TRX_TRACKING_NO"/><input type="hidden" id="H_SP_TR_YMD_S"/>
<input type="hidden" id="H_SP_TR_YMD_E"/><input type="hidden" id="H_REQ_RES_TYPE"/>

<div class="mt32">
	<div class="flex_between">
		<span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span>
	</div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover">
			<thead><tr>
				<th>거래일시</th><th>채널명</th><th>화면ID</th><th>전문ID</th><th>요청구분</th><th>전문명</th>
				<th>고객번호</th><th>사용자ID</th><th>전화번호</th><th>출금계좌</th><th>입금계좌</th>
				<th>결과코드</th><th>전문일련번호</th><th>IP</th><th>법인구분</th>
			</tr></thead>
			<tbody id="gridBody"></tbody>
		</table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>

<script>
	var sessionsabun="<%=session.getAttribute("userid")%>", sessionUserCd="<%=session.getAttribute("usercd")%>", sessionUserNm="<%=session.getAttribute("username")%>";
	var sessionGroupId="<%=session.getAttribute("groupid")%>", sessionSsoId="<%=session.getAttribute("sso_id")!=null?session.getAttribute("sso_id"):""%>";
</script>
<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/magicview/transactionIlog.js"></script>
