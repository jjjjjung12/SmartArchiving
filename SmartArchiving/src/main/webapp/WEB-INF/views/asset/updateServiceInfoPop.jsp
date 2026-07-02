<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "서비스 정보 수정"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop" id="main_title">서비스 정보 수정</h2>
			<span id="sub_title" class="sr-only">서비스 정보 수정</span>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<form method="post" name="SetServiceForm" id="SetServiceForm" class="form_input form_input--fill validate">
				<input type="hidden" name="groupId" id="groupId" value="<c:out value='${param.groupId}'/>"/>
				<input type="hidden" name="serviceId" id="serviceId" value="<c:out value='${param.serviceId}'/>"/>
				<input type="hidden" name="selGubun" id="selGubun" value="<c:out value='${param.selGubun}'/>"/>
				<input type="hidden" name="delYn" id="delYn" value="<c:out value='${param.delYn}'/>"/>
				<dl class="input_box"><dt><label for="F_APPLICATION_GROUP_ID">채널 코드</label></dt><dd>
					<input type="text" id="F_APPLICATION_GROUP_ID" name="F_APPLICATION_GROUP_ID"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SERVICE_ID">서비스 ID</label></dt><dd>
					<input type="text" id="F_SERVICE_ID" name="F_SERVICE_ID"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_SEL_GUBUN">SEL 구분</label></dt><dd>
					<input type="text" id="F_SEL_GUBUN" name="F_SEL_GUBUN"/>
				</dd></dl>
				<dl class="input_box"><dt><label for="F_DEL_YN">삭제 여부</label></dt><dd>
					<input type="text" id="F_DEL_YN" name="F_DEL_YN"/>
				</dd></dl>
			</form>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="btnUpdateSave" class="btn mcolor mid">저장</button>
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){ $('#pop_close').on('click', function() { closePop(); }); });
</script>
<script src="${pageContext.request.contextPath}/js/asset/updateServiceInfoPop.js"></script>
