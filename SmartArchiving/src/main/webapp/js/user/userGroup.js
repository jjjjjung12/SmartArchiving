var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
var gridGroup, gridAvail, gridMember;

function buildGroupParam() {
	return { __gb: 'A', __userGroup_id: '*', __userGroup_nm: '', __rows: '50', __page: '1' };
}

function buildAvailParam() {
	return {
		__gb: 'B',
		__userGroup_id: $('#F_USER_GRP_ID').val() || '*',
		__userGroup_nm: '*',
	};
}

function buildMemberParam() {
	return {
		__gb: 'C',
		__userGroup_id: $('#F_USER_GRP_ID').val() || '*',
		__userGroup_nm: '*',
	};
}

function loadMemberGrids() {
	if (!$('#F_USER_GRP_ID').val()) {
		ArchiveGrid.setRows(gridAvail, []);
		ArchiveGrid.setRows(gridMember, []);
		return;
	}
	ArchiveGrid.load(gridAvail);
	ArchiveGrid.load(gridMember);
}

function loadInfo(selectedRow) {
	$('#ROWID1').val('');
	$('#F_USER_GRP_ID').val(selectedRow.USER_GRP_ID).attr('readonly', true);
	$('#F_USER_GRP_NM').val(selectedRow.USER_GRP_NM);
	$('#F_DESCRIPTION').val(selectedRow.DESCRIPTION || '');
	$('input[name="F_USE_YN"]').val([selectedRow.USE_YN || 'Y']);
	$('#CRUD1').val('U');
	loadMemberGrids();
}

function moveToGroup(row) {
	gridMember.allRows = gridMember.allRows.concat([{ USER_ID: row.USER_ID, USER_NM: row.USER_NM }]);
	gridAvail.allRows = gridAvail.allRows.filter(function(r) { return r.USER_ID !== row.USER_ID; });
	ArchiveGrid.renderPaging(gridAvail);
	ArchiveGrid.renderPaging(gridMember);
}

function moveFromGroup(row) {
	gridAvail.allRows = gridAvail.allRows.concat([{ USER_ID: row.USER_ID, USER_NM: row.USER_NM }]);
	gridMember.allRows = gridMember.allRows.filter(function(r) { return r.USER_ID !== row.USER_ID; });
	ArchiveGrid.renderPaging(gridAvail);
	ArchiveGrid.renderPaging(gridMember);
}

$(function() {
	gridGroup = ArchiveGrid.create({
		url: 'GetUserGroup',
		pageSize: 10,
		bodySelector: '#gridBody',
		pagingSelector: '.wr_page',
		countSelector: '#totalCnt',
		columns: [
			{name:'USER_GRP_ID', align:'center'},
			{name:'USER_GRP_NM', align:'center'},
			{name:'DESCRIPTION', hidden:true},
			{name:'USE_YN', align:'center'},
		],
		getPostData: buildGroupParam,
		onRowClick: function(row) { loadInfo(row); },
	});
	gridAvail = ArchiveGrid.create({
		url: 'GetUserGroup',
		pageSize: 10,
		bodySelector: '#gridBody2',
		pagingSelector: '.wr_page2',
		columns: [
			{name:'USER_ID', hidden:true},
			{name:'USER_NM', align:'center'},
		],
		getPostData: buildAvailParam,
		onRowClick: moveToGroup,
	});
	gridMember = ArchiveGrid.create({
		url: 'GetUserGroup',
		pageSize: 10,
		bodySelector: '#gridBody3',
		pagingSelector: '.wr_page3',
		columns: [
			{name:'USER_ID', hidden:true},
			{name:'USER_NM', align:'center'},
		],
		getPostData: buildMemberParam,
		onRowClick: moveFromGroup,
	});
	ArchiveGrid.wirePage(gridGroup, { autoLoad: true });

	$('#btnAdd').click(function () {
		$('#F_USER_GRP_ID').attr('readonly', false).val('');
		$('#F_USER_GRP_NM').val('');
		$('#F_DESCRIPTION').val('');
		$('input[name="F_USE_YN"]').val(['Y']);
		$('#CRUD1').val('C');
		ArchiveGrid.setRows(gridAvail, []);
		ArchiveGrid.setRows(gridMember, []);
	});

	$('#btnSave').click(function () {
		var obj = {
			user_grp_id: $('#F_USER_GRP_ID').val(),
			user_grp_nm: $('#F_USER_GRP_NM').val(),
			description: $('#F_DESCRIPTION').val(),
			use_yn: $('input[name="F_USE_YN"]:checked').val(),
			crud: $('#CRUD1').val()
		};
		if (!obj.user_grp_id) { alert('[알림] 그룹ID를입력하세요'); return; }
		if (!obj.user_grp_nm) { alert('[알림] 그룹명을 입력하세요'); return; }
		$.ajax({
			url: 'SetUserGroup',
			type: 'POST',
			dataType: 'json',
			data: { param: JSON.stringify(obj) },
			success: function(json_data) {
				if (json_data.result === 'OK') {
					alert('정상적으로처리 되었습니다');
					$('#btnAdd').click();
					ArchiveGrid.load(gridGroup);
				}
			},
			error: function() { alert('조회 오류가 발생했습니다.'); }
		});
	});

	$('#btnSaveMember').click(function () {
		var user_grp_id = $('#F_USER_GRP_ID').val();
		if (!user_grp_id) { alert('[알림] 그룹을 선택하세요'); return; }
		var allObj = gridMember.allRows.map(function(r) { return { user_id: r.USER_ID }; });
		$.ajax({
			url: 'SetUserGroupMember',
			type: 'POST',
			dataType: 'json',
			data: { param: JSON.stringify(allObj), user_grp_id: user_grp_id },
			success: function(json_data) {
				if (json_data.result === 'OK') alert('정상적으로처리 되었습니다');
			},
			error: function() { alert('조회 오류가 발생했습니다.'); }
		});
	});
});
