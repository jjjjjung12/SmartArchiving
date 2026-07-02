var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetCnvJobList',
		pageSize: 10,
		columns: [
			{name:'OUTPUT1_SERVERID', hidden:true},
			{name:'OUTPUT2_SERVERID', hidden:true},
			{name:'OUTPUT1_SERVERNM', hidden:true},
			{name:'OUTPUT2_SERVERNM', hidden:true},
			{name:'JOB_ID', hidden:true},
			{name:'JOB_CD', align:'center'},
			{name:'JOB_NM', align:'center'},
			{name:'OUTPUT1_TABLEID', hidden:true},
			{name:'OUTPUT2_TABLEID', hidden:true},
			{name:'SOURCE_PATH', hidden:true},
			{name:'SOURCE_FILE', hidden:true},
			{name:'OUTPUT1_INFO_PATH', hidden:true},
			{name:'OUTPUT2_INFO_PATH', hidden:true},
			{name:'OUTPUT1_INFO_FILE', hidden:true},
			{name:'OUTPUT2_INFO_FILE', hidden:true},
			{name:'JOB_TM', hidden:true},
			{name:'SCHEDULE_YN', hidden:true},
			{name:'SCHEDULE_VALUE', hidden:true},
			{name:'ERROR_PATH', hidden:true},
			{name:'SOURCE_DEL_YN', hidden:true},
			{name:'LAST_COL_YN', hidden:true},
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
		Obj.__job_id  = '*';
		Obj.__job_nm  =  $('#C_JOB_NM').val();
	return Obj;
}

$(document).ready(function() {
	
	getServer($("#C_SERVER_ID"),1);

	

	function loadInfo(selectedRow, currentRow, currentPage) {

		var selectdRow = selectedRow;

		console.log(selectdRow);

		$('#ROWID').val(rowId);
		
		jsonObj = {};

		jsonObj.__use_yn    = $('input[name="C_USE_YN"]:checked').val();
		jsonObj.__server_id = $('#C_SERVER_ID').val();;
		jsonObj.__job_id    = selectdRow.JOB_ID;
		jsonObj.__job_nm    = selectdRow.JOB_NM;
		jsonObj.__rows      = currentRow+"";
		jsonObj.__page      = currentPage+"" ;
		
		$.ajax({
			url: 'GetCnvJobList',    
			data: {param:JSON.stringify(jsonObj)},
			type:"post",
			dataType:"json",
			success: function(json_data) {
	
				if(json_data.result == 'OK') {
		        	
		        	$('#F_OUTPUT1_SERVERID').val(json_data.rows[0].OUTPUT1_SERVERID);
		    		$('#F_OUTPUT2_SERVERID').val(json_data.rows[0].OUTPUT2_SERVERID);
		    		$('#F_OUTPUT1_SERVERNM').val(json_data.rows[0].OUTPUT1_SERVERNM);
		    		$('#F_OUTPUT2_SERVERNM').val(json_data.rows[0].OUTPUT2_SERVERNM);
		        	
			        $('#F_JOB_ID').val(json_data.rows[0].JOB_ID);
			        $('#F_JOB_CD').val(json_data.rows[0].JOB_CD);
			        $('#F_JOB_NM').val(json_data.rows[0].JOB_NM);
			        
			        $('#F_TABLE_ID').val(json_data.rows[0].OUTPUT1_TABLEID);
			        $('#F_TABLE_ID2').val(json_data.rows[0].OUTPUT2_TABLEID);
			        
			        $('#F_TABLE_CD').val(json_data.rows[0].TABLE_CD);
			        $('#F_TABLE_NM').val(json_data.rows[0].TABLE_CD);
			        $('#F_TABLE_CD2').val(json_data.rows[0].TABLE_CD2);
			        $('#F_TABLE_NM2').val(json_data.rows[0].TABLE_CD2);
			        
			        $('#F_SOURCE_PATH').val(json_data.rows[0].SOURCE_PATH);
			        $('#F_SOURCE_FILE').val(json_data.rows[0].SOURCE_FILE);
			        
			        $('#F_INFO_PATH').val(json_data.rows[0].OUTPUT1_INFO_PATH);
			        $('#F_INFO_FILE').val(json_data.rows[0].OUTPUT1_INFO_FILE);
			        
			        $('#F_INFO_PATH2').val(json_data.rows[0].OUTPUT2_INFO_PATH);
			        $('#F_INFO_FILE2').val(json_data.rows[0].OUTPUT2_INFO_FILE);
			        
			        
			        var min = parseInt(json_data.rows[0].JOB_TM.substr(0, 2));
			        var sec = parseInt(json_data.rows[0].JOB_TM.substr(2, 2));
			        
			        console.log(min);
			        
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
	};	
   
	
		$('#btnAdd').click();
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);		
	});
	
	$('#btnAdd').click(function (e) {
		
		$('#F_OUTPUT1_SERVERID').val("");
		$('#F_OUTPUT2_SERVERID').val("");
		$('#F_OUTPUT1_SERVERNM').val("");
		$('#F_OUTPUT2_SERVERNM').val("");
		
		
		$('#F_JOB_ID').val("");
		$('#F_JOB_CD').val("");
		$('#F_JOB_NM').val("");
		$('#F_TABLE_ID').val("");
		$('#F_TABLE_CD').val("");
		$('#F_TABLE_NM').val("");
		$('#F_TABLE_ID2').val("");
		$('#F_TABLE_CD2').val("");
		$('#F_TABLE_NM2').val("");
		
		$('#F_SOURCE_PATH').val("");
		$('#F_SOURCE_FILE').val("");
		
		$('#F_INFO_PATH').val("");
		$('#F_INFO_PATH2').val("");
		$('#F_INFO_FILE').val("");
		$('#F_INFO_FILE2').val("");
		
		$('#F_JOB_TM_HAF').val("0");
		$('#F_JOB_TM_MIN').val("00");
		$('#F_JOB_TM_SEC').val("00");
		
		$('input[name="F_LAST_COL_YN"]').val(["N"]);
		$('input[name="F_SCHEDULE_YN"]').val(["N"]);
		$('#F_SCHEDULE_VALUE').val("");
		$('#F_ERROR_PATH').val("");
		
		$('input[name="F_SOURCE_DEL_YN"]').val(["N"]);
		$('input[name="F_USE_YN"]').val(["Y"]);
		$('#CRUD').val("C");
	});
	
	$('#btnDelete').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.job_id  = $("input#F_JOB_ID").val();
		obj.crud    = "D";
		
		$("#SetJobForm").ajaxForm({
			url : 'SetCnvJob',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
					alert("정상적으로처리 되었습니다");
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
					$('#CRUD').val("C");
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
		obj.output1_server_id   = $("input#F_OUTPUT1_SERVERID").val();
		obj.output2_server_id   = $("input#F_OUTPUT2_SERVERID").val();
		
		obj.job_id      = $("input#F_JOB_ID").val();
		obj.job_cd      = $("input#F_JOB_CD").val();
		obj.job_nm      = $("input#F_JOB_NM").val();
		
		obj.table_id    = $("input#F_TABLE_ID").val();
		obj.table_id2   = $("input#F_TABLE_ID2").val();
		
		obj.source_path = $("input#F_SOURCE_PATH").val();
		obj.source_file = $("input#F_SOURCE_FILE").val();
		
		obj.info_path   = $("input#F_INFO_PATH").val();
		obj.info_path2   = $("input#F_INFO_PATH2").val();
		
		obj.info_file   = $("input#F_INFO_FILE").val();
		obj.info_file2   = $("input#F_INFO_FILE2").val();
  
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
		
		console.log('sCrud:'+ obj.crud);
		console.log('server_id:'+ obj.server_id); 
		console.log('job_id:'+ obj.job_id); 
		console.log('job_cd:'+ obj.job_cd); 
		console.log('job_nm:'+ obj.job_nm); 
		console.log('table_id:'+ obj.table_id); 
		console.log('source_path:'+ obj.source_path); 
		console.log('source_file:'+ obj.source_file); 
		console.log('info_path:'+ obj.info_path); 
		console.log('info_file:'+ obj.info_file); 
		console.log('job_tm:'+ obj.job_tm); 
		console.log('separator:'+ obj.separator); 
		console.log('last_col_yn:'+ obj.last_col_yn); 
		console.log('schedule_yn:'+ obj.schedule_yn); 
		console.log('schedule_value:'+ obj.schedule_value); 
		console.log('error_path:'+ obj.error_path); 
		console.log('source_del_yn:'+ obj.source_del_yn); 
		console.log('use_yn:'+ obj.use_yn); 
		
		
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
		
		if(obj.job_tm == ''){
			alert("[알림] 작업시각을입력하세요");
			$("input#F_JOB_TM_HAF").focus();
		    return;
		}
		
		$("#SetJobForm").ajaxForm({
			url : 'SetCnvJob',
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
		gubun = "1";
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
	    	$('#F_TABLE_ID').val(data.TABLE_ID);
			$('#F_TABLE_CD').val(data.TABLE_CD);
			$('#F_TABLE_NM').val(data.TABLE_NM);
	    }
	    else{
	    	$('#F_TABLE_ID2').val(data.TABLE_ID);
			$('#F_TABLE_CD2').val(data.TABLE_CD);
			$('#F_TABLE_NM2').val(data.TABLE_NM);
	    }
	    
		jQuery('#modal-7').modal("hide");
	});
	
	
	var gubun = "";
	
	$('input[name="C_USE_YN"]').val(["Y"]);

	$('input[name="F_LAST_COL_YN"]').val(["N"]);
	$('input[name="F_SCHEDULE_YN"]').val(["N"]);
	$('input[name="F_SOURCE_DEL_YN"]').val(["N"]);
	$('input[name="F_USE_YN"]').val(["Y"]);
//if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);	
	
	$("#searchVal").focus();
