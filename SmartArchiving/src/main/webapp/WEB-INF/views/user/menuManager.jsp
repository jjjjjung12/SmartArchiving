<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "메뉴 관리"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>사용여부</strong><div class="select_box"><select id="C_USE_YN">
									<option selected value="">
									사용(Y,N)
									</option>
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select></div></li>
		<li><strong>MENU NM</strong><input type="text" id="C_MENU_NM" style="width:100px;" placeholder="메뉴명"/></li>
		<li><strong>URL</strong><input type="text" id="C_URL" style="width:100px;" placeholder="URL"/></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
		</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>메뉴 상세</h3></div>
		<form id="SetServerForm" class="form_input detail-form">
			<input type="hidden" id="CRUD" name="CRUD" value="C">
			<input type="hidden" id="ROWID" name="ROWID" >
			<div class="form-group">
			<label for="f_name" class="col-sm-3 control-label">메뉴ID</label>
			<div class="col-sm-8">
			<input type="text" id="F_MENU_ID" readonly>
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">메뉴Code</label>
			<div class="col-sm-8">
			<input type="text" id="F_MENU_CD" maxlength="5">
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">메뉴명</label>
			<div class="col-sm-8">
			<input type="text" id="F_MENU_NM" maxlength="50" >
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">URL</label>
			<div class="col-sm-8">
			<input type="text" id="F_MENU_URL" maxlength="100">
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">설명</label>
			<div class="col-sm-8">
			<textarea rows="4" data-stylesheet-url="assets/css/wysihtml5-color.css" name="F_MENU_DESC" id="F_MENU_DESC" maxlength="1000" ></textarea>
			</div>
			</div>
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">메뉴순서</label>
			<div class="col-sm-8">
			<input type="text" id="F_MENU_ORDER" maxlength="100">
			</div>
			</div>
			<div class="form-group">
			<label for="f_status" class="col-sm-3 control-label">사용(Y,N)</label>
			<div class="col-sm-8" >
			<input type="radio" id="F_USE_YN1" name="F_USE_YN" value="Y" >
			<label for="F_USE_YN">예</label>
			<input type="radio" id="F_USE_YN2" name="F_USE_YN" value="N" checked>
			<label for="F_USE_YN">아니요</label>
			</div>
			</div>
			<!-- 20240904 I_CLASS_ID 추가 -->
			<div class="form-group">
			<label for="f_orgNm" class="col-sm-3 control-label">아이콘 클래스</label>
			<div class="col-sm-8">
			<input type="text" id="F_ICON_CLASS_ID" maxlength="30">
			</div>
			</div>
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/user/menuManager.js"></script>
