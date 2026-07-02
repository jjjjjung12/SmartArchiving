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
	<div id="detail" style="width:100%;min-height:400px;overflow:hidden">    
		
		
		<div rel="body" id="LOAD" style="padding:10px;line-height:150%">        <font color="#FF99FF">
			
			<div rel="body" class="main-sub-content" >								 
					<div class="col-lg-4">	
						
						<div class="popup_wrap_inner" data-collapsed="0"> 
							
							
								<div class="panel-options">
									<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a>
								</div>
							</div>							
							
							<div class="pop_body">
								<div id= "detailTableDivID">
									<table id="detailJqGrid"></table>
									<div id="detailJqGridPager" ></div>
								</div>	
							</div>
						</div>
					</div>
					
					<div id="detail-1area" class="col-lg-8 container-f1luid">	  
					 
						<div class="popup_wrap_inner" data-collapsed="0">
							
							
								<div class="panel-options">
									<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a>
								</div>								
							</div>
							
							<div class="pop_body">
								<div class="form_input">
									<div class="form-group">
										<label for="F_SP_MESSAGE_SER_NO_POP" class="col-lg-2 control-label">거래추적번호</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_MESSAGE_SER_NO_POP"  readonly>
										</div>
										<label for="F_SP_MESSAGE_ID_POP" class="col-lg-2 control-label">전문ID</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_MESSAGE_ID_POP"  readonly>
										</div>
									</div>
									<div class="form-group">
										<label for="F_SP_CUST_REG_NO_POP" class="col-lg-2 control-label">고객번호</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_CUST_REG_NO_POP"  readonly>
										</div>
										<label for="F_SP_TR_ACNO_POP" class="col-lg-2 control-label">출금및조회정보</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_TR_ACNO_POP"  readonly>
										</div>
									</div>
									<div class="form-group">
										<label for="F_SP_USER_ID_POP" class="col-lg-2 control-label">이용자ID</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_USER_ID_POP"  readonly>
										</div>
										<label for="F_SP_IN_ACNO_POP" class="col-lg-2 control-label">입금정보</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_IN_ACNO_POP"  readonly>
										</div>
									</div>
									<div class="form-group">
										<label for="F_SP_IP_POP" class="col-lg-2 control-label">IP/전화번호</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_IP_POP"  readonly>
										</div>
										<label for="F_SP_TEXT_POP" class="col-lg-2 control-label">비고</label>
										<div class="col-lg-3">
											<input type="text"  id="F_SP_TEXT_POP"  readonly>
										</div>
									</div>
									<div class="form-group">
										<label for="F_SP_HEADER_POP" class="col-lg-2 control-label">전문(헤더부)</label>
										<div class="col-lg-8">
											<textarea rows="" cols=""  id="F_SP_HEADER_POP" readonly></textarea>
										</div>
									</div>
									<div class="form-group">
										<label for="F_SP_DATA_POP" class="col-lg-2 control-label">전문(데이터부)</label>
										<div class="col-lg-8">
											<textarea rows="" cols=""  id="F_SP_DATA_POP" readonly></textarea>
										</div>
									</div>
									
								</div>
							</div> 
							
							<div class="pop_body">
								<div class="panel-options">
									<label for="MESSAGE"> </label>
								</div>	
							</div>
							
							<div class="pop_body">
								<div id= "keyValueTableDivID">
									<table id="keyValueJqGrid"></table>
									<div id="keyValueJqGridPager"></div>
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
<script src="${pageContext.request.contextPath}/js/magicview/jsonDetailAsis.js"></script>
