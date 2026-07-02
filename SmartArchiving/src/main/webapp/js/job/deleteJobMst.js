var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetDeleteJobList',
		pageSize: 10,
		columns: [
			{name:'SRC_SERVER_ID', hidden:true},
			{name:'JOB_ID', hidden:true},
			{name:'DAEMON_ID', hidden:true},
			{name:'DAEMON_CD', align:'center'},
			{name:'DAEMON_NM', align:'center'},
			{name:'JOB_NAME', align:'center'},
			{name:'SRC_TABLE_ID', hidden:true},
			{name:'SRC_TABLE_CD', hidden:true},
			{name:'SRC_TABLE_NM', hidden:true},
			{name:'DES_SERVER_ID', hidden:true},
			{name:'DES_TABLE_ID', hidden:true},
			{name:'DES_TABLE_CD', hidden:true},
			{name:'DES_TABLE_NM', hidden:true},
			{name:'DES_FILE_PATH', hidden:true},
			{name:'DES_FLAG_PATH', hidden:true},
			{name:'BASE_DATE', hidden:true},
			{name:'DELIMITER', hidden:true},
			{name:'DAEMON_RESTART_YN', hidden:true},
			{name:'JOB_SCHEDULE', hidden:true},
			{name:'JOB_TYPE', hidden:true},
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
		Obj.__job_id  = '*';
		Obj.__job_nm  =  $('#C_JOB_NM').val();
	return Obj;
}

$(document).ready(function() {
	
	;	
   
	
		$('#btnAdd').click();
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);		
	});
	
	$('#btnAdd').click(function (e) {

		$('#F_SRC_SERVER_ID').val("");
		$('#F_DES_SERVER_ID').val("");
		
		$('#F_SRC_SERVER_NM').val("");
    	$('#F_DES_SERVER_NM').val("");
		
		$('#F_JOB_ID').val("");
		$('#F_JOB_NM').val("");
		$('#F_DAEMON_CD').val("");
		$('#F_DAEMON_ID').val("");
		$('#F_DAEMON_NM').val("");
		$('#F_SRC_TABLE_ID').val("");
		$('#F_SRC_TABLE_CD').val("");
		$('#F_SRC_TABLE_NM').val("");
		$('#F_DES_TABLE_ID').val("");
		$('#F_DES_TABLE_CD').val("");
		$('#F_DES_TABLE_NM').val("");
		$('#F_DES_FILE_PATH').val("");
		$('#F_DES_FLAG_PATH').val("");
		$('#F_BASE_DATE').val("");
		$('#F_JOB_SCHEDULE').val("");
		$('#F_JOB_TYPE').val("");
		$('#F_DELIMITER').val("");
		$('#F_DELIMITER option:eq(0)').prop("selected", true);

		$('input[name="F_USE_YN"]').removeAttr("checked");
		
		$('#CRUD').val("C");
		$('#F_DAEMON_CD'  ).attr("readonly", false); //설정
	});
	
	$('#btnDelete').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.job_id  = $("input#F_JOB_ID").val();
		obj.crud    = "D";
		
		$("#SetJobForm").ajaxForm({
			url : 'SetDeleteJob',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
					alert("정상적으로처리 되었습니다");
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
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
		obj.job_id      = $("input#F_JOB_ID").val();
		obj.job_nm      = $("input#F_JOB_NM").val();
		
		obj.src_server_id   	= $("input#F_SRC_SERVER_ID").val();
		obj.des_server_id   	= $("input#F_DES_SERVER_ID").val();
		
		obj.daemon_id      	= $("input#F_DAEMON_ID").val();
		obj.daemon_cd      	= $("input#F_DAEMON_CD").val();
		obj.daemon_nm      	= $("input#F_DAEMON_NM").val();
		obj.src_table_id    = $("input#F_SRC_TABLE_ID").val();
		obj.src_table_cd    = $("input#F_SRC_TABLE_CD").val();
		obj.src_table_nm    = $("input#F_SRC_TABLE_NM").val();
		obj.des_table_id    = $("input#F_DES_TABLE_ID").val();
		obj.des_table_cd    = $("input#F_DES_TABLE_CD").val();
		obj.des_table_nm    = $("input#F_DES_TABLE_NM").val();
		
		obj.des_file_path	= $("input#F_DES_FILE_PATH").val();
		obj.des_flag_path	= $("input#F_DES_FLAG_PATH").val();
		obj.base_date   	= $("input#F_BASE_DATE").val();
		obj.delimiter   	= $("select#F_DELIMITER").val();
		
		obj.use_yn 				= $('input[name="F_USE_YN"]:checked').val();
		obj.job_schedule		= $("input#F_JOB_SCHEDULE").val();
		obj.job_type			= $("input#F_JOB_TYPE").val();
		obj.crud        		= $("input#CRUD").val();
		
		if(obj.job_nm == ''){
			alert("[알림] 작업명을 입력하세요");
			$("input#F_DAEMON_CD").focus();
		    return;
		}
		
		if(obj.daemon_cd == ''){
			alert("[알림] 데몬ID를입력하세요");
			$("input#F_DAEMON_CD").focus();
		    return;
		}
		
		if(obj.use_yn == '' || typeof(obj.use_yn) == "undefined"){
			alert("[알림] 사용여부를선택하세요");
			$("input#F_USE_YN").focus();
		    return;
		}


		if(obj.table_id == ''){
			alert("[알림] 테이블을 선택하세요");
			$("input#F_TABLE_ID").focus();
		    return;
		}
		
		$("#SetJobForm").ajaxForm({
			url : 'SetDeleteJob',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
 					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
 					alert("정상적으로처리 되었습니다");
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
		    			alert("Error");
		    		}
		    	}
			}
		});	
		$("#SetJobForm").submit() ;
	});

	
	$('#showAjaxModal').click(function (e) {
		showTableSelector();
	});
	
	$('#showAjaxModal2').click(function (e) {
		gubun = "2";
		showTableSelector();
	});
	
	function showTableSelector() {
		
		jQuery('#modal-7').modal({backdrop: 'static', keyboard: false});
		
		console.log('..showAjaxModal...........................');

		
		var Obj = new Object();
		
		Obj.__gb       = 'A';
		Obj.__table_id = '*';
		Obj.__table_nm = '';
		
	
		oTable = $('#uTable').DataTable({
			sDom: 'Bfrtip',
 			select: true,
			processing: true,
			serverSide: false,
			searching: false,
			destroy: true,
			pagingType: "full_numbers",
			pageLength: 10,
			ajax: {
		        url:"GetTableListPop",
				dataType:"json", 
				type: "post",
				data : {param:JSON.stringify(Obj)}
			},
			language: {
				"decimal":        "",
			    "emptyTable":     "데이타가 없습니다.",
			    "info":           "_START_ ~ _END_ ( 전체 _TOTAL_ 건)",
			    "infoEmpty":      "전체 0 건",
			    "infoFiltered":   "(filtered from _MAX_ total entries)",
			    "infoPostFix":    "",
			    "thousands":      ",",
			    "lengthMenu":     "Show _MENU_ entries",
			    "loadingRecords": "Loading...",
			    "processing":     "Processing...",
			    "search":         "Search:",
			    "zeroRecords":    "해당 데이터가 없습니다.",
			    "paginate": {
			        "first":      "처음",
			        "last":       "마지막",
			        "next":       "다음",
			        "previous":   "이전"
			    },
			    "aria": {
			        "sortAscending":  ": activate to sort column ascending",
			        "sortDescending": ": activate to sort column descending"
			    }
	        },		
            columns : [
                {"data":"TABLE_ID"},		 	 
                {"data":"TABLE_CD"},		 	 
                {"data":"TABLE_NM"}
     	    ],
     	    columnDefs: [
     	        { targets: [0], visible: false}
     	    ]	      	    
		});
		
	};
	
	$('#uTable tbody').on('dblclick', 'tr', function () {
	    var data = oTable.row( this ).data();
	    if(gubun == "1"){
	    	$('#F_SRC_TABLE_ID').val(data.TABLE_ID);
			$('#F_SRC_TABLE_CD').val(data.TABLE_CD);
			$('#F_SRC_TABLE_NM').val(data.TABLE_NM);
	    }
	    else{
	    	$('#F_DES_TABLE_ID').val(data.TABLE_ID);
			$('#F_DES_TABLE_CD').val(data.TABLE_CD);
			$('#F_DES_TABLE_NM').val(data.TABLE_NM);
	    }
	    
	    jQuery('#modal-7').modal("hide");
	});
	
	var gubun = "";
	
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);	
	
	$("#searchVal").focus();

function loadInfo(selectedRow, currentRow, currentPage) {

		var selectdRow = selectedRow;

		console.log(selectdRow);

		$('#ROWID').val(rowId);
		
		jsonObj = {};

		jsonObj.__use_yn    = $('input[name="C_USE_YN"]:checked').val();
		jsonObj.__job_id    = selectdRow.JOB_ID;
		jsonObj.__job_nm    = selectdRow.JOB_NM;
		jsonObj.__rows      = currentRow+"";
		jsonObj.__page      = currentPage+"" ;
		
		$.ajax({
			url: 'GetDeleteJobList',    
			data: {param:JSON.stringify(jsonObj)},
			type:"post",
			dataType:"json",
			success: function(json_data) {
	
				if(json_data.result == 'OK') {
		        	$('#F_SRC_SERVER_ID').val(json_data.rows[0].SRC_SERVER_ID);
		        	$('#F_DES_SERVER_ID').val(json_data.rows[0].DES_SERVER_ID);
		        	
		        	$('#F_SRC_SERVER_NM').val(json_data.rows[0].SRC_SERVER_NM);
		        	$('#F_DES_SERVER_NM').val(json_data.rows[0].DES_SERVER_NM);
		        	
		        	$('#F_JOB_ID').val(json_data.rows[0].JOB_ID);
		        	$('#F_JOB_NM').val(json_data.rows[0].JOB_NAME);
		        	
			        $('#F_DAEMON_CD').val(json_data.rows[0].DAEMON_CD);
			        $('#F_DAEMON_ID').val(json_data.rows[0].DAEMON_ID);
			        $('#F_DAEMON_NM').val(json_data.rows[0].DAEMON_NM);
			        $('#F_SRC_TABLE_ID').val(json_data.rows[0].SRC_TABLE_ID);
			        $('#F_SRC_TABLE_CD').val(json_data.rows[0].SRC_TABLE_CD);
			        $('#F_SRC_TABLE_NM').val(json_data.rows[0].SRC_TABLE_NM);
			        
			        $('#F_DES_TABLE_ID').val(json_data.rows[0].DES_TABLE_ID);
			        $('#F_DES_TABLE_CD').val(json_data.rows[0].DES_TABLE_CD);
			        $('#F_DES_TABLE_NM').val(json_data.rows[0].DES_TABLE_NM);
			        
			        $('#F_DES_FILE_PATH').val(json_data.rows[0].DES_FILE_PATH);
			        $('#F_DES_FLAG_PATH').val(json_data.rows[0].DES_FLAG_PATH);
			        $('#F_BASE_DATE').val(json_data.rows[0].BASE_DATE);
			         
			        $('#F_DELIMITER').val(json_data.rows[0].DELIMITER);
			        
		    		$('#F_JOB_SCHEDULE').val(json_data.rows[0].JOB_SCHEDULE);
		    		$('#F_JOB_TYPE').val(json_data.rows[0].JOB_TYPE);
		    		
		    		$('input[name="F_USE_YN"]').val([json_data.rows[0].USE_YN]);
			        $('#F_DAEMON_CD').attr("readonly", true ); //설정
			        
				} else {
					console.log(json_data.result); 
				}
			}
		});	
		$('#CRUD').val("U")
	}
