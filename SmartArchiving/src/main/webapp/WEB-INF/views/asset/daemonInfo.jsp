<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "데몬 관리"); %>
<div class="box_option">
	<ul class="option_list">
		<li>
			<div class="select_box"><select id="C_SEARCH_COL" title="검색 컬럼">
				<option value="">전체</option>
				<option value="DAEMON_CD">코드</option>
				<option value="DAEMON_NM">이름</option>
				<option value="DAEMON_START_PATH">시작경로</option>
				<option value="DAEMON_STOP_PATH">종료경로</option>
				<option value="DAEMON_RESTART_PATH">재시작경로</option>
				<option value="DAEMON_DESC">설명</option>
			</select></div>
		</li>
		<li><input type="text" id="C_SEARCH_VAL" placeholder="검색어 입력" maxlength="100"/></li>
		<li><span class="ir_btn"><input type="radio" name="C_USE_YN" id="C_USE_YN_0" value="Y"/><label for="C_USE_YN_0">사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_1" value="N"/><label for="C_USE_YN_1">미사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_2" value="A"/><label for="C_USE_YN_2">전체</label></span></li>
		<li style="margin-left:auto"><a href="javascript:void(0)" id="btnQuery" class="btn mcolor mid">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
		<li><a href="javascript:void(0)" id="btnStart" class="btn grey mid">시작</a></li>
		<li><a href="javascript:void(0)" id="btnStop" class="btn grey mid">종료</a></li>
		<%-- <li><a href="javascript:void(0)" id="btnReStart" class="btn grey mid">재시작</a></li> --%>
	</ul>
</div>
<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover daemon-boardlist textcut boardlist"><thead><tr>
			<th class="col-chk"><input type="checkbox" id="chkAllDaemon" title="현재 페이지 전체 선택"/></th>
			<th class="col-cd">코드</th>
			<th class="col-nm tal_cut">이름</th>
			<th class="col-run">RUN</th>
			<th class="col-pid">PID</th>
			<th class="col-port">PORT</th>
			<th class="col-health">HEALTH</th>
			<th class="col-start-path tal_cut">데몬시작경로</th>
			<th class="col-stop-path tal_cut">데몬종료경로</th>
			<th class="col-restart-path tal_cut">데몬재시작경로</th>
			<th class="col-desc tal_cut">설명</th>
			<th class="col-stat">상태</th>
			<th class="col-restart-yn">재시작</th>
			<th class="col-use-yn">사용여부</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>
<script>
	var daemonSessionUserId = '<%=session.getAttribute("userid") != null ? session.getAttribute("userid") : ""%>';
</script>
<script src="${pageContext.request.contextPath}/js/asset/daemonInfo.js"></script>
