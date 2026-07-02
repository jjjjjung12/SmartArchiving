var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	
	$('#btnSavePop').click(function (e) {
		var obj = new Object();
		obj.user_id  		= $("input#F_USER_ID_POP").val();
		obj.user_cd  		= $("input#F_USER_CD_POP").val();
		obj.user_nm  		= $("input#F_USER_NM_POP").val();
		obj.team_nm  		= $("input#F_TEAM_NM_POP").val();
		obj.approval_req_id = $("input#F_APPROVAL_REQ_ID_POP").val();
		obj.grade_nm  		= $("input#F_GRADE_NM_POP").val();
		obj.apply_div_cd  	= $("#F_APPLY_DIV_CD_POP").find(":selected").val();
		obj.apply_div_nm  	= $("#F_APPLY_DIV_CD_POP").find(":selected").text();
		obj.approval_req_reason = $("input#F_APPROVAL_REQ_REASON_POP").val();
		
		$.ajax({
            url:"SetTranMaskReq",
            data:{param:JSON.stringify(obj)},
            type:"post",
            dataType:"json",
            success: function(json_data) {
            	if(json_data.result == 'OK') {
            		$("input#F_REQ_DIV_CD_POP").val("");   
            		$("input#F_REQ_DIV_NM_POP").val("");   
            		$("input#F_REQ_NUM_POP").val("");      
            		$("input#F_REQ_REASON_POP").val("");   
            		$("input#F_REG_DATE_POP").val("");
            		alert("저장이 성공했습니다.");
                } else {
                    alert("저장을 실패하였습니다");
                    
                }
            },
            error : function(json_data, status){
                alert("[알림] 처리시오류가 발생하였습니다\n" );
                console.log("[알림] 처리시오류가 발생하였습니다\n" + json_data.responseText);
            }
        });
	});	
	
	w2popup.on('close', function(event) {
		event.onComplete = function () {
			$("#tableDivID").replaceWith(function () {
				  return "<div id='tableDivID'><table id='noticeJqGrid'></table><div id='noticeJqGridPager'></div></div>";
			});
			if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	    }
	});
	
});

