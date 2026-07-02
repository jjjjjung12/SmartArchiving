var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetBatchList',
		pageSize: 10,
		columns: [
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
var $gridPrj;
var objPrj;

var jsonObj = new Object();

function fn_popup(id) {
    var popup = "#" + id;
    $(id).modal("show");
}


objPrj = {
	collapsible: false,
	pageModel: { type: "local", rPP: 16, strRpp: "{0}", strDisplay: "{0} to {1} of {2}", strPage: "Page {0} / {1}", layout: ['first','prev', 'next','last', "|", "strPage"]   },
	wrap: false,
	hwrap: false,
	autofill: false,
//	editable:false,
	numberCell: { show: true, resizable: true, title: "#" },
	resizable: true,
//	freezeCols:5,
	
	rowDblClick : function (e,i) {
		
		console.log(i.rowData.USE_YN);
		
	    $('#F_JOB_ID').val(i.rowData.JOB_ID       );
	    $('#F_JOB_NM').val(i.rowData.JOB_NM       );
	    $('#F_JOB_TYPE_CD').val(i.rowData.JOB_TYPE_CD    );
	    $('#F_JOB_PATH').val(i.rowData.JOB_PATH     );
	    $('#F_JOB_FILE_NAME').val(i.rowData.JOB_FILE_NAME     );
	    $('#F_JOB_ORDER').val(i.rowData.JOB_ORDER    );
	    $('#F_JOB_STATUS_CD').val(i.rowData.JOB_STATUS_CD   );
	    $('#F_JOB_STATUS_NM').val(i.rowData.JOB_STATUS_NM   );
	    $('#F_JOB_USER_ID').val(i.rowData.JOB_USER_ID );
	    $('#F_JOB_USER_NM').val(i.rowData.JOB_USER_NM );
	    $('#F_JOB_WORKER').val(i.rowData.JOB_WORKER   );
	    $('#F_DESCRIPTION').val(i.rowData.DESCRIPTION  );
	    $('#F_SRC_SQL').val(i.rowData.SRC_SQL );
		$('input[name=F_USE_YN][value="' + i.rowData.USE_YN + '"]').prop("checked", true);
	    
	    $('#CRUD').val("U");
		$('#F_JOB_ID' ).attr("readonly", true); //설정
	    
	}
};
objPrj.colModel = [
	{ title: "JOB_ID"  		, width:  70, dataType: "string", dataIndx: "JOB_ID" 		},
	{ title: "JOB_USER_ID"  , width: 100, dataType: "string", dataIndx: "JOB_USER_ID"   , align:"center", hidden:true },
	{ title: "JOB_USER_NM"  , width: 100, dataType: "string", dataIndx: "JOB_USER_NM" 	},
	{ title: "JOB_NM"  		, width: 100, dataType: "string", dataIndx: "JOB_NM" 		},
	{ title: "JOB_STATUS_CD", width: 100, dataType: "string", dataIndx: "JOB_STATUS_CD" , hidden:true },
	{ title: "JOB_STATUS_NM", width: 100, dataType: "string", dataIndx: "JOB_STATUS_NM" , align:"center"},
	{ title: "DESCRIPTION"  , width: 100, dataType: "string", dataIndx: "DESCRIPTION" 	},
	{ title: "JOB_PATH"     , width: 100, dataType: "string", dataIndx: "JOB_PATH" 		},
	{ title: "JOB_FILE_NAME", width: 100, dataType: "string", dataIndx: "JOB_FILE_NAME" 		},
	{ title: "JOB_TYPE_CD" 	, width: 100, dataType: "string", dataIndx: "JOB_TYPE_CD" 	, align:"center", hidden:true },
	{ title: "JOB_TYPE_NM"	, width: 100, dataType: "string", dataIndx: "JOB_TYPE_NM" 	, align:"center"},
	{ title: "JOB_ORDER"  	, width: 100, dataType: "string", dataIndx: "JOB_ORDER" 	},
	{ title: "JOB_WORKER"  	, width: 100, dataType: "string", dataIndx: "JOB_WORKER"	},
	{ title: "SRC_SQL"  	, width: 100, dataType: "string", dataIndx: "SRC_SQL" 		},
	{ title: "USE_YN"  		, width: 100, dataType: "string", dataIndx: "USE_YN" 		, align:"center"},
];
objPrj.height = '100%';
if ($.fn && $.fn.pqGrid) { $gridPrj = $("#grid_json").pqGrid(objPrj); }


var obj1 = {
		collapsible: false,
		pageModel: { type: "local", rPP: 15, strRpp: "", strDisplay: "", strPage: "Page {0} / {1}", layout: ['first','prev', 'next','last', "|", "strPage"]   },
		wrap: false,
		hwrap: false,
		autofill: false,
		numberCell: { show: true, resizable: true, title: "#" },
		resizable: true,
	rowDblClick : function (e,i) {
		$('#F_JOB_USER_ID').val(i.rowData.USER_ID);
		$('#F_JOB_USER_NM').val(i.rowData.USER_NM);
		$("#DocumentListPartial1").modal("hide");	    
	}
};
obj1.colModel = [
    { title: "ID"      , width: 100, dataType: "string", dataIndx: "USER_ID"         },
    { title: "이름"     , width: 100, dataType: "string", dataIndx: "USER_NM"         },
    { title: "연락처"    , width: 100, dataType: "string", dataIndx: "PHONE"         },
];
obj1.height = '200px';

function bodyload( sData ) {
	
	if ($gridPrj && $gridPrj.pqGrid) $gridPrj.pqGrid('option', 'dataModel.data', sData).pqGrid('refreshDataAndView');
}
	
function downloadCSV(csv, filename) {
	var csvFile;
	var downloadLink;

	//한글 처리를해주기위해 BOM 추가하기
	const BOM = "\uFEFF";
	csv = BOM + csv;

	csvFile = new Blob([csv], { type: "text/csv" });
	downloadLink = document.createElement("a");
	downloadLink.download = filename;
	downloadLink.href = window.URL.createObjectURL(csvFile);
	downloadLink.style.display = "none";
	document.body.appendChild(downloadLink);
	downloadLink.click();
}







$('#btnAdd').click(function (e) {
	
	$('#F_JOB_ID'  ).val("");
	$('#F_JOB_NM'  ).val("");
	$('#F_JOB_TYPE_CD'  ).val("");
	$('#F_JOB_PATH'  ).val("");
	$('#F_JOB_FILE_NAME'  ).val("");
	$('#F_JOB_ORDER'  ).val("");
	$('#F_JOB_STATUS_CD'  ).val("W");
	$('#F_JOB_STATUS_NM'  ).val("");
	$('#F_JOB_USER_ID'  ).val("");
	$('#F_JOB_WORKER'  ).val("");
	$('#F_DESCRIPTION'  ).val("");
	$('#F_SRC_SQL'  ).val("");
	$('input[name="F_USE_YN"]').val(["N"]);
	
	
	$('#CRUD'        ).val("C");
	$('#F_JOB_ID' ).attr("readonly", false); //설정
	$("input#F_JOB_ID").focus();

//	$('#F_BOAT_STATUS option:eq(0)').prop("selected", true);
	
});

$('#btnSave').click(function (e) {
	var formData = new FormData();
	$(e.target).attr("disabled", true);
	
	try {
		
		var jobj = new Object();

		jobj.job_id   		= $("input#F_JOB_ID").val();
		jobj.job_nm   		= $("input#F_JOB_NM").val();
		jobj.job_type_cd   	= $('select#F_JOB_TYPE_CD option:selected').val();    
		jobj.job_path   	= $("input#F_JOB_PATH").val();
		jobj.job_file_name	= $("input#F_JOB_FILE_NAME").val();
		jobj.job_order   	= $("input#F_JOB_ORDER").val();
		jobj.job_status_cd  = $('select#F_JOB_STATUS_CD option:selected').val();    
		jobj.job_user_id   	= $("input#F_JOB_USER_ID").val();
		jobj.job_worker   	= $("input#F_JOB_WORKER").val();
		jobj.description   	= $("textarea#F_DESCRIPTION").val();
		jobj.src_sql   		= $("textarea#F_SRC_SQL").val();
		jobj.use_yn   		= $('input[name="F_USE_YN"]:checked').val();	
		jobj.crud        	= $("#CRUD").val();

		if(jobj.job_id == ''){
			alert("[알림] 작업코드를입력하세요");
			$("input#F_JOB_ID").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		
		if(jobj.job_nm == ''){
			alert("[알림] 작업명을 입력하세요");
			$("input#F_JOB_NM").focus();
			$(e.target).attr("disabled", false);
			return;
		}


		$("#SetScheduleForm").ajaxForm({
			url : 'SetBatchList',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(jobj)},
			success: function(json_data) {
				
			//	console.log(json_data.result); 
		        if(json_data.result == 'OK') {
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
					$('#btnAdd').click();
					alert("정상적으로처리 되었습니다");
					$(e.target).attr("disabled", false);
				} else {
					console.log(json_data.result); 
					alert("[알림] 처리시오류가 발생하였습니다\n" + json_data.message);
				}
		        
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("[알림] 처리시오류가 발생하였습니다\n" + data.responseText);
		    		}
		    	}
		    	$(e.target).attr("disabled", false);
			}
		});	
		$("#SetScheduleForm").submit() ;
		$('#F_USER_CD').attr('readonly', false);
	} catch (error){
		alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
		$(e.target).attr("disabled", false);
	}
	
});


$('#btnDelete').click(function (e) {
	var jobj = new Object();
	jobj.job_id   = $("input#F_JOB_ID").val();
	jobj.crud        = "D";

	if(jobj.job_id == ''){
		alert("[알림] 작업을선택하세요");
		$("input#F_JOB_NM").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	
	var input = confirm('삭제하시겠습니까?'); 
	if(!input) {
		$(e.target).attr("disabled", false);
		return;
	}

	try {
		$("#SetScheduleForm").ajaxForm({
			url : 'SetBatchList',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(jobj)},
			success: function(json_data) {
				console.log(json_data.result); 
		        if(json_data.result == 'OK') {					
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
					$('#btnAdd').click();
					alert("정상적으로처리 되었습니다");
					$(e.target).attr("disabled", false);
				} else {
					console.log(json_data.result); 
					alert("[알림] 처리시오류가 발생하였습니다\n" + json_data.message);
				}
			},
			error : function(data, status){
		    	if (data != null){
	    			alert("[알림] 처리시오류가 발생하였습니다\n" + data.responseText);
		    	}
		    	$(e.target).attr("disabled", false);
			}
		});	
		$("#SetScheduleForm").submit() ;
		$('#F_USER_CD').attr('readonly', false);
		
	} catch (error){
		alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
		$(e.target).attr("disabled", false);
	}			
		
});


$('#btnEdit').click(function (e) {
	var formData = new FormData();
	$(e.target).attr("disabled", true);
	
		
	var jid = $("input#F_JOB_ID").val();

	var url = archiveCtx + "/onDemand?passJobId=" + encodeURIComponent(jid);
	location.href = url;
	
});


if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
refreshData1();



$('#btnSaveDocumentAssociation1').click(function (e) {
	$("#DocumentListPartial1").modal("hide");	    
});

$('#btnClear1').click(function (e) {
	$('#F_JOB_USER_ID').val("");
	$('#F_JOB_USER_NM').val("");
});
 
$('#btnDownload').click(function (e) {
	console.log($('#F_JOB_FILE_NAME').val());
	var url2 = "/scheduleDownload/" + $('#F_JOB_FILE_NAME').val();
	location.href= url2;
});


$("input#F_JOB_NM").focus();
$('#btnQuery').attr("disabled", false);
