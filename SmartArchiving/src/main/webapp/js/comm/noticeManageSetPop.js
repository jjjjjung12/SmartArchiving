var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function saveNoticeManageSetPop() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetJobFormPop').length) return;

	if ($pop.find('#F_SUBJECT_POP').val().trim() === '') {
		alert('제목을 입력하세요');
		$pop.find('#F_SUBJECT_POP').focus();
		return;
	}
	if ($pop.find('#F_SUBJECT_DETAIL_POP').val().trim() === '') {
		alert('상세내용을 입력하세요');
		$pop.find('#F_SUBJECT_DETAIL_POP').focus();
		return;
	}

	var frmData = new FormData($pop.find('#SetJobFormPop')[0]);

	$.ajax({
		url: archiveCtx + '/SetNoticeManage',
		type: 'post',
		processData: false,
		contentType: false,
		cache: false,
		data: frmData,
		dataType: 'json',
		success: function(data) {
			if (data && data.result === 'OK') {
				closePop();
				if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
				alert('정상적으로 처리 되었습니다');
			} else {
				alert('저장이 실패하였습니다');
			}
		},
		error: function(xhr) {
			if (xhr && xhr.responseJSON && xhr.responseJSON.error === 2) {
				alert('이미 등록되어 있는 데이터입니다');
			} else {
				alert('처리 시 오류가 발생하였습니다');
			}
		}
	});
}

(function bindNoticeManageSetPop() {
	if (window._noticeManageSetPopBound) return;
	window._noticeManageSetPopBound = true;

	$(document).on('click', '#w2ui-popup #btnSavePop', function(e) {
		e.preventDefault();
		saveNoticeManageSetPop();
	});

	$(document).on('click', '#w2ui-popup #pop_close', function(e) {
		e.preventDefault();
		closePop();
	});

	if (typeof w2popup !== 'undefined') {
		w2popup.on('open', function(event) {
			event.onComplete = function() {
				var $pop = $('#w2ui-popup');
				if (!$pop.find('#SetJobFormPop').length) return;
				if (typeof toDayPoint !== 'undefined' && toDayPoint && !$pop.find('#F_REG_DATE_POP').val()) {
					$pop.find('#F_REG_DATE_POP').val(toDayPoint);
				}
			};
		});
	}
})();
