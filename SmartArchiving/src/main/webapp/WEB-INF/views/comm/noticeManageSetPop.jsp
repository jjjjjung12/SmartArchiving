<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "공지사항 등록"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">공지사항 등록</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<form method="post" name="SetJobFormPop" id="SetJobFormPop" class="form_input form_input--fill" enctype="multipart/form-data">
				<input type="hidden" name="F_USER_CD" id="F_USER_CD" value="<c:out value='${param.usercd}'/>"/>
				<input type="hidden" name="F_REG_START_DATE_POP" id="F_REG_START_DATE_POP" value=""/>
				<input type="hidden" name="F_REG_END_DATE_POP" id="F_REG_END_DATE_POP" value=""/>
				<input type="hidden" id="F_REG_DATE_POP" name="F_REG_DATE_POP"/>
				<dl class="input_box"><dt><label for="F_SUBJECT_POP">제목</label></dt><dd>
					<input type="text" id="F_SUBJECT_POP" name="F_SUBJECT_POP" maxlength="50" placeholder="공지 제목을 입력하세요"/>
				</dd></dl>
				<dl class="input_box input_box--grow"><dt><label for="F_SUBJECT_DETAIL_POP">상세내용</label></dt><dd>
					<textarea id="F_SUBJECT_DETAIL_POP" name="F_SUBJECT_DETAIL_POP" placeholder="공지 내용을 입력하세요"></textarea>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DOWNLOAD_POP">첨부파일</label></dt><dd>
					<input type="file" id="F_DOWNLOAD_POP" name="F_DOWNLOAD_POP"/>
				</dd></dl>
			</form>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnSavePop" class="btn mcolor mid">등록</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/comm/noticeManageSetPop.js"></script>
