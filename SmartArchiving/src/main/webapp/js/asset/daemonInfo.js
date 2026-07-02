var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function openDaemonInfoPop(row) {
	window._daemonPopRow = row || null;
	openArchivePopup('daemonInfoPop', {
		width: 720,
		height: 620,
		onReady: function() {
			if (typeof ensureDaemonInfoPopFilled === 'function') {
				ensureDaemonInfoPopFilled();
			}
		}
	});
}

function syncDaemonChkAll(grid) {
	var $chkAll = $('#chkAllDaemon');
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

function trimDaemonRowVal(v) {
	if (v == null) return '';
	return String(v).trim();
}

function getSelectedDaemonRow() {
	var grid = window._daemonInfoGrid;
	if (!grid) return null;

	var ids = ArchiveGrid.getCheckedKeys(grid);
	if (ids.length > 1) {
		alert('[알림] 한 건만 선택하세요');
		return null;
	}
	if (ids.length === 1) {
		var id = ids[0];
		var found = null;
		grid.allRows.forEach(function(r) {
			if (String(r.DAEMON_ID) === String(id)) found = r;
		});
		return found;
	}

	var $body = $(grid.bodySelector);
	var $sel = $body.find('tr.selected');
	if ($sel.length === 1) {
		var rowIdx = $body.find('tr').index($sel);
		var start = (grid.currentPage - 1) * grid.pageSize;
		return grid.allRows[start + rowIdx] || null;
	}

	alert('[알림] 데몬을 선택하세요');
	return null;
}

function executeDaemonCommand(crud) {
	var row = getSelectedDaemonRow();
	if (!row) return;

	var label = crud === 'START' ? '시작' : (crud === 'STOP' ? '종료' : '재시작');
	if (!confirm('선택한 데몬을 ' + label + ' 하시겠습니까?')) {
		return;
	}

	var obj = {
		daemon_id: trimDaemonRowVal(row.DAEMON_ID),
		daemon_cd: trimDaemonRowVal(row.DAEMON_CD),
		daemon_nm: trimDaemonRowVal(row.DAEMON_NM),
		daemon_start_path: trimDaemonRowVal(row.DAEMON_START_PATH),
		daemon_stop_path: trimDaemonRowVal(row.DAEMON_STOP_PATH),
		user_id: typeof daemonSessionUserId !== 'undefined' ? daemonSessionUserId : '',
		crud: crud
	};

	$.ajax({
		url: archiveCtx + '/SetDaemon',
		type: 'post',
		data: { param: JSON.stringify(obj) },
		dataType: 'json',
		success: function(jsonData) {
			if (jsonData && jsonData.result === 'OK') {
				var msg = '';
				if (jsonData.rows && jsonData.rows[0]) {
					msg = jsonData.rows[0].START_RST || jsonData.rows[0].STOP_RST || jsonData.rows[0].RESTART_RST || '';
				}
				if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid, { keepPage: true });
				alert(msg || '정상적으로 처리 되었습니다');
			} else {
				alert('처리가 실패하였습니다');
			}
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			alert('처리 시 오류가 발생하였습니다');
		}
	});
}

function deleteSelectedDaemons() {
	var grid = window._archiveGrid;
	if (!grid) return;

	var ids = ArchiveGrid.getCheckedKeys(grid);
	if (!ids.length) {
		alert('[알림] 삭제할 데몬을 선택하세요');
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
			alert('일부 데몬 삭제에 실패했습니다. (' + failed + '건)');
		} else {
			alert('정상적으로 처리 되었습니다');
		}
	}

	ids.forEach(function(id) {
		$.ajax({
			url: archiveCtx + '/SetDaemon',
			type: 'post',
			data: { param: JSON.stringify({ daemon_id: id, crud: 'D' }) },
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
		url: 'GetDaemonList',
		pageSize: 10,
		checkboxKeyField: 'DAEMON_ID',
		columns: [
			{name:'DAEMON_ID', hidden:true},
			{name:'_CHK', checkbox:true, keyName:'DAEMON_ID', align:'center', className:'col-chk'},
			{name:'DAEMON_CD', align:'center', className:'col-cd', ellipsis:true},
			{name:'DAEMON_NM', align:'left', className:'col-nm tal_cut', ellipsis:true},
			{name:'PROC_RUNNING', align:'center', className:'col-run'},
			{name:'PROC_PID', align:'center', className:'col-pid'},
			{name:'PORT', align:'center', className:'col-port'},
			{name:'HEALTH', align:'center', className:'col-health'},
			{name:'DAEMON_START_PATH', align:'left', className:'col-start-path tal_cut', ellipsis:true},
			{name:'DAEMON_STOP_PATH', align:'left', className:'col-stop-path tal_cut', ellipsis:true},
			{name:'DAEMON_RESTART_PATH', align:'left', className:'col-restart-path tal_cut', ellipsis:true},
			{name:'DAEMON_DESC', align:'left', className:'col-desc tal_cut', ellipsis:true},
			{name:'DAEMON_STAT_CD', hidden:true},
			{name:'DAEMON_PROCESS_SCHEDULE', hidden:true},
			{name:'DAEMON_STAT_NM', align:'center', className:'col-stat', ellipsis:true},
			{name:'DAEMON_RESTART_YN', align:'center', className:'col-restart-yn'},
			{name:'DAEMON_USE_YN', align:'center', className:'col-use-yn'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowDblClick: function(row) { openDaemonInfoPop(row); },
		onAfterRender: function(inst) { syncDaemonChkAll(inst); },
	});
	ArchiveGrid.wirePage(grid);
	window._daemonInfoGrid = grid;
});

function buildSearchParam() {
	var searchVal = $.trim($('#C_SEARCH_VAL').val() || '');
	return {
		__use_yn: $('input[name="C_USE_YN"]:checked').val(),
		__daemon_id: '*',
		__search_col: $('#C_SEARCH_COL').val() || '',
		__search_val: searchVal
	};
}

$(document).ready(function() {
	$('input[name="C_USE_YN"]').val(['Y']);

	$('#btnAdd').on('click', function(e) {
		e.preventDefault();
		openDaemonInfoPop();
	});

	$('#btnDelete').on('click', function(e) {
		e.preventDefault();
		deleteSelectedDaemons();
	});

	$('#btnStart').on('click', function(e) {
		e.preventDefault();
		executeDaemonCommand('START');
	});

	$('#btnStop').on('click', function(e) {
		e.preventDefault();
		executeDaemonCommand('STOP');
	});

	$('#C_SEARCH_VAL').on('keydown', function(e) {
		if (e.key === 'Enter' || e.keyCode === 13) {
			e.preventDefault();
			if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
		}
	});

	// $('#btnReStart').on('click', function(e) {
	// 	e.preventDefault();
	// 	executeDaemonCommand('RESTART');
	// });

	$('#chkAllDaemon').on('change', function() {
		var grid = window._daemonInfoGrid;
		if (!grid) return;
		ArchiveGrid.togglePageChecked(grid, this.checked);
	});

	$(document).on('change', '#gridBody .archive-grid-chk', function(e) {
		e.stopPropagation();
		var grid = window._daemonInfoGrid;
		if (!grid) return;
		var key = $(this).data('row-key');
		ArchiveGrid.setRowChecked(grid, key, this.checked);
		syncDaemonChkAll(grid);
	});

	if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
});
