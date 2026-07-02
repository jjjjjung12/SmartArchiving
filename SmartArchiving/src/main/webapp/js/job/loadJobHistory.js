var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetLoadJobHistory',
		pageSize: 10,
		columns: [
			{name:'SERVER_ID', hidden:true},
			{name:'JOB_ID', hidden:true},
			{name:'JOB_NM', align:'center'},
			{name:'SOURCE_FILE', hidden:true},
			{name:'JOB_TM', align:'center'},
			{name:'SCAN_REG_TM', hidden:true},
			{name:'SCAN_STAT_CD', hidden:true},
			{name:'SCAN_MSG', align:'center'},
			{name:'SOURCE_SIZE', hidden:true},
			{name:'SCAN_RECORD', hidden:true},
			{name:'LOAD_REG_TM', align:'center'},
			{name:'LOAD_END_TM', hidden:true},
			{name:'LOAD_RECORD', hidden:true},
			{name:'LOAD_MSG', align:'center'},
			{name:'LOAD_STAT_CD', hidden:true},
			{name:'SCAN_PROC_CNT', hidden:true},
			{name:'LOAD_PROC_CNT', hidden:true},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
function buildSearchParam() {
	var Obj = {};

		
		var _sFDate = $('#C_FROM').val();
		var _sTDate =  $('#C_TO').val();
		
		var _serverId = $('#C_SERVER_ID').val();
		var _jobNm    = $('#C_JOB_NM').val();

		console.log(_sFDate);
		console.log(_sTDate);
		console.log(_serverId);
		console.log(_jobNm);
		
		Obj.__server_id    = _serverId;
		Obj.__job_id       = '*';
		Obj.__job_nm  	   = _jobNm;
		Obj.__job_tm_from  = _sFDate;
		Obj.__job_tm_to    = _sTDate;
		Obj.__stat         = 'A';
	return Obj;
}

$(document).ready(function() {
	
	getServer($("#C_SERVER_ID"),1);
	
	$('input[name="F_STAT"]').val(["A"]);
	
	
	
	function loadInfo(selectedRow, currentRow, currentPage) {

		var selectdRow = selectedRow;

		//console.log('selectdRow:'+selectdRow);
		console.log('selectdRow.SERVER_ID:'+selectdRow.SERVER_ID);
		console.log('selectdRow.JOB_ID:'+selectdRow.JOB_ID);
		
		console.log('selectdRow.SCAN_MSG_CD:'+selectdRow.SCAN_STAT_CD);		 
		console.log('selectdRow.SCAN_MSG:'+selectdRow.SCAN_MSG);
		console.log('selectdRow.LOAD_RECORD:'+selectdRow.LOAD_RECORD);
		console.log('selectdRow.LOAD_STAT_CD:'+selectdRow.LOAD_STAT_CD);
		console.log('selectdRow.LOAD_MSG:'+selectdRow.LOAD_MSG);
		        
		
        $('#F_JOB_ID').val(selectdRow.JOB_ID);
        $('#F_JOB_TM').val(selectdRow.JOB_TM);
        $('#F_SOURCE_FILE').val(selectdRow.SOURCE_FILE);
        $('#F_SOURCE_SIZE').val(selectdRow.SOURCE_SIZE );
        $('#F_SCAN_REG_TM').val(selectdRow.JOB_TM );
        $('#F_SCAN_END_TM').val(selectdRow.SCAN_REG_TM);
        $('#F_SCAN_RECORD').val(selectdRow.SCAN_RECORD);
        
        $('#F_SCAN_STAT_CD').val(selectdRow.SCAN_STAT_CD);
        $('#F_SCAN_MSG').val(selectdRow.SCAN_MSG);
      
        $('#F_LOAD_REG_TM').val(selectdRow.LOAD_REG_TM);
        $('#F_LOAD_END_TM').val(selectdRow.LOAD_END_TM);
        $('#F_LOAD_STAT_CD').val(selectdRow.LOAD_STAT_CD);   
        $('#F_LOAD_MSG').val(selectdRow.LOAD_MSG);
        $('#F_LOAD_RECORD').val(selectdRow.LOAD_RECORD);
        $('#F_SCAN_PROC_CNT').val(selectdRow.SCAN_PROC_CNT);
        $('#F_LOAD_PROC_CNT').val(selectdRow.LOAD_PROC_CNT);
        
       

        
        
/*
		$('input[name="F_LAST_COL_YN"]').val([json_data.rows[0].LAST_COL_YN]);
		$('input[name="F_SCHEDULE_YN"]').val([json_data.rows[0].SCHEDULE_YN]);
		$('input[name="F_SOURCE_DEL_YN"]').val([json_data.rows[0].SOURCE_DEL_YN]);
		$('input[name="F_USE_YN"]').val([json_data.rows[0].USE_YN]);
*/		
        $('#F_JOB_ID').attr("readonly", true ); //설정
		
		/*
		$('#ROWID').val(currentRow || '');
		
		jsonObj = {};
		
		jsonObj.__server_id    = selectdRow.SERVER_ID;
		jsonObj.__job_id       = selectdRow.JOB_ID;
		jsonObj.__job_nm  	   = "";
		jsonObj.__job_tm_from  = "";
		jsonObj.__job_tm_to    = "";
		jsonObj.__stat         = "";
		jsonObj.__rows         = "20";
		jsonObj.__page         = "1" ;
		jsonObj.__rows      = currentRow+"";
		jsonObj.__page      = currentPage+"" ;		

		$.ajax({
			url: 'GetLoadJobHistory',    
			data: {param:JSON.stringify(jsonObj)},
			type:"post",
			dataType:"json",
			success: function(json_data) {
	
				console.log(json_data);
				
		        if(json_data.result == 'OK') {

		        	//console.log('json_data.rows[0].FILTER_STAT_CD:'+json_data.rows[0].FILTER_STAT_CD);
		        	console.log('json_data.rows[0].LOAD_STAT_CD:'+json_data.rows[0].LOAD_STAT_CD);
		        	
			        $('#F_JOB_ID').val(json_data.rows[0].JOB_ID);
			        $('#F_JOB_TM').val(json_data.rows[0].JOB_TM);
			        $('#F_SOURCE_FILE').val(json_data.rows[0].SOURCE_FILE);
			        $('#F_SOURCE_SIZE').val(toMoneyPoint(json_data.rows[0].SOURCE_SIZE,0));
			        $('#F_SCAN_REG_TM').val(toDatePoint(json_data.rows[0].SCAN_REG_TM));
			        $('#F_SCAN_END_TM').val(toDatePoint(json_data.rows[0].SCAN_END_TM));
			      //  $('#F_SCAN_STAT_CD').val(json_data.rows[0].SCAN_STAT_CD);
			      //  getAllSelectOptions("F_SCAN_STAT_CD","SCAN_STAT_CD",json_data.rows[0].SCAN_STAT_CD);
			        $('#F_SCAN_MSG_CD').val(json_data.rows[0].SCAN_MSG_CD);
			        $('#F_SCAN_MSG').val(json_data.rows[0].SCAN_MSG);
			       // $('#F_FILTER_REG_TM').val(toDatePoint(json_data.rows[0].FILTER_REG_TM));
			       //$('#F_FILTER_END_TM').val(toDatePoint(json_data.rows[0].FILTER_END_TM));
			      //  getAllSelectOptions("F_FILTER_STAT_CD","FILTER_STAT_CD",json_data.rows[0].FILTER_STAT_CD);
			        //$('#F_FILTER_MSG_CD').val(json_data.rows[0].FILTER_MSG_CD);
			        //$('#F_FILTER_MSG').val(json_data.rows[0].FILTER_MSG);
			        $('#F_LOAD_REG_TM').val(toDatePoint(json_data.rows[0].LOAD_REG_TM));
			        $('#F_LOAD_END_TM').val(toDatePoint(json_data.rows[0].LOAD_END_TM));
			        $('#F_LOAD_STAT_CD').val(json_data.rows[0].LOAD_STAT_CD);
			      //  getAllSelectOptions("F_LOAD_STAT_CD","LOAD_STAT_CD",json_data.rows[0].LOAD_STAT_CD);
			        $('#F_LOAD_MSG_CD').val(json_data.rows[0].LOAD_MSG_CD);
			        $('#F_LOAD_MSG').val(json_data.rows[0].LOAD_MSG);
			        $('#F_SCAN_STAT_NM').val(json_data.rows[0].SCAN_STAT_NM);
			       // $('#F_FILTER_STAT_NM').val(json_data.rows[0].FILTER_STAT_NM);
			        $('#F_LOAD_STAT_NM').val(json_data.rows[0].LOAD_STAT_NM);
			        
			        

		    		$('input[name="F_LAST_COL_YN"]').val([json_data.rows[0].LAST_COL_YN]);
		    		$('input[name="F_SCHEDULE_YN"]').val([json_data.rows[0].SCHEDULE_YN]);
		    		$('input[name="F_SOURCE_DEL_YN"]').val([json_data.rows[0].SOURCE_DEL_YN]);
		    		$('input[name="F_USE_YN"]').val([json_data.rows[0].USE_YN]);
			        $('#F_JOB_ID').attr("readonly", true ); //설정
			        
				} else {
					console.log(json_data.result); 
				}
			}
		});	
		*/
	};	
   
	
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);		
	});
	
	$('#btnAdd').click(function (e) {

		$('#F_SERVER_ID' ).val($('#C_SERVER_ID'  ).val());
		$('#F_SERVER_NM' ).val($('#C_SERVER_ID  option:selected').text());
		$('#F_JOB_ID').val("");
		$('#F_JOB_NM').val("");
		$('#F_TABLE_ID').val("1");
		$('#F_TABLE_CD').val("1");
		$('#F_TABLE_NM').val("1");
		$('#F_SOURCE_PATH').val("");
		$('#F_SOURCE_FILE').val("");
		$('#F_JOB_TM_HAF').val("0");
		$('#F_JOB_TM_MIN').val("00");
		$('#F_JOB_TM_SEC').val("00");
		$('#F_SEPARATOR').val("");
		$('input[name="F_LAST_COL_YN"]').val(["Y"]);
		$('input[name="F_SCHEDULE_YN"]').val(["Y"]);
		$('input[name="F_SOURCE_DEL_YN"]').val(["Y"]);
		$('input[name="F_USE_YN"]').val(["Y"]);
		$('#CRUD').val("C");
		$('#F_SERVER_ID'  ).attr("readonly", true); //설정
		$('#F_JOB_ID'  ).attr("readonly", true); //설정
	});
	
	$('#btnDelete').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.job_id  = $("input#F_JOB_ID").val();
		obj.crud    = "D";
		
		console.log('F_JOB_ID:'+ obj.job_id);
		console.log('sCrud:'+ obj.crud);

		$("#SetJobForm").ajaxForm({
			url : 'SetJob',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(obj)},
			success: function(json_data) {
				alert("정상적으로처리 되었습니다");
				$('#F_SERVER_ID').val($('#C_SERVER_ID'  ).val());
				$('#F_SERVER_NM').val("");
				$('#F_JOB_ID').val("");
				$('#F_JOB_NM').val("");
				$('#F_TABLE_ID').val("");
				$('#F_TABLE_CD').val("");
				$('#F_TABLE_NM').val("");
				$('#F_SOURCE_PATH').val("");
				$('#F_SOURCE_FILE').val("");
				$('#F_JOB_TM_HAF').val("0");
				$('#F_JOB_TM_MIN').val("00");
				$('#F_JOB_TM_SEC').val("00");
				$('#F_SEPARATOR').val("");
				$('input[name="F_LAST_COL_YN"]').val(["Y"]);
				$('input[name="F_SCHEDULE_YN"]').val(["Y"]);
				$('input[name="F_SOURCE_DEL_YN"]').val(["Y"]);
				$('input[name="F_USE_YN"]').val(["Y"]);
				$('#CRUD').val("C");
				$('#F_SERVER_ID'  ).attr("readonly", true); //설정	
				$('#F_JOB_ID'  ).attr("readonly", true); //설정	
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
		$("#SetJobForm").submit() ;
	});
	
	$('#btnSave').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.server_id   = $("input#F_SERVER_ID").val();
		obj.job_id      = $("input#F_JOB_ID").val();
		obj.job_nm      = $("input#F_JOB_NM").val();
		obj.table_id    = $("input#F_TABLE_ID").val();
		obj.source_path = $("input#F_SOURCE_PATH").val();
		obj.source_file = $("input#F_SOURCE_FILE").val();

		job_haf = $("select#F_JOB_TM_HAF").val();
		job_min = parseInt($("select#F_JOB_TM_MIN").val());
		job_sec = $("select#F_JOB_TM_SEC").val();
		
		if(job_haf == '1') {
			job_min = job_min + 12;
		}

		obj.job_tm      = job_min + "" + job_sec;
		obj.separator   = $("select#F_SEPARATOR").val();
		obj.last_col_yn = $('input[name="F_LAST_COL_YN"]:checked').val()
		obj.schedule_yn = $('input[name="F_SCHEDULE_YN"]:checked').val()
		obj.source_del_yn = $('input[name="F_SOURCE_DEL_YN"]:checked').val()
		obj.use_yn      = $('input[name="F_USE_YN"]:checked').val()
		obj.crud        = $("input#CRUD").val();
		
		console.log('sCrud:'+ obj.crud);
		console.log('server_id:'+ obj.server_id); 
		console.log('job_id:'+ obj.job_id); 
		console.log('job_nm:'+ obj.job_nm); 
		console.log('table_id:'+ obj.table_id); 
		console.log('source_path:'+ obj.source_path); 
		console.log('source_file:'+ obj.source_file); 
		console.log('job_tm:'+ obj.job_tm); 
		console.log('separator:'+ obj.separator); 
		console.log('last_col_yn:'+ obj.last_col_yn); 
		console.log('schedule_yn:'+ obj.schedule_yn); 
		console.log('source_del_yn:'+ obj.source_del_yn); 
		console.log('use_yn:'+ obj.use_yn); 

		
		if(obj.job_nm == ''){
			alert("[알림] Job명을 입력하세요");
			$("input#F_JOB_NM").focus();
		    return;
		}

		$("#SetJobForm").ajaxForm({
			url : 'SetJob',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
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
		$("#SetJobForm").submit() ;
	});

	
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	$("#searchVal").focus();
