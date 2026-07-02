var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetMaskingHistoryList',
		pageSize: 10,
		columns: [
			{name:'USER_CD', align:'center'},
			{name:'USER_NM', align:'center'},
			{name:'REQ_DATE', align:'center'},
			{name:'APPROVAL_REQ_ID', align:'center'},
			{name:'PROC_DATE', align:'center'},
			{name:'PROGRAM_ID', align:'left'},
			{name:'PROGRAM_NM', align:'left'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
});
