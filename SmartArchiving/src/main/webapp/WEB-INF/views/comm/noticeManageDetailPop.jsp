<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "공지사항 상세"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">공지사항 상세</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<div class="form_input form_input--fill">
				<input type="hidden" id="F_SERIAL_NUMBER"/>
				<input type="hidden" id="F_REG_USER_CD"/>
				<input type="hidden" id="F_REG_DATE"/>
				<input type="hidden" id="F_FILE_URL"/>
				<dl class="input_box"><dt><label for="F_SUBJECT">제목</label></dt><dd>
					<input type="text" id="F_SUBJECT" readonly/>
				</dd></dl>
				<dl class="input_box input_box--grow"><dt><label for="F_SUBJECT_DETAIL">상세내용</label></dt><dd>
					<textarea id="F_SUBJECT_DETAIL" readonly rows="8"></textarea>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_USER_NM">등록자</label></dt><dd>
					<input type="text" id="F_USER_NM" readonly/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_FILE_NM">첨부파일</label></dt><dd class="disflex gap10">
					<input type="text" id="F_FILE_NM" readonly style="flex:1"/>
					<button type="button" id="btnDownPop" class="btn grey mid" style="display:none;">다운로드</button>
				</dd></dl>
			</div>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnDeletePop" class="btn red mid" style="display:none;">삭제</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/comm/noticeManageDetailPop.js"></script>
