var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetScheduleList',
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
	editable:false,
	numberCell: { show: true, resizable: true, title: "#" },
	resizable: true,
	freezeCols:3,
	rowDblClick : function (e,i) {
		
		console.log(i.rowData.USE_YN);
		
	    $('#F_JOB_ID').val(i.rowData.JOB_ID       );
	    $('#F_JOB_CD').val(i.rowData.JOB_CD       );
	    $('#F_JOB_NM').val(i.rowData.JOB_NM       );
	    $('#F_JOB_SCHEDULE').val(i.rowData.JOB_SCHEDULE );
	    $('#F_JOB_METHOD_CD').val(i.rowData.JOB_METHOD_CD);
	    $('#F_JOB_METHOD_NM').val(i.rowData.JOB_METHOD_NM);
	    $('#F_JOB_CLASS').val(i.rowData.JOB_CLASS    );
	    $('#F_JOB_TYPE_CD').val(i.rowData.JOB_TYPE_CD  );
	    $('#F_JOB_TYPE_NM').val(i.rowData.JOB_TYPE_NM  );
	    $('#F_JOB_PATH').val(i.rowData.JOB_PATH     );
	    $('#F_JOB_ORDER').val(i.rowData.JOB_ORDER    );
	    $('#F_JOB_STATUS_CD').val(i.rowData.JOB_STATUS_CD   );
	    $('#F_JOB_STATUS_NM').val(i.rowData.JOB_STATUS_NM   );
	    $('#F_JOB_USER_ID').val(i.rowData.JOB_USER_ID      );
	    $('#F_JOB_TM').val(i.rowData.JOB_TM       );
	    $('#F_JOB_WORKER').val(i.rowData.JOB_WORKER   );
	    $('#F_DESCRIPTION').val(i.rowData.DESCRIPTION  );
	    $('#F_SRC_IP').val(i.rowData.SRC_IP       );
	    $('#F_SRC_PORT').val(i.rowData.SRC_PORT     );
	    $('#F_SRC_DBMS_CD').val(i.rowData.SRC_DBMS_CD  );
	    $('#F_SRC_DBMS_NM').val(i.rowData.SRC_DBMS_NM  );
	    $('#F_SRC_DB').val(i.rowData.SRC_DB       );
	    $('#F_SRC_USER').val(i.rowData.SRC_USER     );
	    $('#F_SRC_PASSWD').val(i.rowData.SRC_PASSWD   );
	    $('#F_SRC_TYPE_CD').val(i.rowData.SRC_TYPE_CD  );
	    $('#F_SRC_TYPE_NM').val(i.rowData.SRC_TYPE_NM  );
	    $('#F_SRC_SQL').val(i.rowData.SRC_SQL      );
		$('input[name=F_USE_YN][value="' + i.rowData.USE_YN + '"]').prop("checked", true);
	    
	    $('#CRUD').val("U");
		$('#F_JOB_CD' ).attr("readonly", true); //설정
	    
	}
};
objPrj.colModel = [
	{ title: "JOB_ID"  , width: 100, dataType: "string", dataIndx: "JOB_ID" },
	{ title: "JOB_CD"  , width: 100, dataType: "string", dataIndx: "JOB_CD" },
	{ title: "JOB_NM"  , width: 100, dataType: "string", dataIndx: "JOB_NM" },
	{ title: "JOB_SCHEDULE"  , width: 100, dataType: "string", dataIndx: "JOB_SCHEDULE" },
	{ title: "JOB_METHOD_CD"  , width: 100, dataType: "string", dataIndx: "JOB_METHOD_CD", hidden:true },
	{ title: "JOB_METHOD_NM "  , width: 100, dataType: "string", dataIndx: "JOB_METHOD_NM" },
	{ title: "JOB_CLASS"  , width: 100, dataType: "string", dataIndx: "JOB_CLASS" },
	{ title: "JOB_TYPE_CD"  , width: 100, dataType: "string", dataIndx: "JOB_TYPE_CD", hidden:true },
	{ title: "JOB_TYPE_NM"  , width: 100, dataType: "string", dataIndx: "JOB_TYPE_NM" },
	{ title: "JOB_PATH"  , width: 100, dataType: "string", dataIndx: "JOB_PATH" },
	{ title: "JOB_ORDER"  , width: 100, dataType: "string", dataIndx: "JOB_ORDER" },
	{ title: "JOB_STATUS_CD"  , width: 100, dataType: "string", dataIndx: "JOB_STATUS_CD" },
	{ title: "JOB_STATUS_NM"  , width: 100, dataType: "string", dataIndx: "JOB_STATUS_NM" },
	{ title: "JOB_USER_ID"  , width: 100, dataType: "string", dataIndx: "JOB_USER_ID" },
	{ title: "JOB_TM"  , width: 100, dataType: "string", dataIndx: "JOB_TM" },
	{ title: "JOB_WORKER"  , width: 100, dataType: "string", dataIndx: "JOB_WORKER" },
	{ title: "DESCRIPTION"  , width: 100, dataType: "string", dataIndx: "DESCRIPTION" },
	{ title: "SRC_IP"  , width: 100, dataType: "string", dataIndx: "SRC_IP" },
	{ title: "SRC_PORT"  , width: 100, dataType: "string", dataIndx: "SRC_PORT" },
	{ title: "SRC_DBMS_CD"  , width: 100, dataType: "string", dataIndx: "SRC_DBMS_CD" , hidden:true},
	{ title: "SRC_DBMS_NM"  , width: 100, dataType: "string", dataIndx: "SRC_DBMS_NM" },
	{ title: "SRC_DB"  , width: 100, dataType: "string", dataIndx: "SRC_DB" },
	{ title: "SRC_USER"  , width: 100, dataType: "string", dataIndx: "SRC_USER" },
	{ title: "SRC_PASSWD"  , width: 100, dataType: "string", dataIndx: "SRC_PASSWD" },
	{ title: "SRC_TYPE_CD"  , width: 100, dataType: "string", dataIndx: "SRC_TYPE_CD" },
	{ title: "SRC_TYPE_NM"  , width: 100, dataType: "string", dataIndx: "SRC_TYPE_NM" },
	{ title: "SRC_SQL"  , width: 100, dataType: "string", dataIndx: "SRC_SQL" },
	{ title: "USE_YN"  , width: 100, dataType: "string", dataIndx: "USE_YN" },
];
objPrj.height = '100%';
if ($.fn && $.fn.pqGrid) { $gridPrj = $("#grid_json").pqGrid(objPrj); }


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
			url : 'SetScheduleList',
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

if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);


$("input#F_JOB_NM").focus();
$('#btnQuery').attr("disabled", false);
