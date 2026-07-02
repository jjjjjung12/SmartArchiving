var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
var gridGroup, gridAvail, gridAssigned;

function buildGroupParam() {
	return { __gb: 'A', __user_gb: '*', __user_id: '', __rows: '50', __page: '1' };
}

function buildAvailParam() {
	return {
		__gb: 'B',
		__user_gb: $('#C_USERGRP').val() || '*',
	};
}

function buildAssignedParam() {
	return {
		__gb: 'G',
		__user_gb: $('#C_USERGRP').val() || '*',
	};
}

function sortByOrder(rows) {
	return rows.slice().sort(function(a, b) {
		var ao = parseInt(a.MENU_ORDER, 10) || 0;
		var bo = parseInt(b.MENU_ORDER, 10) || 0;
		return ao - bo;
	});
}

function loadMenuGrids() {
	if (!$('#C_USERGRP').val()) {
		ArchiveGrid.setRows(gridAvail, []);
		ArchiveGrid.setRows(gridAssigned, []);
		return;
	}
	ArchiveGrid.load(gridAvail);
	ArchiveGrid.load(gridAssigned);
}

function loadInfo(selectedRow) {
	$('#C_USERGRP').val(selectedRow.USER_GRP_ID);
	$('#CRUD1').val('U');
	loadMenuGrids();
}

function moveToAssigned(row) {
	gridAssigned.allRows = sortByOrder(gridAssigned.allRows.concat([row]));
	gridAvail.allRows = gridAvail.allRows.filter(function(r) { return r.MENU_ID !== row.MENU_ID; });
	ArchiveGrid.renderPaging(gridAvail);
	ArchiveGrid.renderPaging(gridAssigned);
}

function moveFromAssigned(row) {
	gridAvail.allRows = sortByOrder(gridAvail.allRows.concat([row]));
	gridAssigned.allRows = gridAssigned.allRows.filter(function(r) { return r.MENU_ID !== row.MENU_ID; });
	ArchiveGrid.renderPaging(gridAvail);
	ArchiveGrid.renderPaging(gridAssigned);
}

$(function() {
	gridGroup = ArchiveGrid.create({
		url: 'GetAuthList',
		pageSize: 10,
		bodySelector: '#gridBody',
		pagingSelector: '.wr_page',
		countSelector: '#totalCnt',
		columns: [
			{name:'USER_GRP_ID', align:'center'},
			{name:'USER_GRP_NM', align:'center'},
		],
		getPostData: buildGroupParam,
		onRowClick: function(row) { loadInfo(row); },
	});
	gridAvail = ArchiveGrid.create({
		url: 'GetAuthList',
		pageSize: 10,
		bodySelector: '#gridBody2',
		pagingSelector: '.wr_page2',
		columns: [
			{name:'MENU_ID', hidden:true},
			{name:'MENU_NM', align:'left'},
			{name:'MENU_URL', align:'left'},
			{name:'MENU_ORDER', hidden:true},
		],
		getPostData: buildAvailParam,
		onRowClick: moveToAssigned,
	});
	gridAssigned = ArchiveGrid.create({
		url: 'GetAuthList',
		pageSize: 10,
		bodySelector: '#gridBody3',
		pagingSelector: '.wr_page3',
		columns: [
			{name:'MENU_ID', hidden:true},
			{name:'MENU_NM', align:'left'},
			{name:'MENU_URL', align:'left'},
			{name:'MENU_ORDER', hidden:true},
		],
		getPostData: buildAssignedParam,
		onRowClick: moveFromAssigned,
	});
	ArchiveGrid.wirePage(gridGroup, { autoLoad: true });

	$('#btnSave').click(function () {
		var user_gb = $('#C_USERGRP').val();
		if (!user_gb) { alert('[알림] 그룹을 선택하세요'); return; }
		var allObj = gridAssigned.allRows.map(function(r) { return { menu_id: r.MENU_ID }; });
		$.ajax({
			url: 'SetAuth',
			type: 'POST',
			dataType: 'json',
			data: { param: JSON.stringify(allObj), user_gb: user_gb },
			success: function(json_data) {
				if (json_data.result === 'OK') {
					alert('정상적으로처리 되었습니다');
					loadMenuGrids();
				} else {
					alert('저장이 실패하였습니다');
				}
			},
			error: function() { alert('조회 오류가 발생했습니다.'); }
		});
	});
});
