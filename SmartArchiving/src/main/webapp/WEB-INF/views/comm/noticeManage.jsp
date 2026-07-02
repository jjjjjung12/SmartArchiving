<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "공지사항"); request.setAttribute("menuFolded", Boolean.TRUE); %>
<div class="box_option">
	<ul class="option_list">
		<li><a href="javascript:void(0)" id="btnQuery" class="btn mcolor">조회</a></li>
		<li><a href="javascript:void(0)" id="btnSave" class="btn mcolor mid">등록</a></li>
		<li><a href="javascript:void(0)" id="btnSearch" class="btn grey mid">조회</a></li>
	</ul>
</div>
<div class="mt32">
	<div class="flex_between"><span class="black">총 <strong class="pcolor" id="totalCnt">0</strong>건</span></div>
	<div class="tbl_box mt10">
		<table class="tbl02 txthover notice-boardlist textcut boardlist"><thead><tr>
			<th class="col-no">No.</th>
			<th class="col-subject tal_cut">제목</th>
			<th class="col-user">등록자</th>
			<th class="col-date">등록일자</th>
		</tr></thead><tbody id="gridBody"></tbody></table>
	</div>
	<div class="paging mt16"><div class="wr_page"></div></div>
</div>
<script>
	var sessionusercd="<%=session.getAttribute("usercd")%>", sessionGroupId="<%=session.getAttribute("groupid")%>";
</script>
<script src="${pageContext.request.contextPath}/js/comm/noticeManage.js"></script>
