<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "사용자 조회"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>사용자명</strong><input type="text" id="C_USER_NM" style="width:200px;" placeholder="이름또는 ID를 입력하세요."/></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
		<li><a href="javascript:void(0)" id="export" class="btn grey mid">Excel 저장</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
		<th>사용자ID</th>
		<th>사용자명</th>
		<th>팀명</th>
		<th>그룹명</th>
		<th>등록 IP</th>
		<th>만료일자</th>
		<th>최근접속일</th>
		<th>사용여부</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>사용자정보</h3></div>
		<form id="SetUserForm" class="form_input detail-form">
			<input type="hidden" id="CRUD" name="CRUD" value="U">
			<input type="hidden" id="ROWID" name="ROWID" >
			<input type="hidden" id="F_EMAIL" name="F_EMAIL" >
			<input type="hidden" id="F_TELEPHONE" name="F_TELEPHONE" >
			<div class="form-group">
			<label for="f_sabun" class="col-sm-3 control-label">사용자번호</label>
			<div class="col-sm-8">
			<input type="text" id="F_USER_CD" name="F_USER_CD" >
			<input type="hidden" id="F_USER_ID" name="F_USER_ID" >
			</div>
			</div>
			<div class="form-group">
			<label for="f_name" class="col-sm-3 control-label">이름</label>
			<div class="col-sm-8">
			<input type="text" id="F_USER_NM" name="F_USER_NM" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_TELEPHONE" class="col-sm-3 control-label">팀코드</label>
			<div class="col-sm-8">
			<input type="text" id="F_BRC" name="F_BRC" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_TELEPHONE" class="col-sm-3 control-label">팀명</label>
			<div class="col-sm-8">
			<input type="text" id="F_BRNM" name="F_BRNM" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_EMAIL" class="col-sm-3 control-label">등록 IP</label>
			<div class="col-sm-8">
			<input type="text" id="F_IP_ADDRESS" name="F_IP_ADDRESS" >
			</div>
			</div>
			<div class="form-group">
			<label for="F_PASSWORD" class="col-sm-3 control-label">비밀번호</label>
			<div class="col-sm-8">
			<input type="password" id="F_PASSWORD" name="F_PASSWORD" >
			<input type="hidden" id="F_PASSWORDORG" name="F_PASSWORDORG" >
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">사용여부</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_USE_YN" name="F_USE_YN" value="Y">
			<label for="F_USE_YN">사용</label>
			<input type="radio" id="F_USE_YN2" name="F_USE_YN" value="N">
			<label for="F_USE_YN">미사용</label>
			<input type="hidden" name="F_USE_YN_VAL" id="F_USE_YN_VAL" >
			</div>
			</div>
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/user/userInfo.js"></script>
