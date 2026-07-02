var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetExportJobHistory',
		pageSize: 10,
		columns: [
			{name:'JOB_SEQ', hidden:true},
			{name:'JOB_ID', hidden:true},
			{name:'JOB_NAME', align:'center'},
			{name:'DES_FILE_PATH', hidden:true},
			{name:'JOB_TM', align:'center'},
			{name:'START_DATETIME', align:'center'},
			{name:'END_DATETIME', align:'center'},
			{name:'DES_JOBTIME', align:'center'},
			{name:'JOB_STTS', hidden:true},
			{name:'JOB_MSG', align:'center'},
			{name:'JOB_TYPE', hidden:true},
			{name:'SRC_DATA_ROWS', align:'center'},
			{name:'DES_DATA_ROWS', align:'center'},
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

	
	
	function fillZero(width, str){
	    return str.length >= width ? str:new Array(width-str.length+1).join('0')+str;//남는 길이만큼 0으로 채움
	}
	
	function loadInfo(selectedRow, currentRow, currentPage) {

		var selectdRow = selectedRow;

		$('#F_JOB_SEQ').val(selectdRow.JOB_SEQ);
		$('#F_JOB_ID').val(selectdRow.JOB_ID);
        $('#F_DES_FILE_PATH').val(selectdRow.DES_FILE_PATH);
        $('#F_JOB_TM').val(selectdRow.JOB_TM);
        $('#F_JOB_NAME').val(selectdRow.JOB_NAME);
        $('#F_DES_JOBTIME').val(selectdRow.DES_JOBTIME);
        
        $('#F_SRC_DATA_ROWS').val(selectdRow.SRC_DATA_ROWS );
        $('#F_DES_DATA_ROWS').val(selectdRow.DES_DATA_ROWS );
        $('#F_START_DATETIME').val(selectdRow.START_DATETIME);
        $('#F_END_DATETIME').val(selectdRow.END_DATETIME);
        $('#F_JOB_STTS').val(selectdRow.JOB_STTS);
        $('#F_JOB_MSG').val(selectdRow.JOB_MSG);
        $('#F_PROC_CNT').val(selectdRow.PROC_CNT);
              
        $('#F_JOB_ID').attr("readonly", true ); //설정
	};	
   
	
		
		$('#F_JOB_SEQ').val("");
		$('#F_JOB_ID').val("");
        $('#F_DES_FILE_PATH').val("");
        $('#F_JOB_TM').val("");
        $('#F_JOB_NAME').val("");
        $('#F_DES_JOBTIME').val("");
        
        $('#F_SRC_DATA_ROWS').val("" );
        $('#F_DES_DATA_ROWS').val("");
        $('#F_START_DATETIME').val("");
        $('#F_END_DATETIME').val("");
        $('#F_JOB_STTS').val("");
        $('#F_JOB_MSG').val("");
		$('#CRUD').val("C");
		$('#F_SERVER_ID'  ).attr("readonly", true); //설정
		$('#F_JOB_ID'  ).attr("readonly", true); //설정
		
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);		
	});
