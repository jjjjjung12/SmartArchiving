<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "Agent 관리"); %>
<div class="box_option">
	<ul class="option_list">
		<li><strong>서버</strong><div class="select_box"><select id="C_SERVER_ID">
									<option value="0">서버를 선택하세요.</option>
									<%=codeClass.getComboBoxByServer("", true) %>									
								</select></div></li>
		<li><span class="ir_btn"><input type="radio" name="C_USE_YN" id="C_USE_YN_0" value="Y"/><label for="C_USE_YN_0">사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_1" value="N"/><label for="C_USE_YN_1">미사용</label><input type="radio" name="C_USE_YN" id="C_USE_YN_2" value="A"/><label for="C_USE_YN_2">전체</label></span></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnAdd" class="btn mcolor mid">신규</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">저장</a></li>
		<li><a href="javascript:void(0)" id="btnDelete" class="btn red mid">삭제</a></li>
		<li><a href="javascript:void(0)" id="btnStart" class="btn grey mid">시작</a></li>
		<li><a href="javascript:void(0)" id="btnStop" class="btn grey mid">종료</a></li>
		<li><a href="javascript:void(0)" id="btnReStart" class="btn grey mid">재시작</a></li>
		<li><a href="javascript:void(0)" id="btnRefresh" class="btn grey mid">갱신</a></li>
	</ul>
</div>
<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
		<table class="tbl02 txthover"><thead><tr>
		<th>이름</th>
		<th>PORT</th>
		<th>계정</th>
		<th>설명</th>
		<th>동작</th>
		<th>사용</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>Agent정보 상세</h3></div>
		<form id="SetAgentForm" class="form_input">
			<input type="hidden" id="CRUD" value="U"/>
			<input type="hidden" id="ROWID"/>
			<dl class="input_box"><dt><label for="F_AGENT_ID">AgentID</label></dt><dd><input type="text" id="F_AGENT_ID"/></dd></dl>
			<dl class="input_box"><dt><label for="F_AGENT_NM">Agent명</label></dt><dd><input type="text" id="F_AGENT_NM"/></dd></dl>
			<dl class="input_box"><dt><label for="F_AGENT_PORT">AgentPort</label></dt><dd><input type="text" id="F_AGENT_PORT"/></dd></dl>
			<dl class="input_box"><dt><label for="F_ACCOUNT_CD">계정</label></dt><dd><input type="text" id="F_ACCOUNT_CD"/></dd></dl>
			<dl class="input_box"><dt><label for="F_PASSWORD">비밀번호</label></dt><dd><input type="password" id="F_PASSWORD"/></dd></dl>
			<dl class="input_box"><dt><label for="F_PATH">경로</label></dt><dd><input type="text" id="F_PATH"/></dd></dl>
			<dl class="input_box"><dt><label for="F_DESCRIPTION">설명</label></dt><dd><textarea id="F_DESCRIPTION" rows="3"></textarea></dd></dl>
			<dl class="input_box"><dt><label for="F_RUN_CD">종류</label></dt><dd><div class="select_box"><select id="F_RUN_CD"><%=codeClass.getComboBoxByCodeList("RUN_CD", "", true) %></select></div></dd></dl>
			<dl class="input_box"><dt>사용여부</dt><dd class="ir_btn">
				<input type="radio" name="F_USE_YN" id="F_USE_Y" value="Y"/><label for="F_USE_Y">사용</label>
				<input type="radio" name="F_USE_YN" id="F_USE_N" value="N"/><label for="F_USE_N">미사용</label>
			</dd></dl>
		</form>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/asset/agentInfo.js"></script>
