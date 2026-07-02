var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	// 결재자목록은 팝업이아니라부모화면의셀렉트(#F_USER_LINE / #F_USER_SECOND_LINE)에존재한다.
	// w2popup.load 로열린 경우 parent.document 에서 가져와야한다.
	try {
		var parentFirst = $(parent.document).find("select#F_USER_LINE").html();
		var parentSecond = $(parent.document).find("select#F_USER_SECOND_LINE").html();

		if (typeof parentFirst !== "undefined" && parentFirst !== null && parentFirst !== "") {
			$("select#F_FIRST_USER_ID_POP").append(parentFirst);
		}
		if (typeof parentSecond !== "undefined" && parentSecond !== null && parentSecond !== "") {
			$("select#F_SECOND_USER_ID_POP").append(parentSecond);
		}
	} catch (e) {
		// 부모접근 불가(단독 로드 에 시에는빈목록으로 남긴다
		console.log("approval user list load failed:", e);
	}
	
	
	//권한신청 삭제
	$("#F_APPLY_DIV_CD_POP option[value='01']").remove();
	
	if($("#F_GUBUN").val() == "masking"){
		$("#main_title").html("마스킹해제 요청");
		$("#sub_title").html("마스킹해제 요청");
//		$("#F_APPLY_DIV_CD_POP option[value='02']").selected();
		$("#F_APPLY_DIV_CD_POP").val("02");
	}else{
		$("#main_title").html("대량조회 요청");
		$("#sub_title").html("대량조회 요청");
//		$("#F_APPLY_DIV_CD_POP option[value='03']").selected();
		$("#F_APPLY_DIV_CD_POP").val("03");
	}
	
	
	
	search();
	
	$('#btnSavePop').click(function (e) {
		var obj = new Object();
		obj.user_cd     		= $("input#F_USER_CD_POP").val();
		obj.user_nm     		= $("input#F_USER_NM_POP").val();
		obj.brc    				= $("input#F_BRC_POP").val();
		obj.brmm    			= $("input#F_BRMM_POP").val();
		obj.approval_req_id 	= $("input#F_APPROVAL_REQ_ID_POP").val();
		obj.oft_c   			= $("input#F_OFT_C_POP").val();
		obj.oft   				= $("input#F_OFT_POP").val();
		obj.apply_div_cd    	= $("select#F_APPLY_DIV_CD_POP").val();
		obj.approval_req_reason = $("input#F_APPROVAL_REQ_REASON_POP").val();
		obj.first_user_id		= $("select#F_FIRST_USER_ID_POP").val();
		obj.first_user_nm		= $("#F_FIRST_USER_ID_POP option:checked").text();
		obj.second_user_id		= $("select#F_SECOND_USER_ID_POP").val();
		obj.second_user_nm		= $("#F_SECOND_USER_ID_POP option:checked").text();
		obj.req_date     		= $("input#F_REQ_DATE").val();
		obj.page_name     		= $("input#F_PAGE_NAME").val();
		obj.crud        		= "ap";
		
		if(obj.user_cd == ''){
			alert("[알림] 사용자ID를입력하세요");
//			$("input#F_USER_CD").focus();
			$("input#F_USER_CD_POP").focus();
		    return;
		}

		if(obj.user_nm == ''){
			alert("[알림] 사용자이름을 입력하세요");
//			$("input#F_USER_NM").focus();
			$("input#F_USER_NM_POP").focus();
		    return;
		}

//		if(obj.password == ''){
//			alert("[알림] 비밀번호를 입력하세요");
//			$("input#F_PASSWORD").focus();
//		    return;
//		}
		
		if(obj.apply_div_cd == "01"){
			alert("[알림] 해당화면에서는사용자권한 신청이되지 않습니다.");
			$("select#F_APPLY_DIV_CD_POP").focus();
		    return;
		}
		
		if(obj.first_user_id == ''){
			alert("[알림] 1차결재자를 선택하세요");
//			$("select#F_FIRST_USER_ID").focus();
			$("select#F_FIRST_USER_ID_POP").focus();
		    return;
		}
		
		if(obj.second_user_id == ''){
			alert("[알림] 2차결재자를 선택하세요");
//			$("select#F_SECOND_USER_ID").focus();
			$("select#F_SECOND_USER_ID_POP").focus();
		    return;
		}
		
		//bulk 
		if(typeof $('#SetBulkForm')[0] != "undefined"){
			if(obj.apply_div_cd != '03'){
				alert("[알림] 결재구분코드를배치요청 작업으로 선택하세요");
				$("select#F_SECOND_USER_ID").focus();
			    return;
			}
			var parentExcelBulk = parent.document.SetBulkForm["bulkExcelJsonData"].value;
			console.log("F_BULK_EXCEL_JSON####:" + parentExcelBulk );
			
			obj.bulkReqList		    = parentExcelBulk;
			/// ykh==> json으로 변경
			$("input#F_BULK_EXCEL_JSON").val(parentExcelBulk);
			$("input#F_FIRST_USER_NM_POP").val($("#F_FIRST_USER_ID_POP option:checked").text());
			$("input#F_SECOND_USER_NM_POP").val($("#F_SECOND_USER_ID_POP option:checked").text());
			
			var form = $('#SetBulkForm')[0];
			var frmData = new FormData(form);
			var form2 = $('#applyForm')[0];
			var frmData2 = new FormData(form2);
			
			for(var item of frmData2.entries()){
				frmData.append(item[0],item[1]);
			}
			
			$.ajax({
				url : 'SetBulkInquiryJobReq',
				dataType:'json',
				type: 'post',
				data:{param:JSON.stringify(obj)},
				success: function(json_data) {
					if(json_data.result == "OK"){
						alert("결재 완료 에나의작업>요청현황에서 볼에있습니다.");
						closePop();
					}
				},
				error : function(data, status){
					if (data != null){
			    		alert("Error  " + data.responseText );
			    	}
				}
			});	
			/*
			$.ajax({
				enctype: 'multipart/form-data',
				type: 'POST',
				url : "SetBulkInquiryJobReq",
				processData : false,
				contentType : false,
				cache : false,
				data : frmData,
				success : function(jsonData){
					alert(jsonData);
					w2popup.close();
				},
				error : function(e){
					console.log(e);
					alert("실패");
				}
			});
			**/
		}
		else{
			if(obj.apply_div_cd != '02'){
				alert("[알림] 결재구분코드를마스킹해제로선택하세요");
				$("select#F_SECOND_USER_ID").focus();
			    return;
			}
			
			if(obj.approval_req_reason.length < 10){
				alert("[알림] 사유를상세히기술해주세요.");
				$("#F_APPROVAL_REQ_REASON_POP").focus();
			    return;
			}
			
			$.ajax({
				url : 'SetUserAuthApply',
				dataType:'json',
				type: 'post',
				data:{param:JSON.stringify(obj)},
				success: function(json_data) {
					if(json_data.result == "OK"){
						alert("결재 완료 에나의작업>요청현황에서 볼에있습니다.");
						closePop();
					}
				},
				error : function(data, status){
					if (data != null){
			    		alert("Error  " + data.responseText );
			    	}
				}
			});	
		}
	});
});

function search(){
	var obj = new Object();
	obj.user_cd = $("input#F_USER_CD_POP").val();

    $.ajax({
        url:"GetUserAuthApply",
        data:{param:JSON.stringify(obj)},
        dataType:"json",
        success: function(json_data) {
        	if(json_data.result == 'OK') {
        		$('#F_APPROVAL_REQ_ID_POP').val(json_data.approval_req_id);
        		$('#F_BRC_POP').val(json_data.brc);
        		$('#F_BRMM_POP').val(json_data.brnm);
        		$('#F_OFT_C_POP').val(json_data.oft_c);
        		$('#F_OFT_POP').val(json_data.oft);
        	} else {
                alert("권한이존재하지 않습니다.");
               // w2popup.close();
            }
        },
        error : function(json_data, status){
            alert("[알림] 처리시오류가 발생하였습니다\n" );
            console.log("[알림] 처리시오류가 발생하였습니다\n" + json_data.responseText);
        }
    });
}
