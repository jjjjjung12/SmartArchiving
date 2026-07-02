var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function openServerInfoPop(row) {
	window._serverPopRow = row || null;
	openArchivePopup('serverInfoPop', {
		width: 680,
		height: 560,
		onReady: function() {
			if (typeof ensureServerInfoPopFilled === 'function') {
				ensureServerInfoPopFilled();
			}
		}
	});
}

function syncServerChkAll(grid) {
	var $chkAll = $('#chkAllServer');
	if (!$chkAll.length || !grid) return;
	var $rows = $(grid.bodySelector).find('.archive-grid-chk');
	if (!$rows.length) {
		$chkAll.prop('checked', false).prop('indeterminate', false);
		return;
	}
	var checkedCount = $rows.filter(':checked').length;
	$chkAll.prop('checked', checkedCount === $rows.length);
	$chkAll.prop('indeterminate', checkedCount > 0 && checkedCount < $rows.length);
}

function deleteSelectedServers() {
	var grid = window._archiveGrid;
	if (!grid) return;

	var ids = ArchiveGrid.getCheckedKeys(grid);
	if (!ids.length) {
		alert('[알림] 삭제할 서버를 선택하세요');
		return;
	}
	if (!confirm('선택한 ' + ids.length + '건을 삭제하시겠습니까?')) {
		return;
	}

	var failed = 0;
	var done = 0;

	function finish() {
		if (done < ids.length) return;
		ArchiveGrid.clearChecked(grid);
		ArchiveGrid.load(grid, { keepPage: true });
		if (failed) {
			alert('일부 서버 삭제에 실패했습니다. (' + failed + '건)');
		} else {
			alert('정상적으로 처리 되었습니다');
		}
	}

	ids.forEach(function(id) {
		$.ajax({
			url: archiveCtx + '/SetServer',
			type: 'post',
			data: { param: JSON.stringify({ server_id: id, crud: 'D' }) },
			dataType: 'json',
			success: function(data) {
				if (!data || data.result !== 'OK') failed++;
			},
			error: function(xhr) {
				if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
					return;
				}
				failed++;
			},
			complete: function() {
				done++;
				finish();
			}
		});
	});
}

$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetServerList',
		pageSize: 10,
		checkboxKeyField: 'SERVER_ID',
		columns: [
			{name:'SERVER_ID', hidden:true},
			{name:'_CHK', checkbox:true, keyName:'SERVER_ID', align:'center', className:'col-chk'},
			{name:'SERVER_NM', align:'center', className:'col-name'},
			{name:'SERVER_CLASS_CD', hidden:true},
			{name:'SERVER_CLASS_NM', align:'center', className:'col-class'},
			{name:'SERVER_IP', align:'center', className:'col-ip'},
			{name:'USE_YN', align:'center', className:'col-use'},
			{name:'SERVER_DESC', align:'left', className:'col-desc tal_cut', ellipsis:true},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowDblClick: function(row) { openServerInfoPop(row); },
		onAfterRender: function(inst) { syncServerChkAll(inst); },
	});
	ArchiveGrid.wirePage(grid);
	window._serverInfoGrid = grid;
});

function buildSearchParam() {
	var searchVal = $.trim($('#C_SEARCH_VAL').val() || '');
	return {
		__use_yn: $('input[name="C_USE_YN"]:checked').val(),
		__server_id: '*',
		__search_col: $('#C_SEARCH_COL').val() || '',
		__search_val: searchVal
	};
}

$(document).ready(function() {
	$('input[name="C_USE_YN"]').val(['Y']);

	$('#btnAdd').on('click', function(e) {
		e.preventDefault();
		openServerInfoPop();
	});

	$('#btnDelete').on('click', function(e) {
		e.preventDefault();
		deleteSelectedServers();
	});

	$('#C_SEARCH_VAL').on('keydown', function(e) {
		if (e.key === 'Enter' || e.keyCode === 13) {
			e.preventDefault();
			if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
		}
	});

	$('#chkAllServer').on('change', function() {
		var grid = window._serverInfoGrid;
		if (!grid) return;
		ArchiveGrid.togglePageChecked(grid, this.checked);
	});

	$(document).on('change', '#gridBody .archive-grid-chk', function(e) {
		e.stopPropagation();
		var grid = window._serverInfoGrid;
		if (!grid) return;
		var key = $(this).data('row-key');
		ArchiveGrid.setRowChecked(grid, key, this.checked);
		syncServerChkAll(grid);
	});

	if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
});
