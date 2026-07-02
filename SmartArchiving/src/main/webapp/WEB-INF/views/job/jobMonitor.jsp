<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "사용 라이선스"); %>
<div class="mt32">
	<div class="license-panel">
		<h3>라이선스 사용 현황</h3>
		<div class="license-panel__row">
			<strong>전체</strong>
			<span><span class="value" id="F_TOTAL_AMT">-</span><span class="unit">TB</span></span>
		</div>
		<div class="license-panel__row">
			<strong>사용</strong>
			<span><span class="value" id="F_USED_AMT">-</span><span class="unit">TB</span></span>
		</div>
		<div class="license-panel__row">
			<strong>여유</strong>
			<span><span class="value" id="F_FREE_AMT">-</span><span class="unit">TB</span></span>
		</div>
		<div class="license-panel__row">
			<strong>상태</strong>
			<span class="value" id="F_STATUS">-</span>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/job/jobMonitor.js"></script>
