var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetSlogHeader',
		pageSize: 10,
		columns: [
			{name:'APPLICATION_GROUP_NM', align:'center'},
			{name:'SERVICE_ID', align:'center'},
			{name:'SERVICE_NAME', align:'center'},
			{name:'CREADTED_TIME', hidden:true},
			{name:'APPLICATION_GROUP_ID', hidden:true},
			{name:'SUCCESS_YN', align:'center'},
			{name:'SUCCESS_COUNT', align:'center'},
			{name:'FAIL_COUNT', align:'center'},
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

		var fileName = "통계로그_" + toDayNoPoint + ".xlsx";

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
