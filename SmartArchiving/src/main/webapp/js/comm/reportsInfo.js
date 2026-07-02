var REPORT_LIST_COLS = [
	{name:'REPORT_ID', align:'center'},
	{name:'REPORT_CD', align:'center'},
	{name:'REPORT_NM', align:'center'},
	{name:'REPORT_TYPE', align:'center'},
	{name:'DETAIL_PAGE', hidden:true},
	{name:'DESCRIPTION', hidden:true}
];
var TABLE_LIST_COLS = [
	{name:'TABLE_ID', align:'center'},
	{name:'TABLE_CD', align:'center'},
	{name:'TABLE_NM', align:'center'},
	{name:'TABLE_JOIN_NM', align:'center'},
	{name:'USE_YN', hidden:true}
];
var TABLE_ATTR_COLS = [
	{name:'TABLE_ID', hidden:true},
	{name:'ATTR_CD', align:'center'},
	{name:'ATTR_NM', align:'center'},
	{name:'ATTR_TYPE_NM', align:'center'},
	{name:'ATTR_SIZE', align:'center'},
	{name:'DECIMAL_SIZE', hidden:true},
	{name:'SORT_INDEX', hidden:true},
	{name:'WHERE_INDEX', hidden:true},
	{name:'OUTPUT_INDEX', hidden:true},
	{name:'ATTR_NULL_YN', hidden:true}
];
var REPORT_ATTR_COLS = [
	{name:'REPORT_ID', hidden:true},
	{name:'ATTR_CD', align:'center'},
	{name:'ATTR_NM', align:'center'},
	{name:'ATTR_TYPE_NM', align:'center'},
	{name:'ATTR_SIZE', align:'center'},
	{name:'DECIMAL_SIZE', align:'center'},
	{name:'SORT_INDEX', align:'center'},
	{name:'WHERE_INDEX', align:'center'},
	{name:'OUTPUT_INDEX', align:'center'},
	{name:'DATE_TYPE_YN', hidden:true},
	{name:'TIME_TYPE_YN', hidden:true},
	{name:'ATTR_NULL_YN', align:'center'}
];

var lastTableRow = null;

var gridReportTable = ArchiveGrid.create({
	url: 'GetReportList',
	bodySelector: '#gridBodyReport',
	pagingSelector: '.wr_page_report',
	pageSize: 10,
	columns: REPORT_LIST_COLS,
	getPostData: reportListParam
});
ArchiveGrid.register('jqGridReportTable', gridReportTable);

var gridTable = ArchiveGrid.create({
	url: 'GetTableList',
	bodySelector: '#gridBodyTable',
	pagingSelector: '.wr_page_table',
	pageSize: 10,
	columns: TABLE_LIST_COLS,
	getPostData: tableListParam
});

var gridLocal2 = ArchiveGrid.createLocal({
	bodySelector: '#gridBodyTableAttr',
	pagingSelector: '.wr_page_tattr',
	pageSize: 25,
	columns: TABLE_ATTR_COLS,
	onRowClick: function(row) { selectedRow = row; }
});
ArchiveGrid.register('jqGrid2', gridLocal2);

var gridLocal1 = ArchiveGrid.createLocal({
	bodySelector: '#gridBody2',
	pagingSelector: '.wr_page2',
	pageSize: 25,
	columns: REPORT_ATTR_COLS,
	onRowClick: function(row) { selectedRow = row; },
	onRowDblClick: function(row, idx) {
		selectedRow = row;
		$('#ROWID2').val(idx);
		$('#F_ATTR_CD').val(row.ATTR_CD || '');
		$('#F_ATTR_NM').val(row.ATTR_NM || '');
		$('#F_ATTR_SIZE').val(row.ATTR_SIZE || '');
		$('#F_DECIMAL_SIZE').val(row.DECIMAL_SIZE || '0');
		$('#F_SORT_INDEX').val(row.SORT_INDEX || row.ATTR_ORDER || '');
		$('#F_WHERE_INDEX').val(row.WHERE_INDEX || '');
		$('#F_OUTPUT_INDEX').val(row.OUTPUT_INDEX || '');
		$('#CRUD2').val('AU');
	}
});
ArchiveGrid.register('jqGrid1', gridLocal1);
ArchiveGrid.wireLocal(gridLocal1);
ArchiveGrid.wireLocal(gridLocal2);

function reportListParam() {
	return {
		__serverId: $('#C_SERVER_ID').val(),
		__use_yn: '',
		__gb: 'A',
		__report_id: '*',
		__report_nm: '',
	};
}

function tableListParam() {
	return {
		__serverId: $('#C_SERVER_ID').val(),
		__use_yn: $('input[name="C_USE_YN"]:checked').val(),
		__gb: 'A',
		__table_id: '*',
		__table_nm: '',
	};
}

function buildSearchParam() {
	return tableListParam();
}

function searchTableDetail(obj) {
	$.ajax({
		url: 'GetTableList',
		type: 'POST',
		dataType: 'json',
		data: { param: JSON.stringify(obj) },
		success: function(data) {
			ArchiveGrid.loadRows(gridLocal2, data.rows || []);
		}
	});
}

function loadReportInfo2(rowId) {
	var selectdRow = ArchiveGrid.getRowData(gridReportTable, rowId);
	$('#ROWID1').val(rowId);
	$('#F_REPORT_ID').val(selectdRow.REPORT_ID);
	$('#F_REPORT_CD').val(selectdRow.REPORT_CD);
	$('#F_REPORT_NM').val(selectdRow.REPORT_NM);
	$('#F_REPORT_ID').attr('readonly', true);
	if (selectdRow.REPORT_TYPE == 'J') {
		$('#F_REPORT_JSON').prop('checked', true);
		$('#F_REPORT_FLAT').prop('checked', false);
		$('#F_REPORT_STAT').prop('checked', false);
	} else if (selectdRow.REPORT_TYPE == 'F') {
		$('#F_REPORT_JSON').prop('checked', false);
		$('#F_REPORT_FLAT').prop('checked', true);
		$('#F_REPORT_STAT').prop('checked', false);
	} else if (selectdRow.REPORT_TYPE == 'S') {
		$('#F_REPORT_JSON').prop('checked', false);
		$('#F_REPORT_FLAT').prop('checked', false);
		$('#F_REPORT_STAT').prop('checked', true);
	} else {
		$('#F_REPORT_JSON').prop('checked', false);
		$('#F_REPORT_FLAT').prop('checked', false);
		$('#F_REPORT_STAT').prop('checked', false);
	}
	$('#F_DETAIL_PAGE').val(selectdRow.DETAIL_PAGE);
	$('#F_DESCRIPTION').val(selectdRow.DESCRIPTION);
	$('#CRUD1').val('AU');
}

function loadReportAttr(rowId) {
	var selectdRow = ArchiveGrid.getRowData(gridReportTable, rowId);
	$('#F_ATTR_CD').val('');
	$('#F_ATTR_NM').val('');
	$('#F_ATTR_TYPE_CD').val('');
	$('#F_ATTR_SIZE').val('');
	$('#F_DECIMAL_SIZE').val('0');
	$('#F_ATTR_CD').attr('readonly', false);
	$('#CRUD2').val('AC');
	var jsonObj = {
		__serverId: $('#C_SERVER_ID').val(),
		__use_yn: '',
		__gb: 'R',
		__report_id: selectdRow.REPORT_ID,
	};
	$.ajax({
		url: 'GetReportList',
		type: 'POST',
		dataType: 'json',
		data: { param: JSON.stringify(jsonObj) },
		success: function(data) {
			ArchiveGrid.loadRows(gridLocal1, data.rows || []);
		}
	});
}

function loadInfo1(rowId) {
	lastTableRow = selectedRow;
	$('#F_ATTR_CD').attr('readonly', false);
	$('#CRUD2').val('AC');
	if (!lastTableRow) return;
	searchTableDetail({
		__serverId: $('#C_SERVER_ID').val(),
		__use_yn: $('input[name="C_USE_YN"]:checked').val(),
		__gb: 'C',
		__table_id: lastTableRow.TABLE_ID,
		__table_nm: '*',
	});
	$('#CRUD1').val('U');
}

function loadInfo2(rowId, currentRow, currentPage, table_id, table_cd, table_nm, table_alias) {
	var checkedJ = document.querySelector('#JOIN_CHECKBOX').checked;
	var selectdRow = selectedRow;
	if (checkedJ) {
		$('#F_JOIN_TABLE_CD').val(table_cd);
		$('#F_JOIN_TABLE_ALIAS_NM').val(table_alias);
		$('#F_JOIN_COLUMN').val(selectdRow.ATTR_CD);
	} else {
		$('#ROWID2').val(rowId);
		$('#F_ATTR_CD').val(selectdRow.ATTR_CD);
		$('#F_ATTR_NM').val(selectdRow.ATTR_NM);
		$('#F_ATTR_TYPE_CD').val(selectdRow.ATTR_TYPE_CD);
		$('#F_ATTR_TYPE_NM').val(selectdRow.ATTR_TYPE_NM);
		$('input[name="F_ATTR_NULL_YN"]').val([selectdRow.ATTR_NULL_YN]);
		$('#F_ATTR_SIZE').val(selectdRow.ATTR_SIZE);
		$('#F_TABLE_ID').val(table_id);
		$('#F_TABLE_CD').val(table_cd);
		$('#F_TABLE_NM').val(table_nm);
		$('#F_TABLE_ALIAS_NM').val(table_alias);
	}
	$('#CRUD2').val('AU');
}

var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	
	getServer($("#C_SERVER_ID"),1);

	gridReportTable.onRowDblClick = function(row, idx) {
		loadReportInfo2(idx);
		loadReportAttr(idx);
	};
	gridTable.onRowDblClick = function(row, idx) {
		selectedRow = row;
		loadInfo1(idx);
	};
	gridLocal2.onRowDblClick = function(row, idx) {
		selectedRow = row;
		if (lastTableRow) {
			loadInfo2(idx, null, null, lastTableRow.TABLE_ID, lastTableRow.TABLE_CD, lastTableRow.TABLE_NM, lastTableRow.TABLE_JOIN_NM);
		}
	};

	function reportRefreshData() {
		$('#CRUD1').val('U');
		ArchiveGrid.load(gridReportTable);
	}

	function tableRefreshData() {
		$('#CRUD1').val('U');
		ArchiveGrid.load(gridTable);
	}

	$('#btnQuery').off('click.reports').on('click.reports', function(e) {
		e.preventDefault();
		tableRefreshData();
	});
	$('#btnReportQuery').off('click.reports').on('click.reports', function(e) {
		e.preventDefault();
		reportRefreshData();
	});

		$('#btnReportAdd').click(function (e) {
			$('#F_REPORT_ID'   ).val("");
			$('#F_REPORT_CD'   ).val("");
			$('#F_REPORT_NM'   ).val("");		
			$('#F_DESCRIPTION').val("");		
			$('#F_REPORT_JSON').prop("checked",false);
			$('#F_REPORT_FLAT').prop("checked",false);
			$('#F_REPORT_STAT').prop("checked",false);
			$('#F_DETAIL_PAGE').val("");
			
			$('#CRUD1'         ).val("C");
			$('#F_REPORT_ID'   ).attr("readonly", true); //설정
		});
		
		$('#btnReportDel').click(function (e) {
			var formData = new FormData();
			
			var obj = new Object();
			obj.report_id   = $("input#F_REPORT_ID").val();
			obj.crud        = "D";
			
			console.log('F_REPORT_ID:'+ obj.table_id);
			console.log('sCrud:'+ obj.crud);

			if(obj.report_id == ''){
				alert("[알림] 조회화면 영문명을 입력하세요");
				$("input#F_TABLE_CD").focus();
			    return;
			}
			
			$("#SetReportForm").ajaxForm({
				url : 'SetReport',
				dataType:'json',
				type: 'post',
				data : {param:JSON.stringify(obj)},
				success: function(json_data) {
					alert("정상적으로처리 되었습니다");
					$('#F_REPORT_ID'   ).val("");				
					$('#CRUD'         ).val("C");
					$('#F_REPORT_ID'   ).attr("readonly", false); //설정	
					ArchiveGrid.load(gridReportTable);
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
			$("#SetReportForm").submit() ;
		});
		
		
		
		
		$('#btnReportSave').click(function (e) {
			var formData = new FormData();

			var obj = new Object();
			obj.report_id     = $("input#F_REPORT_ID").val();
			obj.report_cd     = $("input#F_REPORT_CD").val();
			obj.report_nm     = $("input#F_REPORT_NM").val();
			obj.description  = $("input#F_DESCRIPTION").val();		
			obj.crud         = $("input#CRUD1").val();
			obj.serverId     = $('#C_SERVER_ID').val();		
			obj.report_type     = $('input[name=F_REPORT_TYPE]:checked').val();		
			obj.detail_page     = $("input#F_DETAIL_PAGE").val();
			
			console.log('sCrud:'+ obj.crud);
			console.log('F_REPORT_ID:'+ obj.report_id); 
			console.log('F_REPORT_CD:'+ obj.report_cd); 
			console.log('F_REPORT_NM:'+ obj.report_nm); 
			console.log('C_SERVER_ID:'+ obj.serverId); 
			console.log('F_DESCRIPTION:'+ obj.description); 		
			console.log('F_REPORT_TYPE:'+ obj.report_type); 		
			console.log('CRUD:'+ obj.crud); 
						
			if(obj.serverId == 0){
				alert("[알림] 서버를 선택해주세요");			
			    return;
			}
			
			if(obj.report_cd == ''){
				alert("[알림] 조회화면 영문명을 입력하세요");
				$("input#F_TABLE_CD").focus();
			    return;
			}

			if(obj.report_nm == ''){
				alert("[알림] 조회화면 한글명을 입력하세요");
				$("input#F_TABLE_NM").focus();
			    return;
			}

			$("#SetReportForm").ajaxForm({
				url : 'SetReport',
				dataType:'json',
				type: 'post',
				data:{param:JSON.stringify(obj)},
				success: function(json_data) {
	 				if (obj.crud == "U" ) {		
						console.log('grid update.........');
						var rowData = ArchiveGrid.getRowData(gridLocal1, $('#ROWID1').val());
						rowData.REPORT_ID= obj.report_id; 
						rowData.REPORT_CD= obj.report_cd; 
						rowData.REPORT_NM= obj.report_nm; 
						rowData.DESCRIPTION= obj.description; 
						ArchiveGrid.setRowData(gridLocal1, $('#ROWID1').val(), rowData)
					} else {
						console.log('grid append.........');
						var ids = ArchiveGrid.getRows(gridLocal1).map(function(r,i){return i+1;});
						var idsLen = ids.length + 1;
						ArchiveGrid.addRowData(gridLocal1, {});	
						var rowData = ArchiveGrid.getRowData(gridLocal1, idsLen);
						rowData.REPORT_ID= obj.report_id; 
						rowData.REPORT_CD= obj.report_cd; 
						rowData.REPORT_NM= obj.report_nm; 
						rowData.DESCRIPTION= obj.description; 
						ArchiveGrid.setRowData(gridLocal1, idsLen, rowData)
					} 

					//$('#btnAdd').click();				
					alert("업데이트가 정상적으로처리 되었습니다");
					$('#btnReportQuery').click();
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
			$("#SetReportForm").submit() ;
		});
		
	
	
    
		tableRefreshData();	
	});
		
    
 
	$('#btnDel').click(function (e) {
		var formData = new FormData();

		var obj = new Object();
		obj.table_id     = $("input#F_TABLE_ID").val();
		obj.table_cd     = $("input#F_TABLE_CD").val();
		obj.table_nm     = $("input#F_TABLE_NM").val();
		obj.save_preq_cd = $("select#F_SAVE_PREQ_CD").val();
		obj.save_preq_nm = $('select#F_SAVE_PREQ_CD option:selected').text();
		obj.save_preq    = $("input#F_SAVE_PREQ").val();
		obj.exp_preq_cd = $("select#F_EXP_PREQ_CD").val();
		obj.exp_preq_nm = $('select#F_EXP_PREQ_CD option:selected').text();
		obj.exp_preq    = $("input#F_EXP_PREQ").val();		
		obj.description  = $("textarea#F_DESCRIPTION").val();
		obj.use_yn       = $('input[name="F_USE_YN"]:checked').val()
		obj.crud         = "AD";
		obj.serverId     = $('#C_SERVER_ID').val();		
		
		console.log('sCrud:'+ obj.crud);
		console.log('F_TABLE_ID:'+ obj.table_id); 
		console.log('F_TABLE_CD:'+ obj.table_cd); 
		console.log('F_TABLE_NM:'+ obj.table_nm); 
		console.log('C_SERVER_ID:'+ obj.serverId); 
		console.log('F_SAVE_PREQ_CD:'+ obj.save_preq_cd); 
		console.log('F_SAVE_PREQ_NM:'+ obj.save_preq_nm); 
		console.log('F_SAVE_PREQ:'+ obj.save_preq); 
		console.log('F_EXP_PREQ_CD:'+ obj.save_preq_cd); 
		console.log('F_EXP_PREQ_NM:'+ obj.save_preq_nm); 
		console.log('F_EXP_PREQ:'+ obj.save_preq); 
		console.log('F_DESCRIPTION:'+ obj.description); 
		console.log('F_USE_YN:'+ obj.use_yn); 
		console.log('CRUD:'+ obj.crud); 
		
		//alert("[알림] 해당 테이블및관련컴럼을삭제합니다.");
		let isConfirmAction = confirm("[알림] 해당 테이블및관련컴럼을삭제합니다\n진행하시겠습니까?");
        if(isConfirmAction)
        {
    		$("#SetTableForm").ajaxForm({
    			url : 'SetTable',
    			dataType:'json',
    			type: 'post',
    			data:{param:JSON.stringify(obj)},
    			success: function(json_data) {
     
					console.log('grid delete.........');
					$('#F_TABLE_ID'   ).val("");
					$('#F_TABLE_CD'   ).val("");
					$('#F_TABLE_NM'   ).val("");
					$('#F_SAVE_PREQ'  ).val("");
					$('#F_EXP_PREQ'  ).val("");
					$('#F_DESCRIPTION').val("");
					$('#F_USE_YN'     ).val("");
					$('#F_SAVE_PREQ_CD').val("");
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
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
    		$("#SetTableForm").submit() ;        	
        }      

	});
	
	
	
	
	//###################  #Excel Export
	$("#btnExpExcel").click(function(){
		var saveFileName = $('select#C_SERVER_ID option:selected').text() +"."+ $('#F_TABLE_CD'   ).val();
		console.log( "saveFileName:" + saveFileName);
		//exportExcel($("#jqGrid2"), saveFileName);   // exportExcel('jqgird이름','파일명)
		
		var ids = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});
		var idsLen = ids.length ;
		
		if(idsLen > 0)
		{
			
		    var tableid = $("input#F_TABLE_ID").val();
		    if(tableid.length > 0)
		    {		
				ArchiveGrid.exportToExcel(gridLocal2, {
					includeLabels : true,
					includeGroupHeader : true,
					includeFooter: true,
					fileName : saveFileName+".xlsx",
					maxlength : 40 // maxlength for visible string data 
				})
		    }else
		    {
		    	alert("테이블이 선택되지 않았습니다");	
		    }	
		}else
		{
		   alert("다운로드할데이터가 없습니다.");	
		}
	});
	

	$('#btnAttrAdd').on( 'click', function () {
		$('#F_ATTR_CD'   ).val("");
		$('#F_ATTR_NM'   ).val("");
//		getAllSelectOptions("F_ATTR_TYPE_CD","ATTR_TYPE_CD","");   
		$('#F_ATTR_TYPE_CD'   ).val("");
		$('#F_ATTR_TYPE_CD option:eq(0)').prop("selected", true);
		$('#F_ATTR_SIZE'  ).val("");
		$('#F_DECIMAL_SIZE'  ).val("0");
	    $('#F_ATTR_CD'   ).attr("readonly", false ); //설정
        $('#CRUD2'   ).val("AC");
	 });

	
	$('#btnAttrDel').click(function (e) {
		var formData = new FormData();

		var obj = new Object();
		obj.table_id     = $("input#F_TABLE_ID").val();
		obj.attr_cd      = $("input#F_ATTR_CD").val();
		obj.crud         = "AD";
		
		console.log('sCrud:'+ obj.crud);
		console.log('F_TABLE_ID:'+ obj.table_id); 
		console.log('F_ATTR_CD:'+ obj.attr_cd); 
		
		if(obj.table_id == ''){
			alert("[알림] 테이블을 먼저 선택하세요");
			$("input#F_ATTR_CD").focus();
		    return;
		}
		
		if(obj.attr_cd == ''){
			alert("[알림] 컬럼을먼저 선택하세요");
			$("input#F_ATTR_CD").focus();
		    return;
		}
		
		$("#SetATTRForm").ajaxForm({
			url : 'SetTable',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				alert("정상적으로처리 되었습니다");
				$('#F_ATTR_CD').val("");
				$('#F_ATTR_NM').val("");
				$('#F_ATTR_SIZE').val("");
				$('#F_DECIMAL_SIZE').val("");
				$('#F_ATTR_TYPE_CD').val("");
				$('#F_ATTR_NULL_YN').val("");
				$('#CRUD2').val("AC");
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
		$("#SetATTRForm").submit() ;
	});

	
	
	$('#btnSaveExcel').click(function (e) {
		
		console.log("btnSaveExcel");
		
		//var formData = new FormData();

		var ids = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});
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
					console.log('F_DECIMAL_SIZE:'+ obj.decimal_size); 
					console.log('F_ATTR_TYPE_CD:'+ obj.attr_type_cd); 
					console.log('F_ATTR_TYPE_NM:'+ obj.attr_type_nm);  
					console.log('F_ATTR_NULL_YN:'+ obj.attr_null_yn);  
					console.log('F_SORT_INDEX:'+ obj.sort_index); 
					console.log('F_WHERE_INDEX:'+ obj.where_index); 
					console.log('F_OUTPUT_INDEX:'+ obj.output_index); 
					
					$("#SetATTRForm").ajaxForm({
						url : 'SetTable',
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
	        	alert("저장할 내용이 없습니다.");
        }
		
	});
			
	
	$('#btnAttrSave').click(function (e) {
		var formData = new FormData();

		var obj = new Object();
		obj.table_id     = $("input#F_TABLE_ID").val();
		obj.attr_cd      = $("input#F_ATTR_CD").val();
		obj.attr_nm      = $("input#F_ATTR_NM").val();
		obj.attr_size    = $("input#F_ATTR_SIZE").val();
		obj.decimal_size = $("input#F_DECIMAL_SIZE").val();
		obj.attr_type_cd = $("select#F_ATTR_TYPE_CD").val();
		obj.attr_type_nm = $('select#F_ATTR_TYPE_CD option:selected').text();
		obj.attr_null_yn = $('input[name="F_ATTR_NULL_YN"]:checked').val();
		obj.sort_index   = $ ("input#F_SORT_INDEX").val();
		obj.where_index  = $ ("input#F_WHERE_INDEX").val();
		obj.output_index = $ ("input#F_OUTPUT_INDEX").val();
		obj.crud         = $("input#CRUD2").val();
		obj.date_type_yn = $('input[name="F_DATE_TYPE_YN"]:checked').val();
		obj.time_type_yn = $('input[name="F_TIME_TYPE_YN"]:checked').val();
		
		console.log('sCrud:'+ obj.crud);
		console.log('F_TABLE_ID:'+ obj.table_id); 
		console.log('F_ATTR_CD:'+ obj.attr_cd); 
		console.log('F_ATTR_NM:'+ obj.attr_nm); 
		console.log('F_ATTR_SIZE:'+ obj.attr_size); 
		console.log('F_DECIMAL_SIZE:'+ obj.decimal_size); 
		console.log('F_ATTR_TYPE_CD:'+ obj.attr_type_cd); 
		console.log('F_ATTR_TYPE_NM:'+ obj.attr_type_nm); 
 
		console.log('F_SORT_INDEX:'+ obj.sort_index); 
		console.log('F_WHERE_INDEX:'+ obj.where_index); 
		console.log('F_OUTPUT_INDEX:'+ obj.output_index); 
		
		if(obj.table_id == ''){
			alert("[알림] 테이블을 먼저 선택하세요");
			$("input#F_ATTR_CD").focus();
		    return;
		}

		if(obj.attr_nm == ''){
			alert("[알림] 컬럼명을 입력하세요");
			$("input#F_ATTR_NM").focus();
		    return;
		}

		if(Number(obj.attr_size) <= 0){
			alert("[알림] 길이가 너무 작습니다.");
			$("input#F_ATTR_SIZE").focus();
		    return;
		}

		if(Number(obj.attr_size) > 999){
			alert("[알림] 길이가 너무 큽니다");
			$("input#F_ATTR_SIZE").focus();
		    return;
		}

		if(Number(obj.decimal_size) < 0){
			alert("[알림] 소수자리가 너무 작습니다.");
			$("input#F_DECIMAL_SIZE").focus();
		    return;
		}

		if(Number(obj.decimal_size) > 999){
			alert("[알림] 소수자리가 너무 큽니다");
			$("input#F_DECIMAL_SIZE").focus();
		    return;
		}

		$("#SetATTRForm").ajaxForm({
			url : 'SetTable',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if (obj.crud == "AU" ) {	
				
					console.log('grid update.........');
					var rowData = ArchiveGrid.getRowData(gridLocal2, $('#ROWID2').val());
					rowData.ATTR_CD= obj.attr_cd; 
					rowData.ATTR_NM= obj.attr_nm; 
					rowData.ATTR_SIZE= obj.attr_size; 
					rowData.DECIMAL_SIZE= obj.decimal_size; 
					rowData.ATTR_TYPE_CD= obj.attr_type_cd; 
					rowData.ATTR_TYPE_NM= obj.attr_type_nm; 
					rowData.ATTR_NULL_YN= obj.attr_null_yn; 
					rowData.SORT_INDEX= obj.sort_index; 
					rowData.WHERE_INDEX= obj.where_index; 
					rowData.OUTPUT_INDEX= obj.output_index; 
					rowData.DATE_TYPE_YN= obj.date_type_yn; 
					rowData.TIME_TYPE_YN= obj.time_type_yn; 

					ArchiveGrid.setRowData(gridLocal2, $('#ROWID2').val(), rowData)
					
				} else {
					console.log('grid append.........');
					var ids = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});
					var idsLen = ids.length + 1;
					ArchiveGrid.addRowData(gridLocal2, {});	
					var ids2 = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});
					var rowData = ArchiveGrid.getRowData(gridLocal2, idsLen);
					rowData.ATTR_CD= obj.attr_cd; 
					rowData.ATTR_NM= obj.attr_nm; 
					rowData.ATTR_SIZE= obj.attr_size; 
					rowData.DECIMAL_SIZE= obj.decimal_size; 
					rowData.ATTR_TYPE_CD= obj.attr_type_cd; 
					rowData.ATTR_TYPE_NM= obj.attr_type_nm; 
					rowData.ATTR_NULL_YN= obj.attr_null_yn; 
					rowData.SORT_INDEX= obj.sort_index; 
					rowData.WHERE_INDEX= obj.where_index; 
					rowData.OUTPUT_INDEX= obj.output_index; 
					rowData.DATE_TYPE_YN= obj.date_type_yn; 
					rowData.TIME_TYPE_YN= obj.time_type_yn; 
					
					ArchiveGrid.setRowData(gridLocal2, idsLen, rowData)
				}
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
		$("#SetATTRForm").submit() ;
	});
		
	$('#btnAttrRenum').click(function (e) {
		var formData = new FormData();

		console.log('grid append.........');
		var ids = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});

		var allObj = new Array();
		
   		$.each(ids, function(index, value) {
   			
   			// 선택한 ROW의 순번   			
   			var rowNum  = index+1
   			var rowData = ArchiveGrid.getRowData(gridLocal2, index+1);

   			console.log((index+1) + "::" + rowNum + "::" + rowData.ATTR_NM);
   			
   			var obj = new Object();
   			obj.table_id = $("input#F_TABLE_ID").val();
   			obj.attr_cd  = rowData.ATTR_CD ;   			
   			obj.attr_seq = rowNum + "";  
   			obj.crud     = "RN";  //renumbering

   			console.log(JSON.stringify(obj));
   			
   			$("#SetATTRForm").ajaxForm({
   				url : 'SetTable',
   				dataType:'json',
   				type: 'post',
   				data:{param:JSON.stringify(obj)},
   				success: function(json_data) {
//   					alert("정상적으로처리 되었습니다");
   				}
   			});	
   			$("#SetATTRForm").submit() ;
   		});
		alert("정상적으로처리 되었습니다");
	});
	
	$('input[name="C_USE_YN"]').val(["A"]);
	tableRefreshData();
	reportRefreshData();

/*
var reader = new FileReader();
var excelFileData = null;

var inputElement = document.getElementById('btnImpExcel');
inputElement.addEventListener('change', function() {
	console.log(event.target.files);
	reader.readAsArrayBuffer(event.target.files[0]);
}, false);

var tideGrid;

reader.onload = function (e) {
	
	
	ArchiveGrid.clearRows(gridLocal2);
	
	var data = e.target.result;
	var workbook = XLSX.read(data, { type: "array", cellDates: true, dateNF: 'YYYY-MM-DD' });
	
	var sheetName = workbook.SheetNames;
	var sheet = workbook.Sheets[sheetName];	

	excelFileData = XLSX.utils.sheet_to_json(sheet, {header:2, raw:false});	
	//console.log("JsonData : "+ JSON.stringify(excelFileData));
	let jsonGetData = JSON.parse(JSON.stringify(excelFileData));
	
	jsonGetData.forEach(function(value){
		//console.log("VALUED:" + JSON.stringify(value.ATTR_CD));
		var newValue = JSON.stringify(value.ATTR_CD).replaceAll('"','');				
		if(newValue.length > 0)
		{	
			var ids = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});
			var idsLen = ids.length + 1;
						
			ArchiveGrid.addRowData(gridLocal2, {});	
			var ids2 = ArchiveGrid.getRows(gridLocal2).map(function(r,i){return i+1;});
			var rowData = ArchiveGrid.getRowData(gridLocal2, idsLen);
			rowData.ATTR_CD     = JSON.stringify(value.ATTR_CD).replaceAll('"','');
			rowData.ATTR_NM     = JSON.stringify(value.ATTR_NM).replaceAll('"','');
			rowData.ATTR_SIZE   = JSON.stringify(value.ATTR_SIZE).replaceAll('"','');
			rowData.DECIMAL_SIZE= JSON.stringify(value.DECIMAL_SIZE).replaceAll('"','');
			//rowData.ATTR_TYPE_CD= JSON.stringify(value.ATTR_TYPE_CD).replaceAll('"','');
			rowData.ATTR_TYPE_NM= JSON.stringify(value.ATTR_TYPE_NM).replaceAll('"','');
			rowData.ATTR_NULL_YN= JSON.stringify(value.ATTR_NULL_YN).replaceAll('"','');
			rowData.SORT_INDEX  = JSON.stringify(value.SORT_INDEX).replaceAll('"','');; 
			rowData.WHERE_INDEX = JSON.stringify(value.WHERE_INDEX).replaceAll('"',''); 
			rowData.OUTPUT_INDEX= JSON.stringify(value.OUTPUT_INDEX).replaceAll('"','');				
			ArchiveGrid.setRowData(gridLocal2, idsLen, rowData)					
			
			//############### 해당 테이블데이터삭제
			
		}
	});
	
}
*/
