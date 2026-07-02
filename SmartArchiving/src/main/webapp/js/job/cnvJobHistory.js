var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetCnvJobHistory',
		pageSize: 10,
		columns: [
			{name:'SERVER_ID', hidden:true},
			{name:'JOB_ID', hidden:true},
			{name:'JOB_NM', align:'center'},
			{name:'CONVERT_FILE', hidden:true},
			{name:'JOB_TM', align:'center'},
			{name:'CONVERT_REG_TM', hidden:true},
			{name:'CONVERT_STAT_CD', hidden:true},
			{name:'CONVERT_MSG', align:'center'},
			{name:'CONVERT_SIZE', hidden:true},
			{name:'CONVERT_RECORD', hidden:true},
			{name:'OUTPUT1_INFO_FILE', hidden:true},
			{name:'OUTPUT2_INFO_FILE', hidden:true},
			{name:'PROC_CNT', hidden:true},
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
		var _jobNm    = $('#C_JOB_NM').val();
		
		console.log(_sFDate);
		console.log(_sTDate);
		console.log(_jobNm);
		
		Obj.__job_id       = '*';
		Obj.__job_nm  	   = _jobNm;
		Obj.__info_file_nm = $('#C_INFO_FILE_NM').val();
		Obj.__job_tm_from  = _sFDate;
		Obj.__job_tm_to    = _sTDate;
		Obj.__stat         = 'A';
	return Obj;
}

$(document).ready(function() {
	$('input[name="F_STAT"]').val(["A"]);

	

	function loadInfo(selectedRow, currentRow, currentPage) {

		var selectdRow = selectedRow;

		//console.log('selectdRow:'+selectdRow);
		console.log('selectdRow.SERVER_ID:'+selectdRow.SERVER_ID);
		console.log('selectdRow.JOB_ID:'+selectdRow.JOB_ID);
		
		console.log('selectdRow.CONVERT_STAT_CD:'+selectdRow.CONVERT_STAT_CD);		 
		console.log('selectdRow.CONVERT_MSG:'+selectdRow.CONVERT_MSG);
		console.log('selectdRow.CONVERT_RECORD:'+selectdRow.CONVERT_RECORD);
		        
		
        $('#F_JOB_ID').val(selectdRow.JOB_ID);
        $('#F_JOB_TM').val(selectdRow.JOB_TM);
        $('#F_CONVERT_FILE').val(selectdRow.CONVERT_FILE);
        $('#F_CONVERT_SIZE').val(selectdRow.CONVERT_SIZE );
        $('#F_REG_TM').val(selectdRow.JOB_TM );
        $('#F_END_TM').val(selectdRow.CONVERT_REG_TM);
        $('#F_RECORD').val(selectdRow.CONVERT_RECORD);
        
        $('#F_STAT_CD').val(selectdRow.CONVERT_STAT_CD);
        $('#F_MSG').val(selectdRow.CONVERT_MSG);
        
        $('#F_INFO_FILE').val(selectdRow.OUTPUT1_INFO_FILE);
        $('#F_INFO_FILE2').val(selectdRow.OUTPUT2_INFO_FILE);
        $('#F_PROC_CNT').val(selectdRow.PROC_CNT);
      
        $('#F_JOB_ID').attr("readonly", true ); //설정		

	};	
   
	
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);		
	});
	
	$('#btnAdd').click(function (e) {

		$('#F_JOB_ID').val("");
		$('#F_JOB_NM').val("");
		$('#F_TABLE_ID').val("1");
		$('#F_TABLE_CD').val("1");
		$('#F_TABLE_NM').val("1");
		$('#F_CONVERT_PATH').val("");
		$('#F_CONVERT_FILE').val("");
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
		obj.source_file = $("input#F_CONVERT_FILE").val();

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
