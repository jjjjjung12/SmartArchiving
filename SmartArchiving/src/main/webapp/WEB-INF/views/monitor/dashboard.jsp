<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "대시보드"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong>
			<div class="select_box"><select id="C_SERVER_ID"><option value="0">서버 선택</option></select></div>
		</li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
	</ul>
</div>

<div class="mt32 dashboard-grid" style="display:grid;grid-template-columns:repeat(auto-fit,minmax(280px,1fr));gap:20px;">
	<div class="tbl_box" style="padding:20px;"><h3 class="mb16">에이전트 상태</h3><div id="chartAgent" style="min-height:200px;"></div></div>
	<div class="tbl_box" style="padding:20px;"><h3 class="mb16">작업 현황</h3><div id="chartJob" style="min-height:200px;"></div></div>
	<div class="tbl_box" style="padding:20px;"><h3 class="mb16">디스크 사용량</h3><div id="chartDisk" style="min-height:200px;"></div></div>
	<div class="tbl_box" style="padding:20px;"><h3 class="mb16">처리량</h3><div id="chartThroughput" style="min-height:200px;"></div></div>
</div>

<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/monitor/dashboard.js"></script>
