<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "modalDept"); %>
<div class="popup_wrap_inner">
	<div class="pop_header"><h3 class="tit_pop">modalDept</h3></div>
	<div class="pop_body"><!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">

<script type="text/javascript">
function showDeptSearchModal()
{
	jQuery('#deptSearch').modal('show', {backdrop: 'static'});
	sPocus = 11;
}
</script>
	
	<!-- userSearch (Ajax Modal)-->
	<div class="modal fade"  data-backdrop="static" id="deptSearch">
		<div class="modal-dialog" style="width:17%">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">부서찾기</h4>
				</div>
				<div class="modal-body">
							<!-- main-sub-content start-->
							<div class="main-sub-content" >
								<div class="row">
									<div class="popup_wrap_inner" data-collapsed="0">
										<!-- div class="panel-heading"> 
											<div class="panel-title">
												조직도
											</div> 
											<div class="panel-options"> 
												<a href="#" data-rel="collapse"><i class="entypo-down-open"></i></a> 
												<a href="#" data-rel="reload"><i class="entypo-arrows-ccw"></i></a> 
											</div> 
										</div -->
										<div class="pop_body no-padding" style="padding: 5px 0;"> 
											<div class="sabun_panel">
												<div class="sabun_panel_left" id="dynatreeDept"></div>
											</div>
										</div> 
										
										<!-- div class="pop_body border-top"></div -->
									</div>
								</div> 
							</div>
							<!-- main-sub-content end-->
					
				</div> <!--  modal-body  -->
				<div class="modal-footer">
					<button type="button" name="close" class="btn btn-default" data-dismiss="modal">Close</button>
					<!--  button type="button" class="btn btn-info">Save changes</button -->
				</div>				
			</div>
		</div>
	</div>

</div>
</div>
<script src="${pageContext.request.contextPath}/js/user/modalDept.js"></script>
