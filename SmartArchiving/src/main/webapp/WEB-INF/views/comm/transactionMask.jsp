<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "전문 마스킹"); %>
<div class="box_option">
	<ul class="option_list">
		
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
		<th>전문영문메타명</th>
		<th>전문한글메타명</th>
		<th>패턴</th>
		<th>수정/추가일시</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>전문 마스킹 정보 상세</h3></div>
		<form id="SetTranMaskForm" class="form_input detail-form">
			<input type="hidden" id="CRUD" name="CRUD" value="U">
			<input type="hidden" id="ROWID" name="ROWID" >
			<input type="hidden" id="F_USER_ID" value='<%=session.getAttribute("userid") %>' >
			<div class="form-group">
			<label for="F_TGRM_NM_ENG" class="col-sm-3 control-label">전문 영문 메타명</label>
			<div class="col-sm-8">
			<input type="text" id="F_TGRM_NM_ENG" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_TGRM_NM_ENG" class="col-sm-3 control-label">전문 한글 메타명</label>
			<div class="col-sm-8">
			<input type="text" id="F_TGRM_NM_KOR" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_MSK_PTTRN" class="col-sm-3 control-label">Masking 패턴</label>
			<div class="col-sm-8">
			<input type="text" id="F_MSK_PTTRN" >
			</div>
			</div>
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/comm/transactionMask.js"></script>
