var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetBulkInquiryJobReqList',
		pageSize: 10,
		columns: [
			{name:'START_DATE', align:'center'},
			{name:'END_DATE', align:'center'},
			{name:'SEARCH_DIV_CD', hidden:true},
			{name:'SEARCH_DIV_NM', align:'center'},
			{name:'SEARCH_DIV_VAL', align:'center'},
			{name:'WARRANT_NUM', align:'center'},
			{name:'BATCH_YN', align:'center'},
			{name:'APPLY_YN', align:'center'},
			{name:'FILE_URL', hidden:true},
			{name:'APPROVAL_REQ_ID', hidden:true},
			{name:'USER_CD', hidden:true},
			{name:'USER_NM', hidden:true},
			{name:'REQ_DATE', hidden:true},
			{name:'FILE_NM', align:'center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {
	getServer($("#C_SERVER_ID"),1);

	
			
	
	
	$('#btnSave').click(function (e) {
		
		// Exception
		if($('#F_SP_START_TR_YMD').val().length != 8 || $('#F_SP_END_TR_YMD').val().length != 8)
		{
		   alert("날짜 시간을확인해주세요.");
		   return;
		}
		
		if($('#F_SP_REQ_YMD').val().length != 8 ){
			alert("요청일자를입력해주세요.");
			return;
		}
		
		if($('#F_SP_SEARCH_DIV_CD').val() == "" ){
			alert("조회구분을선택해주세요.");
			return;
		}
		
		if($('#F_SP_WARRANT_NUM').val() == "" ){
			alert("영정번호를입력해주세요.");
			return;
		}
		
		var popWidth = document.body.clientWidth;
	    var popHeight = document.body.clientHeight;
	    
	    var pageName = "";
	    var tempPageName= window.location.href;
	    var strPageName	= tempPageName.split("/");
	    pageName = strPageName[strPageName.length-1].split("?")[0];
	    
		openArchivePopup('userAuthApplyPop?&userId=' + sessionsabun + '&userCd=' + sessionUserCd
			+ '&userNm=' + sessionUserNm + '&teamNm=' + sessionGroupId + '&ssoId=' + sessionSsoId + '&pageName=' + pageName, {
			width: popWidth - 850,
			height: popHeight - 520,
			keyboard: false
		});
		
		
		/*$('#crud').val("up");
		
		var form = $('#SetTableForm')[0];
		var frmData = new FormData(form);
		
		$.ajax({
			enctype: 'multipart/form-data',
			type: 'POST',
			url : "SetBulkInquiryJobReq",
			processData : false,
			contentType : false,
			cache : false,
			data : frmData,
			sucess : function(data){
				if(data.result == "OK"){
					alert("저장성공");
				}else{
					alert("저장실패");
				}
			},
			error : function(e){
				console.log(e);
				alert("실패");
			}
		});*/
	});
	
	function searchTable()  
	{
		var Obj = new Object();
		
		Obj.F_SP_USER_CD     = $('#F_SP_USER_CD').val();
		Obj.F_SP_REQ_YMD     = $('#F_SP_REQ_YMD').val();
		Obj.F_SP_WARRANT_NUM  = $('#F_SP_WARRANT_NUM').val();
		Obj.F_SP_START_TR_YMD = $('#F_SP_START_TR_YMD').val();
		Obj.F_SP_END_TR_YMD   = $('#F_SP_END_TR_YMD').val();
		Obj.F_SP_SEARCH_DIV_CD= $('#F_SP_SEARCH_DIV_CD').val();
		
		// 초기화		
		
		
		
		
		
	}

	$('#btnSaveExcel').click(function (e) {
		
		console.log("btnSaveExcel");
		var form = $('#SetBulkForm')[0];
		var frmData = new FormData(form);
		$.ajax({
			enctype: 'multipart/form-data',
			type: 'post',
			url : "ParseBulkInquiryJobReq",
			processData : false,
			contentType : false,
			cache : false,
			data : frmData,
			success : function(jsonData){
				alert(jsonData);
			},
			error : function(e){
				console.log(e);
				alert("실패");
			}
		})
		
		/*var ids = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});
		var idsLen = ids.length ;
		
		console.log( "btnSaveExcel idsLen:" + idsLen );
       
        if(idsLen > 0)
        {
		    var tableid = $("input#F_TABLE_ID").val();
		    if(tableid.length > 0)
		    {
		
				for(var i = 0; i< idsLen; ++i)
				{
					
					var selectdRow = selectedRow;
		
					console.log( "rowId:" + i+1 + "::" + selectdRow.ATTR_NULL_YN);
				
					var obj = new Object();
					obj.table_id     = $("input#F_TABLE_ID").val();
					obj.attr_cd      = selectdRow.ATTR_CD;
					obj.attr_nm      = selectdRow.ATTR_NM;
					obj.attr_size    = selectdRow.ATTR_SIZE;
					obj.decimal_size = selectdRow.DECIMAL_SIZE;
					obj.attr_type_cd = selectdRow.ATTR_TYPE_CD;
					obj.attr_type_nm = selectdRow.ATTR_TYPE_NM;
					obj.attr_null_yn = selectdRow.ATTR_NULL_YN;
					obj.sort_index   = selectdRow.SORT_INDEX;
					obj.where_index  = selectdRow.WHERE_INDEX;
					obj.output_index = selectdRow.OUTPUT_INDEX;
					obj.crud         = "AAU";
					
					console.log('sCrud:'+ obj.crud);
					console.log('F_TABLE_ID:'+ obj.table_id); 
					console.log('F_ATTR_CD:'+ obj.attr_cd); 
					console.log('F_ATTR_NM:'+ obj.attr_nm); 
					console.log('F_ATTR_SIZE:'+ obj.attr_size); 
					
					console.log('F_ATTR_TYPE_CD:'+ obj.attr_type_cd); 
					console.log('F_ATTR_TYPE_NM:'+ obj.attr_type_nm);  
					console.log('F_ATTR_NULL_YN:'+ obj.attr_null_yn);  
					console.log('F_SORT_INDEX:'+ obj.sort_index); 
					console.log('F_WHERE_INDEX:'+ obj.where_index); 
					console.log('F_OUTPUT_INDEX:'+ obj.output_index); 
					
					$("#SetATTRForm").ajaxForm({
						url : 'SetBulkInquiryJobReq',
						dataType:'json',
						type: 'post',
						data:{param:JSON.stringify(obj)},
						success: function(json_data) {
											
							
						},
						error : function(data, status){
					    	if (data != null){
					    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달					// data 오브젝트의 error 값이 2일 때의 이벤트 처리
					    			//alert("이미 등록되어 있는 아이디입니다");
					    		} else {
					    			//alert("Error");
					    		}
					    	}
						}
					});	
					$("#SetATTRForm").submit() ;
				   
				}
		    }else
		    {
		    	alert("저장할 테이블을 선택해주세요");
		    }
        }else
        {
        	alert("저장할 내용이없습니다.");
        }*/
		
	});

});

function fillZero(width, str){
    return str.length >= width ? str:new Array(width-str.length+1).join('0')+str;//남는 길이만큼 0으로 채움
}
