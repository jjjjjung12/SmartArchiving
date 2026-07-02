<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "Agent Monitor"); %>
<div class="box_option">
	<ul class="option_list">
		<li><span class="ir_btn">
			<input type="radio" name="C_USE_YN" id="C_USE_YN_0" value="Y"/><label for="C_USE_YN_0">사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_YN_1" value="N"/><label for="C_USE_YN_1">미사용</label>
			<input type="radio" name="C_USE_YN" id="C_USE_YN_2" value="A" checked/><label for="C_USE_YN_2">전체</label>
		</span></li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnRefresh" class="btn grey mid">갱신</a></li>
	</ul>
</div>

<div class="master-detail mt32">
	<div class="master-panel">
		<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
		<div class="tbl_box mt10">
			<table class="tbl02 txthover"><thead><tr>
				<th>서버ID</th><th>서버명</th><th>서버IP</th><th>AGENT_ID</th><th>에이전트명</th>
				<th>PORT</th><th>HEALTH</th><th>HEALTH_URL</th><th>CODE</th><th>RUN</th><th>사용</th><th>설명</th>
			</tr></thead><tbody id="gridBody"></tbody></table>
		</div>
		<div class="paging mt16"><div class="wr_page"></div></div>
	</div>
	<div class="detail-panel">
		<div class="tit mb16"><h3>상세 정보</h3></div>
		<div class="form_input">
			<dl class="input_box"><dt><label for="D_SERVER">서버</label></dt><dd><input type="text" id="D_SERVER" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="D_AGENT">에이전트</label></dt><dd><input type="text" id="D_AGENT" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="D_HEALTH">HEALTH</label></dt><dd><input type="text" id="D_HEALTH" readonly/></dd></dl>
			<dl class="input_box"><dt><label for="D_HEALTH_URL">HEALTH URL</label></dt><dd><input type="text" id="D_HEALTH_URL" readonly/></dd></dl>
			<dl class="input_box input_box--full"><dt><label for="D_HEALTH_BODY">응답 본문</label></dt><dd><textarea id="D_HEALTH_BODY" rows="6" readonly></textarea></dd></dl>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/common/common-js.js"></script>
<script src="${pageContext.request.contextPath}/js/asset/agentMonitor.js"></script>
