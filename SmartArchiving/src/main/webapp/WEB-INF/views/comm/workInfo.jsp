<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "업무코드관리"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong><div class="select_box"><select id="C_SERVER_ID">
									<option value="0">상태를 선택하세요.</option>
									<%=codeClass.getComboBoxByServer("", true) %>									
								</select></div></li>
		<li><span class="ir_btn"><input type="radio" name="C_USE_YN" id="C_USE_YN_0" value="Y"/><label for="C_USE_YN_0">사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_1" value="N"/><label for="C_USE_YN_1">미사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_2" value="A"/><label for="C_USE_YN_2">전체</label></span></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
		<th>업무코드</th>
		<th>업무명</th>
		<th>계정</th>
		<th>사용</th>
		<th>설명</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>업무정보 상세</h3></div>
		<form id="SetWorkForm" class="form_input detail-form">
			<input type="hidden" id="CRUD" name="CRUD" value="C">
			<input type="hidden" id="ROWID" name="ROWID" >
			<div class="form-group">
			<label for="f_name" class="col-sm-3 control-label">업무코드</label>
			<div class="col-sm-8">
			<input type="text" id="F_WORK_CD" onKeyUp="this.value=this.value.toUpperCase();" placeholder="업무코드 ">
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">업무명</label>
			<div class="col-sm-8">
			<input type="text" id="F_WORK_NM" placeholder="업무명" >
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">계정</label>
			<div class="col-sm-8">
			<input type="text" id="F_ACCOUNT_CD" placeholder="계정" >
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">설명</label>
			<div class="col-sm-8">
			<textarea rows="4" data-stylesheet-url="assets/css/wysihtml5-color.css" name="F_DESCRIPTION" id="F_DESCRIPTION" placeholder="설명" ></textarea>
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">사용여부</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_USE_YN" name="F_USE_YN" value="Y">
			<label for="F_USE_YN">사용</label>
			<input type="radio" id="F_USE_YN" name="F_USE_YN" value="N">
			<label for="F_USE_YN">미사용</label>
			</div>
			</div>
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/comm/workInfo.js"></script>
