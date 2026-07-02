var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetASISIlogHeader',
		pageSize: 10,
		columns: [
			{name:'MESSAGE_SER_NO', hidden:true},
			{name:'TRX_DTIME', align:'center'},
			{name:'TRX_TRACKING_NO', hidden:true},
			{name:'MESSAGE_ID', align:'center'},
			{name:'MESSAGE_NAME', align:'center'},
			{name:'USER_ID', align:'center'},
			{name:'TR_ACNO', align:'center'},
			{name:'IN_ACNO', align:'center'},
			{name:'CUS_NO', align:'center'},
			{name:'ERROR_CODE', align:'center'},
			{name:'IP', align:'center'},
			{name:'CHANNEL_CODE', align:'center'},
			{name:'SERVICE_ID', hidden:true},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : { __rows:'50', __page:'1' }; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {

		console.log("btn searchData.........");
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	});
	
	
	$("#export").on("click", function(){
		
		if(!window._archiveGrid || ArchiveGrid.getRows(window._archiveGrid).length === 0){
			alert("데이터가 존재하지 않습니다.");
			return false;
		}
		
		var fileName = "통합로그ASIS_" + toDayNoPoint + ".xlsx";
		
		ArchiveGrid.exportToExcel(_archiveGrid, {
			includeLabels : true,
			includeGroupHeader : true,
			includeFooter: true,
			fileName : fileName,
			maxlength : 40 // maxlength for visible string data 
		})
	});	
	
	$('input[name="C_USE_YN"]').val(["Y"]);
	
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	$("#searchVal").focus();

function loadInfo(selectedRow, currentRow, currentPage, startDateTime, endDateTime) {
		var selectdRow = selectedRow;

		var sCorp = selectdRow['CORP'];	
		var sSP_MESSAGE_SER_NO = selectdRow['MESSAGE_SER_NO'];	
		var sSP_TRX_DTIME = selectdRow['TRX_DTIME'];
		var sTRX_TRACKING_NO = selectdRow['TRX_TRACKING_NO'];
		var sSP_MESSAGE_ID = selectdRow['MESSAGE_ID'];
		
		$('#H_CORP' ).val(sCorp);
		$('#H_MESSAGE_SER_NO' ).val(sSP_MESSAGE_SER_NO);
		$('#H_TRX_DTIME' ).val(sSP_TRX_DTIME);
		$('#H_MESSSAGE_ID' ).val(sSP_MESSAGE_ID);
		$('#H_TRX_TRACKING_NO' ).val(sTRX_TRACKING_NO);
		$('#H_SP_TR_YMD_S' ).val(startDateTime); 
		$('#H_SP_TR_YMD_E' ).val(endDateTime );
		
        var popWidth = document.body.clientWidth;
        var popHeight = document.body.clientHeight;
        
        //console.log("###CORP #######" +sCorp);
       // $('#TRANID').val("opStart");
                
       var sServer = $('#serverValue').val() ;
       var sCorp =  $('#corpValue').val() ;
       openArchivePopup('jsonDetailAsis?&corp=' + sCorp + '&server=' + sServer + '&user_cd=' + sessionUserCd, {
            width: popWidth - 500,
            height: popHeight - 10
        });
	}
