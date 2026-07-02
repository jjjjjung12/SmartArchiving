var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetLoadJobList',
		pageSize: 10,
		columns: [
			{name:'SERVER_ID', hidden:true},
			{name:'JOB_ID', hidden:true},
			{name:'JOB_CD', align:'center'},
			{name:'JOB_NM', align:'center'},
			{name:'TABLE_ID', hidden:true},
			{name:'SOURCE_PATH', hidden:true},
			{name:'SOURCE_FILE', hidden:true},
			{name:'INFO_PATH', hidden:true},
			{name:'INFO_FILE', hidden:true},
			{name:'JOB_TM', hidden:true},
			{name:'SCHEDULE_YN', hidden:true},
			{name:'SCHEDULE_VALUE', hidden:true},
			{name:'ERROR_PATH', hidden:true},
			{name:'SOURCE_DEL_YN', hidden:true},
			{name:'LAST_COL_YN', hidden:true},
			{name:'USE_YN', align:'center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowClick: function(row, idx, inst) { if (typeof loadInfo === 'function') loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
function buildSearchParam() {
	var Obj = {};

		
		Obj.__use_yn    = $('input[name="C_USE_YN"]:checked').val();
		Obj.__server_id = $('#C_SERVER_ID').val();
		Obj.__job_id  = '*';
		Obj.__job_nm  =  $('#C_JOB_NM').val();
	return Obj;
}

// 테이블선택 팝업 그리드(sangju ArchiveGrid)
var _uTableGrid = null;
function ensureTableSelectorGrid() {
	if (_uTableGrid) return _uTableGrid;
	_uTableGrid = ArchiveGrid.createLocal({
		columns: [
			{name:'TABLE_ID', hidden:true},
			{name:'TABLE_CD', align:'center'},
			{name:'TABLE_NM'}
		],
		bodySelector: '#uTableBody',
		pagingSelector: '#uTablePaging',
		countSelector: '#uTableCnt',
		pageSize: 10,
		data: [],
		onRowDblClick: function(row) {
			$('#F_TABLE_ID').val(row.TABLE_ID);
			$('#F_TABLE_CD').val(row.TABLE_CD);
			$('#F_TABLE_NM').val(row.TABLE_NM);
			jQuery('#modal-7').hide();
		}
	});
	return _uTableGrid;
}

$(document).ready(function() {
	
	getServer($("#C_SERVER_ID"),1);

	$('#btnAdd').click(function (e) {

		$('#F_JOB_ID').val("");
		$('#F_JOB_CD').val("");
		$('#F_JOB_NM').val("");
		$('#F_TABLE_ID').val("");
		$('#F_TABLE_CD').val("");
		$('#F_TABLE_NM').val("");
		$('#F_SOURCE_PATH').val("");
		$('#F_SOURCE_FILE').val("");
		$('#F_INFO_PATH').val("");
		$('#F_INFO_FILE').val("");
		$('#F_JOB_TM_HAF').val("0");
		$('#F_JOB_TM_MIN').val("00");
		$('#F_JOB_TM_SEC').val("00");
		$('#F_SEPARATOR').val("");
		$('#F_SEPARATOR option:eq(0)').prop("selected", true);
		$('input[name="F_LAST_COL_YN"]').val(["N"]);
		$('input[name="F_SCHEDULE_YN"]').val(["N"]);
		$('#F_SCHEDULE_VALUE').val("");
		$('#F_ERROR_PATH').val("");
		
		$('input[name="F_SOURCE_DEL_YN"]').val(["N"]);
		$('input[name="F_USE_YN"]').val(["N"]);
		$('#CRUD').val("C");
		$('#F_JOB_CD'  ).attr("readonly", false); //설정
	});
	
	$('#btnDelete').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.job_id  = $("input#F_JOB_ID").val();
		obj.crud    = "D";
		
		$("#SetJobForm").ajaxForm({
			url : 'SetLoadJob',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);	
					alert("정상적으로처리 되었습니다");
					$('#btnAdd').click();
				}
				else{
					alert("삭제가 실패하였습니다");
				}
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
		obj.server_id   = $("#C_SERVER_ID").val();
		obj.job_id      = $("input#F_JOB_ID").val();
		obj.job_cd      = $("input#F_JOB_CD").val();
		obj.job_nm      = $("input#F_JOB_NM").val();
		obj.table_id    = $("input#F_TABLE_ID").val();
		obj.source_path = $("input#F_SOURCE_PATH").val();
		obj.source_file = $("input#F_SOURCE_FILE").val();
		obj.info_path   = $("input#F_INFO_PATH").val();
		obj.info_file   = $("input#F_INFO_FILE").val();
  
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
		obj.schedule_value = $("input#F_SCHEDULE_VALUE").val();
		obj.error_path = $("input#F_ERROR_PATH").val();
		obj.source_del_yn = $('input[name="F_SOURCE_DEL_YN"]:checked').val()
		obj.use_yn      = $('input[name="F_USE_YN"]:checked').val()
		obj.crud        = $("input#CRUD").val();
		
		if(obj.job_cd == ''){
			alert("[알림] 작업ID를입력하세요");
			$("input#F_JOB_CD").focus();
		    return;
		}

		if(obj.job_nm == ''){
			alert("[알림] 작업명을 입력하세요");
			$("input#F_JOB_NM").focus();
		    return;
		}


		if(obj.table_id == ''){
			alert("[알림] 테이블을 선택하세요");
			$("input#F_TABLE_ID").focus();
		    return;
		}
		
		$("#SetJobForm").ajaxForm({
			url : 'SetLoadJob',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);	
					alert("정상적으로처리 되었습니다");
					$('#btnAdd').click();
				}
				else{
					alert("저장이 실패하였습니다");
				}
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			
		    		}
		    	}
			}
		});	
		$("#SetJobForm").submit() ;
	});

	
	$('#showAjaxModal').click(function (e) {
		showTableSelector();
	});
	
	function showTableSelector() {
		
		jQuery('#modal-7').show();
		
		ensureTableSelectorGrid();
		
		var Obj = new Object();
		Obj.__gb       = 'A';
		Obj.__table_id = '*';
		Obj.__table_nm = '';
		
		$.ajax({
			url: 'GetTableListPop',
			dataType: 'json',
			type: 'post',
			data: { param: JSON.stringify(Obj) },
			success: function(json_data) {
				var rows = (json_data && json_data.data) ? json_data.data : [];
				ArchiveGrid.setRows(_uTableGrid, rows);
			},
			error: function() {
				ArchiveGrid.setRows(_uTableGrid, []);
			}
		});
	};
	
	$('input[name="C_USE_YN"]').val(["Y"]);

	$('input[name="F_LAST_COL_YN"]').val(["N"]);
	$('input[name="F_SCHEDULE_YN"]').val(["N"]);
	$('input[name="F_SOURCE_DEL_YN"]').val(["N"]);
	$('input[name="F_USE_YN"]').val(["Y"]);
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);	
	
	$("#searchVal").focus();

});

function loadInfo(selectedRow, currentRow, currentPage) {

		var selectdRow = selectedRow;

		jsonObj = {};

		jsonObj.__use_yn    = $('input[name="C_USE_YN"]:checked').val();
		jsonObj.__server_id = $('#C_SERVER_ID').val();;
		jsonObj.__job_id    = selectdRow.JOB_ID;
		jsonObj.__job_nm    = selectdRow.JOB_NM;
		jsonObj.__rows      = currentRow+"";
		jsonObj.__page      = currentPage+"" ;
		
		$.ajax({
			url: 'GetLoadJobList',    
			data: {param:JSON.stringify(jsonObj)},
			type:"post",
			dataType:"json",
			success: function(json_data) {
		        if(json_data.result == 'OK') {

			        $('#F_JOB_ID').val(json_data.rows[0].JOB_ID);
			        $('#F_JOB_CD').val(json_data.rows[0].JOB_CD);
			        $('#F_JOB_NM').val(json_data.rows[0].JOB_NM);
			        $('#F_TABLE_ID').val(json_data.rows[0].TABLE_ID);
			        $('#F_TABLE_CD').val(json_data.rows[0].TABLE_CD);
			        $('#F_TABLE_NM').val(json_data.rows[0].TABLE_NM);
			        $('#F_SOURCE_PATH').val(json_data.rows[0].SOURCE_PATH);
			        $('#F_SOURCE_FILE').val(json_data.rows[0].SOURCE_FILE);
			        $('#F_INFO_PATH').val(json_data.rows[0].INFO_PATH);
			        $('#F_INFO_FILE').val(json_data.rows[0].INFO_FILE);
			        
			        //getAllSelectOptions("F_RUN_CD","RUN_CD",json_data.rows[0].RUN_CD);
			        $('#F_SEPARATOR').val(json_data.rows[0].SEPARATOR);

			        var min = parseInt(json_data.rows[0].JOB_TM.substr(0, 2));
			        var sec = parseInt(json_data.rows[0].JOB_TM.substr(2, 2));
			        
			        if (min < 12) {
				        $('#F_JOB_TM_HAF').val("0");
			        } else {
			        	$('#F_JOB_TM_HAF').val("1");
			        	min = min - 12;
			        }
			        
			        $('#F_JOB_TM_MIN').val(("0"+min).substr(("0"+min).length-2,2));
			        $('#F_JOB_TM_SEC').val(("0"+sec).substr(("0"+sec).length-2,2));
			        
		    		$('input[name="F_LAST_COL_YN"]').val([json_data.rows[0].LAST_COL_YN]);
		    		$('input[name="F_SCHEDULE_YN"]').val([json_data.rows[0].SCHEDULE_YN]);
		    		$('#F_SCHEDULE_VALUE').val(json_data.rows[0].SCHEDULE_VALUE);
		    		$('#F_ERROR_PATH').val(json_data.rows[0].ERROR_PATH);
		    		$('input[name="F_SOURCE_DEL_YN"]').val([json_data.rows[0].SOURCE_DEL_YN]);			        
		    		
		    		$('input[name="F_USE_YN"]').val([json_data.rows[0].USE_YN]);
			        
				} else {
					console.log(json_data.result); 
				}
			}
		});	
		$('#CRUD').val("U")
	}
