var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
var actionMode ="";
$(document).ready(function() {

	if( $('#groupId').val() != ""){
		$('#F_APPLICATION_GROUP_ID').prop( 'readonly', true) ;
		actionMode ="U";
	}else{
		actionMode ="I";
		document.getElementById('main_title').innerText = "서비스등록";
		document.getElementById('sub_title').innerText = "서비스정보 신규 등록";
	}
	if( $('#serviceId').val() != ""){
		$('#F_SERVICE_ID').prop( 'readonly', true) ;
	}
	$('#F_APPLICATION_GROUP_ID').val( $('#groupId').val() );
	$('#F_SERVICE_ID').val( $('#serviceId').val() );
	$('#F_SEL_GUBUN').val( $('#selGubun').val() );
	$('#F_DEL_YN').val( $('#delYn').val() );
	



    
	//서비스정보 Data 저장
	$('#btnUpdateSave').click(function (e) {
		console.log("btnUpdateSave############################");
		if(  $("#F_APPLICATION_GROUP_ID").val().trim() == ""){
			alert("채널코드를입력 해주세요.");
			return;
		}
		if(  $("#F_SERVICE_ID").val().trim() == ""){
			alert("서비스ID를입력 해주세요.");
			return;
		}
		if(  $("#F_SEL_GUBUN").val().trim() == ""){
			alert("SEL구분을입력 해주세요.");
			return;
		}
		if(  $("#F_DEL_YN").val().trim() == ""){
			alert("삭제 여부를입력 해주세요.");
			return;
		}
		var obj = new Object();
		obj.APPLICATION_GROUP_ID = $("#F_APPLICATION_GROUP_ID").val();
		obj.SERVICE_ID = $("#F_SERVICE_ID").val();
		obj.SEL_GUBUN = $("#F_SEL_GUBUN").val();
		obj.DEL_YN = $("#F_DEL_YN").val();
		obj.CRUD = actionMode;
		
		
		
		$.ajax({
	        url:"SetServiceInfoOne",
	        type: 'post',
	        data:{param:JSON.stringify(obj)},
		//	processData : false,
		//	cache : false,R
	        dataType:"json",
	        success: function(jsonData) {
	        	if(jsonData.result == "OK"){
					$('#pop_close').click();
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);					
					alert("정상적으로처리 되었습니다");
					
				}
				else{
					alert("저장이 실패하였습니다");
					//$('#pop_close').click();
				}
	        },
	        error : function(jsonData, status){
	            alert("[알림] 처리시오류가 발생하였습니다\n" );
	            console.log("[알림] 처리시오류가 발생하였습니다\n" + jsonData.responseText);
	        }
	    });
	});



});



