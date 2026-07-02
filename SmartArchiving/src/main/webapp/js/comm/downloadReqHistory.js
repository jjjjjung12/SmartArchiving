var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetDownloadReqList',
		pageSize: 10,
		columns: [
			{name:'REG_DATE', align:'center'},
			{name:'USER_ID', hidden:true},
			{name:'USER_CD', align:'center'},
			{name:'USER_NM', align:'center'},
			{name:'REQ_DIV', hidden:true},
			{name:'REQ_NM', align:'center'},
			{name:'REQ_NUM', align:'center'},
			{name:'WARRANT_NUM', hidden:true},
			{name:'PROGRAM_ID', hidden:true},
			{name:'PROGRAM_NM', hidden:true},
			{name:'REQ_REASON', align:'center'},
			{name:'PROGRAM_WHERE', align:'center'},
			{name:'BULK_YN', align:'center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
});
