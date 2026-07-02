var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetWorkList',
		pageSize: 10,
		columns: [
			{name:'SERVER_ID', hidden:true},
			{name:'SERVER_NM', hidden:true},
			{name:'WORK_CD', align:'center'},
			{name:'WORK_NM', align:'center'},
			{name:'ACCOUNT_CD', align:'center'},
			{name:'USE_YN', align:'center'},
			{name:'DESCRIPTION', align:'center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
function buildSearchParam() {
	var Obj = {};

		
		Obj.__use_yn    = $('input[name="C_USE_YN"]:checked').val();
		Obj.__server_id = $('#C_SERVER_ID').val();
		Obj.__work_cd   = "*";
	return Obj;
}

$(document).ready(function() {
	
	$('#btnCancel').click(function (e) {
		$('#searchVal').val("");
		$("#searchVal").focus();
	});

	$('#btnAdd').click(function (e) {
//		$('#F_SERVER_NM'  ).val($('#C_SERVER_ID  option:selected').text());
		$('#F_WORK_CD'    ).val("");
		$('#F_WORK_NM'    ).val("");
		$('#F_ACCOUNT_CD' ).val("");
		$('#F_DESCRIPTION').val("");
		$('#CRUD'         ).val("C");
		$('#F_WORK_CD'    ).attr("readonly", false); //설정
	});
	
	$('#btnDelete').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.server_id   = $('#C_SERVER_ID').val();
		obj.work_cd     = $("input#F_WORK_CD").val();
		obj.work_nm     = "";
		obj.account_cd  = "";
		obj.description = "";
		obj.crud        = "D";
		
		console.log('C_SERVER_ID:'+ obj.server_id);
		console.log('F_WORK_CD:'+ obj.work_cd);
		console.log('sCrud:'+ obj.crud);

		$("#SetWorkForm").ajaxForm({
			url : 'SetWork',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(obj)},
			success: function(json_data) {
				alert("정상적으로처리 되었습니다");
				//$('#btl02', parent.document).click();
				//$('#iframItemRange').reload();
				
				$('#F_WORK_CD'    ).val("");
				$('#F_WORK_NM'    ).val("");
				$('#F_ACCOUNT_CD' ).val("");
				$('#F_DESCRIPTION').val("");
				$('#CRUD'         ).val("C");
				$('#F_WORK_CD'    ).attr("readonly", false); //설정				
				if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달					// data 오브젝트의 error 값이 2일 때의 이벤트 처리
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("Error");
		    		}
		    	}
			}
		});	
		$("#SetWorkForm").submit() ;
	});
	
	$('#btnSave').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.server_id   = $('#C_SERVER_ID').val();
		obj.server_nm   = $("input#F_SERVER_NM").val();
		obj.work_cd     = $("input#F_WORK_CD").val();
		obj.work_nm     = $("input#F_WORK_NM").val();
		obj.account_cd  = $("input#F_ACCOUNT_CD").val();
		obj.description = $("textarea#F_DESCRIPTION").val();
		obj.use_yn      = $('input[name="F_USE_YN"]:checked').val()
		obj.crud        = $("input#CRUD").val();
		
		console.log('crud:'+ obj.crud);
		console.log('server_id:'+ obj.server_id); 
		console.log('work_cd:'+ obj.work_cd);
		console.log('work_nm:'+ obj.work_nm);
		console.log('account_cd:'+ obj.account_cd);
		console.log('description:'+ obj.description);

		if (!IsAlphabet(obj.work_cd, 4 )) {
			alert("[알림] 업무코드는영문 4자리로입력하세요");
			$("input#F_WORK_CD").focus();
		    return;			
		}
		
		if(obj.work_nm == ''){
			alert("[알림] 업무명을 입력하세요");
			$("input#F_WORK_NM").focus();
		    return;
		}
		
		console.log('0000000000000');
		$("#SetWorkForm").ajaxForm({
			url : 'SetWork',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
/*				if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
				*/
				if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
				$('#btnAdd').click();
				alert("정상적으로처리 되었습니다");
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달					// data 오브젝트의 error 값이 2일 때의 이벤트 처리
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("Error");
		    		}
		    	}
			}
		});	
		$("#SetWorkForm").submit() ;
	});

// 	$.when(getServer($("#C_SERVER_ID"), '1')).done($('#btnQuery').click());
// 	$.when(getServer($("#C_SERVER_ID"), '1')).done( console.log('wwwwwwwww') );

	$('input[name="C_USE_YN"]').val(["Y"]);
	$('#F_WORK_CD').attr("readonly", false); //설정
	
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	 
	$("#searchVal").focus();

});

function loadInfo(selectedRow, currentRow, currentPage) {
		
		var selectdRow = selectedRow;

		console.log(selectdRow);
		
		$('#ROWID').val(currentRow || '');
		
		jsonObj = {};
		
		jsonObj.__use_yn    = $('input[name="C_USE_YN"]:checked').val();
		jsonObj.__server_id = selectdRow.SERVER_ID;
		jsonObj.__work_cd   = selectdRow.WORK_CD;
		jsonObj.__rows      = currentRow+"";
		jsonObj.__page      = currentPage+"" ;
		
		console.log(jsonObj.__server_id);
		console.log(jsonObj.__work_cd);
		
		$.ajax({
			url: 'GetWorkList',    
			data: {param:JSON.stringify(jsonObj)},
			type:"post",			
			dataType:"json",
			success: function(json_data) {
	
		        if(json_data.result == 'OK') {
			        $('#F_WORK_CD' ).val(json_data.rows[0].WORK_CD);
			        $('#F_WORK_NM' ).val(json_data.rows[0].WORK_NM);
			        $('#F_ACCOUNT_CD' ).val(json_data.rows[0].ACCOUNT_CD);
			        $('#F_DESCRIPTION' ).val(json_data.rows[0].DESCRIPTION);
			        $('#F_WORK_CD'    ).attr("readonly", true ); //설정
			        $('input[name="F_USE_YN"]').val([json_data.rows[0].USE_YN]);			        
			        $('#CRUD').val("U");
				} else {
					console.log(json_data.result); 
				}
			}
		});	
	}
