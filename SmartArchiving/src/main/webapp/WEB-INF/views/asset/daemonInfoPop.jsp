<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "데몬 정보"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">데몬 등록</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<form id="SetDaemonFormPop" class="form_input form_input--fill">
				<input type="hidden" id="CRUD_POP" value="C"/>
				<input type="hidden" id="F_DAEMON_ID_POP"/>
				<input type="hidden" id="F_USER_ID_POP" value="<%=session.getAttribute("userid")%>"/>
				<input type="hidden" id="F_DAEMON_STAT_CD_POP" value="1"/>
				<input type="hidden" id="F_DAEMON_RESTART_YN_POP" value=""/>
				<dl class="input_box"><dt><label for="F_DAEMON_CD_POP">Daemon 코드</label></dt><dd>
					<input type="text" id="F_DAEMON_CD_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DAEMON_NM_POP">Daemon명</label></dt><dd>
					<input type="text" id="F_DAEMON_NM_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DAEMON_START_PATH_POP">Daemon Start</label></dt><dd>
					<input type="text" id="F_DAEMON_START_PATH_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DAEMON_STOP_PATH_POP">Daemon Stop</label></dt><dd>
					<input type="text" id="F_DAEMON_STOP_PATH_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DAEMON_RESTART_PATH_POP">Daemon Restart</label></dt><dd>
					<input type="text" id="F_DAEMON_RESTART_PATH_POP"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DAEMON_DESC_POP">설명</label></dt><dd>
					<textarea id="F_DAEMON_DESC_POP" rows="3"></textarea>
				</dd></dl>
				<dl class="input_box"><dt>사용여부</dt><dd class="ir_btn">
					<input type="radio" name="F_USE_YN_POP" id="F_USE_Y_POP" value="Y"/><label for="F_USE_Y_POP">예</label>
					<input type="radio" name="F_USE_YN_POP" id="F_USE_N_POP" value="N"/><label for="F_USE_N_POP">아니오</label>
				</dd></dl>
			</form>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnSavePop" class="btn mcolor mid">저장</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/asset/daemonInfoPop.js"></script>
