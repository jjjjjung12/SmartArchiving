var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function formatNoticeRegDate(v) {
	if (!v) return '';
	var s = String(v);
	if (s.indexOf('.') > 0) return s;
	return (typeof toDatePoint2 === 'function') ? toDatePoint2(s) : s;
}

function openNoticeDetailPop(row) {
	if (!row) return;
	window._noticeDetailRow = row;
	window._noticeDetailCanDelete = (sessionGroupId === '101');
	openArchivePopup('noticeManageDetailPop', {
		width: 680,
		height: 560,
		onReady: function() {
			if (typeof ensureNoticeDetailPopFilled === 'function') {
				ensureNoticeDetailPopFilled(0);
			}
		}
	});
}

$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetNoticeManageList',
		method: 'GET',
		pageSize: 10,
		columns: [
			{name:'SERIAL_NUMBER', align:'center', className:'col-no'},
			{name:'REG_USER_CD', hidden:true},
			{name:'SUBJECT', align:'left', className:'col-subject tal_cut', ellipsis:true},
			{name:'USER_NM', align:'center', className:'col-user'},
			{name:'REG_DATE', align:'center', className:'col-date', formatter: formatNoticeRegDate},
			{name:'REG_START_DATE', hidden:true},
			{name:'REG_END_DATE', hidden:true},
			{name:'SUBJECT_DETAIL', hidden:true},
			{name:'FILE_NM', hidden:true},
			{name:'FILE_URL', hidden:true},
		],
		getPostData: function(){ return {}; },
		onRowDblClick: function(row) { openNoticeDetailPop(row); },
	});
	ArchiveGrid.wirePage(grid);
});

$(document).ready(function() {
	if (sessionGroupId === '101') {
		$('#btnSave').show();
	} else {
		$('#btnSave').hide();
	}

	if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);

	$('#btnSearch').click(function () {
		if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	});
});

$("#btnSave").on("click", function(){
	openArchivePopup('noticeManageSetPop?&usercd=' + sessionusercd, {
		width: 680,
		height: 520
	});
});
