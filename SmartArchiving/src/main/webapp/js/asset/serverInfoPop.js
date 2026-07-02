var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function trimServerVal(v) {
	if (v == null) return '';
	return String(v).trim();
}

function ensureServerInfoPopFilled() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetServerFormPop').length) return;

	var row = window._serverPopRow || null;
	if (row) {
		$pop.find('.tit_pop').text('서버 상세');
		$pop.find('#CRUD_POP').val('U');
		$pop.find('#F_SERVER_ID_POP').val(trimServerVal(row.SERVER_ID)).prop('readonly', true);
		$pop.find('#F_SERVER_NM_POP').val(trimServerVal(row.SERVER_NM));
		$pop.find('#F_SERVER_IP_POP').val(trimServerVal(row.SERVER_IP));
		$pop.find('#F_SERVER_DESC_POP').val(trimServerVal(row.SERVER_DESC));
		$pop.find('#F_SERVER_CLASS_CD_POP').val(trimServerVal(row.SERVER_CLASS_CD));
		$pop.find('input[name="F_USE_YN_POP"]').val([trimServerVal(row.USE_YN) || 'Y']);
	} else {
		$pop.find('.tit_pop').text('서버 등록');
		$pop.find('#CRUD_POP').val('C');
		$pop.find('#F_SERVER_ID_POP').val('').prop('readonly', true);
		$pop.find('#F_SERVER_NM_POP').val('');
		$pop.find('#F_SERVER_IP_POP').val('');
		$pop.find('#F_SERVER_DESC_POP').val('');
		$pop.find('#F_SERVER_CLASS_CD_POP option:eq(0)').prop('selected', true);
		$pop.find('input[name="F_USE_YN_POP"]').val(['Y']);
	}
	$pop.find('#F_SERVER_NM_POP').focus();
}

function saveServerInfoPop() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetServerFormPop').length) return;

	var obj = {
		server_id: trimServerVal($pop.find('#F_SERVER_ID_POP').val()),
		server_nm: trimServerVal($pop.find('#F_SERVER_NM_POP').val()),
		server_ip: trimServerVal($pop.find('#F_SERVER_IP_POP').val()),
		server_desc: trimServerVal($pop.find('#F_SERVER_DESC_POP').val()),
		server_class_cd: trimServerVal($pop.find('#F_SERVER_CLASS_CD_POP').val()),
		use_yn: $pop.find('input[name="F_USE_YN_POP"]:checked').val(),
		crud: $pop.find('#CRUD_POP').val()
	};

	if (!obj.server_nm) {
		alert('[알림] 서버명을 입력하세요');
		$pop.find('#F_SERVER_NM_POP').focus();
		return;
	}

	$.ajax({
		url: archiveCtx + '/SetServer',
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
			if (xhr && xhr.responseJSON && xhr.responseJSON.error === 2) {
				alert('이미 등록되어 있는 아이디입니다');
			} else {
				alert('처리 시 오류가 발생하였습니다');
			}
		}
	});
}

(function bindServerInfoPop() {
	if (window._serverInfoPopBound) return;
	window._serverInfoPopBound = true;

	$(document).on('click', '#w2ui-popup #btnSavePop', function(e) {
		e.preventDefault();
		saveServerInfoPop();
	});

	$(document).on('click', '#w2ui-popup #pop_close', function(e) {
		e.preventDefault();
		closePop();
	});
})();
