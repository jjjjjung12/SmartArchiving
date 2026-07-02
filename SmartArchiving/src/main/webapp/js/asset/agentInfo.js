var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetAgentList',
		pageSize: 10,
		columns: [
			{name:'SERVER_ID', hidden:true},
			{name:'SERVER_NM', hidden:true},
			{name:'AGENT_ID', hidden:true},
			{name:'AGENT_NM', align:'center'},
			{name:'AGENT_PORT', align:'center'},
			{name:'ACCOUNT_CD', align:'center'},
			{name:'PASSWORD', hidden:true},
			{name:'PATH', hidden:true},
			{name:'DESCRIPTION', align:'center'},
			{name:'RUN_CD', hidden:true},
			{name:'RUN_NM', align:'center'},
			{name:'USE_YN', align:'center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
function buildSearchParam() {
	var Obj = {};

		
		Obj.__use_yn    = $('input[name="C_USE_YN"]:checked').val();
		Obj.__server_id = $('#C_SERVER_ID').val();;
		Obj.__agent_id  = '*';
	return Obj;
}

$(document).ready(function() {
	
	//getServer($("#C_SERVER_ID"),1);

	$('#btnCancel').click(function (e) {
		$('#searchVal').val("");
		$("#searchVal").focus();
	});

	
	$('#btnAdd').click(function (e) {

		$('#F_AGENT_ID').val("");
		$('#F_AGENT_NM').val("");
		$('#F_AGENT_PORT').val("");
		$('#F_ACCOUNT_CD').val("");
		$('#F_PASSWORD').val("");
		$('#F_PATH').val("");
		$('#F_DESCRIPTION').val("");
		//getAllSelectOptions("F_RUN_CD","RUN_CD","");
		$('#F_RUN_CD').val("");
		$('#F_RUN_CD option:eq(0)').prop("selected", true);

		$('input[name="F_USE_YN"]').val(["Y"]);
		$('#CRUD').val("C");
	});
	
	$('#btnDelete').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.server_id   = $("C_SERVER_ID").val();
		obj.agent_id   = $("input#F_AGENT_ID").val();
		obj.crud        = "D";
		
		console.log('C_SERVER_ID:'+ obj.server_id);
		console.log('F_AGENT_ID:'+ obj.agent_id);
		console.log('sCrud:'+ obj.crud);

		$("#SetAgentForm").ajaxForm({
			url : 'SetAgent',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(obj)},
			success: function(json_data) {
				alert("정상적으로처리 되었습니다");
				$('#F_AGENT_ID').val("");
				$('#F_AGENT_NM').val("");
				$('#F_AGENT_PORT').val("");
				$('#F_ACCOUNT_CD').val("");
				$('#F_PASSWORD').val("");
				$('#F_PATH').val("");
				$('#F_DESCRIPTION').val("");
				$('#F_RUN_CD').val("");
				$('input[name="F_USE_YN"]').val(["Y"]);
				$('#CRUD').val("C");
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
		$("#SetAgentForm").submit() ;
	});
	
	$('#btnSave').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.server_id   = $("#C_SERVER_ID").val();
		obj.agent_id    = $("input#F_AGENT_ID").val();
		obj.agent_nm    = $("input#F_AGENT_NM").val();
		obj.agent_port  = $("input#F_AGENT_PORT").val();
		obj.account_cd  = $("input#F_ACCOUNT_CD").val();
		obj.password    = $("input#F_PASSWORD").val();
		obj.path        = $("input#F_PATH").val();
		obj.description = $("textarea#F_DESCRIPTION").val();
		obj.run_cd      = $("select#F_RUN_CD").val();
		obj.run_nm      = $('select#F_RUN_CD option:selected').text();
		obj.use_yn      = $('input[name="F_USE_YN"]:checked').val()
		obj.crud        = $("input#CRUD").val();
		
		console.log('sCrud:'+ obj.crud);
		console.log('server_id:'+ obj.server_id); 
		console.log('agent_id:'+ obj.agent_id); 
		console.log('agent_nm:'+ obj.agent_nm); 
		console.log('agent_port:'+ obj.agent_port); 
		console.log('account_cd:'+ obj.account_cd); 
		console.log('password:'+ obj.password); 
		console.log('path:'+ obj.path); 
		console.log('description:'+ obj.description); 
		console.log('run_cd:'+ obj.run_cd); 
		console.log('run_nm:'+ obj.run_nm); 
		console.log('use_yn:'+ obj.use_yn); 

		
		if(obj.agent_nm == '' || obj.agent_nm == null){
			alert("[알림] Agent명을 입력하세요");
			$("input#F_AGENT_NM").focus();
		    return;
		}

		if(obj.run_cd == '' || obj.run_cd == null){
			alert("[알림] Agent 종류를선택하세요");
			$("input#F_RUN_CD").focus();
		    return;
		}
		
		$("#SetAgentForm").ajaxForm({
			url : 'SetAgent',
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
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("Error");
		    		}
		    	}
			}
		});	
		$("#SetAgentForm").submit() ;
	});

	$('input[name="C_USE_YN"]').val(["Y"]);
	
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
		jsonObj.__agent_id  = selectdRow.AGENT_ID;
		jsonObj.__rows      = currentRow+"";
		jsonObj.__page      = currentPage+"" ;
		
		$.ajax({
			url: 'GetAgentList',    
			data: {param:JSON.stringify(jsonObj)},
			type:"post",
			dataType:"json",
			success: function(json_data) {
	
				console.log(json_data);
				
		        if(json_data.result == 'OK') {

			        $('#F_AGENT_ID').val(json_data.rows[0].AGENT_ID);
			        $('#F_AGENT_NM').val(json_data.rows[0].AGENT_NM);
			        $('#F_AGENT_PORT').val(json_data.rows[0].AGENT_PORT);
			        $('#F_ACCOUNT_CD').val(json_data.rows[0].ACCOUNT_CD);
			        $('#F_PASSWORD').val(json_data.rows[0].PASSWORD);
			        $('#F_PATH').val(json_data.rows[0].PATH);
			        $('#F_DESCRIPTION').val(json_data.rows[0].DESCRIPTION);
			        //getAllSelectOptions("F_RUN_CD","RUN_CD",json_data.rows[0].RUN_CD);
			        $('#F_RUN_CD').val(json_data.rows[0].RUN_CD);
		    		$('input[name="F_USE_YN"]').val([json_data.rows[0].USE_YN]);
		    		$('#CRUD').val("U");
			        $('#F_AGENT_ID').attr("readonly", true ); //설정
			        
				} else {
					console.log(json_data.result); 
				}
			}
		});	
	}
