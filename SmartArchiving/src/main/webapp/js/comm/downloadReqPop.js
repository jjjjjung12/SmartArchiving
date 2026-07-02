var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	
	if($("#gubun").val() == "downloadReqPop") search();
	
	$('#btnSavePop').click(function (e) {
		
		if(ArchiveGrid.getRows(window._archiveGrid).length > 100000){
			alert("데이터갯수가 10만건이넘습니다. 10만건 미만으로 조회해주세요");
			return;
		}
		
		var obj = new Object();
		obj.user_id  	= $("input#F_USER_ID_POP_EXCEL").val();
		obj.user_cd  	= $("input#F_USER_CD_POP_EXCEL").val();
		obj.user_nm  	= $("input#F_USER_NM_POP_EXCEL").val();
		obj.brc  		= $("input#F_BRC_POP_EXCEL").val();
		obj.brmm  		= $("input#F_BRMM_POP_EXCEL").val();
		obj.down_cd  	= $("select#F_DOWN_CD_POP_EXCEL").val();
		obj.req_nm		= $("#F_DOWN_CD_POP_EXCEL option:checked").text();
		obj.req_num  	= $("input#F_REQ_NUM_POP_EXCEL").val();
		//obj.warrant_num	= $("input#F_WARRANT_NUM_POP_EXCEL").val();
		obj.req_reason  = $("input#F_REQ_REASON_POP_EXCEL").val();
		obj.reg_date  	= $("input#F_REG_DATE_POP_EXCEL").val();
		obj.page_name  	= $("input#F_PAGE_NAME_POP_EXCEL").val();
		
		var result_yn;
		var startDateTime = $('#F_SP_START_TR_YMD').val() + $('#F_SP_TR_STIME').val();
		var endDateTime =  $('#F_SP_END_TR_YMD').val() + $('#F_SP_TR_ETIME').val();		
		var sendStartDateTime = startDateTime.substring(0,4) + "-" +startDateTime.substring(4,6) + "-" +startDateTime.substring(6,8) + " " +startDateTime.substring(8,10) +":" + startDateTime.substring(10,12) ;
		var sendEndDateTime = endDateTime.substring(0,4) + "-" +endDateTime.substring(4,6) + "-" +endDateTime.substring(6,8) + " " +endDateTime.substring(8,10) +":" + endDateTime.substring(10,12) ;
		
		obj.where = "조회기간: "+ sendStartDateTime + " ~ " + sendEndDateTime
					+",  화면ID: "+ $('#F_SP_SCRN_ID').val()     
					+",  전문ID: "+$('#F_SP_MESSAGE_ID').val()   
					+",  고객번호: "+$('#F_SP_REG_CUST_NO').val()      
					+",  사용자ID: "+$('#F_SP_USER_ID').val()     
					+",  전화번호: "+$('#F_TEL_NO').val()
					+",  출금계좌번호: "+$('#F_TR_ACNO').val()
					+",  전문일련번호: "+$('#F_SP_MESSAGE_SER_NO').val()
					+",  IP: "+$('#F_SP_IN_IP').val()    
					+",  PC MAC Address: "+$('#F_MAC').val()    
		
		var fileName = "통합로그_" +toDayNoPoint + ".xlsx";
		console.log(fileName);
		try{
    		ArchiveGrid.exportToExcel(_archiveGrid, {
    			includeLabels : true,
    			includeGroupHeader : true,
    			includeFooter: true,
    			fileName : fileName,
    			maxlength : 40 // maxlength for visible string data 
    		});
    		result_yn = true;
		}catch(e){
			alert(e);
			result_yn = false;
		}			
		
		if(!result_yn){
			return;
		}
		
		$.ajax({
            url:"SetDownloadReq",
            data:{param:JSON.stringify(obj)},
            type:"post",
            dataType:"json",
            success: function(json_data) {
            	if(json_data.result == 'OK') {
            		alert("저장이 성공했습니다.");
            		closePop();
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
			$('#getbody').remove();
	    }
	});
	
	$('#F_DOWN_CD_POP_EXCEL').change(function (e) {
		$('#F_REQ_NUM_POP_EXCEL').val("");
		$('#F_REQ_REASON_POP_EXCEL').val("");
		
		//요청번호 입력
		if($('#F_DOWN_CD_POP_EXCEL').val() == "01" || $('#F_DOWN_CD_POP_EXCEL').val() == "02"){
			$('#F_REQ_NUM_POP_EXCEL').attr('readonly',false);
			$('#F_REQ_REASON_POP_EXCEL').attr('readonly',true);
		}
		//고객 민원응대
		else if($('#F_DOWN_CD_POP_EXCEL').val() == "03"){
			$('#F_REQ_NUM_POP_EXCEL').attr('readonly',true);
			$('#F_REQ_REASON_POP_EXCEL').attr('readonly',true);
		}
		//요성사유 입력
		else if($('#F_DOWN_CD_POP_EXCEL').val() == "04"){
			$('#F_REQ_NUM_POP_EXCEL').attr('readonly',true);
			$('#F_REQ_REASON_POP_EXCEL').attr('readonly',false);
		}
		
	});
	
});

function search(){
	var obj = new Object();
	obj.user_cd = $('#F_USER_CD_POP_EXCEL').val() ;
	
	$.ajax({
        url:"GetDownloadReq",
        data:{param:JSON.stringify(obj)},
        dataType:"json",
        success: function(json_data) {
        	if(json_data.result == 'OK') {
        		$('#F_BRC_POP_EXCEL').val(json_data.brc);
        		$('#F_BRMM_POP_EXCEL').val(json_data.brnm);
        	}
            else{
            	console.log('NOTFOUND');
            }
        },
        error : function(json_data, status){
            alert("[알림] 처리시오류가 발생하였습니다\n" );
            console.log("[알림] 처리시오류가 발생하였습니다\n" + json_data.responseText);
        }
    });
}
