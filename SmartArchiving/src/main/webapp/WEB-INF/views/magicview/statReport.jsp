<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="codeClass" class="com.archiving.util.CodeClass" scope="page"/>
<% request.setAttribute("pageTitle", "서비스통계"); request.setAttribute("menuFolded", Boolean.TRUE); %>
<span data-menu-folded="true" hidden></span>

<div class="box_option">
	<ul class="option_list" style="flex-wrap:wrap;">
		<li><strong>채널</strong>
			<select id="F_MEDIA_CATEGORY_CD" name="F_MEDIA_CATEGORY_CD">
				<option value="">채널을 선택하세요.</option>
				<%=codeClass.getComboBoxByCodeList("MEDIA_CATEGORY_CD", "", true) %>
			</select>
		</li>
		<li><strong>거래구분</strong>
			<select id="F_SEL_GUBUN" name="F_SEL_GUBUN">
				<option value="">전체</option>
				<%=codeClass.getComboBoxByCodeList("ILOG_SEL_GUBUN", "", true) %>
			</select>
		</li>
		<li><strong>집계포함여부</strong>
			<select id="F_DEL_YN" name="F_DEL_YN">
				<option value="">전체</option>
				<option value="Y">여</option>
				<option value="N">부</option>
			</select>
		</li>
		<li><strong>기간</strong>
			<div class="disflex align_center gap10">
				<div class="input_box date"><input type="text" id="F_SP_START_TR_YMD" maxlength="8" readonly/><button type="button" onclick="show_date_picker('#F_SP_START_TR_YMD')"><i class="fa-solid fa-calendar"></i></button></div>
				<span>~</span>
				<div class="input_box date"><input type="text" id="F_SP_END_TR_YMD" maxlength="8" readonly/><button type="button" onclick="show_date_picker('#F_SP_END_TR_YMD')"><i class="fa-solid fa-calendar"></i></button></div>
			</div>
		</li>
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="export" class="btn grey mid">Excel 저장</a></li>
	</ul>
</div>

<input type="hidden" id="serverValue" value="<c:out value='${param.server}'/>"/>

<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover">
			<thead>
				<tr>
					<th>채널명</th>
					<th>조회구분</th>
					<th>집계포함여부</th>
					<th>성공건수</th>
					<th>실패건수</th>
				</tr>
			</thead>
			<tbody id="gridBody"></tbody>
		</table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>

<script src="${pageContext.request.contextPath}/js/magicview/statReport.js"></script>