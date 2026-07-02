var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetUserApproveProcList',
		pageSize: 10,
		columns: [
			{name:'APPROVAL_REQ_ID', align:'center'},
			{name:'APPROVAL_DIV_CD', hidden:true},
			{name:'USER_CD', hidden:true},
			{name:'USER_NM', align:'center'},
			{name:'APPROVAL_DIV_NM', align:'center'},
			{name:'APPROVAL_REQ_REASON', align:'left'},
			{name:'REQ_DATE', align:'center'},
			{name:'APPROVAL_LINE_USER_NM', align:'center'},
			{name:'APPROVAL_DATE', align:'center'},
			{name:'APPROVAL_YN', hidden:true},
			{name:'APPROVAL_YN_NM', align:'center'},
			{name:'APPROVAL_LINE_USER_ID', hidden:true},
			{name:'APPROVAL_LINE_INDEX', hidden:true},
			{name:'APPROVAL_REJECT_DOCU', hidden:true},
			{name:'APPROVAL_REQ_DOCU', hidden:true},
			{name:'PROGRAM_ID', hidden:true},
			{name:'PROGRAM_NM', hidden:true},
			{name:'BRC', hidden:true},
			{name:'BRNM', hidden:true},
			{name:'OFT_C', hidden:true},
			{name:'OFT', hidden:true},
			{name:'IP_ADDRESS', hidden:true},
			{name:'AUTH', hidden:true},
			{name:'COM_CD', hidden:true},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	function refresh(){
		$('#F_USER_ID_SET').val("");
		$('#F_APPROVAL_REQ_ID').val("");
		$('#F_APPROVAL_DIV_CD').val("");
		$('#F_APPROVAL_LINE_USER_ID').val("");
		
		$('#F_APPROVAL_YN').val("");
		$('#F_APPROVAL_REJECT_DOCU').val("");
		$('#F_REG_START_DATE').val("");
		$('#F_APPROVAL_DATE').val("");
		$('#F_REQ_DATE').val("");
		$("#F_APPROVAL_REQ_DOCU").val("");
		$("#F_APPROVAL_REJECT_DOCU").attr("readonly", false);
		$("#F_APPROVAL_REQ_DOCU").attr("readonly", false);
		$('#F_APPROVAL_YN').attr("readonly",false);
		
		$('#F_BRC').val("");
		$('#F_BRNM').val("");
		$('#F_OFT_C').val("");
		$('#F_OFT').val("");
		
		$('#F_IP_ADDRESS').val("");
		$('#F_AUTH').val("");
		$('#F_COM_CD').val("");
	}
	
		console.log("btn searchData.........");
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	$('#btnSave').click(function (e) {
		var obj = new Object();
		obj.user_cd   			= $("input#F_USER_CD_SET").val();
		obj.group_id 			= "";
		obj.user_nm   			= $("input#F_USER_NM_SET").val();
		obj.apply_user_nm 		= "";
		obj.approval_req_id		= $("input#F_APPROVAL_REQ_ID").val();
		obj.approval_div_cd     = $("input#F_APPROVAL_DIV_CD").val();
		obj.approval_line_user_id = $("input#F_APPROVAL_LINE_USER_ID").val();
		obj.approval_yn   		= $("select#F_APPROVAL_YN").val();
		obj.approval_reject_docu= $("input#F_APPROVAL_REJECT_DOCU").val();
		obj.approval_req_docu   = $("input#F_APPROVAL_REQ_DOCU").val();
		obj.approval_date      	= $("input#F_APPROVAL_DATE").val();
		obj.req_date      		= $("input#F_REQ_DATE").val();
		obj.approval_line_index = $("input#F_APPROVAL_LINE_INDEX").val();
		obj.program_id 			= $("input#F_PROGRAM_ID").val();
		obj.program_nm 			= $("input#F_PROGRAM_NM").val();
		obj.brc 				= $("input#F_BRC").val();
		obj.brnm 				= $("input#F_BRNM").val();
		obj.oft_c 				= $("input#F_OFT_C").val();
		obj.oft 				= $("input#F_OFT").val();
		obj.ip_address 			= $("input#F_IP_ADDRESS").val();
		obj.auth 				= $("input#F_AUTH").val();
		obj.com_cd 				= $("input#F_COM_CD").val();
		
		obj.crud        		= "c";
		
		if(obj.approval_req_id == '') {
			alert("결재 건을 선택하세요");
			return;
		}
				
		if($('#F_APPROVAL_YN').prop("disabled")){
			alert("[알림] 이미 결재자건입니다.");
			return;
		}
		
		if(obj.approval_yn == ''){
			alert("[알림] 결재상태를선택하세요");
			$("input#F_APPROVAL_YN").focus();
		    return;
		}

		if(obj.req_date == ''){
			alert("[알림] 신청일자를입력하세요");
			$("input#F_REQ_DATE").focus();
		    return;
		}

		if(obj.approval_yn == 'N' && obj.approval_reject_docu == ""){
			alert("[알림] 반려사유를입력하세요");
			$("input#F_APPROVAL_REJECT_DOCU").focus();
		    return;
		}
		
		
		$.ajax({
			url : 'SetUserApproveProc',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
 					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
 					alert("정상적으로처리 되었습니다");
 				}
 				else{
					alert(json_data.msg);
				}
 			},
			error : function(data, status){
		    	if (data != null){
		    		alert(data.responseText || "[알림] 처리 중 오류가 발생하였습니다");
		    	}
			}
		});	
	});
	
	$('#F_APPROVAL_YN').click(function (e) {
		//승인
		if($("select#F_APPROVAL_YN").val() == "Y"){
			$("#F_APPROVAL_REJECT_DOCU").val("");
			$("#F_APPROVAL_REJECT_DOCU").attr("readonly", true);
			$("#F_APPROVAL_REQ_DOCU").attr("readonly", false);
		}
		//반려
		else if($("select#F_APPROVAL_YN").val() == "N"){
			$("#F_APPROVAL_REQ_DOCU").val("");
			$("#F_APPROVAL_REJECT_DOCU").attr("readonly", false);
			$("#F_APPROVAL_REQ_DOCU").attr("readonly", true);
		}
		else{
			$("#F_APPROVAL_REQ_DOCU").val("");
			$("#F_APPROVAL_REJECT_DOCU").val("");
			$("#F_APPROVAL_REJECT_DOCU").attr("readonly", false);
			$("#F_APPROVAL_REQ_DOCU").attr("readonly", false);
		}
	});
	
	$('#login').click(function (e) {
		$.ajax({
			url: "GetLogin",
			dataType:"json", 
			type:"post",
			success: function(json){
				if(json.result == "success") location.href = archiveCtx + "/login";
			}
		}); 
	});
});

function loadInfo(selectedRow, currentRow, currentPage) {

		var selectdRow = selectedRow;
		
		if(selectdRow.APPROVAL_YN == "Y" || selectdRow.APPROVAL_YN == "N"){
			$('#F_APPROVAL_DATE').val(selectdRow.APPROVAL_DATE);
		}else{
			$('#F_APPROVAL_DATE').val(toDayPoint);
		}
		
		$('#F_USER_CD_SET').val(selectdRow.USER_CD);
		$('#F_USER_NM_SET').val(selectdRow.USER_NM);
		$('#F_APPROVAL_REQ_ID').val(selectdRow.APPROVAL_REQ_ID);
		$('#F_APPROVAL_DIV_CD').val(selectdRow.APPROVAL_DIV_CD);
		$('#F_APPROVAL_LINE_USER_ID').val(selectdRow.APPROVAL_LINE_USER_ID);
		
		$('#F_APPROVAL_YN').val(selectdRow.APPROVAL_YN);
		$('#F_APPROVAL_REJECT_DOCU').val(selectdRow.APPROVAL_REJECT_DOCU);
		$('#F_APPROVAL_REQ_DOCU').val(selectdRow.APPROVAL_REQ_DOCU);
		$('#F_REQ_DATE').val(selectdRow.REQ_DATE);
		$('#F_APPROVAL_LINE_INDEX').val(selectdRow.APPROVAL_LINE_INDEX);
		$('#F_PROGRAM_ID').val(selectdRow.PROGRAM_ID);
		$('#F_PROGRAM_NM').val(selectdRow.PROGRAM_NM);
		
		$('#F_BRC').val(selectdRow.BRC);
		$('#F_BRNM').val(selectdRow.BRNM);
		$('#F_OFT_C').val(selectdRow.OFT_C);
		$('#F_OFT').val(selectdRow.OFT);
		
		$('#F_IP_ADDRESS').val(selectdRow.IP_ADDRESS);
		$('#F_AUTH').val(selectdRow.AUTH);
		$('#F_COM_CD').val(selectdRow.COM_CD);
		$('#F_EXPIRE_DATE').val(selectdRow.EXPIRE_DATE);
		
		if($('#F_APPROVAL_YN').val() != ""){
			$('#F_APPROVAL_YN').attr('disabled', true);
			$('#F_APPROVAL_REQ_DOCU').attr("readonly",true);
			$('#F_APPROVAL_REJECT_DOCU').attr("readonly",true);
		}else{
			$('#F_APPROVAL_YN').attr('disabled', false);
			$('#F_APPROVAL_REQ_DOCU').attr("readonly",false);
			$('#F_APPROVAL_REJECT_DOCU').attr("readonly",false);
		}
		
	}
