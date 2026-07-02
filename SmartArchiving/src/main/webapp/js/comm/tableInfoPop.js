var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function trimTableVal(v) {
	if (v == null) return '';
	return String(v).trim();
}

function ensureTableInfoPopFilled() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetTableFormPop').length) return;

	var row = window._tablePopRow || null;
	var serverId = window._tablePopServerId || '';

	$pop.find('#F_SERVER_ID_POP').val(serverId);

	if (row) {
		$pop.find('.tit_pop').text('테이블 상세');
		$pop.find('#CRUD_POP').val('U');
		$pop.find('#F_TABLE_ID_POP').val(trimTableVal(row.TABLE_ID)).prop('readonly', true);
		$pop.find('#F_TABLE_CD_POP').val(trimTableVal(row.TABLE_CD));
		$pop.find('#F_TABLE_NM_POP').val(trimTableVal(row.TABLE_NM));
		$pop.find('#F_TABLE_JOIN_NM_POP').val(trimTableVal(row.TABLE_JOIN_NM));
		$pop.find('#F_SAVE_PREQ_CD_POP').val(trimTableVal(row.SAVE_PREQ_CD));
		$pop.find('#F_SAVE_PREQ_POP').val(trimTableVal(row.SAVE_PREQ));
		$pop.find('#F_EXP_PREQ_CD_POP').val(trimTableVal(row.EXP_PREQ_CD));
		$pop.find('#F_EXP_PREQ_POP').val(trimTableVal(row.EXP_PREQ));
		$pop.find('#F_DESCRIPTION_POP').val(trimTableVal(row.DESCRIPTION));
		$pop.find('input[name="F_USE_YN_POP"]').val([trimTableVal(row.USE_YN) || 'Y']);
	} else {
		$pop.find('.tit_pop').text('테이블 등록');
		$pop.find('#CRUD_POP').val('C');
		$pop.find('#F_TABLE_ID_POP').val('').prop('readonly', false);
		$pop.find('#F_TABLE_CD_POP').val('');
		$pop.find('#F_TABLE_NM_POP').val('');
		$pop.find('#F_TABLE_JOIN_NM_POP').val('');
		$pop.find('#F_SAVE_PREQ_POP').val('');
		$pop.find('#F_EXP_PREQ_POP').val('');
		$pop.find('#F_DESCRIPTION_POP').val('');
		$pop.find('#F_SAVE_PREQ_CD_POP option:eq(0)').prop('selected', true);
		$pop.find('#F_EXP_PREQ_CD_POP option:eq(0)').prop('selected', true);
		$pop.find('input[name="F_USE_YN_POP"]').val(['Y']);
	}
	$pop.find('#F_TABLE_ID_POP').focus();
}

function saveTableInfoPop() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetTableFormPop').length) return;

	var obj = {
		table_id: trimTableVal($pop.find('#F_TABLE_ID_POP').val()),
		table_cd: trimTableVal($pop.find('#F_TABLE_CD_POP').val()),
		table_nm: trimTableVal($pop.find('#F_TABLE_NM_POP').val()),
		table_join_nm: trimTableVal($pop.find('#F_TABLE_JOIN_NM_POP').val()),
		save_preq_cd: trimTableVal($pop.find('#F_SAVE_PREQ_CD_POP').val()),
		save_preq_nm: $pop.find('#F_SAVE_PREQ_CD_POP option:selected').text(),
		save_preq: trimTableVal($pop.find('#F_SAVE_PREQ_POP').val()),
		exp_preq_cd: trimTableVal($pop.find('#F_EXP_PREQ_CD_POP').val()),
		exp_preq_nm: $pop.find('#F_EXP_PREQ_CD_POP option:selected').text(),
		exp_preq: trimTableVal($pop.find('#F_EXP_PREQ_POP').val()),
		description: trimTableVal($pop.find('#F_DESCRIPTION_POP').val()),
		use_yn: $pop.find('input[name="F_USE_YN_POP"]:checked').val(),
		crud: $pop.find('#CRUD_POP').val(),
		serverId: trimTableVal($pop.find('#F_SERVER_ID_POP').val())
	};

	if (obj.table_id && isNaN(obj.table_id)) {
		alert('[알림] 테이블아이디는 숫자입니다');
		$pop.find('#F_TABLE_ID_POP').focus();
		return;
	}
	if (!obj.table_id) {
		alert('[알림] 테이블아이디를 입력하세요');
		$pop.find('#F_TABLE_ID_POP').focus();
		return;
	}
	if (!obj.table_cd) {
		alert('[알림] 테이블영문명을 입력하세요');
		$pop.find('#F_TABLE_CD_POP').focus();
		return;
	}
	if (!obj.table_nm) {
		alert('[알림] 테이블한글명을 입력하세요');
		$pop.find('#F_TABLE_NM_POP').focus();
		return;
	}
	if (!obj.serverId || obj.serverId === '0' || obj.serverId === '*') {
		alert('[알림] 테이블 등록 시 서버를 선택하세요');
		return;
	}

	$.ajax({
		url: archiveCtx + '/SetTable',
		type: 'post',
		data: { param: JSON.stringify(obj) },
		dataType: 'json',
		success: function() {
			var editedId = obj.table_id;
			closePop();
			if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
			if (window._selectedTableRow && String(window._selectedTableRow.TABLE_ID) === String(editedId)) {
				if (typeof reloadColumnList === 'function') reloadColumnList();
			}
			alert('정상적으로 처리 되었습니다');
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			if (xhr && xhr.responseJSON && xhr.responseJSON.error === 2) {
				alert('이미 등록되어 있는 아이디입니다');
			} else {
				alert('처리 시 오류가 발생하였습니다');
			}
		}
	});
}

(function bindTableInfoPop() {
	if (window._tableInfoPopBound) return;
	window._tableInfoPopBound = true;

	$(document).on('click', '#w2ui-popup #btnSavePop', function(e) {
		if (!$('#w2ui-popup #SetTableFormPop').length) return;
		e.preventDefault();
		saveTableInfoPop();
	});

	$(document).on('click', '#w2ui-popup #pop_close', function(e) {
		if (!$('#w2ui-popup #SetTableFormPop').length) return;
		e.preventDefault();
		closePop();
	});
})();
