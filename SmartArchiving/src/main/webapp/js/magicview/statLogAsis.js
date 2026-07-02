var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetASISSlogHeader',
		pageSize: 10,
		columns: [
			{name:'SVC_ID', align:'center'},
			{name:'SVC_NM', align:'left'},
			{name:'TR_CN', align:'center'},
			{name:'NML_PRC_CN', align:'center'},
			{name:'ERR_PRC_CN', align:'center'},
			{name:'PARTNER_CN', align:'center'},
			{name:'OS1', align:'center'},
			{name:'OS2', align:'center'},
			{name:'OS3', align:'center'},
			{name:'OS4', align:'center'},
			{name:'OS5', align:'center'},
			{name:'OS6', align:'center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : { __rows:'50', __page:'1' }; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {

	$("#export").on("click", function(){
		if(!window._archiveGrid || ArchiveGrid.getRows(window._archiveGrid).length === 0){
			alert("데이터가 존재하지 않습니다.");
			return false;
		}

		var fileName = "통계로그ASIS_" + toDayNoPoint + ".xlsx";

		ArchiveGrid.exportToExcel(window._archiveGrid, {
			includeLabels : true,
			includeGroupHeader : true,
			includeFooter: true,
			fileName : fileName,
			maxlength : 40
		});
	});

	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
});
