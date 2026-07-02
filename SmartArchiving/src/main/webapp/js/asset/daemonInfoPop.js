var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function trimDaemonVal(v) {
	if (v == null) return '';
	return String(v).trim();
}

function ensureDaemonInfoPopFilled() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetDaemonFormPop').length) return;

	var row = window._daemonPopRow || null;
	if (row) {
		$pop.find('.tit_pop').text('데몬 상세');
		$pop.find('#CRUD_POP').val('U');
		$pop.find('#F_DAEMON_ID_POP').val(trimDaemonVal(row.DAEMON_ID));
		$pop.find('#F_DAEMON_CD_POP').val(trimDaemonVal(row.DAEMON_CD)).prop('readonly', true);
		$pop.find('#F_DAEMON_NM_POP').val(trimDaemonVal(row.DAEMON_NM));
		$pop.find('#F_DAEMON_START_PATH_POP').val(trimDaemonVal(row.DAEMON_START_PATH));
		$pop.find('#F_DAEMON_STOP_PATH_POP').val(trimDaemonVal(row.DAEMON_STOP_PATH));
		$pop.find('#F_DAEMON_RESTART_PATH_POP').val(trimDaemonVal(row.DAEMON_RESTART_PATH));
		$pop.find('#F_DAEMON_DESC_POP').val(trimDaemonVal(row.DAEMON_DESC));
		$pop.find('#F_DAEMON_STAT_CD_POP').val(trimDaemonVal(row.DAEMON_STAT_CD) || '1');
		$pop.find('#F_DAEMON_RESTART_YN_POP').val(trimDaemonVal(row.DAEMON_RESTART_YN));
		$pop.find('input[name="F_USE_YN_POP"]').val([trimDaemonVal(row.DAEMON_USE_YN) || 'Y']);
	} else {
		$pop.find('.tit_pop').text('데몬 등록');
		$pop.find('#CRUD_POP').val('C');
		$pop.find('#F_DAEMON_ID_POP').val('');
		$pop.find('#F_DAEMON_CD_POP').val('').prop('readonly', false);
		$pop.find('#F_DAEMON_NM_POP').val('');
		$pop.find('#F_DAEMON_START_PATH_POP').val('');
		$pop.find('#F_DAEMON_STOP_PATH_POP').val('');
		$pop.find('#F_DAEMON_RESTART_PATH_POP').val('');
		$pop.find('#F_DAEMON_DESC_POP').val('');
		$pop.find('#F_DAEMON_STAT_CD_POP').val('1');
		$pop.find('#F_DAEMON_RESTART_YN_POP').val('');
		$pop.find('input[name="F_USE_YN_POP"]').val(['Y']);
	}
	$pop.find('#F_DAEMON_CD_POP').focus();
}

function saveDaemonInfoPop() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetDaemonFormPop').length) return;

	var obj = {
		daemon_id: trimDaemonVal($pop.find('#F_DAEMON_ID_POP').val()),
		daemon_cd: trimDaemonVal($pop.find('#F_DAEMON_CD_POP').val()),
		daemon_nm: trimDaemonVal($pop.find('#F_DAEMON_NM_POP').val()),
		daemon_desc: trimDaemonVal($pop.find('#F_DAEMON_DESC_POP').val()),
		daemon_start_path: trimDaemonVal($pop.find('#F_DAEMON_START_PATH_POP').val()),
		daemon_stop_path: trimDaemonVal($pop.find('#F_DAEMON_STOP_PATH_POP').val()),
		daemon_restart_path: trimDaemonVal($pop.find('#F_DAEMON_RESTART_PATH_POP').val()),
		daemon_stat_cd: trimDaemonVal($pop.find('#F_DAEMON_STAT_CD_POP').val()) || '1',
		daemon_restart_yn: trimDaemonVal($pop.find('#F_DAEMON_RESTART_YN_POP').val()),
		use_yn: $pop.find('input[name="F_USE_YN_POP"]:checked').val(),
		user_id: trimDaemonVal($pop.find('#F_USER_ID_POP').val()),
		crud: $pop.find('#CRUD_POP').val()
	};

	if (!obj.daemon_cd) {
		alert('[알림] Daemon 코드를 입력하세요');
		$pop.find('#F_DAEMON_CD_POP').focus();
		return;
	}
	if (!obj.daemon_nm) {
		alert('[알림] Daemon명을 입력하세요');
		$pop.find('#F_DAEMON_NM_POP').focus();
		return;
	}

	$.ajax({
		url: archiveCtx + '/SetDaemon',
		type: 'post',
		data: { param: JSON.stringify(obj) },
		dataType: 'json',
		success: function(jsonData) {
			if (jsonData && jsonData.result === 'OK') {
				closePop();
				if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
				alert('정상적으로 처리 되었습니다');
			} else {
				alert('저장이 실패하였습니다');
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

(function bindDaemonInfoPop() {
	if (window._daemonInfoPopBound) return;
	window._daemonInfoPopBound = true;

	$(document).on('click', '#w2ui-popup #btnSavePop', function(e) {
		e.preventDefault();
		saveDaemonInfoPop();
	});

	$(document).on('click', '#w2ui-popup #pop_close', function(e) {
		e.preventDefault();
		closePop();
	});
})();
