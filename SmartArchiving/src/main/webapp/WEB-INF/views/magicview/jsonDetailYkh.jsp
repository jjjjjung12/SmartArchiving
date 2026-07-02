<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "거래 상세"); %>
<div class="popup_wrap popup_wrap--fill">
	<div class="popup_wrap__inner">
		<div class="pop_header">
			<h2 class="tit_pop">거래 상세</h2>
			<button type="button" class="close" title="창닫기" onclick="closePop(); return false;"><i class="fas fa-times"></i></button>
		</div>
		<div class="pop_body">
			<div class="pop_body__scroll">
    <input type="hidden" name="corpValue" id="corpValue" value="<c:out value='${param.corp}'/>"/>
    <input type="hidden" name="serverValue" id="serverValue" value="<c:out value='${param.server}'/>"/>
    <input type="hidden" name="user_cd_detail" id="user_cd_detail" value="<c:out value='${param.user_cd}'/>"/>
    <input type="hidden" name="gubun" id="gubun" value="<c:out value='${param.gubun}'/>"/>
	<div id="detail" style="width:100%;min-height:400px;overflow:hidden">    
		
		
			<div rel="body" class="main-sub-content" >								 
					<div class="col-lg-4">	
						
						<div class="popup_wrap_inner" data-collapsed="0"> 
							
							
								<div class="panel-options">
									<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a>
								</div>
							</div>							
							
							<div class="pop_body">
								<div id="detailTableDivID" class="tbl_box">
									<table class="tbl02 txthover"><thead><tr>
										<th>거래시간</th><th>전문일련번호</th><th>전문ID</th><th>구분</th>
									</tr></thead><tbody id="gridBodyDetail"></tbody></table>
									<div class="paging mt16"><div class="wr_page_detail"></div></div>
								</div>	
							</div>
							<div class="pop_body" style="display: none;"> <!-- 20241125 현업요청으로 숨김 -->
								<div id="metaTableDivID" class="tbl_box">
									<table class="tbl02 txthover"><thead><tr>
										<th>등록시간</th><th>수정시간</th><th>인터페이스ID</th><th>영문명</th><th>한글명</th>
									</tr></thead><tbody id="gridBodyMeta"></tbody></table>
									<div class="paging mt16"><div class="wr_page_meta"></div></div>
								</div>	
							</div>		
						</div>
					</div>
					
					<div id="detail-1area" class="col-lg-8 container-f1luid">	  
					 	<input type="hidden"  id="F_SP_MESSAGE_SER_NO_POP"  readonly>
					 	<input type="hidden"  id="F_SP_MESSAGE_ID_POP"  readonly>
					 	<input type="hidden" name="POP_REQ_RES_TYPE" id ="POP_REQ_RES_TYPE" value ="">
	
						<div class="popup_wrap_inner" data-collapsed="0">
							
							
								<div class="panel-options">
									<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a>
								</div>								
							</div>							
							
							<div class="pop_body">
								<div class="form_input">
									<div class="form-group">
										<label for="F_REG_CUST_NO_POP" class="col-lg-1 control-label">고객번호</label>
										<div class="col-lg-3">
											<input type="text"  id="F_REG_CUST_NO_POP"  readonly>
										</div>
										<label for="F_SP_USER_ID_POP" class="col-lg-1 control-label">사용자ID</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_USER_ID_POP"  readonly>
										</div>
										<label for="F_SP_IN_IP_POP" class="col-lg-1 control-label">IP</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_IN_IP_POP"  readonly>
										</div>
									</div>
								</div>
							</div> 
							
							<div class="pop_body" style="display: none;" id="CHK_MESSAGE">
								<div class="panel-options">
									<label for="MESSAGE"> </label>
								</div>	
							</div> 
							
							<div class="pop_body">
								<div class="panel-options">									
									<div id="keyValueTableDivID" class="tbl_box">
									   <table class="tbl02 txthover"><thead><tr>
									   	<th>순서</th><th>코드</th><th>코드명</th><th>값</th>
									   </tr></thead><tbody id="gridBodyKeyValue"></tbody></table>
									   <div class="paging mt16"><div class="wr_page_kv"></div></div>
								    </div>
								</div>	
									
							</div> 
							
						</div> 
					</div> 
						  
				</div>
		
			</div>
			<div class="tac mt20 popup_btn_div">
				<button type="button" id="pop_close" class="btn grey mid">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){ $('#pop_close').on('click', function() { closePop(); }); });
</script>
<script src="${pageContext.request.contextPath}/js/magicview/jsonDetailYkh.js"></script>
