<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "통계로그 서비스 대량등록"); %>
<div class="box_option box_option--actions-right">
	<ul class="option_list">
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor mid">조회</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnSaveExcel" class="btn mcolor mid">파일 업로드</a></li>
	</ul>
</div>

<form id="SetBulkForm" enctype="multipart/form-data" style="display:none">
	<input type="hidden" id="bulkUniqFileName" name="bulkUniqFileName"/>
	<input type="file" id="btnImpExcel" name="btnImpExcel" accept=".csv" style="display:none"/>
</form>

<div class="mt32 service-info-admin-panel">
	<div class="flex_between list-toolbar">
		<span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span>
		<div class="list-toolbar__actions">
			<a href="javascript:void(0)" id="exportSample" class="btn grey mid">샘플파일 다운로드</a>
		</div>
	</div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover service-info-boardlist textcut boardlist"><thead><tr>
			<th class="col-channel">채널구분</th>
			<th class="col-service-id">서비스 ID</th>
			<th class="col-sel-gubun">조회/기타 구분</th>
			<th class="col-del-yn">삭제여부</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>

<script src="${pageContext.request.contextPath}/js/asset/serviceInfoAdmin.js"></script>
